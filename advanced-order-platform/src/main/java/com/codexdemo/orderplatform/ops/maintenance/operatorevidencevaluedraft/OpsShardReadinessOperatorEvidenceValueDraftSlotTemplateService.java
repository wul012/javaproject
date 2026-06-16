package com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluedraft;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceValueDraftSlotTemplateService {

  public static final String ENDPOINT =
      OpsShardReadinessOperatorEvidenceValueDraftRoutePaths.BASE_PATH
          + OpsShardReadinessOperatorEvidenceValueDraftRoutePaths
              .OPERATOR_EVIDENCE_VALUE_DRAFT_SLOT_TEMPLATE;
  static final String PROFILE =
      "java-shard-readiness-operator-evidence-value-draft-slot-template.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessOperatorEvidenceValueDraftResponse template() {
    return OpsShardReadinessOperatorEvidenceValueDraftSupport.response(
        "Java v612",
        ENDPOINT,
        PROFILE,
        OpsShardReadinessOperatorEvidenceValueDraftSlotCatalog.slots(0, 4),
        List.of(
            "value-draft-slot-template-catalog-slice-1-4",
            "value-draft-slot-template-fields-present",
            "value-draft-slot-template-no-operator-values"));
  }
}
