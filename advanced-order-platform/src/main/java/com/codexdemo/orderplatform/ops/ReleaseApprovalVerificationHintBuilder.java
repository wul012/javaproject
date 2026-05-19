package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverDisabledPrecheckEchoRecords
        .RehearsalManagedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker;
import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverTestOnlyShellEchoRecords
        .RehearsalManagedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarker;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

final class ReleaseApprovalVerificationHintBuilder {

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
    private final ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarkerBuilder
            sandboxEndpointCredentialResolverTestOnlyShellEchoMarkerBuilder;
    private final List<VerificationContribution> verificationContributions;
    private final ReleaseApprovalVerificationWarningDigestBuilder warningDigestBuilder;

    ReleaseApprovalVerificationHintBuilder(
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
                    sandboxEndpointCredentialResolverDisabledPrecheckEchoMarkerBuilder,
            ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarkerBuilder
                    sandboxEndpointCredentialResolverTestOnlyShellEchoMarkerBuilder
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
        this.sandboxEndpointCredentialResolverTestOnlyShellEchoMarkerBuilder =
                sandboxEndpointCredentialResolverTestOnlyShellEchoMarkerBuilder;
        this.verificationContributions = List.of(
                contribution(
                        sandboxAdapterApprovalSchemaGuardReceiptBuilder::warningDigestWarningInputNames,
                        sandboxAdapterApprovalSchemaGuardReceiptBuilder::warningDigestBoundaryInputNames,
                        sandboxAdapterApprovalSchemaGuardReceiptBuilder::proofClaims,
                        () -> sandboxAdapterApprovalSchemaGuardReceiptBuilder.nodeVerificationActions().stream()
                                .filter(action -> !("Verify managedAuditSandboxAdapterApprovalSchemaGuardReceipt"
                                        + ".qualityGateBoundary.builderOrHelperSplitApplied=true").equals(action))
                                .toList()
                ),
                contribution(sandboxConnectionOperatorHandoffMarkerBuilder::warningDigestWarningInputNames,
                        sandboxConnectionOperatorHandoffMarkerBuilder::warningDigestBoundaryInputNames,
                        sandboxConnectionOperatorHandoffMarkerBuilder::proofClaims,
                        sandboxConnectionOperatorHandoffMarkerBuilder::nodeVerificationActions),
                contribution(sandboxConnectionPreflightEchoMarkerBuilder::warningDigestWarningInputNames,
                        sandboxConnectionPreflightEchoMarkerBuilder::warningDigestBoundaryInputNames,
                        sandboxConnectionPreflightEchoMarkerBuilder::proofClaims,
                        sandboxConnectionPreflightEchoMarkerBuilder::nodeVerificationActions),
                contribution(sandboxConnectionPreconditionReceiptBuilder::warningDigestWarningInputNames,
                        sandboxConnectionPreconditionReceiptBuilder::warningDigestBoundaryInputNames,
                        sandboxConnectionPreconditionReceiptBuilder::proofClaims,
                        sandboxConnectionPreconditionReceiptBuilder::nodeVerificationActions),
                contribution(sandboxConnectionDryRunEnvelopeEchoReceiptBuilder::warningDigestWarningInputNames,
                        sandboxConnectionDryRunEnvelopeEchoReceiptBuilder::warningDigestBoundaryInputNames,
                        sandboxConnectionDryRunEnvelopeEchoReceiptBuilder::proofClaims,
                        sandboxConnectionDryRunEnvelopeEchoReceiptBuilder::nodeVerificationActions),
                contribution(sandboxConnectionOperatorWindowChecklistEchoReceiptBuilder::warningDigestWarningInputNames,
                        sandboxConnectionOperatorWindowChecklistEchoReceiptBuilder::warningDigestBoundaryInputNames,
                        sandboxConnectionOperatorWindowChecklistEchoReceiptBuilder::proofClaims,
                        sandboxConnectionOperatorWindowChecklistEchoReceiptBuilder::nodeVerificationActions),
                contribution(sandboxConnectionDryRunCommandPackageEchoReceiptBuilder::warningDigestWarningInputNames,
                        sandboxConnectionDryRunCommandPackageEchoReceiptBuilder::warningDigestBoundaryInputNames,
                        sandboxConnectionDryRunCommandPackageEchoReceiptBuilder::proofClaims,
                        sandboxConnectionDryRunCommandPackageEchoReceiptBuilder::nodeVerificationActions),
                contribution(sandboxConnectionPrecheckPacketEchoReceiptBuilder::warningDigestWarningInputNames,
                        sandboxConnectionPrecheckPacketEchoReceiptBuilder::warningDigestBoundaryInputNames,
                        sandboxConnectionPrecheckPacketEchoReceiptBuilder::proofClaims,
                        sandboxConnectionPrecheckPacketEchoReceiptBuilder::nodeVerificationActions),
                contribution(sandboxConnectionDisabledAdapterClientPrecheckEchoReceiptBuilder
                                ::warningDigestWarningInputNames,
                        sandboxConnectionDisabledAdapterClientPrecheckEchoReceiptBuilder
                                ::warningDigestBoundaryInputNames,
                        sandboxConnectionDisabledAdapterClientPrecheckEchoReceiptBuilder::proofClaims,
                        sandboxConnectionDisabledAdapterClientPrecheckEchoReceiptBuilder::nodeVerificationActions),
                contribution(sandboxConnectionFakeTransportDryRunPacketEchoMarkerBuilder
                                ::warningDigestWarningInputNames,
                        sandboxConnectionFakeTransportDryRunPacketEchoMarkerBuilder
                                ::warningDigestBoundaryInputNames,
                        sandboxConnectionFakeTransportDryRunPacketEchoMarkerBuilder::proofClaims,
                        sandboxConnectionFakeTransportDryRunPacketEchoMarkerBuilder::nodeVerificationActions),
                contribution(sandboxEndpointHandlePreflightEchoMarkerBuilder::warningDigestWarningInputNames,
                        sandboxEndpointHandlePreflightEchoMarkerBuilder::warningDigestBoundaryInputNames,
                        sandboxEndpointHandlePreflightEchoMarkerBuilder::proofClaims,
                        sandboxEndpointHandlePreflightEchoMarkerBuilder::nodeVerificationActions),
                contribution(sandboxEndpointCredentialResolverDecisionEchoMarkerBuilder
                                ::warningDigestWarningInputNames,
                        sandboxEndpointCredentialResolverDecisionEchoMarkerBuilder
                                ::warningDigestBoundaryInputNames,
                        sandboxEndpointCredentialResolverDecisionEchoMarkerBuilder::proofClaims,
                        sandboxEndpointCredentialResolverDecisionEchoMarkerBuilder::nodeVerificationActions),
                contribution(sandboxEndpointCredentialResolverDisabledPrecheckEchoMarkerBuilder
                                ::warningDigestWarningInputNames,
                        sandboxEndpointCredentialResolverDisabledPrecheckEchoMarkerBuilder
                                ::warningDigestBoundaryInputNames,
                        sandboxEndpointCredentialResolverDisabledPrecheckEchoMarkerBuilder::proofClaims,
                        sandboxEndpointCredentialResolverDisabledPrecheckEchoMarkerBuilder::nodeVerificationActions),
                contribution(sandboxEndpointCredentialResolverTestOnlyShellEchoMarkerBuilder
                                ::warningDigestWarningInputNames,
                        sandboxEndpointCredentialResolverTestOnlyShellEchoMarkerBuilder
                                ::warningDigestBoundaryInputNames,
                        sandboxEndpointCredentialResolverTestOnlyShellEchoMarkerBuilder::proofClaims,
                        sandboxEndpointCredentialResolverTestOnlyShellEchoMarkerBuilder::nodeVerificationActions)
        );
        this.warningDigestBuilder = new ReleaseApprovalVerificationWarningDigestBuilder(
                sandboxAdapterApprovalSchemaGuardReceiptBuilder,
                sandboxConnectionOperatorHandoffMarkerBuilder,
                sandboxConnectionPreflightEchoMarkerBuilder,
                sandboxConnectionPreconditionReceiptBuilder,
                sandboxConnectionDryRunEnvelopeEchoReceiptBuilder,
                sandboxConnectionOperatorWindowChecklistEchoReceiptBuilder,
                sandboxConnectionDryRunCommandPackageEchoReceiptBuilder,
                sandboxConnectionPrecheckPacketEchoReceiptBuilder,
                sandboxConnectionDisabledAdapterClientPrecheckEchoReceiptBuilder,
                sandboxConnectionFakeTransportDryRunPacketEchoMarkerBuilder,
                sandboxEndpointHandlePreflightEchoMarkerBuilder,
                sandboxEndpointCredentialResolverDecisionEchoMarkerBuilder,
                sandboxEndpointCredentialResolverDisabledPrecheckEchoMarkerBuilder,
                sandboxEndpointCredentialResolverTestOnlyShellEchoMarkerBuilder
        );
    }

