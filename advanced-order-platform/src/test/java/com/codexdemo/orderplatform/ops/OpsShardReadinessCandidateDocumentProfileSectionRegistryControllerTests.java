package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentProfileSectionRegistryTestSupport;
import org.junit.jupiter.api.Test;

class OpsShardReadinessCandidateDocumentProfileSectionRegistryControllerTests {

  @Test
  void registryRouteExposesReadOnlyProfileSectionRegistry() {
    assertThat(OpsShardReadinessRoutePaths.CANDIDATE_DOCUMENT_PROFILE_SECTION_REGISTRY)
        .isEqualTo("/candidate-document-profile-section-registry");

    var response =
        new OpsShardReadinessCandidateDocumentProfileSectionRegistryController(
                OpsShardReadinessCandidateDocumentProfileSectionRegistryTestSupport.service())
            .registry();

    assertThat(response.endpoint())
        .isEqualTo("/api/v1/ops/shard-readiness/candidate-document-profile-section-registry");
    assertThat(response.profile())
        .isEqualTo("java-shard-readiness-candidate-document-profile-section-registry.v1");
    assertThat(response.version()).isEqualTo("Java v1212");
    assertThat(response.readOnly()).isTrue();
    assertThat(response.executionAllowed()).isFalse();
  }
}
