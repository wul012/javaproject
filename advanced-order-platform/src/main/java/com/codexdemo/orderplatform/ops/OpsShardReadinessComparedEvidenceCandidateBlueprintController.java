package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.comparedevidencecandidateblueprint.OpsShardReadinessComparedEvidenceCandidateBlueprintCatalogService;
import com.codexdemo.orderplatform.ops.maintenance.comparedevidencecandidateblueprint.OpsShardReadinessComparedEvidenceCandidateBlueprintCloseoutService;
import com.codexdemo.orderplatform.ops.maintenance.comparedevidencecandidateblueprint.OpsShardReadinessComparedEvidenceCandidateBlueprintComparisonService;
import com.codexdemo.orderplatform.ops.maintenance.comparedevidencecandidateblueprint.OpsShardReadinessComparedEvidenceCandidateBlueprintPolicyService;
import com.codexdemo.orderplatform.ops.maintenance.comparedevidencecandidateblueprint.OpsShardReadinessComparedEvidenceCandidateBlueprintResponse;
import com.codexdemo.orderplatform.ops.maintenance.comparedevidencecandidateblueprint.OpsShardReadinessComparedEvidenceCandidateBlueprintRoutePaths;
import com.codexdemo.orderplatform.ops.maintenance.comparedevidencecandidateblueprint.OpsShardReadinessComparedEvidenceCandidateBlueprintSourceService;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessService.BASE_PATH)
public class OpsShardReadinessComparedEvidenceCandidateBlueprintController {

  private final OpsShardReadinessComparedEvidenceCandidateBlueprintCatalogService catalogService;
  private final OpsShardReadinessComparedEvidenceCandidateBlueprintSourceService sourceService;
  private final OpsShardReadinessComparedEvidenceCandidateBlueprintComparisonService
      comparisonService;
  private final OpsShardReadinessComparedEvidenceCandidateBlueprintPolicyService policyService;
  private final OpsShardReadinessComparedEvidenceCandidateBlueprintCloseoutService closeoutService;

  public OpsShardReadinessComparedEvidenceCandidateBlueprintController(
      OpsShardReadinessComparedEvidenceCandidateBlueprintCatalogService catalogService,
      OpsShardReadinessComparedEvidenceCandidateBlueprintSourceService sourceService,
      OpsShardReadinessComparedEvidenceCandidateBlueprintComparisonService comparisonService,
      OpsShardReadinessComparedEvidenceCandidateBlueprintPolicyService policyService,
      OpsShardReadinessComparedEvidenceCandidateBlueprintCloseoutService closeoutService) {
    this.catalogService = catalogService;
    this.sourceService = sourceService;
    this.comparisonService = comparisonService;
    this.policyService = policyService;
    this.closeoutService = closeoutService;
  }

  @GetMapping(
      OpsShardReadinessComparedEvidenceCandidateBlueprintRoutePaths
          .COMPARED_EVIDENCE_CANDIDATE_BLUEPRINT_CATALOG)
  public OpsShardReadinessComparedEvidenceCandidateBlueprintResponse catalog() {
    return catalogService.catalog();
  }

  @GetMapping(
      OpsShardReadinessComparedEvidenceCandidateBlueprintRoutePaths
          .COMPARED_EVIDENCE_CANDIDATE_BLUEPRINT_SOURCE)
  public OpsShardReadinessComparedEvidenceCandidateBlueprintResponse source() {
    return sourceService.source();
  }

  @GetMapping(
      OpsShardReadinessComparedEvidenceCandidateBlueprintRoutePaths
          .COMPARED_EVIDENCE_CANDIDATE_BLUEPRINT_COMPARISON)
  public OpsShardReadinessComparedEvidenceCandidateBlueprintResponse comparison() {
    return comparisonService.comparison();
  }

  @GetMapping(
      OpsShardReadinessComparedEvidenceCandidateBlueprintRoutePaths
          .COMPARED_EVIDENCE_CANDIDATE_BLUEPRINT_POLICY)
  public OpsShardReadinessComparedEvidenceCandidateBlueprintResponse policy() {
    return policyService.policy();
  }

  @GetMapping(
      OpsShardReadinessComparedEvidenceCandidateBlueprintRoutePaths
          .COMPARED_EVIDENCE_CANDIDATE_BLUEPRINT_CLOSEOUT)
  public OpsShardReadinessComparedEvidenceCandidateBlueprintResponse closeout() {
    return closeoutService.closeout();
  }
}
