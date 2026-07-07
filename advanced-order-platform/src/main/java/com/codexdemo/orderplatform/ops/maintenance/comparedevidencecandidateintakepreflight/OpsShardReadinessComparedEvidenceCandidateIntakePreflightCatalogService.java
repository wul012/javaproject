package com.codexdemo.orderplatform.ops.maintenance.comparedevidencecandidateintakepreflight;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessComparedEvidenceCandidateIntakePreflightCatalogService {

  public static final String ENDPOINT =
      OpsShardReadinessComparedEvidenceCandidateIntakePreflightRoutePaths.BASE_PATH
          + OpsShardReadinessComparedEvidenceCandidateIntakePreflightRoutePaths
              .COMPARED_EVIDENCE_CANDIDATE_INTAKE_PREFLIGHT_CATALOG;
  static final String PROFILE =
      "java-shard-readiness-compared-evidence-candidate-intake-preflight-catalog.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessComparedEvidenceCandidateIntakePreflightResponse catalog() {
    return response(
        "Java v1075",
        ENDPOINT,
        PROFILE,
        OpsShardReadinessComparedEvidenceCandidateIntakePreflightSlotCatalog.allSlots(),
        OpsShardReadinessComparedEvidenceCandidateIntakePreflightGuardCatalog.allGuards(),
        OpsShardReadinessComparedEvidenceCandidateIntakePreflightGuardCatalog.allGates(),
        List.of("compared-evidence-candidate-intake-preflight-catalog-full"));
  }

  static OpsShardReadinessComparedEvidenceCandidateIntakePreflightResponse response(
      String version,
      String endpoint,
      String profile,
      List<OpsShardReadinessComparedEvidenceCandidateIntakePreflightResponse.IntakeSlot> slots,
      List<OpsShardReadinessComparedEvidenceCandidateIntakePreflightResponse.IntakeGuard> guards,
      List<String> gates,
      List<String> checks) {
    return OpsShardReadinessComparedEvidenceCandidateIntakePreflightSupport.response(
        version, endpoint, profile, slots, guards, gates, checks);
  }
}
