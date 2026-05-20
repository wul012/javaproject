package com.codexdemo.orderplatform.ops;

import static com.codexdemo.orderplatform.ops.OpsEvidenceServiceTestFixtures.headerBackedRehearsalRequest;
import static com.codexdemo.orderplatform.ops.OpsEvidenceServiceTestFixtures.paddedHeaderBackedRehearsalRequest;
import static com.codexdemo.orderplatform.ops.OpsEvidenceServiceTestFixtures.readOnlyFixtureService;
import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.notification.FailedEventSummaryService;
import com.codexdemo.orderplatform.order.IdempotencyStore;
import com.codexdemo.orderplatform.outbox.OutboxRepository;
import org.junit.jupiter.api.Test;

class OpsEvidenceServiceSandboxConnectionPacketEchoTests {

    private final FailedEventSummaryService failedEventSummaryService =
            org.mockito.Mockito.mock(FailedEventSummaryService.class);
    private final OutboxRepository outboxRepository = org.mockito.Mockito.mock(OutboxRepository.class);
    private final IdempotencyStore idempotencyStore = org.mockito.Mockito.mock(IdempotencyStore.class);

    @Test
    void releaseApprovalRehearsalExposesDryRunCommandPackageEchoReceipt() {
        OpsEvidenceService service = readOnlyFixtureService(
                failedEventSummaryService,
                outboxRepository,
                idempotencyStore
        );

        ReleaseApprovalRehearsalResponse rehearsal =
                service.releaseApprovalRehearsal(headerBackedRehearsalRequest());

        ReleaseApprovalRehearsalResponseRecords.RehearsalManagedAuditSandboxConnectionDryRunCommandPackageEchoReceipt
                receipt = rehearsal.managedAuditSandboxConnectionDryRunCommandPackageEchoReceipt();
        assertThat(receipt.receiptVersion())
                .isEqualTo(
                        "java-release-approval-rehearsal-managed-audit-sandbox-connection-dry-run-command-package-echo-receipt.v1"
                );
        assertThat(receipt.sourceSandboxConnectionOperatorWindowChecklistEchoReceiptSchemaVersion())
                .isEqualTo("java-release-approval-rehearsal-response-schema.v21");
        assertThat(receipt.consumedByNodeDryRunCommandPackageVersion()).isEqualTo("Node v241");
        assertThat(receipt.consumedByNodeDryRunCommandPackageProfile())
                .isEqualTo("managed-audit-manual-sandbox-connection-dry-run-command-package.v1");
        assertThat(receipt.nextNodeUpstreamEchoVerificationVersion()).isEqualTo("Node v244");
        assertThat(receipt.packageShape().commandCount()).isEqualTo(6);
        assertThat(receipt.packageShape().disabledByDefault()).isTrue();
        assertThat(receipt.packageShape().dryRunOnly()).isTrue();
        assertThat(receipt.fieldEcho().credentialHandleCommandId()).isEqualTo("verify-credential-handle");
        assertThat(receipt.fieldEcho().schemaRehearsalCommandId()).isEqualTo("review-schema-rehearsal");
        assertThat(receipt.fieldEcho().rollbackPathCommandId()).isEqualTo("review-rollback-path");
        assertThat(receipt.fieldEcho().timeoutBudgetCommandId()).isEqualTo("confirm-timeout-budget");
        assertThat(receipt.fieldEcho().manualAbortCommandId()).isEqualTo("confirm-manual-abort-marker");
        assertThat(receipt.fieldEcho().timeoutBudgetMs()).isEqualTo(15000);
        assertThat(receipt.fieldEcho().credentialValueEchoed()).isFalse();
        assertThat(receipt.echoedCommandIds())
                .containsExactly(
                        "review-owner-approval-artifact",
                        "verify-credential-handle",
                        "review-schema-rehearsal",
                        "review-rollback-path",
                        "confirm-timeout-budget",
                        "confirm-manual-abort-marker"
                );
        assertThat(receipt.javaExecutionBoundary().carriesCredentialValue()).isFalse();
        assertThat(receipt.javaExecutionBoundary().credentialValueReadByJava()).isFalse();
        assertThat(receipt.javaExecutionBoundary().actualConnectionAttemptedByJava()).isFalse();
        assertThat(receipt.javaExecutionBoundary().schemaMigrationSqlExecutedByJava()).isFalse();
        assertThat(receipt.javaExecutionBoundary().approvalLedgerWrittenByJava()).isFalse();
        assertThat(receipt.javaExecutionBoundary().managedAuditStateWriteRequestedByJava()).isFalse();
        assertThat(receipt.javaExecutionBoundary().upstreamServiceAutoStartRequestedByJava()).isFalse();
        assertThat(receipt.javaExecutionBoundary().miniKvWritePermissionRequestedByJava()).isFalse();
        assertThat(receipt.readyForNodeV244ManualSandboxDryRunCommandUpstreamEchoVerification()).isTrue();
        assertThat(receipt.readyForManagedAuditSandboxAdapterConnection()).isFalse();
        assertThat(receipt.receiptWarnings()).isEmpty();
        assertThat(receipt.receiptDigest()).startsWith("sha256:");
        assertThat(rehearsal.verificationHint().schemaFields())
                .contains("managedAuditSandboxConnectionDryRunCommandPackageEchoReceipt");
        assertThat(rehearsal.verificationHint().warningDigestInputs())
                .contains(
                        "managedAuditSandboxConnectionDryRunCommandPackageEchoReceiptWarnings",
                        "sandboxConnectionDryRunCommandPackageEchoReceiptDigest",
                        "sandboxConnectionDryRunCommandPackageApprovalLedgerWrittenByJava"
                );
        assertThat(rehearsal.verificationHint().proofClaims())
                .contains(
                        "managedAuditSandboxConnectionDryRunCommandPackageEchoReceipt.packageShape.commandCount=6",
                        "managedAuditSandboxConnectionDryRunCommandPackageEchoReceipt.javaExecutionBoundary.approvalLedgerWrittenByJava=false"
                );
        assertThat(rehearsal.verificationHint().nodeVerificationActions())
                .contains(
                        "Compare managedAuditSandboxConnectionDryRunCommandPackageEchoReceipt.consumedByNodeDryRunCommandPackageProfile with Node v241",
                        "Require managedAuditSandboxConnectionDryRunCommandPackageEchoReceipt.readyForNodeV244ManualSandboxDryRunCommandUpstreamEchoVerification=true before Node v244"
                );

        ReleaseApprovalRehearsalResponse repeated =
                service.releaseApprovalRehearsal(paddedHeaderBackedRehearsalRequest());
        assertThat(repeated.managedAuditSandboxConnectionDryRunCommandPackageEchoReceipt().receiptDigest())
                .isEqualTo(receipt.receiptDigest());
    }


