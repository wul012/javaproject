package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessScreenshotExplanationArchiveRegistryResponse(
        String project,
        String version,
        boolean readOnly,
        boolean executionAllowed,
        boolean startsJavaService,
        boolean startsMiniKvService,
        boolean capturesScreenshot,
        boolean movesHistoricalArchive,
        boolean readsCredentialValue,
        boolean resolvesRawEndpointUrl,
        boolean managedAuditHttpAllowed,
        String endpoint,
        String profile,
        String sourcePlan,
        String legacyRoot,
        String nextRoot,
        String registryState,
        int currentArchiveAssessmentCount,
        int segmentPlanCount,
        int namingRuleCount,
        int boundaryRuleCount,
        int deniedBoundaryRuleCount,
        int verificationStepCount,
        int markdownSectionCount,
        List<CurrentArchiveAssessment> currentArchiveAssessments,
        List<ArchiveSegmentPlan> segmentPlans,
        List<NamingRule> namingRules,
        List<BoundaryRule> boundaryRules,
        List<VerificationStep> verificationSteps,
        List<MarkdownSection> markdownSections,
        List<String> checks,
        String status
) {

    public record CurrentArchiveAssessment(
            String root,
            int versionDirectoryCount,
            int fileCount,
            String status,
            String nextAction
    ) {
    }

    public record ArchiveSegmentPlan(
            String segment,
            String path,
            String versionRange,
            String purpose,
            boolean active
    ) {
    }

    public record NamingRule(
            String code,
            String pattern,
            String rationale,
            boolean required
    ) {
    }

    public record BoundaryRule(
            String code,
            String forbiddenAction,
            boolean allowed,
            String rationale
    ) {
    }

    public record VerificationStep(
            String name,
            String commandOrClass,
            String scope,
            boolean required
    ) {
    }

    public record MarkdownSection(
            String heading,
            List<String> lines
    ) {
    }
}
