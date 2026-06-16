package com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluedraft;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceValueDraftOperatorHandoffService {

  public static final String ENDPOINT =
      OpsShardReadinessOperatorEvidenceValueDraftRoutePaths.BASE_PATH
          + OpsShardReadinessOperatorEvidenceValueDraftRoutePaths
              .OPERATOR_EVIDENCE_VALUE_DRAFT_OPERATOR_HANDOFF;
  static final String PROFILE =
      "java-shard-readiness-operator-evidence-value-draft-operator-handoff.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessOperatorEvidenceValueDraftResponse handoff() {
    return OpsShardReadinessOperatorEvidenceValueDraftSupport.response(
        "Java v630",
        ENDPOINT,
        PROFILE,
        OpsShardReadinessOperatorEvidenceValueDraftSlotCatalog.slots(8, 13),
        List.of(
            "value-draft-operator-handoff-owner-count-5",
            "value-draft-operator-handoff-no-values",
            "value-draft-operator-handoff-no-execution-approval"));
  }
}
