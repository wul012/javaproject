package com.codexdemo.orderplatform.ops.maintenance.releaseapproval;

import java.util.List;

final class ReleaseApprovalVerificationWarningDigestBuilder {
  private final ReleaseApprovalVerificationWarningDigestLineCatalog.Builders lineBuilders;

  ReleaseApprovalVerificationWarningDigestBuilder(
      ReleaseApprovalRehearsalManagedAuditReceiptChainBuilder.ReceiptChain receiptChain) {
    this.lineBuilders =
        new ReleaseApprovalVerificationWarningDigestLineCatalog.Builders(
            receiptChain.sandboxAdapterApprovalSchemaGuardReceiptBuilder(),
            receiptChain.sandboxConnectionOperatorHandoffMarkerBuilder(),
            receiptChain.sandboxConnectionPreflightEchoMarkerBuilder(),
            receiptChain.sandboxConnectionPreconditionReceiptBuilder(),
            receiptChain.sandboxConnectionDryRunEnvelopeEchoReceiptBuilder(),
            receiptChain.sandboxConnectionOperatorWindowChecklistEchoReceiptBuilder(),
            receiptChain.sandboxConnectionDryRunCommandPackageEchoReceiptBuilder(),
            receiptChain.sandboxConnectionPrecheckPacketEchoReceiptBuilder(),
            receiptChain.sandboxConnectionDisabledAdapterClientPrecheckEchoReceiptBuilder(),
            receiptChain.sandboxConnectionFakeTransportDryRunPacketEchoMarkerBuilder(),
            receiptChain.sandboxEndpointHandlePreflightEchoMarkerBuilder(),
            receiptChain.sandboxEndpointCredentialResolverDecisionEchoMarkerBuilder(),
            receiptChain.sandboxEndpointCredentialResolverDisabledPrecheckEchoMarkerBuilder(),
            receiptChain.sandboxEndpointCredentialResolverTestOnlyShellEchoMarkerBuilder(),
            receiptChain.sandboxEndpointCredentialResolverFakeShellArchiveEchoReceiptBuilder(),
            receiptChain
                .sandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceiptBuilder(),
            receiptChain
                .sandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceiptBuilder(),
            receiptChain
                .sandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceiptBuilder(),
            receiptChain
                .sandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoReceiptBuilder(),
            receiptChain.sandboxEndpointCredentialResolverImplementationPlanEchoReceiptBuilder(),
            receiptChain.sandboxEndpointCredentialResolverExecutionDeniedEchoReceiptBuilder(),
            receiptChain
                .sandboxEndpointCredentialResolverDisabledRuntimeShellHandoffEchoReceiptBuilder(),
            receiptChain
                .sandboxEndpointCredentialResolverDisabledRuntimeShellCandidateGateEchoReceiptBuilder(),
            receiptChain
                .sandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoReceiptBuilder(),
            receiptChain
                .sandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoReceiptBuilder(),
            receiptChain
                .sandboxEndpointCredentialResolverRuntimeShellStopPrerequisiteDecisionEchoReceiptBuilder(),
            receiptChain
                .sandboxEndpointCredentialResolverApprovalPrerequisiteArtifactIntakeEchoReceiptBuilder(),
            receiptChain
                .sandboxEndpointCredentialResolverHumanApprovalArtifactReviewPacketEchoReceiptBuilder(),
            receiptChain
                .sandboxEndpointCredentialResolverHumanApprovalArtifactReviewPostEchoDecisionGateEchoReceiptBuilder(),
            receiptChain
                .sandboxEndpointCredentialResolverSignedHumanApprovalArtifactContractEchoReceiptBuilder(),
            receiptChain
                .sandboxEndpointCredentialResolverCredentialHandleApprovalContractEchoReceiptBuilder(),
            receiptChain
                .sandboxEndpointCredentialResolverEndpointHandleAllowlistApprovalContractEchoReceiptBuilder(),
            receiptChain
                .sandboxEndpointCredentialResolverNoNetworkSafetyFixtureContractEchoReceiptBuilder(),
            receiptChain
                .sandboxEndpointCredentialResolverAbortRollbackSemanticsContractEchoReceiptBuilder());
  }

