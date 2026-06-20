package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.signedapprovaldrafttextpackageprofilesection.OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryTestSupport;
import org.junit.jupiter.api.Test;

class OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryControllerTests {

  @Test
  void registryRouteExposesReadOnlyTextPackageProfileSectionRegistry() {
    assertThat(
            OpsShardReadinessRoutePaths.SIGNED_APPROVAL_DRAFT_TEXT_PACKAGE_PROFILE_SECTION_REGISTRY)
        .isEqualTo("/signed-approval-draft-text-package-profile-section-registry");

    var response =
        new OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryController(
                OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryTestSupport
                    .service())
            .registry();

    assertThat(response.endpoint())
        .isEqualTo(
            "/api/v1/ops/shard-readiness/signed-approval-draft-text-package-profile-section-registry");
    assertThat(response.profile())
        .isEqualTo(
            "java-shard-readiness-signed-approval-draft-text-package-profile-section-registry.v1");
    assertThat(response.version()).isEqualTo("Java v1287");
    assertThat(response.readOnly()).isTrue();
    assertThat(response.executionAllowed()).isFalse();
  }
}
