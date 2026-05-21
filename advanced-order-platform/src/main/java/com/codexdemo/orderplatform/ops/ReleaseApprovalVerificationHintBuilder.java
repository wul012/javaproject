package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverDisabledPrecheckEchoRecords
        .RehearsalManagedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker;
import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoRecords
        .RehearsalManagedAuditSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceipt;
import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoRecords
        .RehearsalManagedAuditSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoReceipt;
import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverImplementationPlanEchoRecords
        .RehearsalManagedAuditSandboxEndpointCredentialResolverImplementationPlanEchoReceipt;
import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverExecutionDeniedEchoRecords
        .RehearsalManagedAuditSandboxEndpointCredentialResolverExecutionDeniedEchoReceipt;
import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverDisabledRuntimeShellHandoffEchoRecords
        .RehearsalManagedAuditSandboxEndpointCredentialResolverDisabledRuntimeShellHandoffEchoReceipt;
import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverDisabledRuntimeShellCandidateGateEchoRecords
        .RehearsalManagedAuditSandboxEndpointCredentialResolverDisabledRuntimeShellCandidateGateEchoReceipt;
import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoRecords
        .RehearsalManagedAuditSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoReceipt;
import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoRecords
        .RehearsalManagedAuditSandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoReceipt;
import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverFakeShellArchiveEchoRecords
        .RehearsalManagedAuditSandboxEndpointCredentialResolverFakeShellArchiveEchoReceipt;
import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoRecords
        .RehearsalManagedAuditSandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceipt;
import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoRecords
        .RehearsalManagedAuditSandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceipt;
import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverTestOnlyShellEchoRecords
        .RehearsalManagedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarker;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

final class ReleaseApprovalVerificationHintBuilder {

    private final ReleaseApprovalManagedAuditSandboxAdapterApprovalSchemaGuardReceiptBuilder
            sandboxAdapterApprovalSchemaGuardReceiptBuilder;
    private final ReleaseApprovalManagedAuditSandboxConnectionOperatorHandoffMarkerBuilder
            sandboxConnectionOperatorHandoffMarkerBuilder;
    private final ReleaseApprovalManagedAuditSandboxConnectionPreflightEchoMarkerBuilder
            sandboxConnectionPreflightEchoMarkerBuilder;
    private final ReleaseApprovalManagedAuditSandboxConnectionPreconditionReceiptBuilder
            sandboxConnectionPreconditionReceiptBuilder;
    private final ReleaseApprovalManagedAuditSandboxConnectionDryRunEnvelopeEchoReceiptBuilder
            sandboxConnectionDryRunEnvelopeEchoReceiptBuilder;
    private final ReleaseApprovalManagedAuditSandboxConnectionOperatorWindowChecklistEchoReceiptBuilder
            sandboxConnectionOperatorWindowChecklistEchoReceiptBuilder;
    private final ReleaseApprovalManagedAuditSandboxConnectionDryRunCommandPackageEchoReceiptBuilder
            sandboxConnectionDryRunCommandPackageEchoReceiptBuilder;
    private final ReleaseApprovalManagedAuditSandboxConnectionPrecheckPacketEchoReceiptBuilder
            sandboxConnectionPrecheckPacketEchoReceiptBuilder;
    private final ReleaseApprovalManagedAuditSandboxConnectionDisabledAdapterClientPrecheckEchoReceiptBuilder
            sandboxConnectionDisabledAdapterClientPrecheckEchoReceiptBuilder;
    private final ReleaseApprovalManagedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarkerBuilder
            sandboxConnectionFakeTransportDryRunPacketEchoMarkerBuilder;
    private final ReleaseApprovalManagedAuditSandboxEndpointHandlePreflightEchoMarkerBuilder
            sandboxEndpointHandlePreflightEchoMarkerBuilder;
    private final ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverDecisionEchoMarkerBuilder
            sandboxEndpointCredentialResolverDecisionEchoMarkerBuilder;
    private final ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarkerBuilder
            sandboxEndpointCredentialResolverDisabledPrecheckEchoMarkerBuilder;
    private final ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarkerBuilder
            sandboxEndpointCredentialResolverTestOnlyShellEchoMarkerBuilder;
    private final ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverFakeShellArchiveEchoReceiptBuilder
            sandboxEndpointCredentialResolverFakeShellArchiveEchoReceiptBuilder;
    private final ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceiptBuilder
            sandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceiptBuilder;
    private final ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceiptBuilder
            sandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceiptBuilder;
    private final ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceiptBuilder
            sandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceiptBuilder;
    private final ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoReceiptBuilder
            sandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoReceiptBuilder;
    private final ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverImplementationPlanEchoReceiptBuilder
            sandboxEndpointCredentialResolverImplementationPlanEchoReceiptBuilder;
    private final ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverExecutionDeniedEchoReceiptBuilder
            sandboxEndpointCredentialResolverExecutionDeniedEchoReceiptBuilder;
    private final ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverDisabledRuntimeShellHandoffEchoReceiptBuilder
            sandboxEndpointCredentialResolverDisabledRuntimeShellHandoffEchoReceiptBuilder;
    private final ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverDisabledRuntimeShellCandidateGateEchoReceiptBuilder
            sandboxEndpointCredentialResolverDisabledRuntimeShellCandidateGateEchoReceiptBuilder;
    private final ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoReceiptBuilder
            sandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoReceiptBuilder;
    private final ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoReceiptBuilder
            sandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoReceiptBuilder;
    private final List<ReleaseApprovalVerificationHintContribution> verificationContributions;
    private final ReleaseApprovalVerificationWarningDigestBuilder warningDigestBuilder;