  String build(ReleaseApprovalVerificationHintContext context) {
    var receiptChain = context.receiptChain();
    var requestContext = context.requestContext();
    var operatorWindowHint = context.operatorWindowHint();
    var ciEvidenceHint = context.ciEvidenceHint();
    var artifactRetentionHint = context.artifactRetentionHint();
    var liveReadinessHint = context.liveReadinessHint();
    var auditPersistenceHandoffHint = context.auditPersistenceHandoffHint();
    var approvalRecordHandoffHint = context.approvalRecordHandoffHint();
    var failureTaxonomy = context.failureTaxonomy();
    var executionBoundaries = context.executionBoundaries();
    var approvalHandoffVerificationMarker = receiptChain.approvalHandoffVerificationMarker();
    var managedAuditAdapterBoundaryReceipt = receiptChain.managedAuditAdapterBoundaryReceipt();
    var managedAuditProductionAdapterPrerequisiteReceipt =
        receiptChain.managedAuditProductionAdapterPrerequisiteReceipt();
    var opsEvidenceServiceQualitySplitReceipt =
        receiptChain.opsEvidenceServiceQualitySplitReceipt();
    var managedAuditAdapterImplementationGuardReceipt =
        receiptChain.managedAuditAdapterImplementationGuardReceipt();
    var managedAuditExternalAdapterMigrationGuardReceipt =
        receiptChain.managedAuditExternalAdapterMigrationGuardReceipt();
    var managedAuditSandboxAdapterApprovalSchemaGuardReceipt =
        receiptChain.managedAuditSandboxAdapterApprovalSchemaGuardReceipt();
    var managedAuditSandboxConnectionOperatorHandoffMarker =
        receiptChain.managedAuditSandboxConnectionOperatorHandoffMarker();
    var managedAuditSandboxConnectionPreflightEchoMarker =
        receiptChain.managedAuditSandboxConnectionPreflightEchoMarker();
    var managedAuditSandboxConnectionPreconditionReceipt =
        receiptChain.managedAuditSandboxConnectionPreconditionReceipt();
    var managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt =
        receiptChain.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt();
    var managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt =
        receiptChain.managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt();
    var managedAuditSandboxConnectionDryRunCommandPackageEchoReceipt =
        receiptChain.managedAuditSandboxConnectionDryRunCommandPackageEchoReceipt();
    var managedAuditSandboxConnectionPrecheckPacketEchoReceipt =
        receiptChain.managedAuditSandboxConnectionPrecheckPacketEchoReceipt();
    var managedAuditSandboxConnectionDisabledAdapterClientPrecheckEchoReceipt =
        receiptChain.managedAuditSandboxConnectionDisabledAdapterClientPrecheckEchoReceipt();
    var managedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker =
        receiptChain.managedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker();
    var managedAuditSandboxEndpointHandlePreflightEchoMarker =
        receiptChain.managedAuditSandboxEndpointHandlePreflightEchoMarker();
    var managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker =
        receiptChain.managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker();
    var managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker =
        receiptChain.managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker();
    var managedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarker =
        receiptChain.managedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarker();
    var managedAuditSandboxEndpointCredentialResolverFakeShellArchiveEchoReceipt =
        receiptChain.managedAuditSandboxEndpointCredentialResolverFakeShellArchiveEchoReceipt();
    var managedAuditSandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceipt =
        receiptChain
            .managedAuditSandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceipt();
    var managedAuditSandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceipt =
        receiptChain
            .managedAuditSandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceipt();
    var managedAuditSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceipt =
        receiptChain
            .managedAuditSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceipt();
    var
        managedAuditSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoReceipt =
            receiptChain
                .managedAuditSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoReceipt();
    var managedAuditSandboxEndpointCredentialResolverImplementationPlanEchoReceipt =
        receiptChain.managedAuditSandboxEndpointCredentialResolverImplementationPlanEchoReceipt();
    var managedAuditSandboxEndpointCredentialResolverExecutionDeniedEchoReceipt =
        receiptChain.managedAuditSandboxEndpointCredentialResolverExecutionDeniedEchoReceipt();
    var managedAuditSandboxEndpointCredentialResolverDisabledRuntimeShellHandoffEchoReceipt =
        receiptChain
            .managedAuditSandboxEndpointCredentialResolverDisabledRuntimeShellHandoffEchoReceipt();
    var managedAuditSandboxEndpointCredentialResolverDisabledRuntimeShellCandidateGateEchoReceipt =
        receiptChain
            .managedAuditSandboxEndpointCredentialResolverDisabledRuntimeShellCandidateGateEchoReceipt();
    var managedAuditSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoReceipt =
        receiptChain
            .managedAuditSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoReceipt();
    var managedAuditSandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoReceipt =
        receiptChain
            .managedAuditSandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoReceipt();
    var
        managedAuditSandboxEndpointCredentialResolverRuntimeShellStopPrerequisiteDecisionEchoReceipt =
            receiptChain
                .managedAuditSandboxEndpointCredentialResolverRuntimeShellStopPrerequisiteDecisionEchoReceipt();
    var managedAuditSandboxEndpointCredentialResolverApprovalPrerequisiteArtifactIntakeEchoReceipt =
        receiptChain
            .managedAuditSandboxEndpointCredentialResolverApprovalPrerequisiteArtifactIntakeEchoReceipt();
    var managedAuditSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPacketEchoReceipt =
        receiptChain
            .managedAuditSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPacketEchoReceipt();
    var
        managedAuditSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPostEchoDecisionGateEchoReceipt =
            receiptChain
                .managedAuditSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPostEchoDecisionGateEchoReceipt();
    var
        managedAuditSandboxEndpointCredentialResolverSignedHumanApprovalArtifactContractEchoReceipt =
            receiptChain
                .managedAuditSandboxEndpointCredentialResolverSignedHumanApprovalArtifactContractEchoReceipt();
    var managedAuditSandboxEndpointCredentialResolverCredentialHandleApprovalContractEchoReceipt =
        receiptChain
            .managedAuditSandboxEndpointCredentialResolverCredentialHandleApprovalContractEchoReceipt();
    var
        managedAuditSandboxEndpointCredentialResolverEndpointHandleAllowlistApprovalContractEchoReceipt =
            receiptChain
                .managedAuditSandboxEndpointCredentialResolverEndpointHandleAllowlistApprovalContractEchoReceipt();
    var managedAuditSandboxEndpointCredentialResolverNoNetworkSafetyFixtureContractEchoReceipt =
        receiptChain
            .managedAuditSandboxEndpointCredentialResolverNoNetworkSafetyFixtureContractEchoReceipt();
    var managedAuditSandboxEndpointCredentialResolverAbortRollbackSemanticsContractEchoReceipt =
        receiptChain
            .managedAuditSandboxEndpointCredentialResolverAbortRollbackSemanticsContractEchoReceipt();
    var lineReceipts =
        new ReleaseApprovalVerificationWarningDigestLineCatalog.Receipts(
            managedAuditSandboxAdapterApprovalSchemaGuardReceipt,
            managedAuditSandboxConnectionOperatorHandoffMarker,
            managedAuditSandboxConnectionPreflightEchoMarker,
            managedAuditSandboxConnectionPreconditionReceipt,
            managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt,
            managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt,
            managedAuditSandboxConnectionDryRunCommandPackageEchoReceipt,
            managedAuditSandboxConnectionPrecheckPacketEchoReceipt,
            managedAuditSandboxConnectionDisabledAdapterClientPrecheckEchoReceipt,
            managedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker,
            managedAuditSandboxEndpointHandlePreflightEchoMarker,
            managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker,
            managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker,
            managedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarker,
            managedAuditSandboxEndpointCredentialResolverFakeShellArchiveEchoReceipt,
            managedAuditSandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceipt,
            managedAuditSandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceipt,
            managedAuditSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceipt,
            managedAuditSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoReceipt,
            managedAuditSandboxEndpointCredentialResolverImplementationPlanEchoReceipt,
            managedAuditSandboxEndpointCredentialResolverExecutionDeniedEchoReceipt,
            managedAuditSandboxEndpointCredentialResolverDisabledRuntimeShellHandoffEchoReceipt,
            managedAuditSandboxEndpointCredentialResolverDisabledRuntimeShellCandidateGateEchoReceipt,
            managedAuditSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoReceipt,
            managedAuditSandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoReceipt,
            managedAuditSandboxEndpointCredentialResolverRuntimeShellStopPrerequisiteDecisionEchoReceipt,
            managedAuditSandboxEndpointCredentialResolverApprovalPrerequisiteArtifactIntakeEchoReceipt,
            managedAuditSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPacketEchoReceipt,
            managedAuditSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPostEchoDecisionGateEchoReceipt,
            managedAuditSandboxEndpointCredentialResolverSignedHumanApprovalArtifactContractEchoReceipt,
            managedAuditSandboxEndpointCredentialResolverCredentialHandleApprovalContractEchoReceipt,
            managedAuditSandboxEndpointCredentialResolverEndpointHandleAllowlistApprovalContractEchoReceipt,
            managedAuditSandboxEndpointCredentialResolverNoNetworkSafetyFixtureContractEchoReceipt,
            managedAuditSandboxEndpointCredentialResolverAbortRollbackSemanticsContractEchoReceipt);
    var lines =
        new java.util.ArrayList<>(
            List.of(
                ReleaseApprovalDigestSupport.line("digestKind", "releaseApprovalRehearsalWarning"),
                ReleaseApprovalDigestSupport.line(
                    "hintVersion",
                    ReleaseApprovalContractConstants
                        .RELEASE_APPROVAL_REHEARSAL_VERIFICATION_HINT_VERSION),
                ReleaseApprovalDigestSupport.line(
                    "responseSchemaVersion",
                    ReleaseApprovalContractConstants
                        .RELEASE_APPROVAL_REHEARSAL_RESPONSE_SCHEMA_VERSION),
                ReleaseApprovalDigestSupport.line(
                    "contextWarnings", requestContext.contextWarnings()),
                ReleaseApprovalDigestSupport.line(
                    "operatorWindowEchoWarnings", operatorWindowHint.echoWarnings()),
                ReleaseApprovalDigestSupport.line(
                    "ciEvidenceEchoWarnings", ciEvidenceHint.echoWarnings()),
                ReleaseApprovalDigestSupport.line(
                    "artifactRetentionEchoWarnings", artifactRetentionHint.echoWarnings()),
                ReleaseApprovalDigestSupport.line(
                    "liveReadinessEchoWarnings", liveReadinessHint.echoWarnings()),
                ReleaseApprovalDigestSupport.line(
                    "auditPersistenceHandoffEchoWarnings",
                    auditPersistenceHandoffHint.echoWarnings()),
                ReleaseApprovalDigestSupport.line(
                    "approvalRecordHandoffEchoWarnings", approvalRecordHandoffHint.echoWarnings()),
                ReleaseApprovalDigestSupport.line(
                    "approvalHandoffVerificationMarkerWarnings",
                    approvalHandoffVerificationMarker.markerWarnings()),
                ReleaseApprovalDigestSupport.line(
                    "managedAuditAdapterBoundaryReceiptWarnings",
                    managedAuditAdapterBoundaryReceipt.receiptWarnings()),
                ReleaseApprovalDigestSupport.line(
                    "managedAuditProductionAdapterPrerequisiteReceiptWarnings",
                    managedAuditProductionAdapterPrerequisiteReceipt.receiptWarnings()),
                ReleaseApprovalDigestSupport.line(
                    "opsEvidenceServiceQualitySplitReceiptWarnings",
                    opsEvidenceServiceQualitySplitReceipt.receiptWarnings()),
                ReleaseApprovalDigestSupport.line(
                    "managedAuditAdapterImplementationGuardReceiptWarnings",
                    managedAuditAdapterImplementationGuardReceipt.guardWarnings()),
                ReleaseApprovalDigestSupport.line(
                    "managedAuditExternalAdapterMigrationGuardReceiptWarnings",
                    managedAuditExternalAdapterMigrationGuardReceipt.guardWarnings())));
    lines.addAll(
        ReleaseApprovalVerificationWarningDigestLineCatalog.warningLines(
            lineBuilders, lineReceipts));
    lines.addAll(
        List.of(
            ReleaseApprovalDigestSupport.line(
                "failureCategories", failureTaxonomy.failureCategories()),
            ReleaseApprovalDigestSupport.line(
                "taxonomyWarnings", failureTaxonomy.taxonomyWarnings()),
            ReleaseApprovalDigestSupport.line("executionAllowed", false),
            ReleaseApprovalDigestSupport.line(
                "approvalLedgerWritten", requestContext.approvalLedgerWritten()),
            ReleaseApprovalDigestSupport.line(
                "ciArtifactUploadedByJava", ciEvidenceHint.ciArtifactUploadedByJava()),
            ReleaseApprovalDigestSupport.line(
                "githubArtifactAccessedByJava", ciEvidenceHint.githubArtifactAccessedByJava()),
            ReleaseApprovalDigestSupport.line(
                "retentionCiArtifactUploadedByJava",
                artifactRetentionHint.ciArtifactUploadedByJava()),
            ReleaseApprovalDigestSupport.line(
                "retentionGithubArtifactAccessedByJava",
                artifactRetentionHint.githubArtifactAccessedByJava()),
            ReleaseApprovalDigestSupport.line(
                "retentionAuthorization",
                artifactRetentionHint.nodeMayTreatAsRetentionAuthorization()),
            ReleaseApprovalDigestSupport.line(
                "runtimeSmokeExecutedByJava", liveReadinessHint.runtimeSmokeExecutedByJava()),
            ReleaseApprovalDigestSupport.line(
                "javaStartedProcessForNode", liveReadinessHint.javaStartedProcessForNode()),
            ReleaseApprovalDigestSupport.line(
                "nodeMayTreatAsProductionAuthorization",
                liveReadinessHint.nodeMayTreatAsProductionAuthorization()),
            ReleaseApprovalDigestSupport.line(
                "javaManagedAuditWriteAllowed",
                auditPersistenceHandoffHint.javaManagedAuditWriteAllowed()),
            ReleaseApprovalDigestSupport.line(
                "javaExternalAuditSystemAccessed",
                auditPersistenceHandoffHint.javaExternalAuditSystemAccessed()),
            ReleaseApprovalDigestSupport.line(
                "nodeMayTreatAsProductionAuditRecord",
                auditPersistenceHandoffHint.nodeMayTreatAsProductionAuditRecord()),
            ReleaseApprovalDigestSupport.line(
                "javaApprovalRecordPersisted",
                approvalRecordHandoffHint.javaApprovalRecordPersisted()),
            ReleaseApprovalDigestSupport.line(
                "nodeMayTreatAsProductionApprovalRecord",
                approvalRecordHandoffHint.nodeMayTreatAsProductionApprovalRecord()),
            ReleaseApprovalDigestSupport.line(
                "nodeV211ProductionAuditRecordAllowed",
                approvalHandoffVerificationMarker.nodeV211ProductionAuditRecordAllowed()),
            ReleaseApprovalDigestSupport.line(
                "nodeV211RealApprovalDecisionCreated",
                approvalHandoffVerificationMarker.nodeV211RealApprovalDecisionCreated()),
            ReleaseApprovalDigestSupport.line(
                "nodeV215MayConnectManagedAudit",
                managedAuditAdapterBoundaryReceipt.nodeV215MayConnectManagedAudit()),
            ReleaseApprovalDigestSupport.line(
                "nodeV215MayCreateApprovalDecision",
                managedAuditAdapterBoundaryReceipt.nodeV215MayCreateApprovalDecision()),
            ReleaseApprovalDigestSupport.line(
                "nodeV215MayWriteApprovalLedger",
                managedAuditAdapterBoundaryReceipt.nodeV215MayWriteApprovalLedger()),
            ReleaseApprovalDigestSupport.line(
                "nodeV215MayExecuteSql",
                managedAuditAdapterBoundaryReceipt.nodeV215MayExecuteSql()),
            ReleaseApprovalDigestSupport.line(
                "nodeV215MayTriggerDeployment",
                managedAuditAdapterBoundaryReceipt.nodeV215MayTriggerDeployment()),
            ReleaseApprovalDigestSupport.line(
                "nodeV215MayTriggerRollback",
                managedAuditAdapterBoundaryReceipt.nodeV215MayTriggerRollback()),
            ReleaseApprovalDigestSupport.line(
                "nodeV215MayExecuteRestore",
                managedAuditAdapterBoundaryReceipt.nodeV215MayExecuteRestore()),
            ReleaseApprovalDigestSupport.line(
                "nodeV217MayConnectManagedAudit",
                managedAuditProductionAdapterPrerequisiteReceipt.nodeV217MayConnectManagedAudit()),
            ReleaseApprovalDigestSupport.line(
                "nodeV217MayWriteApprovalLedger",
                managedAuditProductionAdapterPrerequisiteReceipt.nodeV217MayWriteApprovalLedger()),
            ReleaseApprovalDigestSupport.line(
                "nodeV217MayExecuteSql",
                managedAuditProductionAdapterPrerequisiteReceipt.nodeV217MayExecuteSql()),
            ReleaseApprovalDigestSupport.line(
                "nodeV217MayTriggerDeployment",
                managedAuditProductionAdapterPrerequisiteReceipt.nodeV217MayTriggerDeployment()),
            ReleaseApprovalDigestSupport.line(
                "nodeV217MayTriggerRollback",
                managedAuditProductionAdapterPrerequisiteReceipt.nodeV217MayTriggerRollback()),
            ReleaseApprovalDigestSupport.line(
                "nodeV217MayExecuteRestore",
                managedAuditProductionAdapterPrerequisiteReceipt.nodeV217MayExecuteRestore()),
            ReleaseApprovalDigestSupport.line(
                "qualitySplitApiShapeChanged",
                opsEvidenceServiceQualitySplitReceipt.apiShapeChanged()),
            ReleaseApprovalDigestSupport.line(
                "qualitySplitApprovalDecisionCreated",
                opsEvidenceServiceQualitySplitReceipt.approvalDecisionCreated()),
            ReleaseApprovalDigestSupport.line(
                "qualitySplitApprovalLedgerWritten",
                opsEvidenceServiceQualitySplitReceipt.approvalLedgerWritten()),
            ReleaseApprovalDigestSupport.line(
                "qualitySplitManagedAuditStoreWritten",
                opsEvidenceServiceQualitySplitReceipt.managedAuditStoreWritten()),
            ReleaseApprovalDigestSupport.line(
                "qualitySplitSqlExecuted", opsEvidenceServiceQualitySplitReceipt.sqlExecuted()),
            ReleaseApprovalDigestSupport.line(
                "implementationGuardDigest",
                managedAuditAdapterImplementationGuardReceipt.guardDigest()),
            ReleaseApprovalDigestSupport.line(
                "implementationGuardJavaApprovalLedgerWritten",
                managedAuditAdapterImplementationGuardReceipt.javaApprovalLedgerWritten()),
            ReleaseApprovalDigestSupport.line(
                "implementationGuardJavaManagedAuditStoreWritten",
                managedAuditAdapterImplementationGuardReceipt.javaManagedAuditStoreWritten()),
            ReleaseApprovalDigestSupport.line(
                "implementationGuardJavaSqlExecuted",
                managedAuditAdapterImplementationGuardReceipt.javaSqlExecuted()),
            ReleaseApprovalDigestSupport.line(
                "implementationGuardNodeV220AppendWritten",
                managedAuditAdapterImplementationGuardReceipt.nodeV220AppendWritten()),
            ReleaseApprovalDigestSupport.line(
                "implementationGuardNodeV220ExternalManagedAuditAccessed",
                managedAuditAdapterImplementationGuardReceipt
                    .nodeV220ExternalManagedAuditAccessed()),
            ReleaseApprovalDigestSupport.line(
                "implementationGuardNodeV220LocalDryRunWritePerformed",
                managedAuditAdapterImplementationGuardReceipt.nodeV220LocalDryRunWritePerformed()),
            ReleaseApprovalDigestSupport.line(
                "externalAdapterMigrationGuardDigest",
                managedAuditExternalAdapterMigrationGuardReceipt.guardDigest()),
            ReleaseApprovalDigestSupport.line(
                "externalAdapterMigrationCredentialValueReadByJava",
                managedAuditExternalAdapterMigrationGuardReceipt.credentialValueReadByJava()),
            ReleaseApprovalDigestSupport.line(
                "externalAdapterMigrationConnectionOpened",
                managedAuditExternalAdapterMigrationGuardReceipt
                    .externalManagedAuditConnectionOpened()),
            ReleaseApprovalDigestSupport.line(
                "externalAdapterMigrationSchemaMigrated",
                managedAuditExternalAdapterMigrationGuardReceipt
                    .externalManagedAuditSchemaMigrated()),
            ReleaseApprovalDigestSupport.line(
                "externalAdapterMigrationJavaManagedAuditStoreWritten",
                managedAuditExternalAdapterMigrationGuardReceipt.javaManagedAuditStoreWritten()),
            ReleaseApprovalDigestSupport.line(
                "externalAdapterMigrationJavaSqlExecuted",
                managedAuditExternalAdapterMigrationGuardReceipt.javaSqlExecuted()),
            ReleaseApprovalDigestSupport.line(
                "externalAdapterMigrationNodeV222SourceEndpointRerunPerformed",
                managedAuditExternalAdapterMigrationGuardReceipt
                    .nodeV222SourceEndpointRerunPerformed()),
            ReleaseApprovalDigestSupport.line(
                "externalAdapterMigrationNodeV222AdditionalLocalDryRunWritePerformed",
                managedAuditExternalAdapterMigrationGuardReceipt
                    .nodeV222AdditionalLocalDryRunWritePerformed())));
    lines.addAll(
        ReleaseApprovalVerificationWarningDigestLineCatalog.boundaryLines(
            lineBuilders, lineReceipts));
    lines.add(
        ReleaseApprovalDigestSupport.line(
            "nodeMayWriteApprovalLedger", executionBoundaries.nodeMayWriteApprovalLedger()));
    return ReleaseApprovalDigestSupport.digest(lines);
  }
}