    ReleaseApprovalRehearsalResponseRecords.RehearsalVerificationHint build(
            ReleaseApprovalRehearsalResponseRecords.RehearsalRequestContext requestContext,
            ReleaseApprovalRehearsalResponseRecords.RehearsalOperatorWindowHint operatorWindowHint,
            ReleaseApprovalRehearsalResponseRecords.RehearsalCiEvidenceHint ciEvidenceHint,
            ReleaseApprovalRehearsalResponseRecords.RehearsalArtifactRetentionHint artifactRetentionHint,
            ReleaseApprovalRehearsalResponseRecords.RehearsalLiveReadinessHint liveReadinessHint,
            ReleaseApprovalRehearsalResponseRecords.RehearsalAuditPersistenceHandoffHint auditPersistenceHandoffHint,
            ReleaseApprovalRehearsalResponseRecords.RehearsalApprovalRecordHandoffHint approvalRecordHandoffHint,
            ReleaseApprovalRehearsalResponseRecords.RehearsalApprovalHandoffVerificationMarker
                    approvalHandoffVerificationMarker,
            ReleaseApprovalRehearsalResponseRecords.RehearsalManagedAuditAdapterBoundaryReceipt
                    managedAuditAdapterBoundaryReceipt,
            ReleaseApprovalRehearsalResponseRecords.RehearsalManagedAuditProductionAdapterPrerequisiteReceipt
                    managedAuditProductionAdapterPrerequisiteReceipt,
            ReleaseApprovalRehearsalResponseRecords.RehearsalOpsEvidenceServiceQualitySplitReceipt
                    opsEvidenceServiceQualitySplitReceipt,
            ReleaseApprovalRehearsalResponseRecords.RehearsalManagedAuditAdapterImplementationGuardReceipt
                    managedAuditAdapterImplementationGuardReceipt,
            ReleaseApprovalRehearsalResponseRecords.RehearsalManagedAuditExternalAdapterMigrationGuardReceipt
                    managedAuditExternalAdapterMigrationGuardReceipt,
            ReleaseApprovalRehearsalResponseRecords.RehearsalManagedAuditSandboxAdapterApprovalSchemaGuardReceipt
                    managedAuditSandboxAdapterApprovalSchemaGuardReceipt,
            ReleaseApprovalRehearsalResponseRecords.RehearsalManagedAuditSandboxConnectionOperatorHandoffMarker
                    managedAuditSandboxConnectionOperatorHandoffMarker,
            ReleaseApprovalRehearsalResponseRecords.RehearsalManagedAuditSandboxConnectionPreflightEchoMarker
                    managedAuditSandboxConnectionPreflightEchoMarker,
            ReleaseApprovalRehearsalResponseRecords.RehearsalManagedAuditSandboxConnectionPreconditionReceipt
                    managedAuditSandboxConnectionPreconditionReceipt,
            ReleaseApprovalRehearsalResponseRecords.RehearsalManagedAuditSandboxConnectionDryRunEnvelopeEchoReceipt
                    managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt,
            ReleaseApprovalRehearsalResponseRecords.RehearsalManagedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt
                    managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt,
            ReleaseApprovalRehearsalResponseRecords.RehearsalManagedAuditSandboxConnectionDryRunCommandPackageEchoReceipt
                    managedAuditSandboxConnectionDryRunCommandPackageEchoReceipt,
            ReleaseApprovalRehearsalResponseRecords.RehearsalManagedAuditSandboxConnectionPrecheckPacketEchoReceipt
                    managedAuditSandboxConnectionPrecheckPacketEchoReceipt,
            ReleaseApprovalRehearsalResponseRecords.RehearsalManagedAuditSandboxConnectionDisabledAdapterClientPrecheckEchoReceipt
                    managedAuditSandboxConnectionDisabledAdapterClientPrecheckEchoReceipt,
            ReleaseApprovalRehearsalResponseRecords.RehearsalManagedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker
                    managedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker,
            ReleaseApprovalRehearsalResponseRecords.RehearsalManagedAuditSandboxEndpointHandlePreflightEchoMarker
                    managedAuditSandboxEndpointHandlePreflightEchoMarker,
            ReleaseApprovalRehearsalResponseRecords.RehearsalManagedAuditSandboxEndpointCredentialResolverDecisionEchoMarker
                    managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker,
            RehearsalManagedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker
                    managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker,
            RehearsalManagedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarker
                    managedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarker,
            ReleaseApprovalRehearsalResponseRecords.RehearsalFailureTaxonomy failureTaxonomy,
            ReleaseApprovalRehearsalResponseRecords.ExecutionBoundaries executionBoundaries
    ) {
        return new ReleaseApprovalRehearsalResponseRecords.RehearsalVerificationHint(
                OpsEvidenceService.RELEASE_APPROVAL_REHEARSAL_VERIFICATION_HINT_VERSION,
                OpsEvidenceService.RELEASE_APPROVAL_REHEARSAL_RESPONSE_SCHEMA_VERSION,
                warningDigestBuilder.build(
                        requestContext,
                        operatorWindowHint,
                        ciEvidenceHint,
                        artifactRetentionHint,
                        liveReadinessHint,
                        auditPersistenceHandoffHint,
                        approvalRecordHandoffHint,
                        approvalHandoffVerificationMarker,
                        managedAuditAdapterBoundaryReceipt,
                        managedAuditProductionAdapterPrerequisiteReceipt,
                        opsEvidenceServiceQualitySplitReceipt,
                        managedAuditAdapterImplementationGuardReceipt,
                        managedAuditExternalAdapterMigrationGuardReceipt,
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
                        failureTaxonomy,
                        executionBoundaries
                ),
                "NO_LEDGER_WRITE_PROOF_BY_RESPONSE_FIELDS",
                noLedgerWriteProved(
                        requestContext,
                        ciEvidenceHint,
                        artifactRetentionHint,
                        liveReadinessHint,
                        auditPersistenceHandoffHint,
                        approvalRecordHandoffHint,
                        approvalHandoffVerificationMarker,
                        managedAuditAdapterBoundaryReceipt,
                        managedAuditProductionAdapterPrerequisiteReceipt,
                        opsEvidenceServiceQualitySplitReceipt,
                        managedAuditAdapterImplementationGuardReceipt,
                        managedAuditExternalAdapterMigrationGuardReceipt,
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
                        executionBoundaries
                ),
                false,
                schemaFields(),
                warningDigestInputs(),
                proofClaims(),
                nodeVerificationActions()
        );
    }