    ReleaseApprovalVerificationHintBuilder(
            ReleaseApprovalManagedAuditSandboxAdapterApprovalSchemaGuardReceiptBuilder
                    sandboxAdapterApprovalSchemaGuardReceiptBuilder,
            ReleaseApprovalManagedAuditSandboxConnectionOperatorHandoffMarkerBuilder
                    sandboxConnectionOperatorHandoffMarkerBuilder,
            ReleaseApprovalManagedAuditSandboxConnectionPreflightEchoMarkerBuilder
                    sandboxConnectionPreflightEchoMarkerBuilder,
            ReleaseApprovalManagedAuditSandboxConnectionPreconditionReceiptBuilder
                    sandboxConnectionPreconditionReceiptBuilder,
            ReleaseApprovalManagedAuditSandboxConnectionDryRunEnvelopeEchoReceiptBuilder
                    sandboxConnectionDryRunEnvelopeEchoReceiptBuilder,
            ReleaseApprovalManagedAuditSandboxConnectionOperatorWindowChecklistEchoReceiptBuilder
                    sandboxConnectionOperatorWindowChecklistEchoReceiptBuilder,
            ReleaseApprovalManagedAuditSandboxConnectionDryRunCommandPackageEchoReceiptBuilder
                    sandboxConnectionDryRunCommandPackageEchoReceiptBuilder,
            ReleaseApprovalManagedAuditSandboxConnectionPrecheckPacketEchoReceiptBuilder
                    sandboxConnectionPrecheckPacketEchoReceiptBuilder,
            ReleaseApprovalManagedAuditSandboxConnectionDisabledAdapterClientPrecheckEchoReceiptBuilder
                    sandboxConnectionDisabledAdapterClientPrecheckEchoReceiptBuilder,
            ReleaseApprovalManagedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarkerBuilder
                    sandboxConnectionFakeTransportDryRunPacketEchoMarkerBuilder,
            ReleaseApprovalManagedAuditSandboxEndpointHandlePreflightEchoMarkerBuilder
                    sandboxEndpointHandlePreflightEchoMarkerBuilder,
            ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverDecisionEchoMarkerBuilder
                    sandboxEndpointCredentialResolverDecisionEchoMarkerBuilder,
            ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarkerBuilder
                    sandboxEndpointCredentialResolverDisabledPrecheckEchoMarkerBuilder,
            ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarkerBuilder
                    sandboxEndpointCredentialResolverTestOnlyShellEchoMarkerBuilder,
            ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverFakeShellArchiveEchoReceiptBuilder
                    sandboxEndpointCredentialResolverFakeShellArchiveEchoReceiptBuilder,
            ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceiptBuilder
                    sandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceiptBuilder,
            ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceiptBuilder
                    sandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceiptBuilder,
            ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceiptBuilder
                    sandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceiptBuilder,
            ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoReceiptBuilder
                    sandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoReceiptBuilder,
            ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverImplementationPlanEchoReceiptBuilder
                    sandboxEndpointCredentialResolverImplementationPlanEchoReceiptBuilder,
            ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverExecutionDeniedEchoReceiptBuilder
                    sandboxEndpointCredentialResolverExecutionDeniedEchoReceiptBuilder,
            ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverDisabledRuntimeShellHandoffEchoReceiptBuilder
                    sandboxEndpointCredentialResolverDisabledRuntimeShellHandoffEchoReceiptBuilder,
            ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverDisabledRuntimeShellCandidateGateEchoReceiptBuilder
                    sandboxEndpointCredentialResolverDisabledRuntimeShellCandidateGateEchoReceiptBuilder,
            ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoReceiptBuilder
                    sandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoReceiptBuilder,
            ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoReceiptBuilder
                    sandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoReceiptBuilder
    ) {
        this.sandboxAdapterApprovalSchemaGuardReceiptBuilder =
                sandboxAdapterApprovalSchemaGuardReceiptBuilder;
        this.sandboxConnectionOperatorHandoffMarkerBuilder =
                sandboxConnectionOperatorHandoffMarkerBuilder;
        this.sandboxConnectionPreflightEchoMarkerBuilder =
                sandboxConnectionPreflightEchoMarkerBuilder;
        this.sandboxConnectionPreconditionReceiptBuilder =
                sandboxConnectionPreconditionReceiptBuilder;
        this.sandboxConnectionDryRunEnvelopeEchoReceiptBuilder =
                sandboxConnectionDryRunEnvelopeEchoReceiptBuilder;
        this.sandboxConnectionOperatorWindowChecklistEchoReceiptBuilder =
                sandboxConnectionOperatorWindowChecklistEchoReceiptBuilder;
        this.sandboxConnectionDryRunCommandPackageEchoReceiptBuilder =
                sandboxConnectionDryRunCommandPackageEchoReceiptBuilder;
        this.sandboxConnectionPrecheckPacketEchoReceiptBuilder =
                sandboxConnectionPrecheckPacketEchoReceiptBuilder;
        this.sandboxConnectionDisabledAdapterClientPrecheckEchoReceiptBuilder =
                sandboxConnectionDisabledAdapterClientPrecheckEchoReceiptBuilder;
        this.sandboxConnectionFakeTransportDryRunPacketEchoMarkerBuilder =
                sandboxConnectionFakeTransportDryRunPacketEchoMarkerBuilder;
        this.sandboxEndpointHandlePreflightEchoMarkerBuilder =
                sandboxEndpointHandlePreflightEchoMarkerBuilder;
        this.sandboxEndpointCredentialResolverDecisionEchoMarkerBuilder =
                sandboxEndpointCredentialResolverDecisionEchoMarkerBuilder;
        this.sandboxEndpointCredentialResolverDisabledPrecheckEchoMarkerBuilder =
                sandboxEndpointCredentialResolverDisabledPrecheckEchoMarkerBuilder;
        this.sandboxEndpointCredentialResolverTestOnlyShellEchoMarkerBuilder =
                sandboxEndpointCredentialResolverTestOnlyShellEchoMarkerBuilder;
        this.sandboxEndpointCredentialResolverFakeShellArchiveEchoReceiptBuilder =
                sandboxEndpointCredentialResolverFakeShellArchiveEchoReceiptBuilder;
        this.sandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceiptBuilder =
                sandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceiptBuilder;
        this.sandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceiptBuilder =
                sandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceiptBuilder;
        this.sandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceiptBuilder =
                sandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceiptBuilder;
        this.sandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoReceiptBuilder =
                sandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoReceiptBuilder;
        this.sandboxEndpointCredentialResolverImplementationPlanEchoReceiptBuilder =
                sandboxEndpointCredentialResolverImplementationPlanEchoReceiptBuilder;
        this.sandboxEndpointCredentialResolverExecutionDeniedEchoReceiptBuilder =
                sandboxEndpointCredentialResolverExecutionDeniedEchoReceiptBuilder;
        this.sandboxEndpointCredentialResolverDisabledRuntimeShellHandoffEchoReceiptBuilder =
                sandboxEndpointCredentialResolverDisabledRuntimeShellHandoffEchoReceiptBuilder;
        this.sandboxEndpointCredentialResolverDisabledRuntimeShellCandidateGateEchoReceiptBuilder =
                sandboxEndpointCredentialResolverDisabledRuntimeShellCandidateGateEchoReceiptBuilder;
        this.sandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoReceiptBuilder =
                sandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoReceiptBuilder;
        this.sandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoReceiptBuilder =
                sandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoReceiptBuilder;
        this.verificationContributions = List.of(
                contribution(
                        sandboxAdapterApprovalSchemaGuardReceiptBuilder::warningDigestWarningInputNames,
                        sandboxAdapterApprovalSchemaGuardReceiptBuilder::warningDigestBoundaryInputNames,
                        sandboxAdapterApprovalSchemaGuardReceiptBuilder::proofClaims,
                        () -> sandboxAdapterApprovalSchemaGuardReceiptBuilder.nodeVerificationActions().stream()
                                .filter(action -> !("Verify managedAuditSandboxAdapterApprovalSchemaGuardReceipt"
                                        + ".qualityGateBoundary.builderOrHelperSplitApplied=true").equals(action))
                                .toList()
                ),
                contribution(sandboxConnectionOperatorHandoffMarkerBuilder::warningDigestWarningInputNames,
                        sandboxConnectionOperatorHandoffMarkerBuilder::warningDigestBoundaryInputNames,
                        sandboxConnectionOperatorHandoffMarkerBuilder::proofClaims,
                        sandboxConnectionOperatorHandoffMarkerBuilder::nodeVerificationActions),
                contribution(sandboxConnectionPreflightEchoMarkerBuilder::warningDigestWarningInputNames,
                        sandboxConnectionPreflightEchoMarkerBuilder::warningDigestBoundaryInputNames,
                        sandboxConnectionPreflightEchoMarkerBuilder::proofClaims,
                        sandboxConnectionPreflightEchoMarkerBuilder::nodeVerificationActions),
                contribution(sandboxConnectionPreconditionReceiptBuilder::warningDigestWarningInputNames,
                        sandboxConnectionPreconditionReceiptBuilder::warningDigestBoundaryInputNames,
                        sandboxConnectionPreconditionReceiptBuilder::proofClaims,
                        sandboxConnectionPreconditionReceiptBuilder::nodeVerificationActions),
                contribution(sandboxConnectionDryRunEnvelopeEchoReceiptBuilder::warningDigestWarningInputNames,
                        sandboxConnectionDryRunEnvelopeEchoReceiptBuilder::warningDigestBoundaryInputNames,
                        sandboxConnectionDryRunEnvelopeEchoReceiptBuilder::proofClaims,
                        sandboxConnectionDryRunEnvelopeEchoReceiptBuilder::nodeVerificationActions),
                contribution(sandboxConnectionOperatorWindowChecklistEchoReceiptBuilder::warningDigestWarningInputNames,
                        sandboxConnectionOperatorWindowChecklistEchoReceiptBuilder::warningDigestBoundaryInputNames,
                        sandboxConnectionOperatorWindowChecklistEchoReceiptBuilder::proofClaims,
                        sandboxConnectionOperatorWindowChecklistEchoReceiptBuilder::nodeVerificationActions),
                contribution(sandboxConnectionDryRunCommandPackageEchoReceiptBuilder::warningDigestWarningInputNames,
                        sandboxConnectionDryRunCommandPackageEchoReceiptBuilder::warningDigestBoundaryInputNames,
                        sandboxConnectionDryRunCommandPackageEchoReceiptBuilder::proofClaims,
                        sandboxConnectionDryRunCommandPackageEchoReceiptBuilder::nodeVerificationActions),
                contribution(sandboxConnectionPrecheckPacketEchoReceiptBuilder::warningDigestWarningInputNames,
                        sandboxConnectionPrecheckPacketEchoReceiptBuilder::warningDigestBoundaryInputNames,
                        sandboxConnectionPrecheckPacketEchoReceiptBuilder::proofClaims,
                        sandboxConnectionPrecheckPacketEchoReceiptBuilder::nodeVerificationActions),
                contribution(sandboxConnectionDisabledAdapterClientPrecheckEchoReceiptBuilder
                                ::warningDigestWarningInputNames,
                        sandboxConnectionDisabledAdapterClientPrecheckEchoReceiptBuilder
                                ::warningDigestBoundaryInputNames,
                        sandboxConnectionDisabledAdapterClientPrecheckEchoReceiptBuilder::proofClaims,
                        sandboxConnectionDisabledAdapterClientPrecheckEchoReceiptBuilder::nodeVerificationActions),
                contribution(sandboxConnectionFakeTransportDryRunPacketEchoMarkerBuilder
                                ::warningDigestWarningInputNames,
                        sandboxConnectionFakeTransportDryRunPacketEchoMarkerBuilder
                                ::warningDigestBoundaryInputNames,
                        sandboxConnectionFakeTransportDryRunPacketEchoMarkerBuilder::proofClaims,
                        sandboxConnectionFakeTransportDryRunPacketEchoMarkerBuilder::nodeVerificationActions),
                contribution(sandboxEndpointHandlePreflightEchoMarkerBuilder::warningDigestWarningInputNames,
                        sandboxEndpointHandlePreflightEchoMarkerBuilder::warningDigestBoundaryInputNames,
                        sandboxEndpointHandlePreflightEchoMarkerBuilder::proofClaims,
                        sandboxEndpointHandlePreflightEchoMarkerBuilder::nodeVerificationActions),
                contribution(sandboxEndpointCredentialResolverDecisionEchoMarkerBuilder
                                ::warningDigestWarningInputNames,
                        sandboxEndpointCredentialResolverDecisionEchoMarkerBuilder
                                ::warningDigestBoundaryInputNames,
                        sandboxEndpointCredentialResolverDecisionEchoMarkerBuilder::proofClaims,
                        sandboxEndpointCredentialResolverDecisionEchoMarkerBuilder::nodeVerificationActions),
                contribution(sandboxEndpointCredentialResolverDisabledPrecheckEchoMarkerBuilder
                                ::warningDigestWarningInputNames,
                        sandboxEndpointCredentialResolverDisabledPrecheckEchoMarkerBuilder
                                ::warningDigestBoundaryInputNames,
                        sandboxEndpointCredentialResolverDisabledPrecheckEchoMarkerBuilder::proofClaims,
                        sandboxEndpointCredentialResolverDisabledPrecheckEchoMarkerBuilder::nodeVerificationActions),
                contribution(sandboxEndpointCredentialResolverTestOnlyShellEchoMarkerBuilder
                                ::warningDigestWarningInputNames,
                        sandboxEndpointCredentialResolverTestOnlyShellEchoMarkerBuilder
                                ::warningDigestBoundaryInputNames,
                        sandboxEndpointCredentialResolverTestOnlyShellEchoMarkerBuilder::proofClaims,
                        sandboxEndpointCredentialResolverTestOnlyShellEchoMarkerBuilder::nodeVerificationActions),
                contribution(sandboxEndpointCredentialResolverFakeShellArchiveEchoReceiptBuilder
                                ::warningDigestWarningInputNames,
                        sandboxEndpointCredentialResolverFakeShellArchiveEchoReceiptBuilder
                                ::warningDigestBoundaryInputNames,
                        sandboxEndpointCredentialResolverFakeShellArchiveEchoReceiptBuilder::proofClaims,
                        sandboxEndpointCredentialResolverFakeShellArchiveEchoReceiptBuilder::nodeVerificationActions),
                contribution(sandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceiptBuilder
                                ::warningDigestWarningInputNames,
                        sandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceiptBuilder
                                ::warningDigestBoundaryInputNames,
                        sandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceiptBuilder::proofClaims,
                        sandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceiptBuilder
                                ::nodeVerificationActions),
                contribution(sandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceiptBuilder
                                ::warningDigestWarningInputNames,
                        sandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceiptBuilder
                                ::warningDigestBoundaryInputNames,
                        sandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceiptBuilder::proofClaims,
                        sandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceiptBuilder
                                ::nodeVerificationActions),
                contribution(sandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceiptBuilder
                                ::warningDigestWarningInputNames,
                        sandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceiptBuilder
                                ::warningDigestBoundaryInputNames,
                        sandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceiptBuilder::proofClaims,
                        sandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceiptBuilder
                                ::nodeVerificationActions),
                contribution(sandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoReceiptBuilder
                                ::warningDigestWarningInputNames,
                        sandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoReceiptBuilder
                                ::warningDigestBoundaryInputNames,
                        sandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoReceiptBuilder
                                ::proofClaims,
                        sandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoReceiptBuilder
                                ::nodeVerificationActions),
                contribution(sandboxEndpointCredentialResolverImplementationPlanEchoReceiptBuilder
                                ::warningDigestWarningInputNames,
                        sandboxEndpointCredentialResolverImplementationPlanEchoReceiptBuilder
                                ::warningDigestBoundaryInputNames,
                        sandboxEndpointCredentialResolverImplementationPlanEchoReceiptBuilder
                                ::proofClaims,
                        sandboxEndpointCredentialResolverImplementationPlanEchoReceiptBuilder
                                ::nodeVerificationActions),
                contribution(sandboxEndpointCredentialResolverExecutionDeniedEchoReceiptBuilder
                                ::warningDigestWarningInputNames,
                        sandboxEndpointCredentialResolverExecutionDeniedEchoReceiptBuilder
                                ::warningDigestBoundaryInputNames,
                        sandboxEndpointCredentialResolverExecutionDeniedEchoReceiptBuilder
                                ::proofClaims,
                        sandboxEndpointCredentialResolverExecutionDeniedEchoReceiptBuilder
                                ::nodeVerificationActions),
                contribution(sandboxEndpointCredentialResolverDisabledRuntimeShellHandoffEchoReceiptBuilder
                                ::warningDigestWarningInputNames,
                        sandboxEndpointCredentialResolverDisabledRuntimeShellHandoffEchoReceiptBuilder
                                ::warningDigestBoundaryInputNames,
                        sandboxEndpointCredentialResolverDisabledRuntimeShellHandoffEchoReceiptBuilder
                                ::proofClaims,
                        sandboxEndpointCredentialResolverDisabledRuntimeShellHandoffEchoReceiptBuilder
                                ::nodeVerificationActions),
                contribution(sandboxEndpointCredentialResolverDisabledRuntimeShellCandidateGateEchoReceiptBuilder
                                ::warningDigestWarningInputNames,
                        sandboxEndpointCredentialResolverDisabledRuntimeShellCandidateGateEchoReceiptBuilder
                                ::warningDigestBoundaryInputNames,
                        sandboxEndpointCredentialResolverDisabledRuntimeShellCandidateGateEchoReceiptBuilder
                                ::proofClaims,
                        sandboxEndpointCredentialResolverDisabledRuntimeShellCandidateGateEchoReceiptBuilder
                                ::nodeVerificationActions),
                contribution(sandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoReceiptBuilder
                                ::warningDigestWarningInputNames,
                        sandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoReceiptBuilder
                                ::warningDigestBoundaryInputNames,
                        sandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoReceiptBuilder
                                ::proofClaims,
                        sandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoReceiptBuilder
                                ::nodeVerificationActions),
                contribution(sandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoReceiptBuilder
                                ::warningDigestWarningInputNames,
                        sandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoReceiptBuilder
                                ::warningDigestBoundaryInputNames,
                        sandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoReceiptBuilder
                                ::proofClaims,
                        sandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoReceiptBuilder
                                ::nodeVerificationActions)
        );
        this.warningDigestBuilder = new ReleaseApprovalVerificationWarningDigestBuilder(
                sandboxAdapterApprovalSchemaGuardReceiptBuilder,
                sandboxConnectionOperatorHandoffMarkerBuilder,
                sandboxConnectionPreflightEchoMarkerBuilder,
                sandboxConnectionPreconditionReceiptBuilder,
                sandboxConnectionDryRunEnvelopeEchoReceiptBuilder,
                sandboxConnectionOperatorWindowChecklistEchoReceiptBuilder,
                sandboxConnectionDryRunCommandPackageEchoReceiptBuilder,
                sandboxConnectionPrecheckPacketEchoReceiptBuilder,
                sandboxConnectionDisabledAdapterClientPrecheckEchoReceiptBuilder,
                sandboxConnectionFakeTransportDryRunPacketEchoMarkerBuilder,
                sandboxEndpointHandlePreflightEchoMarkerBuilder,
                sandboxEndpointCredentialResolverDecisionEchoMarkerBuilder,
                sandboxEndpointCredentialResolverDisabledPrecheckEchoMarkerBuilder,
                sandboxEndpointCredentialResolverTestOnlyShellEchoMarkerBuilder,
                sandboxEndpointCredentialResolverFakeShellArchiveEchoReceiptBuilder,
                sandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceiptBuilder,
                sandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceiptBuilder,
                sandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceiptBuilder,
                sandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoReceiptBuilder,
                sandboxEndpointCredentialResolverImplementationPlanEchoReceiptBuilder,
                sandboxEndpointCredentialResolverExecutionDeniedEchoReceiptBuilder,
                sandboxEndpointCredentialResolverDisabledRuntimeShellHandoffEchoReceiptBuilder,
                sandboxEndpointCredentialResolverDisabledRuntimeShellCandidateGateEchoReceiptBuilder,
                sandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoReceiptBuilder,
                sandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoReceiptBuilder
        );
    }

