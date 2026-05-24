package com.codexdemo.orderplatform.ops;

import java.util.ArrayList;
import java.util.List;

final class ReleaseApprovalManagedAuditSandboxConnectionOperatorHandoffMarkerBuilder {

    private static final List<String> WARNING_DIGEST_WARNING_INPUT_NAMES = List.of(
            "managedAuditSandboxConnectionOperatorHandoffMarkerWarnings"
    );

    private static final List<String> WARNING_DIGEST_BOUNDARY_INPUT_NAMES = List.of(
            "sandboxConnectionOperatorHandoffMarkerDigest",
            "sandboxConnectionOperatorWindowOpenedByJava",
            "sandboxConnectionOwnerArtifactIdFieldRecognizedByJava",
            "sandboxConnectionCredentialValueReadByJava",
            "sandboxConnectionSchemaMigrationSqlExecutedByJava",
            "sandboxConnectionRollbackTriggeredByJava",
            "sandboxConnectionExternalManagedAuditConnectionOpenedByJava"
    );

    private static final List<String> PROOF_CLAIMS = List.of(
            "managedAuditSandboxConnectionOperatorHandoffMarker.sandboxConnectionWindowBoundary.manualSandboxConnectionWindowOpenedByJava=false",
            "managedAuditSandboxConnectionOperatorHandoffMarker.operatorPacketBoundary.ownerApprovalArtifactIdFieldRecognizedByJava=true",
            "managedAuditSandboxConnectionOperatorHandoffMarker.operatorPacketBoundary.schemaRehearsalIdFieldRecognizedByJava=true",
            "managedAuditSandboxConnectionOperatorHandoffMarker.credentialBoundary.credentialValueReadByJava=false",
            "managedAuditSandboxConnectionOperatorHandoffMarker.schemaRehearsalBoundary.schemaMigrationSqlExecutedByJava=false",
            "managedAuditSandboxConnectionOperatorHandoffMarker.rollbackPathBoundary.rollbackExecutionAllowedByJava=false",
            "managedAuditSandboxConnectionOperatorHandoffMarker.javaExecutionBoundary.externalManagedAuditConnectionOpenedByJava=false",
            "managedAuditSandboxConnectionOperatorHandoffMarker.javaExecutionBoundary.approvalLedgerWrittenByJava=false",
            "managedAuditSandboxConnectionOperatorHandoffMarker.javaExecutionBoundary.sqlExecutedByJava=false"
    );

    private static final List<String> NODE_VERIFICATION_ACTIONS = List.of(
            "Compare managedAuditSandboxConnectionOperatorHandoffMarker.consumedByNodeOperatorPacketProfile with Node v228",
            "Require managedAuditSandboxConnectionOperatorHandoffMarker.readyForNodeV229ManualSandboxConnectionPacketVerification=true before Node v229",
            "Keep managedAuditSandboxConnectionOperatorHandoffMarker.credentialBoundary.credentialValueReadByJava=false",
            "Keep managedAuditSandboxConnectionOperatorHandoffMarker.schemaRehearsalBoundary.schemaMigrationSqlExecutedByJava=false",
            "Keep managedAuditSandboxConnectionOperatorHandoffMarker.javaExecutionBoundary.externalManagedAuditConnectionOpenedByJava=false",
            "Keep managedAuditSandboxConnectionOperatorHandoffMarker.sandboxConnectionWindowBoundary.manualSandboxConnectionWindowOpenedByJava=false"
    );

