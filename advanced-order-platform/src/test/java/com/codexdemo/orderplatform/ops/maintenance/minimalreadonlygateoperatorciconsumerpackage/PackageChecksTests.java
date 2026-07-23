package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorciconsumerpackage;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class PackageChecksTests {

  @Test
  void checksRemainStableAndBoundaryFocused() {
    var checks = ConsumerPackageTestData.registry().checks();

    assertThat(checks)
        .hasSize(28)
        .contains(
            "minimal-read-only-gate-operator-ci-handoff-consumer-package-no-upstream-autostart",
            "minimal-read-only-gate-operator-ci-handoff-consumer-package-no-write-routing",
            "minimal-read-only-gate-operator-ci-handoff-consumer-package-no-secret-value",
            "minimal-read-only-gate-operator-ci-handoff-consumer-package-no-raw-endpoint-resolution",
            "minimal-read-only-gate-operator-ci-handoff-consumer-package-no-managed-audit-http");
  }
}
