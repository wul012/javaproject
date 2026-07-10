package com.codexdemo.orderplatform.ops.maintenance.comparedevidenceevaluationpreflight;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessComparedEvidenceEvaluationPreflightIdentityDigestService {

  static final String ENDPOINT =
      OpsShardReadinessComparedEvidenceEvaluationPreflightRoutePaths.IDENTITY_DIGEST;
  static final String PROFILE =
      "java-shard-readiness-compared-evidence-evaluation-preflight-identity-digest.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessComparedEvidenceEvaluationPreflightResponse identityDigest() {
    return OpsShardReadinessComparedEvidenceEvaluationPreflightCatalogService.response(
        "Java v1052",
        ENDPOINT,
        PROFILE,
        OpsShardReadinessComparedEvidenceEvaluationPreflightIdentityDigestRuleCatalog
            .identityDigestRules(),
        OpsShardReadinessComparedEvidenceEvaluationPreflightGuardCatalog.identityDigestGuards(),
        List.of("compared-evidence-evaluation-preflight-identity-digest-rules"));
  }
}
