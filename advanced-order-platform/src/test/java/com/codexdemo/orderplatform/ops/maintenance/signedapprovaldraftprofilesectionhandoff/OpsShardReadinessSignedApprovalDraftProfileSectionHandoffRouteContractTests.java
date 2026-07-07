package com.codexdemo.orderplatform.ops.maintenance.signedapprovaldraftprofilesectionhandoff;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessSignedApprovalDraftProfileSectionHandoffRouteContractTests {

  @Test
  void routeContractsTransferFiveLockedFieldsForEachSection() {
    var response = OpsShardReadinessSignedApprovalDraftProfileSectionHandoffTestSupport.handoff();

    assertThat(response.routeContracts()).hasSize(5);
    assertThat(response.routeContracts())
        .extracting(
            OpsShardReadinessSignedApprovalDraftProfileSectionHandoffResponse.RouteContract
                ::lockedFieldCount)
        .containsOnly(5);
    assertThat(response.transferredLockedRouteFieldCount()).isEqualTo(25);
  }

  @Test
  void routeContractsRemainReadOnlyForDownstreamConsumers() {
    var response = OpsShardReadinessSignedApprovalDraftProfileSectionHandoffTestSupport.handoff();

    assertThat(response.routeContracts())
        .allSatisfy(
            contract -> {
              assertThat(contract.consumerRule())
                  .isEqualTo("downstream-may-read-route-contract-only");
              assertThat(contract.endpoint()).startsWith("/api/v1/ops/shard-readiness/");
              assertThat(contract.profile()).startsWith("java-shard-readiness-");
              assertThat(contract.javaVersion()).startsWith("Java v");
              assertThat(contract.nodeVersionMarker()).startsWith("Node v");
            });
  }
}
