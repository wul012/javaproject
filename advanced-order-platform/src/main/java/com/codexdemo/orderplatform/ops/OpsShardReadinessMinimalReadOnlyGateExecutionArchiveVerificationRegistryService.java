package com.codexdemo.orderplatform.ops;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryService {

    static final String RESPONSE_VERSION = "Java v1337";
    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths
                    .MINIMAL_READ_ONLY_GATE_EXECUTION_ARCHIVE_VERIFICATION_REGISTRY;
    static final String PROFILE =
            "java-shard-readiness-minimal-read-only-gate-execution-archive-verification-registry.v1";

    private final OpsShardReadinessMinimalReadOnlyGateExecutionRegistryService sourceRegistryService;

    public OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryService(
            OpsShardReadinessMinimalReadOnlyGateExecutionRegistryService sourceRegistryService
    ) {
        this.sourceRegistryService = sourceRegistryService;
    }

    @Transactional(readOnly = true)
    public OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse registry() {
        var sourceRegistry = sourceRegistryService.registry();
        var sourceRegistrySnapshots =
                OpsShardReadinessMinimalReadOnlyGateExecutionArchiveSourceRegistrySnapshotCatalog
                        .snapshots(sourceRegistry);
        var artifactVerifications =
                OpsShardReadinessMinimalReadOnlyGateExecutionArtifactVerificationCatalog
                        .artifactVerifications(sourceRegistry);
        var readTargetVerifications =
                OpsShardReadinessMinimalReadOnlyGateExecutionReadTargetVerificationCatalog
                        .readTargetVerifications(sourceRegistry);
        var gateCheckVerifications =
                OpsShardReadinessMinimalReadOnlyGateExecutionGateCheckVerificationCatalog
                        .gateCheckVerifications(sourceRegistry);
        var boundaryVerifications =
                OpsShardReadinessMinimalReadOnlyGateExecutionBoundaryVerificationCatalog
                        .boundaryVerifications(sourceRegistry);
        var ciBatchVerifications =
                OpsShardReadinessMinimalReadOnlyGateExecutionCiBatchVerificationCatalog
                        .ciBatchVerifications(sourceRegistry);
        var operatorHandoffVerifications =
                OpsShardReadinessMinimalReadOnlyGateExecutionOperatorHandoffVerificationCatalog
                        .operatorHandoffVerifications(sourceRegistry);
        var scorecard = OpsShardReadinessMinimalReadOnlyGateExecutionArchiveScorecardCatalog
                .scorecard(sourceRegistry);
        return OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistrySupport.response(
                RESPONSE_VERSION,
                ENDPOINT,
                PROFILE,
                sourceRegistry,
                sourceRegistrySnapshots,
                artifactVerifications,
                readTargetVerifications,
                gateCheckVerifications,
                boundaryVerifications,
                ciBatchVerifications,
                operatorHandoffVerifications,
                scorecard,
                OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryRenderer.render(
                        sourceRegistrySnapshots,
                        artifactVerifications,
                        readTargetVerifications,
                        gateCheckVerifications,
                        boundaryVerifications,
                        ciBatchVerifications,
                        operatorHandoffVerifications,
                        scorecard
                )
        );
    }
}
