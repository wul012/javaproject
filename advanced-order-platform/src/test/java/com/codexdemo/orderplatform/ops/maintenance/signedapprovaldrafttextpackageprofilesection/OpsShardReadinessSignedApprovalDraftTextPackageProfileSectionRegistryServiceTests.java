package com.codexdemo.orderplatform.ops.maintenance.signedapprovaldrafttextpackageprofilesection;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryServiceTests {

  @Test
  void buildsReadOnlyTextPackageProfileSectionRegistryFromNineRoutes() {
    var response =
        OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryTestSupport.registry();

    assertThat(response.project()).isEqualTo("advanced-order-platform");
    assertThat(response.version()).isEqualTo("Java v1287");
    assertThat(response.readOnly()).isTrue();
    assertThat(response.executionAllowed()).isFalse();
    assertThat(response.readyForTextPackageProfileSectionRegistry()).isTrue();
    assertThat(response.sourcePlan()).isEqualTo("Node v1531");
    assertThat(response.sourceNodeTextPackageRendererVersion()).isEqualTo("Node v1531");
    assertThat(response.moduleCount()).isEqualTo(10);
    assertThat(response.sourceRouteCount()).isEqualTo(9);
    assertThat(response.sectionCount()).isEqualTo(9);
    assertThat(response.submissionRendererSectionCount()).isEqualTo(5);
    assertThat(response.comparedEvidenceRendererSectionCount()).isEqualTo(4);
    assertThat(response.fieldEntryCount()).isEqualTo(63);
    assertThat(response.status()).isEqualTo("passed");
  }

  @Test
  void keepsPackageApprovalRuntimeAndSecretValuePathsClosed() {
    var response =
        OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryTestSupport.registry();

    assertThat(response.packageAcceptedCount()).isZero();
    assertThat(response.signedApprovalCount()).isZero();
    assertThat(response.runtimePayloadCount()).isZero();
    assertThat(response.secretValueCount()).isZero();
    assertThat(response.writeOperationCount()).isZero();
    assertThat(response.packageAcceptanceAllowed()).isFalse();
    assertThat(response.signedApprovalCaptureAllowed()).isFalse();
    assertThat(response.approvalGrantAllowed()).isFalse();
    assertThat(response.valueImportAllowed()).isFalse();
    assertThat(response.runtimePayloadAllowed()).isFalse();
    assertThat(response.secretValueAllowed()).isFalse();
    assertThat(response.writeAllowed()).isFalse();
    assertThat(response.siblingMutationAllowed()).isFalse();
  }
}
