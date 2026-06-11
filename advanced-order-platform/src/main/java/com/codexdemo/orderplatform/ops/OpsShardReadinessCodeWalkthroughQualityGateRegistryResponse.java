package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessCodeWalkthroughQualityGateRegistryResponse(
        String project,
        String version,
        boolean readOnly,
        boolean executionAllowed,
        boolean startsJavaService,
        boolean startsMiniKvService,
        boolean readsCredentialValue,
        boolean resolvesRawEndpointUrl,
        boolean managedAuditHttpAllowed,
        String endpoint,
        String profile,
        String sourcePlan,
        String priorComplianceRegistry,
        String registryState,
        int versionRuleCount,
        int explanationRubricCount,
        int evidenceAnchorCount,
        int reviewChecklistCount,
        int boundaryRuleCount,
        int deniedBoundaryRuleCount,
        int markdownSectionCount,
        List<VersionRule> versionRules,
        List<ExplanationRubric> explanationRubrics,
        List<EvidenceAnchor> evidenceAnchors,
        List<ReviewChecklist> reviewChecklists,
        List<BoundaryRule> boundaryRules,
        List<MarkdownSection> markdownSections,
        List<String> checks,
        String status
) {

    public record VersionRule(
            String code,
            String minimumScope,
            String explanationRequirement,
            String splitGuidance,
            boolean required
    ) {
    }

    public record ExplanationRubric(
            String section,
            String mustExplain,
            String standoutSignal,
            int minimumEvidencePoints
    ) {
    }

    public record EvidenceAnchor(
            String anchor,
            String owner,
            String source,
            String requiredProof,
            boolean runtimeFree
    ) {
    }

    public record ReviewChecklist(
            String item,
            String reviewerQuestion,
            String releaseBlocker,
            boolean blocksRelease
    ) {
    }

    public record BoundaryRule(
            String code,
            String owner,
            String forbiddenAction,
            boolean allowed,
            String rationale
    ) {
    }

    public record MarkdownSection(
            String heading,
            List<String> lines
    ) {
    }
}
