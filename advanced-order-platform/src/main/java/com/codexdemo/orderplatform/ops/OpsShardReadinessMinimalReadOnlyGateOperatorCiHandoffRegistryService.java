package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryService {

    static final String RESPONSE_VERSION = "Java v1352";
    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_REGISTRY;
    static final String PROFILE =
            "java-shard-readiness-minimal-read-only-gate-operator-ci-handoff-registry.v1";

    private final OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryService
            sourceArchiveService;

    public OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryService(
            OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryService sourceArchiveService
    ) {
        this.sourceArchiveService = sourceArchiveService;
    }

    @Transactional(readOnly = true)
    public OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse registry() {
        var sourceArchive = sourceArchiveService.registry();
        var sourceArchives =
                OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffSourceArchiveCatalog
                        .snapshots(sourceArchive);
        var lanes = OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffLaneCatalog
                .lanes(sourceArchive);
        var ciBatches = OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffBatchCatalog
                .batches();
        var boundaryLocks = OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffBoundaryLockCatalog
                .locks();
        var scorecard = scorecard(sourceArchive, lanes, ciBatches, boundaryLocks);
        return OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistrySupport.response(
                RESPONSE_VERSION,
                ENDPOINT,
                PROFILE,
                sourceArchive,
                sourceArchives,
                lanes,
                ciBatches,
                boundaryLocks,
                scorecard,
                OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryRenderer.render(
                        sourceArchives,
                        lanes,
                        ciBatches,
                        boundaryLocks,
                        scorecard
                )
        );
    }

    private List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse
            .ScorecardEntry> scorecard(
                    OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse sourceArchive,
                    List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse.OperatorLane>
                            lanes,
                    List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse.CiBatchPlan>
                            ciBatches,
                    List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse.BoundaryLock>
                            boundaryLocks
            ) {
        return List.of(
                score("source-archive-status", 1, "passed".equals(sourceArchive.status()) ? 1 : 0),
                score("operator-lanes",
                        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistrySupport
                                .EXPECTED_OPERATOR_LANE_COUNT,
                        lanes.size()),
                score("ci-batches",
                        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistrySupport
                                .EXPECTED_CI_BATCH_COUNT,
                        ciBatches.size()),
                score("boundary-locks",
                        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistrySupport
                                .EXPECTED_BOUNDARY_LOCK_COUNT,
                        boundaryLocks.size()),
                score("source-archive-scorecard", sourceArchive.scorecardEntryCount(),
                        passedSourceArchiveScorecardEntryCount(sourceArchive))
        );
    }

    private int passedSourceArchiveScorecardEntryCount(
            OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse sourceArchive
    ) {
        return (int) sourceArchive.scorecard().stream()
                .filter(score -> "passed".equals(score.status()))
                .count();
    }

    private OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse.ScorecardEntry
            score(String name, int expected, int actual) {
        return new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse
                .ScorecardEntry(
                        name,
                        expected,
                        actual,
                        expected == actual ? "passed" : "blocked"
                );
    }
}