    ReleaseApprovalRehearsalResponseRecords.RehearsalVerificationHint build(
            ReleaseApprovalRehearsalResponseRecords.RehearsalRequestContext requestContext,
            ReleaseApprovalRehearsalResponseRecords.RehearsalOperatorWindowHint operatorWindowHint,
            ReleaseApprovalRehearsalResponseRecords.RehearsalCiEvidenceHint ciEvidenceHint,
            ReleaseApprovalRehearsalResponseRecords.RehearsalArtifactRetentionHint artifactRetentionHint,
            ReleaseApprovalRehearsalResponseRecords.RehearsalLiveReadinessHint liveReadinessHint,
            ReleaseApprovalRehearsalResponseRecords.RehearsalAuditPersistenceHandoffHint auditPersistenceHandoffHint,
            ReleaseApprovalRehearsalResponseRecords.RehearsalApprovalRecordHandoffHint approvalRecordHandoffHint,
            ReleaseApprovalRehearsalResponseRecords.RehearsalApprovalHandoffVerificationMarker
                    approvalHandoffVerificationMarker,
            ReleaseApprovalRehearsalResponseRecords.RehearsalManagedAuditAdapterBoundaryReceipt
                    managedAuditAdapterBoundaryReceipt,
            ReleaseApprovalRehearsalResponseRecords.RehearsalManagedAuditProductionAdapterPrerequisiteReceipt
                    managedAuditProductionAdapterPrerequisiteReceipt,
            ReleaseApprovalRehearsalResponseRecords.RehearsalOpsEvidenceServiceQualitySplitReceipt
                    opsEvidenceServiceQualitySplitReceipt,
            ReleaseApprovalRehearsalResponseRecords.RehearsalManagedAuditAdapterImplementationGuardReceipt
                    managedAuditAdapterImplementationGuardReceipt,
            ReleaseApprovalRehearsalResponseRecords.RehearsalManagedAuditExternalAdapterMigrationGuardReceipt
                    managedAuditExternalAdapterMigrationGuardReceipt,
            ReleaseApprovalRehearsalResponseRecords.RehearsalManagedAuditSandboxAdapterApprovalSchemaGuardReceipt
                    managedAuditSandboxAdapterApprovalSchemaGuardReceipt,
            ReleaseApprovalRehearsalResponseRecords.RehearsalManagedAuditSandboxConnectionOperatorHandoffMarker
                    managedAuditSandboxConnectionOperatorHandoffMarker,
            ReleaseApprovalRehearsalResponseRecords.RehearsalManagedAuditSandboxConnectionPreflightEchoMarker
                    managedAuditSandboxConnectionPreflightEchoMarker,
            ReleaseApprovalRehearsalResponseRecords.RehearsalManagedAuditSandboxConnectionPreconditionReceipt
                    managedAuditSandboxConnectionPreconditionReceipt,
            ReleaseApprovalRehearsalResponseRecords.RehearsalManagedAuditSandboxConnectionDryRunEnvelopeEchoReceipt
                    managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt,
            ReleaseApprovalRehearsalResponseRecords.RehearsalManagedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt
                    managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt,
            ReleaseApprovalRehearsalResponseRecords.RehearsalManagedAuditSandboxConnectionDryRunCommandPackageEchoReceipt
                    managedAuditSandboxConnectionDryRunCommandPackageEchoReceipt,
            ReleaseApprovalRehearsalResponseRecords.RehearsalManagedAuditSandboxConnectionPrecheckPacketEchoReceipt
                    managedAuditSandboxConnectionPrecheckPacketEchoReceipt,
            ReleaseApprovalSandboxConnectionAdapterPreflightEchoRecords.RehearsalManagedAuditSandboxConnectionDisabledAdapterClientPrecheckEchoReceipt
                    managedAuditSandboxConnectionDisabledAdapterClientPrecheckEchoReceipt,
            ReleaseApprovalSandboxConnectionAdapterPreflightEchoRecords.RehearsalManagedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker
                    managedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker,
            ReleaseApprovalSandboxConnectionAdapterPreflightEchoRecords.RehearsalManagedAuditSandboxEndpointHandlePreflightEchoMarker
                    managedAuditSandboxEndpointHandlePreflightEchoMarker,
            ReleaseApprovalSandboxEndpointCredentialResolverDecisionEchoRecords.RehearsalManagedAuditSandboxEndpointCredentialResolverDecisionEchoMarker
                    managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker,
            RehearsalManagedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker
                    managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker,
            RehearsalManagedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarker
                    managedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarker,
            RehearsalManagedAuditSandboxEndpointCredentialResolverFakeShellArchiveEchoReceipt
                    managedAuditSandboxEndpointCredentialResolverFakeShellArchiveEchoReceipt,
            RehearsalManagedAuditSandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceipt
                    managedAuditSandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceipt,
            RehearsalManagedAuditSandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceipt
                    managedAuditSandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceipt,
            RehearsalManagedAuditSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceipt
                    managedAuditSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceipt,
            RehearsalManagedAuditSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoReceipt
                    managedAuditSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoReceipt,
            RehearsalManagedAuditSandboxEndpointCredentialResolverImplementationPlanEchoReceipt
                    managedAuditSandboxEndpointCredentialResolverImplementationPlanEchoReceipt,
            RehearsalManagedAuditSandboxEndpointCredentialResolverExecutionDeniedEchoReceipt
                    managedAuditSandboxEndpointCredentialResolverExecutionDeniedEchoReceipt,
            RehearsalManagedAuditSandboxEndpointCredentialResolverDisabledRuntimeShellHandoffEchoReceipt
                    managedAuditSandboxEndpointCredentialResolverDisabledRuntimeShellHandoffEchoReceipt,
            RehearsalManagedAuditSandboxEndpointCredentialResolverDisabledRuntimeShellCandidateGateEchoReceipt
                    managedAuditSandboxEndpointCredentialResolverDisabledRuntimeShellCandidateGateEchoReceipt,
            RehearsalManagedAuditSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoReceipt
                    managedAuditSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoReceipt,
            RehearsalManagedAuditSandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoReceipt
                    managedAuditSandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoReceipt,
            ReleaseApprovalRehearsalResponseRecords.RehearsalFailureTaxonomy failureTaxonomy,
            ReleaseApprovalRehearsalResponseRecords.ExecutionBoundaries executionBoundaries
    ) {
        return new ReleaseApprovalRehearsalResponseRecords.RehearsalVerificationHint(
                OpsEvidenceService.RELEASE_APPROVAL_REHEARSAL_VERIFICATION_HINT_VERSION,
                OpsEvidenceService.RELEASE_APPROVAL_REHEARSAL_RESPONSE_SCHEMA_VERSION,
                warningDigestBuilder.build(
                        requestContext,
                        operatorWindowHint,
                        ciEvidenceHint,
                        artifactRetentionHint,
                        liveReadinessHint,
                        auditPersistenceHandoffHint,
                        approvalRecordHandoffHint,
                        approvalHandoffVerificationMarker,
                        managedAuditAdapterBoundaryReceipt,
                        managedAuditProductionAdapterPrerequisiteReceipt,
                        opsEvidenceServiceQualitySplitReceipt,
                        managedAuditAdapterImplementationGuardReceipt,
                        managedAuditExternalAdapterMigrationGuardReceipt,
                        managedAuditSandboxAdapterApprovalSchemaGuardReceipt,
                        managedAuditSandboxConnectionOperatorHandoffMarker,
                        managedAuditSandboxConnectionPreflightEchoMarker,
                        managedAuditSandboxConnectionPreconditionReceipt,
                        managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt,
                        managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt,
                        managedAuditSandboxConnectionDryRunCommandPackageEchoReceipt,
                        managedAuditSandboxConnectionPrecheckPacketEchoReceipt,
                        managedAuditSandboxConnectionDisabledAdapterClientPrecheckEchoReceipt,
                        managedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker,
                        managedAuditSandboxEndpointHandlePreflightEchoMarker,
                        managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker,
                        managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker,
                        managedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarker,
                        managedAuditSandboxEndpointCredentialResolverFakeShellArchiveEchoReceipt,
                        managedAuditSandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceipt,
                        managedAuditSandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceipt,
                        managedAuditSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceipt,
                        managedAuditSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoReceipt,
                        managedAuditSandboxEndpointCredentialResolverImplementationPlanEchoReceipt,
                        managedAuditSandboxEndpointCredentialResolverExecutionDeniedEchoReceipt,
                        managedAuditSandboxEndpointCredentialResolverDisabledRuntimeShellHandoffEchoReceipt,
                        managedAuditSandboxEndpointCredentialResolverDisabledRuntimeShellCandidateGateEchoReceipt,
                        managedAuditSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoReceipt,
                        managedAuditSandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoReceipt,
                        failureTaxonomy,
                        executionBoundaries
                ),
                "NO_LEDGER_WRITE_PROOF_BY_RESPONSE_FIELDS",
                noLedgerWriteProved(
                        requestContext,
                        ciEvidenceHint,
                        artifactRetentionHint,
                        liveReadinessHint,
                        auditPersistenceHandoffHint,
                        approvalRecordHandoffHint,
                        approvalHandoffVerificationMarker,
                        managedAuditAdapterBoundaryReceipt,
                        managedAuditProductionAdapterPrerequisiteReceipt,
                        opsEvidenceServiceQualitySplitReceipt,
                        managedAuditAdapterImplementationGuardReceipt,
                        managedAuditExternalAdapterMigrationGuardReceipt,
                        managedAuditSandboxAdapterApprovalSchemaGuardReceipt,
                        managedAuditSandboxConnectionOperatorHandoffMarker,
                        managedAuditSandboxConnectionPreflightEchoMarker,
                        managedAuditSandboxConnectionPreconditionReceipt,
                        managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt,
                        managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt,
                        managedAuditSandboxConnectionDryRunCommandPackageEchoReceipt,
                        managedAuditSandboxConnectionPrecheckPacketEchoReceipt,
                        managedAuditSandboxConnectionDisabledAdapterClientPrecheckEchoReceipt,
                        managedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker,
                        managedAuditSandboxEndpointHandlePreflightEchoMarker,
                        managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker,
                        managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker,
                        managedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarker,
                        managedAuditSandboxEndpointCredentialResolverFakeShellArchiveEchoReceipt,
                        managedAuditSandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceipt,
                        managedAuditSandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceipt,
                        managedAuditSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceipt,
                        managedAuditSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoReceipt,
                        managedAuditSandboxEndpointCredentialResolverImplementationPlanEchoReceipt,
                        managedAuditSandboxEndpointCredentialResolverExecutionDeniedEchoReceipt,
                        managedAuditSandboxEndpointCredentialResolverDisabledRuntimeShellHandoffEchoReceipt,
                        managedAuditSandboxEndpointCredentialResolverDisabledRuntimeShellCandidateGateEchoReceipt,
                        managedAuditSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoReceipt,
                        managedAuditSandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoReceipt,
                        executionBoundaries
                ),
                false,
                ReleaseApprovalVerificationHintCatalog.schemaFields(),
                warningDigestInputs(),
                proofClaims(),
                nodeVerificationActions()
        );
    }

