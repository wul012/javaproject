package com.codexdemo.orderplatform.ops;

import static com.codexdemo.orderplatform.ops.OpsEvidenceServiceTestFixtures.headerBackedRehearsalRequest;
import static com.codexdemo.orderplatform.ops.OpsEvidenceServiceTestFixtures.paddedHeaderBackedRehearsalRequest;
import static com.codexdemo.orderplatform.ops.OpsEvidenceServiceTestFixtures.readOnlyFixtureService;
import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.notification.FailedEventSummaryService;
import com.codexdemo.orderplatform.order.IdempotencyStore;
import com.codexdemo.orderplatform.outbox.OutboxRepository;
import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoRecords
        .RehearsalManagedAuditSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoReceipt;
import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoRecords
        .RehearsalSandboxEndpointCredentialResolverApprovalRequiredImplementationBoundaryReadiness;
import org.junit.jupiter.api.Test;

class OpsEvidenceServiceApprovalRequiredImplementationReadinessEchoTests {

    private final FailedEventSummaryService failedEventSummaryService =
            org.mockito.Mockito.mock(FailedEventSummaryService.class);
    private final OutboxRepository outboxRepository = org.mockito.Mockito.mock(OutboxRepository.class);
    private final IdempotencyStore idempotencyStore = org.mockito.Mockito.mock(IdempotencyStore.class);

