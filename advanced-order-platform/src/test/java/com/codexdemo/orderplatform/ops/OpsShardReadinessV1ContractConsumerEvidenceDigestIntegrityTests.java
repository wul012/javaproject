package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerEvidenceDigestIntegrityTests {

    @Test
    void keepsDigestAlignedWithChecklistSnapshotAndV1Registry() {
        OpsShardReadinessV1ContractConsumerVerificationChecklistResponse checklist =
                OpsShardReadinessV1ContractConsumerVerificationChecklistSnapshot.v215Checklist();
        OpsShardReadinessV1ContractConsumerEvidenceDigestResponse digest =
                OpsShardReadinessV1ContractConsumerEvidenceDigestSnapshot.v220Digest();

        assertThat(OpsShardReadinessV1ContractEndpointPairs.endpointPairs()).hasSize(11);
        assertThat(OpsShardReadinessV1ContractEndpointPairs.liveEndpoints())
                .doesNotHaveDuplicates()
                .containsSubsequence(
                        OpsShardReadinessV1ContractConsumerHandoffBundleService.ENDPOINT,
                        OpsShardReadinessV1ContractConsumerVerificationChecklistService.ENDPOINT,
                        OpsShardReadinessV1ContractConsumerEvidenceDigestService.ENDPOINT,
                        OpsShardReadinessV1ContractConsumerReadinessHandoffService.ENDPOINT
                )
                .doesNotContain(
                        OpsShardReadinessReadOnlyEvidenceCatalogService.ENDPOINT,
                        OpsShardReadinessRuntimeExecutionPassEvidenceCloseoutService.ENDPOINT
                );

        assertThat(digest.verificationChecklistEndpoint()).isEqualTo(checklist.verificationChecklistEndpoint());
        assertThat(digest.verificationChecklistFixtureEndpoint()).isEqualTo(checklist.verificationChecklistFixtureEndpoint());
        assertThat(digest.verificationChecklistEvidencePath()).isEqualTo(checklist.evidencePath());
        assertThat(digest.verificationChecklistReceiptId()).isEqualTo(checklist.receiptId());
        assertThat(digest.checklistItemCount()).isEqualTo(checklist.verificationItems().size());
        assertThat(digest.requiredEvidenceCount()).isEqualTo(checklist.requiredEvidence().size());
        assertThat(digest.verificationCheckCount()).isEqualTo(checklist.verificationChecks().size());
        assertThat(digest.blockedOperations()).containsExactlyElementsOf(checklist.blockedOperations());
        assertThat(digest.digestEvidence())
                .containsExactlyElementsOf(
                        OpsShardReadinessV1ContractConsumerEvidenceDigestSnapshot.v220DigestEvidence(checklist)
                )
                .doesNotContain(
                        OpsShardReadinessV1ContractConsumerEvidenceDigestService.EVIDENCE_PATH,
                        OpsShardReadinessV1ContractConsumerEvidenceDigestService
                                .CONSUMER_EVIDENCE_DIGEST_SNAPSHOT_FREEZE_EVIDENCE_PATH,
                        OpsShardReadinessV1ContractConsumerEvidenceDigestService
                                .CONSUMER_EVIDENCE_DIGEST_HISTORICAL_COMPATIBILITY_EVIDENCE_PATH,
                        OpsShardReadinessV1ContractConsumerEvidenceDigestService
                                .CONSUMER_EVIDENCE_DIGEST_INTEGRITY_EVIDENCE_PATH
                );
        assertThat(digest.digestChecks())
                .containsExactlyElementsOf(
                        OpsShardReadinessV1ContractConsumerEvidenceDigestSnapshot.v220DigestChecks(checklist)
                );
    }
}
