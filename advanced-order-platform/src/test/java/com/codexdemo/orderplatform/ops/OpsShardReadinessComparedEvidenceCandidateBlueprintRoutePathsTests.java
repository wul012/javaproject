package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.comparedevidencecandidateblueprint.OpsShardReadinessComparedEvidenceCandidateBlueprintCatalogService;
import com.codexdemo.orderplatform.ops.maintenance.comparedevidencecandidateblueprint.OpsShardReadinessComparedEvidenceCandidateBlueprintCloseoutService;
import com.codexdemo.orderplatform.ops.maintenance.comparedevidencecandidateblueprint.OpsShardReadinessComparedEvidenceCandidateBlueprintComparisonService;
import com.codexdemo.orderplatform.ops.maintenance.comparedevidencecandidateblueprint.OpsShardReadinessComparedEvidenceCandidateBlueprintPolicyService;
import com.codexdemo.orderplatform.ops.maintenance.comparedevidencecandidateblueprint.OpsShardReadinessComparedEvidenceCandidateBlueprintRoutePaths;
import com.codexdemo.orderplatform.ops.maintenance.comparedevidencecandidateblueprint.OpsShardReadinessComparedEvidenceCandidateBlueprintSourceService;
import org.junit.jupiter.api.Test;

class OpsShardReadinessComparedEvidenceCandidateBlueprintRoutePathsTests {

  @Test
  void candidateBlueprintRoutesRemainReadOnlyBlueprintSurfaces() {
    assertThat(
            OpsShardReadinessRoutePaths
                .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_COMPARED_EVIDENCE_CANDIDATE_BLUEPRINT_CATALOG)
        .endsWith("compared-evidence-candidate-blueprint-catalog");
    assertThat(
            OpsShardReadinessRoutePaths
                .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_COMPARED_EVIDENCE_CANDIDATE_BLUEPRINT_SOURCE)
        .endsWith("compared-evidence-candidate-blueprint-source");
    assertThat(
            OpsShardReadinessRoutePaths
                .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_COMPARED_EVIDENCE_CANDIDATE_BLUEPRINT_COMPARISON)
        .endsWith("compared-evidence-candidate-blueprint-comparison");
    assertThat(
            OpsShardReadinessRoutePaths
                .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_COMPARED_EVIDENCE_CANDIDATE_BLUEPRINT_POLICY)
        .endsWith("compared-evidence-candidate-blueprint-policy");
    assertThat(
            OpsShardReadinessRoutePaths
                .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_COMPARED_EVIDENCE_CANDIDATE_BLUEPRINT_CLOSEOUT)
        .endsWith("compared-evidence-candidate-blueprint-closeout");
  }

  @Test
  void candidateBlueprintRoutesDelegateToSplitOwnerAndMovedServices() {
    assertThat(
            OpsShardReadinessRoutePaths
                .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_COMPARED_EVIDENCE_CANDIDATE_BLUEPRINT_CATALOG)
        .isEqualTo(
            OpsShardReadinessComparedEvidenceCandidateBlueprintRoutePaths
                .COMPARED_EVIDENCE_CANDIDATE_BLUEPRINT_CATALOG);
    assertThat(OpsShardReadinessComparedEvidenceCandidateBlueprintCatalogService.ENDPOINT)
        .endsWith(
            OpsShardReadinessComparedEvidenceCandidateBlueprintRoutePaths
                .COMPARED_EVIDENCE_CANDIDATE_BLUEPRINT_CATALOG);
    assertThat(OpsShardReadinessComparedEvidenceCandidateBlueprintSourceService.ENDPOINT)
        .endsWith(
            OpsShardReadinessComparedEvidenceCandidateBlueprintRoutePaths
                .COMPARED_EVIDENCE_CANDIDATE_BLUEPRINT_SOURCE);
    assertThat(OpsShardReadinessComparedEvidenceCandidateBlueprintComparisonService.ENDPOINT)
        .endsWith(
            OpsShardReadinessComparedEvidenceCandidateBlueprintRoutePaths
                .COMPARED_EVIDENCE_CANDIDATE_BLUEPRINT_COMPARISON);
    assertThat(OpsShardReadinessComparedEvidenceCandidateBlueprintPolicyService.ENDPOINT)
        .endsWith(
            OpsShardReadinessComparedEvidenceCandidateBlueprintRoutePaths
                .COMPARED_EVIDENCE_CANDIDATE_BLUEPRINT_POLICY);
    assertThat(OpsShardReadinessComparedEvidenceCandidateBlueprintCloseoutService.ENDPOINT)
        .endsWith(
            OpsShardReadinessComparedEvidenceCandidateBlueprintRoutePaths
                .COMPARED_EVIDENCE_CANDIDATE_BLUEPRINT_CLOSEOUT);
  }
}
