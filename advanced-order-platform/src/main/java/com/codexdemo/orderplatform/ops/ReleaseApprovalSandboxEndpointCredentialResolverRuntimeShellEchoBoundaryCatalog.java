package com.codexdemo.orderplatform.ops;

import static com.codexdemo.orderplatform.ops.ReleaseApprovalEchoMarkerSupport.boundaryInput;
import static com.codexdemo.orderplatform.ops.ReleaseApprovalEchoMarkerSupport.boundaryLines;

import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoRecords
        .RehearsalManagedAuditSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoReceipt;
import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoRecords
        .RehearsalRuntimeShellDecisionRecordSideEffectBoundary;
import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoRecords
        .RehearsalManagedAuditSandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoReceipt;
import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoRecords
        .RehearsalRuntimeShellPostDecisionPlanIntakeSideEffectBoundary;
import java.util.List;

final class ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellEchoBoundaryCatalog {

    private static final List<String> DECISION_RECORD_WARNING_DIGEST_BOUNDARY_INPUT_NAMES =
            ReleaseApprovalEchoMarkerSupport.boundaryInputNames(
                    "sandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoReceiptDigest",
                    "sandboxEndpointCredentialResolverRuntimeShellDecisionRecordState",
                    "sandboxEndpointCredentialResolverRuntimeShellDecisionRecordDecision",
                    "sandboxEndpointCredentialResolverRuntimeShellDecisionRecordRequiredEvidenceCount",
                    "sandboxEndpointCredentialResolverRuntimeShellDecisionRecordNoGoConditionCount",
                    "sandboxEndpointCredentialResolverRuntimeShellDecisionRecordReadyForNodeV300",
                    "sandboxEndpointCredentialResolverRuntimeShellDecisionRecordRuntimeImplemented",
                    "sandboxEndpointCredentialResolverRuntimeShellDecisionRecordRuntimeInvocationAllowed",
                    "sandboxEndpointCredentialResolverRuntimeShellDecisionRecordCredentialValueRead",
                    "sandboxEndpointCredentialResolverRuntimeShellDecisionRecordRawEndpointUrlParsed",
                    "sandboxEndpointCredentialResolverRuntimeShellDecisionRecordExternalRequestSent",
                    "sandboxEndpointCredentialResolverRuntimeShellDecisionRecordSecretProviderInstantiated",
                    "sandboxEndpointCredentialResolverRuntimeShellDecisionRecordResolverClientInstantiated",
                    "sandboxEndpointCredentialResolverRuntimeShellDecisionRecordApprovalLedgerWritten",
                    "sandboxEndpointCredentialResolverRuntimeShellDecisionRecordSqlExecuted",
                    "sandboxEndpointCredentialResolverRuntimeShellDecisionRecordSchemaMigrationExecuted",
                    "sandboxEndpointCredentialResolverRuntimeShellDecisionRecordAutomaticUpstreamStart"
            );

    private static final List<String> POST_DECISION_PLAN_INTAKE_WARNING_DIGEST_BOUNDARY_INPUT_NAMES =
            ReleaseApprovalEchoMarkerSupport.boundaryInputNames(
                    "sandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoReceiptDigest",
                    "sandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeState",
                    "sandboxEndpointCredentialResolverRuntimeShellPostDecisionSelectedContinuationDecision",
                    "sandboxEndpointCredentialResolverRuntimeShellPostDecisionDecisionOptionCount",
                    "sandboxEndpointCredentialResolverRuntimeShellPostDecisionSelectedDecisionOptionCount",
                    "sandboxEndpointCredentialResolverRuntimeShellPostDecisionRejectedRuntimeImplementationOptionCount",
                    "sandboxEndpointCredentialResolverRuntimeShellPostDecisionReadyForNodeV302",
                    "sandboxEndpointCredentialResolverRuntimeShellPostDecisionRuntimeImplemented",
                    "sandboxEndpointCredentialResolverRuntimeShellPostDecisionRuntimeInvocationAllowed",
                    "sandboxEndpointCredentialResolverRuntimeShellPostDecisionCredentialValueRead",
                    "sandboxEndpointCredentialResolverRuntimeShellPostDecisionRawEndpointUrlParsed",
                    "sandboxEndpointCredentialResolverRuntimeShellPostDecisionExternalRequestSent",
                    "sandboxEndpointCredentialResolverRuntimeShellPostDecisionSecretProviderInstantiated",
                    "sandboxEndpointCredentialResolverRuntimeShellPostDecisionResolverClientInstantiated",
                    "sandboxEndpointCredentialResolverRuntimeShellPostDecisionApprovalLedgerWritten",
                    "sandboxEndpointCredentialResolverRuntimeShellPostDecisionSqlExecuted",
                    "sandboxEndpointCredentialResolverRuntimeShellPostDecisionSchemaMigrationExecuted",
                    "sandboxEndpointCredentialResolverRuntimeShellPostDecisionAutomaticUpstreamStart"
            );

    private ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellEchoBoundaryCatalog() {
    }

