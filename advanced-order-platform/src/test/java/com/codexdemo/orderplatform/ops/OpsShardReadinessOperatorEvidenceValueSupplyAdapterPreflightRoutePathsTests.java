package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluesupplyadapterpreflight.OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightArchivePlanService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluesupplyadapterpreflight.OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightCatalogService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluesupplyadapterpreflight.OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightCloseoutService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluesupplyadapterpreflight.OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightCompatibilityMatrixService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluesupplyadapterpreflight.OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightDigestBlueprintService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluesupplyadapterpreflight.OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightMissingValueRejectionService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluesupplyadapterpreflight.OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightOperatorRehearsalChecklistService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluesupplyadapterpreflight.OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightPayloadFirewallService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluesupplyadapterpreflight.OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightProvenanceBindingService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluesupplyadapterpreflight.OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightRedactionBoundaryService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluesupplyadapterpreflight.OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightRuntimeSubmissionLockService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluesupplyadapterpreflight.OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightSourceEvidenceSnapshotService;
import java.util.Map;
import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightRoutePathsTests {

  @Test
  void adapterPreflightEndpointsUseSharedRouteConstants() {
    assertThat(
            Map.ofEntries(
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_ADAPTER_PREFLIGHT_CATALOG,
                    OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightCatalogService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_ADAPTER_PREFLIGHT_COMPATIBILITY_MATRIX,
                    OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightCompatibilityMatrixService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_ADAPTER_PREFLIGHT_REDACTION_BOUNDARY,
                    OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightRedactionBoundaryService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_ADAPTER_PREFLIGHT_PROVENANCE_BINDING,
                    OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightProvenanceBindingService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_ADAPTER_PREFLIGHT_MISSING_VALUE_REJECTION,
                    OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightMissingValueRejectionService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_ADAPTER_PREFLIGHT_SOURCE_EVIDENCE_SNAPSHOT,
                    OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightSourceEvidenceSnapshotService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_ADAPTER_PREFLIGHT_PAYLOAD_FIREWALL,
                    OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightPayloadFirewallService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_ADAPTER_PREFLIGHT_RUNTIME_SUBMISSION_LOCK,
                    OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightRuntimeSubmissionLockService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_ADAPTER_PREFLIGHT_OPERATOR_REHEARSAL_CHECKLIST,
                    OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightOperatorRehearsalChecklistService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_ADAPTER_PREFLIGHT_DIGEST_BLUEPRINT,
                    OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightDigestBlueprintService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_ADAPTER_PREFLIGHT_ARCHIVE_PLAN,
                    OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightArchivePlanService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_ADAPTER_PREFLIGHT_CLOSEOUT,
                    OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightCloseoutService
                        .ENDPOINT)))
        .allSatisfy(
            (route, endpoint) ->
                assertThat(endpoint).isEqualTo(OpsShardReadinessRoutePaths.BASE_PATH + route));
  }
}
