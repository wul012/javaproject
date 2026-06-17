package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.approvalpreflight.OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightArchivePlanService;
import com.codexdemo.orderplatform.ops.maintenance.approvalpreflight.OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightCatalogService;
import com.codexdemo.orderplatform.ops.maintenance.approvalpreflight.OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightCleanupReceiptService;
import com.codexdemo.orderplatform.ops.maintenance.approvalpreflight.OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightCloseoutService;
import com.codexdemo.orderplatform.ops.maintenance.approvalpreflight.OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightDigestBlueprintService;
import com.codexdemo.orderplatform.ops.maintenance.approvalpreflight.OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightIdentitySignatureService;
import com.codexdemo.orderplatform.ops.maintenance.approvalpreflight.OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightImportFirewallService;
import com.codexdemo.orderplatform.ops.maintenance.approvalpreflight.OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightProvenanceBindingService;
import com.codexdemo.orderplatform.ops.maintenance.approvalpreflight.OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightRedactionDigestService;
import com.codexdemo.orderplatform.ops.maintenance.approvalpreflight.OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightRoutePaths;
import com.codexdemo.orderplatform.ops.maintenance.approvalpreflight.OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightTimestampWindowService;
import com.codexdemo.orderplatform.ops.maintenance.approvalpreflight.OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightValueRejectionService;
import com.codexdemo.orderplatform.ops.maintenance.approvalpreflight.OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightZeroValueLedgerService;
import java.util.Map;
import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightRoutePathsTests {

  @Test
  void approvalPreflightEndpointsUseSharedRouteConstants() {
    assertThat(
            Map.ofEntries(
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_APPROVAL_PREFLIGHT_CATALOG,
                    OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightCatalogService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_APPROVAL_PREFLIGHT_IDENTITY_SIGNATURE,
                    OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightIdentitySignatureService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_APPROVAL_PREFLIGHT_TIMESTAMP_WINDOW,
                    OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightTimestampWindowService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_APPROVAL_PREFLIGHT_REDACTION_DIGEST,
                    OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightRedactionDigestService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_APPROVAL_PREFLIGHT_PROVENANCE_BINDING,
                    OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightProvenanceBindingService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_APPROVAL_PREFLIGHT_VALUE_REJECTION,
                    OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightValueRejectionService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_APPROVAL_PREFLIGHT_ZERO_VALUE_LEDGER,
                    OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightZeroValueLedgerService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_APPROVAL_PREFLIGHT_CLEANUP_RECEIPT,
                    OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightCleanupReceiptService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_APPROVAL_PREFLIGHT_IMPORT_FIREWALL,
                    OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightImportFirewallService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_APPROVAL_PREFLIGHT_DIGEST_BLUEPRINT,
                    OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightDigestBlueprintService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_APPROVAL_PREFLIGHT_ARCHIVE_PLAN,
                    OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightArchivePlanService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_APPROVAL_PREFLIGHT_CLOSEOUT,
                    OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightCloseoutService
                        .ENDPOINT)))
        .allSatisfy(
            (route, endpoint) ->
                assertThat(endpoint).isEqualTo(OpsShardReadinessRoutePaths.BASE_PATH + route));
  }

  @Test
  void approvalPreflightRoutesDelegateToSplitOwner() {
    assertThat(
            OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_APPROVAL_PREFLIGHT_CATALOG)
        .isEqualTo(
            OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightRoutePaths
                .OPERATOR_EVIDENCE_VALUE_SUPPLY_APPROVAL_PREFLIGHT_CATALOG);
    assertThat(
            OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_APPROVAL_PREFLIGHT_CLOSEOUT)
        .isEqualTo(
            OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightRoutePaths
                .OPERATOR_EVIDENCE_VALUE_SUPPLY_APPROVAL_PREFLIGHT_CLOSEOUT);
  }
}
