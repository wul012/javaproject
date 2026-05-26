package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoRecords
        .RehearsalManagedAuditSandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceipt;
import org.junit.jupiter.api.Test;

class OpsEvidenceServiceCredentialResolverPreImplementationPlanIntakeEchoTests extends OpsEvidenceServiceRehearsalTestSupport {

    @Test
    void releaseApprovalRehearsalAddsSandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceipt() {
        OpsEvidenceService service = readOnlyFixtureService();

        ReleaseApprovalRehearsalResponse rehearsal =
                service.releaseApprovalRehearsal(headerBackedRehearsalRequest());

        RehearsalManagedAuditSandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceipt receipt =
                rehearsal.managedAuditSandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceipt();
        assertThat(receipt.receiptVersion())
                .isEqualTo(
                        "java-release-approval-rehearsal-managed-audit-sandbox-endpoint-credential-resolver-pre-implementation-plan-intake-echo-receipt.v1"
                );
        assertThat(receipt.sourceProductionReadinessBlockedDecisionEchoReceiptSchemaVersion())
                .isEqualTo("java-release-approval-rehearsal-response-schema.v31");
        assertThat(receipt.consumedByNodeCredentialResolverPreImplementationPlanIntakeVersion())
                .isEqualTo("Node v270");
        assertThat(receipt.consumedByNodeCredentialResolverPreImplementationPlanIntakeProfile())
                .isEqualTo(
                        "managed-audit-manual-sandbox-connection-credential-resolver-pre-implementation-plan-intake.v1"
                );
        assertThat(receipt.consumedByNodeCredentialResolverPreImplementationPlanIntakeEndpoint())
                .isEqualTo(
                        "/api/v1/audit/managed-audit-manual-sandbox-connection-credential-resolver-pre-implementation-plan-intake"
                );
        assertThat(receipt.consumedByNodeCredentialResolverPreImplementationPlanIntakeState())
                .isEqualTo("credential-resolver-pre-implementation-plan-intake-ready");
        assertThat(receipt.sourceNodeCredentialResolverProductionReadinessBlockedDecisionUpstreamEchoVerificationVersion())
                .isEqualTo("Node v269");
        assertThat(receipt.nodeV272MayConsume()).isTrue();
        assertThat(receipt.planIntakeEchoMode())
                .isEqualTo("java-v112-credential-resolver-pre-implementation-plan-intake-echo-receipt-only");
        assertThat(receipt.sourceSpan())
                .isEqualTo("Node v270 credential resolver pre-implementation plan intake");
        assertThat(receipt.sourceNodeV270().sourceVersion()).isEqualTo("Node v270");
        assertThat(receipt.sourceNodeV270().profileVersion())
                .isEqualTo(
                        "managed-audit-manual-sandbox-connection-credential-resolver-pre-implementation-plan-intake.v1"
                );
        assertThat(receipt.sourceNodeV270().planIntakeState())
                .isEqualTo("credential-resolver-pre-implementation-plan-intake-ready");
        assertThat(receipt.sourceNodeV270().readyForManagedAuditManualSandboxConnectionCredentialResolverPreImplementationPlanIntake())
                .isTrue();
        assertThat(receipt.sourceNodeV270().planIntakeOnly()).isTrue();
        assertThat(receipt.sourceNodeV270().readOnlyPlanIntake()).isTrue();
        assertThat(receipt.sourceNodeV270().readyForCredentialResolverPreImplementationPlan()).isTrue();
        assertThat(receipt.sourceNodeV270().readyForManagedAuditSandboxAdapterConnection()).isFalse();
        assertThat(receipt.sourceNodeV270().realResolverImplementationAllowed()).isFalse();
        assertThat(receipt.sourceNodeV270().executionAllowed()).isFalse();
        assertThat(receipt.sourceNodeV270().connectsManagedAudit()).isFalse();
        assertThat(receipt.sourceNodeV270().credentialValueRead()).isFalse();
        assertThat(receipt.sourceNodeV270().rawEndpointUrlParsed()).isFalse();
        assertThat(receipt.sourceNodeV270().externalRequestSent()).isFalse();
        assertThat(receipt.sourceNodeV270().secretProviderInstantiated()).isFalse();
        assertThat(receipt.sourceNodeV270().resolverClientInstantiated()).isFalse();
        assertThat(receipt.sourceNodeV270().schemaMigrationExecuted()).isFalse();
        assertThat(receipt.sourceNodeV270().approvalLedgerWritten()).isFalse();
        assertThat(receipt.sourceNodeV270().automaticUpstreamStart()).isFalse();
        assertThat(receipt.sourceNodeV270().sourceNodeV269().profileVersion())
                .isEqualTo(
                        "managed-audit-manual-sandbox-connection-credential-resolver-production-readiness-blocked-decision-upstream-echo-verification.v1"
                );
        assertThat(receipt.sourceNodeV270().sourceNodeV269().verificationState())
                .isEqualTo(
                        "credential-resolver-production-readiness-blocked-decision-upstream-echo-verification-ready"
                );
        assertThat(receipt.sourceNodeV270().sourceNodeV269().readyForBlockedDecisionUpstreamEchoVerification())
                .isTrue();
        assertThat(receipt.sourceNodeV270().sourceNodeV269().verificationDigest()).startsWith("sha256:");
        assertThat(receipt.sourceNodeV270().sourceNodeV269().sourceSpan())
                .isEqualTo("Node v268 + Java v111 + mini-kv v118");
        assertThat(receipt.sourceNodeV270().sourceNodeV269().sourceNodeV268Ready()).isTrue();
        assertThat(receipt.sourceNodeV270().sourceNodeV269().javaV111EchoReady()).isTrue();
        assertThat(receipt.sourceNodeV270().sourceNodeV269().miniKvV118NonParticipationReady()).isTrue();
        assertThat(receipt.sourceNodeV270().sourceNodeV269().blockedDecisionAligned()).isTrue();
        assertThat(receipt.sourceNodeV270().sourceNodeV269().missingRequirementBlockersAligned()).isTrue();
        assertThat(receipt.sourceNodeV270().sourceNodeV269().credentialBoundaryAligned()).isTrue();
        assertThat(receipt.sourceNodeV270().sourceNodeV269().rawEndpointBoundaryAligned()).isTrue();
        assertThat(receipt.sourceNodeV270().sourceNodeV269().resolverBoundaryAligned()).isTrue();
        assertThat(receipt.sourceNodeV270().sourceNodeV269().connectionBoundaryAligned()).isTrue();
        assertThat(receipt.sourceNodeV270().sourceNodeV269().writeBoundaryAligned()).isTrue();
        assertThat(receipt.sourceNodeV270().sourceNodeV269().autoStartBoundaryAligned()).isTrue();
        assertThat(receipt.sourceNodeV270().sourceNodeV269().checkCount()).isEqualTo(22);
        assertThat(receipt.sourceNodeV270().sourceNodeV269().passedCheckCount()).isEqualTo(22);
        assertThat(receipt.sourceNodeV270().sourceNodeV269().sourceCheckCount()).isEqualTo(25);
        assertThat(receipt.sourceNodeV270().sourceNodeV269().sourcePassedCheckCount()).isEqualTo(15);
        assertThat(receipt.sourceNodeV270().sourceNodeV269().missingPreImplementationRequirementCount())
                .isEqualTo(10);
        assertThat(receipt.sourceNodeV270().sourceNodeV269().productionBlockerCount()).isZero();
        assertThat(receipt.preImplementationPlan().planVersion())
                .isEqualTo("node-v270-credential-resolver-pre-implementation-plan-intake.v1");
        assertThat(receipt.preImplementationPlan().planMode()).isEqualTo("plan-intake-only");
        assertThat(receipt.preImplementationPlan().sourceSpan()).isEqualTo("Node v269");
        assertThat(receipt.preImplementationPlan().planDigest()).startsWith("sha256:");
        assertThat(receipt.preImplementationPlan().boundaryCount()).isEqualTo(10);
        assertThat(receipt.preImplementationPlan().definedBoundaryCount()).isEqualTo(10);
        assertThat(receipt.preImplementationPlan().allRequiredBoundariesDefined()).isTrue();
        assertThat(receipt.preImplementationPlan().realResolverImplementationAllowed()).isFalse();
        assertThat(receipt.preImplementationPlan().secretProviderRuntimeAllowed()).isFalse();
        assertThat(receipt.preImplementationPlan().credentialValueReadAllowed()).isFalse();
        assertThat(receipt.preImplementationPlan().rawEndpointUrlParseAllowed()).isFalse();
        assertThat(receipt.preImplementationPlan().externalRequestAllowed()).isFalse();
        assertThat(receipt.preImplementationPlan().schemaMigrationAllowed()).isFalse();
        assertThat(receipt.preImplementationPlan().approvalLedgerWriteAllowed()).isFalse();
        assertThat(receipt.preImplementationPlan().automaticUpstreamStartAllowed()).isFalse();
        assertThat(receipt.preImplementationPlan().boundaries())
                .extracting(boundary -> boundary.requirementFromV268())
                .containsExactly(
                        "REAL_RESOLVER_PRE_IMPLEMENTATION_PLAN_MISSING",
                        "CREDENTIAL_HANDLE_BOUNDARY_MISSING",
                        "ENDPOINT_HANDLE_BOUNDARY_MISSING",
                        "SECRET_PROVIDER_STUB_MISSING",
                        "OPERATOR_APPROVAL_BOUNDARY_MISSING",
                        "ROLLBACK_BOUNDARY_MISSING",
                        "REDACTION_POLICY_MISSING",
                        "EXTERNAL_REQUEST_SIMULATION_PLAN_MISSING",
                        "SCHEMA_MIGRATION_POLICY_MISSING",
                        "AUDIT_LEDGER_WRITE_POLICY_MISSING"
                );
        assertThat(receipt.preImplementationPlan().boundaries())
                .allMatch(boundary -> "defined-for-review".equals(boundary.status()));
        assertThat(receipt.planIntake().intakeDigest()).startsWith("sha256:");
        assertThat(receipt.planIntake().intakeMode()).isEqualTo("node-v270-plan-intake-only");
        assertThat(receipt.planIntake().consumedNodeVersion()).isEqualTo("Node v269");
        assertThat(receipt.planIntake().requiredBoundaryCount()).isEqualTo(10);
        assertThat(receipt.planIntake().definedBoundaryCount()).isEqualTo(10);
        assertThat(receipt.planIntake().missingBoundaryCount()).isZero();
        assertThat(receipt.planIntake().planDocumentPresent()).isTrue();
        assertThat(receipt.planIntake().credentialHandleBoundaryDefined()).isTrue();
        assertThat(receipt.planIntake().endpointHandleBoundaryDefined()).isTrue();
        assertThat(receipt.planIntake().secretProviderStubDefined()).isTrue();
        assertThat(receipt.planIntake().operatorApprovalBoundaryDefined()).isTrue();
        assertThat(receipt.planIntake().rollbackBoundaryDefined()).isTrue();
        assertThat(receipt.planIntake().redactionPolicyDefined()).isTrue();
        assertThat(receipt.planIntake().externalRequestSimulationDefined()).isTrue();
        assertThat(receipt.planIntake().schemaMigrationPolicyDefined()).isTrue();
        assertThat(receipt.planIntake().auditLedgerWritePolicyDefined()).isTrue();
        assertThat(receipt.planIntake().nextJavaEchoVersion()).isEqualTo("Java v112");
        assertThat(receipt.planIntake().nextMiniKvReceiptVersion()).isEqualTo("mini-kv v119");
        assertThat(receipt.planIntake().nextNodeVerificationVersion()).isEqualTo("Node v272");
        assertThat(receipt.checks().sourceNodeV269Ready()).isTrue();
        assertThat(receipt.checks().sourceNodeV269KeepsBlockedDecision()).isTrue();
        assertThat(receipt.checks().sourceNodeV269KeepsRealResolverBlocked()).isTrue();
        assertThat(receipt.checks().allTenBoundariesDefined()).isTrue();
        assertThat(receipt.checks().credentialValueStillForbidden()).isTrue();
        assertThat(receipt.checks().rawEndpointStillForbidden()).isTrue();
        assertThat(receipt.checks().secretProviderRuntimeStillDisabled()).isTrue();
        assertThat(receipt.checks().realResolverClientStillDisabled()).isTrue();
        assertThat(receipt.checks().externalRequestStillSimulationOnly()).isTrue();
        assertThat(receipt.checks().schemaMigrationStillReviewOnly()).isTrue();
        assertThat(receipt.checks().auditLedgerWriteStillReviewOnly()).isTrue();
        assertThat(receipt.checks().readyForManagedAuditManualSandboxConnectionCredentialResolverPreImplementationPlanIntake())
                .isTrue();
        assertThat(receipt.sourceNodeV270().summary().checkCount()).isEqualTo(26);
        assertThat(receipt.sourceNodeV270().summary().passedCheckCount()).isEqualTo(26);
        assertThat(receipt.sourceNodeV270().summary().boundaryCount()).isEqualTo(10);
        assertThat(receipt.sourceNodeV270().summary().definedBoundaryCount()).isEqualTo(10);
        assertThat(receipt.sourceNodeV270().summary().productionBlockerCount()).isZero();
        assertThat(receipt.sourceNodeV270().summary().warningCount()).isEqualTo(2);
        assertThat(receipt.sourceNodeV270().summary().recommendationCount()).isEqualTo(2);
        assertThat(receipt.sideEffectBoundary().planIntakeOnly()).isTrue();
        assertThat(receipt.sideEffectBoundary().readOnlyPlanIntake()).isTrue();
        assertThat(receipt.sideEffectBoundary().readyForManagedAuditSandboxAdapterConnection()).isFalse();
        assertThat(receipt.sideEffectBoundary().credentialValueRead()).isFalse();
        assertThat(receipt.sideEffectBoundary().rawEndpointUrlParsed()).isFalse();
        assertThat(receipt.sideEffectBoundary().externalRequestSent()).isFalse();
        assertThat(receipt.sideEffectBoundary().secretProviderInstantiated()).isFalse();
        assertThat(receipt.sideEffectBoundary().resolverClientInstantiated()).isFalse();
        assertThat(receipt.sideEffectBoundary().approvalLedgerWritten()).isFalse();
        assertThat(receipt.sideEffectBoundary().sqlExecuted()).isFalse();
        assertThat(receipt.sideEffectBoundary().schemaMigrationExecuted()).isFalse();
        assertThat(receipt.sideEffectBoundary().automaticUpstreamStart()).isFalse();
        assertThat(receipt.sourceNodeV270Echoed()).isTrue();
        assertThat(receipt.sourceNodeV269UpstreamEchoed()).isTrue();
        assertThat(receipt.preImplementationPlanEchoed()).isTrue();
        assertThat(receipt.planIntakeEchoed()).isTrue();
        assertThat(receipt.allRequiredBoundariesEchoed()).isTrue();
        assertThat(receipt.noCredentialBoundaryEchoed()).isTrue();
        assertThat(receipt.noRawEndpointBoundaryEchoed()).isTrue();
        assertThat(receipt.noResolverRuntimeBoundaryEchoed()).isTrue();
        assertThat(receipt.noConnectionBoundaryEchoed()).isTrue();
        assertThat(receipt.noWriteBoundaryEchoed()).isTrue();
        assertThat(receipt.noAutoStartBoundaryEchoed()).isTrue();
        assertThat(receipt.readyForNodeV272CredentialResolverPreImplementationPlanVerification()).isTrue();
        assertThat(receipt.readyForCredentialResolverPreImplementationPlan()).isTrue();
        assertThat(receipt.readyForManagedAuditSandboxAdapterConnection()).isFalse();
        assertThat(receipt.readyForProductionAudit()).isFalse();
        assertThat(receipt.readyForProductionWindow()).isFalse();
        assertThat(receipt.nodeMayTreatAsProductionAuditRecord()).isFalse();
        assertThat(receipt.boundaryCodes())
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
        assertThat(receipt.requirementCodes())
                .containsExactly(
                        "REAL_RESOLVER_PRE_IMPLEMENTATION_PLAN_MISSING",
                        "CREDENTIAL_HANDLE_BOUNDARY_MISSING",
                        "ENDPOINT_HANDLE_BOUNDARY_MISSING",
                        "SECRET_PROVIDER_STUB_MISSING",
                        "OPERATOR_APPROVAL_BOUNDARY_MISSING",
                        "ROLLBACK_BOUNDARY_MISSING",
                        "REDACTION_POLICY_MISSING",
                        "EXTERNAL_REQUEST_SIMULATION_PLAN_MISSING",
                        "SCHEMA_MIGRATION_POLICY_MISSING",
                        "AUDIT_LEDGER_WRITE_POLICY_MISSING"
                );
        assertThat(receipt.nodeWarningCodes())
                .containsExactly("PLAN_INTAKE_ONLY", "UPSTREAM_ECHO_REQUIRED_BEFORE_V272");
        assertThat(receipt.nodeRecommendationCodes())
                .containsExactly("RUN_V271_STATUS_ROUTES_QUALITY_BRANCH", "RUN_PARALLEL_JAVA_V112_MINI_KV_V119");
        assertThat(receipt.nextRequiredEchoVersions())
                .contains(
                        "Java v112 credential resolver pre-implementation plan intake echo receipt",
                        "mini-kv v119 credential resolver pre-implementation plan intake non-participation receipt"
                );
        assertThat(receipt.receiptWarnings()).isEmpty();
        assertThat(receipt.receiptDigest()).startsWith("sha256:");
        assertThat(rehearsal.verificationHint().responseSchemaVersion())
                .isEqualTo("java-release-approval-rehearsal-response-schema.v52");
        assertThat(rehearsal.verificationHint().schemaFields())
                .contains("managedAuditSandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceipt");
        assertThat(rehearsal.verificationHint().warningDigestInputs())
                .contains(
                        "managedAuditSandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceiptWarnings",
                        "sandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceiptDigest",
                        "sandboxEndpointCredentialResolverPreImplementationPlanBoundaryCount",
                        "sandboxEndpointCredentialResolverPreImplementationPlanAutomaticUpstreamStart"
                );
        assertThat(rehearsal.verificationHint().proofClaims())
                .contains(
                        "managedAuditSandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceipt.preImplementationPlan.boundaryCount=10",
                        "managedAuditSandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceipt.planIntake.missingBoundaryCount=0",
                        "managedAuditSandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceipt.sideEffectBoundary.approvalLedgerWritten=false",
                        "managedAuditSandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceipt.readyForManagedAuditSandboxAdapterConnection=false"
                );
        assertThat(rehearsal.verificationHint().nodeVerificationActions())
                .contains(
                        "Compare managedAuditSandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceipt.consumedByNodeCredentialResolverPreImplementationPlanIntakeProfile with Node v270",
                        "Require managedAuditSandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceipt.readyForNodeV272CredentialResolverPreImplementationPlanVerification=true before Node v272",
                        "Keep managedAuditSandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceipt.preImplementationPlan.credentialValueReadAllowed=false",
                        "Keep managedAuditSandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceipt.sideEffectBoundary.automaticUpstreamStart=false"
                );

        ReleaseApprovalRehearsalResponse repeated =
                service.releaseApprovalRehearsal(paddedHeaderBackedRehearsalRequest());
        assertThat(repeated.managedAuditSandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceipt()
                .receiptDigest()).isEqualTo(receipt.receiptDigest());
    }
}