    static List<String> decisionRecordWarningDigestBoundaryInputNames() {
        return DECISION_RECORD_WARNING_DIGEST_BOUNDARY_INPUT_NAMES;
    }

    static List<String> postDecisionPlanIntakeWarningDigestBoundaryInputNames() {
        return POST_DECISION_PLAN_INTAKE_WARNING_DIGEST_BOUNDARY_INPUT_NAMES;
    }

    static RehearsalRuntimeShellDecisionRecordSideEffectBoundary decisionRecordSideEffectBoundary() {
        return new RehearsalRuntimeShellDecisionRecordSideEffectBoundary(
                true,
                true,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false
        );
    }

    static RehearsalRuntimeShellPostDecisionPlanIntakeSideEffectBoundary postDecisionPlanIntakeSideEffectBoundary() {
        return new RehearsalRuntimeShellPostDecisionPlanIntakeSideEffectBoundary(
                true,
                true,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false
        );
    }

    static List<String> decisionRecordWarningDigestBoundaryLines(
            RehearsalManagedAuditSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoReceipt receipt
    ) {
        RehearsalRuntimeShellDecisionRecordSideEffectBoundary boundary = receipt.sideEffectBoundary();
        return boundaryLines(
                boundaryInput("sandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoReceiptDigest",
                        receipt.receiptDigest()),
                boundaryInput("sandboxEndpointCredentialResolverRuntimeShellDecisionRecordState",
                        receipt.consumedByNodeRuntimeShellCandidateGateDecisionRecordState()),
                boundaryInput("sandboxEndpointCredentialResolverRuntimeShellDecisionRecordDecision",
                        receipt.decisionRecord().decision()),
                boundaryInput("sandboxEndpointCredentialResolverRuntimeShellDecisionRecordRequiredEvidenceCount",
                        receipt.decisionRecord().requiredEvidenceCount()),
                boundaryInput("sandboxEndpointCredentialResolverRuntimeShellDecisionRecordNoGoConditionCount",
                        receipt.decisionRecord().noGoConditionCount()),
                boundaryInput("sandboxEndpointCredentialResolverRuntimeShellDecisionRecordReadyForNodeV300",
                        receipt.readyForNodeV300RuntimeShellDecisionRecordUpstreamEchoVerification()),
                boundaryInput("sandboxEndpointCredentialResolverRuntimeShellDecisionRecordRuntimeImplemented",
                        boundary.disabledRuntimeShellImplemented()),
                boundaryInput("sandboxEndpointCredentialResolverRuntimeShellDecisionRecordRuntimeInvocationAllowed",
                        boundary.disabledRuntimeShellInvocationAllowed()),
                boundaryInput("sandboxEndpointCredentialResolverRuntimeShellDecisionRecordCredentialValueRead",
                        boundary.credentialValueRead()),
                boundaryInput("sandboxEndpointCredentialResolverRuntimeShellDecisionRecordRawEndpointUrlParsed",
                        boundary.rawEndpointUrlParsed()),
                boundaryInput("sandboxEndpointCredentialResolverRuntimeShellDecisionRecordExternalRequestSent",
                        boundary.externalRequestSent()),
                boundaryInput("sandboxEndpointCredentialResolverRuntimeShellDecisionRecordSecretProviderInstantiated",
                        boundary.secretProviderInstantiated()),
                boundaryInput("sandboxEndpointCredentialResolverRuntimeShellDecisionRecordResolverClientInstantiated",
                        boundary.resolverClientInstantiated()),
                boundaryInput("sandboxEndpointCredentialResolverRuntimeShellDecisionRecordApprovalLedgerWritten",
                        boundary.approvalLedgerWritten()),
                boundaryInput("sandboxEndpointCredentialResolverRuntimeShellDecisionRecordSqlExecuted",
                        boundary.sqlExecuted()),
                boundaryInput("sandboxEndpointCredentialResolverRuntimeShellDecisionRecordSchemaMigrationExecuted",
                        boundary.schemaMigrationExecuted()),
                boundaryInput("sandboxEndpointCredentialResolverRuntimeShellDecisionRecordAutomaticUpstreamStart",
                        boundary.automaticUpstreamStart())
        );
    }

