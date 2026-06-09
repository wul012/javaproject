package com.codexdemo.orderplatform.ops;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryService {

    static final String RESPONSE_VERSION = "Java v1377";
    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths
                    .MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_ARCHIVE_VERIFICATION_REGISTRY;
    static final String PROFILE =
            "java-shard-readiness-minimal-read-only-gate-operator-ci-handoff-archive-verification-registry.v1";

    private final OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryService sourceHandoffService;

    public OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryService(
            OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryService sourceHandoffService
    ) {
        this.sourceHandoffService = sourceHandoffService;
    }

    @Transactional(readOnly = true)
    public OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
            registry() {
        var sourceHandoff = sourceHandoffService.registry();
        var sourceHandoffs =
                OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveSourceHandoffCatalog
                        .snapshots(sourceHandoff);
        var artifacts =
                OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveArtifactVerificationCatalog
                        .artifactVerifications(sourceHandoff);
        var lanes =
                OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveLaneVerificationCatalog
                        .laneVerifications(sourceHandoff);
        var ciBatches =
                OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveCiBatchVerificationCatalog
                        .ciBatchVerifications(sourceHandoff);
        var boundaries =
                OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveBoundaryVerificationCatalog
                        .boundaryVerifications(sourceHandoff);
        var scorecard =
                OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveScorecardCatalog
                        .scorecard(sourceHandoff, artifacts, lanes, ciBatches, boundaries);
        return OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistrySupport
                .response(
                        RESPONSE_VERSION,
                        ENDPOINT,
                        PROFILE,
                        sourceHandoff,
                        sourceHandoffs,
                        artifacts,
                        lanes,
                        ciBatches,
                        boundaries,
                        scorecard,
                        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryRenderer
                                .render(
                                        sourceHandoffs,
                                        artifacts,
                                        lanes,
                                        ciBatches,
                                        boundaries,
                                        scorecard
                                )
                );
    }
}
