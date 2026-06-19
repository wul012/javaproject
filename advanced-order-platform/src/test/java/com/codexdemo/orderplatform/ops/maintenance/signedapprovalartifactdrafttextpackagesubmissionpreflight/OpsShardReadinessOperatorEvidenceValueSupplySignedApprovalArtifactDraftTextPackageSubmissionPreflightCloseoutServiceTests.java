package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackagesubmissionpreflight;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutServiceTests {

  @Test
  void closeoutServicesExposeEvidenceWithoutOpeningRuntimeOrAcceptance() {
    var catalog =
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutCatalogService()
            .catalog();
    var ledger =
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutHandoffLedgerService()
            .ledger();
    var slotComparison =
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutSlotComparisonService()
            .slotComparison();
    var guardrails =
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutGuardrailSummaryService()
            .guardrails();
    var routes =
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutRouteEvidenceService()
            .routeEvidence();

    assertThat(catalog.version()).isEqualTo("Java v979");
    assertThat(catalog.handoffItemCount()).isEqualTo(25);
    assertThat(catalog.guardrailCount()).isEqualTo(12);
    assertThat(catalog.routeEvidenceCount()).isEqualTo(11);
    assertThat(ledger.readyForSubmittedPackageAcceptance()).isFalse();
    assertThat(slotComparison.readyForDetachedSignatureParsing()).isFalse();
    assertThat(guardrails.readyForApprovalGrant()).isFalse();
    assertThat(routes.readyForRuntimePayload()).isFalse();
  }

  @Test
  void archiveRuntimeOperatorAndIntegrityServicesKeepCloseoutReadOnly() {
    var archive =
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutArchiveManifestService()
            .manifest();
    var runtime =
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutRuntimeBoundaryService()
            .runtimeBoundary();
    var operator =
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutOperatorHandoffService()
            .operatorHandoff();
    var integrity =
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutIntegritySummaryService()
            .summary();

    assertThat(archive.version()).isEqualTo("Java v984");
    assertThat(runtime.siblingMutationAllowed()).isFalse();
    assertThat(operator.handoffItemCount()).isEqualTo(25);
    assertThat(integrity.version()).isEqualTo("Java v987");
    assertThat(integrity.status()).isEqualTo("passed");
  }
}
