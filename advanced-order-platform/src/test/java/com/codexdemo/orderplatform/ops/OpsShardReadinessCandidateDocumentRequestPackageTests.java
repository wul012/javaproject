package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentRequestPackageResponse;
import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentRequestPackageService;
import org.junit.jupiter.api.Test;

class OpsShardReadinessCandidateDocumentRequestPackageTests {

  @Test
  void exposesReadOnlyCandidateDocumentRequestPackageWithoutImportingDocuments() {
    var response = new OpsShardReadinessCandidateDocumentRequestPackageService().packageCatalog();

    assertThat(response.version()).isEqualTo("Java v1081");
    assertThat(response.sourcePlan()).isEqualTo("Node v1386");
    assertThat(response.requestItemCount()).isEqualTo(15);
    assertThat(response.acceptanceCheckCount()).isEqualTo(15);
    assertThat(response.requestedCandidateFieldCount()).isEqualTo(20);
    assertThat(response.gateCount()).isEqualTo(38);
    assertThat(response.realDocumentCount()).isZero();
    assertThat(response.syntheticDocumentCount()).isZero();
    assertThat(response.importedDocumentCount()).isZero();
    assertThat(response.evaluatedDocumentCount()).isZero();
    assertThat(response.acceptedDocumentCount()).isZero();
    assertThat(response.rejectedDocumentCount()).isZero();
    assertThat(response.payloadCount()).isZero();
    assertThat(response.importAllowed()).isFalse();
    assertThat(response.evaluationAllowed()).isFalse();
    assertThat(response.approvalGrantAllowed()).isFalse();
    assertThat(response.runtimePayloadAllowed()).isFalse();
    assertThat(response.writeAllowed()).isFalse();
    assertThat(response.siblingMutationAllowed()).isFalse();
    assertThat(response.status()).isEqualTo("passed");
  }

  @Test
  void requestPackageRouteAndControllerStayReadOnly() {
    var controller =
        new OpsShardReadinessCandidateDocumentRequestPackageController(
            new OpsShardReadinessCandidateDocumentRequestPackageService());

    assertThat(
            OpsShardReadinessRoutePaths
                .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_CANDIDATE_DOCUMENT_REQUEST_PACKAGE)
        .endsWith("candidate-document-request-package");
    assertThat(controller.packageCatalog().readOnly()).isTrue();
    assertThat(controller.packageCatalog().requestItems())
        .extracting(OpsShardReadinessCandidateDocumentRequestPackageResponse.RequestItem::code)
        .doesNotHaveDuplicates()
        .contains("source-readiness-request", "approval-runtime-write-freeze-request");
  }
}
