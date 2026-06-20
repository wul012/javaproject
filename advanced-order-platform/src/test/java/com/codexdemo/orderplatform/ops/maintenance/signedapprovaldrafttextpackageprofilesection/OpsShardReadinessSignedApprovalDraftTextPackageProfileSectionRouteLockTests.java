package com.codexdemo.orderplatform.ops.maintenance.signedapprovaldrafttextpackageprofilesection;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRouteLockTests {

  @Test
  void routeLocksFreezeFiveFieldsForEachTextPackageSection() {
    var response =
        OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryTestSupport.registry();

    assertThat(response.routeFieldLocks()).hasSize(9);
    assertThat(response.routeFieldLocks())
        .extracting(
            OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryResponse
                    .RouteFieldLock
                ::lockedFieldCount)
        .containsOnly(5);
    assertThat(response.lockedRouteFieldCount()).isEqualTo(45);
  }

  @Test
  void routeLocksCarryRendererGroupAndRouteIdentity() {
    var response =
        OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryTestSupport.registry();

    assertThat(response.routeFieldLocks())
        .allSatisfy(
            lock -> {
              assertThat(lock.endpoint()).startsWith("/api/v1/ops/shard-readiness/");
              assertThat(lock.profile()).startsWith("java-shard-readiness-");
              assertThat(lock.javaVersion()).startsWith("Java v");
              assertThat(lock.nodeVersionMarker()).startsWith("Node v");
              assertThat(lock.rendererGroup()).isIn("submission", "compared-evidence");
            });
  }
}
