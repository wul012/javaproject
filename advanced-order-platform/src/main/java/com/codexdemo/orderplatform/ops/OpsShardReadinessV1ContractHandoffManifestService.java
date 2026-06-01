package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessV1ContractHandoffManifestService {

    public static final String ENDPOINT =
            "/api/v1/ops/shard-readiness/v1-contract-handoff-manifest";

    public static final String FIXTURE_ENDPOINT =
            "/contracts/java-shard-readiness-v1-contract-handoff-manifest-v199.fixture.json";

    public static final String EVIDENCE_PATH =
            "e/199/evidence/java-shard-readiness-v1-contract-handoff-manifest-v199.json";

    private final OpsShardReadinessV1ContractOperatorChecklistService checklistService;

    public OpsShardReadinessV1ContractHandoffManifestService(
            OpsShardReadinessV1ContractOperatorChecklistService checklistService
    ) {
        this.checklistService = checklistService;
    }

    @Transactional(readOnly = true)
    public OpsShardReadinessV1ContractHandoffManifestResponse manifest() {
        OpsShardReadinessV1ContractOperatorChecklistResponse checklist = checklistService.checklist();
        return new OpsShardReadinessV1ContractHandoffManifestResponse(
                "advanced-order-platform",
                "Java v199",
                checklist.contractName(),
                true,
                false,
                false,
                ENDPOINT,
                FIXTURE_ENDPOINT,
                checklist.packetEndpoint(),
                checklist.packetFixtureEndpoint(),
                checklist.packetEvidencePath(),
                checklist.checklistEndpoint(),
                checklist.checklistFixtureEndpoint(),
                checklist.evidencePath(),
                prerequisiteEvidence(checklist),
                manifestSections(),
                consumerReadTargets(checklist),
                consumerFixtureTargets(checklist),
                operatorHandoffChecks(),
                checklist.blockedOperations(),
                verificationChecks(checklist),
                checklist.packetFrozen(),
                true,
                checklist.historicalSnapshotsProtected(),
                checklist.writeRoutingAllowed(),
                checklist.activeShardRouterAllowed(),
                checklist.credentialValueRead(),
                checklist.rawEndpointParsed(),
                checklist.managedAuditConnectionAllowed(),
                checklist.deploymentOrRollbackAllowed(),
                checklist.nodeMayStartOrStopJavaOrMiniKv(),
                "java-shard-readiness-v1-contract-handoff-manifest-receipt-v199",
                EVIDENCE_PATH,
                "passed"
        );
    }

    private static List<String> prerequisiteEvidence(
            OpsShardReadinessV1ContractOperatorChecklistResponse checklist
    ) {
        return List.of(
                checklist.packetEvidencePath(),
                checklist.packetSnapshotFreezeEvidencePath(),
                checklist.packetHistoricalCompatibilityEvidencePath(),
                checklist.evidencePath(),
                "e/197/evidence/java-shard-readiness-v196-operator-checklist-snapshot-freeze-v197.json",
                "e/198/evidence/java-shard-readiness-v196-operator-checklist-historical-snapshot-compatibility-v198.json",
                EVIDENCE_PATH
        );
    }

    private static List<String> manifestSections() {
        return List.of(
                "contract-summary",
                "read-only-endpoints",
                "fixture-endpoints",
                "required-evidence",
                "operator-handoff-checks",
                "blocked-operations"
        );
    }

    private static List<String> consumerReadTargets(
            OpsShardReadinessV1ContractOperatorChecklistResponse checklist
    ) {
        return List.of(
                OpsShardReadinessService.ENDPOINT,
                checklist.packetEndpoint(),
                checklist.checklistEndpoint(),
                ENDPOINT
        );
    }

    private static List<String> consumerFixtureTargets(
            OpsShardReadinessV1ContractOperatorChecklistResponse checklist
    ) {
        return List.of(
                OpsShardReadinessService.FIXTURE_ENDPOINT,
                checklist.packetFixtureEndpoint(),
                checklist.checklistFixtureEndpoint(),
                FIXTURE_ENDPOINT
        );
    }

    private static List<String> operatorHandoffChecks() {
        return List.of(
                "read-manifest-with-get-only",
                "verify-packet-and-checklist-receipts-are-present",
                "verify-snapshot-freeze-evidence-before-consuming-current-registry",
                "verify-historical-compatibility-evidence-before-adding-new-consumers",
                "keep-upstream-actions-disabled"
        );
    }

    private static List<String> verificationChecks(
            OpsShardReadinessV1ContractOperatorChecklistResponse checklist
    ) {
        return List.of(
                "contract-name:" + checklist.contractName(),
                "packet-endpoint:" + checklist.packetEndpoint(),
                "checklist-endpoint:" + checklist.checklistEndpoint(),
                "manifest-section-count:6",
                "consumer-read-target-count:4",
                "consumer-fixture-target-count:4",
                "operator-handoff-check-count:5",
                "prerequisite-evidence-count:7",
                "execution-allowed:false"
        );
    }
}
