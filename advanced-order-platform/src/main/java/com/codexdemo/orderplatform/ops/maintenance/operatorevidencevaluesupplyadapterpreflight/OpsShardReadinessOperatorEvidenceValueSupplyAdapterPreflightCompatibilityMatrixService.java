package com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluesupplyadapterpreflight;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public
class OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightCompatibilityMatrixService {

  public static final String ENDPOINT =
      OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightRoutePaths.BASE_PATH
          + OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightRoutePaths
              .OPERATOR_EVIDENCE_VALUE_SUPPLY_ADAPTER_PREFLIGHT_COMPATIBILITY_MATRIX;
  static final String PROFILE =
      "java-shard-readiness-operator-evidence-value-supply-adapter-preflight-compatibility-matrix.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse matrix() {
    return OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightSupport.response(
        "Java v664",
        ENDPOINT,
        PROFILE,
        OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightSlotCatalog.slots(0, 4),
        OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightSlotCatalog.rules(0, 4),
        List.of(
            "value-supply-adapter-preflight-compatibility-slice-1-4",
            "value-supply-adapter-preflight-compatibility-metadata-only",
            "value-supply-adapter-preflight-compatibility-no-value-body",
            "value-supply-adapter-preflight-compatibility-no-approval-capture"));
  }
}
