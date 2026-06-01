package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerEvidenceDigestSnapshotTests {

    @Test
    void freezesV220ConsumerEvidenceDigestSnapshot() {
        OpsShardReadinessV1ContractConsumerVerificationChecklistResponse checklist =
                OpsShardReadinessV1ContractConsumerVerificationChecklistSnapshot.v215Checklist();
        OpsShardReadinessV1ContractConsumerEvidenceDigestResponse digest =
                OpsShardReadinessV1ContractConsumerEvidenceDigestSnapshot.v220Digest();

        assertThat(digest.version()).isEqualTo("Java v220");
        assertThat(digest.verificationChecklistEndpoint()).isEqualTo(checklist.verificationChecklistEndpoint());
        assertThat(digest.verificationChecklistReceiptId()).isEqualTo(checklist.receiptId());
        assertThat(digest.digestEvidence())
                .containsExactlyElementsOf(
                        OpsShardReadinessV1ContractConsumerEvidenceDigestSnapshot.v220DigestEvidence(checklist)
                );
        assertThat(digest.digestChecks())
                .containsExactlyElementsOf(
                        OpsShardReadinessV1ContractConsumerEvidenceDigestSnapshot.v220DigestChecks(checklist)
                );
        assertThat(digest.evidencePath())
                .isEqualTo(OpsShardReadinessV1ContractConsumerEvidenceDigestService.EVIDENCE_PATH);
    }

    @Test
    void serviceReturnsTheFrozenV220Digest() {
        OpsShardReadinessV1ContractConsumerEvidenceDigestResponse serviceResponse =
                new OpsShardReadinessV1ContractConsumerEvidenceDigestService().digest();

        assertThat(serviceResponse)
                .isEqualTo(OpsShardReadinessV1ContractConsumerEvidenceDigestSnapshot.v220Digest());
    }
}
