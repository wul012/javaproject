package com.codexdemo.orderplatform.ops.maintenance.candidatedocument;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessCandidateDocumentProfileSectionRegistryServiceTests {

  @Test
  void buildsReadOnlyProfileSectionRegistryFromFiveRoutes() {
    var response = OpsShardReadinessCandidateDocumentProfileSectionRegistryTestSupport.registry();

    assertThat(response.project()).isEqualTo("advanced-order-platform");
    assertThat(response.version()).isEqualTo("Java v1212");
    assertThat(response.readOnly()).isTrue();
    assertThat(response.executionAllowed()).isFalse();
    assertThat(response.readyForProfileSectionRegistry()).isTrue();
    assertThat(response.sourcePlan()).isEqualTo("Node v1481");
    assertThat(response.sourceNodeProfileRendererVersion()).isEqualTo("Node v1481");
    assertThat(response.moduleCount()).isEqualTo(5);
    assertThat(response.sourceRouteCount()).isEqualTo(5);
    assertThat(response.sectionCount()).isEqualTo(5);
    assertThat(response.renderedSectionCount()).isEqualTo(5);
    assertThat(response.fieldEntryCount()).isEqualTo(25);
    assertThat(response.routeFieldLockCount()).isEqualTo(5);
    assertThat(response.lockedRouteFieldCount()).isEqualTo(15);
    assertThat(response.gateCount()).isEqualTo(43);
    assertThat(response.status()).isEqualTo("passed");
  }

  @Test
  void keepsRendererSplitRuntimeAndMutationPathsClosed() {
    var response = OpsShardReadinessCandidateDocumentProfileSectionRegistryTestSupport.registry();

    assertThat(response.realDocumentCount()).isZero();
    assertThat(response.syntheticDocumentCount()).isZero();
    assertThat(response.stagedDocumentCount()).isZero();
    assertThat(response.importedDocumentCount()).isZero();
    assertThat(response.evaluatedDocumentCount()).isZero();
    assertThat(response.acceptedDocumentCount()).isZero();
    assertThat(response.rejectedDocumentCount()).isZero();
    assertThat(response.payloadCount()).isZero();
    assertThat(response.materialSubmissionAccepted()).isFalse();
    assertThat(response.importAllowed()).isFalse();
    assertThat(response.evaluationAllowed()).isFalse();
    assertThat(response.approvalGrantAllowed()).isFalse();
    assertThat(response.signedApprovalCaptureAllowed()).isFalse();
    assertThat(response.runtimePayloadAllowed()).isFalse();
    assertThat(response.writeAllowed()).isFalse();
    assertThat(response.siblingMutationAllowed()).isFalse();
  }
}
