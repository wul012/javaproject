package com.codexdemo.orderplatform.ops.maintenance.signedapprovaldraftprofilesection;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessSignedApprovalDraftProfileSectionSourceCatalogTests {

  @Test
  void sourceCatalogKeepsDraftRouteOrderAndVersionMarkersStable() {
    var response = OpsShardReadinessSignedApprovalDraftProfileSectionRegistryTestSupport.registry();

    assertThat(response.sources())
        .extracting(
            OpsShardReadinessSignedApprovalDraftProfileSectionRegistryResponse.DraftSectionSource
                ::code)
        .containsExactly(
            "signed-approval-artifact-draft-preflight",
            "signed-approval-artifact-draft-readiness",
            "signed-approval-artifact-draft-review-package-preflight",
            "signed-approval-artifact-draft-authoring-readiness",
            "signed-approval-artifact-draft-instruction-preflight");
    assertThat(response.sources())
        .extracting(
            OpsShardReadinessSignedApprovalDraftProfileSectionRegistryResponse.DraftSectionSource
                ::nodeVersionMarker)
        .containsExactly("Node v1111", "Node v1136", "Node v1161", "Node v1186", "Node v1211");
  }

  @Test
  void sourceCatalogKeepsJavaRouteVersionsVisible() {
    var response = OpsShardReadinessSignedApprovalDraftProfileSectionRegistryTestSupport.registry();

    assertThat(response.sources())
        .extracting(
            OpsShardReadinessSignedApprovalDraftProfileSectionRegistryResponse.DraftSectionSource
                ::javaVersion)
        .containsExactly("Java v796", "Java v771", "Java v846", "Java v871", "Java v896");
  }
}
