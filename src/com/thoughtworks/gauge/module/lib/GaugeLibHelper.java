/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/

package com.thoughtworks.gauge.module.lib;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.roots.CompilerModuleExtension;
import com.intellij.openapi.roots.ContentEntry;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.roots.libraries.Library;
import com.intellij.openapi.roots.libraries.LibraryTable;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.thoughtworks.gauge.connection.GaugeConnection;
import com.thoughtworks.gauge.GaugeModuleComponent;
import com.thoughtworks.gauge.PluginNotInstalledException;
import com.thoughtworks.gauge.core.Gauge;
import com.thoughtworks.gauge.core.GaugeService;

import java.io.File;
import java.io.IOException;

import static com.intellij.openapi.roots.OrderRootType.CLASSES;
import static com.thoughtworks.gauge.util.GaugeUtil.moduleDir;
import static com.thoughtworks.gauge.util.GaugeUtil.moduleDirPath;

public class GaugeLibHelper extends AbstractLibHelper {
    public static final String PROJECT_LIB = "project-lib";
    public static final String GAUGE_LIB = "gauge-lib";
    public static final String JAVA = "java";
    private static final String SRC_DIR = new File(new File("src", "test"), JAVA).getPath();
    public static final String LIBS = "libs";
    private static final Logger LOG = Logger.getInstance("#com.thoughtworks.gauge.module.lib.GaugeLibHelper");

    public GaugeLibHelper(Module module) {
        super(module);
    }

    public void checkDeps() {
        final ModifiableRootModel modifiableModel = ModuleRootManager.getInstance(getModule()).getModifiableModel();
        if (!gaugeJavaLibIsAdded(modifiableModel)) {
            addGaugeJavaLib(modifiableModel);
        } else {
            updateGaugeJavaLibIfNeeded(modifiableModel);
        }
        addProjectLibIfNeeded(modifiableModel);
        checkProjectSourceAndOutputDirectory(modifiableModel);
        ApplicationManager.getApplication().runWriteAction(modifiableModel::commit);

    }

    private void checkProjectSourceAndOutputDirectory(ModifiableRootModel modifiableModel) {
        VirtualFile[] sourceRoots = modifiableModel.getSourceRoots();
        if (sourceRoots.length < 1) {
            ContentEntry contentEntry = modifiableModel.addContentEntry(modifiableModel.getProject().getBaseDir());
            VirtualFile srcPath = srcPath(modifiableModel);
            if (srcPath != null) {
                contentEntry.addSourceFolder(srcPath, false);
            }
            CompilerModuleExtension compilerModuleExtension = modifiableModel.getModuleExtension(CompilerModuleExtension.class);
            compilerModuleExtension.setCompilerOutputPath(outputPath(modifiableModel.getModule()));
            compilerModuleExtension.setCompilerOutputPathForTests(testOutputPath(modifiableModel.getModule()));
            compilerModuleExtension.inheritCompilerOutputPath(false);
            compilerModuleExtension.commit();
        }
    }

    private VirtualFile testOutputPath(Module module) {
        File outputDir = new File(String.format("%s%sout%stest%s%s", moduleDirPath(module), File.separator, File.separator, File.separator, module.getName()));
        outputDir.mkdirs();
        return LocalFileSystem.getInstance().refreshAndFindFileByIoFile(outputDir);
    }

    private VirtualFile outputPath(Module module) {
        File outputDir = new File(String.format("%s%sout%sproduction%s%s", moduleDirPath(module), File.separator, File.separator, File.separator, module.getName()));
        outputDir.mkdirs();
        return LocalFileSystem.getInstance().refreshAndFindFileByIoFile(outputDir);
    }

    private VirtualFile srcPath(ModifiableRootModel modifiableModel) {
        return LocalFileSystem.getInstance().refreshAndFindFileByIoFile(new File(moduleDir(modifiableModel.getModule()), SRC_DIR));
    }

    private void addProjectLibIfNeeded(ModifiableRootModel model) {
        Library library = model.getModuleLibraryTable().getLibraryByName(PROJECT_LIB);
        if (library == null) {
            addProjectLib(model);
        }
    }

    private void updateGaugeJavaLibIfNeeded(ModifiableRootModel model) {
        LibraryTable libraryTable = model.getModuleLibraryTable();
        Library library = libraryTable.getLibraryByName(GAUGE_LIB);
        ProjectLib latestGaugeLib = gaugeLib(model.getModule());
        updateLibrary(library, latestGaugeLib);
    }

    private void updateLibrary(Library library, ProjectLib newLib) {
        VirtualFile lib = LocalFileSystem.getInstance().refreshAndFindFileByIoFile(newLib.getDir());
        Library.ModifiableModel model = library.getModifiableModel();
        if (lib != null) {
            model.removeRoot(getClassesRootFrom(model), CLASSES);
            model.addJarDirectory(lib, true, CLASSES);
        }
        model.commit();
    }

    private String getClassesRootFrom(Library.ModifiableModel model) {
        return model.getUrls(CLASSES)[0];
    }

    private boolean gaugeJavaLibIsAdded(ModifiableRootModel model) {
        Library library = model.getModuleLibraryTable().getLibraryByName(GAUGE_LIB);
        return !(library == null);
    }

    private void addGaugeJavaLib(ModifiableRootModel modifiableRootModel) {
        Module module = modifiableRootModel.getModule();
        ProjectLib gaugeLib = gaugeLib(module);
        if (gaugeLib != null) {
            addLib(gaugeLib, modifiableRootModel);
        }
    }

    private void addProjectLib(ModifiableRootModel modifiableRootModel) {
        addLib(projectLib(modifiableRootModel.getModule()), modifiableRootModel);
    }

    private void addLib(ProjectLib lib, ModifiableRootModel modifiableRootModel) {
        final Library library = modifiableRootModel.getModuleLibraryTable().createLibrary(lib.getLibName());
        final VirtualFile libDir = LocalFileSystem.getInstance().refreshAndFindFileByIoFile(lib.getDir());
        if (libDir != null) {
            final Library.ModifiableModel libModel = library.getModifiableModel();
            libModel.addJarDirectory(libDir, true);
            libModel.commit();
        }
    }


    private ProjectLib projectLib(Module module) {
        return new ProjectLib(PROJECT_LIB, new File(moduleDir(module), LIBS));
    }

    private ProjectLib gaugeLib(Module module) {
        String libRoot;
        try {
            GaugeService gaugeService = Gauge.getGaugeService(module, true);
            if (gaugeService == null) {
                gaugeService = GaugeModuleComponent.createGaugeService(module);
            }
            GaugeConnection gaugeConnection = gaugeService.getGaugeConnection();
            if (gaugeConnection == null) {
                throw new IOException("Gauge api connection not established");
            }
            libRoot = gaugeConnection.getLibPath("java");
        } catch (IOException e) {
            System.err.println("Could not add gauge lib, add it manually: " + e.getMessage());
            LOG.debug("Could not add gauge lib, add it manually: " + e.getMessage());
            return null;
        } catch (PluginNotInstalledException e) {
            throw new RuntimeException("Gauge " + JAVA + " plugin is not installed.");
        }
        return new ProjectLib(GAUGE_LIB, new File(libRoot));
    }

    private class ProjectLib {
        private String libName;
        public File dir;

        public ProjectLib(String libName, File dir) {
            this.libName = libName;
            this.dir = dir;
        }

        public String getLibName() {
            return libName;
        }

        public File getDir() {
            return dir;
        }
    }
}
