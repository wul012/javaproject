package com.codexdemo.orderplatform.ops;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessMinimalReadOnlyGateExecutionArchiveRenderer {

    private OpsShardReadinessMinimalReadOnlyGateExecutionArchiveRenderer() {
    }

    static List<OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.MarkdownSection>
            render(
                    List<OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.BoundaryRule>
                            boundaryRules,
                    List<OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.CiBatch>
                            ciBatches,
                    List<OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.ArchiveRequirement>
                            archiveRequirements,
                    List<OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.OperatorHandoff>
                            operatorHandoffs
            ) {
        return List.of(
                boundarySection(boundaryRules),
                ciSection(ciBatches),
                archiveSection(archiveRequirements),
                handoffSection(operatorHandoffs)
        );
    }

    private static OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.MarkdownSection
            boundarySection(
                    List<OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.BoundaryRule>
                            boundaryRules
            ) {
        List<String> lines = new ArrayList<>();
        lines.add("boundary-rule-count=" + boundaryRules.size());
        boundaryRules.forEach(rule -> lines.add(String.join(
                " | ",
                rule.code(),
                rule.owner(),
                rule.forbiddenAction(),
                OpsShardReadinessMinimalReadOnlyGateExecutionRendererSupport.flag("allowed", rule.allowed())
        )));
        return OpsShardReadinessMinimalReadOnlyGateExecutionRendererSupport.section(
                "Boundary Rules",
                lines
        );
    }

    private static OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.MarkdownSection
            ciSection(
                    List<OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.CiBatch>
                            ciBatches
            ) {
        List<String> lines = new ArrayList<>();
        lines.add("ci-batch-count=" + ciBatches.size());
        ciBatches.forEach(batch -> lines.add(batch.order()
                + ". "
                + batch.name()
                + " | "
                + batch.commandFamily()
                + " | "
                + batch.scope()));
        return OpsShardReadinessMinimalReadOnlyGateExecutionRendererSupport.section(
                "CI Batches",
                lines
        );
    }

    private static OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.MarkdownSection
            archiveSection(
                    List<OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.ArchiveRequirement>
                            archiveRequirements
            ) {
        List<String> lines = new ArrayList<>();
        lines.add("archive-requirement-count=" + archiveRequirements.size());
        archiveRequirements.forEach(requirement -> lines.add(String.join(
                " | ",
                requirement.artifact(),
                requirement.producer(),
                requirement.evidence(),
                OpsShardReadinessMinimalReadOnlyGateExecutionRendererSupport.flag(
                        "required",
                        requirement.required())
        )));
        return OpsShardReadinessMinimalReadOnlyGateExecutionRendererSupport.section(
                "Archive Requirements",
                lines
        );
    }

    private static OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.MarkdownSection
            handoffSection(
                    List<OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.OperatorHandoff>
                            operatorHandoffs
            ) {
        List<String> lines = new ArrayList<>();
        lines.add("operator-handoff-count=" + operatorHandoffs.size());
        operatorHandoffs.forEach(handoff -> lines.add(String.join(
                " | ",
                handoff.step(),
                handoff.owner(),
                handoff.instruction()
        )));
        return OpsShardReadinessMinimalReadOnlyGateExecutionRendererSupport.section(
                "Operator Handoff",
                lines
        );
    }
}
