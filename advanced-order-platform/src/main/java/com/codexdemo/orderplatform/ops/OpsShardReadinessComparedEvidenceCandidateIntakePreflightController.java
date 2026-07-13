package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.comparedevidencecandidateintakepreflight.OpsShardReadinessComparedEvidenceCandidateIntakePreflightCatalogService;
import com.codexdemo.orderplatform.ops.maintenance.comparedevidencecandidateintakepreflight.OpsShardReadinessComparedEvidenceCandidateIntakePreflightCloseoutService;
import com.codexdemo.orderplatform.ops.maintenance.comparedevidencecandidateintakepreflight.OpsShardReadinessComparedEvidenceCandidateIntakePreflightComparisonService;
import com.codexdemo.orderplatform.ops.maintenance.comparedevidencecandidateintakepreflight.OpsShardReadinessComparedEvidenceCandidateIntakePreflightPolicyService;
import com.codexdemo.orderplatform.ops.maintenance.comparedevidencecandidateintakepreflight.OpsShardReadinessComparedEvidenceCandidateIntakePreflightResponse;
import com.codexdemo.orderplatform.ops.maintenance.comparedevidencecandidateintakepreflight.OpsShardReadinessComparedEvidenceCandidateIntakePreflightRoutePaths;
import com.codexdemo.orderplatform.ops.maintenance.comparedevidencecandidateintakepreflight.OpsShardReadinessComparedEvidenceCandidateIntakePreflightSourceService;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessService.BASE_PATH)
public class OpsShardReadinessComparedEvidenceCandidateIntakePreflightController {

  private final OpsShardReadinessComparedEvidenceCandidateIntakePreflightCatalogService
      catalogService;
  private final OpsShardReadinessComparedEvidenceCandidateIntakePreflightSourceService
      sourceService;
  private final OpsShardReadinessComparedEvidenceCandidateIntakePreflightComparisonService
      comparisonService;
  private final OpsShardReadinessComparedEvidenceCandidateIntakePreflightPolicyService
      policyService;
  private final OpsShardReadinessComparedEvidenceCandidateIntakePreflightCloseoutService
      closeoutService;

  public OpsShardReadinessComparedEvidenceCandidateIntakePreflightController(
      OpsShardReadinessComparedEvidenceCandidateIntakePreflightCatalogService catalogService,
      OpsShardReadinessComparedEvidenceCandidateIntakePreflightSourceService sourceService,
      OpsShardReadinessComparedEvidenceCandidateIntakePreflightComparisonService comparisonService,
      OpsShardReadinessComparedEvidenceCandidateIntakePreflightPolicyService policyService,
      OpsShardReadinessComparedEvidenceCandidateIntakePreflightCloseoutService closeoutService) {
    this.catalogService = catalogService;
    this.sourceService = sourceService;
    this.comparisonService = comparisonService;
    this.policyService = policyService;
    this.closeoutService = closeoutService;
  }

  @GetMapping(
      OpsShardReadinessComparedEvidenceCandidateIntakePreflightRoutePaths
          .COMPARED_EVIDENCE_CANDIDATE_INTAKE_PREFLIGHT_CATALOG)
  public OpsShardReadinessComparedEvidenceCandidateIntakePreflightResponse catalog() {
    return catalogService.catalog();
  }

  @GetMapping(
      OpsShardReadinessComparedEvidenceCandidateIntakePreflightRoutePaths
          .COMPARED_EVIDENCE_CANDIDATE_INTAKE_PREFLIGHT_SOURCE)
  public OpsShardReadinessComparedEvidenceCandidateIntakePreflightResponse source() {
    return sourceService.source();
  }

  @GetMapping(
      OpsShardReadinessComparedEvidenceCandidateIntakePreflightRoutePaths
          .COMPARED_EVIDENCE_CANDIDATE_INTAKE_PREFLIGHT_COMPARISON)
  public OpsShardReadinessComparedEvidenceCandidateIntakePreflightResponse comparison() {
    return comparisonService.comparison();
  }

  @GetMapping(
      OpsShardReadinessComparedEvidenceCandidateIntakePreflightRoutePaths
          .COMPARED_EVIDENCE_CANDIDATE_INTAKE_PREFLIGHT_POLICY)
  public OpsShardReadinessComparedEvidenceCandidateIntakePreflightResponse policy() {
    return policyService.policy();
  }

  @GetMapping(
      OpsShardReadinessComparedEvidenceCandidateIntakePreflightRoutePaths
          .COMPARED_EVIDENCE_CANDIDATE_INTAKE_PREFLIGHT_CLOSEOUT)
  public OpsShardReadinessComparedEvidenceCandidateIntakePreflightResponse closeout() {
    return closeoutService.closeout();
  }
}
