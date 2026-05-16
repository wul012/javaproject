package com.codexdemo.orderplatform.ops;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HexFormat;
import java.util.List;

final class ReleaseApprovalManagedAuditExternalAdapterMigrationGuardReceiptBuilder {

    private static final String RECEIPT_VERSION =
            "java-release-approval-rehearsal-managed-audit-external-adapter-migration-guard-receipt.v1";

    private static final String SOURCE_SCHEMA_VERSION =
            "java-release-approval-rehearsal-response-schema.v14";

    private static final String NODE_V222_VERSION = "Node v222";

    private static final String NODE_V222_PROFILE =
            "managed-audit-local-adapter-candidate-verification-report.v1";

    private static final String NODE_V222_ENDPOINT =
            "/api/v1/audit/managed-audit-local-adapter-candidate-verification-report";

    private static final String NODE_V222_STATE =
            "local-adapter-candidate-verification-ready";

    private static final String NODE_V223_VERSION = "Node v223";

    private static final String NODE_V223_PROFILE =
            "managed-audit-external-adapter-connection-readiness-review.v1";

    ReleaseApprovalRehearsalResponse.RehearsalManagedAuditExternalAdapterMigrationGuardReceipt
            build(
                    ReleaseApprovalRehearsalResponse.RehearsalManagedAuditAdapterImplementationGuardReceipt
                            implementationGuardReceipt
    ) {
        boolean sourceReceiptAccepted =
                OpsEvidenceService
                        .RELEASE_APPROVAL_REHEARSAL_MANAGED_AUDIT_ADAPTER_IMPLEMENTATION_GUARD_RECEIPT_VERSION
                        .equals(implementationGuardReceipt.receiptVersion())
                        && implementationGuardReceipt.readyForNodeV221LocalAdapterCandidateDryRun()
                        && !implementationGuardReceipt.nodeV220AppendWritten()
                        && !implementationGuardReceipt.nodeV220QueryReturnedRecords()
                        && !implementationGuardReceipt.nodeV220ExternalManagedAuditAccessed()
                        && !implementationGuardReceipt.nodeV220LocalDryRunWritePerformed()
                        && !implementationGuardReceipt.javaApprovalDecisionCreated()
                        && !implementationGuardReceipt.javaApprovalLedgerWritten()
                        && !implementationGuardReceipt.javaApprovalRecordPersisted()
                        && !implementationGuardReceipt.javaManagedAuditStoreWritten()
                        && !implementationGuardReceipt.javaSqlExecuted()
                        && !implementationGuardReceipt.javaDeploymentTriggered()
                        && !implementationGuardReceipt.javaRollbackTriggered()
                        && !implementationGuardReceipt.javaRestoreExecuted()
                        && !implementationGuardReceipt.nodeMayTreatAsProductionAuditRecord();
        boolean nodeV222VerificationReportReady = true;
        boolean nodeV222ReadOnlyReport = true;
        boolean nodeV222SourceEndpointRerunPerformed = false;
        boolean nodeV222AdditionalLocalDryRunWritePerformed = false;
        boolean nodeV222ConnectsManagedAudit = false;
        boolean nodeV222ReadyForProductionAudit = false;
        boolean ownerApprovalRequiredBeforeConnection = true;
        boolean schemaMigrationReviewRequired = true;
        boolean credentialReviewRequired = true;
        boolean credentialValueReadByJava = false;
        boolean credentialValueStoredByJava = false;
        boolean externalManagedAuditConnectionOpened = false;
        boolean externalManagedAuditSchemaMigrated = false;
        boolean javaApprovalDecisionCreated = false;
        boolean javaApprovalLedgerWritten = false;
        boolean javaApprovalRecordPersisted = false;
        boolean javaManagedAuditStoreWritten = false;
        boolean javaSqlExecuted = false;
        boolean javaDeploymentTriggered = false;
        boolean javaRollbackTriggered = false;
        boolean javaRestoreExecuted = false;

        List<String> guardWarnings = new ArrayList<>();
        if (!sourceReceiptAccepted) {
            guardWarnings.add("NODE_V223_SOURCE_IMPLEMENTATION_GUARD_RECEIPT_NOT_READY");
        }
        boolean readyForNodeV223ExternalAdapterConnectionReadinessReview =
                sourceReceiptAccepted
                        && nodeV222VerificationReportReady
                        && nodeV222ReadOnlyReport
                        && !nodeV222SourceEndpointRerunPerformed
                        && !nodeV222AdditionalLocalDryRunWritePerformed
                        && !nodeV222ConnectsManagedAudit
                        && !nodeV222ReadyForProductionAudit
                        && ownerApprovalRequiredBeforeConnection
                        && schemaMigrationReviewRequired
                        && credentialReviewRequired
                        && !credentialValueReadByJava
                        && !credentialValueStoredByJava
                        && !externalManagedAuditConnectionOpened
                        && !externalManagedAuditSchemaMigrated
                        && !javaApprovalDecisionCreated
                        && !javaApprovalLedgerWritten
                        && !javaApprovalRecordPersisted
                        && !javaManagedAuditStoreWritten
                        && !javaSqlExecuted
                        && !javaDeploymentTriggered
                        && !javaRollbackTriggered
                        && !javaRestoreExecuted;
        String guardDigest = digest(List.of(
                line("receiptVersion", RECEIPT_VERSION),
                line("sourceImplementationGuardReceiptVersion", implementationGuardReceipt.receiptVersion()),
                line("sourceImplementationGuardSchemaVersion", SOURCE_SCHEMA_VERSION),
                line("consumedByNodeVerificationReportVersion", NODE_V222_VERSION),
                line("consumedByNodeVerificationReportProfile", NODE_V222_PROFILE),
                line("consumedByNodeVerificationReportState", NODE_V222_STATE),
                line("nodeV222ReadOnlyReport", nodeV222ReadOnlyReport),
                line("nodeV222SourceEndpointRerunPerformed", nodeV222SourceEndpointRerunPerformed),
                line("nodeV222AdditionalLocalDryRunWritePerformed", nodeV222AdditionalLocalDryRunWritePerformed),
                line("nodeV222ConnectsManagedAudit", nodeV222ConnectsManagedAudit),
                line("ownerApprovalRequiredBeforeConnection", ownerApprovalRequiredBeforeConnection),
                line("schemaMigrationReviewRequired", schemaMigrationReviewRequired),
                line("credentialReviewRequired", credentialReviewRequired),
                line("credentialValueReadByJava", credentialValueReadByJava),
                line("externalManagedAuditConnectionOpened", externalManagedAuditConnectionOpened),
                line("externalManagedAuditSchemaMigrated", externalManagedAuditSchemaMigrated),
                line("javaApprovalLedgerWritten", javaApprovalLedgerWritten),
                line("javaManagedAuditStoreWritten", javaManagedAuditStoreWritten),
                line("javaSqlExecuted", javaSqlExecuted),
                line("readyForNodeV223ExternalAdapterConnectionReadinessReview",
                        readyForNodeV223ExternalAdapterConnectionReadinessReview)
        ));

        return new ReleaseApprovalRehearsalResponse.RehearsalManagedAuditExternalAdapterMigrationGuardReceipt(
                RECEIPT_VERSION,
                implementationGuardReceipt.receiptVersion(),
                SOURCE_SCHEMA_VERSION,
                NODE_V222_VERSION,
                NODE_V222_PROFILE,
                NODE_V222_ENDPOINT,
                NODE_V222_STATE,
                NODE_V223_VERSION,
                NODE_V223_PROFILE,
                true,
                nodeV222VerificationReportReady,
                nodeV222ReadOnlyReport,
                nodeV222SourceEndpointRerunPerformed,
                nodeV222AdditionalLocalDryRunWritePerformed,
                nodeV222ConnectsManagedAudit,
                nodeV222ReadyForProductionAudit,
                ownerApprovalRequiredBeforeConnection,
                schemaMigrationReviewRequired,
                credentialReviewRequired,
                credentialValueReadByJava,
                credentialValueStoredByJava,
                externalManagedAuditConnectionOpened,
                externalManagedAuditSchemaMigrated,
                javaApprovalDecisionCreated,
                javaApprovalLedgerWritten,
                javaApprovalRecordPersisted,
                javaManagedAuditStoreWritten,
                javaSqlExecuted,
                javaDeploymentTriggered,
                javaRollbackTriggered,
                javaRestoreExecuted,
                readyForNodeV223ExternalAdapterConnectionReadinessReview,
                false,
                false,
                false,
                guardDigest,
                List.of(
                        "external managed audit owner approval",
                        "external managed audit schema migration review",
                        "external managed audit credential review",
                        "external managed audit rollback and cleanup review",
                        "Node v223 readiness review must remain non-connecting"
                ),
                List.of(
                        "Java v81 records credential source requirements only",
                        "Java v81 must not read credential values",
                        "Java v81 must not store credential values",
                        "Java v81 must not open an external managed audit connection",
                        "Node v223 must not read production secrets during readiness review"
                ),
                List.of(
                        "Open external managed audit connection during Java v81 migration guard",
                        "Read or persist external managed audit credential values during Java v81 migration guard",
                        "Execute schema migration SQL during Java v81 migration guard",
                        "Write approval ledger during Java v81 migration guard",
                        "Write managed audit store during Java v81 migration guard",
                        "Treat Node v221 local JSONL dry-run records as production audit records"
                ),
                List.of(
                        "Node v222 verification report must be ready and read-only",
                        "Java v81 external adapter migration guard receipt must be ready",
                        "mini-kv v90 external adapter non-participation receipt must be present before Node v223",
                        "Node v223 must perform readiness review only and avoid production credential reads",
                        "UPSTREAM_ACTIONS_ENABLED must remain false"
                ),
                List.copyOf(guardWarnings),
                List.of(
                        "Compare managedAuditExternalAdapterMigrationGuardReceipt.consumedByNodeVerificationReportProfile with Node v222",
                        "Require managedAuditExternalAdapterMigrationGuardReceipt.readyForNodeV223ExternalAdapterConnectionReadinessReview=true before Node v223",
                        "Keep managedAuditExternalAdapterMigrationGuardReceipt.credentialValueReadByJava=false",
                        "Keep managedAuditExternalAdapterMigrationGuardReceipt.externalManagedAuditConnectionOpened=false",
                        "Keep managedAuditExternalAdapterMigrationGuardReceipt.javaSqlExecuted=false"
                )
        );
    }

    private static String digest(List<String> lines) {
        String canonical = String.join("\n", lines) + "\n";
        try {
            byte[] bytes = MessageDigest.getInstance("SHA-256")
                    .digest(canonical.getBytes(StandardCharsets.UTF_8));
            return "sha256:" + HexFormat.of().formatHex(bytes);
        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalStateException("SHA-256 digest algorithm is not available", ex);
        }
    }

    private static String line(String key, Object value) {
        return key + "=" + value(value);
    }

    private static String value(Object value) {
        if (value == null) {
            return "<null>";
        }
        if (value instanceof List<?> list) {
            return "[" + String.join(",", list.stream().map(ReleaseApprovalManagedAuditExternalAdapterMigrationGuardReceiptBuilder::value).toList()) + "]";
        }
        return String.valueOf(value);
    }
}
