package com.codexdemo.orderplatform.ops;

import java.util.List;

final class ReleaseApprovalVerificationHintCatalog {

    private ReleaseApprovalVerificationHintCatalog() {
    }

    static List<String> warningDigestWarningInputNames() {
        return List.of(
                "contextWarnings",
                "operatorWindowEchoWarnings",
                "ciEvidenceEchoWarnings",
                "artifactRetentionEchoWarnings",
                "liveReadinessEchoWarnings",
                "auditPersistenceHandoffEchoWarnings",
                "approvalRecordHandoffEchoWarnings",
                "approvalHandoffVerificationMarkerWarnings",
                "managedAuditAdapterBoundaryReceiptWarnings",
                "managedAuditProductionAdapterPrerequisiteReceiptWarnings",
                "opsEvidenceServiceQualitySplitReceiptWarnings",
                "managedAuditAdapterImplementationGuardReceiptWarnings",
                "managedAuditExternalAdapterMigrationGuardReceiptWarnings"
        );
    }

    static List<String> warningDigestBoundaryInputNames() {
        return List.of(
                "failureCategories",
                "taxonomyWarnings",
                "executionAllowed",
                "approvalLedgerWritten",
                "javaManagedAuditWriteAllowed",
                "javaApprovalRecordPersisted",
                "nodeMayTreatAsProductionApprovalRecord",
                "nodeMayTreatAsProductionAuditRecord",
                "nodeV211ProductionAuditRecordAllowed",
                "nodeV211RealApprovalDecisionCreated",
                "nodeV215MayConnectManagedAudit",
                "nodeV215MayCreateApprovalDecision",
                "nodeV215MayWriteApprovalLedger",
                "nodeV215MayExecuteSql",
                "nodeV215MayTriggerDeployment",
                "nodeV215MayTriggerRollback",
                "nodeV215MayExecuteRestore",
                "nodeV217MayConnectManagedAudit",
                "nodeV217MayWriteApprovalLedger",
                "nodeV217MayExecuteSql",
                "nodeV217MayTriggerDeployment",
                "nodeV217MayTriggerRollback",
                "nodeV217MayExecuteRestore",
                "qualitySplitApiShapeChanged",
                "qualitySplitApprovalDecisionCreated",
                "qualitySplitApprovalLedgerWritten",
                "qualitySplitManagedAuditStoreWritten",
                "qualitySplitSqlExecuted",
                "implementationGuardDigest",
                "implementationGuardJavaApprovalLedgerWritten",
                "implementationGuardJavaManagedAuditStoreWritten",
                "implementationGuardJavaSqlExecuted",
                "implementationGuardNodeV220AppendWritten",
                "implementationGuardNodeV220ExternalManagedAuditAccessed",
                "implementationGuardNodeV220LocalDryRunWritePerformed",
                "externalAdapterMigrationGuardDigest",
                "externalAdapterMigrationCredentialValueReadByJava",
                "externalAdapterMigrationConnectionOpened",
                "externalAdapterMigrationSchemaMigrated",
                "externalAdapterMigrationJavaManagedAuditStoreWritten",
                "externalAdapterMigrationJavaSqlExecuted",
                "externalAdapterMigrationNodeV222SourceEndpointRerunPerformed",
                "externalAdapterMigrationNodeV222AdditionalLocalDryRunWritePerformed"
        );
    }

    static String finalWarningDigestBoundaryInputName() {
        return "nodeMayWriteApprovalLedger";
    }

