package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.ProfileTestData;
import org.junit.jupiter.api.Test;

class OpsShardReadinessCandidateDocumentProfileSectionRouteEvidenceTests {

  @Test
  void routeCarriesRendererSplitEvidenceWithoutRuntimeBehavior() {
    var response =
        new OpsShardReadinessCandidateDocumentProfileSectionRegistryController(
                ProfileTestData.service())
            .registry();

    assertThat(response.checks())
        .contains(
            "candidate-document-profile-section-registry-source-plan-Node v1481",
            "candidate-document-profile-section-registry-source-node-Node v1481",
            "candidate-document-profile-section-registry-section-count-5",
            "candidate-document-profile-section-registry-rendered-section-count-5",
            "candidate-document-profile-section-registry-field-entry-count-25",
            "candidate-document-profile-section-registry-runtime-disabled",
            "candidate-document-profile-section-registry-write-disabled",
            "candidate-document-profile-section-registry-sibling-mutation-disabled",
            "candidate-document-profile-section-registry-service-assembled-from-five-read-only-routes");
  }
}
