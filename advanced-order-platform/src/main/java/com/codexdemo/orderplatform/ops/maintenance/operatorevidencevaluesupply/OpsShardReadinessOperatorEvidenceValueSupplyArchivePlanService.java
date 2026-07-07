package com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluesupply;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceValueSupplyArchivePlanService {

  public static final String ENDPOINT =
      OpsShardReadinessOperatorEvidenceValueSupplyRoutePaths.BASE_PATH
          + OpsShardReadinessOperatorEvidenceValueSupplyRoutePaths
              .OPERATOR_EVIDENCE_VALUE_SUPPLY_ARCHIVE_PLAN;
  static final String PROFILE =
      "java-shard-readiness-operator-evidence-value-supply-archive-plan.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessOperatorEvidenceValueSupplyResponse plan() {
    return OpsShardReadinessOperatorEvidenceValueSupplySlotCatalog.response(
        "Java v656",
        ENDPOINT,
        PROFILE,
        OpsShardReadinessOperatorEvidenceValueSupplySlotCatalog.slots(20, 25),
        List.of(
            "value-supply-archive-plan-external-capture",
            "value-supply-archive-plan-no-file-write",
            "value-supply-archive-plan-no-runtime-process",
            "value-supply-archive-plan-lock-summary-required"));
  }
}
