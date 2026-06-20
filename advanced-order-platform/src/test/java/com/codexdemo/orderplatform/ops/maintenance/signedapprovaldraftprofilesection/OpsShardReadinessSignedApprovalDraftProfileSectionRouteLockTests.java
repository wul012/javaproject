package com.codexdemo.orderplatform.ops.maintenance.signedapprovaldraftprofilesection;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessSignedApprovalDraftProfileSectionRouteLockTests {

  @Test
  void routeLocksFreezeFiveRouteFacingFieldsForEachSection() {
    var response = OpsShardReadinessSignedApprovalDraftProfileSectionRegistryTestSupport.registry();

    assertThat(response.routeFieldLocks()).hasSize(5);
    assertThat(response.routeFieldLocks())
        .extracting(
            OpsShardReadinessSignedApprovalDraftProfileSectionRegistryResponse.RouteFieldLock
                ::lockedFieldCount)
        .containsOnly(5);
    assertThat(response.lockedRouteFieldCount()).isEqualTo(25);
  }

  @Test
  void routeLocksCarryEndpointProfileJavaAndNodeMarkers() {
    var response = OpsShardReadinessSignedApprovalDraftProfileSectionRegistryTestSupport.registry();

    assertThat(response.routeFieldLocks())
        .allSatisfy(
            lock -> {
              assertThat(lock.endpoint()).startsWith("/api/v1/ops/shard-readiness/");
              assertThat(lock.profile()).startsWith("java-shard-readiness-");
              assertThat(lock.javaVersion()).startsWith("Java v");
              assertThat(lock.nodeVersionMarker()).startsWith("Node v");
              assertThat(lock.enforcement())
                  .isEqualTo("java-version-endpoint-profile-node-marker-status-locked");
            });
  }
}
