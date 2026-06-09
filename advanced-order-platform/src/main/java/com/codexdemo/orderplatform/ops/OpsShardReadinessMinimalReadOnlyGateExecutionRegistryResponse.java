package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse(
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
        String previousSmokeLane,
        String registryState,
        int sourcePlanCount,
        int readTargetCount,
        int passedReadTargetCount,
        int gateCheckCount,
        int passedGateCheckCount,
        int boundaryRuleCount,
        int deniedBoundaryRuleCount,
        int ciBatchCount,
        int archiveRequirementCount,
        int operatorHandoffCount,
        List<SourcePlanEntry> sourcePlans,
        List<ReadTarget> readTargets,
        List<GateCheck> gateChecks,
        List<BoundaryRule> boundaryRules,
        List<CiBatch> ciBatches,
        List<ArchiveRequirement> archiveRequirements,
        List<OperatorHandoff> operatorHandoffs,
        List<MarkdownSection> markdownSections,
        List<String> checks,
        String status
) {

    public record SourcePlanEntry(
            String nodeVersion,
            String title,
            String role,
            String result,
            int expectedChecks,
            int passedChecks
    ) {
    }

    public record ReadTarget(
            String target,
            String owner,
            String protocol,
            String addressHandle,
            String commandOrRoute,
            boolean readOnly,
            boolean externallyStarted,
            String status
    ) {
    }

    public record GateCheck(
            String code,
            String group,
            String evidence,
            boolean passed
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

    public record CiBatch(
            String name,
            int order,
            String commandFamily,
            String scope,
            boolean blocksNextBatch
    ) {
    }

    public record ArchiveRequirement(
            String artifact,
            String producer,
            String evidence,
            boolean required
    ) {
    }

    public record OperatorHandoff(
            String step,
            String owner,
            String instruction,
            boolean manual
    ) {
    }

    public record MarkdownSection(
            String heading,
            List<String> lines
    ) {
    }
}
