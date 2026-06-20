package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackagecomparedpackageevidenceintake;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeSupportTests {

  @Test
  void buildsIntakeContractWithoutFabricatingOrAcceptingEvidence() {
    var slot =
        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeSupport
            .slot("slot", "v1322", "slot contract", "question", "guard", "source");
    var guard =
        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeSupport
            .guard("guard", "source", "guard text", "reject-missing-evidence");

    var response =
        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeSupport
            .response(
                "Java v1015",
                "/ops/shard-readiness/example",
                "compared-evidence-intake.example",
                List.of(slot),
                List.of(guard),
                List.of("extra-check"));

    assertThat(response.sourcePlan()).isEqualTo("Node v1331");
    assertThat(response.sourceJavaAcceptancePrecheckVersion()).isEqualTo("Java v1014");
    assertThat(response.readyForComparedEvidenceAcceptance()).isFalse();
    assertThat(response.readyForSignedDraftTextParsing()).isFalse();
    assertThat(response.readyForApprovalGrant()).isFalse();
    assertThat(response.readyForRuntimePayload()).isFalse();
    assertThat(response.siblingMutationAllowed()).isFalse();
    assertThat(response.status()).isEqualTo("passed");
  }
}
