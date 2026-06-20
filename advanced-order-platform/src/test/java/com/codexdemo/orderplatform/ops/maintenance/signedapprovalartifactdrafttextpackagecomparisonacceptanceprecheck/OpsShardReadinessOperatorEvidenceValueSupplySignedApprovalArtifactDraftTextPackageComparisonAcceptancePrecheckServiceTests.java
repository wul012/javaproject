package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackagecomparisonacceptanceprecheck;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckServiceTests {

  @Test
  void acceptancePrecheckServicesExposeCheckpointSlicesWithoutOpeningAcceptance() {
    var catalog =
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckCatalogService()
            .catalog();
    var source =
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckSourceIdentityDigestService()
            .sourceIdentityDigest();
    var signature =
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckSignatureEvidenceValueService()
            .signatureEvidenceValue();
    var policy =
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckPolicyExecutionArchiveService()
            .policyExecutionArchive();

    assertThat(catalog.version()).isEqualTo("Java v1009");
    assertThat(catalog.checkpointCount()).isEqualTo(10);
    assertThat(catalog.guardCount()).isEqualTo(10);
    assertThat(source.checkpointCount()).isEqualTo(3);
    assertThat(signature.guardCount()).isEqualTo(3);
    assertThat(policy.checkpointCount()).isEqualTo(4);
    assertThat(policy.readyForComparedPackageAcceptance()).isFalse();
    assertThat(policy.readyForRuntimePayload()).isFalse();
  }
}
