package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.comparedevidenceevaluationpreflight.OpsShardReadinessComparedEvidenceEvaluationPreflightCatalogService;
import com.codexdemo.orderplatform.ops.maintenance.comparedevidenceevaluationpreflight.OpsShardReadinessComparedEvidenceEvaluationPreflightExclusionCloseoutService;
import com.codexdemo.orderplatform.ops.maintenance.comparedevidenceevaluationpreflight.OpsShardReadinessComparedEvidenceEvaluationPreflightIdentityDigestService;
import com.codexdemo.orderplatform.ops.maintenance.comparedevidenceevaluationpreflight.OpsShardReadinessComparedEvidenceEvaluationPreflightPolicyRuntimeService;
import com.codexdemo.orderplatform.ops.maintenance.comparedevidenceevaluationpreflight.OpsShardReadinessComparedEvidenceEvaluationPreflightResponse;
import com.codexdemo.orderplatform.ops.maintenance.comparedevidenceevaluationpreflight.OpsShardReadinessComparedEvidenceEvaluationPreflightSourceArtifactService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessComparedEvidenceEvaluationPreflightController {

  private final OpsShardReadinessComparedEvidenceEvaluationPreflightCatalogService catalogService;
  private final OpsShardReadinessComparedEvidenceEvaluationPreflightSourceArtifactService
      sourceArtifactService;
  private final OpsShardReadinessComparedEvidenceEvaluationPreflightIdentityDigestService
      identityDigestService;
  private final OpsShardReadinessComparedEvidenceEvaluationPreflightPolicyRuntimeService
      policyRuntimeService;
  private final OpsShardReadinessComparedEvidenceEvaluationPreflightExclusionCloseoutService
      exclusionCloseoutService;

  public OpsShardReadinessComparedEvidenceEvaluationPreflightController(
      OpsShardReadinessComparedEvidenceEvaluationPreflightCatalogService catalogService,
      OpsShardReadinessComparedEvidenceEvaluationPreflightSourceArtifactService
          sourceArtifactService,
      OpsShardReadinessComparedEvidenceEvaluationPreflightIdentityDigestService
          identityDigestService,
      OpsShardReadinessComparedEvidenceEvaluationPreflightPolicyRuntimeService policyRuntimeService,
      OpsShardReadinessComparedEvidenceEvaluationPreflightExclusionCloseoutService
          exclusionCloseoutService) {
    this.catalogService = catalogService;
    this.sourceArtifactService = sourceArtifactService;
    this.identityDigestService = identityDigestService;
    this.policyRuntimeService = policyRuntimeService;
    this.exclusionCloseoutService = exclusionCloseoutService;
  }

  @GetMapping(
      OpsShardReadinessRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_COMPARED_EVIDENCE_EVALUATION_PREFLIGHT_CATALOG)
  public OpsShardReadinessComparedEvidenceEvaluationPreflightResponse catalog() {
    return catalogService.catalog();
  }

  @GetMapping(
      OpsShardReadinessRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_COMPARED_EVIDENCE_EVALUATION_PREFLIGHT_SOURCE_ARTIFACT)
  public OpsShardReadinessComparedEvidenceEvaluationPreflightResponse sourceArtifact() {
    return sourceArtifactService.sourceArtifact();
  }

  @GetMapping(
      OpsShardReadinessRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_COMPARED_EVIDENCE_EVALUATION_PREFLIGHT_IDENTITY_DIGEST)
  public OpsShardReadinessComparedEvidenceEvaluationPreflightResponse identityDigest() {
    return identityDigestService.identityDigest();
  }

  @GetMapping(
      OpsShardReadinessRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_COMPARED_EVIDENCE_EVALUATION_PREFLIGHT_POLICY_RUNTIME)
  public OpsShardReadinessComparedEvidenceEvaluationPreflightResponse policyRuntime() {
    return policyRuntimeService.policyRuntime();
  }

  @GetMapping(
      OpsShardReadinessRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_COMPARED_EVIDENCE_EVALUATION_PREFLIGHT_EXCLUSION_CLOSEOUT)
  public OpsShardReadinessComparedEvidenceEvaluationPreflightResponse exclusionCloseout() {
    return exclusionCloseoutService.exclusionCloseout();
  }
}
