package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsEvidenceServiceReleaseApprovalRehearsalAdapterGuardOverviewTests extends OpsEvidenceServiceRehearsalTestSupport {

    @Test
    void buildsReleaseApprovalRehearsalAdapterGuardOverviewForDefaultRequest() {
        OpsEvidenceService service = readOnlyFixtureService();

        ReleaseApprovalRehearsalResponse rehearsal = service.releaseApprovalRehearsal();
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().receiptVersion())
                .isEqualTo(
                        "java-release-approval-rehearsal-ops-evidence-service-quality-split-receipt.v1"
                );
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt()
                .sourceProductionAdapterPrerequisiteReceiptVersion())
                .isEqualTo(
                        "java-release-approval-rehearsal-managed-audit-production-adapter-prerequisite-receipt.v1"
                );
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt()
                .sourceProductionAdapterPrerequisiteSchemaVersion())
                .isEqualTo("java-release-approval-rehearsal-response-schema.v12");
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().consumedByNodeQualityPassVersion())
                .isEqualTo("Node v218");
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().consumedByNodeQualityPassProfile())
                .isEqualTo("audit-route-managed-audit-helper-quality-pass.v1");
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().nextNodePrecheckVersion())
                .isEqualTo("Node v219");
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().nextNodePrecheckProfile())
                .isEqualTo("managed-audit-adapter-implementation-precheck-packet.v1");
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().nodeV219MayConsume()).isTrue();
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().receiptResponsibilityDocumented()).isTrue();
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().digestResponsibilityDocumented()).isTrue();
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().hintResponsibilityDocumented()).isTrue();
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().renderResponsibilityDocumented()).isTrue();
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().recordResponsibilityDocumented()).isTrue();
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().firstSafeSplitApplied()).isFalse();
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().broadServiceSplitDeferred()).isTrue();
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().apiShapeChanged()).isFalse();
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().approvalDecisionCreated()).isFalse();
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().approvalLedgerWritten()).isFalse();
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().approvalRecordPersisted()).isFalse();
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().managedAuditStoreWritten()).isFalse();
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().sqlExecuted()).isFalse();
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().deploymentTriggered()).isFalse();
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().rollbackTriggered()).isFalse();
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().restoreExecuted()).isFalse();
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().readyForNodeV219ImplementationPrecheck())
                .isFalse();
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().readyForProductionAudit()).isFalse();
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().readyForProductionWindow()).isFalse();
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().nodeMayTreatAsProductionAuditRecord())
                .isFalse();
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().responsibilityBoundaries())
                .contains(
                        "receipt builders own Node-facing handoff and prerequisite response blocks",
                        "digest helpers own warningDigestInputs and proofClaims stability",
                        "hint builders own request/header echo and read-only readiness hints",
                        "record types own response shape and schema-versioned field names"
                );
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().safeSplitSequence())
                .contains(
                        "Extract receipt builders after Node v219 has consumed v79 schema v13",
                        "Extract digest helpers only after warningDigest repeatability tests stay green",
                        "Run focused release approval rehearsal tests after each split"
                );
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().deferredSplitReasons())
                .contains(
                        "OpsEvidenceService still coordinates many evidence families, so broad split is deferred",
                        "Receipt extraction must not change warningDigest ordering or response field names"
                );
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().forbiddenQualityPassOperations())
                .contains(
                        "Create approval decision during Java v79 quality pass",
                        "Write approval ledger during Java v79 quality pass",
                        "Execute SQL during Java v79 quality pass",
                        "Change release approval rehearsal API path during Java v79 quality pass"
                );
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().nodeV219Prerequisites())
                .contains(
                        "Node v218 audit route and managed audit helper quality pass must be complete",
                        "Java v79 quality split receipt must expose receipt digest hint render record boundaries",
                        "mini-kv v88 command dispatch quality receipt must be present before Node v219",
                        "UPSTREAM_ACTIONS_ENABLED must remain false"
                );
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().receiptWarnings())
                .containsExactly("NODE_V219_SOURCE_PRODUCTION_ADAPTER_PREREQUISITE_RECEIPT_NOT_READY");
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().nodeVerificationActions())
                .contains(
                        "Compare opsEvidenceServiceQualitySplitReceipt.consumedByNodeQualityPassVersion with Node v218",
                        "Require opsEvidenceServiceQualitySplitReceipt.readyForNodeV219ImplementationPrecheck=true before Node v219",
                        "Keep opsEvidenceServiceQualitySplitReceipt.apiShapeChanged=false"
                );
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().receiptVersion())
                .isEqualTo(
                        "java-release-approval-rehearsal-managed-audit-adapter-implementation-guard-receipt.v1"
                );
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().sourceQualitySplitReceiptVersion())
                .isEqualTo(
                        "java-release-approval-rehearsal-ops-evidence-service-quality-split-receipt.v1"
                );
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().sourceQualitySplitSchemaVersion())
                .isEqualTo("java-release-approval-rehearsal-response-schema.v13");
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().consumedByNodeDisabledShellVersion())
                .isEqualTo("Node v220");
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().consumedByNodeDisabledShellProfile())
                .isEqualTo("managed-audit-adapter-disabled-shell.v1");
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().consumedByNodeDisabledShellEndpoint())
                .isEqualTo("/api/v1/audit/managed-audit-adapter-disabled-shell");
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().consumedByNodeDisabledShellState())
                .isEqualTo("disabled-shell-ready");
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().nextNodeCandidateVersion())
                .isEqualTo("Node v221");
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().nextNodeCandidateProfile())
                .isEqualTo("managed-audit-local-adapter-candidate-dry-run.v1");
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().nodeV221MayConsume()).isTrue();
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().nodeV220DisabledShellReady())
                .isTrue();
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().nodeV220SelectedAdapterDisabled())
                .isTrue();
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().nodeV220LocalDryRunOnlyDeclared())
                .isTrue();
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().nodeV220AppendWritten())
                .isFalse();
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().nodeV220QueryReturnedRecords())
                .isFalse();
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().nodeV220ExternalManagedAuditAccessed())
                .isFalse();
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().nodeV220LocalDryRunWritePerformed())
                .isFalse();
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().javaApprovalDecisionCreated())
                .isFalse();
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().javaApprovalLedgerWritten())
                .isFalse();
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().javaApprovalRecordPersisted())
                .isFalse();
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().javaManagedAuditStoreWritten())
                .isFalse();
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().javaSqlExecuted()).isFalse();
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().javaDeploymentTriggered()).isFalse();
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().javaRollbackTriggered()).isFalse();
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().javaRestoreExecuted()).isFalse();
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt()
                .readyForNodeV221LocalAdapterCandidateDryRun()).isFalse();
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().readyForProductionAudit()).isFalse();
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().readyForProductionWindow()).isFalse();
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt()
                .nodeMayTreatAsProductionAuditRecord()).isFalse();
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().guardDigest())
                .startsWith("sha256:");
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().acceptedAdapterShellChecks())
                .contains(
                        "Node v220 profileVersion must equal managed-audit-adapter-disabled-shell.v1",
                        "Node v220 shellState must equal disabled-shell-ready",
                        "Node v220 selectedAdapterKind must stay disabled",
                        "Node v220 acceptedCandidateKinds may declare local-dry-run but must not select it"
                );
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().forbiddenImplementationOperations())
                .contains(
                        "Write approval ledger during Java v80 implementation guard",
                        "Write managed audit store during Java v80 implementation guard",
                        "Execute SQL during Java v80 implementation guard",
                        "Select local-dry-run adapter from Java v80 guard"
                );
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().nodeV221Prerequisites())
                .contains(
                        "Node v220 managed audit adapter disabled shell must be complete",
                        "Java v80 managed audit adapter implementation guard receipt must be ready",
                        "mini-kv v89 adapter shell non-storage guard receipt must be present before Node v221",
                        "UPSTREAM_ACTIONS_ENABLED must remain false"
                );
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().guardWarnings())
                .containsExactly("NODE_V221_SOURCE_OPS_EVIDENCE_SERVICE_QUALITY_SPLIT_RECEIPT_NOT_READY");
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().nodeVerificationActions())
                .contains(
                        "Compare managedAuditAdapterImplementationGuardReceipt.consumedByNodeDisabledShellProfile with Node v220",
                        "Require managedAuditAdapterImplementationGuardReceipt.readyForNodeV221LocalAdapterCandidateDryRun=true before Node v221",
                        "Keep managedAuditAdapterImplementationGuardReceipt.javaApprovalLedgerWritten=false",
                        "Keep managedAuditAdapterImplementationGuardReceipt.nodeV220AppendWritten=false"
                );
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt().receiptVersion())
                .isEqualTo(
                        "java-release-approval-rehearsal-managed-audit-external-adapter-migration-guard-receipt.v1"
                );
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .sourceImplementationGuardReceiptVersion())
                .isEqualTo(
                        "java-release-approval-rehearsal-managed-audit-adapter-implementation-guard-receipt.v1"
                );
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .sourceImplementationGuardSchemaVersion())
                .isEqualTo("java-release-approval-rehearsal-response-schema.v14");
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .consumedByNodeVerificationReportVersion()).isEqualTo("Node v222");
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .consumedByNodeVerificationReportProfile())
                .isEqualTo("managed-audit-local-adapter-candidate-verification-report.v1");
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .consumedByNodeVerificationReportEndpoint())
                .isEqualTo("/api/v1/audit/managed-audit-local-adapter-candidate-verification-report");
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .consumedByNodeVerificationReportState())
                .isEqualTo("local-adapter-candidate-verification-ready");
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt().nextNodeReviewVersion())
                .isEqualTo("Node v223");
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt().nextNodeReviewProfile())
                .isEqualTo("managed-audit-external-adapter-connection-readiness-review.v1");
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt().nodeV223MayConsume()).isTrue();
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt().nodeV222VerificationReportReady())
                .isTrue();
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt().nodeV222ReadOnlyReport())
                .isTrue();
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .nodeV222SourceEndpointRerunPerformed()).isFalse();
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .nodeV222AdditionalLocalDryRunWritePerformed()).isFalse();
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt().nodeV222ConnectsManagedAudit())
                .isFalse();
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .nodeV222ReadyForProductionAudit()).isFalse();
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .ownerApprovalRequiredBeforeConnection()).isTrue();
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .schemaMigrationReviewRequired()).isTrue();
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt().credentialReviewRequired())
                .isTrue();
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt().credentialValueReadByJava())
                .isFalse();
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt().credentialValueStoredByJava())
                .isFalse();
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .externalManagedAuditConnectionOpened()).isFalse();
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .externalManagedAuditSchemaMigrated()).isFalse();
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt().javaApprovalDecisionCreated())
                .isFalse();
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt().javaApprovalLedgerWritten())
                .isFalse();
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt().javaApprovalRecordPersisted())
                .isFalse();
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt().javaManagedAuditStoreWritten())
                .isFalse();
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt().javaSqlExecuted()).isFalse();
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt().javaDeploymentTriggered())
                .isFalse();
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt().javaRollbackTriggered())
                .isFalse();
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt().javaRestoreExecuted())
                .isFalse();
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .readyForNodeV223ExternalAdapterConnectionReadinessReview()).isFalse();
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt().readyForProductionAudit())
                .isFalse();
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt().readyForProductionWindow())
                .isFalse();
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .nodeMayTreatAsProductionAuditRecord()).isFalse();
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt().guardDigest())
                .startsWith("sha256:");
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt().requiredPreConnectionReviews())
                .contains(
                        "external managed audit owner approval",
                        "external managed audit schema migration review",
                        "external managed audit credential review"
                );
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt().credentialBoundaryClaims())
                .contains(
                        "Java v81 must not read credential values",
                        "Java v81 must not store credential values",
                        "Java v81 must not open an external managed audit connection"
                );
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .forbiddenExternalAdapterOperations())
                .contains(
                        "Open external managed audit connection during Java v81 migration guard",
                        "Execute schema migration SQL during Java v81 migration guard",
                        "Write managed audit store during Java v81 migration guard"
                );
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt().nodeV223Prerequisites())
                .contains(
                        "Node v222 verification report must be ready and read-only",
                        "Java v81 external adapter migration guard receipt must be ready",
                        "mini-kv v90 external adapter non-participation receipt must be present before Node v223",
                        "UPSTREAM_ACTIONS_ENABLED must remain false"
                );
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt().guardWarnings())
                .containsExactly("NODE_V223_SOURCE_IMPLEMENTATION_GUARD_RECEIPT_NOT_READY");
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt().nodeVerificationActions())
                .contains(
                        "Compare managedAuditExternalAdapterMigrationGuardReceipt.consumedByNodeVerificationReportProfile with Node v222",
                        "Require managedAuditExternalAdapterMigrationGuardReceipt.readyForNodeV223ExternalAdapterConnectionReadinessReview=true before Node v223",
                        "Keep managedAuditExternalAdapterMigrationGuardReceipt.credentialValueReadByJava=false",
                        "Keep managedAuditExternalAdapterMigrationGuardReceipt.externalManagedAuditConnectionOpened=false"
                );
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt().receiptVersion())
                .isEqualTo(
                        "java-release-approval-rehearsal-managed-audit-sandbox-adapter-approval-schema-guard-receipt.v1"
                );
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .sourceExternalAdapterMigrationGuardReceiptVersion())
                .isEqualTo(
                        "java-release-approval-rehearsal-managed-audit-external-adapter-migration-guard-receipt.v1"
                );
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .sourceExternalAdapterMigrationGuardSchemaVersion())
                .isEqualTo("java-release-approval-rehearsal-response-schema.v15");
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .consumedByNodeSandboxPlanVersion()).isEqualTo("Node v224");
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .consumedByNodeSandboxPlanProfile())
                .isEqualTo("managed-audit-sandbox-adapter-dry-run-plan.v1");
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .consumedByNodeSandboxPlanEndpoint())
                .isEqualTo("/api/v1/audit/managed-audit-sandbox-adapter-dry-run-plan");
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .consumedByNodeSandboxPlanState())
                .isEqualTo("sandbox-adapter-dry-run-plan-ready");
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt().nextNodePackageVersion())
                .isEqualTo("Node v225");
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt().nextNodePackageProfile())
                .isEqualTo("managed-audit-sandbox-adapter-dry-run-package.v1");
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt().nodeV225MayConsume())
                .isTrue();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .nodeV224SandboxPlan().readyForManagedAuditSandboxAdapterDryRunPlan()).isTrue();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .nodeV224SandboxPlan().readyForManagedAuditSandboxAdapterDryRunPackage()).isFalse();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .nodeV224SandboxPlan().readOnlyPlan()).isTrue();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .nodeV224SandboxPlan().connectsManagedAudit()).isFalse();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .nodeV224SandboxPlan().readsManagedAuditCredential()).isFalse();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .nodeV224SandboxPlan().schemaMigrationExecuted()).isFalse();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .ownerApprovalBoundary().ownerApprovalArtifactRequired()).isTrue();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .ownerApprovalBoundary().ownerApprovalArtifactProvidedByJava()).isFalse();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .ownerApprovalBoundary().javaApprovalDecisionCreated()).isFalse();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .ownerApprovalBoundary().javaApprovalLedgerWritten()).isFalse();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .schemaRehearsalBoundary().schemaMigrationRehearsalRequired()).isTrue();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .schemaRehearsalBoundary().schemaMigrationChecklistRequired()).isTrue();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .schemaRehearsalBoundary().schemaMigrationExecutionAllowed()).isFalse();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .schemaRehearsalBoundary().schemaMigrationSqlExecutedByJava()).isFalse();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .credentialBoundary().sandboxCredentialHandleRequired()).isTrue();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .credentialBoundary().sandboxCredentialHandleName())
                .isEqualTo("ORDEROPS_MANAGED_AUDIT_SANDBOX_CREDENTIAL_HANDLE");
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .credentialBoundary().productionCredentialAllowed()).isFalse();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .credentialBoundary().credentialValueRequired()).isFalse();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .credentialBoundary().credentialValueReadByJava()).isFalse();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .executionBoundary().externalManagedAuditConnectionOpened()).isFalse();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .executionBoundary().javaManagedAuditStoreWritten()).isFalse();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .executionBoundary().javaSqlExecuted()).isFalse();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .qualityGateBoundary().qualityGatesAreHardAcceptanceCriteria()).isTrue();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .qualityGateBoundary().builderOrHelperSplitApplied()).isTrue();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .qualityGateBoundary().longBooleanConstructorAvoided()).isTrue();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .qualityGateBoundary().receiptFieldsGroupedByBoundary()).isTrue();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .qualityGateBoundary().opsEvidenceServiceOnlyWiresReceipt()).isTrue();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .readyForNodeV225SandboxAdapterDryRunPackage()).isFalse();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt().readyForProductionAudit())
                .isFalse();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt().readyForProductionWindow())
                .isFalse();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .nodeMayTreatAsProductionAuditRecord()).isFalse();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt().guardDigest())
                .startsWith("sha256:");
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt().requiredSandboxEvidence())
                .contains(
                        "Owner approval artifact identifier for sandbox rehearsal",
                        "Sandbox credential handle without credential value disclosure",
                        "Schema migration rehearsal checklist without SQL execution"
                );
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt().forbiddenSandboxOperations())
                .contains(
                        "Read or print a production managed audit credential value during Java v82 guard",
                        "Open an external managed audit connection during Java v82 guard",
                        "Execute schema migration SQL during Java v82 guard"
                );
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt().nodeV225Prerequisites())
                .contains(
                        "Node v224 sandbox adapter dry-run plan must be ready and read-only",
                        "Java v82 sandbox approval/schema guard receipt must be ready",
                        "mini-kv v91 sandbox runtime evidence non-participation receipt must be present"
                );
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt().guardWarnings())
                .containsExactly("NODE_V225_SOURCE_EXTERNAL_ADAPTER_MIGRATION_GUARD_RECEIPT_NOT_READY");
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt().nodeVerificationActions())
                .contains(
                        "Compare managedAuditSandboxAdapterApprovalSchemaGuardReceipt.consumedByNodeSandboxPlanProfile with Node v224",
                        "Require managedAuditSandboxAdapterApprovalSchemaGuardReceipt.readyForNodeV225SandboxAdapterDryRunPackage=true before Node v225",
                        "Keep managedAuditSandboxAdapterApprovalSchemaGuardReceipt.credentialBoundary.credentialValueReadByJava=false",
                        "Verify managedAuditSandboxAdapterApprovalSchemaGuardReceipt.qualityGateBoundary.builderOrHelperSplitApplied=true"
                );
    }
}
