package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution;

import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution.OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.ArchiveRequirement;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution.OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.BoundaryRule;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution.OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.CiBatch;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution.OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.GateCheck;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution.OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.MarkdownSection;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution.OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.OperatorHandoff;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution.OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.ReadTarget;
import com.codexdemo.orderplatform.ops.maintenance.rendering.MarkdownSections;
import java.util.List;

final class ExecutionRenderer {

  private ExecutionRenderer() {}

  static List<MarkdownSection> render(
      List<ReadTarget> readTargets,
      List<GateCheck> gateChecks,
      List<BoundaryRule> boundaryRules,
      List<CiBatch> ciBatches,
      List<ArchiveRequirement> archiveRequirements,
      List<OperatorHandoff> operatorHandoffs) {
    return List.of(
        MarkdownSections.counted(
            "Read Targets",
            "read-target-count",
            readTargets,
            ExecutionRenderer::readTargetLine,
            MarkdownSection::new),
        MarkdownSections.groupedCounted(
            "Gate Checks",
            "gate-check-count",
            gateChecks,
            GateCheck::group,
            check -> check.code() + "=" + check.evidence(),
            MarkdownSection::new),
        MarkdownSections.counted(
            "Boundary Rules",
            "boundary-rule-count",
            boundaryRules,
            ExecutionRenderer::boundaryLine,
            MarkdownSection::new),
        MarkdownSections.counted(
            "CI Batches",
            "ci-batch-count",
            ciBatches,
            ExecutionRenderer::ciBatchLine,
            MarkdownSection::new),
        MarkdownSections.counted(
            "Archive Requirements",
            "archive-requirement-count",
            archiveRequirements,
            ExecutionRenderer::archiveLine,
            MarkdownSection::new),
        MarkdownSections.counted(
            "Operator Handoff",
            "operator-handoff-count",
            operatorHandoffs,
            ExecutionRenderer::handoffLine,
            MarkdownSection::new));
  }

  private static String readTargetLine(ReadTarget target) {
    return String.join(
        " | ",
        target.target(),
        target.owner(),
        target.protocol(),
        target.addressHandle(),
        target.commandOrRoute(),
        "status=" + target.status());
  }

  private static String boundaryLine(BoundaryRule rule) {
    return String.join(
        " | ", rule.code(), rule.owner(), rule.forbiddenAction(), "allowed=" + rule.allowed());
  }

  private static String ciBatchLine(CiBatch batch) {
    return batch.order()
        + ". "
        + batch.name()
        + " | "
        + batch.commandFamily()
        + " | "
        + batch.scope();
  }

  private static String archiveLine(ArchiveRequirement requirement) {
    return String.join(
        " | ",
        requirement.artifact(),
        requirement.producer(),
        requirement.evidence(),
        "required=" + requirement.required());
  }

  private static String handoffLine(OperatorHandoff handoff) {
    return String.join(" | ", handoff.step(), handoff.owner(), handoff.instruction());
  }
}