    ReleaseApprovalRehearsalSandboxConnectionResponseRecords.RehearsalManagedAuditSandboxConnectionOperatorHandoffMarker build(
            ReleaseApprovalRehearsalResponseRecords.RehearsalManagedAuditSandboxAdapterApprovalSchemaGuardReceipt
                    sandboxAdapterApprovalSchemaGuardReceipt
    ) {
        boolean sourceReceiptAccepted =
                OpsEvidenceService
                        .RELEASE_APPROVAL_REHEARSAL_MANAGED_AUDIT_SANDBOX_ADAPTER_APPROVAL_SCHEMA_GUARD_RECEIPT_VERSION
                        .equals(sandboxAdapterApprovalSchemaGuardReceipt.receiptVersion())
                        && sandboxAdapterApprovalSchemaGuardReceipt.readyForNodeV225SandboxAdapterDryRunPackage()
                        && !sandboxAdapterApprovalSchemaGuardReceipt.readyForProductionAudit()
                        && !sandboxAdapterApprovalSchemaGuardReceipt.readyForProductionWindow()
                        && !sandboxAdapterApprovalSchemaGuardReceipt.nodeMayTreatAsProductionAuditRecord()
                        && !sandboxAdapterApprovalSchemaGuardReceipt.ownerApprovalBoundary().ownerApprovalArtifactProvidedByJava()
                        && !sandboxAdapterApprovalSchemaGuardReceipt.schemaRehearsalBoundary().schemaMigrationExecutionAllowed()
                        && !sandboxAdapterApprovalSchemaGuardReceipt.schemaRehearsalBoundary().schemaMigrationSqlExecutedByJava()
                        && !sandboxAdapterApprovalSchemaGuardReceipt.credentialBoundary().credentialValueReadByJava()
                        && !sandboxAdapterApprovalSchemaGuardReceipt.executionBoundary().externalManagedAuditConnectionOpened()
                        && !sandboxAdapterApprovalSchemaGuardReceipt.executionBoundary().javaManagedAuditStoreWritten()
                        && !sandboxAdapterApprovalSchemaGuardReceipt.executionBoundary().javaSqlExecuted();

        ReleaseApprovalRehearsalSandboxConnectionResponseRecords.RehearsalSandboxConnectionWindowBoundary sandboxConnectionWindowBoundary =
                sandboxConnectionWindowBoundary();
        ReleaseApprovalRehearsalSandboxConnectionResponseRecords.RehearsalSandboxConnectionOperatorPacketBoundary
                operatorPacketBoundary = operatorPacketBoundary();
        ReleaseApprovalRehearsalSandboxConnectionResponseRecords.RehearsalSandboxConnectionCredentialBoundary credentialBoundary =
                credentialBoundary();
        ReleaseApprovalRehearsalSandboxConnectionResponseRecords.RehearsalSandboxConnectionSchemaRehearsalBoundary schemaRehearsalBoundary =
                schemaRehearsalBoundary();
        ReleaseApprovalRehearsalSandboxConnectionResponseRecords.RehearsalSandboxConnectionRollbackPathBoundary rollbackPathBoundary =
                rollbackPathBoundary();
        ReleaseApprovalRehearsalSandboxConnectionResponseRecords.RehearsalSandboxConnectionJavaExecutionBoundary javaExecutionBoundary =
                javaExecutionBoundary();

        List<String> markerWarnings = new ArrayList<>();
        if (!sourceReceiptAccepted) {
            markerWarnings.add("NODE_V229_SOURCE_SANDBOX_ADAPTER_APPROVAL_SCHEMA_GUARD_RECEIPT_NOT_READY");
        }

        boolean readyForNodeV229ManualSandboxConnectionPacketVerification =
                sourceReceiptAccepted
                        && sandboxConnectionWindowBoundary.manualSandboxConnectionWindowRequired()
                        && !sandboxConnectionWindowBoundary.manualSandboxConnectionWindowOpenedByJava()
                        && !sandboxConnectionWindowBoundary.javaStartsManagedAuditService()
                        && !sandboxConnectionWindowBoundary.nodeAutoStartAllowed()
                        && !sandboxConnectionWindowBoundary.connectionExecutionAllowed()
                        && !sandboxConnectionWindowBoundary.readyForManagedAuditSandboxAdapterConnection()
                        && operatorPacketBoundary.operatorPacketReadOnly()
                        && operatorPacketBoundary.manualReviewRequired()
                        && operatorPacketBoundary.ownerApprovalArtifactIdFieldRecognizedByJava()
                        && operatorPacketBoundary.schemaRehearsalIdFieldRecognizedByJava()
                        && !operatorPacketBoundary.packetCreatesApprovalDecision()
                        && credentialBoundary.credentialHandleNameRecognizedByJava()
                        && !credentialBoundary.credentialValueRequiredByJava()
                        && !credentialBoundary.credentialValueReadByJava()
                        && !credentialBoundary.credentialValueStoredByJava()
                        && !credentialBoundary.productionCredentialAllowed()
                        && schemaRehearsalBoundary.schemaRehearsalIdRequired()
                        && !schemaRehearsalBoundary.schemaMigrationExecutionAllowed()
                        && !schemaRehearsalBoundary.schemaMigrationSqlExecutedByJava()
                        && !schemaRehearsalBoundary.schemaMigrationAppliedByJava()
                        && rollbackPathBoundary.rollbackPathIdRequired()
                        && rollbackPathBoundary.manualAbortMarkerRequired()
                        && !rollbackPathBoundary.rollbackExecutionAllowedByJava()
                        && !rollbackPathBoundary.restoreExecutionAllowedByJava()
                        && !javaExecutionBoundary.approvalDecisionCreatedByJava()
                        && !javaExecutionBoundary.approvalLedgerWrittenByJava()
                        && !javaExecutionBoundary.approvalRecordPersistedByJava()
                        && !javaExecutionBoundary.managedAuditStoreWrittenByJava()
                        && !javaExecutionBoundary.externalManagedAuditConnectionOpenedByJava()
                        && !javaExecutionBoundary.sqlExecutedByJava()
                        && !javaExecutionBoundary.deploymentTriggeredByJava()
                        && !javaExecutionBoundary.rollbackTriggeredByJava()
                        && !javaExecutionBoundary.restoreExecutedByJava();

        String markerDigest = ReleaseApprovalDigestSupport.digest(List.of(
                ReleaseApprovalDigestSupport.line(
                        "markerVersion",
                        OpsEvidenceService
                                .RELEASE_APPROVAL_REHEARSAL_MANAGED_AUDIT_SANDBOX_CONNECTION_OPERATOR_HANDOFF_MARKER_VERSION
                ),
                ReleaseApprovalDigestSupport.line(
                        "sourceSandboxAdapterApprovalSchemaGuardReceiptVersion",
                        sandboxAdapterApprovalSchemaGuardReceipt.receiptVersion()
                ),
                ReleaseApprovalDigestSupport.line(
                        "sourceSandboxAdapterApprovalSchemaGuardSchemaVersion",
                        OpsEvidenceService
                                .RELEASE_APPROVAL_REHEARSAL_MANAGED_AUDIT_SANDBOX_ADAPTER_APPROVAL_SCHEMA_GUARD_SCHEMA_VERSION
                ),
                ReleaseApprovalDigestSupport.line(
                        "consumedByNodeOperatorPacketProfile",
                        OpsEvidenceService.NODE_V228_MANUAL_SANDBOX_CONNECTION_OPERATOR_PACKET_PROFILE
                ),
                ReleaseApprovalDigestSupport.line(
                        "consumedByNodeOperatorPacketState",
                        OpsEvidenceService.NODE_V228_MANUAL_SANDBOX_CONNECTION_OPERATOR_PACKET_STATE
                ),
                ReleaseApprovalDigestSupport.line(
                        "ownerApprovalArtifactIdField",
                        operatorPacketBoundary.ownerApprovalArtifactIdField()
                ),
                ReleaseApprovalDigestSupport.line(
                        "schemaRehearsalIdField",
                        operatorPacketBoundary.schemaRehearsalIdField()
                ),
                ReleaseApprovalDigestSupport.line(
                        "credentialHandleNameField",
                        credentialBoundary.credentialHandleNameField()
                ),
                ReleaseApprovalDigestSupport.line(
                        "rollbackPathIdField",
                        rollbackPathBoundary.rollbackPathIdField()
                ),
                ReleaseApprovalDigestSupport.line(
                        "manualAbortMarkerField",
                        rollbackPathBoundary.manualAbortMarkerField()
                ),
                ReleaseApprovalDigestSupport.line("timeoutBudgetMs", rollbackPathBoundary.timeoutBudgetMs()),
                ReleaseApprovalDigestSupport.line(
                        "manualSandboxConnectionWindowOpenedByJava",
                        sandboxConnectionWindowBoundary.manualSandboxConnectionWindowOpenedByJava()
                ),
                ReleaseApprovalDigestSupport.line(
                        "credentialValueReadByJava",
                        credentialBoundary.credentialValueReadByJava()
                ),
                ReleaseApprovalDigestSupport.line(
                        "schemaMigrationSqlExecutedByJava",
                        schemaRehearsalBoundary.schemaMigrationSqlExecutedByJava()
                ),
                ReleaseApprovalDigestSupport.line(
                        "externalManagedAuditConnectionOpenedByJava",
                        javaExecutionBoundary.externalManagedAuditConnectionOpenedByJava()
                ),
                ReleaseApprovalDigestSupport.line(
                        "readyForNodeV229ManualSandboxConnectionPacketVerification",
                        readyForNodeV229ManualSandboxConnectionPacketVerification
                )
        ));

        return new ReleaseApprovalRehearsalSandboxConnectionResponseRecords.RehearsalManagedAuditSandboxConnectionOperatorHandoffMarker(
                        OpsEvidenceService
                                .RELEASE_APPROVAL_REHEARSAL_MANAGED_AUDIT_SANDBOX_CONNECTION_OPERATOR_HANDOFF_MARKER_VERSION,
                        sandboxAdapterApprovalSchemaGuardReceipt.receiptVersion(),
                        OpsEvidenceService
                                .RELEASE_APPROVAL_REHEARSAL_MANAGED_AUDIT_SANDBOX_ADAPTER_APPROVAL_SCHEMA_GUARD_SCHEMA_VERSION,
                        OpsEvidenceService.NODE_V227_MANUAL_SANDBOX_CONNECTION_EVIDENCE_CHECKLIST_VERSION,
                        OpsEvidenceService.NODE_V227_MANUAL_SANDBOX_CONNECTION_EVIDENCE_CHECKLIST_PROFILE,
                        OpsEvidenceService.NODE_V228_MANUAL_SANDBOX_CONNECTION_OPERATOR_PACKET_VERSION,
                        OpsEvidenceService.NODE_V228_MANUAL_SANDBOX_CONNECTION_OPERATOR_PACKET_PROFILE,
                        OpsEvidenceService.NODE_V228_MANUAL_SANDBOX_CONNECTION_OPERATOR_PACKET_ENDPOINT,
                        OpsEvidenceService.NODE_V228_MANUAL_SANDBOX_CONNECTION_OPERATOR_PACKET_STATE,
                        OpsEvidenceService.NODE_V229_MANUAL_SANDBOX_CONNECTION_PACKET_VERIFICATION_VERSION,
                        OpsEvidenceService.NODE_V229_MANUAL_SANDBOX_CONNECTION_PACKET_VERIFICATION_PROFILE,
                        true,
                        sandboxConnectionWindowBoundary,
                        operatorPacketBoundary,
                        credentialBoundary,
                        schemaRehearsalBoundary,
                        rollbackPathBoundary,
                        javaExecutionBoundary,
                        readyForNodeV229ManualSandboxConnectionPacketVerification,
                        false,
                        false,
                        false,
                        false,
                        markerDigest,
                        List.of(
                                "ORDEROPS_MANAGED_AUDIT_OWNER_APPROVAL_ARTIFACT_ID",
                                "ORDEROPS_MANAGED_AUDIT_SANDBOX_CREDENTIAL_HANDLE",
                                "ORDEROPS_MANAGED_AUDIT_SCHEMA_REHEARSAL_ID",
                                "ORDEROPS_MANAGED_AUDIT_ROLLBACK_PATH_ID",
                                "timeoutBudgetMs=15000",
                                "ORDEROPS_MANAGED_AUDIT_MANUAL_ABORT"
                        ),
                        List.of(
                                "Read or print a managed audit credential value during Java v87 marker",
                                "Open a managed audit sandbox connection during Java v87 marker",
                                "Execute schema migration SQL during Java v87 marker",
                                "Write approval ledger or managed audit state during Java v87 marker",
                                "Trigger deployment, rollback, or restore during Java v87 marker",
                                "Start Java, mini-kv, or external audit services automatically"
                        ),
                        List.of(
                                "Node v228 manual sandbox connection operator packet must be archived",
                                "Java v87 sandbox connection operator handoff marker must be ready",
                                "mini-kv v96 sandbox connection receipt echo marker must be ready",
                                "Node v229 must compare owner artifact, credential handle, schema rehearsal, rollback path, timeout, and abort marker",
                                "UPSTREAM_ACTIONS_ENABLED must remain false"
                        ),
                        List.copyOf(markerWarnings),
                        NODE_VERIFICATION_ACTIONS
                );
    }