    private List<String> warningDigestInputs() {
        List<String> inputs = new ArrayList<>(List.of(
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
        ));
        verificationContributions.forEach(contribution ->
                inputs.addAll(contribution.warningDigestWarningInputValues()));
        inputs.addAll(List.of(
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
        ));
        verificationContributions.forEach(contribution ->
                inputs.addAll(contribution.warningDigestBoundaryInputValues()));
        inputs.add("nodeMayWriteApprovalLedger");
        return inputs;
    }

    private List<String> proofClaims() {
        List<String> claims = new ArrayList<>(List.of(
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
        ));
        verificationContributions.forEach(contribution -> claims.addAll(contribution.proofClaimValues()));
        claims.addAll(List.of(
                "executionBoundaries.nodeMayCreateApprovalDecision=false",
                "executionBoundaries.nodeMayWriteApprovalLedger=false",
                "executionBoundaries.nodeMayTriggerDeployment=false",
                "executionBoundaries.nodeMayTriggerRollback=false",
                "executionBoundaries.nodeMayExecuteRollbackSql=false"
        ));
        return claims;
    }

    private boolean noLedgerWriteProved(
            ReleaseApprovalRehearsalResponseRecords.RehearsalRequestContext requestContext,
            ReleaseApprovalRehearsalResponseRecords.RehearsalCiEvidenceHint ciEvidenceHint,
            ReleaseApprovalRehearsalResponseRecords.RehearsalArtifactRetentionHint artifactRetentionHint,
            ReleaseApprovalRehearsalResponseRecords.RehearsalLiveReadinessHint liveReadinessHint,
            ReleaseApprovalRehearsalResponseRecords.RehearsalAuditPersistenceHandoffHint auditPersistenceHandoffHint,
            ReleaseApprovalRehearsalResponseRecords.RehearsalApprovalRecordHandoffHint approvalRecordHandoffHint,
            ReleaseApprovalRehearsalResponseRecords.RehearsalApprovalHandoffVerificationMarker
                    approvalHandoffVerificationMarker,
            ReleaseApprovalRehearsalResponseRecords.RehearsalManagedAuditAdapterBoundaryReceipt
                    managedAuditAdapterBoundaryReceipt,
            ReleaseApprovalRehearsalResponseRecords.RehearsalManagedAuditProductionAdapterPrerequisiteReceipt
                    managedAuditProductionAdapterPrerequisiteReceipt,
            ReleaseApprovalRehearsalResponseRecords.RehearsalOpsEvidenceServiceQualitySplitReceipt
                    opsEvidenceServiceQualitySplitReceipt,
            ReleaseApprovalRehearsalResponseRecords.RehearsalManagedAuditAdapterImplementationGuardReceipt
                    managedAuditAdapterImplementationGuardReceipt,
            ReleaseApprovalRehearsalResponseRecords.RehearsalManagedAuditExternalAdapterMigrationGuardReceipt
                    managedAuditExternalAdapterMigrationGuardReceipt,
            ReleaseApprovalRehearsalResponseRecords.RehearsalManagedAuditSandboxAdapterApprovalSchemaGuardReceipt
                    managedAuditSandboxAdapterApprovalSchemaGuardReceipt,
            ReleaseApprovalRehearsalResponseRecords.RehearsalManagedAuditSandboxConnectionOperatorHandoffMarker
                    managedAuditSandboxConnectionOperatorHandoffMarker,
            ReleaseApprovalRehearsalResponseRecords.RehearsalManagedAuditSandboxConnectionPreflightEchoMarker
                    managedAuditSandboxConnectionPreflightEchoMarker,
            ReleaseApprovalRehearsalResponseRecords.RehearsalManagedAuditSandboxConnectionPreconditionReceipt
                    managedAuditSandboxConnectionPreconditionReceipt,
            ReleaseApprovalRehearsalResponseRecords.RehearsalManagedAuditSandboxConnectionDryRunEnvelopeEchoReceipt
                    managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt,
            ReleaseApprovalRehearsalResponseRecords.RehearsalManagedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt
                    managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt,
            ReleaseApprovalRehearsalResponseRecords.RehearsalManagedAuditSandboxConnectionDryRunCommandPackageEchoReceipt
                    managedAuditSandboxConnectionDryRunCommandPackageEchoReceipt,
            ReleaseApprovalRehearsalResponseRecords.RehearsalManagedAuditSandboxConnectionPrecheckPacketEchoReceipt
                    managedAuditSandboxConnectionPrecheckPacketEchoReceipt,
            ReleaseApprovalRehearsalResponseRecords.RehearsalManagedAuditSandboxConnectionDisabledAdapterClientPrecheckEchoReceipt
                    managedAuditSandboxConnectionDisabledAdapterClientPrecheckEchoReceipt,
            ReleaseApprovalRehearsalResponseRecords.RehearsalManagedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker
                    managedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker,
            ReleaseApprovalRehearsalResponseRecords.RehearsalManagedAuditSandboxEndpointHandlePreflightEchoMarker
                    managedAuditSandboxEndpointHandlePreflightEchoMarker,
            ReleaseApprovalRehearsalResponseRecords.RehearsalManagedAuditSandboxEndpointCredentialResolverDecisionEchoMarker
                    managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker,
            RehearsalManagedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker
                    managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker,
            RehearsalManagedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarker
                    managedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarker,
            ReleaseApprovalRehearsalResponseRecords.ExecutionBoundaries executionBoundaries
    ) {
        return !requestContext.approvalLedgerWritten()
                && ciEvidenceHint.noLedgerWriteProved()
                && artifactRetentionHint.javaRetentionFixtureReadOnly()
                && !artifactRetentionHint.ciArtifactUploadedByJava()
                && !artifactRetentionHint.githubArtifactAccessedByJava()
                && liveReadinessHint.readOnlyEndpointReady()
                && !liveReadinessHint.runtimeSmokeExecutedByJava()
                && !liveReadinessHint.javaStartedProcessForNode()
                && auditPersistenceHandoffHint.javaAuditSourceReadOnly()
                && !auditPersistenceHandoffHint.javaLedgerWriteAllowed()
                && !auditPersistenceHandoffHint.javaManagedAuditWriteAllowed()
                && !auditPersistenceHandoffHint.javaExternalAuditSystemAccessed()
                && approvalRecordHandoffHint.approvalRecordFixtureReadOnly()
                && !approvalRecordHandoffHint.javaApprovalDecisionCreated()
                && !approvalRecordHandoffHint.javaApprovalLedgerWritten()
                && !approvalRecordHandoffHint.javaApprovalRecordPersisted()
                && !approvalHandoffVerificationMarker.nodeV211RealApprovalDecisionCreated()
                && !approvalHandoffVerificationMarker.nodeV211RealApprovalLedgerWritten()
                && !approvalHandoffVerificationMarker.nodeV211ProductionAuditRecordAllowed()
                && !managedAuditAdapterBoundaryReceipt.nodeV215MayConnectManagedAudit()
                && !managedAuditAdapterBoundaryReceipt.nodeV215MayCreateApprovalDecision()
                && !managedAuditAdapterBoundaryReceipt.nodeV215MayWriteApprovalLedger()
                && !managedAuditAdapterBoundaryReceipt.nodeV215MayPersistApprovalRecord()
                && !managedAuditAdapterBoundaryReceipt.nodeV215MayExecuteSql()
                && !managedAuditAdapterBoundaryReceipt.nodeV215MayTriggerDeployment()
                && !managedAuditAdapterBoundaryReceipt.nodeV215MayTriggerRollback()
                && !managedAuditAdapterBoundaryReceipt.nodeV215MayExecuteRestore()
                && !managedAuditAdapterBoundaryReceipt.javaApprovalDecisionCreated()
                && !managedAuditAdapterBoundaryReceipt.javaApprovalLedgerWritten()
                && !managedAuditAdapterBoundaryReceipt.javaApprovalRecordPersisted()
                && !managedAuditAdapterBoundaryReceipt.javaManagedAuditWriteExecuted()
                && !managedAuditProductionAdapterPrerequisiteReceipt.javaCreatesApprovalDecision()
                && !managedAuditProductionAdapterPrerequisiteReceipt.javaWritesApprovalLedger()
                && !managedAuditProductionAdapterPrerequisiteReceipt.javaPersistsApprovalRecord()
                && !managedAuditProductionAdapterPrerequisiteReceipt.javaWritesManagedAuditStore()
                && !managedAuditProductionAdapterPrerequisiteReceipt.javaExecutesSql()
                && !managedAuditProductionAdapterPrerequisiteReceipt.javaTriggersDeployment()
                && !managedAuditProductionAdapterPrerequisiteReceipt.javaTriggersRollback()
                && !managedAuditProductionAdapterPrerequisiteReceipt.javaExecutesRestore()
                && !managedAuditProductionAdapterPrerequisiteReceipt.nodeV217MayConnectManagedAudit()
                && !managedAuditProductionAdapterPrerequisiteReceipt.nodeV217MayWriteApprovalLedger()
                && !managedAuditProductionAdapterPrerequisiteReceipt.nodeV217MayExecuteSql()
                && !managedAuditProductionAdapterPrerequisiteReceipt.nodeV217MayTriggerDeployment()
                && !managedAuditProductionAdapterPrerequisiteReceipt.nodeV217MayTriggerRollback()
                && !managedAuditProductionAdapterPrerequisiteReceipt.nodeV217MayExecuteRestore()
                && !opsEvidenceServiceQualitySplitReceipt.apiShapeChanged()
                && !opsEvidenceServiceQualitySplitReceipt.approvalDecisionCreated()
                && !opsEvidenceServiceQualitySplitReceipt.approvalLedgerWritten()
                && !opsEvidenceServiceQualitySplitReceipt.approvalRecordPersisted()
                && !opsEvidenceServiceQualitySplitReceipt.managedAuditStoreWritten()
                && !opsEvidenceServiceQualitySplitReceipt.sqlExecuted()
                && !opsEvidenceServiceQualitySplitReceipt.deploymentTriggered()
                && !opsEvidenceServiceQualitySplitReceipt.rollbackTriggered()
                && !opsEvidenceServiceQualitySplitReceipt.restoreExecuted()
                && !managedAuditAdapterImplementationGuardReceipt.nodeV220AppendWritten()
                && !managedAuditAdapterImplementationGuardReceipt.nodeV220QueryReturnedRecords()
                && !managedAuditAdapterImplementationGuardReceipt.nodeV220ExternalManagedAuditAccessed()
                && !managedAuditAdapterImplementationGuardReceipt.nodeV220LocalDryRunWritePerformed()
                && !managedAuditAdapterImplementationGuardReceipt.javaApprovalDecisionCreated()
                && !managedAuditAdapterImplementationGuardReceipt.javaApprovalLedgerWritten()
                && !managedAuditAdapterImplementationGuardReceipt.javaApprovalRecordPersisted()
                && !managedAuditAdapterImplementationGuardReceipt.javaManagedAuditStoreWritten()
                && !managedAuditAdapterImplementationGuardReceipt.javaSqlExecuted()
                && !managedAuditAdapterImplementationGuardReceipt.javaDeploymentTriggered()
                && !managedAuditAdapterImplementationGuardReceipt.javaRollbackTriggered()
                && !managedAuditAdapterImplementationGuardReceipt.javaRestoreExecuted()
                && !managedAuditExternalAdapterMigrationGuardReceipt.nodeV222SourceEndpointRerunPerformed()
                && !managedAuditExternalAdapterMigrationGuardReceipt.nodeV222AdditionalLocalDryRunWritePerformed()
                && !managedAuditExternalAdapterMigrationGuardReceipt.nodeV222ConnectsManagedAudit()
                && !managedAuditExternalAdapterMigrationGuardReceipt.credentialValueReadByJava()
                && !managedAuditExternalAdapterMigrationGuardReceipt.credentialValueStoredByJava()
                && !managedAuditExternalAdapterMigrationGuardReceipt.externalManagedAuditConnectionOpened()
                && !managedAuditExternalAdapterMigrationGuardReceipt.externalManagedAuditSchemaMigrated()
                && !managedAuditExternalAdapterMigrationGuardReceipt.javaApprovalDecisionCreated()
                && !managedAuditExternalAdapterMigrationGuardReceipt.javaApprovalLedgerWritten()
                && !managedAuditExternalAdapterMigrationGuardReceipt.javaApprovalRecordPersisted()
                && !managedAuditExternalAdapterMigrationGuardReceipt.javaManagedAuditStoreWritten()
                && !managedAuditExternalAdapterMigrationGuardReceipt.javaSqlExecuted()
                && !managedAuditExternalAdapterMigrationGuardReceipt.javaDeploymentTriggered()
                && !managedAuditExternalAdapterMigrationGuardReceipt.javaRollbackTriggered()
                && !managedAuditExternalAdapterMigrationGuardReceipt.javaRestoreExecuted()
                && sandboxAdapterApprovalSchemaGuardReceiptBuilder.noWriteCredentialConnectionOrSchemaEffectProved(
                        managedAuditSandboxAdapterApprovalSchemaGuardReceipt
                )
                && sandboxConnectionOperatorHandoffMarkerBuilder
                .noWriteCredentialConnectionSchemaRollbackOrServiceStartProved(
                        managedAuditSandboxConnectionOperatorHandoffMarker
                )
                && sandboxConnectionPreflightEchoMarkerBuilder
                .noWriteCredentialConnectionSchemaRollbackOrServiceStartProved(
                        managedAuditSandboxConnectionPreflightEchoMarker
                )
                && sandboxConnectionPreconditionReceiptBuilder
                .noWriteCredentialConnectionSchemaRollbackOrServiceStartProved(
                        managedAuditSandboxConnectionPreconditionReceipt
                )
                && sandboxConnectionDryRunEnvelopeEchoReceiptBuilder
                .noWriteCredentialConnectionSchemaRollbackOrServiceStartProved(
                        managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt
                )
                && sandboxConnectionOperatorWindowChecklistEchoReceiptBuilder
                .noWriteCredentialConnectionSchemaRollbackOrServiceStartProved(
                        managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt
                )
                && sandboxConnectionDryRunCommandPackageEchoReceiptBuilder
                .noWriteCredentialConnectionSchemaRollbackOrServiceStartProved(
                        managedAuditSandboxConnectionDryRunCommandPackageEchoReceipt
                )
                && sandboxConnectionPrecheckPacketEchoReceiptBuilder
                .noWriteCredentialConnectionSchemaRollbackOrServiceStartProved(
                        managedAuditSandboxConnectionPrecheckPacketEchoReceipt
                )
                && sandboxConnectionDisabledAdapterClientPrecheckEchoReceiptBuilder
                .noWriteCredentialConnectionSchemaRollbackOrServiceStartProved(
                        managedAuditSandboxConnectionDisabledAdapterClientPrecheckEchoReceipt
                )
                && sandboxConnectionFakeTransportDryRunPacketEchoMarkerBuilder
                .noCredentialConnectionWriteOrAutoStartProved(
                        managedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker
                )
                && sandboxEndpointHandlePreflightEchoMarkerBuilder
                .noCredentialConnectionWriteOrAutoStartProved(
                        managedAuditSandboxEndpointHandlePreflightEchoMarker
                )
                && sandboxEndpointCredentialResolverDecisionEchoMarkerBuilder
                .noCredentialConnectionWriteOrAutoStartProved(
                        managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker
                )
                && sandboxEndpointCredentialResolverDisabledPrecheckEchoMarkerBuilder
                .noCredentialConnectionWriteOrAutoStartProved(
                        managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker
                )
                && sandboxEndpointCredentialResolverTestOnlyShellEchoMarkerBuilder
                .noCredentialConnectionWriteOrAutoStartProved(
                        managedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarker
                )
                && !executionBoundaries.nodeMayCreateApprovalDecision()
                && !executionBoundaries.nodeMayWriteApprovalLedger();
    }

