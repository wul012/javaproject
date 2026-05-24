package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverExecutionDeniedEchoRecords
        .RehearsalManagedAuditSandboxEndpointCredentialResolverExecutionDeniedEchoReceipt;

import org.junit.jupiter.api.Test;

class OpsEvidenceServiceCredentialResolverExecutionDeniedEchoTests extends OpsEvidenceServiceRehearsalTestSupport {

    @Test
    void releaseApprovalRehearsalAddsDirectExecutionDeniedEchoReceiptForNodeV293() {
        OpsEvidenceService service = readOnlyFixtureService();

        ReleaseApprovalRehearsalResponse rehearsal =
                service.releaseApprovalRehearsal(headerBackedRehearsalRequest());
        RehearsalManagedAuditSandboxEndpointCredentialResolverExecutionDeniedEchoReceipt receipt =
                rehearsal.managedAuditSandboxEndpointCredentialResolverExecutionDeniedEchoReceipt();

        assertThat(receipt.receiptVersion())
                .isEqualTo(
                        "java-release-approval-rehearsal-managed-audit-sandbox-endpoint-credential-resolver-execution-denied-echo-receipt.v1"
                );
        assertThat(receipt.sourceImplementationPlanEchoReceiptVersion())
                .isEqualTo(
                        "java-release-approval-rehearsal-managed-audit-sandbox-endpoint-credential-resolver-implementation-plan-echo-receipt.v1"
                );
        assertThat(receipt.sourceImplementationPlanEchoReceiptSchemaVersion())
                .isEqualTo("java-release-approval-rehearsal-response-schema.v36");
        assertThat(receipt.consumedByNodeCredentialResolverFakeHarnessReadinessDecisionVersion())
                .isEqualTo("Node v292");
        assertThat(receipt.consumedByNodeCredentialResolverFakeHarnessReadinessDecisionProfile())
                .isEqualTo(
                        "managed-audit-manual-sandbox-connection-credential-resolver-fake-harness-readiness-decision.v1"
                );
        assertThat(receipt.consumedByNodeCredentialResolverFakeHarnessReadinessDecisionEndpoint())
                .isEqualTo(
                        "/api/v1/audit/managed-audit-manual-sandbox-connection-credential-resolver-fake-harness-readiness-decision"
                );
        assertThat(receipt.consumedByNodeCredentialResolverFakeHarnessReadinessDecisionMarkdownEndpoint())
                .endsWith("?format=markdown");
        assertThat(receipt.consumedByNodeCredentialResolverFakeHarnessReadinessDecisionState())
                .isEqualTo("credential-resolver-fake-harness-readiness-blocked");
        assertThat(receipt.nextNodeCredentialResolverFakeHarnessReadinessBlockedDecisionUpstreamEchoVerificationVersion())
                .isEqualTo("Node v293");
        assertThat(receipt.nextNodeCredentialResolverFakeHarnessReadinessBlockedDecisionUpstreamEchoVerificationProfile())
                .isEqualTo(
                        "managed-audit-manual-sandbox-connection-credential-resolver-fake-harness-readiness-blocked-decision-upstream-echo-verification.v1"
                );
        assertThat(receipt.nextNodeCredentialResolverFakeHarnessReadinessBlockedDecisionUpstreamEchoVerificationState())
                .isEqualTo("credential-resolver-fake-harness-readiness-blocked-decision-upstream-echo-verification-ready");
        assertThat(receipt.executionDeniedEchoMode())
                .isEqualTo("java-v131-credential-resolver-direct-execution-denied-echo-only");
        assertThat(receipt.sourceSpan()).isEqualTo("Node v292");

        assertThat(receipt.sourceImplementationPlanEcho().sourcePlanState())
                .isEqualTo("credential-resolver-implementation-plan-draft-ready");
        assertThat(receipt.sourceImplementationPlanEcho().implementationPlanEchoReady()).isTrue();
        assertThat(receipt.sourceImplementationPlanEcho().javaV121MiniKvV126EchoReady()).isTrue();
        assertThat(receipt.sourceImplementationPlanEcho().sourceInterfaceBoundaryCount()).isEqualTo(7);
        assertThat(receipt.sourceImplementationPlanEcho().sourceRequiredArtifactCount()).isEqualTo(21);
        assertThat(receipt.sourceImplementationPlanEcho().sourceProhibitedActionCount()).isEqualTo(21);
        assertThat(receipt.sourceImplementationPlanEcho().sourceJavaRequirementCount()).isEqualTo(4);
        assertThat(receipt.sourceImplementationPlanEcho().sourceMiniKvRequirementCount()).isEqualTo(4);

        assertThat(receipt.executionDeniedDecision().blockingNodeDecisionState())
                .isEqualTo("credential-resolver-fake-harness-readiness-blocked");
        assertThat(receipt.executionDeniedDecision().fakeHarnessExecutionDenied()).isTrue();
        assertThat(receipt.executionDeniedDecision().managedAuditResolverImplementationDenied()).isTrue();
        assertThat(receipt.executionDeniedDecision().disabledRuntimeShellDenied()).isTrue();
        assertThat(receipt.executionDeniedDecision().directExecutionDeniedEchoSupplied()).isTrue();
        assertThat(receipt.executionDeniedDecision().nodeV293MayConsumeWithoutRuntimeExecution()).isTrue();

        assertThat(receipt.checks().sourceImplementationPlanReady()).isTrue();
        assertThat(receipt.checks().sourceImplementationStillBlocked()).isTrue();
        assertThat(receipt.checks().nodeV292ReadinessDecisionBlocked()).isTrue();
        assertThat(receipt.checks().directExecutionDeniedEchoPresent()).isTrue();
        assertThat(receipt.checks().readyForNodeV293FakeHarnessReadinessBlockedDecisionUpstreamEchoVerification())
                .isTrue();
        assertRuntimeBlocked(
                receipt.sideEffectBoundary().disabledRuntimeShellAllowed(),
                receipt.sideEffectBoundary().fakeHarnessRuntimeAllowed(),
                receipt.sideEffectBoundary().managedAuditResolverImplementationAllowed(),
                receipt.sideEffectBoundary().productionAuditAllowed(),
                receipt.sideEffectBoundary().productionWindowAllowed(),
                receipt.sideEffectBoundary().executionAllowed(),
                receipt.sideEffectBoundary().connectsManagedAudit(),
                receipt.sideEffectBoundary().readsManagedAuditCredential(),
                receipt.sideEffectBoundary().storesManagedAuditCredential(),
                receipt.sideEffectBoundary().credentialValueRead(),
                receipt.sideEffectBoundary().rawEndpointUrlParsed(),
                receipt.sideEffectBoundary().rawEndpointUrlRendered(),
                receipt.sideEffectBoundary().externalRequestSent(),
                receipt.sideEffectBoundary().secretProviderInstantiated(),
                receipt.sideEffectBoundary().resolverClientInstantiated(),
                receipt.sideEffectBoundary().approvalLedgerWritten(),
                receipt.sideEffectBoundary().managedAuditStoreWritten(),
                receipt.sideEffectBoundary().sqlExecuted(),
                receipt.sideEffectBoundary().schemaMigrationExecuted(),
                receipt.sideEffectBoundary().rollbackExecuted(),
                receipt.sideEffectBoundary().automaticUpstreamStart(),
                receipt.sideEffectBoundary().javaStartedNodeMiniKvOrHarness()
        );

        assertThat(receipt.echoWorkflowReadySteps())
                .containsExactly(
                        "sourceImplementationPlanEchoed",
                        "nodeV292ReadinessBlockedDecisionEchoed",
                        "fakeHarnessExecutionDeniedEchoed",
                        "noCredentialReadEchoed",
                        "noRawEndpointParseEchoed",
                        "noManagedAuditConnectionEchoed",
                        "noSqlOrLedgerWriteEchoed",
                        "noAutoStartBoundaryEchoed",
                        "sourceStillBlocked"
                );
        assertThat(receipt.echoWorkflowMissingSteps()).isEmpty();
        assertThat(receipt.javaExecutionDeniedEchoPresent()).isTrue();
        assertThat(receipt.readyForNodeV293FakeHarnessReadinessBlockedDecisionUpstreamEchoVerification()).isTrue();
        assertThat(receipt.readyForDisabledRuntimeShell()).isFalse();
        assertThat(receipt.readyForFakeHarnessRuntime()).isFalse();
        assertThat(receipt.readyForManagedAuditResolverImplementation()).isFalse();
        assertThat(receipt.nodeMayTreatAsProductionAuditRecord()).isFalse();
        assertThat(receipt.denialReasonCodes())
                .contains(
                        "JAVA_DIRECT_EXECUTION_DENIED_ECHO_ONLY",
                        "NODE_V292_FAKE_HARNESS_READINESS_BLOCKED",
                        "LEDGER_SQL_AND_SCHEMA_SIDE_EFFECTS_FORBIDDEN"
                );
        assertThat(receipt.noGoConditionCodes())
                .contains(
                        "NO_FAKE_HARNESS_RUNTIME",
                        "NO_CREDENTIAL_VALUE_READ",
                        "NO_LEDGER_SQL_OR_SCHEMA_MUTATION"
                );
        assertThat(receipt.nextRequiredEchoVersions())
                .containsExactly(
                        "mini-kv v129 fake harness non-participation receipt",
                        "Node v293 fake harness readiness blocked decision upstream echo verification"
                );
        assertThat(receipt.receiptWarnings()).isEmpty();
        assertThat(receipt.receiptDigest()).startsWith("sha256:");

        assertThat(rehearsal.verificationHint().responseSchemaVersion())
                .isEqualTo("java-release-approval-rehearsal-response-schema.v49");
        assertThat(rehearsal.verificationHint().schemaFields())
                .contains("managedAuditSandboxEndpointCredentialResolverExecutionDeniedEchoReceipt");
        assertThat(rehearsal.verificationHint().warningDigestInputs())
                .contains(
                        "managedAuditSandboxEndpointCredentialResolverExecutionDeniedEchoReceiptWarnings",
                        "sandboxEndpointCredentialResolverExecutionDeniedEchoReceiptDigest",
                        "sandboxEndpointCredentialResolverExecutionDeniedCredentialValueRead",
                        "sandboxEndpointCredentialResolverExecutionDeniedApprovalLedgerWritten",
                        "sandboxEndpointCredentialResolverExecutionDeniedReadyForNodeV293"
                );
        assertThat(rehearsal.verificationHint().proofClaims())
                .contains(
                        "managedAuditSandboxEndpointCredentialResolverExecutionDeniedEchoReceipt.javaExecutionDeniedEchoPresent=true",
                        "managedAuditSandboxEndpointCredentialResolverExecutionDeniedEchoReceipt.executionDeniedDecision.fakeHarnessExecutionDenied=true",
                        "managedAuditSandboxEndpointCredentialResolverExecutionDeniedEchoReceipt.readyForNodeV293FakeHarnessReadinessBlockedDecisionUpstreamEchoVerification=true"
                );
        assertThat(rehearsal.verificationHint().nodeVerificationActions())
                .contains(
                        "Compare managedAuditSandboxEndpointCredentialResolverExecutionDeniedEchoReceipt.consumedByNodeCredentialResolverFakeHarnessReadinessDecisionProfile with Node v292",
                        "Verify managedAuditSandboxEndpointCredentialResolverExecutionDeniedEchoReceipt.javaExecutionDeniedEchoPresent=true before Node v293",
                        "Require managedAuditSandboxEndpointCredentialResolverExecutionDeniedEchoReceipt.readyForNodeV293FakeHarnessReadinessBlockedDecisionUpstreamEchoVerification=true before Node v293"
                );

        ReleaseApprovalRehearsalResponse repeated =
                service.releaseApprovalRehearsal(paddedHeaderBackedRehearsalRequest());
        assertThat(repeated.managedAuditSandboxEndpointCredentialResolverExecutionDeniedEchoReceipt()
                .receiptDigest()).isEqualTo(receipt.receiptDigest());
    }

    private static void assertRuntimeBlocked(boolean... flags) {
        assertThat(flags).containsOnly(false);
    }
}
