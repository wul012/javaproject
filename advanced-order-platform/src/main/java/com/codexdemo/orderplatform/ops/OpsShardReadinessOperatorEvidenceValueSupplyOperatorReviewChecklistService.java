package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceValueSupplyOperatorReviewChecklistService {

  public static final String ENDPOINT =
      OpsShardReadinessRoutePaths.BASE_PATH
          + OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_OPERATOR_REVIEW_CHECKLIST;
  static final String PROFILE =
      "java-shard-readiness-operator-evidence-value-supply-operator-review-checklist.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessOperatorEvidenceValueSupplyResponse checklist() {
    return OpsShardReadinessOperatorEvidenceValueSupplySupport.response(
        "Java v652",
        ENDPOINT,
        PROFILE,
        OpsShardReadinessOperatorEvidenceValueSupplySlotCatalog.slots(0, 4),
        List.of(
            "value-supply-operator-review-checklist-envelope-id",
            "value-supply-operator-review-checklist-source-draft-slot",
            "value-supply-operator-review-checklist-redaction-before-value",
            "value-supply-operator-review-checklist-no-approval-grant"));
  }
}
