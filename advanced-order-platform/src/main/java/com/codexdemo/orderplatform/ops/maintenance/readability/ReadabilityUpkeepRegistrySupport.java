package com.codexdemo.orderplatform.ops.maintenance.readability;

import java.util.ArrayList;
import java.util.List;

final class ReadabilityUpkeepRegistrySupport {

    static final String PROJECT = "advanced-order-platform";
    static final String SOURCE_ADVICE =
            "D:\\C\\four-project-readable-upkeep\\Java-order-governance-advice";
    static final String DOCS_ROOT = "docs/ops";
    static final String PACKAGE_ROOT =
            "com.codexdemo.orderplatform.ops.maintenance.readability";
    static final String REGISTRY_STATE =
            "readability-upkeep-subpackage-registry-active-v1781";
    static final int EXPECTED_TOPIC_COUNT = 5;
    static final int EXPECTED_PACKAGE_RULE_COUNT = 4;
    static final int EXPECTED_TEMPLATE_RULE_COUNT = 10;
    static final int EXPECTED_CLASS_NAME_TRIAL_COUNT = 3;
    static final int EXPECTED_BOUNDARY_RULE_COUNT = 8;
    static final int EXPECTED_VERIFICATION_STEP_COUNT = 6;

    private ReadabilityUpkeepRegistrySupport() {
    }

    static ReadabilityUpkeepRegistryResponse response(
            String version,
            String endpoint,
            String profile,
            List<ReadabilityUpkeepRegistryResponse.TopicMap> topics,
            List<ReadabilityUpkeepRegistryResponse.PackageRule> packageRules,
            List<ReadabilityUpkeepRegistryResponse.RegistryTemplateRule> templateRules,
            List<ReadabilityUpkeepRegistryResponse.ClassNameTrial> classNameTrials,
            List<ReadabilityUpkeepRegistryResponse.BoundaryRule> boundaryRules,
            List<ReadabilityUpkeepRegistryResponse.VerificationStep> verificationSteps,
            List<ReadabilityUpkeepRegistryResponse.MarkdownSection> markdownSections
    ) {
        var topicCopy = List.copyOf(topics);
        var packageRuleCopy = List.copyOf(packageRules);
        var templateRuleCopy = List.copyOf(templateRules);
        var classNameTrialCopy = List.copyOf(classNameTrials);
        var boundaryRuleCopy = List.copyOf(boundaryRules);
        var verificationStepCopy = List.copyOf(verificationSteps);
        var markdownSectionCopy = List.copyOf(markdownSections);

        int deniedBoundaryRuleCount = (int) boundaryRuleCopy.stream()
                .filter(rule -> !rule.allowed())
                .count();
        boolean topicsIndexed = topicCopy.stream()
                .allMatch(ReadabilityUpkeepRegistryResponse.TopicMap::indexed);
        boolean newPackageRulesPresent = packageRuleCopy.stream()
                .filter(ReadabilityUpkeepRegistryResponse.PackageRule::appliesToNewCode)
                .count() >= 3;
        boolean templateRequired = templateRuleCopy.stream()
                .allMatch(ReadabilityUpkeepRegistryResponse.RegistryTemplateRule::required);
        boolean classNameTrialsActive = classNameTrialCopy.stream()
                .allMatch(ReadabilityUpkeepRegistryResponse.ClassNameTrial
                        ::activeForNewSubpackages);
        boolean verificationRequired = verificationStepCopy.stream()
                .allMatch(ReadabilityUpkeepRegistryResponse.VerificationStep::required);
        boolean statusPassed = topicCopy.size() == EXPECTED_TOPIC_COUNT
                && packageRuleCopy.size() == EXPECTED_PACKAGE_RULE_COUNT
                && templateRuleCopy.size() == EXPECTED_TEMPLATE_RULE_COUNT
                && classNameTrialCopy.size() == EXPECTED_CLASS_NAME_TRIAL_COUNT
                && boundaryRuleCopy.size() == EXPECTED_BOUNDARY_RULE_COUNT
                && verificationStepCopy.size() == EXPECTED_VERIFICATION_STEP_COUNT
                && deniedBoundaryRuleCount == boundaryRuleCopy.size()
                && topicsIndexed
                && newPackageRulesPresent
                && templateRequired
                && classNameTrialsActive
                && verificationRequired;

        List<String> checks = new ArrayList<>();
        checks.add("readability-upkeep-source-advice-java-only");
        checks.add("readability-upkeep-docs-root-" + DOCS_ROOT);
        checks.add("readability-upkeep-package-root-" + PACKAGE_ROOT);
        checks.add("readability-upkeep-topic-count-" + topicCopy.size());
        checks.add("readability-upkeep-package-rule-count-" + packageRuleCopy.size());
        checks.add("readability-upkeep-template-rule-count-" + templateRuleCopy.size());
        checks.add("readability-upkeep-class-name-trial-count-" + classNameTrialCopy.size());
        checks.add("readability-upkeep-boundary-rule-count-" + boundaryRuleCopy.size());
        checks.add("readability-upkeep-denied-boundary-rule-count-"
                + deniedBoundaryRuleCount);
        checks.add("readability-upkeep-verification-step-count-"
                + verificationStepCopy.size());
        checks.add("readability-upkeep-new-code-subpackage-first");
        checks.add("readability-upkeep-registry-template-required");
        checks.add("readability-upkeep-short-class-name-trial-active");
        checks.add("readability-upkeep-no-write-routing");
        checks.add("readability-upkeep-no-credential-value");
        checks.add("readability-upkeep-no-upstream-autostart");

        return new ReadabilityUpkeepRegistryResponse(
                PROJECT,
                version,
                true,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                endpoint,
                profile,
                SOURCE_ADVICE,
                DOCS_ROOT,
                PACKAGE_ROOT,
                REGISTRY_STATE,
                topicCopy.size(),
                packageRuleCopy.size(),
                templateRuleCopy.size(),
                classNameTrialCopy.size(),
                boundaryRuleCopy.size(),
                deniedBoundaryRuleCount,
                verificationStepCopy.size(),
                markdownSectionCopy.size(),
                topicCopy,
                packageRuleCopy,
                templateRuleCopy,
                classNameTrialCopy,
                boundaryRuleCopy,
                verificationStepCopy,
                markdownSectionCopy,
                List.copyOf(checks),
                statusPassed ? "passed" : "blocked"
        );
    }
}
