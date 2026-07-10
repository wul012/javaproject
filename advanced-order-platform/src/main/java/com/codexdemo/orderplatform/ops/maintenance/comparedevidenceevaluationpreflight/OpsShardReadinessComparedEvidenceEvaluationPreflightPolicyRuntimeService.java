package com.codexdemo.orderplatform.ops.maintenance.comparedevidenceevaluationpreflight;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessComparedEvidenceEvaluationPreflightPolicyRuntimeService {

  static final String ENDPOINT =
      OpsShardReadinessComparedEvidenceEvaluationPreflightRoutePaths.POLICY_RUNTIME;
  static final String PROFILE =
      "java-shard-readiness-compared-evidence-evaluation-preflight-policy-runtime.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessComparedEvidenceEvaluationPreflightResponse policyRuntime() {
    return OpsShardReadinessComparedEvidenceEvaluationPreflightCatalogService.response(
        "Java v1053",
        ENDPOINT,
        PROFILE,
        OpsShardReadinessComparedEvidenceEvaluationPreflightPolicyRuntimeRuleCatalog
            .policyRuntimeRules(),
        OpsShardReadinessComparedEvidenceEvaluationPreflightGuardCatalog.policyRuntimeGuards(),
        List.of("compared-evidence-evaluation-preflight-policy-runtime-rules"));
  }
}
