package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessMinimalReadOnlyGateExecutionReadTargetCatalogTests {

    @Test
    void exposesFiveReadTargetsWithoutRawEndpointResolution() {
        var readTargets = OpsShardReadinessMinimalReadOnlyGateExecutionReadTargetCatalog.readTargets();

        assertThat(readTargets).hasSize(
                OpsShardReadinessMinimalReadOnlyGateExecutionRegistrySupport
                        .EXPECTED_READ_TARGET_COUNT);
        assertThat(readTargets)
                .extracting(OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse
                        .ReadTarget::target)
                .containsExactly(
                        "java-health",
                        "java-ops-overview",
                        "mini-kv-health",
                        "mini-kv-infojson",
                        "mini-kv-statsjson"
                );
        assertThat(readTargets)
                .allSatisfy(target -> {
                    assertThat(target.readOnly()).isTrue();
                    assertThat(target.externallyStarted()).isTrue();
                    assertThat(target.status()).isEqualTo("passed");
                    assertThat(target.addressHandle()).doesNotContain("://");
                });
    }
}
