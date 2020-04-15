/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/

package com.thoughtworks.gauge.language.psi;

import com.intellij.lang.ASTNode;
import com.intellij.navigation.ItemPresentation;
import com.intellij.openapi.module.Module;
import com.intellij.psi.PsiElement;
import com.thoughtworks.gauge.StepValue;
import com.thoughtworks.gauge.connection.GaugeConnection;
import com.thoughtworks.gauge.core.Gauge;
import com.thoughtworks.gauge.core.GaugeService;
import com.thoughtworks.gauge.language.psi.impl.SpecStepImpl;
import com.thoughtworks.gauge.util.GaugeUtil;
import com.thoughtworks.gauge.util.StepUtil;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.ArrayList;

public class SpecPsiImplUtil {

    public static StepValue getStepValue(SpecStep element) {
        ASTNode step = element.getNode();
        String stepText = step.getText().trim();
        int newLineIndex = stepText.indexOf("\n");
        int endIndex = newLineIndex == -1 ? stepText.length() : newLineIndex;
        SpecTable inlineTable = element.getInlineTable();
        stepText = stepText.substring(1, endIndex).trim();
        return getStepValueFor(element, stepText, inlineTable != null);
    }

    public static StepValue getStepValueFor(PsiElement element, String stepText, Boolean hasInlineTable) {
        return getStepValueFor(GaugeUtil.moduleForPsiElement(element), element, stepText, hasInlineTable);
    }

    public static StepValue getStepValueFor(Module module, PsiElement element, String stepText, Boolean hasInlineTable) {
        GaugeService gaugeService = Gauge.getGaugeService(module, false);
        if (gaugeService == null) {
            return getDefaultStepValue(element);
        }
        GaugeConnection apiConnection = gaugeService.getGaugeConnection();
        if (apiConnection == null) {
            return getDefaultStepValue(element);
        }
        StepValue value = StepUtil.getStepValue(apiConnection, stepText, hasInlineTable);
        return value == null ? getDefaultStepValue(element) : value;
    }

    private static StepValue getDefaultStepValue(PsiElement element) {
        return new StepValue(element.getText(), element.getText(), new ArrayList<>());
    }

    public static ItemPresentation getPresentation(final SpecStepImpl element) {
        return new ItemPresentation() {
            @Nullable
            @Override
            public String getPresentableText() {
                return element.getText();
            }

            @Nullable
            @Override
            public String getLocationString() {
                return element.getContainingFile().getName();
            }

            @Nullable
            @Override
            public Icon getIcon(boolean unused) {
                return null;
            }
        };
    }
}
