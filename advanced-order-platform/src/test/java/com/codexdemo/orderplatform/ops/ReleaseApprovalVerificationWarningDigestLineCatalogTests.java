package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class ReleaseApprovalVerificationWarningDigestLineCatalogTests extends OpsEvidenceServiceRehearsalTestSupport {

    @Test
    void collectsWarningAndBoundaryLinesInExpectedEchoOrder() {
        ReleaseApprovalRehearsalResponse rehearsal =
                readOnlyFixtureService().releaseApprovalRehearsal(headerBackedRehearsalRequest());
        ReleaseApprovalVerificationWarningDigestLineCatalog.Builders builders = builders();
        ReleaseApprovalVerificationWarningDigestLineCatalog.Receipts receipts = receipts(rehearsal);

        List<String> warningLines = ReleaseApprovalVerificationWarningDigestLineCatalog
                .warningLines(builders, receipts);
        List<String> boundaryLines = ReleaseApprovalVerificationWarningDigestLineCatalog
                .boundaryLines(builders, receipts);

        assertThat(warningLines).hasSize(34);
        assertThat(warningLines.getFirst())
                .startsWith("managedAuditSandboxAdapterApprovalSchemaGuardReceiptWarnings=");
        assertThat(warningLines.getLast())
                .startsWith(
                        "managedAuditSandboxEndpointCredentialResolverAbortRollbackSemanticsContractEchoReceiptWarnings="
                );

        assertThat(boundaryLines.getFirst())
                .startsWith("sandboxAdapterApprovalSchemaGuardDigest=");
        assertThat(boundaryLines)
                .contains(
                        "sandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeState=runtime-shell-post-decision-continuation-plan-intake-ready",
                        "sandboxEndpointCredentialResolverRuntimeShellPostDecisionReadyForNodeV302=true",
                        "sandboxEndpointCredentialResolverRuntimeShellPostDecisionApprovalLedgerWritten=false",
                        "sandboxEndpointCredentialResolverRuntimeShellStopPrerequisiteDecisionRecordState=runtime-shell-chain-stop-or-prerequisite-decision-record-ready",
                        "sandboxEndpointCredentialResolverRuntimeShellStopPrerequisiteReadyForNodeV305=true",
                        "sandboxEndpointCredentialResolverRuntimeShellStopPrerequisiteMiniKvWriteOrAuthorityCommandExecuted=false",
                        "sandboxEndpointCredentialResolverApprovalPrerequisiteArtifactIntakePlanState=approval-prerequisite-artifact-intake-plan-ready",
                        "sandboxEndpointCredentialResolverApprovalPrerequisiteArtifactReadyForNodeV307=true",
                        "sandboxEndpointCredentialResolverApprovalPrerequisiteArtifactMiniKvWriteOrAuthorityCommandExecuted=false",
                        "sandboxEndpointCredentialResolverHumanApprovalArtifactReviewPacketState=human-approval-artifact-review-packet-ready",
                        "sandboxEndpointCredentialResolverHumanApprovalArtifactReadyForNodeV309=true",
                        "sandboxEndpointCredentialResolverHumanApprovalArtifactMiniKvWriteOrAuthorityCommandExecuted=false",
                        "sandboxEndpointCredentialResolverHumanApprovalArtifactReviewPostEchoDecisionGateState=human-approval-artifact-review-post-echo-decision-gate-ready",
                        "sandboxEndpointCredentialResolverHumanApprovalArtifactPostEchoReadyForNodeV311=true",
                        "sandboxEndpointCredentialResolverHumanApprovalArtifactPostEchoMiniKvWriteOrAuthorityCommandExecuted=false",
                        "sandboxEndpointCredentialResolverCredentialHandleApprovalContractState=credential-handle-approval-contract-intake-ready",
                        "sandboxEndpointCredentialResolverCredentialHandleApprovalContractReadyForNodeV318=true",
                        "sandboxEndpointCredentialResolverCredentialHandleApprovalContractCredentialAuthorityClaimed=false",
                        "sandboxEndpointCredentialResolverEndpointHandleAllowlistApprovalContractState=endpoint-handle-allowlist-approval-contract-intake-ready",
                        "sandboxEndpointCredentialResolverEndpointHandleAllowlistApprovalContractReadyForNodeV321=true",
                        "sandboxEndpointCredentialResolverEndpointHandleAllowlistApprovalContractEndpointHandleAuthorityClaimed=false",
                        "sandboxEndpointCredentialResolverNoNetworkSafetyFixtureContractState=no-network-safety-fixture-contract-intake-ready",
                        "sandboxEndpointCredentialResolverNoNetworkSafetyFixtureContractReadyForNodeV324=true",
                        "sandboxEndpointCredentialResolverNoNetworkSafetyFixtureContractHttpRequestSent=false",
                        "sandboxEndpointCredentialResolverNoNetworkSafetyFixtureContractTcpConnectionAttempted=false",
                        "sandboxEndpointCredentialResolverAbortRollbackSemanticsContractState=abort-rollback-semantics-contract-intake-ready",
                        "sandboxEndpointCredentialResolverAbortRollbackSemanticsContractReadyForNodeV327=true",
                        "sandboxEndpointCredentialResolverAbortRollbackSemanticsContractAbortRollbackSemanticsExecuted=false",
                        "sandboxEndpointCredentialResolverAbortRollbackSemanticsContractRollbackExecuted=false"
                );
        assertThat(boundaryLines.getLast())
                .isEqualTo("sandboxEndpointCredentialResolverAbortRollbackSemanticsContractAutomaticUpstreamStart=false");
    }

    private static ReleaseApprovalVerificationWarningDigestLineCatalog.Builders builders() {
        return new ReleaseApprovalVerificationWarningDigestLineCatalog.Builders(
                new ReleaseApprovalManagedAuditSandboxAdapterApprovalSchemaGuardReceiptBuilder(),
                new ReleaseApprovalManagedAuditSandboxConnectionOperatorHandoffMarkerBuilder(),
                new ReleaseApprovalManagedAuditSandboxConnectionPreflightEchoMarkerBuilder(),
                new ReleaseApprovalManagedAuditSandboxConnectionPreconditionReceiptBuilder(),
                new ReleaseApprovalManagedAuditSandboxConnectionDryRunEnvelopeEchoReceiptBuilder(),
                new ReleaseApprovalManagedAuditSandboxConnectionOperatorWindowChecklistEchoReceiptBuilder(),
                new ReleaseApprovalManagedAuditSandboxConnectionDryRunCommandPackageEchoReceiptBuilder(),
                new ReleaseApprovalManagedAuditSandboxConnectionPrecheckPacketEchoReceiptBuilder(),
                new ReleaseApprovalManagedAuditSandboxConnectionDisabledAdapterClientPrecheckEchoReceiptBuilder(),
                new ReleaseApprovalManagedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarkerBuilder(),
                new ReleaseApprovalManagedAuditSandboxEndpointHandlePreflightEchoMarkerBuilder(),
                new ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverDecisionEchoMarkerBuilder(),
                new ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarkerBuilder(),
                new ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarkerBuilder(),
                new ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverFakeShellArchiveEchoReceiptBuilder(),
                new ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceiptBuilder(),
                new ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceiptBuilder(),
                new ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceiptBuilder(),
                new ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoReceiptBuilder(),
                new ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverImplementationPlanEchoReceiptBuilder(),
                new ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverExecutionDeniedEchoReceiptBuilder(),
                new ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverDisabledRuntimeShellHandoffEchoReceiptBuilder(),
                new ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverDisabledRuntimeShellCandidateGateEchoReceiptBuilder(),
                new ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoReceiptBuilder(),
                new ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoReceiptBuilder(),
                new ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverRuntimeShellStopPrerequisiteDecisionEchoReceiptBuilder(),
                new ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverApprovalPrerequisiteArtifactIntakeEchoReceiptBuilder(),
                new ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPacketEchoReceiptBuilder(),
                new ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPostEchoDecisionGateEchoReceiptBuilder(),
                new ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverSignedHumanApprovalArtifactContractEchoReceiptBuilder(),
                new ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverCredentialHandleApprovalContractEchoReceiptBuilder(),
                new ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverEndpointHandleAllowlistApprovalContractEchoReceiptBuilder(),
                new ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverNoNetworkSafetyFixtureContractEchoReceiptBuilder(),
                new ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverAbortRollbackSemanticsContractEchoReceiptBuilder()
        );
    }

    private static ReleaseApprovalVerificationWarningDigestLineCatalog.Receipts receipts(
            ReleaseApprovalRehearsalResponse rehearsal
    ) {
        return new ReleaseApprovalVerificationWarningDigestLineCatalog.Receipts(
                rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt(),
                rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker(),
                rehearsal.managedAuditSandboxConnectionPreflightEchoMarker(),
                rehearsal.managedAuditSandboxConnectionPreconditionReceipt(),
                rehearsal.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt(),
                rehearsal.managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt(),
                rehearsal.managedAuditSandboxConnectionDryRunCommandPackageEchoReceipt(),
                rehearsal.managedAuditSandboxConnectionPrecheckPacketEchoReceipt(),
                rehearsal.managedAuditSandboxConnectionDisabledAdapterClientPrecheckEchoReceipt(),
                rehearsal.managedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker(),
                rehearsal.managedAuditSandboxEndpointHandlePreflightEchoMarker(),
                rehearsal.managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker(),
                rehearsal.managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker(),
                rehearsal.managedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarker(),
                rehearsal.managedAuditSandboxEndpointCredentialResolverFakeShellArchiveEchoReceipt(),
                rehearsal.managedAuditSandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceipt(),
                rehearsal.managedAuditSandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceipt(),
                rehearsal.managedAuditSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceipt(),
                rehearsal.managedAuditSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoReceipt(),
                rehearsal.managedAuditSandboxEndpointCredentialResolverImplementationPlanEchoReceipt(),
                rehearsal.managedAuditSandboxEndpointCredentialResolverExecutionDeniedEchoReceipt(),
                rehearsal.managedAuditSandboxEndpointCredentialResolverDisabledRuntimeShellHandoffEchoReceipt(),
                rehearsal.managedAuditSandboxEndpointCredentialResolverDisabledRuntimeShellCandidateGateEchoReceipt(),
                rehearsal.managedAuditSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoReceipt(),
                rehearsal.managedAuditSandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoReceipt(),
                rehearsal.managedAuditSandboxEndpointCredentialResolverRuntimeShellStopPrerequisiteDecisionEchoReceipt(),
                rehearsal.managedAuditSandboxEndpointCredentialResolverApprovalPrerequisiteArtifactIntakeEchoReceipt(),
                rehearsal.managedAuditSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPacketEchoReceipt(),
                rehearsal.managedAuditSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPostEchoDecisionGateEchoReceipt(),
                rehearsal.managedAuditSandboxEndpointCredentialResolverSignedHumanApprovalArtifactContractEchoReceipt(),
                rehearsal.managedAuditSandboxEndpointCredentialResolverCredentialHandleApprovalContractEchoReceipt(),
                rehearsal.managedAuditSandboxEndpointCredentialResolverEndpointHandleAllowlistApprovalContractEchoReceipt(),
                rehearsal.managedAuditSandboxEndpointCredentialResolverNoNetworkSafetyFixtureContractEchoReceipt(),
                rehearsal.managedAuditSandboxEndpointCredentialResolverAbortRollbackSemanticsContractEchoReceipt()
        );
    }
}
