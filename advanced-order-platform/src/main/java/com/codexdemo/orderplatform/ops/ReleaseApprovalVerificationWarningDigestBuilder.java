package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverDisabledPrecheckEchoRecords
        .RehearsalManagedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker;

import java.util.ArrayList;
import java.util.List;

final class ReleaseApprovalVerificationWarningDigestBuilder {

    private final ReleaseApprovalManagedAuditSandboxAdapterApprovalSchemaGuardReceiptBuilder
            sandboxAdapterApprovalSchemaGuardReceiptBuilder;
    private final ReleaseApprovalManagedAuditSandboxConnectionOperatorHandoffMarkerBuilder
            sandboxConnectionOperatorHandoffMarkerBuilder;
    private final ReleaseApprovalManagedAuditSandboxConnectionPreflightEchoMarkerBuilder
            sandboxConnectionPreflightEchoMarkerBuilder;
    private final ReleaseApprovalManagedAuditSandboxConnectionPreconditionReceiptBuilder
            sandboxConnectionPreconditionReceiptBuilder;
    private final ReleaseApprovalManagedAuditSandboxConnectionDryRunEnvelopeEchoReceiptBuilder
            sandboxConnectionDryRunEnvelopeEchoReceiptBuilder;
    private final ReleaseApprovalManagedAuditSandboxConnectionOperatorWindowChecklistEchoReceiptBuilder
            sandboxConnectionOperatorWindowChecklistEchoReceiptBuilder;
    private final ReleaseApprovalManagedAuditSandboxConnectionDryRunCommandPackageEchoReceiptBuilder
            sandboxConnectionDryRunCommandPackageEchoReceiptBuilder;
    private final ReleaseApprovalManagedAuditSandboxConnectionPrecheckPacketEchoReceiptBuilder
            sandboxConnectionPrecheckPacketEchoReceiptBuilder;
    private final ReleaseApprovalManagedAuditSandboxConnectionDisabledAdapterClientPrecheckEchoReceiptBuilder
            sandboxConnectionDisabledAdapterClientPrecheckEchoReceiptBuilder;
    private final ReleaseApprovalManagedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarkerBuilder
            sandboxConnectionFakeTransportDryRunPacketEchoMarkerBuilder;
    private final ReleaseApprovalManagedAuditSandboxEndpointHandlePreflightEchoMarkerBuilder
            sandboxEndpointHandlePreflightEchoMarkerBuilder;
    private final ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverDecisionEchoMarkerBuilder
            sandboxEndpointCredentialResolverDecisionEchoMarkerBuilder;
    private final ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarkerBuilder
            sandboxEndpointCredentialResolverDisabledPrecheckEchoMarkerBuilder;