    List<String> warningDigestWarningInputNames() {
        return WARNING_DIGEST_WARNING_INPUT_NAMES;
    }

    List<String> warningDigestBoundaryInputNames() {
        return WARNING_DIGEST_BOUNDARY_INPUT_NAMES;
    }

    List<String> proofClaims() {
        return PROOF_CLAIMS;
    }

    List<String> nodeVerificationActions() {
        return NODE_VERIFICATION_ACTIONS;
    }

    List<String> warningDigestWarningLines(
            ReleaseApprovalRehearsalSandboxConnectionResponseRecords.RehearsalManagedAuditSandboxConnectionOperatorHandoffMarker
                    marker
    ) {
        return List.of(
                ReleaseApprovalDigestSupport.line(
                        "managedAuditSandboxConnectionOperatorHandoffMarkerWarnings",
                        marker.markerWarnings()
                )
        );
    }

    List<String> warningDigestBoundaryLines(
            ReleaseApprovalRehearsalSandboxConnectionResponseRecords.RehearsalManagedAuditSandboxConnectionOperatorHandoffMarker
                    marker
    ) {
        return List.of(
                ReleaseApprovalDigestSupport.line("sandboxConnectionOperatorHandoffMarkerDigest", marker.markerDigest()),
                ReleaseApprovalDigestSupport.line(
                        "sandboxConnectionOperatorWindowOpenedByJava",
                        marker.sandboxConnectionWindowBoundary().manualSandboxConnectionWindowOpenedByJava()
                ),
                ReleaseApprovalDigestSupport.line(
                        "sandboxConnectionOwnerArtifactIdFieldRecognizedByJava",
                        marker.operatorPacketBoundary().ownerApprovalArtifactIdFieldRecognizedByJava()
                ),
                ReleaseApprovalDigestSupport.line(
                        "sandboxConnectionCredentialValueReadByJava",
                        marker.credentialBoundary().credentialValueReadByJava()
                ),
                ReleaseApprovalDigestSupport.line(
                        "sandboxConnectionSchemaMigrationSqlExecutedByJava",
                        marker.schemaRehearsalBoundary().schemaMigrationSqlExecutedByJava()
                ),
                ReleaseApprovalDigestSupport.line(
                        "sandboxConnectionRollbackTriggeredByJava",
                        marker.javaExecutionBoundary().rollbackTriggeredByJava()
                ),
                ReleaseApprovalDigestSupport.line(
                        "sandboxConnectionExternalManagedAuditConnectionOpenedByJava",
                        marker.javaExecutionBoundary().externalManagedAuditConnectionOpenedByJava()
                )
        );
    }

