package com.codexdemo.orderplatform.ops.maintenance.comparedevidencecandidateblueprint;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessComparedEvidenceCandidateBlueprintPolicyService {

  public static final String ENDPOINT =
      OpsShardReadinessComparedEvidenceCandidateBlueprintRoutePaths.BASE_PATH
          + OpsShardReadinessComparedEvidenceCandidateBlueprintRoutePaths
              .COMPARED_EVIDENCE_CANDIDATE_BLUEPRINT_POLICY;
  static final String PROFILE =
      "java-shard-readiness-compared-evidence-candidate-blueprint-policy.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessComparedEvidenceCandidateBlueprintResponse policy() {
    return OpsShardReadinessComparedEvidenceCandidateBlueprintCatalogService.response(
        "Java v1063",
        ENDPOINT,
        PROFILE,
        OpsShardReadinessComparedEvidenceCandidateBlueprintPolicySectionCatalog.policySections(),
        OpsShardReadinessComparedEvidenceCandidateBlueprintBlockerCatalog.policyBlockers(),
        List.of("compared-evidence-candidate-blueprint-policy-sections"));
  }
}