    static List<String> postDecisionPlanIntakeWarningDigestBoundaryLines(
            RehearsalManagedAuditSandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoReceipt receipt
    ) {
        RehearsalRuntimeShellPostDecisionPlanIntakeSideEffectBoundary boundary = receipt.sideEffectBoundary();
        return boundaryLines(
                boundaryInput("sandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoReceiptDigest",
                        receipt.receiptDigest()),
                boundaryInput("sandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeState",
                        receipt.consumedByNodeRuntimeShellPostDecisionPlanIntakeState()),
                boundaryInput("sandboxEndpointCredentialResolverRuntimeShellPostDecisionSelectedContinuationDecision",
                        receipt.planIntake().selectedContinuationDecision()),
                boundaryInput("sandboxEndpointCredentialResolverRuntimeShellPostDecisionDecisionOptionCount",
                        receipt.planIntake().decisionOptionCount()),
                boundaryInput("sandboxEndpointCredentialResolverRuntimeShellPostDecisionSelectedDecisionOptionCount",
                        receipt.planIntake().selectedDecisionOptionCount()),
                boundaryInput("sandboxEndpointCredentialResolverRuntimeShellPostDecisionRejectedRuntimeImplementationOptionCount",
                        receipt.planIntake().rejectedRuntimeImplementationOptionCount()),
                boundaryInput("sandboxEndpointCredentialResolverRuntimeShellPostDecisionReadyForNodeV302",
                        receipt.readyForNodeV302PostDecisionPlanIntakeUpstreamEchoVerification()),
                boundaryInput("sandboxEndpointCredentialResolverRuntimeShellPostDecisionRuntimeImplemented",
                        boundary.disabledRuntimeShellImplemented()),
                boundaryInput("sandboxEndpointCredentialResolverRuntimeShellPostDecisionRuntimeInvocationAllowed",
                        boundary.disabledRuntimeShellInvocationAllowed()),
                boundaryInput("sandboxEndpointCredentialResolverRuntimeShellPostDecisionCredentialValueRead",
                        boundary.credentialValueRead()),
                boundaryInput("sandboxEndpointCredentialResolverRuntimeShellPostDecisionRawEndpointUrlParsed",
                        boundary.rawEndpointUrlParsed()),
                boundaryInput("sandboxEndpointCredentialResolverRuntimeShellPostDecisionExternalRequestSent",
                        boundary.externalRequestSent()),
                boundaryInput("sandboxEndpointCredentialResolverRuntimeShellPostDecisionSecretProviderInstantiated",
                        boundary.secretProviderInstantiated()),
                boundaryInput("sandboxEndpointCredentialResolverRuntimeShellPostDecisionResolverClientInstantiated",
                        boundary.resolverClientInstantiated()),
                boundaryInput("sandboxEndpointCredentialResolverRuntimeShellPostDecisionApprovalLedgerWritten",
                        boundary.approvalLedgerWritten()),
                boundaryInput("sandboxEndpointCredentialResolverRuntimeShellPostDecisionSqlExecuted",
                        boundary.sqlExecuted()),
                boundaryInput("sandboxEndpointCredentialResolverRuntimeShellPostDecisionSchemaMigrationExecuted",
                        boundary.schemaMigrationExecuted()),
                boundaryInput("sandboxEndpointCredentialResolverRuntimeShellPostDecisionAutomaticUpstreamStart",
                        boundary.automaticUpstreamStart())
        );
    }

    static boolean decisionRecordNoCredentialConnectionWriteOrAutoStartProved(
            RehearsalManagedAuditSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoReceipt receipt
    ) {
        RehearsalRuntimeShellDecisionRecordSideEffectBoundary boundary = receipt.sideEffectBoundary();
        return receipt.readyForNodeV300RuntimeShellDecisionRecordUpstreamEchoVerification()
                && !receipt.readyForDisabledRuntimeShellImplementation()
                && !receipt.readyForDisabledRuntimeShellInvocation()
                && !receipt.readyForManagedAuditResolverImplementation()
                && !boundary.disabledRuntimeShellImplemented()
                && !boundary.disabledRuntimeShellEnabled()
                && !boundary.disabledRuntimeShellInvocationAllowed()
                && !boundary.credentialValueRead()
                && !boundary.credentialValueProvided()
                && !boundary.rawEndpointUrlParsed()
                && !boundary.rawEndpointUrlRendered()
                && !boundary.externalRequestSent()
                && !boundary.secretProviderInstantiated()
                && !boundary.resolverClientInstantiated()
                && !boundary.approvalLedgerWritten()
                && !boundary.managedAuditStoreWritten()
                && !boundary.sqlExecuted()
                && !boundary.schemaMigrationExecuted()
                && !boundary.automaticUpstreamStart();
    }

    static boolean postDecisionPlanIntakeNoCredentialConnectionWriteOrAutoStartProved(
            RehearsalManagedAuditSandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoReceipt receipt
    ) {
        RehearsalRuntimeShellPostDecisionPlanIntakeSideEffectBoundary boundary = receipt.sideEffectBoundary();
        return receipt.readyForNodeV302PostDecisionPlanIntakeUpstreamEchoVerification()
                && !receipt.readyForDisabledRuntimeShellImplementation()
                && !receipt.readyForDisabledRuntimeShellInvocation()
                && !receipt.readyForManagedAuditResolverImplementation()
                && !boundary.disabledRuntimeShellImplemented()
                && !boundary.disabledRuntimeShellInvocationAllowed()
                && !boundary.credentialValueRead()
                && !boundary.rawEndpointUrlParsed()
                && !boundary.externalRequestSent()
                && !boundary.approvalLedgerWritten()
                && !boundary.sqlExecuted()
                && !boundary.schemaMigrationExecuted()
                && !boundary.automaticUpstreamStart();
    }
}
