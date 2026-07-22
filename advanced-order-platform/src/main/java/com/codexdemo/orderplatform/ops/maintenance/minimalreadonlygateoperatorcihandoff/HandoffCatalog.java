package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoff;

import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution.OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoff.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse.BoundaryLock;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoff.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse.CiBatchPlan;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoff.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse.OperatorLane;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoff.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse.ScorecardEntry;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoff.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse.SourceArchiveSnapshot;
import java.util.List;

final class HandoffCatalog {

  private HandoffCatalog() {}

  static Evidence evidence(
      OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse archive) {
    var lanes = lanes(archive);
    var batches = batches();
    var locks = locks();
    return new Evidence(
        snapshots(archive), lanes, batches, locks, scorecard(archive, lanes, batches, locks));
  }

  private static List<SourceArchiveSnapshot> snapshots(
      OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse archive) {
    return List.of(
        new SourceArchiveSnapshot(
            archive.version(),
            archive.endpoint(),
            archive.sourcePlan(),
            archive.archiveState(),
            archive.artifactVerificationCount(),
            archive.readTargetVerificationCount(),
            archive.gateCheckVerificationCount(),
            archive.boundaryVerificationCount(),
            archive.status()));
  }

  private static List<OperatorLane> lanes(
      OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse archive) {
    return List.of(
        lane(
            "focused",
            1,
            "ci-operator",
            archive.version(),
            "Run focused registry and catalog tests first."),
        lane(
            "grouped",
            2,
            "ci-operator",
            archive.version(),
            "Run grouped controller and route evidence tests after focused success."),
        lane(
            "build",
            3,
            "build-operator",
            archive.version(),
            "Run Maven compile and non-Docker regression before smoke."),
        lane(
            "smoke",
            4,
            "release-operator",
            archive.version(),
            "Run read-only smoke only after build validation passes."));
  }

  private static OperatorLane lane(
      String lane, int order, String owner, String sourceEvidence, String instruction) {
    return new OperatorLane(lane, order, owner, sourceEvidence, true, instruction);
  }

  private static List<CiBatchPlan> batches() {
    return List.of(
        batch(
            "archive-verification-registry",
            1,
            "focused",
            "OpsShardReadinessMinimalReadOnlyGateExecutionArchive*Tests",
            true),
        batch(
            "operator-ci-handoff-registry",
            2,
            "focused",
            "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoff*Tests",
            true),
        batch(
            "route-evidence",
            3,
            "grouped",
            "controller route evidence and shared route path tests",
            true),
        batch(
            "non-docker-regression",
            4,
            "build",
            "mvn -q test with Docker-dependent tests remaining guarded",
            true),
        batch(
            "read-only-smoke",
            5,
            "smoke",
            "no upstream autostart and no write routing smoke",
            false));
  }

  private static CiBatchPlan batch(
      String batch, int order, String commandFamily, String scope, boolean blocksNextBatch) {
    return new CiBatchPlan(batch, order, commandFamily, scope, true, blocksNextBatch);
  }

  private static List<BoundaryLock> locks() {
    return List.of(
        lock("no-java-autostart", "Node must not start Java"),
        lock("no-mini-kv-autostart", "Node must not start mini-kv"),
        lock("no-write-routing", "No write routing may be enabled"),
        lock("no-credential-value", "Credential values stay unread"),
        lock("no-raw-endpoint-url", "Raw endpoint URLs stay unresolved"),
        lock("no-managed-audit-http", "Managed audit HTTP/TCP stays disabled"),
        lock("no-runtime-shell", "Runtime shell remains disabled"),
        lock("no-mini-kv-write-admin", "mini-kv write/admin commands remain forbidden"));
  }

  private static BoundaryLock lock(String code, String behavior) {
    return new BoundaryLock(
        code, behavior, true, "operator-ci handoff is advisory read-only evidence");
  }

  private static List<ScorecardEntry> scorecard(
      OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse archive,
      List<OperatorLane> lanes,
      List<CiBatchPlan> batches,
      List<BoundaryLock> locks) {
    return List.of(
        score("source-archive-status", 1, "passed".equals(archive.status()) ? 1 : 0),
        score(
            "operator-lanes",
            OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistrySupport
                .EXPECTED_OPERATOR_LANE_COUNT,
            lanes.size()),
        score(
            "ci-batches",
            OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistrySupport
                .EXPECTED_CI_BATCH_COUNT,
            batches.size()),
        score(
            "boundary-locks",
            OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistrySupport
                .EXPECTED_BOUNDARY_LOCK_COUNT,
            locks.size()),
        score(
            "source-archive-scorecard",
            archive.scorecardEntryCount(),
            passedScorecardEntryCount(archive)));
  }

  private static int passedScorecardEntryCount(
      OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse archive) {
    return (int)
        archive.scorecard().stream().filter(score -> "passed".equals(score.status())).count();
  }

  private static ScorecardEntry score(String name, int expected, int actual) {
    return new ScorecardEntry(name, expected, actual, expected == actual ? "passed" : "blocked");
  }

  record Evidence(
      List<SourceArchiveSnapshot> sourceArchiveSnapshots,
      List<OperatorLane> operatorLanes,
      List<CiBatchPlan> ciBatches,
      List<BoundaryLock> boundaryLocks,
      List<ScorecardEntry> scorecard) {
    Evidence {
      sourceArchiveSnapshots = List.copyOf(sourceArchiveSnapshots);
      operatorLanes = List.copyOf(operatorLanes);
      ciBatches = List.copyOf(ciBatches);
      boundaryLocks = List.copyOf(boundaryLocks);
      scorecard = List.copyOf(scorecard);
    }
  }
}