    static List<String> proofClaims() {
        return List.of(
                "executionAllowed=false",
                "requestContext.approvalLedgerWritten=false",
                "ciEvidenceHint.noLedgerWriteProved=true",
                "ciEvidenceHint.ciArtifactUploadedByJava=false",
                "ciEvidenceHint.githubArtifactAccessedByJava=false",
                "ciEvidenceHint.productionWindowAllowedByJava=false",
                "artifactRetentionHint.javaRetentionFixtureReadOnly=true",
                "artifactRetentionHint.ciArtifactUploadedByJava=false",
                "artifactRetentionHint.githubArtifactAccessedByJava=false",
                "artifactRetentionHint.nodeMayTreatAsRetentionAuthorization=false",
                "liveReadinessHint.readOnlyEndpointReady=true",
                "liveReadinessHint.runtimeSmokeExecutedByJava=false",
                "liveReadinessHint.javaStartedProcessForNode=false",
                "liveReadinessHint.nodeMayTreatAsProductionAuthorization=false",
                "auditPersistenceHandoffHint.javaAuditSourceReadOnly=true",
                "auditPersistenceHandoffHint.javaLedgerWriteAllowed=false",
                "auditPersistenceHandoffHint.javaManagedAuditWriteAllowed=false",
                "auditPersistenceHandoffHint.javaExternalAuditSystemAccessed=false",
                "auditPersistenceHandoffHint.nodeMayTreatAsProductionAuditRecord=false",
                "approvalRecordHandoffHint.approvalRecordFixtureReadOnly=true",
                "approvalRecordHandoffHint.javaApprovalDecisionCreated=false",
                "approvalRecordHandoffHint.javaApprovalLedgerWritten=false",
                "approvalRecordHandoffHint.javaApprovalRecordPersisted=false",
                "approvalRecordHandoffHint.nodeMayTreatAsProductionApprovalRecord=false",
                "approvalHandoffVerificationMarker.nodeV211ProductionAuditRecordAllowed=false",
                "approvalHandoffVerificationMarker.nodeV211RealApprovalDecisionCreated=false",
                "approvalHandoffVerificationMarker.nodeV211RealApprovalLedgerWritten=false",
                "approvalHandoffVerificationMarker.javaApprovalRecordPersisted=false",
                "managedAuditAdapterBoundaryReceipt.nodeV215MayConnectManagedAudit=false",
                "managedAuditAdapterBoundaryReceipt.nodeV215MayCreateApprovalDecision=false",
                "managedAuditAdapterBoundaryReceipt.nodeV215MayWriteApprovalLedger=false",
                "managedAuditAdapterBoundaryReceipt.nodeV215MayExecuteSql=false",
                "managedAuditAdapterBoundaryReceipt.nodeV215MayTriggerDeployment=false",
                "managedAuditAdapterBoundaryReceipt.nodeV215MayTriggerRollback=false",
                "managedAuditAdapterBoundaryReceipt.nodeV215MayExecuteRestore=false",
                "managedAuditAdapterBoundaryReceipt.javaApprovalDecisionCreated=false",
                "managedAuditAdapterBoundaryReceipt.javaApprovalLedgerWritten=false",
                "managedAuditProductionAdapterPrerequisiteReceipt.javaCreatesApprovalDecision=false",
                "managedAuditProductionAdapterPrerequisiteReceipt.javaWritesApprovalLedger=false",
                "managedAuditProductionAdapterPrerequisiteReceipt.javaPersistsApprovalRecord=false",
                "managedAuditProductionAdapterPrerequisiteReceipt.javaWritesManagedAuditStore=false",
                "managedAuditProductionAdapterPrerequisiteReceipt.javaExecutesSql=false",
                "managedAuditProductionAdapterPrerequisiteReceipt.javaTriggersDeployment=false",
                "managedAuditProductionAdapterPrerequisiteReceipt.javaTriggersRollback=false",
                "managedAuditProductionAdapterPrerequisiteReceipt.javaExecutesRestore=false",
                "managedAuditProductionAdapterPrerequisiteReceipt.nodeV217MayConnectManagedAudit=false",
                "managedAuditProductionAdapterPrerequisiteReceipt.nodeV217MayWriteApprovalLedger=false",
                "managedAuditProductionAdapterPrerequisiteReceipt.nodeV217MayExecuteSql=false",
                "managedAuditProductionAdapterPrerequisiteReceipt.nodeV217MayTriggerDeployment=false",
                "managedAuditProductionAdapterPrerequisiteReceipt.nodeV217MayTriggerRollback=false",
                "managedAuditProductionAdapterPrerequisiteReceipt.nodeV217MayExecuteRestore=false",
                "opsEvidenceServiceQualitySplitReceipt.apiShapeChanged=false",
                "opsEvidenceServiceQualitySplitReceipt.approvalDecisionCreated=false",
                "opsEvidenceServiceQualitySplitReceipt.approvalLedgerWritten=false",
                "opsEvidenceServiceQualitySplitReceipt.managedAuditStoreWritten=false",
                "opsEvidenceServiceQualitySplitReceipt.sqlExecuted=false",
                "opsEvidenceServiceQualitySplitReceipt.deploymentTriggered=false",
                "opsEvidenceServiceQualitySplitReceipt.rollbackTriggered=false",
                "opsEvidenceServiceQualitySplitReceipt.restoreExecuted=false",
                "managedAuditAdapterImplementationGuardReceipt.nodeV220SelectedAdapterDisabled=true",
                "managedAuditAdapterImplementationGuardReceipt.nodeV220AppendWritten=false",
                "managedAuditAdapterImplementationGuardReceipt.nodeV220ExternalManagedAuditAccessed=false",
                "managedAuditAdapterImplementationGuardReceipt.javaApprovalLedgerWritten=false",
                "managedAuditAdapterImplementationGuardReceipt.javaManagedAuditStoreWritten=false",
                "managedAuditAdapterImplementationGuardReceipt.javaSqlExecuted=false",
                "managedAuditAdapterImplementationGuardReceipt.javaDeploymentTriggered=false",
                "managedAuditAdapterImplementationGuardReceipt.javaRollbackTriggered=false",
                "managedAuditExternalAdapterMigrationGuardReceipt.ownerApprovalRequiredBeforeConnection=true",
                "managedAuditExternalAdapterMigrationGuardReceipt.schemaMigrationReviewRequired=true",
                "managedAuditExternalAdapterMigrationGuardReceipt.credentialReviewRequired=true",
                "managedAuditExternalAdapterMigrationGuardReceipt.credentialValueReadByJava=false",
                "managedAuditExternalAdapterMigrationGuardReceipt.externalManagedAuditConnectionOpened=false",
                "managedAuditExternalAdapterMigrationGuardReceipt.externalManagedAuditSchemaMigrated=false",
                "managedAuditExternalAdapterMigrationGuardReceipt.javaApprovalLedgerWritten=false",
                "managedAuditExternalAdapterMigrationGuardReceipt.javaManagedAuditStoreWritten=false",
                "managedAuditExternalAdapterMigrationGuardReceipt.javaSqlExecuted=false"
        );
    }

