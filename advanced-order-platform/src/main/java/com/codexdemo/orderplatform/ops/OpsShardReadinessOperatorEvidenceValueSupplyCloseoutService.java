package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceValueSupplyCloseoutService {

  public static final String ENDPOINT =
      OpsShardReadinessRoutePaths.BASE_PATH
          + OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_CLOSEOUT;
  static final String PROFILE = "java-shard-readiness-operator-evidence-value-supply-closeout.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessOperatorEvidenceValueSupplyResponse closeout() {
    return OpsShardReadinessOperatorEvidenceValueSupplySupport.response(
        "Java v658",
        ENDPOINT,
        PROFILE,
        OpsShardReadinessOperatorEvidenceValueSupplySlotCatalog.allSlots(),
        List.of(
            "value-supply-closeout-versions-v634-v658",
            "value-supply-closeout-slot-count-25",
            "value-supply-closeout-foundation-and-assurance-split",
            "value-supply-closeout-values-not-accepted",
            "value-supply-closeout-all-execution-locks-held"));
  }
}
