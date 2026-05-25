package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoRecords
        .RehearsalManagedAuditSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceipt;
import org.junit.jupiter.api.Test;

class OpsEvidenceServiceCredentialResolverDisabledImplementationCandidateEchoTests extends OpsEvidenceServiceRehearsalTestSupport {

    @Test
    void releaseApprovalRehearsalAddsSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceipt() {
        OpsEvidenceService service = readOnlyFixtureService();

        ReleaseApprovalRehearsalResponse rehearsal =
                service.releaseApprovalRehearsal(headerBackedRehearsalRequest());

        RehearsalManagedAuditSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceipt receipt =
                rehearsal.managedAuditSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceipt();
        assertThat(receipt.receiptVersion())
                .isEqualTo(
                        "java-release-approval-rehearsal-managed-audit-sandbox-endpoint-credential-resolver-disabled-implementation-candidate-echo-receipt.v2"
                );
        assertThat(receipt.sourcePreImplementationPlanIntakeEchoReceiptVersion())
                .isEqualTo(
                        "java-release-approval-rehearsal-managed-audit-sandbox-endpoint-credential-resolver-pre-implementation-plan-intake-echo-receipt.v1"
                );
        assertThat(receipt.sourcePreImplementationPlanIntakeEchoReceiptSchemaVersion())
                .isEqualTo("java-release-approval-rehearsal-response-schema.v32");
        assertThat(receipt.consumedByNodeCredentialResolverDisabledImplementationCandidateReviewVersion())
                .isEqualTo("Node v273");
        assertThat(receipt.consumedByNodeCredentialResolverDisabledImplementationCandidateReviewProfile())
                .isEqualTo(
                        "managed-audit-manual-sandbox-connection-credential-resolver-disabled-implementation-candidate-review.v1"
                );
        assertThat(receipt.consumedByNodeCredentialResolverDisabledImplementationCandidateReviewEndpoint())
                .isEqualTo(
                        "/api/v1/audit/managed-audit-manual-sandbox-connection-credential-resolver-disabled-implementation-candidate-review"
                );
        assertThat(receipt.consumedByNodeCredentialResolverDisabledImplementationCandidateReviewMarkdownEndpoint())
                .isEqualTo(
                        "/api/v1/audit/managed-audit-manual-sandbox-connection-credential-resolver-disabled-implementation-candidate-review?format=markdown"
                );
        assertThat(receipt.consumedByNodeCredentialResolverDisabledImplementationCandidateReviewState())
                .isEqualTo("credential-resolver-disabled-implementation-candidate-review-ready");
        assertThat(receipt.sourceNodeCredentialResolverPreImplementationPlanIntakeUpstreamEchoVerificationVersion())
                .isEqualTo("Node v272");
        assertThat(receipt.sourceNodeCredentialResolverPreImplementationPlanIntakeUpstreamEchoVerificationProfile())
                .isEqualTo(
                        "managed-audit-manual-sandbox-connection-credential-resolver-pre-implementation-plan-intake-upstream-echo-verification.v1"
                );
        assertThat(receipt.nodeV274MayConsume()).isTrue();
        assertThat(receipt.disabledCandidateEchoMode())
                .isEqualTo("java-v115-credential-resolver-approval-required-boundary-echo-refinement-only");
        assertThat(receipt.sourceSpan()).isEqualTo("Node v273 disabled implementation candidate review");

        assertThat(receipt.sourceNodeV273().sourceVersion()).isEqualTo("Node v273");
        assertThat(receipt.sourceNodeV273().profileVersion())
                .isEqualTo(
                        "managed-audit-manual-sandbox-connection-credential-resolver-disabled-implementation-candidate-review.v1"
                );
        assertThat(receipt.sourceNodeV273().reviewState())
                .isEqualTo("credential-resolver-disabled-implementation-candidate-review-ready");
        assertThat(receipt.sourceNodeV273()
                .readyForManagedAuditManualSandboxConnectionCredentialResolverDisabledImplementationCandidateReview())
                .isTrue();
        assertThat(receipt.sourceNodeV273().disabledImplementationCandidateReviewOnly()).isTrue();
        assertThat(receipt.sourceNodeV273().readOnlyCandidateReview()).isTrue();
        assertThat(receipt.sourceNodeV273().readyForDisabledResolverInterfaceCandidate()).isTrue();
        assertThat(receipt.sourceNodeV273().readyForManagedAuditSandboxAdapterConnection()).isFalse();
        assertThat(receipt.sourceNodeV273().realResolverImplementationAllowed()).isFalse();
        assertThat(receipt.sourceNodeV273().executionAllowed()).isFalse();
        assertThat(receipt.sourceNodeV273().connectsManagedAudit()).isFalse();
        assertThat(receipt.sourceNodeV273().credentialValueRead()).isFalse();
        assertThat(receipt.sourceNodeV273().rawEndpointUrlParsed()).isFalse();
        assertThat(receipt.sourceNodeV273().externalRequestSent()).isFalse();
        assertThat(receipt.sourceNodeV273().secretProviderInstantiated()).isFalse();
        assertThat(receipt.sourceNodeV273().resolverClientInstantiated()).isFalse();
        assertThat(receipt.sourceNodeV273().schemaMigrationExecuted()).isFalse();
        assertThat(receipt.sourceNodeV273().approvalLedgerWritten()).isFalse();
        assertThat(receipt.sourceNodeV273().automaticUpstreamStart()).isFalse();

        assertThat(receipt.sourceNodeV273().sourceNodeV272().sourceVersion()).isEqualTo("Node v272");
        assertThat(receipt.sourceNodeV273().sourceNodeV272().verificationState())
                .isEqualTo("credential-resolver-pre-implementation-plan-intake-upstream-echo-verification-ready");
        assertThat(receipt.sourceNodeV273().sourceNodeV272().readyForPlanIntakeUpstreamEchoVerification())
                .isTrue();
        assertThat(receipt.sourceNodeV273().sourceNodeV272().readOnlyUpstreamEchoVerification()).isTrue();
        assertThat(receipt.sourceNodeV273().sourceNodeV272().planIntakeEchoVerificationOnly()).isTrue();
        assertThat(receipt.sourceNodeV273().sourceNodeV272().sourceSpan())
                .isEqualTo("Node v270 + Java v112 + mini-kv v119");
        assertThat(receipt.sourceNodeV273().sourceNodeV272().boundaryCount()).isEqualTo(10);
        assertThat(receipt.sourceNodeV273().sourceNodeV272().definedBoundaryCount()).isEqualTo(10);
        assertThat(receipt.sourceNodeV273().sourceNodeV272().missingBoundaryCount()).isZero();
        assertThat(receipt.sourceNodeV273().sourceNodeV272().sourceNodeV270Ready()).isTrue();
        assertThat(receipt.sourceNodeV273().sourceNodeV272().javaV112EchoReady()).isTrue();
        assertThat(receipt.sourceNodeV273().sourceNodeV272().miniKvV119NonParticipationReady()).isTrue();
        assertThat(receipt.sourceNodeV273().sourceNodeV272().credentialBoundaryAligned()).isTrue();
        assertThat(receipt.sourceNodeV273().sourceNodeV272().rawEndpointBoundaryAligned()).isTrue();
        assertThat(receipt.sourceNodeV273().sourceNodeV272().resolverBoundaryAligned()).isTrue();
        assertThat(receipt.sourceNodeV273().sourceNodeV272().connectionBoundaryAligned()).isTrue();
        assertThat(receipt.sourceNodeV273().sourceNodeV272().writeBoundaryAligned()).isTrue();
        assertThat(receipt.sourceNodeV273().sourceNodeV272().autoStartBoundaryAligned()).isTrue();
        assertThat(receipt.sourceNodeV273().sourceNodeV272().checkCount()).isEqualTo(22);
        assertThat(receipt.sourceNodeV273().sourceNodeV272().passedCheckCount()).isEqualTo(22);
        assertThat(receipt.sourceNodeV273().sourceNodeV272().sourceCheckCount()).isEqualTo(26);
        assertThat(receipt.sourceNodeV273().sourceNodeV272().sourcePassedCheckCount()).isEqualTo(26);

        assertThat(receipt.candidate().candidateVersion())
                .isEqualTo("node-v273-credential-resolver-disabled-implementation-candidate-review.v1");
        assertThat(receipt.candidate().candidateMode())
                .isEqualTo("disabled-interface-and-fake-wiring-review-only");
        assertThat(receipt.candidate().candidateDecisionCount()).isEqualTo(10);
        assertThat(receipt.candidate().candidateReadyDecisionCount()).isEqualTo(4);
        assertThat(receipt.candidate().approvalRequiredDecisionCount()).isEqualTo(6);
        assertThat(receipt.candidate().disabledInterfaceCandidateAllowed()).isTrue();
        assertThat(receipt.candidate().fakeWiringReviewAllowed()).isTrue();
        assertThat(receipt.candidate().realResolverImplementationAllowed()).isFalse();
        assertThat(receipt.candidate().credentialValueReadAllowed()).isFalse();
        assertThat(receipt.candidate().rawEndpointUrlParseAllowed()).isFalse();
        assertThat(receipt.candidate().externalRequestAllowed()).isFalse();
        assertThat(receipt.candidate().schemaMigrationAllowed()).isFalse();
        assertThat(receipt.candidate().approvalLedgerWriteAllowed()).isFalse();
        assertThat(receipt.candidate().automaticUpstreamStartAllowed()).isFalse();
        assertThat(receipt.candidate().decisions()).hasSize(10);
        assertThat(receipt.candidate().decisions())
                .extracting(decision -> decision.code())
                .containsExactly(
                        "PLAN_DOCUMENT",
                        "CREDENTIAL_HANDLE",
                        "ENDPOINT_HANDLE",
                        "DISABLED_SECRET_PROVIDER_STUB",
                        "OPERATOR_APPROVAL",
                        "ROLLBACK_BOUNDARY",
                        "REDACTION_POLICY",
                        "EXTERNAL_REQUEST_SIMULATION",
                        "SCHEMA_MIGRATION_POLICY",
                        "AUDIT_LEDGER_WRITE_POLICY"
                );
        assertThat(receipt.candidateReadyBoundaryCodes())
                .containsExactly(
                        "PLAN_DOCUMENT",
                        "DISABLED_SECRET_PROVIDER_STUB",
                        "REDACTION_POLICY",
                        "EXTERNAL_REQUEST_SIMULATION"
                );
        assertThat(receipt.approvalRequiredBoundaryCodes())
                .containsExactly(
                        "CREDENTIAL_HANDLE",
                        "ENDPOINT_HANDLE",
                        "OPERATOR_APPROVAL",
                        "ROLLBACK_BOUNDARY",
                        "SCHEMA_MIGRATION_POLICY",
                        "AUDIT_LEDGER_WRITE_POLICY"
                );
        assertThat(receipt.approvalRequiredBoundaryExplanations())
                .extracting(ReleaseApprovalSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoRecords.RehearsalSandboxEndpointCredentialResolverApprovalRequiredBoundaryExplanation::code)
                .containsExactly(
                        "CREDENTIAL_HANDLE",
                        "ENDPOINT_HANDLE",
                        "OPERATOR_APPROVAL",
                        "ROLLBACK_BOUNDARY",
                        "SCHEMA_MIGRATION_POLICY",
                        "AUDIT_LEDGER_WRITE_POLICY"
                );
        assertThat(receipt.approvalRequiredBoundaryExplanations())
                .allMatch(explanation -> explanation.evidenceAllowed().equals("approval-required-read-only-evidence")
                        && !explanation.credentialValueReadAllowed()
                        && !explanation.rawEndpointUrlParseAllowed()
                        && !explanation.managedAuditConnectionAllowed()
                        && !explanation.approvalLedgerWriteAllowed()
                        && !explanation.sqlExecutionAllowed()
                        && !explanation.rollbackExecutionAllowed()
                        && !explanation.automaticUpstreamStartAllowed());
        assertThat(receipt.candidate().interfaceShape().requestFields())
                .containsExactly(
                        "credentialHandle",
                        "endpointHandle",
                        "resolverPolicyHandle",
                        "operatorIdentity",
                        "approvalCorrelationId",
                        "manualWindowMarker"
                );
        assertThat(receipt.candidate().interfaceShape().responseFields())
                .containsExactly(
                        "resolverState",
                        "resolvedCredentialValue",
                        "rawEndpointUrl",
                        "redactionApplied",
                        "externalRequestSent",
                        "failureClass",
                        "auditDigest"
                );
        assertThat(receipt.candidate().interfaceShape().failureClasses())
                .containsExactly(
                        "disabled-by-config",
                        "missing-credential-handle",
                        "missing-endpoint-handle",
                        "operator-approval-required",
                        "manual-window-required",
                        "real-runtime-forbidden"
                );
        assertThat(receipt.candidate().interfaceShape().handleOnlyRequest()).isTrue();
        assertThat(receipt.candidate().interfaceShape().includesCredentialValue()).isFalse();
        assertThat(receipt.candidate().interfaceShape().includesRawEndpointUrl()).isFalse();
        assertThat(receipt.candidate().interfaceShape().sendsExternalRequest()).isFalse();
        assertThat(receipt.candidate().fakeWiringReview().fakeWiringReviewOnly()).isTrue();
        assertThat(receipt.candidate().fakeWiringReview().fakeRuntimeInstantiated()).isFalse();
        assertThat(receipt.candidate().fakeWiringReview().realSecretProviderAllowed()).isFalse();
        assertThat(receipt.candidate().fakeWiringReview().realManagedAuditTransportAllowed()).isFalse();
        assertThat(receipt.candidate().fakeWiringReview().externalRequestAllowed()).isFalse();
        assertThat(receipt.candidate().fakeWiringReview().cleanupArtifactCount()).isZero();

        assertThat(receipt.checks().sourceNodeV272Ready()).isTrue();
        assertThat(receipt.checks().allCandidateDecisionsCovered()).isTrue();
        assertThat(receipt.checks().candidateReadyBoundariesLimited()).isTrue();
        assertThat(receipt.checks().approvalRequiredBoundariesPreserved()).isTrue();
        assertThat(receipt.checks().interfaceShapeHandleOnly()).isTrue();
        assertThat(receipt.checks().fakeWiringReviewOnly()).isTrue();
        assertThat(receipt.checks().credentialValueStillForbidden()).isTrue();
        assertThat(receipt.checks().rawEndpointStillForbidden()).isTrue();
        assertThat(receipt.checks().secretProviderRuntimeStillDisabled()).isTrue();
        assertThat(receipt.checks().resolverClientStillDisabled()).isTrue();
        assertThat(receipt.checks().externalRequestStillBlocked()).isTrue();
        assertThat(receipt.checks().schemaMigrationStillBlocked()).isTrue();
        assertThat(receipt.checks().ledgerWriteStillBlocked()).isTrue();
        assertThat(receipt.checks().upstreamActionsStillDisabled()).isTrue();
        assertThat(receipt.checks()
                .readyForManagedAuditManualSandboxConnectionCredentialResolverDisabledImplementationCandidateReview())
                .isTrue();
        assertThat(receipt.sourceNodeV273().summary().checkCount()).isEqualTo(21);
        assertThat(receipt.sourceNodeV273().summary().passedCheckCount()).isEqualTo(21);
        assertThat(receipt.sourceNodeV273().summary().sourceCheckCount()).isEqualTo(22);
        assertThat(receipt.sourceNodeV273().summary().sourcePassedCheckCount()).isEqualTo(22);
        assertThat(receipt.sourceNodeV273().summary().candidateDecisionCount()).isEqualTo(10);
        assertThat(receipt.sourceNodeV273().summary().candidateReadyDecisionCount()).isEqualTo(4);
        assertThat(receipt.sourceNodeV273().summary().approvalRequiredDecisionCount()).isEqualTo(6);
        assertThat(receipt.sourceNodeV273().summary().warningCount()).isEqualTo(2);
        assertThat(receipt.sourceNodeV273().summary().recommendationCount()).isEqualTo(2);

        assertThat(receipt.sideEffectBoundary().disabledImplementationCandidateReviewOnly()).isTrue();
        assertThat(receipt.sideEffectBoundary().readOnlyCandidateReview()).isTrue();
        assertThat(receipt.sideEffectBoundary().fakeWiringReviewOnly()).isTrue();
        assertThat(receipt.sideEffectBoundary().readyForManagedAuditSandboxAdapterConnection()).isFalse();
        assertThat(receipt.sideEffectBoundary().realResolverImplementationAllowed()).isFalse();
        assertThat(receipt.sideEffectBoundary().executionAllowed()).isFalse();
        assertThat(receipt.sideEffectBoundary().connectsManagedAudit()).isFalse();
        assertThat(receipt.sideEffectBoundary().credentialValueRead()).isFalse();
        assertThat(receipt.sideEffectBoundary().rawEndpointUrlParsed()).isFalse();
        assertThat(receipt.sideEffectBoundary().rawEndpointUrlIncluded()).isFalse();
        assertThat(receipt.sideEffectBoundary().externalRequestSent()).isFalse();
        assertThat(receipt.sideEffectBoundary().secretProviderInstantiated()).isFalse();
        assertThat(receipt.sideEffectBoundary().resolverClientInstantiated()).isFalse();
        assertThat(receipt.sideEffectBoundary().fakeRuntimeInstantiated()).isFalse();
        assertThat(receipt.sideEffectBoundary().approvalLedgerWritten()).isFalse();
        assertThat(receipt.sideEffectBoundary().managedAuditStoreWritten()).isFalse();
        assertThat(receipt.sideEffectBoundary().sqlExecuted()).isFalse();
        assertThat(receipt.sideEffectBoundary().schemaMigrationExecuted()).isFalse();
        assertThat(receipt.sideEffectBoundary().automaticUpstreamStart()).isFalse();
        assertThat(receipt.sideEffectBoundary().javaStartedNodeOrMiniKv()).isFalse();

        assertThat(receipt.echoWorkflowReadySteps())
                .containsExactly(
                        "sourceNodeV273Echoed",
                        "sourceNodeV272UpstreamEchoed",
                        "disabledImplementationCandidateEchoed",
                        "candidateDecisionsEchoed",
                        "candidateReadyScopeEchoed",
                        "approvalRequiredScopeEchoed",
                        "approvalRequiredBoundaryExplanationsEchoed",
                        "handleOnlyInterfaceEchoed",
                        "fakeWiringReviewEchoed",
                        "noCredentialBoundaryEchoed",
                        "noRawEndpointBoundaryEchoed",
                        "noResolverRuntimeBoundaryEchoed",
                        "noConnectionBoundaryEchoed",
                        "noWriteBoundaryEchoed",
                        "noAutoStartBoundaryEchoed"
                );
        assertThat(receipt.echoWorkflowMissingSteps()).isEmpty();
        assertThat(receipt.echoWorkflowTemplateApplied()).isTrue();
        assertThat(receipt.readyForNodeV274CredentialResolverDisabledCandidateVerification()).isTrue();
        assertThat(receipt.readyForDisabledResolverInterfaceCandidate()).isTrue();
        assertThat(receipt.readyForManagedAuditSandboxAdapterConnection()).isFalse();
        assertThat(receipt.readyForProductionAudit()).isFalse();
        assertThat(receipt.readyForProductionWindow()).isFalse();
        assertThat(receipt.nodeMayTreatAsProductionAuditRecord()).isFalse();
        assertThat(receipt.nodeWarningCodes())
                .containsExactly("DISABLED_CANDIDATE_REVIEW_ONLY", "APPROVAL_BOUNDARIES_REMAIN");
        assertThat(receipt.nodeRecommendationCodes())
                .containsExactly("RUN_PARALLEL_JAVA_V113_MINI_KV_V120",
                        "VERIFY_WITH_NODE_V274_AFTER_UPSTREAM_ECHO");
        assertThat(receipt.nextRequiredEchoVersions())
                .contains(
                        "Java v113 credential resolver disabled implementation candidate echo receipt",
                        "mini-kv v120 credential resolver disabled non-participation receipt"
                );
        assertThat(receipt.receiptWarnings()).isEmpty();
        assertThat(receipt.receiptDigest()).startsWith("sha256:");

        assertThat(rehearsal.verificationHint().responseSchemaVersion())
                .isEqualTo("java-release-approval-rehearsal-response-schema.v50");
        assertThat(rehearsal.verificationHint().schemaFields())
                .contains("managedAuditSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceipt");
        assertThat(rehearsal.verificationHint().warningDigestInputs())
                .contains(
                        "managedAuditSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceiptWarnings",
                        "sandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceiptDigest",
                        "sandboxEndpointCredentialResolverDisabledImplementationCandidateCandidateReadyDecisionCount",
                        "sandboxEndpointCredentialResolverDisabledImplementationCandidateApprovalRequiredExplanationCount",
                        "sandboxEndpointCredentialResolverDisabledImplementationCandidateApprovalLedgerWritten",
                        "sandboxEndpointCredentialResolverDisabledImplementationCandidateAutomaticUpstreamStart"
                );
        assertThat(rehearsal.verificationHint().proofClaims())
                .contains(
                        "managedAuditSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceipt.candidate.candidateDecisionCount=10",
                        "managedAuditSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceipt.candidate.candidateReadyDecisionCount=4",
                        "managedAuditSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceipt.approvalRequiredBoundaryExplanations.size=6",
                        "managedAuditSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceipt.sideEffectBoundary.approvalLedgerWritten=false",
                        "managedAuditSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceipt.readyForManagedAuditSandboxAdapterConnection=false"
                );
        assertThat(rehearsal.verificationHint().nodeVerificationActions())
                .contains(
                        "Compare managedAuditSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceipt.consumedByNodeCredentialResolverDisabledImplementationCandidateReviewProfile with Node v273",
                        "Require managedAuditSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceipt.readyForNodeV274CredentialResolverDisabledCandidateVerification=true before Node v274",
                        "Verify managedAuditSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceipt.approvalRequiredBoundaryExplanations.size=6",
                        "Keep managedAuditSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceipt.candidate.interfaceShape.includesCredentialValue=false",
                        "Keep managedAuditSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceipt.sideEffectBoundary.automaticUpstreamStart=false"
                );

        ReleaseApprovalRehearsalResponse repeated =
                service.releaseApprovalRehearsal(paddedHeaderBackedRehearsalRequest());
        assertThat(repeated.managedAuditSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceipt()
                .receiptDigest()).isEqualTo(receipt.receiptDigest());
    }
}