    @Test
    void releaseApprovalRehearsalAddsApprovalRequiredImplementationReadinessEchoReceipt() {
        OpsEvidenceService service = readOnlyFixtureService(
                failedEventSummaryService,
                outboxRepository,
                idempotencyStore
        );

        ReleaseApprovalRehearsalResponse rehearsal =
                service.releaseApprovalRehearsal(headerBackedRehearsalRequest());
        RehearsalManagedAuditSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoReceipt receipt =
                rehearsal
                        .managedAuditSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoReceipt();

        assertThat(receipt.receiptVersion())
                .isEqualTo(
                        "java-release-approval-rehearsal-managed-audit-sandbox-endpoint-credential-resolver-approval-required-implementation-readiness-echo-receipt.v1"
                );
        assertThat(receipt.sourceDisabledImplementationCandidateEchoReceiptVersion())
                .isEqualTo(
                        "java-release-approval-rehearsal-managed-audit-sandbox-endpoint-credential-resolver-disabled-implementation-candidate-echo-receipt.v2"
                );
        assertThat(receipt.sourceDisabledImplementationCandidateEchoReceiptSchemaVersion())
                .isEqualTo("java-release-approval-rehearsal-response-schema.v34");
        assertThat(receipt.consumedByNodeCredentialResolverApprovalRequiredImplementationReadinessReviewVersion())
                .isEqualTo("Node v281");
        assertThat(receipt.consumedByNodeCredentialResolverApprovalRequiredImplementationReadinessReviewProfile())
                .isEqualTo(
                        "managed-audit-manual-sandbox-connection-credential-resolver-approval-required-implementation-readiness-review.v1"
                );
        assertThat(receipt.consumedByNodeCredentialResolverApprovalRequiredImplementationReadinessReviewEndpoint())
                .isEqualTo(
                        "/api/v1/audit/managed-audit-manual-sandbox-connection-credential-resolver-approval-required-implementation-readiness-review"
                );
        assertThat(receipt.consumedByNodeCredentialResolverApprovalRequiredImplementationReadinessReviewMarkdownEndpoint())
                .endsWith("?format=markdown");
        assertThat(receipt.consumedByNodeCredentialResolverApprovalRequiredImplementationReadinessReviewState())
                .isEqualTo("credential-resolver-approval-required-implementation-readiness-review-ready");
        assertThat(receipt.sourceNodeApprovalRequiredBoundaryUpstreamEchoVerificationVersion()).isEqualTo("Node v275");
        assertThat(receipt.sourceNodeApprovalRequiredBoundaryUpstreamEchoVerificationState())
                .isEqualTo("credential-resolver-approval-required-boundary-upstream-echo-verification-ready");
        assertThat(receipt.nodeV282MayConsume()).isTrue();
        assertThat(receipt.implementationReadinessEchoMode())
                .isEqualTo("java-v116-credential-resolver-approval-required-implementation-readiness-echo-only");
        assertThat(receipt.sourceSpan()).isEqualTo("Node v281");

        assertThat(receipt.sourceNodeV281().sourceVersion()).isEqualTo("Node v281");
        assertThat(receipt.sourceNodeV281()
                .readyForManagedAuditManualSandboxConnectionCredentialResolverApprovalRequiredImplementationReadinessReview())
                .isTrue();
        assertThat(receipt.sourceNodeV281().readyForJavaV116MiniKvV122Echo()).isTrue();
        assertThat(receipt.sourceNodeV281().readyForManagedAuditResolverImplementation()).isFalse();
        assertThat(receipt.sourceNodeV281().summary().checkCount()).isEqualTo(21);
        assertThat(receipt.sourceNodeV281().summary().passedCheckCount()).isEqualTo(21);
        assertThat(receipt.sourceNodeV281().summary().boundaryCount()).isEqualTo(6);
        assertThat(receipt.sourceNodeV281().summary().requiredArtifactCount()).isEqualTo(18);

        assertThat(receipt.sourceNodeV275().sourceSpan()).isEqualTo("Node v274 + Java v115 + mini-kv v121");
        assertThat(receipt.sourceNodeV275().sourceCheckCount()).isEqualTo(25);
        assertThat(receipt.sourceNodeV275().sourcePassedCheckCount()).isEqualTo(25);
        assertThat(receipt.sourceNodeV275().approvalRequiredBoundaryCodes())
                .containsExactly(
                        "CREDENTIAL_HANDLE",
                        "ENDPOINT_HANDLE",
                        "OPERATOR_APPROVAL",
                        "ROLLBACK_BOUNDARY",
                        "SCHEMA_MIGRATION_POLICY",
                        "AUDIT_LEDGER_WRITE_POLICY"
                );
        assertThat(receipt.sourceNodeV275().approvalRequiredRequirementCodes())
                .containsExactly(
                        "CREDENTIAL_HANDLE_BOUNDARY_MISSING",
                        "ENDPOINT_HANDLE_BOUNDARY_MISSING",
                        "OPERATOR_APPROVAL_BOUNDARY_MISSING",
                        "ROLLBACK_BOUNDARY_MISSING",
                        "SCHEMA_MIGRATION_POLICY_MISSING",
                        "AUDIT_LEDGER_WRITE_POLICY_MISSING"
                );
        assertRuntimeBlocked(
                receipt.sourceNodeV275().realResolverImplementationAllowed(),
                receipt.sourceNodeV275().executionAllowed(),
                receipt.sourceNodeV275().credentialValueRead(),
                receipt.sourceNodeV275().rawEndpointUrlParsed(),
                receipt.sourceNodeV275().connectsManagedAudit(),
                receipt.sourceNodeV275().approvalLedgerWritten(),
                receipt.sourceNodeV275().automaticUpstreamStart()
        );

        assertThat(receipt.readinessReview().reviewMode())
                .isEqualTo("node-v281-approval-required-implementation-readiness-review-only");
        assertThat(receipt.readinessReview().implementationStage())
                .isEqualTo("blocked-until-java-v116-mini-kv-v122-and-node-v282");
        assertThat(receipt.readinessReview().allApprovalRequiredBoundariesEchoReady()).isTrue();
        assertThat(receipt.readinessReview().allApprovalRequiredBoundariesImplementationBlocked()).isTrue();
        assertThat(receipt.readinessReview().allRequiredArtifactsNamed()).isTrue();
        assertThat(receipt.readinessReview().nodeV282VerificationRequired()).isTrue();

        assertThat(receipt.boundaryReadiness())
                .extracting(RehearsalSandboxEndpointCredentialResolverApprovalRequiredImplementationBoundaryReadiness::code)
                .containsExactly(
                        "CREDENTIAL_HANDLE",
                        "ENDPOINT_HANDLE",
                        "OPERATOR_APPROVAL",
                        "ROLLBACK_BOUNDARY",
                        "SCHEMA_MIGRATION_POLICY",
                        "AUDIT_LEDGER_WRITE_POLICY"
                );
        assertThat(receipt.boundaryReadiness())
                .allMatch(boundary -> boundary.readinessState().equals("echo-ready-implementation-blocked")
                        && boundary.implementationDisposition().equals("requires-explicit-follow-up-artifacts")
                        && boundary.requiredArtifacts().size() == 3
                        && boundary.prohibitedRuntimeActions().size() == 3
                        && boundary.readyForJavaV116Echo()
                        && boundary.readyForMiniKvV122Receipt()
                        && !boundary.readyForNodeV282Verification()
                        && !boundary.readyForRuntimeImplementation());
        assertThat(receipt.requiredArtifactIds()).hasSize(18)
                .contains(
                        "credential-handle-review-id",
                        "allowlist-review-status",
                        "approval-correlation-marker",
                        "rollback-abort-marker",
                        "sql-execution-prohibition-marker",
                        "approval-ledger-write-policy-id"
                );

        assertThat(receipt.checks().sourceNodeV275Ready()).isTrue();
        assertThat(receipt.checks().boundaryReadinessCountExpected()).isTrue();
        assertThat(receipt.checks().allBoundariesEchoReadyForJavaV116()).isTrue();
        assertThat(receipt.checks().allBoundariesStillBlockedForRuntimeImplementation()).isTrue();

        assertRuntimeBlocked(
                receipt.sideEffectBoundary().readyForManagedAuditResolverImplementation(),
                receipt.sideEffectBoundary().executionAllowed(),
                receipt.sideEffectBoundary().credentialValueRead(),
                receipt.sideEffectBoundary().rawEndpointUrlParsed(),
                receipt.sideEffectBoundary().connectsManagedAudit(),
                receipt.sideEffectBoundary().approvalLedgerWritten(),
                receipt.sideEffectBoundary().automaticUpstreamStart()
        );
        assertThat(receipt.sideEffectBoundary().sqlExecuted()).isFalse();
        assertThat(receipt.sideEffectBoundary().schemaMigrationExecuted()).isFalse();
        assertThat(receipt.sideEffectBoundary().javaStartedNodeOrMiniKv()).isFalse();

        assertThat(receipt.echoWorkflowReadySteps())
                .containsExactly(
                        "sourceNodeV281Echoed",
                        "sourceNodeV275Echoed",
                        "readinessReviewEchoed",
                        "boundaryReadinessEchoed",
                        "requiredArtifactsEchoed",
                        "javaV116EchoHintsEchoed",
                        "noCredentialBoundaryEchoed",
                        "noRawEndpointBoundaryEchoed",
                        "noResolverRuntimeBoundaryEchoed",
                        "noConnectionBoundaryEchoed",
                        "noWriteBoundaryEchoed",
                        "noAutoStartBoundaryEchoed"
                );
        assertThat(receipt.echoWorkflowMissingSteps()).isEmpty();
        assertThat(receipt.readyForNodeV282CredentialResolverApprovalRequiredImplementationReadinessVerification())
                .isTrue();
        assertThat(receipt.readyForJavaV116MiniKvV122Echo()).isTrue();
        assertThat(receipt.readyForManagedAuditResolverImplementation()).isFalse();
        assertThat(receipt.nodeWarningCodes())
                .containsExactly("IMPLEMENTATION_STILL_BLOCKED", "JAVA_V116_ECHO_ONLY");
        assertThat(receipt.nodeRecommendationCodes())
                .containsExactly("RUN_PARALLEL_JAVA_V116_MINI_KV_V122",
                        "VERIFY_WITH_NODE_V282_BEFORE_IMPLEMENTATION_DRAFT");
        assertThat(receipt.receiptWarnings()).isEmpty();
        assertThat(receipt.receiptDigest()).startsWith("sha256:");

        assertThat(rehearsal.verificationHint().responseSchemaVersion())
                .isEqualTo("java-release-approval-rehearsal-response-schema.v35");
        assertThat(rehearsal.verificationHint().schemaFields())
                .contains("managedAuditSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoReceipt");
        assertThat(rehearsal.verificationHint().warningDigestInputs())
                .contains(
                        "managedAuditSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoReceiptWarnings",
                        "sandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoReceiptDigest",
                        "sandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessRequiredArtifactCount",
                        "sandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessApprovalLedgerWritten",
                        "sandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessAutomaticUpstreamStart"
                );
        assertThat(rehearsal.verificationHint().proofClaims())
                .contains(
                        "managedAuditSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoReceipt.boundaryReadiness.size=6",
                        "managedAuditSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoReceipt.requiredArtifactIds.size=18",
                        "managedAuditSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoReceipt.readyForManagedAuditResolverImplementation=false"
                );
        assertThat(rehearsal.verificationHint().nodeVerificationActions())
                .contains(
                        "Compare managedAuditSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoReceipt.consumedByNodeCredentialResolverApprovalRequiredImplementationReadinessReviewProfile with Node v281",
                        "Require managedAuditSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoReceipt.readyForNodeV282CredentialResolverApprovalRequiredImplementationReadinessVerification=true before Node v282",
                        "Verify managedAuditSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoReceipt.requiredArtifactIds.size=18"
                );

        ReleaseApprovalRehearsalResponse repeated =
                service.releaseApprovalRehearsal(paddedHeaderBackedRehearsalRequest());
        assertThat(repeated
                .managedAuditSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoReceipt()
                .receiptDigest()).isEqualTo(receipt.receiptDigest());
    }

    private static void assertRuntimeBlocked(boolean... flags) {
        assertThat(flags).containsOnly(false);
    }
}
