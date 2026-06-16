package com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluedraft;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceValueDraftValueBoundaryService {

  public static final String ENDPOINT =
      OpsShardReadinessOperatorEvidenceValueDraftRoutePaths.BASE_PATH
          + OpsShardReadinessOperatorEvidenceValueDraftRoutePaths
              .OPERATOR_EVIDENCE_VALUE_DRAFT_VALUE_BOUNDARY;
  static final String PROFILE =
      "java-shard-readiness-operator-evidence-value-draft-value-boundary.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessOperatorEvidenceValueDraftResponse boundary() {
    return OpsShardReadinessOperatorEvidenceValueDraftSupport.response(
        "Java v614",
        ENDPOINT,
        PROFILE,
        OpsShardReadinessOperatorEvidenceValueDraftSlotCatalog.slots(4, 8),
        List.of(
            "value-draft-boundary-source-slice-5-8",
            "value-draft-boundary-actual-values-not-supplied",
            "value-draft-boundary-import-value-state-blocked"));
  }
}
