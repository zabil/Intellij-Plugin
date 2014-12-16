package com.thoughtworks.gauge.execution;

import com.intellij.execution.actions.ConfigurationContext;
import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.util.Ref;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.thoughtworks.gauge.language.SpecFile;
import com.thoughtworks.gauge.language.psi.impl.SpecScenarioImpl;
import com.thoughtworks.gauge.language.psi.impl.SpecTableImpl;
import com.thoughtworks.gauge.language.token.SpecTokenTypes;

public class ScenarioExecutionProducer extends GaugeExecutionProducer {
    Integer NO_SCENARIOS = -1;
    Integer NON_SCENARIO_CONTEXT = -2;

    public ScenarioExecutionProducer() {
        super();
    }
    protected ScenarioExecutionProducer(ConfigurationFactory configurationFactory) {
        super(configurationFactory);
    }

    @Override
    protected boolean setupConfigurationFromContext(RunConfiguration configuration, ConfigurationContext context, Ref sourceElement) {

        if (context.getPsiLocation() == null || !(context.getPsiLocation().getContainingFile() instanceof SpecFile) || context.getPsiLocation().getContainingFile().getVirtualFile() == null)
            return false;

        try {
            Project project = context.getPsiLocation().getContainingFile().getProject();
            Module module = ProjectRootManager.getInstance(project).getFileIndex().getModuleForFile(context.getPsiLocation().getContainingFile().getVirtualFile());
            String name = context.getPsiLocation().getContainingFile().getVirtualFile().getCanonicalPath();
            Integer scenarioIndex = getScenarioIndex(context, context.getPsiLocation().getContainingFile());
            if(scenarioIndex == NO_SCENARIOS){
                configuration.setName("Context step(s)");
                ((GaugeRunConfiguration) configuration).setSpecsToExecute(name + ":0");
                ((GaugeRunConfiguration) configuration).setModule(module);
            } else if(scenarioIndex == NON_SCENARIO_CONTEXT){
                configuration.setName("Scenario(default)");
                ((GaugeRunConfiguration) configuration).setSpecsToExecute(name + ":0");
                ((GaugeRunConfiguration) configuration).setModule(module);
            }
            else {
                String scenarioName = getScenarioName(context);
                configuration.setName(scenarioName);
                ((GaugeRunConfiguration) configuration).setSpecsToExecute(name + ":" + scenarioIndex);
                ((GaugeRunConfiguration) configuration).setModule(module);
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return true;
    }


    private String getScenarioName(ConfigurationContext context) {
        PsiElement selectedElement = context.getPsiLocation();
        String scenarioName = null;

        if(selectedElement== null) return null;
        if(selectedElement.getClass().equals(SpecScenarioImpl.class)){
            scenarioName= selectedElement.getText();
        }else {
            String text = getScenarioHeading(selectedElement).trim();
            if(text.equals("*")) {
                scenarioName =  selectedElement.getParent().getParent().getNode().getFirstChildNode().getText();
            } else scenarioName = text;
        }
        if(scenarioName.startsWith("##"))
            scenarioName = scenarioName.replaceFirst("##","");
        scenarioName = scenarioName.trim();
        if(scenarioName.contains("\n"))
            return scenarioName.substring(0,scenarioName.indexOf("\n"));
        else
            return scenarioName;
    }

    private Integer getScenarioIndex(ConfigurationContext context, PsiFile file) {
        Integer count = NO_SCENARIOS;
        PsiElement selectedElement = context.getPsiLocation();
        if (selectedElement == null)    return 0;
        String scenarioHeading = (!selectedElement.getClass().equals(SpecScenarioImpl.class)) ? getScenarioHeading(selectedElement) : selectedElement.getText();
        for (PsiElement psiElement : file.getChildren())
            if (psiElement.getClass().equals(SpecScenarioImpl.class)) {
                count++;
                if (psiElement.getNode().getFirstChildNode().getText().equals(scenarioHeading)) return count;
            }
        if(count == NO_SCENARIOS) return NO_SCENARIOS;
        if(count == 1) return NON_SCENARIO_CONTEXT;
        return 0;
    }

    private String getScenarioHeading(PsiElement selectedElement) {
        if (selectedElement.getClass().equals(SpecScenarioImpl.class)) {
            return selectedElement.getNode().getFirstChildNode().getText();
        }
        if (selectedElement.getParent() == null) return "";
        return getScenarioHeading(selectedElement.getParent());
    }
}
