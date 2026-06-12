package com.codexdemo.orderplatform.ops.maintenance.readability;

import java.util.ArrayList;
import java.util.List;

final class ReadabilityUpkeepRegistryRenderer {

    private ReadabilityUpkeepRegistryRenderer() {
    }

    static List<ReadabilityUpkeepRegistryResponse.MarkdownSection> render(
            List<ReadabilityUpkeepRegistryResponse.TopicMap> topics,
            List<ReadabilityUpkeepRegistryResponse.PackageRule> packageRules,
            List<ReadabilityUpkeepRegistryResponse.RegistryTemplateRule> templateRules,
            List<ReadabilityUpkeepRegistryResponse.ClassNameTrial> classNameTrials,
            List<ReadabilityUpkeepRegistryResponse.BoundaryRule> boundaryRules,
            List<ReadabilityUpkeepRegistryResponse.VerificationStep> verificationSteps
    ) {
        return List.of(
                section("Topic Maps", renderTopics(topics)),
                section("Package Rules", renderPackageRules(packageRules)),
                section("Registry Template Rules", renderTemplateRules(templateRules)),
                section("Class Name Trials", renderClassNameTrials(classNameTrials)),
                section("Boundary Rules", renderBoundaryRules(boundaryRules)),
                section("Verification Steps", renderVerificationSteps(verificationSteps))
        );
    }

    private static List<String> renderTopics(
            List<ReadabilityUpkeepRegistryResponse.TopicMap> topics
    ) {
        List<String> lines = new ArrayList<>();
        for (var topic : topics) {
            lines.add("- " + topic.code()
                    + " docsPath=" + topic.docsPath()
                    + " sourcePattern=" + topic.sourcePattern()
                    + " indexed=" + topic.indexed());
        }
        return lines;
    }

    private static List<String> renderPackageRules(
            List<ReadabilityUpkeepRegistryResponse.PackageRule> packageRules
    ) {
        List<String> lines = new ArrayList<>();
        for (var rule : packageRules) {
            lines.add("- " + rule.code()
                    + " packageName=" + rule.packageName()
                    + " appliesToNewCode=" + rule.appliesToNewCode());
        }
        return lines;
    }

    private static List<String> renderTemplateRules(
            List<ReadabilityUpkeepRegistryResponse.RegistryTemplateRule> templateRules
    ) {
        List<String> lines = new ArrayList<>();
        for (var rule : templateRules) {
            lines.add("- " + rule.code()
                    + " requiredLayer=" + rule.requiredLayer()
                    + " evidence=" + rule.evidence()
                    + " required=" + rule.required());
        }
        return lines;
    }

    private static List<String> renderClassNameTrials(
            List<ReadabilityUpkeepRegistryResponse.ClassNameTrial> classNameTrials
    ) {
        List<String> lines = new ArrayList<>();
        for (var trial : classNameTrials) {
            lines.add("- " + trial.code()
                    + " oldNamePattern=" + trial.oldNamePattern()
                    + " newNamePattern=" + trial.newNamePattern()
                    + " activeForNewSubpackages=" + trial.activeForNewSubpackages());
        }
        return lines;
    }

    private static List<String> renderBoundaryRules(
            List<ReadabilityUpkeepRegistryResponse.BoundaryRule> boundaryRules
    ) {
        List<String> lines = new ArrayList<>();
        for (var rule : boundaryRules) {
            lines.add("- " + rule.code()
                    + " forbiddenAction=" + rule.forbiddenAction()
                    + " allowed=" + rule.allowed());
        }
        return lines;
    }

    private static List<String> renderVerificationSteps(
            List<ReadabilityUpkeepRegistryResponse.VerificationStep> verificationSteps
    ) {
        List<String> lines = new ArrayList<>();
        for (var step : verificationSteps) {
            lines.add("- " + step.name()
                    + " commandOrClass=" + step.commandOrClass()
                    + " required=" + step.required());
        }
        return lines;
    }

    private static ReadabilityUpkeepRegistryResponse.MarkdownSection section(
            String heading,
            List<String> lines
    ) {
        return new ReadabilityUpkeepRegistryResponse.MarkdownSection(heading, List.copyOf(lines));
    }
}
