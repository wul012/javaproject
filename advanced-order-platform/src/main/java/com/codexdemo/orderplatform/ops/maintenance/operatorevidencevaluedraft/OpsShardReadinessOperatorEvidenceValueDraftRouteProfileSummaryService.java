package com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluedraft;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceValueDraftRouteProfileSummaryService {

  public static final String ENDPOINT =
      OpsShardReadinessOperatorEvidenceValueDraftRoutePaths.BASE_PATH
          + OpsShardReadinessOperatorEvidenceValueDraftRoutePaths
              .OPERATOR_EVIDENCE_VALUE_DRAFT_ROUTE_PROFILE_SUMMARY;
  static final String PROFILE =
      "java-shard-readiness-operator-evidence-value-draft-route-profile-summary.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessOperatorEvidenceValueDraftResponse summary() {
    return OpsShardReadinessOperatorEvidenceValueDraftSupport.response(
        "Java v626",
        ENDPOINT,
        PROFILE,
        OpsShardReadinessOperatorEvidenceValueDraftSlotCatalog.slots(0, 4),
        List.of(
            "value-draft-route-profile-foundation-routes-6",
            "value-draft-route-profile-assurance-routes-6",
            "value-draft-route-profile-get-only"));
  }
}
