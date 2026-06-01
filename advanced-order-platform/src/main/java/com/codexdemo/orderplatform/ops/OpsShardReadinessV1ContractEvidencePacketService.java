package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessV1ContractEvidencePacketService {

    static final String ENDPOINT = "/api/v1/ops/shard-readiness/v1-contract-evidence-packet";
    static final String FIXTURE_ENDPOINT =
            "/contracts/java-shard-readiness-v1-contract-evidence-packet-v193.fixture.json";
    static final String EVIDENCE_PATH =
            "e/193/evidence/java-shard-readiness-v1-contract-evidence-packet-v193.json";

    private final OpsShardReadinessV1ContractAlignmentHandoffService handoffService;

    public OpsShardReadinessV1ContractEvidencePacketService(
            OpsShardReadinessV1ContractAlignmentHandoffService handoffService
    ) {
        this.handoffService = handoffService;
    }

    @Transactional(readOnly = true)
    public OpsShardReadinessV1ContractEvidencePacketResponse packet() {
        OpsShardReadinessV1ContractAlignmentHandoffResponse handoff = handoffService.handoff();

        return new OpsShardReadinessV1ContractEvidencePacketResponse(
                "advanced-order-platform",
                "Java v193",
                handoff.contractName(),
                true,
                false,
                false,
                ENDPOINT,
                FIXTURE_ENDPOINT,
                OpsShardReadinessV1ContractAlignmentSnapshot.v187SourceEndpoint(),
                OpsShardReadinessV1ContractAlignmentSnapshot.v187SourceFixtureEndpoint(),
                OpsShardReadinessV1ContractAlignmentService.ENDPOINT,
                OpsShardReadinessV1ContractAlignmentService.FIXTURE_ENDPOINT,
                OpsShardReadinessV1ContractAlignmentHandoffService.ENDPOINT,
                OpsShardReadinessV1ContractAlignmentHandoffService.FIXTURE_ENDPOINT,
                handoff.snapshotFreezeEvidencePath(),
                handoff.historicalCompatibilityEvidencePath(),
                evidenceChain(handoff),
                nodeConsumableEndpoints(),
                nodeConsumableFixtureEndpoints(),
                blockedOperations(),
                verificationChecks(handoff),
                handoff.minimalFieldsFrozen(),
                handoff.historicalSnapshotsProtected(),
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                "java-shard-readiness-v1-contract-evidence-packet-receipt-v193",
                EVIDENCE_PATH,
                handoff.historicalSnapshotsProtected() ? "passed" : "blocked"
        );
    }

    private List<String> evidenceChain(OpsShardReadinessV1ContractAlignmentHandoffResponse handoff) {
        return List.of(
                OpsShardReadinessV1ContractAlignmentService.EVIDENCE_PATH,
                handoff.snapshotFreezeEvidencePath(),
                handoff.historicalCompatibilityEvidencePath(),
                handoff.evidencePath(),
                "e/191/evidence/java-shard-readiness-v190-handoff-snapshot-freeze-v191.json",
                "e/192/evidence/java-shard-readiness-v190-handoff-historical-snapshot-compatibility-v192.json"
        );
    }

    private List<String> nodeConsumableEndpoints() {
        return List.of(
                OpsShardReadinessService.ENDPOINT,
                OpsShardReadinessV1ContractAlignmentService.ENDPOINT,
                OpsShardReadinessV1ContractAlignmentHandoffService.ENDPOINT,
                ENDPOINT
        );
    }

    private List<String> nodeConsumableFixtureEndpoints() {
        return List.of(
                OpsShardReadinessService.FIXTURE_ENDPOINT,
                OpsShardReadinessV1ContractAlignmentService.FIXTURE_ENDPOINT,
                OpsShardReadinessV1ContractAlignmentHandoffService.FIXTURE_ENDPOINT,
                FIXTURE_ENDPOINT
        );
    }

    private List<String> blockedOperations() {
        return List.of(
                "write-routing",
                "active-shard-router",
                "credential-value-read",
                "raw-endpoint-parse",
                "managed-audit-connection",
                "deployment-or-rollback",
                "node-start-or-stop-java-or-mini-kv"
        );
    }

    private List<String> verificationChecks(OpsShardReadinessV1ContractAlignmentHandoffResponse handoff) {
        return List.of(
                "contract-name:" + handoff.contractName(),
                "source-readiness-endpoint:" + OpsShardReadinessV1ContractAlignmentSnapshot.v187SourceEndpoint(),
                "alignment-receipt:" + handoff.alignmentReceiptId(),
                "handoff-receipt:" + handoff.receiptId(),
                "evidence-chain-count:6",
                "node-consumable-endpoint-count:4",
                "minimal-fields-frozen:" + handoff.minimalFieldsFrozen(),
                "historical-snapshots-protected:" + handoff.historicalSnapshotsProtected(),
                "execution-allowed:false"
        );
    }
}
