/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/

package com.thoughtworks.gauge;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleComponent;
import com.thoughtworks.gauge.connection.GaugeConnection;
import com.thoughtworks.gauge.core.Gauge;
import com.thoughtworks.gauge.core.GaugeExceptionHandler;
import com.thoughtworks.gauge.core.GaugeService;
import com.thoughtworks.gauge.exception.GaugeNotFoundException;
import com.thoughtworks.gauge.module.GaugeModuleType;
import com.thoughtworks.gauge.module.lib.LibHelperFactory;
import com.thoughtworks.gauge.settings.GaugeSettingsModel;
import com.thoughtworks.gauge.util.GaugeUtil;
import com.thoughtworks.gauge.util.SocketUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

import static com.thoughtworks.gauge.util.GaugeUtil.classpathForModule;
import static com.thoughtworks.gauge.util.GaugeUtil.getGaugeSettings;
import static com.thoughtworks.gauge.util.GaugeUtil.isGaugeProjectDir;
import static com.thoughtworks.gauge.util.GaugeUtil.moduleDir;

/**
 * The definition for what a Gauge module is according to IDEA.
 */
public class GaugeModuleComponent implements ModuleComponent {
    private final Module module;
    private static final Logger LOG = Logger.getInstance("#com.thoughtworks.gauge.GaugeModuleComponent");

    public GaugeModuleComponent(Module module) {
        this.module = module;
    }

    @NotNull
    @Override
    public String getComponentName() {
        return "GaugeModuleComponent";
    }

    @Override
    public void moduleAdded() {
        new LibHelperFactory().helperFor(module).checkDeps();
    }

    /**
     * Creates a gauge service for the particular module. GaugeService is used to make api calls to the gauge daemon process.
     *
     * @param module
     * @return
     */
    public static GaugeService createGaugeService(Module module) {
        int freePortForApi = SocketUtils.findFreePortForApi();
        Process gaugeProcess = initializeGaugeProcess(freePortForApi, module);
        GaugeConnection gaugeConnection = initializeGaugeConnection(freePortForApi);
        GaugeService gaugeService = new GaugeService(gaugeProcess, gaugeConnection);
        Gauge.addModule(module, gaugeService);
        return gaugeService;
    }

    private static GaugeConnection initializeGaugeConnection(int apiPort) {
        if (apiPort != -1) {
            LOG.warn("Initializing Gauge connection");
            GaugeConnection gaugeConn = null;

            for (int i = 1; i <= 10; i++) {
                try {
                    gaugeConn = new GaugeConnection(apiPort);
                    break;
                } catch (java.lang.RuntimeException ex) {
                    LOG.warn("Unable to open connection on try " + i + ".   Waiting and trying again");
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            return gaugeConn;
        } else {
            return null;
        }
    }

    private static Process initializeGaugeProcess(int apiPort, Module module) {
        try {
            GaugeSettingsModel settings = getGaugeSettings();
            String port = String.valueOf(apiPort);
            ProcessBuilder gauge = new ProcessBuilder(settings.getGaugePath(), Constants.DAEMON, port);
            GaugeUtil.setGaugeEnvironmentsTo(gauge, settings);
            String cp = classpathForModule(module);
            LOG.info(String.format("Setting `%s` to `%s`", Constants.GAUGE_CUSTOM_CLASSPATH, cp));
            gauge.environment().put(Constants.GAUGE_CUSTOM_CLASSPATH, cp);
            File dir = moduleDir(module);
            LOG.info(String.format("Using `%s` as api port to connect to gauge API for project %s", port, dir));
            gauge.directory(dir);
            Process process = gauge.start();
            new GaugeExceptionHandler(process, module.getProject()).start();
            return process;
        } catch (IOException | GaugeNotFoundException e) {
            LOG.error("An error occurred while starting gauge api. \n" + e);
        }
        return null;
    }

    /**
     * Sets the type of the given module to that of a Gauge module
     * @param module the module to be set
     */
    public static void makeGaugeModuleType(Module module) {
        module.setModuleType(GaugeModuleType.MODULE_TYPE_ID);
    }

    /**
     * Returns whether the module is a Gauge module. A module is a Gauge module if either its module type name
     * indicates that it is a Gauge module, or if it is a Gauge project.
     * @param module the module to be examined
     * @return whether the module is a Gauge module.
     */
    public static boolean isGaugeModule(Module module) {
        return GaugeModuleType.MODULE_TYPE_ID.equals(module.getModuleTypeName()) || isGaugeProjectDir(moduleDir(module));
    }

    /**
     * Returns whether or not the module is a Gauge project. A module is a Gauge project if the module is also a
     * Gauge project directory (i.e. it has a `specs` directory and other required components).
     * @param module the module to be examined
     * @return whether or not the module is a Gauge project
     */
    public static boolean isGaugeProject(Module module) {
        return isGaugeProjectDir(moduleDir(module));
    }
}