    boolean noWriteCredentialConnectionSchemaRollbackOrServiceStartProved(
            ReleaseApprovalRehearsalSandboxConnectionResponseRecords.RehearsalManagedAuditSandboxConnectionOperatorHandoffMarker
                    marker
    ) {
        return !marker.sandboxConnectionWindowBoundary().manualSandboxConnectionWindowOpenedByJava()
                && !marker.sandboxConnectionWindowBoundary().javaStartsManagedAuditService()
                && !marker.sandboxConnectionWindowBoundary().nodeAutoStartAllowed()
                && !marker.sandboxConnectionWindowBoundary().connectionExecutionAllowed()
                && !marker.operatorPacketBoundary().packetCreatesApprovalDecision()
                && !marker.credentialBoundary().credentialValueRequiredByJava()
                && !marker.credentialBoundary().credentialValueReadByJava()
                && !marker.credentialBoundary().credentialValueStoredByJava()
                && !marker.schemaRehearsalBoundary().schemaMigrationExecutionAllowed()
                && !marker.schemaRehearsalBoundary().schemaMigrationSqlExecutedByJava()
                && !marker.schemaRehearsalBoundary().schemaMigrationAppliedByJava()
                && !marker.rollbackPathBoundary().rollbackExecutionAllowedByJava()
                && !marker.rollbackPathBoundary().restoreExecutionAllowedByJava()
                && !marker.javaExecutionBoundary().approvalDecisionCreatedByJava()
                && !marker.javaExecutionBoundary().approvalLedgerWrittenByJava()
                && !marker.javaExecutionBoundary().approvalRecordPersistedByJava()
                && !marker.javaExecutionBoundary().managedAuditStoreWrittenByJava()
                && !marker.javaExecutionBoundary().externalManagedAuditConnectionOpenedByJava()
                && !marker.javaExecutionBoundary().sqlExecutedByJava()
                && !marker.javaExecutionBoundary().deploymentTriggeredByJava()
                && !marker.javaExecutionBoundary().rollbackTriggeredByJava()
                && !marker.javaExecutionBoundary().restoreExecutedByJava();
    }

