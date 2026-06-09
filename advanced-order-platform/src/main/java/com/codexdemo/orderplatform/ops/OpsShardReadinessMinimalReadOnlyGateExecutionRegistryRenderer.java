package com.codexdemo.orderplatform.ops;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessMinimalReadOnlyGateExecutionRegistryRenderer {

    private OpsShardReadinessMinimalReadOnlyGateExecutionRegistryRenderer() {
    }

    static List<OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.MarkdownSection>
            render(
                    List<OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.ReadTarget>
                            readTargets,
                    List<OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.GateCheck>
                            gateChecks,
                    List<OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.BoundaryRule>
                            boundaryRules,
                    List<OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.CiBatch>
                            ciBatches,
                    List<OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.ArchiveRequirement>
                            archiveRequirements,
                    List<OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.OperatorHandoff>
                            operatorHandoffs
            ) {
        List<OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.MarkdownSection> sections =
                new ArrayList<>();
        sections.add(OpsShardReadinessMinimalReadOnlyGateExecutionReadTargetRenderer.render(readTargets));
        sections.add(OpsShardReadinessMinimalReadOnlyGateExecutionGateRenderer.render(gateChecks));
        sections.addAll(OpsShardReadinessMinimalReadOnlyGateExecutionArchiveRenderer.render(
                boundaryRules,
                ciBatches,
                archiveRequirements,
                operatorHandoffs
        ));
        return List.copyOf(sections);
    }
}
