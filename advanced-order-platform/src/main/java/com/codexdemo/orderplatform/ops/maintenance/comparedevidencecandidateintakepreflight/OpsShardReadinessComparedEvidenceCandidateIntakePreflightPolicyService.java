package com.codexdemo.orderplatform.ops.maintenance.comparedevidencecandidateintakepreflight;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessComparedEvidenceCandidateIntakePreflightPolicyService {

  public static final String ENDPOINT =
      OpsShardReadinessComparedEvidenceCandidateIntakePreflightRoutePaths.BASE_PATH
          + OpsShardReadinessComparedEvidenceCandidateIntakePreflightRoutePaths
              .COMPARED_EVIDENCE_CANDIDATE_INTAKE_PREFLIGHT_POLICY;
  static final String PROFILE =
      "java-shard-readiness-compared-evidence-candidate-intake-preflight-policy.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessComparedEvidenceCandidateIntakePreflightResponse policy() {
    return OpsShardReadinessComparedEvidenceCandidateIntakePreflightCatalogService.response(
        "Java v1078",
        ENDPOINT,
        PROFILE,
        OpsShardReadinessComparedEvidenceCandidateIntakePreflightPolicySlotCatalog.policySlots(),
        OpsShardReadinessComparedEvidenceCandidateIntakePreflightGuardCatalog.policyGuards(),
        OpsShardReadinessComparedEvidenceCandidateIntakePreflightGuardCatalog.allGates(),
        List.of("compared-evidence-candidate-intake-preflight-policy-slots"));
  }
}