    @Test
    void releaseApprovalRehearsalExposesPrecheckPacketEchoReceipt() {
        OpsEvidenceService service = readOnlyFixtureService(
                failedEventSummaryService,
                outboxRepository,
                idempotencyStore
        );

        ReleaseApprovalRehearsalResponse rehearsal =
                service.releaseApprovalRehearsal(headerBackedRehearsalRequest());

        ReleaseApprovalRehearsalResponseRecords.RehearsalManagedAuditSandboxConnectionPrecheckPacketEchoReceipt
                receipt = rehearsal.managedAuditSandboxConnectionPrecheckPacketEchoReceipt();
        assertThat(receipt.receiptVersion())
                .isEqualTo(
                        "java-release-approval-rehearsal-managed-audit-sandbox-connection-precheck-packet-echo-receipt.v1"
                );
        assertThat(receipt.sourceDryRunCommandPackageEchoReceiptSchemaVersion())
                .isEqualTo("java-release-approval-rehearsal-response-schema.v22");
        assertThat(receipt.consumedByNodePrecheckPacketVersion()).isEqualTo("Node v245");
        assertThat(receipt.consumedByNodePrecheckPacketProfile())
                .isEqualTo("managed-audit-manual-sandbox-connection-precheck-packet.v1");
        assertThat(receipt.nextNodePrecheckUpstreamReceiptVerificationVersion()).isEqualTo("Node v246");
        assertThat(receipt.packetShape().precheckItemCount()).isEqualTo(7);
        assertThat(receipt.packetShape().disabledByDefault()).isTrue();
        assertThat(receipt.packetShape().dryRunOnly()).isTrue();
        assertThat(receipt.fieldEcho().ownerApprovalArtifactItemId()).isEqualTo("owner-approval-artifact");
        assertThat(receipt.fieldEcho().credentialHandleReviewItemId()).isEqualTo("credential-handle-review");
        assertThat(receipt.fieldEcho().schemaMigrationRehearsalItemId()).isEqualTo("schema-migration-rehearsal");
        assertThat(receipt.fieldEcho().operatorWindowItemId()).isEqualTo("operator-window");
        assertThat(receipt.fieldEcho().rollbackPathItemId()).isEqualTo("rollback-path");
        assertThat(receipt.fieldEcho().abortMarkerItemId()).isEqualTo("abort-marker");
        assertThat(receipt.fieldEcho().timeoutPolicyItemId()).isEqualTo("timeout-policy");
        assertThat(receipt.fieldEcho().timeoutBudgetMs()).isEqualTo(15000);
        assertThat(receipt.fieldEcho().credentialValueEchoed()).isFalse();
        assertThat(receipt.echoedPrecheckItemIds())
                .containsExactly(
                        "owner-approval-artifact",
                        "credential-handle-review",
                        "schema-migration-rehearsal",
                        "operator-window",
                        "rollback-path",
                        "abort-marker",
                        "timeout-policy"
                );
        assertThat(receipt.javaExecutionBoundary().carriesCredentialValue()).isFalse();
        assertThat(receipt.javaExecutionBoundary().credentialValueReadByJava()).isFalse();
        assertThat(receipt.javaExecutionBoundary().actualConnectionAttemptedByJava()).isFalse();
        assertThat(receipt.javaExecutionBoundary().schemaMigrationSqlExecutedByJava()).isFalse();
        assertThat(receipt.javaExecutionBoundary().approvalLedgerWrittenByJava()).isFalse();
        assertThat(receipt.javaExecutionBoundary().managedAuditStateWriteRequestedByJava()).isFalse();
        assertThat(receipt.javaExecutionBoundary().upstreamServiceAutoStartRequestedByJava()).isFalse();
        assertThat(receipt.javaExecutionBoundary().miniKvWritePermissionRequestedByJava()).isFalse();
        assertThat(receipt.readyForNodeV246ManualSandboxConnectionPrecheckUpstreamReceiptVerification()).isTrue();
        assertThat(receipt.readyForManagedAuditSandboxAdapterConnection()).isFalse();
        assertThat(receipt.receiptWarnings()).isEmpty();
        assertThat(receipt.receiptDigest()).startsWith("sha256:");
        assertThat(rehearsal.verificationHint().schemaFields())
                .contains("managedAuditSandboxConnectionPrecheckPacketEchoReceipt");
        assertThat(rehearsal.verificationHint().warningDigestInputs())
                .contains(
                        "managedAuditSandboxConnectionPrecheckPacketEchoReceiptWarnings",
                        "sandboxConnectionPrecheckPacketEchoReceiptDigest",
                        "sandboxConnectionPrecheckPacketApprovalLedgerWrittenByJava"
                );
        assertThat(rehearsal.verificationHint().proofClaims())
                .contains(
                        "managedAuditSandboxConnectionPrecheckPacketEchoReceipt.packetShape.precheckItemCount=7",
                        "managedAuditSandboxConnectionPrecheckPacketEchoReceipt.javaExecutionBoundary.approvalLedgerWrittenByJava=false"
                );
        assertThat(rehearsal.verificationHint().nodeVerificationActions())
                .contains(
                        "Compare managedAuditSandboxConnectionPrecheckPacketEchoReceipt.consumedByNodePrecheckPacketProfile with Node v245",
                        "Require managedAuditSandboxConnectionPrecheckPacketEchoReceipt.readyForNodeV246ManualSandboxConnectionPrecheckUpstreamReceiptVerification=true before Node v246"
                );

        ReleaseApprovalRehearsalResponse repeated =
                service.releaseApprovalRehearsal(paddedHeaderBackedRehearsalRequest());
        assertThat(repeated.managedAuditSandboxConnectionPrecheckPacketEchoReceipt().receiptDigest())
                .isEqualTo(receipt.receiptDigest());
    }


