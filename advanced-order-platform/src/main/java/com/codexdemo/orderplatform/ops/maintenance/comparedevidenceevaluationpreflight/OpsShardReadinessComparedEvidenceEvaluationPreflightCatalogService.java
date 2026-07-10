package com.codexdemo.orderplatform.ops.maintenance.comparedevidenceevaluationpreflight;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessComparedEvidenceEvaluationPreflightCatalogService {

  static final String ENDPOINT =
      OpsShardReadinessComparedEvidenceEvaluationPreflightRoutePaths.CATALOG;
  static final String PROFILE =
      "java-shard-readiness-compared-evidence-evaluation-preflight-catalog.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessComparedEvidenceEvaluationPreflightResponse catalog() {
    return response(
        "Java v1050",
        ENDPOINT,
        PROFILE,
        OpsShardReadinessComparedEvidenceEvaluationPreflightRuleCatalog.allRules(),
        OpsShardReadinessComparedEvidenceEvaluationPreflightGuardCatalog.allGuards(),
        List.of("compared-evidence-evaluation-preflight-catalog-full"));
  }

  static OpsShardReadinessComparedEvidenceEvaluationPreflightResponse response(
      String version,
      String endpoint,
      String profile,
      List<OpsShardReadinessComparedEvidenceEvaluationPreflightResponse.EvaluationRule> rules,
      List<OpsShardReadinessComparedEvidenceEvaluationPreflightResponse.EvaluationGuard> guards,
      List<String> checks) {
    return OpsShardReadinessComparedEvidenceEvaluationPreflightSupport.response(
        version, endpoint, profile, rules, guards, checks);
  }
}
