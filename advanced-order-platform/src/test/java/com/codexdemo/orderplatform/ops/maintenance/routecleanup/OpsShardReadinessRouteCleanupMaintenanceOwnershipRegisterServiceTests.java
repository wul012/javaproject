package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessRouteCleanupMaintenanceOwnershipRegisterServiceTests {

  @Test
  void buildsOwnershipRegisterFromUpkeepCatalogConsumers() {
    OpsShardReadinessRouteCleanupMaintenanceOwnershipRegisterResponse register =
        new OpsShardReadinessRouteCleanupMaintenanceOwnershipRegisterService().register();

    assertThat(register.version()).isEqualTo("Java v518");
    assertThat(register.endpoint())
        .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-maintenance-ownership-register");
    assertThat(register.profile())
        .isEqualTo("java-shard-readiness-route-cleanup-maintenance-ownership-register.v1");
    assertThat(register.ownerEntryCount()).isEqualTo(9);
    assertThat(register.distinctOwnerCount()).isEqualTo(9);
    assertThat(register.owners().get(4).owner()).isEqualTo("runtime-boundary-reviewer");
    assertThat(register.owners().get(4).boundary()).isEqualTo("read-only-boundary");
    assertThat(register.owners())
        .allSatisfy(
            owner -> {
              assertThat(owner.sourceEndpoint()).startsWith("/api/v1/ops/shard-readiness");
              assertThat(owner.status()).isEqualTo("passed");
            });
    assertThat(register.checks()).contains("ownership-register-remains-read-only");
    assertThat(register.status()).isEqualTo("passed");
  }
}