    private static ReleaseApprovalRehearsalSandboxConnectionResponseRecords.RehearsalSandboxConnectionWindowBoundary
            sandboxConnectionWindowBoundary() {
        return new ReleaseApprovalRehearsalSandboxConnectionResponseRecords.RehearsalSandboxConnectionWindowBoundary(
                true,
                false,
                false,
                false,
                false,
                false
        );
    }

    private static ReleaseApprovalRehearsalSandboxConnectionResponseRecords.RehearsalSandboxConnectionOperatorPacketBoundary
            operatorPacketBoundary() {
        return new ReleaseApprovalRehearsalSandboxConnectionResponseRecords.RehearsalSandboxConnectionOperatorPacketBoundary(
                "ORDEROPS_MANAGED_AUDIT_OWNER_APPROVAL_ARTIFACT_ID",
                "ORDEROPS_MANAGED_AUDIT_SCHEMA_REHEARSAL_ID",
                "manual-sandbox-connection-operator-packet-only",
                true,
                true,
                true,
                true,
                false
        );
    }

    private static ReleaseApprovalRehearsalSandboxConnectionResponseRecords.RehearsalSandboxConnectionCredentialBoundary
            credentialBoundary() {
        return new ReleaseApprovalRehearsalSandboxConnectionResponseRecords.RehearsalSandboxConnectionCredentialBoundary(
                "ORDEROPS_MANAGED_AUDIT_SANDBOX_CREDENTIAL_HANDLE",
                true,
                false,
                false,
                false,
                false
        );
    }

