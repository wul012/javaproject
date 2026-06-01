package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessV1ContractAlignmentHandoffService {

    static final String ENDPOINT = "/api/v1/ops/shard-readiness/v1-contract-alignment-handoff";
    static final String FIXTURE_ENDPOINT =
            "/contracts/java-shard-readiness-v1-contract-alignment-handoff-v190.fixture.json";
    static final String EVIDENCE_PATH =
            "e/190/evidence/java-shard-readiness-v1-contract-alignment-handoff-v190.json";
    static final String SNAPSHOT_FREEZE_EVIDENCE_PATH =
            "e/188/evidence/java-shard-readiness-v1-contract-alignment-snapshot-freeze-v188.json";
    static final String HISTORICAL_COMPATIBILITY_EVIDENCE_PATH =
            "e/189/evidence/java-shard-readiness-v187-historical-snapshot-compatibility-v189.json";

    private final OpsShardReadinessV1ContractAlignmentService alignmentService;

    public OpsShardReadinessV1ContractAlignmentHandoffService(
            OpsShardReadinessV1ContractAlignmentService alignmentService
    ) {
        this.alignmentService = alignmentService;
    }

    @Transactional(readOnly = true)
    public OpsShardReadinessV1ContractAlignmentHandoffResponse handoff() {
        OpsShardReadinessV1ContractAlignmentResponse alignment = alignmentService.alignment();
        List<String> currentLiveEndpoints = OpsShardReadinessEvidenceEndpoints.liveEndpoints();
        List<String> currentFixtureEndpoints = OpsShardReadinessEvidenceEndpoints.fixtureEndpoints();
        List<String> v179LiveEndpoints =
                OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationSnapshot.v179LiveEndpoints();
        List<String> v179FixtureEndpoints =
                OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationSnapshot.v179FixtureEndpoints();
        List<String> v184LiveEndpoints =
                OpsShardReadinessReadOnlyEndpointRegistryIntegritySnapshot.v184LiveEndpoints();
        List<String> v184FixtureEndpoints =
                OpsShardReadinessReadOnlyEndpointRegistryIntegritySnapshot.v184FixtureEndpoints();

        boolean registryContainsAlignment =
                currentLiveEndpoints.contains(OpsShardReadinessV1ContractAlignmentService.ENDPOINT)
                        && currentFixtureEndpoints.contains(OpsShardReadinessV1ContractAlignmentService.FIXTURE_ENDPOINT);
        boolean olderSnapshotsRemainUnbackfilled =
                !v179LiveEndpoints.contains(OpsShardReadinessV1ContractAlignmentService.ENDPOINT)
                        && !v184LiveEndpoints.contains(OpsShardReadinessV1ContractAlignmentService.ENDPOINT)
                        && !v179FixtureEndpoints.contains(OpsShardReadinessV1ContractAlignmentService.FIXTURE_ENDPOINT)
                        && !v184FixtureEndpoints.contains(OpsShardReadinessV1ContractAlignmentService.FIXTURE_ENDPOINT);
        boolean historicalSnapshotsProtected = registryContainsAlignment && olderSnapshotsRemainUnbackfilled;

        return new OpsShardReadinessV1ContractAlignmentHandoffResponse(
                "advanced-order-platform",
                "Java v190",
                alignment.contractName(),
                true,
                false,
                false,
                alignment.version(),
                OpsShardReadinessV1ContractAlignmentService.ENDPOINT,
                OpsShardReadinessV1ContractAlignmentService.FIXTURE_ENDPOINT,
                alignment.evidencePath(),
                alignment.receiptId(),
                "Java v188",
                SNAPSHOT_FREEZE_EVIDENCE_PATH,
                "Java v189",
                HISTORICAL_COMPATIBILITY_EVIDENCE_PATH,
                alignment.minimalFieldsFrozen(),
                historicalSnapshotsProtected,
                registryContainsAlignment,
                olderSnapshotsRemainUnbackfilled,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                handoffArtifacts(),
                verificationChecks(
                        alignment,
                        registryContainsAlignment,
                        olderSnapshotsRemainUnbackfilled,
                        historicalSnapshotsProtected
                ),
                "java-shard-readiness-v1-contract-alignment-handoff-receipt-v190",
                EVIDENCE_PATH,
                historicalSnapshotsProtected ? "passed" : "blocked"
        );
    }

    private List<String> handoffArtifacts() {
        return List.of(
                OpsShardReadinessV1ContractAlignmentService.ENDPOINT,
                OpsShardReadinessV1ContractAlignmentService.FIXTURE_ENDPOINT,
                OpsShardReadinessV1ContractAlignmentService.EVIDENCE_PATH,
                SNAPSHOT_FREEZE_EVIDENCE_PATH,
                HISTORICAL_COMPATIBILITY_EVIDENCE_PATH
        );
    }

    private List<String> verificationChecks(
            OpsShardReadinessV1ContractAlignmentResponse alignment,
            boolean registryContainsAlignment,
            boolean olderSnapshotsRemainUnbackfilled,
            boolean historicalSnapshotsProtected
    ) {
        return List.of(
                "contract-name:" + alignment.contractName(),
                "alignment-version:" + alignment.version(),
                "minimal-fields-frozen:" + alignment.minimalFieldsFrozen(),
                "registry-contains-alignment:" + registryContainsAlignment,
                "older-snapshots-remain-unbackfilled:" + olderSnapshotsRemainUnbackfilled,
                "historical-snapshots-protected:" + historicalSnapshotsProtected,
                "execution-allowed:false"
        );
    }
}
