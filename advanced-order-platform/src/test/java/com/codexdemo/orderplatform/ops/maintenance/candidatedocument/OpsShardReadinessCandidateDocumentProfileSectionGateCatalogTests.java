package com.codexdemo.orderplatform.ops.maintenance.candidatedocument;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessCandidateDocumentProfileSectionGateCatalogTests {

  @Test
  void gatesAreDistinctAndAddNoRuntimeCapability() {
    var response = OpsShardReadinessCandidateDocumentProfileSectionRegistryTestSupport.registry();

    assertThat(response.gates())
        .hasSize(43)
        .doesNotHaveDuplicates()
        .allSatisfy(
            gate ->
                assertThat(gate)
                    .startsWith("candidate-document-profile-section-registry-no-runtime-gate-"));
    assertThat(response.gates())
        .last()
        .isEqualTo("candidate-document-profile-section-registry-no-runtime-gate-43");
  }
}
