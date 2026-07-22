package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution;

import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution.OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse.ArtifactVerification;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution.OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse.BoundaryVerification;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution.OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse.CiBatchVerification;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution.OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse.GateCheckVerification;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution.OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse.OperatorHandoffVerification;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution.OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse.ReadTargetVerification;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution.OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse.ScorecardEntry;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution.OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse.SourceRegistrySnapshot;
import java.util.List;

final class ArchiveCatalog {

  private ArchiveCatalog() {}

  static Evidence evidence(
      OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse sourceRegistry) {
    return new Evidence(
        snapshots(sourceRegistry),
        artifacts(sourceRegistry),
        readTargets(sourceRegistry),
        gateChecks(sourceRegistry),
        boundaries(sourceRegistry),
        ciBatches(sourceRegistry),
        handoffs(sourceRegistry),
        scorecard(sourceRegistry));
  }

  private static List<SourceRegistrySnapshot> snapshots(
      OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse sourceRegistry) {
    return List.of(
        new SourceRegistrySnapshot(
            sourceRegistry.version(),
            sourceRegistry.endpoint(),
            sourceRegistry.profile(),
            sourceRegistry.sourcePlan(),
            sourceRegistry.readTargetCount(),
            sourceRegistry.gateCheckCount(),
            sourceRegistry.boundaryRuleCount(),
            sourceRegistry.status()));
  }

  private static List<ArtifactVerification> artifacts(
      OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse sourceRegistry) {
    return sourceRegistry.archiveRequirements().stream()
        .map(
            requirement ->
                new ArtifactVerification(
                    requirement.artifact(),
                    requirement.producer(),
                    requirement.evidence(),
                    requirement.required(),
                    requirement.required() ? "passed" : "blocked"))
        .toList();
  }

  private static List<ReadTargetVerification> readTargets(
      OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse sourceRegistry) {
    return sourceRegistry.readTargets().stream()
        .map(
            target ->
                new ReadTargetVerification(
                    target.target(),
                    target.commandOrRoute(),
                    target.status(),
                    "passed".equals(target.status()),
                    "passed".equals(target.status()) ? "passed" : "blocked"))
        .toList();
  }

  private static List<GateCheckVerification> gateChecks(
      OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse sourceRegistry) {
    return sourceRegistry.gateChecks().stream()
        .map(
            check ->
                new GateCheckVerification(
                    check.code(),
                    check.group(),
                    check.passed(),
                    check.passed(),
                    check.passed() ? "passed" : "blocked"))
        .toList();
  }

  private static List<BoundaryVerification> boundaries(
      OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse sourceRegistry) {
    return sourceRegistry.boundaryRules().stream()
        .map(
            rule ->
                new BoundaryVerification(
                    rule.code(),
                    rule.forbiddenAction(),
                    rule.allowed(),
                    !rule.allowed(),
                    rule.allowed() ? "blocked" : "passed"))
        .toList();
  }

  private static List<CiBatchVerification> ciBatches(
      OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse sourceRegistry) {
    return sourceRegistry.ciBatches().stream()
        .map(
            batch ->
                new CiBatchVerification(
                    batch.name(), batch.order(), batch.commandFamily(), true, "passed"))
        .toList();
  }

  private static List<OperatorHandoffVerification> handoffs(
      OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse sourceRegistry) {
    return sourceRegistry.operatorHandoffs().stream()
        .map(
            handoff ->
                new OperatorHandoffVerification(
                    handoff.step(),
                    handoff.owner(),
                    handoff.manual(),
                    handoff.manual() ? "passed" : "blocked"))
        .toList();
  }

  private static List<ScorecardEntry> scorecard(
      OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse sourceRegistry) {
    return List.of(
        score("source-registry", 1, "passed".equals(sourceRegistry.status()) ? 1 : 0),
        score(
            "archive-artifacts",
            OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistrySupport
                .EXPECTED_ARTIFACT_VERIFICATION_COUNT,
            sourceRegistry.archiveRequirementCount()),
        score(
            "read-targets",
            OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistrySupport
                .EXPECTED_READ_TARGET_VERIFICATION_COUNT,
            sourceRegistry.passedReadTargetCount()),
        score(
            "gate-checks",
            OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistrySupport
                .EXPECTED_GATE_CHECK_VERIFICATION_COUNT,
            sourceRegistry.passedGateCheckCount()),
        score(
            "boundary-denials",
            OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistrySupport
                .EXPECTED_BOUNDARY_VERIFICATION_COUNT,
            sourceRegistry.deniedBoundaryRuleCount()),
        score(
            "ci-batches",
            OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistrySupport
                .EXPECTED_CI_BATCH_VERIFICATION_COUNT,
            sourceRegistry.ciBatchCount()),
        score(
            "operator-handoffs",
            OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistrySupport
                .EXPECTED_OPERATOR_HANDOFF_VERIFICATION_COUNT,
            sourceRegistry.operatorHandoffCount()));
  }

  private static ScorecardEntry score(String name, int expected, int actual) {
    return new ScorecardEntry(name, expected, actual, expected == actual ? "passed" : "blocked");
  }

  record Evidence(
      List<SourceRegistrySnapshot> sourceRegistrySnapshots,
      List<ArtifactVerification> artifactVerifications,
      List<ReadTargetVerification> readTargetVerifications,
      List<GateCheckVerification> gateCheckVerifications,
      List<BoundaryVerification> boundaryVerifications,
      List<CiBatchVerification> ciBatchVerifications,
      List<OperatorHandoffVerification> operatorHandoffVerifications,
      List<ScorecardEntry> scorecard) {
    Evidence {
      sourceRegistrySnapshots = List.copyOf(sourceRegistrySnapshots);
      artifactVerifications = List.copyOf(artifactVerifications);
      readTargetVerifications = List.copyOf(readTargetVerifications);
      gateCheckVerifications = List.copyOf(gateCheckVerifications);
      boundaryVerifications = List.copyOf(boundaryVerifications);
      ciBatchVerifications = List.copyOf(ciBatchVerifications);
      operatorHandoffVerifications = List.copyOf(operatorHandoffVerifications);
      scorecard = List.copyOf(scorecard);
    }
  }
}
