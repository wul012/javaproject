package com.codexdemo.orderplatform.ops;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessCodeWalkthroughComplianceRegistrySupport {

    static final String PROJECT = "advanced-order-platform";
    static final String SOURCE_PLAN = "Node v367 / Node v368";
    static final String ARCHIVE_DIRECTORY =
            "代码讲解记录_生产雏形阶段4/v1728-v1747";
    static final String REGISTRY_STATE =
            "future-walkthrough-structure-enforced-with-read-only-runtime-boundaries";
    static final int LEGACY_MARKER_CUTOFF_VERSION = 289;
    static final int LEGACY_MARKED_WALKTHROUGH_COUNT = 291;
    static final int EXPECTED_VERSION_COUNT = 20;
    static final int EXPECTED_REQUIRED_HEADING_COUNT = 9;

    private OpsShardReadinessCodeWalkthroughComplianceRegistrySupport() {
    }

    static OpsShardReadinessCodeWalkthroughComplianceRegistryResponse response(
            String version,
            String endpoint,
            String profile,
            List<OpsShardReadinessCodeWalkthroughComplianceRegistryResponse.VersionEntry>
                    versions,
            List<OpsShardReadinessCodeWalkthroughComplianceRegistryResponse.RequiredHeading>
                    requiredHeadings,
            List<OpsShardReadinessCodeWalkthroughComplianceRegistryResponse.ArchiveRange>
                    archiveRanges,
            List<OpsShardReadinessCodeWalkthroughComplianceRegistryResponse.DocumentationRule>
                    documentationRules,
            List<OpsShardReadinessCodeWalkthroughComplianceRegistryResponse.BoundaryRule>
                    boundaryRules,
            List<OpsShardReadinessCodeWalkthroughComplianceRegistryResponse.TestCoverage>
                    testCoverages,
            List<OpsShardReadinessCodeWalkthroughComplianceRegistryResponse.MarkdownSection>
                    markdownSections
    ) {
        var versionCopy = List.copyOf(versions);
        var headingCopy = List.copyOf(requiredHeadings);
        var archiveRangeCopy = List.copyOf(archiveRanges);
        var documentationRuleCopy = List.copyOf(documentationRules);
        var boundaryRuleCopy = List.copyOf(boundaryRules);
        var testCoverageCopy = List.copyOf(testCoverages);
        var markdownSectionCopy = List.copyOf(markdownSections);
        int deniedBoundaryRuleCount = (int) boundaryRuleCopy.stream()
                .filter(rule -> !rule.allowed())
                .count();
        boolean versionsStandard = versionCopy.stream()
                .allMatch(entry -> "standard".equals(entry.status()));
        boolean statusPassed = versionCopy.size() == EXPECTED_VERSION_COUNT
                && headingCopy.size() == EXPECTED_REQUIRED_HEADING_COUNT
                && deniedBoundaryRuleCount == boundaryRuleCopy.size()
                && versionsStandard;

        List<String> checks = new ArrayList<>();
        checks.add("code-walkthrough-compliance-source-plan-" + SOURCE_PLAN);
        checks.add("code-walkthrough-compliance-version-range-v1728-v1747");
        checks.add("code-walkthrough-compliance-version-count-" + versionCopy.size());
        checks.add("code-walkthrough-compliance-required-heading-count-" + headingCopy.size());
        checks.add("code-walkthrough-compliance-archive-range-count-" + archiveRangeCopy.size());
        checks.add("code-walkthrough-compliance-documentation-rule-count-"
                + documentationRuleCopy.size());
        checks.add("code-walkthrough-compliance-boundary-rule-count-" + boundaryRuleCopy.size());
        checks.add("code-walkthrough-compliance-denied-boundary-rule-count-"
                + deniedBoundaryRuleCount);
        checks.add("code-walkthrough-compliance-test-coverage-count-" + testCoverageCopy.size());
        checks.add("code-walkthrough-compliance-markdown-section-count-"
                + markdownSectionCopy.size());
        checks.add("code-walkthrough-compliance-future-cutoff-v"
                + LEGACY_MARKER_CUTOFF_VERSION);
        checks.add("code-walkthrough-compliance-legacy-marked-count-"
                + LEGACY_MARKED_WALKTHROUGH_COUNT);
        checks.add("code-walkthrough-compliance-no-write-routing");
        checks.add("code-walkthrough-compliance-no-active-shard-router");
        checks.add("code-walkthrough-compliance-no-credential-value");
        checks.add("code-walkthrough-compliance-no-raw-endpoint-url");
        checks.add("code-walkthrough-compliance-no-managed-audit-connection");
        checks.add("code-walkthrough-compliance-no-upstream-autostart");

        return new OpsShardReadinessCodeWalkthroughComplianceRegistryResponse(
                PROJECT,
                version,
                true,
                false,
                false,
                false,
                false,
                false,
                false,
                endpoint,
                profile,
                SOURCE_PLAN,
                ARCHIVE_DIRECTORY,
                REGISTRY_STATE,
                versionCopy.size(),
                headingCopy.size(),
                archiveRangeCopy.size(),
                documentationRuleCopy.size(),
                boundaryRuleCopy.size(),
                deniedBoundaryRuleCount,
                testCoverageCopy.size(),
                markdownSectionCopy.size(),
                versionCopy,
                headingCopy,
                archiveRangeCopy,
                documentationRuleCopy,
                boundaryRuleCopy,
                testCoverageCopy,
                markdownSectionCopy,
                List.copyOf(checks),
                statusPassed ? "passed" : "blocked"
        );
    }
}
