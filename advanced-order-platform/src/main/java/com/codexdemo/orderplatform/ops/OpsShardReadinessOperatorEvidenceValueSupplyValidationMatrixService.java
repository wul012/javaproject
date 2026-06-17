package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceValueSupplyValidationMatrixService {

  public static final String ENDPOINT =
      OpsShardReadinessRoutePaths.BASE_PATH
          + OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_VALIDATION_MATRIX;
  static final String PROFILE =
      "java-shard-readiness-operator-evidence-value-supply-validation-matrix.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessOperatorEvidenceValueSupplyResponse matrix() {
    return OpsShardReadinessOperatorEvidenceValueSupplySupport.response(
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