    private List<String> schemaFields() {
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

    private List<String> nodeVerificationActions() {
        List<String> actions = new ArrayList<>(List.of(
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
        ));
        verificationContributions.forEach(contribution ->
                actions.addAll(contribution.nodeVerificationActionValues()));
        actions.addAll(List.of(
                "Compare warningDigest across closed-window and operator-window reads",
                "Require noLedgerWriteProved=true before treating the response as read-only evidence",
                "Keep UPSTREAM_ACTIONS_ENABLED=false"
        ));
        return actions;
    }

    private static VerificationContribution contribution(
            Supplier<List<String>> warningDigestWarningInputNames,
            Supplier<List<String>> warningDigestBoundaryInputNames,
            Supplier<List<String>> proofClaims,
            Supplier<List<String>> nodeVerificationActions
    ) {
        return new VerificationContribution(
                warningDigestWarningInputNames,
                warningDigestBoundaryInputNames,
                proofClaims,
                nodeVerificationActions
        );
    }

    private record VerificationContribution(
            Supplier<List<String>> warningDigestWarningInputNames,
            Supplier<List<String>> warningDigestBoundaryInputNames,
            Supplier<List<String>> proofClaims,
            Supplier<List<String>> nodeVerificationActions
    ) {
        List<String> warningDigestWarningInputValues() {
            return warningDigestWarningInputNames.get();
        }

        List<String> warningDigestBoundaryInputValues() {
            return warningDigestBoundaryInputNames.get();
        }

        List<String> proofClaimValues() {
            return proofClaims.get();
        }

        List<String> nodeVerificationActionValues() {
            return nodeVerificationActions.get();
        }
    }
}
