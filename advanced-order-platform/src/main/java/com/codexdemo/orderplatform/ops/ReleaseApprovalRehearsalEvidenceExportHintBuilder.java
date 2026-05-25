package com.codexdemo.orderplatform.ops;

import java.util.List;

final class ReleaseApprovalRehearsalEvidenceExportHintBuilder {

    ReleaseApprovalRehearsalResponseRecords.RehearsalEvidenceExportHint build() {
        EvidenceExportHintFlags flags = EvidenceExportHintFlags.stableReadOnlyCurrentResponse();
        return new ReleaseApprovalRehearsalResponseRecords.RehearsalEvidenceExportHint(
                OpsEvidenceService.RELEASE_APPROVAL_REHEARSAL_EVIDENCE_EXPORT_HINT_VERSION,
                OpsEvidenceService.RELEASE_APPROVAL_REHEARSAL_EVIDENCE_EXPORT_HINT_SCHEMA_VERSION,
                OpsEvidenceService.RELEASE_APPROVAL_REHEARSAL_ENDPOINT,
                "/api/v1/ops/evidence",
                "release-approval-rehearsal-current.json",
                "stable-read-only-current-response",
                flags.readOnly(),
                flags.stableCurrentResponse(),
                flags.historicalFallbackAllowed(),
                flags.requiresCredentialValue(),
                flags.parsesRawEndpointUrl(),
                flags.executesNetworkRequest(),
                flags.writesLedgerOrSchema(),
                flags.startsUpstreamProcess(),
                List.of(
                        "responseSchemaVersion",
                        "verificationHint.warningDigest",
                        "verificationHint.noLedgerWriteProved",
                        "executionBoundaries.nodeMayWriteApprovalLedger",
                        "executionBoundaries.nodeMayTriggerRollback"
                ),
                List.of(
                        "credential_value_read",
                        "raw_endpoint_url_parse",
                        "http_or_tcp_execution",
                        "approval_ledger_write",
                        "schema_migration_sql_execution",
                        "deployment_or_rollback_execution",
                        "automatic_upstream_start"
                )
        );
    }

    private record EvidenceExportHintFlags(
            boolean readOnly,
            boolean stableCurrentResponse,
            boolean historicalFallbackAllowed,
            boolean requiresCredentialValue,
            boolean parsesRawEndpointUrl,
            boolean executesNetworkRequest,
            boolean writesLedgerOrSchema,
            boolean startsUpstreamProcess
    ) {

        static EvidenceExportHintFlags stableReadOnlyCurrentResponse() {
            return new EvidenceExportHintFlags(
                    true,
                    true,
                    true,
                    false,
                    false,
                    false,
                    false,
                    false
            );
        }
    }
}