    private static ReleaseApprovalRehearsalSandboxConnectionResponseRecords.RehearsalSandboxConnectionSchemaRehearsalBoundary
            schemaRehearsalBoundary() {
        return new ReleaseApprovalRehearsalSandboxConnectionResponseRecords.RehearsalSandboxConnectionSchemaRehearsalBoundary(
                "ORDEROPS_MANAGED_AUDIT_SCHEMA_REHEARSAL_ID",
                true,
                false,
                false,
                false
        );
    }

    private static ReleaseApprovalRehearsalSandboxConnectionResponseRecords.RehearsalSandboxConnectionRollbackPathBoundary
            rollbackPathBoundary() {
        return new ReleaseApprovalRehearsalSandboxConnectionResponseRecords.RehearsalSandboxConnectionRollbackPathBoundary(
                "ORDEROPS_MANAGED_AUDIT_ROLLBACK_PATH_ID",
                "ORDEROPS_MANAGED_AUDIT_MANUAL_ABORT",
                15000,
                true,
                true,
                false,
                false
        );
    }

    private static ReleaseApprovalRehearsalSandboxConnectionResponseRecords.RehearsalSandboxConnectionJavaExecutionBoundary
            javaExecutionBoundary() {
        return new ReleaseApprovalRehearsalSandboxConnectionResponseRecords.RehearsalSandboxConnectionJavaExecutionBoundary(
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false
        );
    }
}
