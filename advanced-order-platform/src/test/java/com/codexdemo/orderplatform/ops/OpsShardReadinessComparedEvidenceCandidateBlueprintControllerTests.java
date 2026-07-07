package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.comparedevidencecandidateblueprint.OpsShardReadinessComparedEvidenceCandidateBlueprintCatalogService;
import com.codexdemo.orderplatform.ops.maintenance.comparedevidencecandidateblueprint.OpsShardReadinessComparedEvidenceCandidateBlueprintCloseoutService;
import com.codexdemo.orderplatform.ops.maintenance.comparedevidencecandidateblueprint.OpsShardReadinessComparedEvidenceCandidateBlueprintComparisonService;
import com.codexdemo.orderplatform.ops.maintenance.comparedevidencecandidateblueprint.OpsShardReadinessComparedEvidenceCandidateBlueprintPolicyService;
import com.codexdemo.orderplatform.ops.maintenance.comparedevidencecandidateblueprint.OpsShardReadinessComparedEvidenceCandidateBlueprintSourceService;
import org.junit.jupiter.api.Test;

class OpsShardReadinessComparedEvidenceCandidateBlueprintControllerTests {

  @Test
  void exposesCandidateBlueprintRoutesWithoutRuntimeOrMutation() {
    var controller = controller();

    assertThat(controller.catalog().candidateSectionCount()).isEqualTo(10);
    assertThat(controller.source().version()).isEqualTo("Java v1061");
    assertThat(controller.comparison().readyForEvidenceImport()).isFalse();
    assertThat(controller.policy().readyForRuntimePayload()).isFalse();
    assertThat(controller.closeout().siblingMutationAllowed()).isFalse();
  }

  private OpsShardReadinessComparedEvidenceCandidateBlueprintController controller() {
    return new OpsShardReadinessComparedEvidenceCandidateBlueprintController(
        new OpsShardReadinessComparedEvidenceCandidateBlueprintCatalogService(),
        new OpsShardReadinessComparedEvidenceCandidateBlueprintSourceService(),
        new OpsShardReadinessComparedEvidenceCandidateBlueprintComparisonService(),
        new OpsShardReadinessComparedEvidenceCandidateBlueprintPolicyService(),
        new OpsShardReadinessComparedEvidenceCandidateBlueprintCloseoutService());
  }
}
