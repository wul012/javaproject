package com.codexdemo.orderplatform.ops.maintenance.candidatedocument;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class PrecheckPolicyLockTests {

  @Test
  void policyLocksPreserveValidatorRejectionCodes() {
    var sourcePrecheck =
        OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffTestSupport
            .sourcePrecheck();
    var policyLocks = PrecheckHandoffCatalog.from(sourcePrecheck).policyLocks();

    assertThat(policyLocks)
        .extracting(
            OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffResponse.PolicyLock
                ::rejectionCode)
        .containsExactlyElementsOf(
            sourcePrecheck.validators().stream()
                .map(
                    OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckResponse.Validator
                        ::rejectionCode)
                .toList());
    assertThat(policyLocks)
        .allSatisfy(lock -> assertThat(lock.lockReason()).contains("blocked", "reviewed"));
  }
}
