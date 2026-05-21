package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoRecords
        .RehearsalManagedAuditSandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceipt;
import org.junit.jupiter.api.Test;

class OpsEvidenceServiceCredentialResolverProductionReadinessBlockedDecisionEchoTests extends OpsEvidenceServiceRehearsalTestSupport {

    @Test
    void releaseApprovalRehearsalAddsSandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceipt() {
        OpsEvidenceService service = readOnlyFixtureService();

        ReleaseApprovalRehearsalResponse rehearsal =
                service.releaseApprovalRehearsal(headerBackedRehearsalRequest());

        RehearsalManagedAuditSandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceipt receipt =
                rehearsal.managedAuditSandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceipt();
        assertThat(receipt.receiptVersion())
                .isEqualTo(
                        "java-release-approval-rehearsal-managed-audit-sandbox-endpoint-credential-resolver-production-readiness-blocked-decision-echo-receipt.v1"
                );
        assertThat(receipt.sourceFakeShellArchiveEchoReceiptSchemaVersion())
                .isEqualTo("java-release-approval-rehearsal-response-schema.v30");
        assertThat(receipt.consumedByNodeCredentialResolverProductionReadinessDecisionGateVersion())
                .isEqualTo("Node v268");
        assertThat(receipt.consumedByNodeCredentialResolverProductionReadinessDecisionGateProfile())
                .isEqualTo(
                        "managed-audit-manual-sandbox-connection-credential-resolver-production-readiness-decision-gate.v1"
                );
        assertThat(receipt.consumedByNodeCredentialResolverProductionReadinessDecisionGateEndpoint())
                .isEqualTo(
                        "/api/v1/audit/managed-audit-manual-sandbox-connection-credential-resolver-production-readiness-decision-gate"
                );
        assertThat(receipt.consumedByNodeCredentialResolverProductionReadinessDecisionGateState())
                .isEqualTo("blocked");
        assertThat(receipt.sourceNodeCredentialResolverFakeShellArchiveUpstreamEchoVerificationVersion())
                .isEqualTo("Node v267");
        assertThat(receipt.nodeV269MayConsume()).isTrue();
        assertThat(receipt.decisionEchoMode())
                .isEqualTo(
                        "java-v111-credential-resolver-production-readiness-blocked-decision-echo-receipt-only"
                );
        assertThat(receipt.sourceSpan())
                .isEqualTo("Node v268 credential resolver production readiness blocked decision gate");
        assertThat(receipt.sourceNodeV268().decisionGateState()).isEqualTo("blocked");
        assertThat(receipt.sourceNodeV268().readinessDecision()).isEqualTo("blocked");
        assertThat(receipt.sourceNodeV268().sourceSpan()).isEqualTo("Node v267");
        assertThat(receipt.sourceNodeV268().sourceNodeV267Ready()).isTrue();
        assertThat(receipt.sourceNodeV268().sourceNodeV267BlocksRealResolver()).isTrue();
        assertThat(receipt.sourceNodeV268().archiveEchoChainReady()).isTrue();
        assertThat(receipt.sourceNodeV268().decisionGateEvaluated()).isTrue();
        assertThat(receipt.sourceNodeV268().productionReadinessGateOnly()).isTrue();
        assertThat(receipt.sourceNodeV268().readOnlyDecisionGate()).isTrue();
        assertThat(receipt.sourceNodeV268().readyForCredentialResolverPreImplementationPlan()).isFalse();
        assertThat(receipt.sourceNodeV268().readyForManagedAuditSandboxAdapterConnection()).isFalse();
        assertThat(receipt.sourceNodeV268().realResolverImplementationAllowed()).isFalse();
        assertThat(receipt.sourceNodeV268().executionAllowed()).isFalse();
        assertThat(receipt.sourceNodeV268().credentialValueRead()).isFalse();
        assertThat(receipt.sourceNodeV268().rawEndpointUrlParsed()).isFalse();
        assertThat(receipt.sourceNodeV268().externalRequestSent()).isFalse();
        assertThat(receipt.sourceNodeV268().secretProviderInstantiated()).isFalse();
        assertThat(receipt.sourceNodeV268().resolverClientInstantiated()).isFalse();
        assertThat(receipt.sourceNodeV268().connectsManagedAudit()).isFalse();
        assertThat(receipt.sourceNodeV268().schemaMigrationExecuted()).isFalse();
        assertThat(receipt.sourceNodeV268().automaticUpstreamStart()).isFalse();
        assertThat(receipt.sourceNodeV268().checkCount()).isEqualTo(25);
        assertThat(receipt.sourceNodeV268().passedCheckCount()).isEqualTo(15);
        assertThat(receipt.sourceNodeV268().sourceCheckCount()).isEqualTo(18);
        assertThat(receipt.sourceNodeV268().sourcePassedCheckCount()).isEqualTo(18);
        assertThat(receipt.sourceNodeV268().archiveFileCount()).isEqualTo(9);
        assertThat(receipt.sourceNodeV268().evidenceFileCount()).isEqualTo(7);
        assertThat(receipt.sourceNodeV268().requiredSnippetCount()).isEqualTo(24);
        assertThat(receipt.sourceNodeV268().matchedSnippetCount()).isEqualTo(32);
        assertThat(receipt.sourceNodeV268().missingPreImplementationRequirementCount()).isEqualTo(10);
        assertThat(receipt.sourceNodeV268().productionBlockerCount()).isEqualTo(10);
        assertThat(receipt.sourceNodeV268().warningCount()).isEqualTo(2);
        assertThat(receipt.sourceNodeV268().recommendationCount()).isEqualTo(2);
        assertThat(receipt.sourceNodeV268().readyForJavaV111EchoReceipt()).isTrue();
        assertThat(receipt.sourceNodeV268().readyForMiniKvV118NonParticipationReceipt()).isTrue();
        assertThat(receipt.preImplementationRequirements().planDocumentPresent()).isFalse();
        assertThat(receipt.preImplementationRequirements().credentialHandleBoundaryDefined()).isFalse();
        assertThat(receipt.preImplementationRequirements().secretProviderStubDefined()).isFalse();
        assertThat(receipt.preImplementationRequirements().auditLedgerWritePolicyDefined()).isFalse();
        assertThat(receipt.productionReadinessDecision().decisionDigest()).startsWith("sha256:");
        assertThat(receipt.productionReadinessDecision().decision()).isEqualTo("blocked");
        assertThat(receipt.productionReadinessDecision().allowsRealResolverPreImplementationPlan()).isFalse();
        assertThat(receipt.productionReadinessDecision().allowsRealCredentialResolverImplementation()).isFalse();
        assertThat(receipt.productionReadinessDecision().allowsSecretProviderStub()).isFalse();
        assertThat(receipt.productionReadinessDecision().allowsCredentialValueRead()).isFalse();
        assertThat(receipt.productionReadinessDecision().allowsRawEndpointUrlParse()).isFalse();
        assertThat(receipt.productionReadinessDecision().allowsExternalRequest()).isFalse();
        assertThat(receipt.productionReadinessDecision().allowsManagedAuditConnection()).isFalse();
        assertThat(receipt.productionReadinessDecision().allowsSchemaMigration()).isFalse();
        assertThat(receipt.productionReadinessDecision().allowsApprovalLedgerWrite()).isFalse();
        assertThat(receipt.productionReadinessDecision().allowsAutomaticUpstreamStart()).isFalse();
        assertThat(receipt.productionReadinessDecision().nextPlanRequiredBeforeImplementation()).isTrue();
        assertThat(receipt.decisionChecks().decisionGateEvaluated()).isTrue();
        assertThat(receipt.decisionChecks().credentialBoundaryStillClosed()).isTrue();
        assertThat(receipt.decisionChecks().rawEndpointBoundaryStillClosed()).isTrue();
        assertThat(receipt.decisionChecks().resolverBoundaryStillClosed()).isTrue();
        assertThat(receipt.decisionChecks().connectionBoundaryStillClosed()).isTrue();
        assertThat(receipt.decisionChecks().writeBoundaryStillClosed()).isTrue();
        assertThat(receipt.decisionChecks().autoStartBoundaryStillClosed()).isTrue();
        assertThat(receipt.decisionChecks().preImplementationPlanPresent()).isFalse();
        assertThat(receipt.decisionChecks().credentialHandleBoundaryDefined()).isFalse();
        assertThat(receipt.decisionChecks().productionAuditStillBlocked()).isTrue();
        assertThat(receipt.decisionChecks().realResolverImplementationStillBlocked()).isTrue();
        assertThat(receipt.sideEffectBoundary().readOnlyDecisionGate()).isTrue();
        assertThat(receipt.sideEffectBoundary().productionReadinessGateOnly()).isTrue();
        assertThat(receipt.sideEffectBoundary().readyForCredentialResolverPreImplementationPlan()).isFalse();
        assertThat(receipt.sideEffectBoundary().readyForManagedAuditSandboxAdapterConnection()).isFalse();
        assertThat(receipt.sideEffectBoundary().readyForProductionOperations()).isFalse();
        assertThat(receipt.sideEffectBoundary().credentialValueRead()).isFalse();
        assertThat(receipt.sideEffectBoundary().rawEndpointUrlParsed()).isFalse();
        assertThat(receipt.sideEffectBoundary().externalRequestSent()).isFalse();
        assertThat(receipt.sideEffectBoundary().secretProviderInstantiated()).isFalse();
        assertThat(receipt.sideEffectBoundary().resolverClientInstantiated()).isFalse();
        assertThat(receipt.sideEffectBoundary().connectsManagedAudit()).isFalse();
        assertThat(receipt.sideEffectBoundary().approvalLedgerWritten()).isFalse();
        assertThat(receipt.sideEffectBoundary().sqlExecuted()).isFalse();
        assertThat(receipt.sideEffectBoundary().schemaMigrationExecuted()).isFalse();
        assertThat(receipt.sideEffectBoundary().automaticUpstreamStart()).isFalse();
        assertThat(receipt.sourceNodeV268Echoed()).isTrue();
        assertThat(receipt.sourceNodeV267UpstreamEchoed()).isTrue();
        assertThat(receipt.blockedDecisionEchoed()).isTrue();
        assertThat(receipt.preImplementationRequirementsEchoed()).isTrue();
        assertThat(receipt.missingRequirementBlockersEchoed()).isTrue();
        assertThat(receipt.noCredentialBoundaryEchoed()).isTrue();
        assertThat(receipt.noRawEndpointBoundaryEchoed()).isTrue();
        assertThat(receipt.noResolverBoundaryEchoed()).isTrue();
        assertThat(receipt.noConnectionBoundaryEchoed()).isTrue();
        assertThat(receipt.noWriteBoundaryEchoed()).isTrue();
        assertThat(receipt.noAutoStartBoundaryEchoed()).isTrue();
        assertThat(receipt.readyForNodeV269CredentialResolverProductionReadinessBlockedDecisionUpstreamEchoVerification())
                .isTrue();
        assertThat(receipt.readyForCredentialResolverPreImplementationPlan()).isFalse();
        assertThat(receipt.readyForManagedAuditSandboxAdapterConnection()).isFalse();
        assertThat(receipt.readyForProductionAudit()).isFalse();
        assertThat(receipt.readyForProductionWindow()).isFalse();
        assertThat(receipt.nodeMayTreatAsProductionAuditRecord()).isFalse();
        assertThat(receipt.missingRequirementCodes())
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
        assertThat(receipt.productionBlockerCodes()).containsExactlyElementsOf(receipt.missingRequirementCodes());
        assertThat(receipt.nodeWarningCodes())
                .containsExactly("DECISION_GATE_ONLY", "SOURCE_CHAIN_READY_BUT_NOT_PRODUCTION_READY");
        assertThat(receipt.nodeRecommendationCodes())
                .containsExactly("WRITE_SUCCESSOR_PLAN", "REQUEST_PARALLEL_UPSTREAM_ECHO");
        assertThat(receipt.nextRequiredEchoVersions())
                .contains(
                        "Java v111 credential resolver production-readiness blocked-decision echo receipt",
                        "mini-kv v118 credential resolver production-readiness blocked-decision non-participation receipt"
                );
        assertThat(receipt.receiptWarnings()).isEmpty();
        assertThat(receipt.receiptDigest()).startsWith("sha256:");
        assertThat(rehearsal.verificationHint().schemaFields())
                .contains("managedAuditSandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceipt");
        assertThat(rehearsal.verificationHint().warningDigestInputs())
                .contains(
                        "managedAuditSandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceiptWarnings",
                        "sandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceiptDigest",
                        "sandboxEndpointCredentialResolverProductionReadinessBlockedDecisionCredentialValueRead",
                        "sandboxEndpointCredentialResolverProductionReadinessBlockedDecisionAutomaticUpstreamStart"
                );
        assertThat(rehearsal.verificationHint().proofClaims())
                .contains(
                        "managedAuditSandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceipt.sourceNodeV268.readinessDecision=blocked",
                        "managedAuditSandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceipt.sourceNodeV268.missingPreImplementationRequirementCount=10",
                        "managedAuditSandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceipt.productionReadinessDecision.allowsManagedAuditConnection=false",
                        "managedAuditSandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceipt.readyForManagedAuditSandboxAdapterConnection=false"
                );
        assertThat(rehearsal.verificationHint().nodeVerificationActions())
                .contains(
                        "Compare managedAuditSandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceipt.consumedByNodeCredentialResolverProductionReadinessDecisionGateProfile with Node v268",
                        "Require managedAuditSandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceipt.readyForNodeV269CredentialResolverProductionReadinessBlockedDecisionUpstreamEchoVerification=true before Node v269",
                        "Keep managedAuditSandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceipt.productionReadinessDecision.allowsCredentialValueRead=false",
                        "Keep managedAuditSandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceipt.sideEffectBoundary.automaticUpstreamStart=false"
                );

        ReleaseApprovalRehearsalResponse repeated =
                service.releaseApprovalRehearsal(paddedHeaderBackedRehearsalRequest());
        assertThat(repeated.managedAuditSandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceipt()
                .receiptDigest()).isEqualTo(receipt.receiptDigest());
    }
}