    static List<String> closingProofClaims() {
        return List.of(
                "executionBoundaries.nodeMayCreateApprovalDecision=false",
                "executionBoundaries.nodeMayWriteApprovalLedger=false",
                "executionBoundaries.nodeMayTriggerDeployment=false",
                "executionBoundaries.nodeMayTriggerRollback=false",
                "executionBoundaries.nodeMayExecuteRollbackSql=false"
        );
    }

    static List<String> schemaFields() {
        return List.of(
                "sampledAt",
                "rehearsalVersion",
                "requestContext",
                "operatorWindowHint",
                "ciEvidenceHint",
                "artifactRetentionHint",
                "liveReadinessHint",
                "auditPersistenceHandoffHint",
                "approvalRecordHandoffHint",
                "approvalHandoffVerificationMarker",
                "managedAuditAdapterBoundaryReceipt",
                "managedAuditProductionAdapterPrerequisiteReceipt",
                "opsEvidenceServiceQualitySplitReceipt",
                "managedAuditAdapterImplementationGuardReceipt",
                "managedAuditExternalAdapterMigrationGuardReceipt",
                "managedAuditSandboxAdapterApprovalSchemaGuardReceipt",
                "managedAuditSandboxConnectionOperatorHandoffMarker",
                "managedAuditSandboxConnectionPreflightEchoMarker",
                "managedAuditSandboxConnectionPreconditionReceipt",
                "managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt",
                "managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt",
                "managedAuditSandboxConnectionDryRunCommandPackageEchoReceipt",
                "managedAuditSandboxConnectionPrecheckPacketEchoReceipt",
                "managedAuditSandboxConnectionDisabledAdapterClientPrecheckEchoReceipt",
                "managedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker",
                "managedAuditSandboxEndpointHandlePreflightEchoMarker",
                "managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker",
                "managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker",
                "managedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarker",
                "managedAuditSandboxEndpointCredentialResolverFakeShellArchiveEchoReceipt",
                "managedAuditSandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceipt",
                "managedAuditSandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceipt",
                "managedAuditSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceipt",
                "managedAuditSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoReceipt",
                "managedAuditSandboxEndpointCredentialResolverImplementationPlanEchoReceipt",
                "managedAuditSandboxEndpointCredentialResolverExecutionDeniedEchoReceipt",
                "managedAuditSandboxEndpointCredentialResolverDisabledRuntimeShellHandoffEchoReceipt",
                "failureTaxonomy",
                "verificationHint",
                "releaseApprovalInputs",
                "liveSignals",
                "executionBoundaries",
                "rehearsalBlockers",
                "requiredNodeEnvironment",
                "nextEvidenceActions"
        );
    }