    ReleaseApprovalVerificationWarningDigestBuilder(
            ReleaseApprovalManagedAuditSandboxAdapterApprovalSchemaGuardReceiptBuilder
                    sandboxAdapterApprovalSchemaGuardReceiptBuilder,
            ReleaseApprovalManagedAuditSandboxConnectionOperatorHandoffMarkerBuilder
                    sandboxConnectionOperatorHandoffMarkerBuilder,
            ReleaseApprovalManagedAuditSandboxConnectionPreflightEchoMarkerBuilder
                    sandboxConnectionPreflightEchoMarkerBuilder,
            ReleaseApprovalManagedAuditSandboxConnectionPreconditionReceiptBuilder
                    sandboxConnectionPreconditionReceiptBuilder,
            ReleaseApprovalManagedAuditSandboxConnectionDryRunEnvelopeEchoReceiptBuilder
                    sandboxConnectionDryRunEnvelopeEchoReceiptBuilder,
            ReleaseApprovalManagedAuditSandboxConnectionOperatorWindowChecklistEchoReceiptBuilder
                    sandboxConnectionOperatorWindowChecklistEchoReceiptBuilder,
            ReleaseApprovalManagedAuditSandboxConnectionDryRunCommandPackageEchoReceiptBuilder
                    sandboxConnectionDryRunCommandPackageEchoReceiptBuilder,
            ReleaseApprovalManagedAuditSandboxConnectionPrecheckPacketEchoReceiptBuilder
                    sandboxConnectionPrecheckPacketEchoReceiptBuilder,
            ReleaseApprovalManagedAuditSandboxConnectionDisabledAdapterClientPrecheckEchoReceiptBuilder
                    sandboxConnectionDisabledAdapterClientPrecheckEchoReceiptBuilder,
            ReleaseApprovalManagedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarkerBuilder
                    sandboxConnectionFakeTransportDryRunPacketEchoMarkerBuilder,
            ReleaseApprovalManagedAuditSandboxEndpointHandlePreflightEchoMarkerBuilder
                    sandboxEndpointHandlePreflightEchoMarkerBuilder,
            ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverDecisionEchoMarkerBuilder
                    sandboxEndpointCredentialResolverDecisionEchoMarkerBuilder,
            ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarkerBuilder
                    sandboxEndpointCredentialResolverDisabledPrecheckEchoMarkerBuilder
    ) {
        this.sandboxAdapterApprovalSchemaGuardReceiptBuilder =
                sandboxAdapterApprovalSchemaGuardReceiptBuilder;
        this.sandboxConnectionOperatorHandoffMarkerBuilder =
                sandboxConnectionOperatorHandoffMarkerBuilder;
        this.sandboxConnectionPreflightEchoMarkerBuilder =
                sandboxConnectionPreflightEchoMarkerBuilder;
        this.sandboxConnectionPreconditionReceiptBuilder =
                sandboxConnectionPreconditionReceiptBuilder;
        this.sandboxConnectionDryRunEnvelopeEchoReceiptBuilder =
                sandboxConnectionDryRunEnvelopeEchoReceiptBuilder;
        this.sandboxConnectionOperatorWindowChecklistEchoReceiptBuilder =
                sandboxConnectionOperatorWindowChecklistEchoReceiptBuilder;
        this.sandboxConnectionDryRunCommandPackageEchoReceiptBuilder =
                sandboxConnectionDryRunCommandPackageEchoReceiptBuilder;
        this.sandboxConnectionPrecheckPacketEchoReceiptBuilder =
                sandboxConnectionPrecheckPacketEchoReceiptBuilder;
        this.sandboxConnectionDisabledAdapterClientPrecheckEchoReceiptBuilder =
                sandboxConnectionDisabledAdapterClientPrecheckEchoReceiptBuilder;
        this.sandboxConnectionFakeTransportDryRunPacketEchoMarkerBuilder =
                sandboxConnectionFakeTransportDryRunPacketEchoMarkerBuilder;
        this.sandboxEndpointHandlePreflightEchoMarkerBuilder =
                sandboxEndpointHandlePreflightEchoMarkerBuilder;
        this.sandboxEndpointCredentialResolverDecisionEchoMarkerBuilder =
                sandboxEndpointCredentialResolverDecisionEchoMarkerBuilder;
        this.sandboxEndpointCredentialResolverDisabledPrecheckEchoMarkerBuilder =
                sandboxEndpointCredentialResolverDisabledPrecheckEchoMarkerBuilder;
    }

