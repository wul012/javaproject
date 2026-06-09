package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffPolicyLockReferenceTests {

    @Test
    void policyLocksPreserveValidatorRejectionCodes() {
        var sourcePrecheck = OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffTestSupport
                .sourcePrecheck();
        var policyLocks = OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffPolicyCatalog
                .policyLocks(sourcePrecheck);

        assertThat(policyLocks)
                .extracting(OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffResponse.PolicyLock::rejectionCode)
                .containsExactlyElementsOf(sourcePrecheck.validators().stream()
                        .map(OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckResponse.Validator::rejectionCode)
                        .toList());
        assertThat(policyLocks)
                .allSatisfy(lock -> assertThat(lock.lockReason()).contains("blocked", "reviewed"));
    }
}
