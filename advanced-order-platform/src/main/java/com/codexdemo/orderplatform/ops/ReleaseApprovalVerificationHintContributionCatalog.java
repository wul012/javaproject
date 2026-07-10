package com.codexdemo.orderplatform.ops;

import java.util.List;
import java.util.function.Supplier;

final class ReleaseApprovalVerificationHintContributionCatalog {

  private ReleaseApprovalVerificationHintContributionCatalog() {}

  static List<ReleaseApprovalVerificationHintContribution> build(
      ReleaseApprovalRehearsalManagedAuditReceiptChainBuilder.ReceiptChain receiptChain) {
    var sandboxAdapterApprovalSchemaGuardReceiptBuilder =
        receiptChain.sandboxAdapterApprovalSchemaGuardReceiptBuilder();
    var sandboxConnectionOperatorHandoffMarkerBuilder =
        receiptChain.sandboxConnectionOperatorHandoffMarkerBuilder();
    var sandboxConnectionPreflightEchoMarkerBuilder =
        receiptChain.sandboxConnectionPreflightEchoMarkerBuilder();
    var sandboxConnectionPreconditionReceiptBuilder =
        receiptChain.sandboxConnectionPreconditionReceiptBuilder();
    var sandboxConnectionDryRunEnvelopeEchoReceiptBuilder =
        receiptChain.sandboxConnectionDryRunEnvelopeEchoReceiptBuilder();
    var sandboxConnectionOperatorWindowChecklistEchoReceiptBuilder =
        receiptChain.sandboxConnectionOperatorWindowChecklistEchoReceiptBuilder();
    var sandboxConnectionDryRunCommandPackageEchoReceiptBuilder =
        receiptChain.sandboxConnectionDryRunCommandPackageEchoReceiptBuilder();
    var sandboxConnectionPrecheckPacketEchoReceiptBuilder =
        receiptChain.sandboxConnectionPrecheckPacketEchoReceiptBuilder();
    var sandboxConnectionDisabledAdapterClientPrecheckEchoReceiptBuilder =
        receiptChain.sandboxConnectionDisabledAdapterClientPrecheckEchoReceiptBuilder();
    var sandboxConnectionFakeTransportDryRunPacketEchoMarkerBuilder =
        receiptChain.sandboxConnectionFakeTransportDryRunPacketEchoMarkerBuilder();
    var sandboxEndpointHandlePreflightEchoMarkerBuilder =
        receiptChain.sandboxEndpointHandlePreflightEchoMarkerBuilder();
    var sandboxEndpointCredentialResolverDecisionEchoMarkerBuilder =
        receiptChain.sandboxEndpointCredentialResolverDecisionEchoMarkerBuilder();
    var sandboxEndpointCredentialResolverDisabledPrecheckEchoMarkerBuilder =
        receiptChain.sandboxEndpointCredentialResolverDisabledPrecheckEchoMarkerBuilder();
    var sandboxEndpointCredentialResolverTestOnlyShellEchoMarkerBuilder =
        receiptChain.sandboxEndpointCredentialResolverTestOnlyShellEchoMarkerBuilder();
    var sandboxEndpointCredentialResolverFakeShellArchiveEchoReceiptBuilder =
        receiptChain.sandboxEndpointCredentialResolverFakeShellArchiveEchoReceiptBuilder();
    var sandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceiptBuilder =
        receiptChain
            .sandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceiptBuilder();
    var sandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceiptBuilder =
        receiptChain
            .sandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceiptBuilder();
    var sandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceiptBuilder =
        receiptChain
            .sandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceiptBuilder();
    var sandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoReceiptBuilder =
        receiptChain
            .sandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoReceiptBuilder();
    var sandboxEndpointCredentialResolverImplementationPlanEchoReceiptBuilder =
        receiptChain.sandboxEndpointCredentialResolverImplementationPlanEchoReceiptBuilder();
    var sandboxEndpointCredentialResolverExecutionDeniedEchoReceiptBuilder =
        receiptChain.sandboxEndpointCredentialResolverExecutionDeniedEchoReceiptBuilder();
    var sandboxEndpointCredentialResolverDisabledRuntimeShellHandoffEchoReceiptBuilder =
        receiptChain
            .sandboxEndpointCredentialResolverDisabledRuntimeShellHandoffEchoReceiptBuilder();
    var sandboxEndpointCredentialResolverDisabledRuntimeShellCandidateGateEchoReceiptBuilder =
        receiptChain
            .sandboxEndpointCredentialResolverDisabledRuntimeShellCandidateGateEchoReceiptBuilder();
    var sandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoReceiptBuilder =
        receiptChain
            .sandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoReceiptBuilder();
    var sandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoReceiptBuilder =
        receiptChain
            .sandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoReceiptBuilder();
    var sandboxEndpointCredentialResolverRuntimeShellStopPrerequisiteDecisionEchoReceiptBuilder =
        receiptChain
            .sandboxEndpointCredentialResolverRuntimeShellStopPrerequisiteDecisionEchoReceiptBuilder();
    var sandboxEndpointCredentialResolverApprovalPrerequisiteArtifactIntakeEchoReceiptBuilder =
        receiptChain
            .sandboxEndpointCredentialResolverApprovalPrerequisiteArtifactIntakeEchoReceiptBuilder();
    var sandboxEndpointCredentialResolverHumanApprovalArtifactReviewPacketEchoReceiptBuilder =
        receiptChain
            .sandboxEndpointCredentialResolverHumanApprovalArtifactReviewPacketEchoReceiptBuilder();
    var
        sandboxEndpointCredentialResolverHumanApprovalArtifactReviewPostEchoDecisionGateEchoReceiptBuilder =
            receiptChain
                .sandboxEndpointCredentialResolverHumanApprovalArtifactReviewPostEchoDecisionGateEchoReceiptBuilder();
    var sandboxEndpointCredentialResolverSignedHumanApprovalArtifactContractEchoReceiptBuilder =
        receiptChain
            .sandboxEndpointCredentialResolverSignedHumanApprovalArtifactContractEchoReceiptBuilder();
    var sandboxEndpointCredentialResolverCredentialHandleApprovalContractEchoReceiptBuilder =
        receiptChain
            .sandboxEndpointCredentialResolverCredentialHandleApprovalContractEchoReceiptBuilder();
    var sandboxEndpointCredentialResolverEndpointHandleAllowlistApprovalContractEchoReceiptBuilder =
        receiptChain
            .sandboxEndpointCredentialResolverEndpointHandleAllowlistApprovalContractEchoReceiptBuilder();
    var sandboxEndpointCredentialResolverNoNetworkSafetyFixtureContractEchoReceiptBuilder =
        receiptChain
            .sandboxEndpointCredentialResolverNoNetworkSafetyFixtureContractEchoReceiptBuilder();
    var sandboxEndpointCredentialResolverAbortRollbackSemanticsContractEchoReceiptBuilder =
        receiptChain
            .sandboxEndpointCredentialResolverAbortRollbackSemanticsContractEchoReceiptBuilder();
    return List.of(
        contribution(
            sandboxAdapterApprovalSchemaGuardReceiptBuilder::warningDigestWarningInputNames,
            sandboxAdapterApprovalSchemaGuardReceiptBuilder::warningDigestBoundaryInputNames,
            sandboxAdapterApprovalSchemaGuardReceiptBuilder::proofClaims,
            () ->
                sandboxAdapterApprovalSchemaGuardReceiptBuilder.nodeVerificationActions().stream()
                    .filter(
                        action ->
                            !("Verify managedAuditSandboxAdapterApprovalSchemaGuardReceipt"
                                    + ".qualityGateBoundary.builderOrHelperSplitApplied=true")
                                .equals(action))
                    .toList()),
        contribution(
            sandboxConnectionOperatorHandoffMarkerBuilder::warningDigestWarningInputNames,
            sandboxConnectionOperatorHandoffMarkerBuilder::warningDigestBoundaryInputNames,
            sandboxConnectionOperatorHandoffMarkerBuilder::proofClaims,
            sandboxConnectionOperatorHandoffMarkerBuilder::nodeVerificationActions),
        contribution(
            sandboxConnectionPreflightEchoMarkerBuilder::warningDigestWarningInputNames,
            sandboxConnectionPreflightEchoMarkerBuilder::warningDigestBoundaryInputNames,
            sandboxConnectionPreflightEchoMarkerBuilder::proofClaims,
            sandboxConnectionPreflightEchoMarkerBuilder::nodeVerificationActions),
        contribution(
            sandboxConnectionPreconditionReceiptBuilder::warningDigestWarningInputNames,
            sandboxConnectionPreconditionReceiptBuilder::warningDigestBoundaryInputNames,
            sandboxConnectionPreconditionReceiptBuilder::proofClaims,
            sandboxConnectionPreconditionReceiptBuilder::nodeVerificationActions),
        contribution(
            sandboxConnectionDryRunEnvelopeEchoReceiptBuilder::warningDigestWarningInputNames,
            sandboxConnectionDryRunEnvelopeEchoReceiptBuilder::warningDigestBoundaryInputNames,
            sandboxConnectionDryRunEnvelopeEchoReceiptBuilder::proofClaims,
            sandboxConnectionDryRunEnvelopeEchoReceiptBuilder::nodeVerificationActions),
        contribution(
            sandboxConnectionOperatorWindowChecklistEchoReceiptBuilder
                ::warningDigestWarningInputNames,
            sandboxConnectionOperatorWindowChecklistEchoReceiptBuilder
                ::warningDigestBoundaryInputNames,
            sandboxConnectionOperatorWindowChecklistEchoReceiptBuilder::proofClaims,
            sandboxConnectionOperatorWindowChecklistEchoReceiptBuilder::nodeVerificationActions),
        contribution(
            sandboxConnectionDryRunCommandPackageEchoReceiptBuilder::warningDigestWarningInputNames,
            sandboxConnectionDryRunCommandPackageEchoReceiptBuilder
                ::warningDigestBoundaryInputNames,
            sandboxConnectionDryRunCommandPackageEchoReceiptBuilder::proofClaims,
            sandboxConnectionDryRunCommandPackageEchoReceiptBuilder::nodeVerificationActions),
        contribution(
            sandboxConnectionPrecheckPacketEchoReceiptBuilder::warningDigestWarningInputNames,
            sandboxConnectionPrecheckPacketEchoReceiptBuilder::warningDigestBoundaryInputNames,
            sandboxConnectionPrecheckPacketEchoReceiptBuilder::proofClaims,
            sandboxConnectionPrecheckPacketEchoReceiptBuilder::nodeVerificationActions),
        contribution(
            sandboxConnectionDisabledAdapterClientPrecheckEchoReceiptBuilder
                ::warningDigestWarningInputNames,
            sandboxConnectionDisabledAdapterClientPrecheckEchoReceiptBuilder
                ::warningDigestBoundaryInputNames,
            sandboxConnectionDisabledAdapterClientPrecheckEchoReceiptBuilder::proofClaims,
            sandboxConnectionDisabledAdapterClientPrecheckEchoReceiptBuilder
                ::nodeVerificationActions),
        contribution(
            sandboxConnectionFakeTransportDryRunPacketEchoMarkerBuilder
                ::warningDigestWarningInputNames,
            sandboxConnectionFakeTransportDryRunPacketEchoMarkerBuilder
                ::warningDigestBoundaryInputNames,
            sandboxConnectionFakeTransportDryRunPacketEchoMarkerBuilder::proofClaims,
            sandboxConnectionFakeTransportDryRunPacketEchoMarkerBuilder::nodeVerificationActions),
        contribution(
            sandboxEndpointHandlePreflightEchoMarkerBuilder::warningDigestWarningInputNames,
            sandboxEndpointHandlePreflightEchoMarkerBuilder::warningDigestBoundaryInputNames,
            sandboxEndpointHandlePreflightEchoMarkerBuilder::proofClaims,
            sandboxEndpointHandlePreflightEchoMarkerBuilder::nodeVerificationActions),
        contribution(
            sandboxEndpointCredentialResolverDecisionEchoMarkerBuilder
                ::warningDigestWarningInputNames,
            sandboxEndpointCredentialResolverDecisionEchoMarkerBuilder
                ::warningDigestBoundaryInputNames,
            sandboxEndpointCredentialResolverDecisionEchoMarkerBuilder::proofClaims,
            sandboxEndpointCredentialResolverDecisionEchoMarkerBuilder::nodeVerificationActions),
        contribution(
            sandboxEndpointCredentialResolverDisabledPrecheckEchoMarkerBuilder
                ::warningDigestWarningInputNames,
            sandboxEndpointCredentialResolverDisabledPrecheckEchoMarkerBuilder
                ::warningDigestBoundaryInputNames,
            sandboxEndpointCredentialResolverDisabledPrecheckEchoMarkerBuilder::proofClaims,
            sandboxEndpointCredentialResolverDisabledPrecheckEchoMarkerBuilder
                ::nodeVerificationActions),
        contribution(
            sandboxEndpointCredentialResolverTestOnlyShellEchoMarkerBuilder
                ::warningDigestWarningInputNames,
            sandboxEndpointCredentialResolverTestOnlyShellEchoMarkerBuilder
                ::warningDigestBoundaryInputNames,
            sandboxEndpointCredentialResolverTestOnlyShellEchoMarkerBuilder::proofClaims,
            sandboxEndpointCredentialResolverTestOnlyShellEchoMarkerBuilder
                ::nodeVerificationActions),
        contribution(
            sandboxEndpointCredentialResolverFakeShellArchiveEchoReceiptBuilder
                ::warningDigestWarningInputNames,
            sandboxEndpointCredentialResolverFakeShellArchiveEchoReceiptBuilder
                ::warningDigestBoundaryInputNames,
            sandboxEndpointCredentialResolverFakeShellArchiveEchoReceiptBuilder::proofClaims,
            sandboxEndpointCredentialResolverFakeShellArchiveEchoReceiptBuilder
                ::nodeVerificationActions),
        contribution(
            sandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceiptBuilder
                ::warningDigestWarningInputNames,
            sandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceiptBuilder
                ::warningDigestBoundaryInputNames,
            sandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceiptBuilder
                ::proofClaims,
            sandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceiptBuilder
                ::nodeVerificationActions),
        contribution(
            sandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceiptBuilder
                ::warningDigestWarningInputNames,
            sandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceiptBuilder
                ::warningDigestBoundaryInputNames,
            sandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceiptBuilder
                ::proofClaims,
            sandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceiptBuilder
                ::nodeVerificationActions),
        contribution(
            sandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceiptBuilder
                ::warningDigestWarningInputNames,
            sandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceiptBuilder
                ::warningDigestBoundaryInputNames,
            sandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceiptBuilder
                ::proofClaims,
            sandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceiptBuilder
                ::nodeVerificationActions),
        contribution(
            sandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoReceiptBuilder
                ::warningDigestWarningInputNames,
            sandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoReceiptBuilder
                ::warningDigestBoundaryInputNames,
            sandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoReceiptBuilder
                ::proofClaims,
            sandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoReceiptBuilder
                ::nodeVerificationActions),
        contribution(
            sandboxEndpointCredentialResolverImplementationPlanEchoReceiptBuilder
                ::warningDigestWarningInputNames,
            sandboxEndpointCredentialResolverImplementationPlanEchoReceiptBuilder
                ::warningDigestBoundaryInputNames,
            sandboxEndpointCredentialResolverImplementationPlanEchoReceiptBuilder::proofClaims,
            sandboxEndpointCredentialResolverImplementationPlanEchoReceiptBuilder
                ::nodeVerificationActions),
        contribution(
            sandboxEndpointCredentialResolverExecutionDeniedEchoReceiptBuilder
                ::warningDigestWarningInputNames,
            sandboxEndpointCredentialResolverExecutionDeniedEchoReceiptBuilder
                ::warningDigestBoundaryInputNames,
            sandboxEndpointCredentialResolverExecutionDeniedEchoReceiptBuilder::proofClaims,
            sandboxEndpointCredentialResolverExecutionDeniedEchoReceiptBuilder
                ::nodeVerificationActions),
        contribution(
            sandboxEndpointCredentialResolverDisabledRuntimeShellHandoffEchoReceiptBuilder
                ::warningDigestWarningInputNames,
            sandboxEndpointCredentialResolverDisabledRuntimeShellHandoffEchoReceiptBuilder
                ::warningDigestBoundaryInputNames,
            sandboxEndpointCredentialResolverDisabledRuntimeShellHandoffEchoReceiptBuilder
                ::proofClaims,
            sandboxEndpointCredentialResolverDisabledRuntimeShellHandoffEchoReceiptBuilder
                ::nodeVerificationActions),
        contribution(
            sandboxEndpointCredentialResolverDisabledRuntimeShellCandidateGateEchoReceiptBuilder
                ::warningDigestWarningInputNames,
            sandboxEndpointCredentialResolverDisabledRuntimeShellCandidateGateEchoReceiptBuilder
                ::warningDigestBoundaryInputNames,
            sandboxEndpointCredentialResolverDisabledRuntimeShellCandidateGateEchoReceiptBuilder
                ::proofClaims,
            sandboxEndpointCredentialResolverDisabledRuntimeShellCandidateGateEchoReceiptBuilder
                ::nodeVerificationActions),
        contribution(
            sandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoReceiptBuilder
                ::warningDigestWarningInputNames,
            sandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoReceiptBuilder
                ::warningDigestBoundaryInputNames,
            sandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoReceiptBuilder
                ::proofClaims,
            sandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoReceiptBuilder
                ::nodeVerificationActions),
        contribution(
            sandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoReceiptBuilder
                ::warningDigestWarningInputNames,
            sandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoReceiptBuilder
                ::warningDigestBoundaryInputNames,
            sandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoReceiptBuilder
                ::proofClaims,
            sandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoReceiptBuilder
                ::nodeVerificationActions),
        contribution(
            sandboxEndpointCredentialResolverRuntimeShellStopPrerequisiteDecisionEchoReceiptBuilder
                ::warningDigestWarningInputNames,
            sandboxEndpointCredentialResolverRuntimeShellStopPrerequisiteDecisionEchoReceiptBuilder
                ::warningDigestBoundaryInputNames,
            sandboxEndpointCredentialResolverRuntimeShellStopPrerequisiteDecisionEchoReceiptBuilder
                ::proofClaims,
            sandboxEndpointCredentialResolverRuntimeShellStopPrerequisiteDecisionEchoReceiptBuilder
                ::nodeVerificationActions),
        contribution(
            sandboxEndpointCredentialResolverApprovalPrerequisiteArtifactIntakeEchoReceiptBuilder
                ::warningDigestWarningInputNames,
            sandboxEndpointCredentialResolverApprovalPrerequisiteArtifactIntakeEchoReceiptBuilder
                ::warningDigestBoundaryInputNames,
            sandboxEndpointCredentialResolverApprovalPrerequisiteArtifactIntakeEchoReceiptBuilder
                ::proofClaims,
            sandboxEndpointCredentialResolverApprovalPrerequisiteArtifactIntakeEchoReceiptBuilder
                ::nodeVerificationActions),
        contribution(
            sandboxEndpointCredentialResolverHumanApprovalArtifactReviewPacketEchoReceiptBuilder
                ::warningDigestWarningInputNames,
            sandboxEndpointCredentialResolverHumanApprovalArtifactReviewPacketEchoReceiptBuilder
                ::warningDigestBoundaryInputNames,
            sandboxEndpointCredentialResolverHumanApprovalArtifactReviewPacketEchoReceiptBuilder
                ::proofClaims,
            sandboxEndpointCredentialResolverHumanApprovalArtifactReviewPacketEchoReceiptBuilder
                ::nodeVerificationActions),
        contribution(
            sandboxEndpointCredentialResolverHumanApprovalArtifactReviewPostEchoDecisionGateEchoReceiptBuilder
                ::warningDigestWarningInputNames,
            sandboxEndpointCredentialResolverHumanApprovalArtifactReviewPostEchoDecisionGateEchoReceiptBuilder
                ::warningDigestBoundaryInputNames,
            sandboxEndpointCredentialResolverHumanApprovalArtifactReviewPostEchoDecisionGateEchoReceiptBuilder
                ::proofClaims,
            sandboxEndpointCredentialResolverHumanApprovalArtifactReviewPostEchoDecisionGateEchoReceiptBuilder
                ::nodeVerificationActions),
        contribution(
            sandboxEndpointCredentialResolverSignedHumanApprovalArtifactContractEchoReceiptBuilder
                ::warningDigestWarningInputNames,
            sandboxEndpointCredentialResolverSignedHumanApprovalArtifactContractEchoReceiptBuilder
                ::warningDigestBoundaryInputNames,
            sandboxEndpointCredentialResolverSignedHumanApprovalArtifactContractEchoReceiptBuilder
                ::proofClaims,
            sandboxEndpointCredentialResolverSignedHumanApprovalArtifactContractEchoReceiptBuilder
                ::nodeVerificationActions),
        contribution(
            sandboxEndpointCredentialResolverCredentialHandleApprovalContractEchoReceiptBuilder
                ::warningDigestWarningInputNames,
            sandboxEndpointCredentialResolverCredentialHandleApprovalContractEchoReceiptBuilder
                ::warningDigestBoundaryInputNames,
            sandboxEndpointCredentialResolverCredentialHandleApprovalContractEchoReceiptBuilder
                ::proofClaims,
            sandboxEndpointCredentialResolverCredentialHandleApprovalContractEchoReceiptBuilder
                ::nodeVerificationActions),
        contribution(
            sandboxEndpointCredentialResolverEndpointHandleAllowlistApprovalContractEchoReceiptBuilder
                ::warningDigestWarningInputNames,
            sandboxEndpointCredentialResolverEndpointHandleAllowlistApprovalContractEchoReceiptBuilder
                ::warningDigestBoundaryInputNames,
            sandboxEndpointCredentialResolverEndpointHandleAllowlistApprovalContractEchoReceiptBuilder
                ::proofClaims,
            sandboxEndpointCredentialResolverEndpointHandleAllowlistApprovalContractEchoReceiptBuilder
                ::nodeVerificationActions),
        contribution(
            sandboxEndpointCredentialResolverNoNetworkSafetyFixtureContractEchoReceiptBuilder
                ::warningDigestWarningInputNames,
            sandboxEndpointCredentialResolverNoNetworkSafetyFixtureContractEchoReceiptBuilder
                ::warningDigestBoundaryInputNames,
            sandboxEndpointCredentialResolverNoNetworkSafetyFixtureContractEchoReceiptBuilder
                ::proofClaims,
            sandboxEndpointCredentialResolverNoNetworkSafetyFixtureContractEchoReceiptBuilder
                ::nodeVerificationActions),
        contribution(
            sandboxEndpointCredentialResolverAbortRollbackSemanticsContractEchoReceiptBuilder
                ::warningDigestWarningInputNames,
            sandboxEndpointCredentialResolverAbortRollbackSemanticsContractEchoReceiptBuilder
                ::warningDigestBoundaryInputNames,
            sandboxEndpointCredentialResolverAbortRollbackSemanticsContractEchoReceiptBuilder
                ::proofClaims,
            sandboxEndpointCredentialResolverAbortRollbackSemanticsContractEchoReceiptBuilder
                ::nodeVerificationActions));
  }

  private static ReleaseApprovalVerificationHintContribution contribution(
      Supplier<List<String>> warningDigestWarningInputNames,
      Supplier<List<String>> warningDigestBoundaryInputNames,
      Supplier<List<String>> proofClaims,
      Supplier<List<String>> nodeVerificationActions) {
    return new ReleaseApprovalVerificationHintContribution(
        warningDigestWarningInputNames,
        warningDigestBoundaryInputNames,
        proofClaims,
        nodeVerificationActions);
  }
}