    private List<String> warningDigestInputs() {
        List<String> inputs = new ArrayList<>(ReleaseApprovalVerificationHintCatalog.warningDigestWarningInputNames());
        verificationContributions.forEach(contribution ->
                inputs.addAll(contribution.warningDigestWarningInputValues()));
        inputs.addAll(ReleaseApprovalVerificationHintCatalog.warningDigestBoundaryInputNames());
        verificationContributions.forEach(contribution ->
                inputs.addAll(contribution.warningDigestBoundaryInputValues()));
        inputs.add(ReleaseApprovalVerificationHintCatalog.finalWarningDigestBoundaryInputName());
        return inputs;
    }

    private List<String> proofClaims() {
        List<String> claims = new ArrayList<>(ReleaseApprovalVerificationHintCatalog.proofClaims());
        verificationContributions.forEach(contribution -> claims.addAll(contribution.proofClaimValues()));
        claims.addAll(ReleaseApprovalVerificationHintCatalog.closingProofClaims());
        return claims;
    }

    private boolean noLedgerWriteProved(
            ReleaseApprovalRehearsalResponseRecords.RehearsalRequestContext requestContext,
            ReleaseApprovalRehearsalResponseRecords.RehearsalCiEvidenceHint ciEvidenceHint,
            ReleaseApprovalRehearsalResponseRecords.RehearsalArtifactRetentionHint artifactRetentionHint,
            ReleaseApprovalRehearsalResponseRecords.RehearsalLiveReadinessHint liveReadinessHint,
            ReleaseApprovalRehearsalResponseRecords.RehearsalAuditPersistenceHandoffHint auditPersistenceHandoffHint,
            ReleaseApprovalRehearsalResponseRecords.RehearsalApprovalRecordHandoffHint approvalRecordHandoffHint,
            ReleaseApprovalRehearsalResponseRecords.RehearsalApprovalHandoffVerificationMarker
                    approvalHandoffVerificationMarker,
            ReleaseApprovalRehearsalResponseRecords.RehearsalManagedAuditAdapterBoundaryReceipt
                    managedAuditAdapterBoundaryReceipt,
            ReleaseApprovalRehearsalResponseRecords.RehearsalManagedAuditProductionAdapterPrerequisiteReceipt
                    managedAuditProductionAdapterPrerequisiteReceipt,
            ReleaseApprovalRehearsalResponseRecords.RehearsalOpsEvidenceServiceQualitySplitReceipt
                    opsEvidenceServiceQualitySplitReceipt,
            ReleaseApprovalRehearsalResponseRecords.RehearsalManagedAuditAdapterImplementationGuardReceipt
                    managedAuditAdapterImplementationGuardReceipt,
            ReleaseApprovalRehearsalResponseRecords.RehearsalManagedAuditExternalAdapterMigrationGuardReceipt
                    managedAuditExternalAdapterMigrationGuardReceipt,
            ReleaseApprovalRehearsalResponseRecords.RehearsalManagedAuditSandboxAdapterApprovalSchemaGuardReceipt
                    managedAuditSandboxAdapterApprovalSchemaGuardReceipt,
            ReleaseApprovalRehearsalResponseRecords.RehearsalManagedAuditSandboxConnectionOperatorHandoffMarker
                    managedAuditSandboxConnectionOperatorHandoffMarker,
            ReleaseApprovalRehearsalResponseRecords.RehearsalManagedAuditSandboxConnectionPreflightEchoMarker
                    managedAuditSandboxConnectionPreflightEchoMarker,
            ReleaseApprovalRehearsalResponseRecords.RehearsalManagedAuditSandboxConnectionPreconditionReceipt
                    managedAuditSandboxConnectionPreconditionReceipt,
            ReleaseApprovalRehearsalResponseRecords.RehearsalManagedAuditSandboxConnectionDryRunEnvelopeEchoReceipt
                    managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt,
            ReleaseApprovalRehearsalResponseRecords.RehearsalManagedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt
                    managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt,
            ReleaseApprovalRehearsalResponseRecords.RehearsalManagedAuditSandboxConnectionDryRunCommandPackageEchoReceipt
                    managedAuditSandboxConnectionDryRunCommandPackageEchoReceipt,
            ReleaseApprovalRehearsalResponseRecords.RehearsalManagedAuditSandboxConnectionPrecheckPacketEchoReceipt
                    managedAuditSandboxConnectionPrecheckPacketEchoReceipt,
            ReleaseApprovalSandboxConnectionAdapterPreflightEchoRecords.RehearsalManagedAuditSandboxConnectionDisabledAdapterClientPrecheckEchoReceipt
                    managedAuditSandboxConnectionDisabledAdapterClientPrecheckEchoReceipt,
            ReleaseApprovalSandboxConnectionAdapterPreflightEchoRecords.RehearsalManagedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker
                    managedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker,
            ReleaseApprovalSandboxConnectionAdapterPreflightEchoRecords.RehearsalManagedAuditSandboxEndpointHandlePreflightEchoMarker
                    managedAuditSandboxEndpointHandlePreflightEchoMarker,
            ReleaseApprovalSandboxEndpointCredentialResolverDecisionEchoRecords.RehearsalManagedAuditSandboxEndpointCredentialResolverDecisionEchoMarker
                    managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker,
            RehearsalManagedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker
                    managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker,
            RehearsalManagedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarker
                    managedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarker,
            RehearsalManagedAuditSandboxEndpointCredentialResolverFakeShellArchiveEchoReceipt
                    managedAuditSandboxEndpointCredentialResolverFakeShellArchiveEchoReceipt,
            RehearsalManagedAuditSandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceipt
                    managedAuditSandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceipt,
            RehearsalManagedAuditSandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceipt
                    managedAuditSandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceipt,
            RehearsalManagedAuditSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceipt
                    managedAuditSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceipt,
            RehearsalManagedAuditSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoReceipt
                    managedAuditSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoReceipt,
            RehearsalManagedAuditSandboxEndpointCredentialResolverImplementationPlanEchoReceipt
                    managedAuditSandboxEndpointCredentialResolverImplementationPlanEchoReceipt,
            RehearsalManagedAuditSandboxEndpointCredentialResolverExecutionDeniedEchoReceipt
                    managedAuditSandboxEndpointCredentialResolverExecutionDeniedEchoReceipt,
            RehearsalManagedAuditSandboxEndpointCredentialResolverDisabledRuntimeShellHandoffEchoReceipt
                    managedAuditSandboxEndpointCredentialResolverDisabledRuntimeShellHandoffEchoReceipt,
            RehearsalManagedAuditSandboxEndpointCredentialResolverDisabledRuntimeShellCandidateGateEchoReceipt
                    managedAuditSandboxEndpointCredentialResolverDisabledRuntimeShellCandidateGateEchoReceipt,
            RehearsalManagedAuditSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoReceipt
                    managedAuditSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoReceipt,
            RehearsalManagedAuditSandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoReceipt
                    managedAuditSandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoReceipt,
            ReleaseApprovalRehearsalResponseRecords.ExecutionBoundaries executionBoundaries
    ) {
        return !requestContext.approvalLedgerWritten()
                && ciEvidenceHint.noLedgerWriteProved()
                && artifactRetentionHint.javaRetentionFixtureReadOnly()
                && !artifactRetentionHint.ciArtifactUploadedByJava()
                && !artifactRetentionHint.githubArtifactAccessedByJava()
                && liveReadinessHint.readOnlyEndpointReady()
                && !liveReadinessHint.runtimeSmokeExecutedByJava()
                && !liveReadinessHint.javaStartedProcessForNode()
                && auditPersistenceHandoffHint.javaAuditSourceReadOnly()
                && !auditPersistenceHandoffHint.javaLedgerWriteAllowed()
                && !auditPersistenceHandoffHint.javaManagedAuditWriteAllowed()
                && !auditPersistenceHandoffHint.javaExternalAuditSystemAccessed()
                && approvalRecordHandoffHint.approvalRecordFixtureReadOnly()
                && !approvalRecordHandoffHint.javaApprovalDecisionCreated()
                && !approvalRecordHandoffHint.javaApprovalLedgerWritten()
                && !approvalRecordHandoffHint.javaApprovalRecordPersisted()
                && !approvalHandoffVerificationMarker.nodeV211RealApprovalDecisionCreated()
                && !approvalHandoffVerificationMarker.nodeV211RealApprovalLedgerWritten()
                && !approvalHandoffVerificationMarker.nodeV211ProductionAuditRecordAllowed()
                && !managedAuditAdapterBoundaryReceipt.nodeV215MayConnectManagedAudit()
                && !managedAuditAdapterBoundaryReceipt.nodeV215MayCreateApprovalDecision()
                && !managedAuditAdapterBoundaryReceipt.nodeV215MayWriteApprovalLedger()
                && !managedAuditAdapterBoundaryReceipt.nodeV215MayPersistApprovalRecord()
                && !managedAuditAdapterBoundaryReceipt.nodeV215MayExecuteSql()
                && !managedAuditAdapterBoundaryReceipt.nodeV215MayTriggerDeployment()
                && !managedAuditAdapterBoundaryReceipt.nodeV215MayTriggerRollback()
                && !managedAuditAdapterBoundaryReceipt.nodeV215MayExecuteRestore()
                && !managedAuditAdapterBoundaryReceipt.javaApprovalDecisionCreated()
                && !managedAuditAdapterBoundaryReceipt.javaApprovalLedgerWritten()
                && !managedAuditAdapterBoundaryReceipt.javaApprovalRecordPersisted()
                && !managedAuditAdapterBoundaryReceipt.javaManagedAuditWriteExecuted()
                && !managedAuditProductionAdapterPrerequisiteReceipt.javaCreatesApprovalDecision()
                && !managedAuditProductionAdapterPrerequisiteReceipt.javaWritesApprovalLedger()
                && !managedAuditProductionAdapterPrerequisiteReceipt.javaPersistsApprovalRecord()
                && !managedAuditProductionAdapterPrerequisiteReceipt.javaWritesManagedAuditStore()
                && !managedAuditProductionAdapterPrerequisiteReceipt.javaExecutesSql()
                && !managedAuditProductionAdapterPrerequisiteReceipt.javaTriggersDeployment()
                && !managedAuditProductionAdapterPrerequisiteReceipt.javaTriggersRollback()
                && !managedAuditProductionAdapterPrerequisiteReceipt.javaExecutesRestore()
                && !managedAuditProductionAdapterPrerequisiteReceipt.nodeV217MayConnectManagedAudit()
                && !managedAuditProductionAdapterPrerequisiteReceipt.nodeV217MayWriteApprovalLedger()
                && !managedAuditProductionAdapterPrerequisiteReceipt.nodeV217MayExecuteSql()
                && !managedAuditProductionAdapterPrerequisiteReceipt.nodeV217MayTriggerDeployment()
                && !managedAuditProductionAdapterPrerequisiteReceipt.nodeV217MayTriggerRollback()
                && !managedAuditProductionAdapterPrerequisiteReceipt.nodeV217MayExecuteRestore()
                && !opsEvidenceServiceQualitySplitReceipt.apiShapeChanged()
                && !opsEvidenceServiceQualitySplitReceipt.approvalDecisionCreated()
                && !opsEvidenceServiceQualitySplitReceipt.approvalLedgerWritten()
                && !opsEvidenceServiceQualitySplitReceipt.approvalRecordPersisted()
                && !opsEvidenceServiceQualitySplitReceipt.managedAuditStoreWritten()
                && !opsEvidenceServiceQualitySplitReceipt.sqlExecuted()
                && !opsEvidenceServiceQualitySplitReceipt.deploymentTriggered()
                && !opsEvidenceServiceQualitySplitReceipt.rollbackTriggered()
                && !opsEvidenceServiceQualitySplitReceipt.restoreExecuted()
                && !managedAuditAdapterImplementationGuardReceipt.nodeV220AppendWritten()
                && !managedAuditAdapterImplementationGuardReceipt.nodeV220QueryReturnedRecords()
                && !managedAuditAdapterImplementationGuardReceipt.nodeV220ExternalManagedAuditAccessed()
                && !managedAuditAdapterImplementationGuardReceipt.nodeV220LocalDryRunWritePerformed()
                && !managedAuditAdapterImplementationGuardReceipt.javaApprovalDecisionCreated()
                && !managedAuditAdapterImplementationGuardReceipt.javaApprovalLedgerWritten()
                && !managedAuditAdapterImplementationGuardReceipt.javaApprovalRecordPersisted()
                && !managedAuditAdapterImplementationGuardReceipt.javaManagedAuditStoreWritten()
                && !managedAuditAdapterImplementationGuardReceipt.javaSqlExecuted()
                && !managedAuditAdapterImplementationGuardReceipt.javaDeploymentTriggered()
                && !managedAuditAdapterImplementationGuardReceipt.javaRollbackTriggered()
                && !managedAuditAdapterImplementationGuardReceipt.javaRestoreExecuted()
                && !managedAuditExternalAdapterMigrationGuardReceipt.nodeV222SourceEndpointRerunPerformed()
                && !managedAuditExternalAdapterMigrationGuardReceipt.nodeV222AdditionalLocalDryRunWritePerformed()
                && !managedAuditExternalAdapterMigrationGuardReceipt.nodeV222ConnectsManagedAudit()
                && !managedAuditExternalAdapterMigrationGuardReceipt.credentialValueReadByJava()
                && !managedAuditExternalAdapterMigrationGuardReceipt.credentialValueStoredByJava()
                && !managedAuditExternalAdapterMigrationGuardReceipt.externalManagedAuditConnectionOpened()
                && !managedAuditExternalAdapterMigrationGuardReceipt.externalManagedAuditSchemaMigrated()
                && !managedAuditExternalAdapterMigrationGuardReceipt.javaApprovalDecisionCreated()
                && !managedAuditExternalAdapterMigrationGuardReceipt.javaApprovalLedgerWritten()
                && !managedAuditExternalAdapterMigrationGuardReceipt.javaApprovalRecordPersisted()
                && !managedAuditExternalAdapterMigrationGuardReceipt.javaManagedAuditStoreWritten()
                && !managedAuditExternalAdapterMigrationGuardReceipt.javaSqlExecuted()
                && !managedAuditExternalAdapterMigrationGuardReceipt.javaDeploymentTriggered()
                && !managedAuditExternalAdapterMigrationGuardReceipt.javaRollbackTriggered()
                && !managedAuditExternalAdapterMigrationGuardReceipt.javaRestoreExecuted()
                && sandboxAdapterApprovalSchemaGuardReceiptBuilder.noWriteCredentialConnectionOrSchemaEffectProved(
                        managedAuditSandboxAdapterApprovalSchemaGuardReceipt
                )
                && sandboxConnectionOperatorHandoffMarkerBuilder
                .noWriteCredentialConnectionSchemaRollbackOrServiceStartProved(
                        managedAuditSandboxConnectionOperatorHandoffMarker
                )
                && sandboxConnectionPreflightEchoMarkerBuilder
                .noWriteCredentialConnectionSchemaRollbackOrServiceStartProved(
                        managedAuditSandboxConnectionPreflightEchoMarker
                )
                && sandboxConnectionPreconditionReceiptBuilder
                .noWriteCredentialConnectionSchemaRollbackOrServiceStartProved(
                        managedAuditSandboxConnectionPreconditionReceipt
                )
                && sandboxConnectionDryRunEnvelopeEchoReceiptBuilder
                .noWriteCredentialConnectionSchemaRollbackOrServiceStartProved(
                        managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt
                )
                && sandboxConnectionOperatorWindowChecklistEchoReceiptBuilder
                .noWriteCredentialConnectionSchemaRollbackOrServiceStartProved(
                        managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt
                )
                && sandboxConnectionDryRunCommandPackageEchoReceiptBuilder
                .noWriteCredentialConnectionSchemaRollbackOrServiceStartProved(
                        managedAuditSandboxConnectionDryRunCommandPackageEchoReceipt
                )
                && sandboxConnectionPrecheckPacketEchoReceiptBuilder
                .noWriteCredentialConnectionSchemaRollbackOrServiceStartProved(
                        managedAuditSandboxConnectionPrecheckPacketEchoReceipt
                )
                && sandboxConnectionDisabledAdapterClientPrecheckEchoReceiptBuilder
                .noWriteCredentialConnectionSchemaRollbackOrServiceStartProved(
                        managedAuditSandboxConnectionDisabledAdapterClientPrecheckEchoReceipt
                )
                && sandboxConnectionFakeTransportDryRunPacketEchoMarkerBuilder
                .noCredentialConnectionWriteOrAutoStartProved(
                        managedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker
                )
                && sandboxEndpointHandlePreflightEchoMarkerBuilder
                .noCredentialConnectionWriteOrAutoStartProved(
                        managedAuditSandboxEndpointHandlePreflightEchoMarker
                )
                && sandboxEndpointCredentialResolverDecisionEchoMarkerBuilder
                .noCredentialConnectionWriteOrAutoStartProved(
                        managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker
                )
                && sandboxEndpointCredentialResolverDisabledPrecheckEchoMarkerBuilder
                .noCredentialConnectionWriteOrAutoStartProved(
                        managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker
                )
                && sandboxEndpointCredentialResolverTestOnlyShellEchoMarkerBuilder
                .noCredentialConnectionWriteOrAutoStartProved(
                        managedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarker
                )
                && sandboxEndpointCredentialResolverFakeShellArchiveEchoReceiptBuilder
                .noCredentialConnectionWriteOrAutoStartProved(
                        managedAuditSandboxEndpointCredentialResolverFakeShellArchiveEchoReceipt
                )
                && sandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceiptBuilder
                .noCredentialConnectionWriteOrAutoStartProved(
                        managedAuditSandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceipt
                )
                && sandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceiptBuilder
                .noCredentialConnectionWriteOrAutoStartProved(
                        managedAuditSandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceipt
                )
                && sandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceiptBuilder
                .noCredentialConnectionWriteOrAutoStartProved(
                        managedAuditSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceipt
                )
                && sandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoReceiptBuilder
                .noCredentialConnectionWriteOrAutoStartProved(
                        managedAuditSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoReceipt
                )
                && sandboxEndpointCredentialResolverImplementationPlanEchoReceiptBuilder
                .noCredentialConnectionWriteOrAutoStartProved(
                        managedAuditSandboxEndpointCredentialResolverImplementationPlanEchoReceipt
                )
                && sandboxEndpointCredentialResolverExecutionDeniedEchoReceiptBuilder
                .noCredentialConnectionWriteOrAutoStartProved(
                        managedAuditSandboxEndpointCredentialResolverExecutionDeniedEchoReceipt
                )
                && sandboxEndpointCredentialResolverDisabledRuntimeShellHandoffEchoReceiptBuilder
                .noCredentialConnectionWriteOrAutoStartProved(
                        managedAuditSandboxEndpointCredentialResolverDisabledRuntimeShellHandoffEchoReceipt
                )
                && sandboxEndpointCredentialResolverDisabledRuntimeShellCandidateGateEchoReceiptBuilder
                .noCredentialConnectionWriteOrAutoStartProved(
                        managedAuditSandboxEndpointCredentialResolverDisabledRuntimeShellCandidateGateEchoReceipt
                )
                && sandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoReceiptBuilder
                .noCredentialConnectionWriteOrAutoStartProved(
                        managedAuditSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoReceipt
                )
                && sandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoReceiptBuilder
                .noCredentialConnectionWriteOrAutoStartProved(
                        managedAuditSandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoReceipt
                )
                && !executionBoundaries.nodeMayCreateApprovalDecision()
                && !executionBoundaries.nodeMayWriteApprovalLedger();
    }

    private List<String> nodeVerificationActions() {
        List<String> actions = new ArrayList<>(ReleaseApprovalVerificationHintCatalog.nodeVerificationActions());
        verificationContributions.forEach(contribution ->
                actions.addAll(contribution.nodeVerificationActionValues()));
        actions.addAll(ReleaseApprovalVerificationHintCatalog.closingNodeVerificationActions());
        return actions;
    }

    private static ReleaseApprovalVerificationHintContribution contribution(
            Supplier<List<String>> warningDigestWarningInputNames,
            Supplier<List<String>> warningDigestBoundaryInputNames,
            Supplier<List<String>> proofClaims,
            Supplier<List<String>> nodeVerificationActions
    ) {
        return new ReleaseApprovalVerificationHintContribution(
                warningDigestWarningInputNames,
                warningDigestBoundaryInputNames,
                proofClaims,
                nodeVerificationActions
        );
    }
}
