package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightRoutePathsTests {

    @Test
    void approvalPreflightEndpointsUseSharedRouteConstants() {
        assertThat(Map.ofEntries(
                Map.entry(
                        OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_APPROVAL_PREFLIGHT_CATALOG,
                        OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightCatalogService.ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_APPROVAL_PREFLIGHT_IDENTITY_SIGNATURE,
                        OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightIdentitySignatureService.ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_APPROVAL_PREFLIGHT_TIMESTAMP_WINDOW,
                        OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightTimestampWindowService.ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_APPROVAL_PREFLIGHT_REDACTION_DIGEST,
                        OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightRedactionDigestService.ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_APPROVAL_PREFLIGHT_PROVENANCE_BINDING,
                        OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightProvenanceBindingService.ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_APPROVAL_PREFLIGHT_VALUE_REJECTION,
                        OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightValueRejectionService.ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_APPROVAL_PREFLIGHT_ZERO_VALUE_LEDGER,
                        OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightZeroValueLedgerService.ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_APPROVAL_PREFLIGHT_CLEANUP_RECEIPT,
                        OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightCleanupReceiptService.ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_APPROVAL_PREFLIGHT_IMPORT_FIREWALL,
                        OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightImportFirewallService.ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_APPROVAL_PREFLIGHT_DIGEST_BLUEPRINT,
                        OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightDigestBlueprintService.ENDPOINT
                )
        )).allSatisfy((route, endpoint) ->
                assertThat(endpoint).isEqualTo(OpsShardReadinessRoutePaths.BASE_PATH + route));
    }
}
