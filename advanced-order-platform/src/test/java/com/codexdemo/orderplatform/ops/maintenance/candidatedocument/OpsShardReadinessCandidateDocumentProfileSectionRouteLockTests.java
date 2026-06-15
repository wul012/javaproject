package com.codexdemo.orderplatform.ops.maintenance.candidatedocument;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessCandidateDocumentProfileSectionRouteLockTests {

  @Test
  void routeLocksPinEndpointProfileAndVersionPerSection() {
    var response = OpsShardReadinessCandidateDocumentProfileSectionRegistryTestSupport.registry();

    assertThat(response.routeFieldLocks()).hasSize(5);
    assertThat(response.routeFieldLocks())
        .allSatisfy(
            lock -> {
              assertThat(lock.endpoint()).startsWith("/api/v1/ops/shard-readiness/");
              assertThat(lock.profile()).startsWith("java-shard-readiness-");
              assertThat(lock.sourceVersion()).startsWith("Java v");
              assertThat(lock.lockedFieldCount()).isEqualTo(3);
              assertThat(lock.enforcement()).isEqualTo("fail-closed-route-facing-fields");
            });
  }
}
