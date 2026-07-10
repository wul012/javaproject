package com.codexdemo.orderplatform.ops;

import java.util.ArrayList;
import java.util.List;

final class ReleaseApprovalVerificationHintBuilder {

  ReleaseApprovalRehearsalResponseRecords.RehearsalVerificationHint build(
      ReleaseApprovalVerificationHintContext context) {
    List<ReleaseApprovalVerificationHintContribution> verificationContributions =
        ReleaseApprovalVerificationHintContributionCatalog.build(context.receiptChain());
    return new ReleaseApprovalRehearsalResponseRecords.RehearsalVerificationHint(
        OpsEvidenceService.RELEASE_APPROVAL_REHEARSAL_VERIFICATION_HINT_VERSION,
        OpsEvidenceService.RELEASE_APPROVAL_REHEARSAL_RESPONSE_SCHEMA_VERSION,
        new ReleaseApprovalVerificationWarningDigestBuilder(context.receiptChain()).build(context),
        "NO_LEDGER_WRITE_PROOF_BY_RESPONSE_FIELDS",
        new ReleaseApprovalNoLedgerWriteProofEvaluator().evaluate(context),
        false,
        ReleaseApprovalVerificationHintCatalog.schemaFields(),
        warningDigestInputs(verificationContributions),
        proofClaims(verificationContributions),
        nodeVerificationActions(verificationContributions));
  }

  private List<String> warningDigestInputs(
      List<ReleaseApprovalVerificationHintContribution> verificationContributions) {
    List<String> inputs =
        new ArrayList<>(ReleaseApprovalVerificationHintCatalog.warningDigestWarningInputNames());
    verificationContributions.forEach(
        contribution -> inputs.addAll(contribution.warningDigestWarningInputValues()));
    inputs.addAll(ReleaseApprovalVerificationHintCatalog.warningDigestBoundaryInputNames());
    verificationContributions.forEach(
        contribution -> inputs.addAll(contribution.warningDigestBoundaryInputValues()));
    inputs.add(ReleaseApprovalVerificationHintCatalog.finalWarningDigestBoundaryInputName());
    return inputs;
  }

  private List<String> proofClaims(
      List<ReleaseApprovalVerificationHintContribution> verificationContributions) {
    List<String> claims = new ArrayList<>(ReleaseApprovalVerificationHintCatalog.proofClaims());
    verificationContributions.forEach(
        contribution -> claims.addAll(contribution.proofClaimValues()));
    claims.addAll(ReleaseApprovalVerificationHintCatalog.closingProofClaims());
    return claims;
  }

  private List<String> nodeVerificationActions(
      List<ReleaseApprovalVerificationHintContribution> verificationContributions) {
    List<String> actions =
        new ArrayList<>(ReleaseApprovalVerificationHintCatalog.nodeVerificationActions());
    verificationContributions.forEach(
        contribution -> actions.addAll(contribution.nodeVerificationActionValues()));
    actions.addAll(ReleaseApprovalVerificationHintCatalog.closingNodeVerificationActions());
    return actions;
  }
}

record ReleaseApprovalVerificationHintContext(
    ReleaseApprovalRehearsalResponseRecords.RehearsalRequestContext requestContext,
    ReleaseApprovalRehearsalResponseRecords.RehearsalOperatorWindowHint operatorWindowHint,
    ReleaseApprovalRehearsalResponseRecords.RehearsalCiEvidenceHint ciEvidenceHint,
    ReleaseApprovalRehearsalResponseRecords.RehearsalArtifactRetentionHint artifactRetentionHint,
    ReleaseApprovalRehearsalResponseRecords.RehearsalLiveReadinessHint liveReadinessHint,
    ReleaseApprovalRehearsalResponseRecords.RehearsalAuditPersistenceHandoffHint
        auditPersistenceHandoffHint,
    ReleaseApprovalRehearsalResponseRecords.RehearsalApprovalRecordHandoffHint
        approvalRecordHandoffHint,
    ReleaseApprovalRehearsalManagedAuditReceiptChainBuilder.ReceiptChain receiptChain,
    ReleaseApprovalRehearsalResponseRecords.RehearsalFailureTaxonomy failureTaxonomy,
    ReleaseApprovalRehearsalResponseRecords.ExecutionBoundaries executionBoundaries) {}
