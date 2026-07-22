package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoff;

import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution.OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse;
import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistrySupport {

  static final String PROJECT = "advanced-order-platform";
  static final String SOURCE_PLAN = "Node v367";
  static final String REQUIRED_ARCHIVE_VERIFICATION_PLAN = "Node v368";
  static final String RECOMMENDED_OPERATOR_PLAN = "Node v369";
  static final String HANDOFF_STATE = "minimal-read-only-gate-operator-ci-handoff-ready";
  static final int EXPECTED_SOURCE_ARCHIVE_SNAPSHOT_COUNT = 1;
  static final int EXPECTED_OPERATOR_LANE_COUNT = 4;
  static final int EXPECTED_CI_BATCH_COUNT = 5;
  static final int EXPECTED_BOUNDARY_LOCK_COUNT = 8;
  static final int EXPECTED_SCORECARD_ENTRY_COUNT = 5;

  private OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistrySupport() {}

  static OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse response(
      String version,
      String endpoint,
      String profile,
      OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse
          sourceArchive,
      HandoffCatalog.Evidence evidence,
      List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse.MarkdownSection>
          markdownSections) {
    var sourceArchiveSnapshotCopy = evidence.sourceArchiveSnapshots();
    var operatorLaneCopy = evidence.operatorLanes();
    var ciBatchCopy = evidence.ciBatches();
    var boundaryLockCopy = evidence.boundaryLocks();
    var scorecardCopy = evidence.scorecard();
    var markdownSectionCopy = List.copyOf(markdownSections);
    int readyOperatorLaneCount =
        (int)
            operatorLaneCopy.stream()
                .filter(
                    OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse
                            .OperatorLane
                        ::ready)
                .count();
    int passedCiBatchCount =
        (int)
            ciBatchCopy.stream()
                .filter(
                    OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse
                            .CiBatchPlan
                        ::passed)
                .count();
    int lockedBoundaryCount =
        (int)
            boundaryLockCopy.stream()
                .filter(
                    OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse
                            .BoundaryLock
                        ::locked)
                .count();
    int passedScorecardEntryCount =
        (int) scorecardCopy.stream().filter(score -> "passed".equals(score.status())).count();
    List<String> checks = new ArrayList<>();
    checks.add("minimal-read-only-gate-operator-ci-handoff-source-plan-" + SOURCE_PLAN);
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-required-archive-"
            + REQUIRED_ARCHIVE_VERIFICATION_PLAN);
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-recommended-plan-" + RECOMMENDED_OPERATOR_PLAN);
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-source-archive-version-"
            + sourceArchive.version());
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-source-archive-count-"
            + sourceArchiveSnapshotCopy.size());
    checks.add("minimal-read-only-gate-operator-ci-handoff-lane-count-" + operatorLaneCopy.size());
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-ready-lane-count-" + readyOperatorLaneCount);
    checks.add("minimal-read-only-gate-operator-ci-handoff-ci-batch-count-" + ciBatchCopy.size());
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-passed-ci-batch-count-" + passedCiBatchCount);
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-boundary-lock-count-"
            + boundaryLockCopy.size());
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-locked-boundary-count-" + lockedBoundaryCount);
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-scorecard-count-" + scorecardCopy.size());
    checks.add("minimal-read-only-gate-operator-ci-handoff-no-upstream-autostart");
    checks.add("minimal-read-only-gate-operator-ci-handoff-no-write-routing");
    checks.add("minimal-read-only-gate-operator-ci-handoff-no-secret-value");

    String status =
        readyOperatorLaneCount == operatorLaneCopy.size()
                && passedCiBatchCount == ciBatchCopy.size()
                && lockedBoundaryCount == boundaryLockCopy.size()
                && passedScorecardEntryCount == scorecardCopy.size()
            ? "passed"
            : "blocked";

    return new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse(
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
        REQUIRED_ARCHIVE_VERIFICATION_PLAN,
        RECOMMENDED_OPERATOR_PLAN,
        sourceArchive.version(),
        sourceArchive.endpoint(),
        HANDOFF_STATE,
        sourceArchiveSnapshotCopy.size(),
        operatorLaneCopy.size(),
        readyOperatorLaneCount,
        ciBatchCopy.size(),
        passedCiBatchCount,
        boundaryLockCopy.size(),
        lockedBoundaryCount,
        scorecardCopy.size(),
        passedScorecardEntryCount,
        sourceArchiveSnapshotCopy,
        operatorLaneCopy,
        ciBatchCopy,
        boundaryLockCopy,
        scorecardCopy,
        markdownSectionCopy,
        List.copyOf(checks),
        status);
  }
}
