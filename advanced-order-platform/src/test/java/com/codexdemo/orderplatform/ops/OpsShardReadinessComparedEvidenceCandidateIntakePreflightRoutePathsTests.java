package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.comparedevidencecandidateintakepreflight.OpsShardReadinessComparedEvidenceCandidateIntakePreflightCatalogService;
import com.codexdemo.orderplatform.ops.maintenance.comparedevidencecandidateintakepreflight.OpsShardReadinessComparedEvidenceCandidateIntakePreflightCloseoutService;
import com.codexdemo.orderplatform.ops.maintenance.comparedevidencecandidateintakepreflight.OpsShardReadinessComparedEvidenceCandidateIntakePreflightComparisonService;
import com.codexdemo.orderplatform.ops.maintenance.comparedevidencecandidateintakepreflight.OpsShardReadinessComparedEvidenceCandidateIntakePreflightPolicyService;
import com.codexdemo.orderplatform.ops.maintenance.comparedevidencecandidateintakepreflight.OpsShardReadinessComparedEvidenceCandidateIntakePreflightRoutePaths;
import com.codexdemo.orderplatform.ops.maintenance.comparedevidencecandidateintakepreflight.OpsShardReadinessComparedEvidenceCandidateIntakePreflightSourceService;
import org.junit.jupiter.api.Test;

class OpsShardReadinessComparedEvidenceCandidateIntakePreflightRoutePathsTests {

  @Test
  void candidateIntakePreflightRoutesRemainReadOnlyPreflightSurfaces() {
    assertThat(
            OpsShardReadinessRoutePaths
                .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_COMPARED_EVIDENCE_CANDIDATE_INTAKE_PREFLIGHT_CATALOG)
        .endsWith("compared-evidence-candidate-intake-preflight-catalog");
    assertThat(
            OpsShardReadinessRoutePaths
                .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_COMPARED_EVIDENCE_CANDIDATE_INTAKE_PREFLIGHT_SOURCE)
        .endsWith("compared-evidence-candidate-intake-preflight-source");
    assertThat(
            OpsShardReadinessRoutePaths
                .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_COMPARED_EVIDENCE_CANDIDATE_INTAKE_PREFLIGHT_COMPARISON)
        .endsWith("compared-evidence-candidate-intake-preflight-comparison");
    assertThat(
            OpsShardReadinessRoutePaths
                .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_COMPARED_EVIDENCE_CANDIDATE_INTAKE_PREFLIGHT_POLICY)
        .endsWith("compared-evidence-candidate-intake-preflight-policy");
    assertThat(
            OpsShardReadinessRoutePaths
                .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_COMPARED_EVIDENCE_CANDIDATE_INTAKE_PREFLIGHT_CLOSEOUT)
        .endsWith("compared-evidence-candidate-intake-preflight-closeout");
  }

  @Test
  void candidateIntakePreflightRoutesDelegateToSplitOwnerAndMovedServices() {
    assertThat(
            OpsShardReadinessRoutePaths
                .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_COMPARED_EVIDENCE_CANDIDATE_INTAKE_PREFLIGHT_CATALOG)
        .isEqualTo(
            OpsShardReadinessComparedEvidenceCandidateIntakePreflightRoutePaths
                .COMPARED_EVIDENCE_CANDIDATE_INTAKE_PREFLIGHT_CATALOG);
    assertThat(OpsShardReadinessComparedEvidenceCandidateIntakePreflightCatalogService.ENDPOINT)
        .endsWith(
            OpsShardReadinessComparedEvidenceCandidateIntakePreflightRoutePaths
                .COMPARED_EVIDENCE_CANDIDATE_INTAKE_PREFLIGHT_CATALOG);
    assertThat(OpsShardReadinessComparedEvidenceCandidateIntakePreflightSourceService.ENDPOINT)
        .endsWith(
            OpsShardReadinessComparedEvidenceCandidateIntakePreflightRoutePaths
                .COMPARED_EVIDENCE_CANDIDATE_INTAKE_PREFLIGHT_SOURCE);
    assertThat(OpsShardReadinessComparedEvidenceCandidateIntakePreflightComparisonService.ENDPOINT)
        .endsWith(
            OpsShardReadinessComparedEvidenceCandidateIntakePreflightRoutePaths
                .COMPARED_EVIDENCE_CANDIDATE_INTAKE_PREFLIGHT_COMPARISON);
    assertThat(OpsShardReadinessComparedEvidenceCandidateIntakePreflightPolicyService.ENDPOINT)
        .endsWith(
            OpsShardReadinessComparedEvidenceCandidateIntakePreflightRoutePaths
                .COMPARED_EVIDENCE_CANDIDATE_INTAKE_PREFLIGHT_POLICY);
    assertThat(OpsShardReadinessComparedEvidenceCandidateIntakePreflightCloseoutService.ENDPOINT)
        .endsWith(
            OpsShardReadinessComparedEvidenceCandidateIntakePreflightRoutePaths
                .COMPARED_EVIDENCE_CANDIDATE_INTAKE_PREFLIGHT_CLOSEOUT);
  }
}
