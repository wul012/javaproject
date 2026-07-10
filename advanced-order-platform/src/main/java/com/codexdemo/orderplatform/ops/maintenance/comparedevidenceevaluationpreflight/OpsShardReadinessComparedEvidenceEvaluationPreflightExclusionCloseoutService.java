package com.codexdemo.orderplatform.ops.maintenance.comparedevidenceevaluationpreflight;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessComparedEvidenceEvaluationPreflightExclusionCloseoutService {

  static final String ENDPOINT =
      OpsShardReadinessComparedEvidenceEvaluationPreflightRoutePaths.EXCLUSION_CLOSEOUT;
  static final String PROFILE =
      "java-shard-readiness-compared-evidence-evaluation-preflight-exclusion-closeout.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessComparedEvidenceEvaluationPreflightResponse exclusionCloseout() {
    return OpsShardReadinessComparedEvidenceEvaluationPreflightCatalogService.response(
        "Java v1054",
        ENDPOINT,
        PROFILE,
        OpsShardReadinessComparedEvidenceEvaluationPreflightExclusionTraceRuleCatalog
            .exclusionTraceRules(),
        OpsShardReadinessComparedEvidenceEvaluationPreflightGuardCatalog.exclusionTraceGuards(),
        List.of("compared-evidence-evaluation-preflight-exclusion-closeout-rules"));
  }
}
