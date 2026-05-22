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

        assertThat(warningLines).hasSize(27);
        assertThat(warningLines.getFirst())
                .startsWith("managedAuditSandboxAdapterApprovalSchemaGuardReceiptWarnings=");
        assertThat(warningLines.getLast())
                .startsWith(
                        "managedAuditSandboxEndpointCredentialResolverApprovalPrerequisiteArtifactIntakeEchoReceiptWarnings="
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
                        "sandboxEndpointCredentialResolverApprovalPrerequisiteArtifactMiniKvWriteOrAuthorityCommandExecuted=false"
                );
        assertThat(boundaryLines.getLast())
                .isEqualTo("sandboxEndpointCredentialResolverApprovalPrerequisiteArtifactAutomaticUpstreamStart=false");
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
                new ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverApprovalPrerequisiteArtifactIntakeEchoReceiptBuilder()
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
                rehearsal.managedAuditSandboxEndpointCredentialResolverApprovalPrerequisiteArtifactIntakeEchoReceipt()
        );
    }
}
