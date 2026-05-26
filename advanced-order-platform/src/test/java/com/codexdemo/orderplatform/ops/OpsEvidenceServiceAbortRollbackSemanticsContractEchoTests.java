package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverAbortRollbackSemanticsContractEchoRecords
        .RehearsalAbortRollbackSemanticsNoGoBoundary;
import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverAbortRollbackSemanticsContractEchoRecords
        .RehearsalAbortRollbackSemanticsProhibitedField;
import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverAbortRollbackSemanticsContractEchoRecords
        .RehearsalAbortRollbackSemanticsRejectionReason;
import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverAbortRollbackSemanticsContractEchoRecords
        .RehearsalAbortRollbackSemanticsRequiredField;
import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverAbortRollbackSemanticsContractEchoRecords
        .RehearsalAbortRollbackSemanticsUpstreamEchoRequest;
import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverAbortRollbackSemanticsContractEchoRecords
        .RehearsalManagedAuditSandboxEndpointCredentialResolverAbortRollbackSemanticsContractEchoReceipt;
import org.junit.jupiter.api.Test;

class OpsEvidenceServiceAbortRollbackSemanticsContractEchoTests extends OpsEvidenceServiceRehearsalTestSupport {

    @Test
    void releaseApprovalRehearsalAddsAbortRollbackSemanticsContractEchoReceiptForNodeV327() {
        OpsEvidenceService service = readOnlyFixtureService();

        ReleaseApprovalRehearsalResponse rehearsal =
                service.releaseApprovalRehearsal(headerBackedRehearsalRequest());
        RehearsalManagedAuditSandboxEndpointCredentialResolverAbortRollbackSemanticsContractEchoReceipt receipt =
                rehearsal.managedAuditSandboxEndpointCredentialResolverAbortRollbackSemanticsContractEchoReceipt();

        assertThat(receipt.receiptVersion())
                .isEqualTo("java-release-approval-rehearsal-managed-audit-sandbox-endpoint-credential-resolver-abort-rollback-semantics-contract-echo-receipt.v1");
        assertThat(receipt.sourceNoNetworkSafetyFixtureContractEchoReceiptVersion())
                .isEqualTo(
                        rehearsal
                                .managedAuditSandboxEndpointCredentialResolverNoNetworkSafetyFixtureContractEchoReceipt()
                                .receiptVersion()
                );
        assertThat(receipt.sourceNoNetworkSafetyFixtureContractEchoReceiptSchemaVersion())
                .isEqualTo("java-release-approval-rehearsal-response-schema.v49");
        assertThat(receipt.sourceNoNetworkSafetyFixtureContractEchoReceiptDigest())
                .isEqualTo(
                        rehearsal
                                .managedAuditSandboxEndpointCredentialResolverNoNetworkSafetyFixtureContractEchoReceipt()
                                .receiptDigest()
                );
        assertThat(receipt.consumedByNodeAbortRollbackSemanticsContractVersion()).isEqualTo("Node v326");
        assertThat(receipt.consumedByNodeAbortRollbackSemanticsContractProfile())
                .isEqualTo(
                        "managed-audit-manual-sandbox-connection-credential-resolver-abort-rollback-semantics-contract-intake.v1"
                );
        assertThat(receipt.consumedByNodeAbortRollbackSemanticsContractEndpoint())
                .isEqualTo(
                        "/api/v1/audit/managed-audit-manual-sandbox-connection-credential-resolver-abort-rollback-semantics-contract-intake"
                );
        assertThat(receipt.consumedByNodeAbortRollbackSemanticsContractMarkdownEndpoint())
                .isEqualTo(
                        "/api/v1/audit/managed-audit-manual-sandbox-connection-credential-resolver-abort-rollback-semantics-contract-intake?format=markdown"
                );
        assertThat(receipt.consumedByNodeAbortRollbackSemanticsContractState())
                .isEqualTo("abort-rollback-semantics-contract-intake-ready");
        assertThat(receipt.nextNodeAbortRollbackSemanticsUpstreamEchoVerificationVersion())
                .isEqualTo("Node v327");
        assertThat(receipt.abortRollbackSemanticsContractEchoMode())
                .isEqualTo("java-v150-abort-rollback-semantics-contract-echo-only");

        assertThat(receipt.sourceNodeV325().sourceVersion()).isEqualTo("Node v325");
        assertThat(receipt.sourceNodeV325().reviewDigest())
                .isEqualTo("5781245b6dd5b67d6e2985e7e6f70e942defcd4ea95a09dc516743abf7abf0ca");
        assertThat(receipt.sourceNodeV325().completedPrerequisiteCount()).isEqualTo(5);
        assertThat(receipt.sourceNodeV325().remainingPrerequisiteCount()).isEqualTo(1);
        assertThat(receipt.sourceNodeV325().completedPrerequisiteIds())
                .contains("no-network-safety-fixture");
        assertThat(receipt.sourceNodeV325().remainingPrerequisiteIds())
                .containsExactly("abort-rollback-semantics");
        assertThat(receipt.sourceNodeV325().runtimeShellImplemented()).isFalse();
        assertThat(receipt.sourceNodeV325().externalRequestSent()).isFalse();

        assertThat(receipt.abortRollbackSemanticsContract().contractDigest())
                .isEqualTo("fe05bcfd65aabf56ef170bf458837053a11edf0ae44ad203a88d4ecd284299f9");
        assertThat(receipt.abortRollbackSemanticsContract().requiredFields())
                .map(RehearsalAbortRollbackSemanticsRequiredField::id)
                .containsExactly(
                        "manual_abort_marker",
                        "rollback_runbook_reference",
                        "operator_confirmation_handle",
                        "approval_correlation_id",
                        "cleanup_evidence_marker",
                        "idempotent_noop_failure_policy",
                        "rollback_authority_boundary",
                        "abort_reason_code",
                        "recovery_checkpoint_reference",
                        "audit_digest"
                );
        assertThat(receipt.abortRollbackSemanticsContract().prohibitedFields())
                .map(RehearsalAbortRollbackSemanticsProhibitedField::id)
                .containsExactly(
                        "credential_value",
                        "raw_endpoint_url",
                        "runtime_shell_command",
                        "shell_script_body",
                        "secret_provider_config",
                        "resolver_client_config",
                        "external_request_payload",
                        "approval_ledger_mutation",
                        "schema_migration_sql",
                        "deployment_action",
                        "rollback_execution_action",
                        "upstream_process_start",
                        "mini_kv_write_command",
                        "java_sql_execution"
                );
        assertThat(receipt.abortRollbackSemanticsContract().rejectionReasons())
                .map(RehearsalAbortRollbackSemanticsRejectionReason::code)
                .containsExactly(
                        "MANUAL_ABORT_MARKER_MISSING",
                        "ROLLBACK_RUNBOOK_REFERENCE_MISSING",
                        "CREDENTIAL_OR_RAW_ENDPOINT_PRESENT",
                        "RUNTIME_SHELL_COMMAND_PRESENT",
                        "NETWORK_OR_PROVIDER_ACTION_PRESENT",
                        "WRITE_OR_ROLLBACK_ACTION_PRESENT"
                );
        assertThat(receipt.abortRollbackSemanticsContract().noGoBoundaries())
                .map(RehearsalAbortRollbackSemanticsNoGoBoundary::id)
                .containsExactly(
                        "credential_value_read",
                        "raw_endpoint_url_parse",
                        "runtime_shell_command_render",
                        "secret_provider_instantiation",
                        "resolver_client_instantiation",
                        "http_or_tcp_execution",
                        "rollback_execution",
                        "java_sql_execution",
                        "mini_kv_write_command",
                        "ledger_or_schema_write",
                        "automatic_upstream_start"
                );
        assertThat(receipt.abortRollbackSemanticsContract().upstreamEchoRequests())
                .map(RehearsalAbortRollbackSemanticsUpstreamEchoRequest::version)
                .containsExactly("Java v150", "mini-kv v142");
        assertThat(receipt.abortRollbackSemanticsContract().abortRollbackExecutionAllowed()).isFalse();

        assertThat(receipt.prerequisiteTransition().afterV326()).isEqualTo("contract-intake-defined");
        assertThat(receipt.prerequisiteTransition().completedPrerequisiteCountBeforeV326()).isEqualTo(5);
        assertThat(receipt.prerequisiteTransition().remainingPrerequisiteCountBeforeV326()).isEqualTo(1);
        assertThat(receipt.prerequisiteTransition().preservesNoNetworkSafetyFixtureClosure()).isTrue();
        assertThat(receipt.prerequisiteTransition().closesAbortRollbackSemantics()).isFalse();
        assertThat(receipt.necessityProof().consumer()).isEqualTo("Java v150 + mini-kv v142, then Node v327");

        assertThat(receipt.checks().sourceNodeV325Ready()).isTrue();
        assertThat(receipt.checks().sourceJavaV149NoNetworkSafetyFixtureContractReady()).isTrue();
        assertThat(receipt.checks().nodeV326ContractEchoed()).isTrue();
        assertThat(receipt.checks().contractRequiredFieldsDocumented()).isTrue();
        assertThat(receipt.checks().contractProhibitedFieldsDocumented()).isTrue();
        assertThat(receipt.checks().rejectionReasonsDocumented()).isTrue();
        assertThat(receipt.checks().noGoBoundariesClosed()).isTrue();
        assertThat(receipt.checks().abortRollbackExecutionStillBlocked()).isTrue();
        assertThat(receipt.checks()
                .readyForManagedAuditManualSandboxConnectionCredentialResolverAbortRollbackSemanticsContractEcho())
                .isTrue();
        assertThat(receipt.sideEffectBoundary().abortRollbackSemanticsContractEchoOnly()).isTrue();
        assertThat(receipt.sideEffectBoundary().readOnlyAbortRollbackSemanticsContract()).isTrue();
        assertThat(receipt.sideEffectBoundary().abortRollbackSemanticsExecuted()).isFalse();
        assertThat(receipt.sideEffectBoundary().httpRequestSent()).isFalse();
        assertThat(receipt.sideEffectBoundary().tcpConnectionAttempted()).isFalse();
        assertThat(receipt.sideEffectBoundary().externalRequestSent()).isFalse();
        assertThat(receipt.sideEffectBoundary().approvalLedgerWritten()).isFalse();
        assertThat(receipt.sideEffectBoundary().sqlExecuted()).isFalse();
        assertThat(receipt.sideEffectBoundary().deploymentExecuted()).isFalse();
        assertThat(receipt.sideEffectBoundary().rollbackExecuted()).isFalse();
        assertThat(receipt.readyForNodeV327AbortRollbackSemanticsUpstreamEchoVerification()).isTrue();
        assertThat(receipt.readyForDisabledRuntimeShellImplementation()).isFalse();
        assertThat(receipt.readyForManagedAuditResolverImplementation()).isFalse();
        assertThat(receipt.receiptWarnings()).isEmpty();
        assertThat(receipt.receiptDigest()).startsWith("sha256:");
        assertThat(receipt.summary().javaCheckCount()).isEqualTo(22);
        assertThat(receipt.summary().requiredFieldCount()).isEqualTo(10);
        assertThat(receipt.summary().prohibitedFieldCount()).isEqualTo(14);
        assertThat(receipt.summary().rejectionReasonCount()).isEqualTo(6);
        assertThat(receipt.summary().noGoBoundaryCount()).isEqualTo(11);
        assertThat(receipt.summary().warningCount()).isEqualTo(2);
        assertThat(receipt.summary().recommendationCount()).isEqualTo(2);

        assertThat(rehearsal.verificationHint().responseSchemaVersion())
                .isEqualTo("java-release-approval-rehearsal-response-schema.v52");
        assertThat(rehearsal.verificationHint().schemaFields())
                .contains("managedAuditSandboxEndpointCredentialResolverAbortRollbackSemanticsContractEchoReceipt");
        assertThat(rehearsal.verificationHint().warningDigestInputs())
                .contains(
                        "managedAuditSandboxEndpointCredentialResolverAbortRollbackSemanticsContractEchoReceiptWarnings",
                        "sandboxEndpointCredentialResolverAbortRollbackSemanticsContractState",
                        "sandboxEndpointCredentialResolverAbortRollbackSemanticsContractRequiredFieldCount",
                        "sandboxEndpointCredentialResolverAbortRollbackSemanticsContractProhibitedFieldCount",
                        "sandboxEndpointCredentialResolverAbortRollbackSemanticsContractReadyForNodeV327",
                        "sandboxEndpointCredentialResolverAbortRollbackSemanticsContractRollbackExecuted"
                );
        assertThat(rehearsal.verificationHint().proofClaims())
                .contains(
                        "managedAuditSandboxEndpointCredentialResolverAbortRollbackSemanticsContractEchoReceipt.abortRollbackSemanticsContract.requiredFieldCount=10",
                        "managedAuditSandboxEndpointCredentialResolverAbortRollbackSemanticsContractEchoReceipt.abortRollbackSemanticsContract.prohibitedFieldCount=14",
                        "managedAuditSandboxEndpointCredentialResolverAbortRollbackSemanticsContractEchoReceipt.abortRollbackSemanticsContract.abortRollbackExecutionAllowed=false",
                        "managedAuditSandboxEndpointCredentialResolverAbortRollbackSemanticsContractEchoReceipt.readyForNodeV327AbortRollbackSemanticsUpstreamEchoVerification=true"
                );
        assertThat(rehearsal.verificationHint().nodeVerificationActions())
                .contains(
                        "Compare managedAuditSandboxEndpointCredentialResolverAbortRollbackSemanticsContractEchoReceipt.consumedByNodeAbortRollbackSemanticsContractProfile with Node v326",
                        "Require managedAuditSandboxEndpointCredentialResolverAbortRollbackSemanticsContractEchoReceipt.abortRollbackSemanticsContract.requiredFieldCount=10 before Node v327",
                        "Keep managedAuditSandboxEndpointCredentialResolverAbortRollbackSemanticsContractEchoReceipt.sideEffectBoundary.rollbackExecuted=false"
                );
        assertThat(rehearsal.verificationHint().noLedgerWriteProved()).isTrue();

        ReleaseApprovalRehearsalResponse repeated =
                service.releaseApprovalRehearsal(paddedHeaderBackedRehearsalRequest());
        assertThat(repeated
                .managedAuditSandboxEndpointCredentialResolverAbortRollbackSemanticsContractEchoReceipt()
                .receiptDigest()).isEqualTo(receipt.receiptDigest());
    }
}
