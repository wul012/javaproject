package com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluedraft;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceValueDraftArchivePlanService {

  public static final String ENDPOINT =
      OpsShardReadinessOperatorEvidenceValueDraftRoutePaths.BASE_PATH
          + OpsShardReadinessOperatorEvidenceValueDraftRoutePaths
              .OPERATOR_EVIDENCE_VALUE_DRAFT_ARCHIVE_PLAN;
  static final String PROFILE =
      "java-shard-readiness-operator-evidence-value-draft-archive-plan.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessOperatorEvidenceValueDraftResponse plan() {
    return OpsShardReadinessOperatorEvidenceValueDraftSupport.response(
        "Java v628",
        ENDPOINT,
        PROFILE,
        OpsShardReadinessOperatorEvidenceValueDraftSlotCatalog.slots(0, 4),
        List.of(
            "value-draft-archive-plan-external-capture",
            "value-draft-archive-plan-no-file-write",
            "value-draft-archive-plan-no-runtime-process"));
  }
}
