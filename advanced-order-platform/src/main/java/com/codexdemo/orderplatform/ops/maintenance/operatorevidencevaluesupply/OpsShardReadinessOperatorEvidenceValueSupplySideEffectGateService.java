package com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluesupply;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceValueSupplySideEffectGateService {

  public static final String ENDPOINT =
      OpsShardReadinessOperatorEvidenceValueSupplyRoutePaths.BASE_PATH
          + OpsShardReadinessOperatorEvidenceValueSupplyRoutePaths
              .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIDE_EFFECT_GATE;
  static final String PROFILE =
      "java-shard-readiness-operator-evidence-value-supply-side-effect-gate.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessOperatorEvidenceValueSupplyResponse gate() {
    return OpsShardReadinessOperatorEvidenceValueSupplySlotCatalog.response(
        "Java v650",
        ENDPOINT,
        PROFILE,
        OpsShardReadinessOperatorEvidenceValueSupplySlotCatalog.allSlots(),
        List.of(
            "value-supply-side-effect-gate-no-sibling-service-start",
            "value-supply-side-effect-gate-no-state-write",
            "value-supply-side-effect-gate-no-runtime-payload",
            "value-supply-side-effect-gate-no-production-path"));
  }
}