    @Test
    void releaseApprovalRehearsalExposesDisabledAdapterClientPrecheckEchoReceipt() {
        OpsEvidenceService service = readOnlyFixtureService(
                failedEventSummaryService,
                outboxRepository,
                idempotencyStore
        );

        ReleaseApprovalRehearsalResponse rehearsal =
                service.releaseApprovalRehearsal(headerBackedRehearsalRequest());

        ReleaseApprovalRehearsalResponseRecords.RehearsalManagedAuditSandboxConnectionDisabledAdapterClientPrecheckEchoReceipt
                receipt = rehearsal.managedAuditSandboxConnectionDisabledAdapterClientPrecheckEchoReceipt();
        assertThat(receipt.receiptVersion())
                .isEqualTo(
                        "java-release-approval-rehearsal-managed-audit-sandbox-connection-disabled-adapter-client-precheck-echo-receipt.v1"
                );
        assertThat(receipt.sourcePrecheckPacketEchoReceiptSchemaVersion())
                .isEqualTo("java-release-approval-rehearsal-response-schema.v23");
        assertThat(receipt.consumedByNodeDisabledAdapterClientPrecheckVersion()).isEqualTo("Node v252");
        assertThat(receipt.consumedByNodeDisabledAdapterClientPrecheckProfile())
                .isEqualTo("managed-audit-manual-sandbox-connection-disabled-adapter-client-precheck.v1");
        assertThat(receipt.consumedByNodeDisabledAdapterClientPrecheckEndpoint())
                .isEqualTo(
                        "/api/v1/audit/managed-audit-manual-sandbox-connection-disabled-adapter-client-precheck"
                );
        assertThat(receipt.consumedByNodeDisabledAdapterClientPrecheckState())
                .isEqualTo("disabled-adapter-client-precheck-ready");
        assertThat(receipt.consumedByNodeTestOnlyAdapterShellContractVersion()).isEqualTo("Node v253");
        assertThat(receipt.consumedByNodeTestOnlyAdapterShellContractProfile())
                .isEqualTo("managed-audit-manual-sandbox-connection-test-only-adapter-shell-contract.v1");
        assertThat(receipt.nextNodeDisabledAdapterClientUpstreamEchoVerificationVersion())
                .isEqualTo("Node v254");
        assertThat(receipt.nodeV254MayConsume()).isTrue();
        assertThat(receipt.precheckShape().adapterMode()).isEqualTo("disabled-client-precheck-only");
        assertThat(receipt.precheckShape().precheckState())
                .isEqualTo("disabled-adapter-client-precheck-ready");
        assertThat(receipt.precheckShape().requiredEnvHandleCount()).isEqualTo(5);
        assertThat(receipt.precheckShape().failureClassCount()).isEqualTo(6);
        assertThat(receipt.precheckShape().dryRunResponseFieldCount()).isEqualTo(10);
        assertThat(receipt.precheckShape().envHandlesRemainHandleOnly()).isTrue();
        assertThat(receipt.precheckShape().noEnvValueReadForPrecheck()).isTrue();
        assertThat(receipt.precheckShape().dryRunResponseReadOnly()).isTrue();
        assertThat(receipt.precheckShape().precheckCreatesRealClient()).isFalse();
        assertThat(receipt.clientBoundary().clientImplementationStatus()).isEqualTo("not-implemented");
        assertThat(receipt.clientBoundary().clientMayBeInstantiated()).isFalse();
        assertThat(receipt.clientBoundary().externalRequestMayBeSent()).isFalse();
        assertThat(receipt.clientBoundary().credentialValueMayBeLoaded()).isFalse();
        assertThat(receipt.clientBoundary().optInGateRequired()).isTrue();
        assertThat(receipt.clientBoundary().productionEndpointAllowed()).isFalse();
        assertThat(receipt.clientBoundary().realTransportAllowed()).isFalse();
        assertThat(receipt.clientBoundary().realAdapterClientImplemented()).isFalse();
        assertThat(receipt.optInGate().gateName())
                .isEqualTo("ORDEROPS_MANAGED_AUDIT_ADAPTER_CLIENT_ENABLED");
        assertThat(receipt.optInGate().requiredValueForFutureConnection()).isEqualTo("true");
        assertThat(receipt.optInGate().currentDefault()).isEqualTo("false");
        assertThat(receipt.optInGate().precheckTreatsEnabledAsBlocked()).isTrue();
        assertThat(receipt.optInGate().operatorApprovalRequired()).isTrue();
        assertThat(receipt.javaExecutionBoundary().carriesCredentialValue()).isFalse();
        assertThat(receipt.javaExecutionBoundary().credentialValueReadByJava()).isFalse();
        assertThat(receipt.javaExecutionBoundary().credentialValueStoredByJava()).isFalse();
        assertThat(receipt.javaExecutionBoundary().actualConnectionAttemptedByJava()).isFalse();
        assertThat(receipt.javaExecutionBoundary().externalManagedAuditConnectionOpenedByJava()).isFalse();
        assertThat(receipt.javaExecutionBoundary().externalRequestSentByJava()).isFalse();
        assertThat(receipt.javaExecutionBoundary().schemaMigrationRequestedByJava()).isFalse();
        assertThat(receipt.javaExecutionBoundary().schemaMigrationSqlExecutedByJava()).isFalse();
        assertThat(receipt.javaExecutionBoundary().approvalLedgerWrittenByJava()).isFalse();
        assertThat(receipt.javaExecutionBoundary().managedAuditStateWriteRequestedByJava()).isFalse();
        assertThat(receipt.javaExecutionBoundary().managedAuditStoreWrittenByJava()).isFalse();
        assertThat(receipt.javaExecutionBoundary().sqlExecutedByJava()).isFalse();
        assertThat(receipt.javaExecutionBoundary().upstreamServiceAutoStartRequestedByJava()).isFalse();
        assertThat(receipt.javaExecutionBoundary().miniKvWritePermissionRequestedByJava()).isFalse();
        assertThat(receipt.echoedRequiredEnvHandles())
                .containsExactly(
                        "ORDEROPS_MANAGED_AUDIT_ADAPTER_CLIENT_ENABLED",
                        "ORDEROPS_MANAGED_AUDIT_SANDBOX_ENDPOINT_HANDLE",
                        "ORDEROPS_MANAGED_AUDIT_SANDBOX_CREDENTIAL_HANDLE",
                        "ORDEROPS_MANAGED_AUDIT_OWNER_APPROVAL_ARTIFACT_ID",
                        "ORDEROPS_MANAGED_AUDIT_TIMEOUT_BUDGET_MS"
                );
        assertThat(receipt.echoedFailureClassCodes())
                .containsExactly(
                        "ADAPTER_CLIENT_DISABLED",
                        "CREDENTIAL_HANDLE_MISSING",
                        "CREDENTIAL_VALUE_REQUESTED",
                        "ENDPOINT_HANDLE_MISSING",
                        "SCHEMA_REHEARSAL_MISSING",
                        "MANUAL_WINDOW_NOT_OPEN"
                );
        assertThat(receipt.echoedDryRunResponseFields())
                .contains(
                        "connectionAttempted",
                        "credentialValueRead",
                        "externalRequestSent",
                        "schemaMigrationExecuted"
                );
        assertThat(receipt.reusedNoGoConditions())
                .contains(
                        "CREDENTIAL_VALUE_REQUIRED",
                        "APPROVAL_LEDGER_WRITE_REQUIRED",
                        "MINI_KV_STORAGE_BACKEND_REQUIRED"
                );
        assertThat(receipt.forbiddenPrecheckOperations())
                .contains(
                        "instantiate managed audit adapter client",
                        "read credential value",
                        "send external managed audit request",
                        "write approval ledger"
                );
        assertThat(receipt.envHandlesEchoed()).isTrue();
        assertThat(receipt.failureTaxonomyEchoed()).isTrue();
        assertThat(receipt.dryRunResponseShapeEchoed()).isTrue();
        assertThat(receipt.disabledClientBoundaryEchoed()).isTrue();
        assertThat(receipt.readOnlyPrecheckBoundaryEchoed()).isTrue();
        assertThat(receipt.readyForNodeV254DisabledAdapterClientUpstreamEchoVerification()).isTrue();
        assertThat(receipt.readyForManagedAuditSandboxAdapterConnection()).isFalse();
        assertThat(receipt.readyForProductionAudit()).isFalse();
        assertThat(receipt.nodeMayTreatAsProductionAuditRecord()).isFalse();
        assertThat(receipt.receiptWarnings()).isEmpty();
        assertThat(receipt.receiptDigest()).startsWith("sha256:");
        assertThat(receipt.nodeV254Prerequisites())
                .contains(
                        "Java v102 disabled adapter client precheck echo receipt is present",
                        "mini-kv v111 non-participation receipt is present",
                        "UPSTREAM_ACTIONS_ENABLED remains false"
                );
        assertThat(rehearsal.verificationHint().schemaFields())
                .contains("managedAuditSandboxConnectionDisabledAdapterClientPrecheckEchoReceipt");
        assertThat(rehearsal.verificationHint().warningDigestInputs())
                .contains(
                        "managedAuditSandboxConnectionDisabledAdapterClientPrecheckEchoReceiptWarnings",
                        "sandboxConnectionDisabledAdapterClientPrecheckEchoReceiptDigest",
                        "sandboxConnectionDisabledAdapterClientPrecheckApprovalLedgerWrittenByJava"
                );
        assertThat(rehearsal.verificationHint().proofClaims())
                .contains(
                        "managedAuditSandboxConnectionDisabledAdapterClientPrecheckEchoReceipt.precheckShape.requiredEnvHandleCount=5",
                        "managedAuditSandboxConnectionDisabledAdapterClientPrecheckEchoReceipt.javaExecutionBoundary.approvalLedgerWrittenByJava=false"
                );
        assertThat(rehearsal.verificationHint().nodeVerificationActions())
                .contains(
                        "Compare managedAuditSandboxConnectionDisabledAdapterClientPrecheckEchoReceipt.consumedByNodeDisabledAdapterClientPrecheckProfile with Node v252",
                        "Require managedAuditSandboxConnectionDisabledAdapterClientPrecheckEchoReceipt.readyForNodeV254DisabledAdapterClientUpstreamEchoVerification=true before Node v254"
                );

        ReleaseApprovalRehearsalResponse repeated =
                service.releaseApprovalRehearsal(paddedHeaderBackedRehearsalRequest());
        assertThat(repeated.managedAuditSandboxConnectionDisabledAdapterClientPrecheckEchoReceipt().receiptDigest())
                .isEqualTo(receipt.receiptDigest());
    }


