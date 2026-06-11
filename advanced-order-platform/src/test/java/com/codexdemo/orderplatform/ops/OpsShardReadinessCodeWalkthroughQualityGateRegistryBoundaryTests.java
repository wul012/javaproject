package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessCodeWalkthroughQualityGateRegistryBoundaryTests {

    @Test
    void keepsQualityGateRuntimeFreeAndReadOnly() {
        var response = OpsShardReadinessCodeWalkthroughQualityGateRegistryTestSupport.registry();

        assertThat(response.readOnly()).isTrue();
        assertThat(response.executionAllowed()).isFalse();
        assertThat(response.startsJavaService()).isFalse();
        assertThat(response.startsMiniKvService()).isFalse();
        assertThat(response.readsCredentialValue()).isFalse();
        assertThat(response.resolvesRawEndpointUrl()).isFalse();
        assertThat(response.managedAuditHttpAllowed()).isFalse();
        assertThat(response.deniedBoundaryRuleCount()).isEqualTo(response.boundaryRuleCount());
        assertThat(response.evidenceAnchors())
                .allSatisfy(anchor -> assertThat(anchor.runtimeFree()).isTrue());
    }

    @Test
    void emitsQualityGateChecksForForbiddenActions() {
        var response = OpsShardReadinessCodeWalkthroughQualityGateRegistryTestSupport.registry();

        assertThat(response.checks())
                .contains(
                        "code-walkthrough-quality-gate-no-micro-version-by-default",
                        "code-walkthrough-quality-gate-standout-explanation-required",
                        "code-walkthrough-quality-gate-evidence-and-tests-travel-together",
                        "code-walkthrough-quality-gate-no-write-routing",
                        "code-walkthrough-quality-gate-no-credential-value",
                        "code-walkthrough-quality-gate-no-raw-endpoint-url",
                        "code-walkthrough-quality-gate-no-upstream-autostart"
                );
    }
}
