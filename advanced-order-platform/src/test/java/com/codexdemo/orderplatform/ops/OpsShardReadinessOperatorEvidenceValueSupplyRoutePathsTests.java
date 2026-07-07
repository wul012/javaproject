package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluesupply.OpsShardReadinessOperatorEvidenceValueSupplyArchivePlanService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluesupply.OpsShardReadinessOperatorEvidenceValueSupplyCatalogService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluesupply.OpsShardReadinessOperatorEvidenceValueSupplyCloseoutService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluesupply.OpsShardReadinessOperatorEvidenceValueSupplyDigestBlueprintService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluesupply.OpsShardReadinessOperatorEvidenceValueSupplyEnvelopeTemplateService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluesupply.OpsShardReadinessOperatorEvidenceValueSupplyMissingValuePolicyService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluesupply.OpsShardReadinessOperatorEvidenceValueSupplyOperatorReviewChecklistService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluesupply.OpsShardReadinessOperatorEvidenceValueSupplyProvenanceRequirementService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluesupply.OpsShardReadinessOperatorEvidenceValueSupplyRedactionPolicyService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluesupply.OpsShardReadinessOperatorEvidenceValueSupplySideEffectGateService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluesupply.OpsShardReadinessOperatorEvidenceValueSupplySourceEvidenceGuardService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluesupply.OpsShardReadinessOperatorEvidenceValueSupplyValidationMatrixService;
import java.util.Map;
import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplyRoutePathsTests {

  @Test
  void operatorEvidenceValueSupplyEndpointsUseSharedRouteConstants() {
    assertThat(
            Map.ofEntries(
                Map.entry(
                    OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_CATALOG,
                    OpsShardReadinessOperatorEvidenceValueSupplyCatalogService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_ENVELOPE_TEMPLATE,
                    OpsShardReadinessOperatorEvidenceValueSupplyEnvelopeTemplateService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_REDACTION_POLICY,
                    OpsShardReadinessOperatorEvidenceValueSupplyRedactionPolicyService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_MISSING_VALUE_POLICY,
                    OpsShardReadinessOperatorEvidenceValueSupplyMissingValuePolicyService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_PROVENANCE_REQUIREMENT,
                    OpsShardReadinessOperatorEvidenceValueSupplyProvenanceRequirementService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SOURCE_EVIDENCE_GUARD,
                    OpsShardReadinessOperatorEvidenceValueSupplySourceEvidenceGuardService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_VALIDATION_MATRIX,
                    OpsShardReadinessOperatorEvidenceValueSupplyValidationMatrixService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_SIDE_EFFECT_GATE,
                    OpsShardReadinessOperatorEvidenceValueSupplySideEffectGateService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_OPERATOR_REVIEW_CHECKLIST,
                    OpsShardReadinessOperatorEvidenceValueSupplyOperatorReviewChecklistService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_DIGEST_BLUEPRINT,
                    OpsShardReadinessOperatorEvidenceValueSupplyDigestBlueprintService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_ARCHIVE_PLAN,
                    OpsShardReadinessOperatorEvidenceValueSupplyArchivePlanService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_CLOSEOUT,
                    OpsShardReadinessOperatorEvidenceValueSupplyCloseoutService.ENDPOINT)))
        .allSatisfy(
            (route, endpoint) ->
                assertThat(endpoint).isEqualTo(OpsShardReadinessRoutePaths.BASE_PATH + route));
  }
}