    String build(
            ReleaseApprovalRehearsalResponse.RehearsalRequestContext requestContext,
            ReleaseApprovalRehearsalResponse.RehearsalOperatorWindowHint operatorWindowHint,
            ReleaseApprovalRehearsalResponse.RehearsalCiEvidenceHint ciEvidenceHint,
            ReleaseApprovalRehearsalResponse.RehearsalArtifactRetentionHint artifactRetentionHint,
            ReleaseApprovalRehearsalResponse.RehearsalLiveReadinessHint liveReadinessHint,
            ReleaseApprovalRehearsalResponse.RehearsalAuditPersistenceHandoffHint auditPersistenceHandoffHint,
            ReleaseApprovalRehearsalResponse.RehearsalApprovalRecordHandoffHint approvalRecordHandoffHint,
            ReleaseApprovalRehearsalResponse.RehearsalApprovalHandoffVerificationMarker
                    approvalHandoffVerificationMarker,
            ReleaseApprovalRehearsalResponse.RehearsalManagedAuditAdapterBoundaryReceipt
                    managedAuditAdapterBoundaryReceipt,
            ReleaseApprovalRehearsalResponse.RehearsalManagedAuditProductionAdapterPrerequisiteReceipt
                    managedAuditProductionAdapterPrerequisiteReceipt,
            ReleaseApprovalRehearsalResponse.RehearsalOpsEvidenceServiceQualitySplitReceipt
                    opsEvidenceServiceQualitySplitReceipt,
            ReleaseApprovalRehearsalResponse.RehearsalManagedAuditAdapterImplementationGuardReceipt
                    managedAuditAdapterImplementationGuardReceipt,
            ReleaseApprovalRehearsalResponse.RehearsalManagedAuditExternalAdapterMigrationGuardReceipt
                    managedAuditExternalAdapterMigrationGuardReceipt,
            ReleaseApprovalRehearsalResponse.RehearsalManagedAuditSandboxAdapterApprovalSchemaGuardReceipt
                    managedAuditSandboxAdapterApprovalSchemaGuardReceipt,
            ReleaseApprovalRehearsalResponse.RehearsalManagedAuditSandboxConnectionOperatorHandoffMarker
                    managedAuditSandboxConnectionOperatorHandoffMarker,
            ReleaseApprovalRehearsalResponse.RehearsalManagedAuditSandboxConnectionPreflightEchoMarker
                    managedAuditSandboxConnectionPreflightEchoMarker,
            ReleaseApprovalRehearsalResponse.RehearsalManagedAuditSandboxConnectionPreconditionReceipt
                    managedAuditSandboxConnectionPreconditionReceipt,
            ReleaseApprovalRehearsalResponse.RehearsalManagedAuditSandboxConnectionDryRunEnvelopeEchoReceipt
                    managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt,
            ReleaseApprovalRehearsalResponse
                    .RehearsalManagedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt
                    managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt,
            ReleaseApprovalRehearsalResponse
                    .RehearsalManagedAuditSandboxConnectionDryRunCommandPackageEchoReceipt
                    managedAuditSandboxConnectionDryRunCommandPackageEchoReceipt,
            ReleaseApprovalRehearsalResponse
                    .RehearsalManagedAuditSandboxConnectionPrecheckPacketEchoReceipt
                    managedAuditSandboxConnectionPrecheckPacketEchoReceipt,
            ReleaseApprovalRehearsalResponse
                    .RehearsalManagedAuditSandboxConnectionDisabledAdapterClientPrecheckEchoReceipt
                    managedAuditSandboxConnectionDisabledAdapterClientPrecheckEchoReceipt,
            ReleaseApprovalRehearsalResponse
                    .RehearsalManagedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker
                    managedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker,
            ReleaseApprovalRehearsalResponse
                    .RehearsalManagedAuditSandboxEndpointHandlePreflightEchoMarker
                    managedAuditSandboxEndpointHandlePreflightEchoMarker,
            ReleaseApprovalRehearsalResponse
                    .RehearsalManagedAuditSandboxEndpointCredentialResolverDecisionEchoMarker
                    managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker,
            RehearsalManagedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker
                    managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker,
            ReleaseApprovalRehearsalResponse.RehearsalFailureTaxonomy failureTaxonomy,
            ReleaseApprovalRehearsalResponse.ExecutionBoundaries executionBoundaries
    ) {
        List<String> lines = new ArrayList<>(List.of(
                ReleaseApprovalDigestSupport.line("digestKind", "releaseApprovalRehearsalWarning"),
                ReleaseApprovalDigestSupport.line(
                        "hintVersion",
                        OpsEvidenceService.RELEASE_APPROVAL_REHEARSAL_VERIFICATION_HINT_VERSION
                ),
                ReleaseApprovalDigestSupport.line(
                        "responseSchemaVersion",
                        OpsEvidenceService.RELEASE_APPROVAL_REHEARSAL_RESPONSE_SCHEMA_VERSION
                ),
                ReleaseApprovalDigestSupport.line("contextWarnings", requestContext.contextWarnings()),
                ReleaseApprovalDigestSupport.line("operatorWindowEchoWarnings", operatorWindowHint.echoWarnings()),
                ReleaseApprovalDigestSupport.line("ciEvidenceEchoWarnings", ciEvidenceHint.echoWarnings()),
                ReleaseApprovalDigestSupport.line("artifactRetentionEchoWarnings", artifactRetentionHint.echoWarnings()),
                ReleaseApprovalDigestSupport.line("liveReadinessEchoWarnings", liveReadinessHint.echoWarnings()),
                ReleaseApprovalDigestSupport.line(
                        "auditPersistenceHandoffEchoWarnings",
                        auditPersistenceHandoffHint.echoWarnings()
                ),
                ReleaseApprovalDigestSupport.line(
                        "approvalRecordHandoffEchoWarnings",
                        approvalRecordHandoffHint.echoWarnings()
                ),
                ReleaseApprovalDigestSupport.line(
                        "approvalHandoffVerificationMarkerWarnings",
                        approvalHandoffVerificationMarker.markerWarnings()
                ),
                ReleaseApprovalDigestSupport.line(
                        "managedAuditAdapterBoundaryReceiptWarnings",
                        managedAuditAdapterBoundaryReceipt.receiptWarnings()
                ),
                ReleaseApprovalDigestSupport.line(
                        "managedAuditProductionAdapterPrerequisiteReceiptWarnings",
                        managedAuditProductionAdapterPrerequisiteReceipt.receiptWarnings()
                ),
                ReleaseApprovalDigestSupport.line(
                        "opsEvidenceServiceQualitySplitReceiptWarnings",
                        opsEvidenceServiceQualitySplitReceipt.receiptWarnings()
                ),
                ReleaseApprovalDigestSupport.line(
                        "managedAuditAdapterImplementationGuardReceiptWarnings",
                        managedAuditAdapterImplementationGuardReceipt.guardWarnings()
                ),
                ReleaseApprovalDigestSupport.line(
                        "managedAuditExternalAdapterMigrationGuardReceiptWarnings",
                        managedAuditExternalAdapterMigrationGuardReceipt.guardWarnings()
                )
        ));
        lines.addAll(sandboxAdapterApprovalSchemaGuardReceiptBuilder.warningDigestWarningLines(
                managedAuditSandboxAdapterApprovalSchemaGuardReceipt
        ));
        lines.addAll(sandboxConnectionOperatorHandoffMarkerBuilder.warningDigestWarningLines(
                managedAuditSandboxConnectionOperatorHandoffMarker
        ));
        lines.addAll(sandboxConnectionPreflightEchoMarkerBuilder.warningDigestWarningLines(
                managedAuditSandboxConnectionPreflightEchoMarker
        ));
        lines.addAll(sandboxConnectionPreconditionReceiptBuilder.warningDigestWarningLines(
                managedAuditSandboxConnectionPreconditionReceipt
        ));
        lines.addAll(sandboxConnectionDryRunEnvelopeEchoReceiptBuilder.warningDigestWarningLines(
                managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt
        ));
        lines.addAll(sandboxConnectionOperatorWindowChecklistEchoReceiptBuilder.warningDigestWarningLines(
                managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt
        ));
        lines.addAll(sandboxConnectionDryRunCommandPackageEchoReceiptBuilder.warningDigestWarningLines(
                managedAuditSandboxConnectionDryRunCommandPackageEchoReceipt
        ));
        lines.addAll(sandboxConnectionPrecheckPacketEchoReceiptBuilder.warningDigestWarningLines(
                managedAuditSandboxConnectionPrecheckPacketEchoReceipt
        ));
        lines.addAll(sandboxConnectionDisabledAdapterClientPrecheckEchoReceiptBuilder.warningDigestWarningLines(
                managedAuditSandboxConnectionDisabledAdapterClientPrecheckEchoReceipt
        ));
        lines.addAll(sandboxConnectionFakeTransportDryRunPacketEchoMarkerBuilder.warningDigestWarningLines(
                managedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker
        ));
        lines.addAll(sandboxEndpointHandlePreflightEchoMarkerBuilder.warningDigestWarningLines(
                managedAuditSandboxEndpointHandlePreflightEchoMarker
        ));
        lines.addAll(sandboxEndpointCredentialResolverDecisionEchoMarkerBuilder.warningDigestWarningLines(
                managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker
        ));
        lines.addAll(sandboxEndpointCredentialResolverDisabledPrecheckEchoMarkerBuilder.warningDigestWarningLines(
                managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker
        ));
        lines.addAll(List.of(
                ReleaseApprovalDigestSupport.line("failureCategories", failureTaxonomy.failureCategories()),
                ReleaseApprovalDigestSupport.line("taxonomyWarnings", failureTaxonomy.taxonomyWarnings()),
                ReleaseApprovalDigestSupport.line("executionAllowed", false),
                ReleaseApprovalDigestSupport.line("approvalLedgerWritten", requestContext.approvalLedgerWritten()),
                ReleaseApprovalDigestSupport.line("ciArtifactUploadedByJava", ciEvidenceHint.ciArtifactUploadedByJava()),
                ReleaseApprovalDigestSupport.line(
                        "githubArtifactAccessedByJava",
                        ciEvidenceHint.githubArtifactAccessedByJava()
                ),
                ReleaseApprovalDigestSupport.line(
                        "retentionCiArtifactUploadedByJava",
                        artifactRetentionHint.ciArtifactUploadedByJava()
                ),
                ReleaseApprovalDigestSupport.line(
                        "retentionGithubArtifactAccessedByJava",
                        artifactRetentionHint.githubArtifactAccessedByJava()
                ),
                ReleaseApprovalDigestSupport.line(
                        "retentionAuthorization",
                        artifactRetentionHint.nodeMayTreatAsRetentionAuthorization()
                ),
                ReleaseApprovalDigestSupport.line(
                        "runtimeSmokeExecutedByJava",
                        liveReadinessHint.runtimeSmokeExecutedByJava()
                ),
                ReleaseApprovalDigestSupport.line("javaStartedProcessForNode", liveReadinessHint.javaStartedProcessForNode()),
                ReleaseApprovalDigestSupport.line(
                        "nodeMayTreatAsProductionAuthorization",
                        liveReadinessHint.nodeMayTreatAsProductionAuthorization()
                ),
                ReleaseApprovalDigestSupport.line(
                        "javaManagedAuditWriteAllowed",
                        auditPersistenceHandoffHint.javaManagedAuditWriteAllowed()
                ),
                ReleaseApprovalDigestSupport.line(
                        "javaExternalAuditSystemAccessed",
                        auditPersistenceHandoffHint.javaExternalAuditSystemAccessed()
                ),
                ReleaseApprovalDigestSupport.line(
                        "nodeMayTreatAsProductionAuditRecord",
                        auditPersistenceHandoffHint.nodeMayTreatAsProductionAuditRecord()
                ),
                ReleaseApprovalDigestSupport.line(
                        "javaApprovalRecordPersisted",
                        approvalRecordHandoffHint.javaApprovalRecordPersisted()
                ),
                ReleaseApprovalDigestSupport.line(
                        "nodeMayTreatAsProductionApprovalRecord",
                        approvalRecordHandoffHint.nodeMayTreatAsProductionApprovalRecord()
                ),
                ReleaseApprovalDigestSupport.line(
                        "nodeV211ProductionAuditRecordAllowed",
                        approvalHandoffVerificationMarker.nodeV211ProductionAuditRecordAllowed()
                ),
                ReleaseApprovalDigestSupport.line(
                        "nodeV211RealApprovalDecisionCreated",
                        approvalHandoffVerificationMarker.nodeV211RealApprovalDecisionCreated()
                ),
                ReleaseApprovalDigestSupport.line(
                        "nodeV215MayConnectManagedAudit",
                        managedAuditAdapterBoundaryReceipt.nodeV215MayConnectManagedAudit()
                ),
                ReleaseApprovalDigestSupport.line(
                        "nodeV215MayCreateApprovalDecision",
                        managedAuditAdapterBoundaryReceipt.nodeV215MayCreateApprovalDecision()
                ),
                ReleaseApprovalDigestSupport.line(
                        "nodeV215MayWriteApprovalLedger",
                        managedAuditAdapterBoundaryReceipt.nodeV215MayWriteApprovalLedger()
                ),
                ReleaseApprovalDigestSupport.line(
                        "nodeV215MayExecuteSql",
                        managedAuditAdapterBoundaryReceipt.nodeV215MayExecuteSql()
                ),
                ReleaseApprovalDigestSupport.line(
                        "nodeV215MayTriggerDeployment",
                        managedAuditAdapterBoundaryReceipt.nodeV215MayTriggerDeployment()
                ),
                ReleaseApprovalDigestSupport.line(
                        "nodeV215MayTriggerRollback",
                        managedAuditAdapterBoundaryReceipt.nodeV215MayTriggerRollback()
                ),
                ReleaseApprovalDigestSupport.line(
                        "nodeV215MayExecuteRestore",
                        managedAuditAdapterBoundaryReceipt.nodeV215MayExecuteRestore()
                ),
                ReleaseApprovalDigestSupport.line(
                        "nodeV217MayConnectManagedAudit",
                        managedAuditProductionAdapterPrerequisiteReceipt.nodeV217MayConnectManagedAudit()
                ),
                ReleaseApprovalDigestSupport.line(
                        "nodeV217MayWriteApprovalLedger",
                        managedAuditProductionAdapterPrerequisiteReceipt.nodeV217MayWriteApprovalLedger()
                ),
                ReleaseApprovalDigestSupport.line(
                        "nodeV217MayExecuteSql",
                        managedAuditProductionAdapterPrerequisiteReceipt.nodeV217MayExecuteSql()
                ),
                ReleaseApprovalDigestSupport.line(
                        "nodeV217MayTriggerDeployment",
                        managedAuditProductionAdapterPrerequisiteReceipt.nodeV217MayTriggerDeployment()
                ),
                ReleaseApprovalDigestSupport.line(
                        "nodeV217MayTriggerRollback",
                        managedAuditProductionAdapterPrerequisiteReceipt.nodeV217MayTriggerRollback()
                ),
                ReleaseApprovalDigestSupport.line(
                        "nodeV217MayExecuteRestore",
                        managedAuditProductionAdapterPrerequisiteReceipt.nodeV217MayExecuteRestore()
                ),
                ReleaseApprovalDigestSupport.line(
                        "qualitySplitApiShapeChanged",
                        opsEvidenceServiceQualitySplitReceipt.apiShapeChanged()
                ),
                ReleaseApprovalDigestSupport.line(
                        "qualitySplitApprovalDecisionCreated",
                        opsEvidenceServiceQualitySplitReceipt.approvalDecisionCreated()
                ),
                ReleaseApprovalDigestSupport.line(
                        "qualitySplitApprovalLedgerWritten",
                        opsEvidenceServiceQualitySplitReceipt.approvalLedgerWritten()
                ),
                ReleaseApprovalDigestSupport.line(
                        "qualitySplitManagedAuditStoreWritten",
                        opsEvidenceServiceQualitySplitReceipt.managedAuditStoreWritten()
                ),
                ReleaseApprovalDigestSupport.line(
                        "qualitySplitSqlExecuted",
                        opsEvidenceServiceQualitySplitReceipt.sqlExecuted()
                ),
                ReleaseApprovalDigestSupport.line(
                        "implementationGuardDigest",
                        managedAuditAdapterImplementationGuardReceipt.guardDigest()
                ),
                ReleaseApprovalDigestSupport.line(
                        "implementationGuardJavaApprovalLedgerWritten",
                        managedAuditAdapterImplementationGuardReceipt.javaApprovalLedgerWritten()
                ),
                ReleaseApprovalDigestSupport.line(
                        "implementationGuardJavaManagedAuditStoreWritten",
                        managedAuditAdapterImplementationGuardReceipt.javaManagedAuditStoreWritten()
                ),
                ReleaseApprovalDigestSupport.line(
                        "implementationGuardJavaSqlExecuted",
                        managedAuditAdapterImplementationGuardReceipt.javaSqlExecuted()
                ),
                ReleaseApprovalDigestSupport.line(
                        "implementationGuardNodeV220AppendWritten",
                        managedAuditAdapterImplementationGuardReceipt.nodeV220AppendWritten()
                ),
                ReleaseApprovalDigestSupport.line(
                        "implementationGuardNodeV220ExternalManagedAuditAccessed",
                        managedAuditAdapterImplementationGuardReceipt.nodeV220ExternalManagedAuditAccessed()
                ),
                ReleaseApprovalDigestSupport.line(
                        "implementationGuardNodeV220LocalDryRunWritePerformed",
                        managedAuditAdapterImplementationGuardReceipt.nodeV220LocalDryRunWritePerformed()
                ),
                ReleaseApprovalDigestSupport.line(
                        "externalAdapterMigrationGuardDigest",
                        managedAuditExternalAdapterMigrationGuardReceipt.guardDigest()
                ),
                ReleaseApprovalDigestSupport.line(
                        "externalAdapterMigrationCredentialValueReadByJava",
                        managedAuditExternalAdapterMigrationGuardReceipt.credentialValueReadByJava()
                ),
                ReleaseApprovalDigestSupport.line(
                        "externalAdapterMigrationConnectionOpened",
                        managedAuditExternalAdapterMigrationGuardReceipt.externalManagedAuditConnectionOpened()
                ),
                ReleaseApprovalDigestSupport.line(
                        "externalAdapterMigrationSchemaMigrated",
                        managedAuditExternalAdapterMigrationGuardReceipt.externalManagedAuditSchemaMigrated()
                ),
                ReleaseApprovalDigestSupport.line(
                        "externalAdapterMigrationJavaManagedAuditStoreWritten",
                        managedAuditExternalAdapterMigrationGuardReceipt.javaManagedAuditStoreWritten()
                ),
                ReleaseApprovalDigestSupport.line(
                        "externalAdapterMigrationJavaSqlExecuted",
                        managedAuditExternalAdapterMigrationGuardReceipt.javaSqlExecuted()
                ),
                ReleaseApprovalDigestSupport.line(
                        "externalAdapterMigrationNodeV222SourceEndpointRerunPerformed",
                        managedAuditExternalAdapterMigrationGuardReceipt.nodeV222SourceEndpointRerunPerformed()
                ),
                ReleaseApprovalDigestSupport.line(
                        "externalAdapterMigrationNodeV222AdditionalLocalDryRunWritePerformed",
                        managedAuditExternalAdapterMigrationGuardReceipt.nodeV222AdditionalLocalDryRunWritePerformed()
                )
        ));
        lines.addAll(sandboxAdapterApprovalSchemaGuardReceiptBuilder.warningDigestBoundaryLines(
                managedAuditSandboxAdapterApprovalSchemaGuardReceipt
        ));
        lines.addAll(sandboxConnectionOperatorHandoffMarkerBuilder.warningDigestBoundaryLines(
                managedAuditSandboxConnectionOperatorHandoffMarker
        ));
        lines.addAll(sandboxConnectionPreflightEchoMarkerBuilder.warningDigestBoundaryLines(
                managedAuditSandboxConnectionPreflightEchoMarker
        ));
        lines.addAll(sandboxConnectionPreconditionReceiptBuilder.warningDigestBoundaryLines(
                managedAuditSandboxConnectionPreconditionReceipt
        ));
        lines.addAll(sandboxConnectionDryRunEnvelopeEchoReceiptBuilder.warningDigestBoundaryLines(
                managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt
        ));
        lines.addAll(sandboxConnectionOperatorWindowChecklistEchoReceiptBuilder.warningDigestBoundaryLines(
                managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt
        ));
        lines.addAll(sandboxConnectionDryRunCommandPackageEchoReceiptBuilder.warningDigestBoundaryLines(
                managedAuditSandboxConnectionDryRunCommandPackageEchoReceipt
        ));
        lines.addAll(sandboxConnectionPrecheckPacketEchoReceiptBuilder.warningDigestBoundaryLines(
                managedAuditSandboxConnectionPrecheckPacketEchoReceipt
        ));
        lines.addAll(sandboxConnectionDisabledAdapterClientPrecheckEchoReceiptBuilder.warningDigestBoundaryLines(
                managedAuditSandboxConnectionDisabledAdapterClientPrecheckEchoReceipt
        ));
        lines.addAll(sandboxConnectionFakeTransportDryRunPacketEchoMarkerBuilder.warningDigestBoundaryLines(
                managedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker
        ));
        lines.addAll(sandboxEndpointHandlePreflightEchoMarkerBuilder.warningDigestBoundaryLines(
                managedAuditSandboxEndpointHandlePreflightEchoMarker
        ));
        lines.addAll(sandboxEndpointCredentialResolverDecisionEchoMarkerBuilder.warningDigestBoundaryLines(
                managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker
        ));
        lines.addAll(sandboxEndpointCredentialResolverDisabledPrecheckEchoMarkerBuilder.warningDigestBoundaryLines(
                managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker
        ));
        lines.add(ReleaseApprovalDigestSupport.line(
                "nodeMayWriteApprovalLedger",
                executionBoundaries.nodeMayWriteApprovalLedger()
        ));
        return ReleaseApprovalDigestSupport.digest(lines);
    }
}