    static List<String> nodeVerificationActions() {
        return List.of(
                "Verify responseSchemaVersion before importing operator window results",
                "Compare ciEvidenceHint.manifestProfileVersion with Node v200 manifest profileVersion",
                "Compare ciEvidenceHint.manifestDigest with Node v200 manifest.manifestDigest",
                "Require ciEvidenceHint.ciArtifactUploadedByJava=false until CI artifact upload exists outside Java",
                "Compare artifactRetentionHint.ciArtifactName and ciRetentionDays with Node v202 dry-run contract",
                "Require artifactRetentionHint.nodeMayTreatAsRetentionAuthorization=false until Node v203 retention gate passes",
                "Compare liveReadinessHint.sourcePreflightVersion and runtimeSmokeSessionId with Node v204/v205 smoke context",
                "Require liveReadinessHint.runtimeSmokeExecutedByJava=false; Node owns v205 process/run evidence",
                "Compare auditPersistenceHandoffHint.managedAuditCandidateVersion with Node v208 managed audit candidate",
                "Require auditPersistenceHandoffHint.javaManagedAuditWriteAllowed=false until Node owns dry-run persistence",
                "Compare approvalRecordHandoffHint.approvalBindingContractVersion with Node v210 binding contract",
                "Require approvalRecordHandoffHint.javaApprovalRecordPersisted=false until a real approval store exists",
                "Compare approvalHandoffVerificationMarker.consumedByNodeProfileVersion with Node v211 packet profile",
                "Require approvalHandoffVerificationMarker.readyForNodeV213RestoreDrillPlan=true before Node v213 restore drill planning",
                "Keep approvalHandoffVerificationMarker.nodeV211ProductionAuditRecordAllowed=false",
                "Compare managedAuditAdapterBoundaryReceipt.consumedByNodeArchiveVerificationVersion with Node v214 profileVersion",
                "Require managedAuditAdapterBoundaryReceipt.readyForNodeV215DryRunAdapterCandidate=true before Node v215",
                "Keep managedAuditAdapterBoundaryReceipt.nodeV215MayConnectManagedAudit=false",
                "Keep managedAuditAdapterBoundaryReceipt.nodeV215MayCreateApprovalDecision=false",
                "Keep managedAuditAdapterBoundaryReceipt.nodeV215MayWriteApprovalLedger=false",
                "Compare managedAuditProductionAdapterPrerequisiteReceipt.consumedByNodeArchiveVerificationVersion with Node v216 profileVersion",
                "Require managedAuditProductionAdapterPrerequisiteReceipt.readyForNodeV217ProductionHardeningReadinessGate=true before Node v217",
                "Keep managedAuditProductionAdapterPrerequisiteReceipt.nodeV217MayConnectManagedAudit=false",
                "Keep managedAuditProductionAdapterPrerequisiteReceipt.javaWritesApprovalLedger=false",
                "Keep managedAuditProductionAdapterPrerequisiteReceipt.javaExecutesSql=false",
                "Compare opsEvidenceServiceQualitySplitReceipt.consumedByNodeQualityPassVersion with Node v218",
                "Require opsEvidenceServiceQualitySplitReceipt.readyForNodeV219ImplementationPrecheck=true before Node v219",
                "Keep opsEvidenceServiceQualitySplitReceipt.apiShapeChanged=false",
                "Keep opsEvidenceServiceQualitySplitReceipt.approvalLedgerWritten=false",
                "Keep opsEvidenceServiceQualitySplitReceipt.sqlExecuted=false",
                "Compare managedAuditAdapterImplementationGuardReceipt.consumedByNodeDisabledShellProfile with Node v220",
                "Require managedAuditAdapterImplementationGuardReceipt.readyForNodeV221LocalAdapterCandidateDryRun=true before Node v221",
                "Keep managedAuditAdapterImplementationGuardReceipt.javaApprovalLedgerWritten=false",
                "Keep managedAuditAdapterImplementationGuardReceipt.nodeV220AppendWritten=false",
                "Keep managedAuditAdapterImplementationGuardReceipt.nodeV220ExternalManagedAuditAccessed=false",
                "Compare managedAuditExternalAdapterMigrationGuardReceipt.consumedByNodeVerificationReportProfile with Node v222",
                "Require managedAuditExternalAdapterMigrationGuardReceipt.readyForNodeV223ExternalAdapterConnectionReadinessReview=true before Node v223",
                "Keep managedAuditExternalAdapterMigrationGuardReceipt.credentialValueReadByJava=false",
                "Keep managedAuditExternalAdapterMigrationGuardReceipt.externalManagedAuditConnectionOpened=false",
                "Keep managedAuditExternalAdapterMigrationGuardReceipt.javaSqlExecuted=false"
        );
    }

    static List<String> closingNodeVerificationActions() {
        return List.of(
                "Compare warningDigest across closed-window and operator-window reads",
                "Require noLedgerWriteProved=true before treating the response as read-only evidence",
                "Keep UPSTREAM_ACTIONS_ENABLED=false"
        );
    }
}
