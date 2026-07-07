package com.codexdemo.orderplatform.ops.maintenance.comparedevidencecandidateblueprint;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessComparedEvidenceCandidateBlueprintComparisonService {

  public static final String ENDPOINT =
      OpsShardReadinessComparedEvidenceCandidateBlueprintRoutePaths.BASE_PATH
          + OpsShardReadinessComparedEvidenceCandidateBlueprintRoutePaths
              .COMPARED_EVIDENCE_CANDIDATE_BLUEPRINT_COMPARISON;
  static final String PROFILE =
      "java-shard-readiness-compared-evidence-candidate-blueprint-comparison.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessComparedEvidenceCandidateBlueprintResponse comparison() {
    return OpsShardReadinessComparedEvidenceCandidateBlueprintCatalogService.response(
        "Java v1062",
        ENDPOINT,
        PROFILE,
        OpsShardReadinessComparedEvidenceCandidateBlueprintComparisonSectionCatalog
            .comparisonSections(),
        OpsShardReadinessComparedEvidenceCandidateBlueprintBlockerCatalog.comparisonBlockers(),
        List.of("compared-evidence-candidate-blueprint-comparison-sections"));
  }
}
