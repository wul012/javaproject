package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessV1ContractConsumerEvidenceDigestService {

    public static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.V1_CONTRACT_CONSUMER_EVIDENCE_DIGEST;

    public static final String FIXTURE_ENDPOINT =
            "/contracts/java-shard-readiness-v1-contract-consumer-evidence-digest-v220.fixture.json";

    public static final String EVIDENCE_PATH =
            "e/220/evidence/java-shard-readiness-v1-contract-consumer-evidence-digest-v220.json";

    @Transactional(readOnly = true)
    public OpsShardReadinessV1ContractConsumerEvidenceDigestResponse digest() {
        OpsShardReadinessV1ContractConsumerVerificationChecklistResponse checklist =
                OpsShardReadinessV1ContractConsumerVerificationChecklistSnapshot.v215Checklist();
        return new OpsShardReadinessV1ContractConsumerEvidenceDigestResponse(
                "advanced-order-platform",
                "Java v220",
                checklist.contractName(),
                true,
                false,
                false,
                ENDPOINT,
                FIXTURE_ENDPOINT,
                checklist.verificationChecklistEndpoint(),
                checklist.verificationChecklistFixtureEndpoint(),
                checklist.evidencePath(),
                checklist.receiptId(),
                checklist.verificationItems().size(),
                checklist.requiredEvidence().size(),
                checklist.verificationChecks().size(),
                digestEvidence(checklist),
                digestChecks(checklist),
                checklist.blockedOperations(),
                checklist.probesAreGetOnly(),
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                "java-shard-readiness-v1-contract-consumer-evidence-digest-receipt-v220",
                EVIDENCE_PATH,
                "passed"
        );
    }

    private List<String> digestEvidence(
            OpsShardReadinessV1ContractConsumerVerificationChecklistResponse checklist
    ) {
        return List.of(
                checklist.evidencePath(),
                OpsShardReadinessV1ContractConsumerVerificationChecklistService
                        .CONSUMER_VERIFICATION_CHECKLIST_SNAPSHOT_FREEZE_EVIDENCE_PATH,
                OpsShardReadinessV1ContractConsumerVerificationChecklistService
                        .CONSUMER_VERIFICATION_CHECKLIST_HISTORICAL_COMPATIBILITY_EVIDENCE_PATH,
                OpsShardReadinessV1ContractConsumerVerificationChecklistService
                        .CONSUMER_VERIFICATION_CHECKLIST_INTEGRITY_EVIDENCE_PATH,
                OpsShardReadinessV1ContractConsumerVerificationChecklistService
                        .CONSUMER_VERIFICATION_CHECKLIST_ROUTE_INVENTORY_EVIDENCE_PATH
        );
    }

    private List<String> digestChecks(
            OpsShardReadinessV1ContractConsumerVerificationChecklistResponse checklist
    ) {
        return List.of(
                "checklist-version:" + checklist.version(),
                "checklist-items:" + checklist.verificationItems().size(),
                "checklist-required-evidence:" + checklist.requiredEvidence().size(),
                "checklist-verification-checks:" + checklist.verificationChecks().size(),
                "digest-evidence-count:" + digestEvidence(checklist).size(),
                "probes-are-get-only:" + checklist.probesAreGetOnly(),
                "node-may-start-or-stop-java-or-mini-kv:" + checklist.nodeMayStartOrStopJavaOrMiniKv()
        );
    }
}
