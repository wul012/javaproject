package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessV1ContractConsumerEvidenceDigestSnapshot {

    private OpsShardReadinessV1ContractConsumerEvidenceDigestSnapshot() {
    }

    static OpsShardReadinessV1ContractConsumerEvidenceDigestResponse v220Digest() {
        OpsShardReadinessV1ContractConsumerVerificationChecklistResponse checklist =
                OpsShardReadinessV1ContractConsumerVerificationChecklistSnapshot.v215Checklist();
        return new OpsShardReadinessV1ContractConsumerEvidenceDigestResponse(
                "advanced-order-platform",
                "Java v220",
                checklist.contractName(),
                true,
                false,
                false,
                OpsShardReadinessV1ContractConsumerEvidenceDigestService.ENDPOINT,
                OpsShardReadinessV1ContractConsumerEvidenceDigestService.FIXTURE_ENDPOINT,
                checklist.verificationChecklistEndpoint(),
                checklist.verificationChecklistFixtureEndpoint(),
                checklist.evidencePath(),
                checklist.receiptId(),
                checklist.verificationItems().size(),
                checklist.requiredEvidence().size(),
                checklist.verificationChecks().size(),
                v220DigestEvidence(checklist),
                v220DigestChecks(checklist),
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
                OpsShardReadinessV1ContractConsumerEvidenceDigestService.EVIDENCE_PATH,
                "passed"
        );
    }

    static List<String> v220DigestEvidence(
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

    static List<String> v220DigestChecks(
            OpsShardReadinessV1ContractConsumerVerificationChecklistResponse checklist
    ) {
        return List.of(
                "checklist-version:" + checklist.version(),
                "checklist-items:" + checklist.verificationItems().size(),
                "checklist-required-evidence:" + checklist.requiredEvidence().size(),
                "checklist-verification-checks:" + checklist.verificationChecks().size(),
                "digest-evidence-count:" + v220DigestEvidence(checklist).size(),
                "probes-are-get-only:" + checklist.probesAreGetOnly(),
                "node-may-start-or-stop-java-or-mini-kv:" + checklist.nodeMayStartOrStopJavaOrMiniKv()
        );
    }
}
