package com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluedraft;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceValueDraftInstructionSetService {

  public static final String ENDPOINT =
      OpsShardReadinessOperatorEvidenceValueDraftRoutePaths.BASE_PATH
          + OpsShardReadinessOperatorEvidenceValueDraftRoutePaths
              .OPERATOR_EVIDENCE_VALUE_DRAFT_INSTRUCTION_SET;
  static final String PROFILE =
      "java-shard-readiness-operator-evidence-value-draft-instruction-set.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessOperatorEvidenceValueDraftResponse instructions() {
    return OpsShardReadinessOperatorEvidenceValueDraftSupport.response(
        "Java v616",
        ENDPOINT,
        PROFILE,
        OpsShardReadinessOperatorEvidenceValueDraftSlotCatalog.slots(8, 13),
        List.of(
            "value-draft-instruction-set-blocker-slice-9-13",
            "value-draft-instruction-set-operator-facing",
            "value-draft-instruction-set-no-submitted-values"));
  }
}
