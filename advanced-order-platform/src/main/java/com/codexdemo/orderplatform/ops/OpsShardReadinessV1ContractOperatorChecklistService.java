package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessV1ContractOperatorChecklistService {

    public static final String ENDPOINT =
            "/api/v1/ops/shard-readiness/v1-contract-operator-checklist";

    public static final String FIXTURE_ENDPOINT =
            "/contracts/java-shard-readiness-v1-contract-operator-checklist-v196.fixture.json";

    public static final String EVIDENCE_PATH =
            "e/196/evidence/java-shard-readiness-v1-contract-operator-checklist-v196.json";

    static final String SNAPSHOT_FREEZE_EVIDENCE_PATH =
            "e/194/evidence/java-shard-readiness-v193-evidence-packet-snapshot-freeze-v194.json";

    static final String HISTORICAL_COMPATIBILITY_EVIDENCE_PATH =
            "e/195/evidence/java-shard-readiness-v193-evidence-packet-historical-snapshot-compatibility-v195.json";

    private final OpsShardReadinessV1ContractEvidencePacketService packetService;

    public OpsShardReadinessV1ContractOperatorChecklistService(
            OpsShardReadinessV1ContractEvidencePacketService packetService
    ) {
        this.packetService = packetService;
    }

    @Transactional(readOnly = true)
    public OpsShardReadinessV1ContractOperatorChecklistResponse checklist() {
        OpsShardReadinessV1ContractEvidencePacketResponse packet = packetService.packet();
        return new OpsShardReadinessV1ContractOperatorChecklistResponse(
                "advanced-order-platform",
                "Java v196",
                packet.contractName(),
                true,
                false,
                false,
                ENDPOINT,
                FIXTURE_ENDPOINT,
                packet.packetEndpoint(),
                packet.packetFixtureEndpoint(),
                packet.evidencePath(),
                SNAPSHOT_FREEZE_EVIDENCE_PATH,
                HISTORICAL_COMPATIBILITY_EVIDENCE_PATH,
                operatorChecklistItems(),
                requiredReadOnlyEvidence(packet),
                nodeResponsibilities(),
                javaResponsibilities(),
                packet.blockedOperations(),
                verificationChecks(packet),
                packet.minimalFieldsFrozen(),
                packet.historicalSnapshotsProtected(),
                packet.writeRoutingAllowed(),
                packet.activeShardRouterAllowed(),
                packet.credentialValueRead(),
                packet.rawEndpointParsed(),
                packet.managedAuditConnectionAllowed(),
                packet.deploymentOrRollbackAllowed(),
                packet.nodeMayStartOrStopJavaOrMiniKv(),
                "java-shard-readiness-v1-contract-operator-checklist-receipt-v196",
                EVIDENCE_PATH,
                "passed"
        );
    }

    private static List<String> operatorChecklistItems() {
        return List.of(
                "confirm-java-v193-packet-endpoint-is-readable",
                "confirm-java-v194-packet-snapshot-freeze-evidence-is-archived",
                "confirm-java-v195-historical-snapshot-compatibility-evidence-is-archived",
                "confirm-node-consumes-only-read-only-get-endpoints",
                "confirm-no-write-routing-or-active-shard-router-is-enabled",
                "confirm-no-credential-value-or-raw-endpoint-is-read",
                "confirm-no-java-or-mini-kv-process-control-is-delegated-to-node"
        );
    }

    private static List<String> requiredReadOnlyEvidence(
            OpsShardReadinessV1ContractEvidencePacketResponse packet
    ) {
        return List.of(
                packet.evidencePath(),
                SNAPSHOT_FREEZE_EVIDENCE_PATH,
                HISTORICAL_COMPATIBILITY_EVIDENCE_PATH,
                EVIDENCE_PATH
        );
    }

    private static List<String> nodeResponsibilities() {
        return List.of(
                "read-checklist-endpoint-with-get-only",
                "read-packet-endpoint-with-get-only",
                "persist-observed-receipts-outside-java",
                "avoid-java-or-mini-kv-process-start-stop",
                "leave-write-routing-and-active-shard-router-disabled"
        );
    }

    private static List<String> javaResponsibilities() {
        return List.of(
                "serve-checklist-as-read-only-json",
                "serve-static-fixture-for-contract-review",
                "keep-packet-and-historical-snapshot-references-stable",
                "keep-execution-allowed-false",
                "keep-shard-enabled-false"
        );
    }

    private static List<String> verificationChecks(OpsShardReadinessV1ContractEvidencePacketResponse packet) {
        return List.of(
                "contract-name:" + packet.contractName(),
                "packet-endpoint:" + packet.packetEndpoint(),
                "packet-evidence-path:" + packet.evidencePath(),
                "required-read-only-evidence-count:4",
                "operator-checklist-item-count:7",
                "node-responsibility-count:5",
                "java-responsibility-count:5",
                "packet-frozen:" + packet.minimalFieldsFrozen(),
                "historical-snapshots-protected:" + packet.historicalSnapshotsProtected(),
                "execution-allowed:false"
        );
    }
}