    @Test
    void releaseApprovalRehearsalExposesFakeTransportDryRunPacketEchoMarker() {
        OpsEvidenceService service = readOnlyFixtureService(
                failedEventSummaryService,
                outboxRepository,
                idempotencyStore
        );

        ReleaseApprovalRehearsalResponse rehearsal =
                service.releaseApprovalRehearsal(headerBackedRehearsalRequest());

        ReleaseApprovalRehearsalResponseRecords.RehearsalManagedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker
                marker = rehearsal.managedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker();
        assertThat(marker.markerVersion())
                .isEqualTo(
                        "java-release-approval-rehearsal-managed-audit-sandbox-connection-fake-transport-dry-run-packet-echo-marker.v1"
                );
        assertThat(marker.sourceDisabledAdapterClientPrecheckEchoReceiptSchemaVersion())
                .isEqualTo("java-release-approval-rehearsal-response-schema.v24");
        assertThat(marker.consumedByNodeFakeTransportDryRunPacketVersion()).isEqualTo("Node v255");
        assertThat(marker.consumedByNodeFakeTransportDryRunPacketProfile())
                .isEqualTo(
                        "managed-audit-manual-sandbox-connection-fake-transport-adapter-dry-run-verification-packet.v1"
                );
        assertThat(marker.consumedByNodeFakeTransportDryRunPacketEndpoint())
                .isEqualTo(
                        "/api/v1/audit/managed-audit-manual-sandbox-connection-fake-transport-adapter-dry-run-verification-packet"
                );
        assertThat(marker.consumedByNodeFakeTransportDryRunPacketState())
                .isEqualTo("fake-transport-adapter-dry-run-verification-packet-ready");
        assertThat(marker.consumedByNodeFakeTransportPacketArchiveVerificationVersion()).isEqualTo("Node v256");
        assertThat(marker.consumedByNodeFakeTransportPacketArchiveVerificationProfile())
                .isEqualTo(
                        "managed-audit-manual-sandbox-connection-fake-transport-packet-archive-verification.v1"
                );
        assertThat(marker.consumedByNodeFakeTransportPacketArchiveVerificationState())
                .isEqualTo("fake-transport-packet-archive-verification-ready");
        assertThat(marker.nextNodeFakeTransportPacketUpstreamEchoVerificationVersion()).isEqualTo("Node v257");
        assertThat(marker.nextNodeFakeTransportPacketUpstreamEchoVerificationProfile())
                .isEqualTo(
                        "managed-audit-manual-sandbox-connection-fake-transport-packet-upstream-echo-verification.v1"
                );
        assertThat(marker.nodeV257MayConsume()).isTrue();
        assertThat(marker.packetMode()).isEqualTo("fake-transport-adapter-dry-run-verification-only");
        assertThat(marker.sourceSpan()).isEqualTo("Node v253 + Node v254 + Node v255 + Node v256");
        assertThat(marker.requestShape().requestId()).isEqualTo("managed-audit-v255-fake-transport-dry-run");
        assertThat(marker.requestShape().transportKind()).isEqualTo("fake-in-memory");
        assertThat(marker.requestShape().credentialHandle())
                .isEqualTo("ORDEROPS_MANAGED_AUDIT_SANDBOX_CREDENTIAL_HANDLE");
        assertThat(marker.requestShape().endpointHandle())
                .isEqualTo("ORDEROPS_MANAGED_AUDIT_SANDBOX_ENDPOINT_HANDLE");
        assertThat(marker.requestShape().ownerApprovalArtifactId())
                .isEqualTo("owner-approval-artifact-review-only");
        assertThat(marker.requestShape().timeoutBudgetMs()).isEqualTo(15000);
        assertThat(marker.requestShape().dryRun()).isTrue();
        assertThat(marker.requestShape().fakeTransportOnly()).isTrue();
        assertThat(marker.requestShape().credentialValueIncluded()).isFalse();
        assertThat(marker.requestShape().rawEndpointUrlIncluded()).isFalse();
        assertThat(marker.requestShape().payloadMayContainSecrets()).isFalse();
        assertThat(marker.requestShape().requestShapeFieldCount()).isEqualTo(8);
        assertThat(marker.responseShape().status()).isEqualTo("fake-transport-dry-run-accepted");
        assertThat(marker.responseShape().code()).isEqualTo("TEST_ONLY_FAKE_TRANSPORT_DRY_RUN");
        assertThat(marker.responseShape().fakeTransportOnly()).isTrue();
        assertThat(marker.responseShape().timeoutBudgetMs()).isEqualTo(15000);
        assertThat(marker.responseShape().connectionAttempted()).isFalse();
        assertThat(marker.responseShape().externalRequestSent()).isFalse();
        assertThat(marker.responseShape().credentialValueRead()).isFalse();
        assertThat(marker.responseShape().schemaMigrationExecuted()).isFalse();
        assertThat(marker.responseShape().productionRecordWritten()).isFalse();
        assertThat(marker.responseShape().responseShapeFieldCount()).isEqualTo(9);
        assertThat(marker.timeoutBoundary().finiteBudget()).isTrue();
        assertThat(marker.timeoutBoundary().budgetSource()).isEqualTo("operator-review-field");
        assertThat(marker.timeoutBoundary().budgetSpent()).isFalse();
        assertThat(marker.timeoutBoundary().timerStarted()).isFalse();
        assertThat(marker.timeoutBoundary().timeoutClassifiable()).isTrue();
        assertThat(marker.failureMappingShape().sourceFailureMappingCount()).isEqualTo(6);
        assertThat(marker.failureMappingShape().mappedFailureCount()).isEqualTo(6);
        assertThat(marker.failureMappingShape().guardConditionCount()).isEqualTo(7);
        assertThat(marker.failureMappingShape().allFailuresNonRetryable()).isTrue();
        assertThat(marker.failureMappingShape().credentialValueRequestStillBlocked()).isTrue();
        assertThat(marker.failureMappingShape().manualWindowClosedStillBlocked()).isTrue();
        assertThat(marker.failureMappingShape().failureMappingCovered()).isTrue();
        assertThat(marker.cleanupBoundary().inMemoryOnly()).isTrue();
        assertThat(marker.cleanupBoundary().temporaryDirectoryCreated()).isFalse();
        assertThat(marker.cleanupBoundary().temporaryFileCreated()).isFalse();
        assertThat(marker.cleanupBoundary().cleanupRequired()).isFalse();
        assertThat(marker.cleanupBoundary().cleanupArtifactCount()).isEqualTo(0);
        assertThat(marker.cleanupBoundary().cleanupVerified()).isTrue();
        assertThat(marker.cleanupBoundary().nodeServiceStartedByPacket()).isFalse();
        assertThat(marker.sideEffectBoundary().connectionAttempted()).isFalse();
        assertThat(marker.sideEffectBoundary().externalRequestSent()).isFalse();
        assertThat(marker.sideEffectBoundary().credentialValueRead()).isFalse();
        assertThat(marker.sideEffectBoundary().credentialValueStored()).isFalse();
        assertThat(marker.sideEffectBoundary().schemaMigrationExecuted()).isFalse();
        assertThat(marker.sideEffectBoundary().productionRecordWritten()).isFalse();
        assertThat(marker.sideEffectBoundary().approvalLedgerWritten()).isFalse();
        assertThat(marker.sideEffectBoundary().managedAuditStateWritten()).isFalse();
        assertThat(marker.sideEffectBoundary().sqlExecuted()).isFalse();
        assertThat(marker.sideEffectBoundary().javaStarted()).isFalse();
        assertThat(marker.sideEffectBoundary().miniKvStarted()).isFalse();
        assertThat(marker.sideEffectBoundary().externalAuditServiceStarted()).isFalse();
        assertThat(marker.readyForNodeV257FakeTransportPacketUpstreamEchoVerification()).isTrue();
        assertThat(marker.readyForManagedAuditSandboxAdapterConnection()).isFalse();
        assertThat(marker.readyForProductionAudit()).isFalse();
        assertThat(marker.readyForProductionWindow()).isFalse();
        assertThat(marker.nodeMayTreatAsProductionAuditRecord()).isFalse();
        assertThat(marker.markerWarnings()).isEmpty();
        assertThat(marker.markerDigest()).startsWith("sha256:");
        assertThat(marker.echoedRequestFieldNames())
                .contains("credentialValueIncluded", "rawEndpointUrlIncluded", "payloadMayContainSecrets");
        assertThat(marker.echoedResponseFieldNames())
                .contains(
                        "connectionAttempted",
                        "externalRequestSent",
                        "credentialValueRead",
                        "schemaMigrationExecuted",
                        "productionRecordWritten"
                );
        assertThat(marker.echoedFailureMappingCodes())
                .containsExactly(
                        "ADAPTER_CLIENT_DISABLED",
                        "CREDENTIAL_HANDLE_MISSING",
                        "CREDENTIAL_VALUE_REQUESTED",
                        "ENDPOINT_HANDLE_MISSING",
                        "SCHEMA_REHEARSAL_MISSING",
                        "MANUAL_WINDOW_NOT_OPEN"
                );
        assertThat(marker.forbiddenFakeTransportOperations())
                .contains(
                        "instantiate real managed audit adapter client",
                        "include raw endpoint URL",
                        "create temporary dry-run directory or file"
                );
        assertThat(marker.nodeV257Prerequisites())
                .contains(
                        "Java v103 fake transport dry-run packet echo marker is present",
                        "mini-kv v112 fake transport dry-run packet non-participation receipt is present",
                        "UPSTREAM_ACTIONS_ENABLED remains false"
                );
        assertThat(rehearsal.verificationHint().schemaFields())
                .contains("managedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker");
        assertThat(rehearsal.verificationHint().warningDigestInputs())
                .contains(
                        "managedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarkerWarnings",
                        "sandboxConnectionFakeTransportDryRunPacketEchoMarkerDigest",
                        "sandboxConnectionFakeTransportDryRunPacketCleanupArtifactCount"
                );
        assertThat(rehearsal.verificationHint().proofClaims())
                .contains(
                        "managedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker.requestShape.requestShapeFieldCount=8",
                        "managedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker.responseShape.responseShapeFieldCount=9",
                        "managedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker.cleanupBoundary.cleanupArtifactCount=0"
                );
        assertThat(rehearsal.verificationHint().nodeVerificationActions())
                .contains(
                        "Compare managedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker.consumedByNodeFakeTransportDryRunPacketProfile with Node v255",
                        "Require managedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker.readyForNodeV257FakeTransportPacketUpstreamEchoVerification=true before Node v257"
                );

        ReleaseApprovalRehearsalResponse repeated =
                service.releaseApprovalRehearsal(paddedHeaderBackedRehearsalRequest());
        assertThat(repeated.managedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker().markerDigest())
                .isEqualTo(marker.markerDigest());
    }

}
