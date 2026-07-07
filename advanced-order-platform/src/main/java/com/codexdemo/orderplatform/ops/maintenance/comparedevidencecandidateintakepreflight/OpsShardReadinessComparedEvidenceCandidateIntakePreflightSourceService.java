package com.codexdemo.orderplatform.ops.maintenance.comparedevidencecandidateintakepreflight;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessComparedEvidenceCandidateIntakePreflightSourceService {

  public static final String ENDPOINT =
      OpsShardReadinessComparedEvidenceCandidateIntakePreflightRoutePaths.BASE_PATH
          + OpsShardReadinessComparedEvidenceCandidateIntakePreflightRoutePaths
              .COMPARED_EVIDENCE_CANDIDATE_INTAKE_PREFLIGHT_SOURCE;
  static final String PROFILE =
      "java-shard-readiness-compared-evidence-candidate-intake-preflight-source.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessComparedEvidenceCandidateIntakePreflightResponse source() {
    return OpsShardReadinessComparedEvidenceCandidateIntakePreflightCatalogService.response(
        "Java v1076",
        ENDPOINT,
        PROFILE,
        OpsShardReadinessComparedEvidenceCandidateIntakePreflightSourceSlotCatalog.sourceSlots(),
        OpsShardReadinessComparedEvidenceCandidateIntakePreflightGuardCatalog.sourceGuards(),
        OpsShardReadinessComparedEvidenceCandidateIntakePreflightGuardCatalog.allGates(),
        List.of("compared-evidence-candidate-intake-preflight-source-slots"));
  }
}
