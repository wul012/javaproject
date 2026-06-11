package com.codexdemo.orderplatform.ops;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessScreenshotExplanationArchiveRegistrySupport {

    static final String PROJECT = "advanced-order-platform";
    static final String SOURCE_PLAN = "Node v367 / Java v1769-v1773";
    static final String LEGACY_ROOT = "d";
    static final String NEXT_ROOT = "f";
    static final String REGISTRY_STATE =
            "screenshot-explanation-archives-canonical-f-root-ready";
    static final int EXPECTED_CURRENT_ARCHIVE_ASSESSMENT_COUNT = 3;
    static final int EXPECTED_SEGMENT_PLAN_COUNT = 5;
    static final int EXPECTED_NAMING_RULE_COUNT = 6;
    static final int EXPECTED_BOUNDARY_RULE_COUNT = 8;
    static final int EXPECTED_VERIFICATION_STEP_COUNT = 5;

    private OpsShardReadinessScreenshotExplanationArchiveRegistrySupport() {
    }

    static OpsShardReadinessScreenshotExplanationArchiveRegistryResponse response(
            String version,
            String endpoint,
            String profile,
            List<OpsShardReadinessScreenshotExplanationArchiveRegistryResponse
                    .CurrentArchiveAssessment> currentArchiveAssessments,
            List<OpsShardReadinessScreenshotExplanationArchiveRegistryResponse.ArchiveSegmentPlan>
                    segmentPlans,
            List<OpsShardReadinessScreenshotExplanationArchiveRegistryResponse.NamingRule>
                    namingRules,
            List<OpsShardReadinessScreenshotExplanationArchiveRegistryResponse.BoundaryRule>
                    boundaryRules,
            List<OpsShardReadinessScreenshotExplanationArchiveRegistryResponse.VerificationStep>
                    verificationSteps,
            List<OpsShardReadinessScreenshotExplanationArchiveRegistryResponse.MarkdownSection>
                    markdownSections
    ) {
        var currentArchiveCopy = List.copyOf(currentArchiveAssessments);
        var segmentPlanCopy = List.copyOf(segmentPlans);
        var namingRuleCopy = List.copyOf(namingRules);
        var boundaryRuleCopy = List.copyOf(boundaryRules);
        var verificationStepCopy = List.copyOf(verificationSteps);
        var markdownSectionCopy = List.copyOf(markdownSections);
        int deniedBoundaryRuleCount = (int) boundaryRuleCopy.stream()
                .filter(rule -> !rule.allowed())
                .count();
        boolean namingRulesRequired = namingRuleCopy.stream()
                .allMatch(OpsShardReadinessScreenshotExplanationArchiveRegistryResponse
                        .NamingRule::required);
        boolean verificationRequired = verificationStepCopy.stream()
                .allMatch(OpsShardReadinessScreenshotExplanationArchiveRegistryResponse
                        .VerificationStep::required);
        boolean hasNextRoot = currentArchiveCopy.stream()
                .anyMatch(assessment -> NEXT_ROOT.equals(assessment.root()));
        boolean hasCurrentSegment = segmentPlanCopy.stream()
                .anyMatch(segment -> segment.path().contains("f/v1769-v1773")
                        && segment.active());
        boolean statusPassed = currentArchiveCopy.size() == EXPECTED_CURRENT_ARCHIVE_ASSESSMENT_COUNT
                && segmentPlanCopy.size() == EXPECTED_SEGMENT_PLAN_COUNT
                && namingRuleCopy.size() == EXPECTED_NAMING_RULE_COUNT
                && boundaryRuleCopy.size() == EXPECTED_BOUNDARY_RULE_COUNT
                && verificationStepCopy.size() == EXPECTED_VERIFICATION_STEP_COUNT
                && deniedBoundaryRuleCount == boundaryRuleCopy.size()
                && namingRulesRequired
                && verificationRequired
                && hasNextRoot
                && hasCurrentSegment;

        List<String> checks = new ArrayList<>();
        checks.add("screenshot-explanation-archive-source-plan-" + SOURCE_PLAN);
        checks.add("screenshot-explanation-archive-legacy-root-" + LEGACY_ROOT);
        checks.add("screenshot-explanation-archive-next-root-" + NEXT_ROOT);
        checks.add("screenshot-explanation-archive-canonical-root-f");
        checks.add("screenshot-explanation-archive-transition-root-closed-d_runtime_screenshot_archive_next");
        checks.add("screenshot-explanation-archive-current-assessment-count-"
                + currentArchiveCopy.size());
        checks.add("screenshot-explanation-archive-segment-plan-count-" + segmentPlanCopy.size());
        checks.add("screenshot-explanation-archive-naming-rule-count-" + namingRuleCopy.size());
        checks.add("screenshot-explanation-archive-boundary-rule-count-" + boundaryRuleCopy.size());
        checks.add("screenshot-explanation-archive-denied-boundary-rule-count-"
                + deniedBoundaryRuleCount);
        checks.add("screenshot-explanation-archive-verification-step-count-"
                + verificationStepCopy.size());
        checks.add("screenshot-explanation-archive-no-root-dumping");
        checks.add("screenshot-explanation-archive-no-screenshot-capture");
        checks.add("screenshot-explanation-archive-no-historical-move");
        checks.add("screenshot-explanation-archive-no-write-routing");
        checks.add("screenshot-explanation-archive-no-credential-value");
        checks.add("screenshot-explanation-archive-no-raw-endpoint-url");
        checks.add("screenshot-explanation-archive-no-upstream-autostart");

        return new OpsShardReadinessScreenshotExplanationArchiveRegistryResponse(
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
                false,
                endpoint,
                profile,
                SOURCE_PLAN,
                LEGACY_ROOT,
                NEXT_ROOT,
                REGISTRY_STATE,
                currentArchiveCopy.size(),
                segmentPlanCopy.size(),
                namingRuleCopy.size(),
                boundaryRuleCopy.size(),
                deniedBoundaryRuleCount,
                verificationStepCopy.size(),
                markdownSectionCopy.size(),
                currentArchiveCopy,
                segmentPlanCopy,
                namingRuleCopy,
                boundaryRuleCopy,
                verificationStepCopy,
                markdownSectionCopy,
                List.copyOf(checks),
                statusPassed ? "passed" : "blocked"
        );
    }
}
