package com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluesupply;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceValueSupplyValidationMatrixService {

  public static final String ENDPOINT =
      OpsShardReadinessOperatorEvidenceValueSupplyRoutePaths.BASE_PATH
          + OpsShardReadinessOperatorEvidenceValueSupplyRoutePaths
              .OPERATOR_EVIDENCE_VALUE_SUPPLY_VALIDATION_MATRIX;
  static final String PROFILE =
      "java-shard-readiness-operator-evidence-value-supply-validation-matrix.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessOperatorEvidenceValueSupplyResponse matrix() {
    return OpsShardReadinessOperatorEvidenceValueSupplySlotCatalog.response(
        "Java v648",
        ENDPOINT,
        PROFILE,
        OpsShardReadinessOperatorEvidenceValueSupplySlotCatalog.slots(20, 25),
        List.of(
            "value-supply-validation-matrix-slice-21-25",
            "value-supply-validation-operator-submission-locked",
            "value-supply-validation-import-preview-locked",
            "value-supply-validation-execution-locks-held"));
  }
}
