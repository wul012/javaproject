package com.codexdemo.orderplatform.ops.maintenance.comparedevidencecandidateintakepreflight;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessComparedEvidenceCandidateIntakePreflightCloseoutService {

  public static final String ENDPOINT =
      OpsShardReadinessComparedEvidenceCandidateIntakePreflightRoutePaths.BASE_PATH
          + OpsShardReadinessComparedEvidenceCandidateIntakePreflightRoutePaths
              .COMPARED_EVIDENCE_CANDIDATE_INTAKE_PREFLIGHT_CLOSEOUT;
  static final String PROFILE =
      "java-shard-readiness-compared-evidence-candidate-intake-preflight-closeout.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessComparedEvidenceCandidateIntakePreflightResponse closeout() {
    return OpsShardReadinessComparedEvidenceCandidateIntakePreflightCatalogService.response(
        "Java v1079",
        ENDPOINT,
        PROFILE,
        OpsShardReadinessComparedEvidenceCandidateIntakePreflightCloseoutSlotCatalog
            .closeoutSlots(),
        OpsShardReadinessComparedEvidenceCandidateIntakePreflightGuardCatalog.closeoutGuards(),
        OpsShardReadinessComparedEvidenceCandidateIntakePreflightGuardCatalog.allGates(),
        List.of("compared-evidence-candidate-intake-preflight-closeout-slots"));
  }
}
