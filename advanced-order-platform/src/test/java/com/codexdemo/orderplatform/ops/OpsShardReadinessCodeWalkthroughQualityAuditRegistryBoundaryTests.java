package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessCodeWalkthroughQualityAuditRegistryBoundaryTests {

    @Test
    void keepsQualityAuditRuntimeFreeAndReadOnly() {
        var response = OpsShardReadinessCodeWalkthroughQualityAuditRegistryTestSupport.registry();

        assertThat(response.readOnly()).isTrue();
        assertThat(response.executionAllowed()).isFalse();
        assertThat(response.startsJavaService()).isFalse();
        assertThat(response.startsMiniKvService()).isFalse();
        assertThat(response.readsCredentialValue()).isFalse();
        assertThat(response.resolvesRawEndpointUrl()).isFalse();
        assertThat(response.managedAuditHttpAllowed()).isFalse();
        assertThat(response.deniedBoundaryAuditCount()).isEqualTo(response.boundaryAuditCount());
        assertThat(response.reviewFindings())
                .allSatisfy(finding -> assertThat(finding.blocking()).isFalse());
    }

    @Test
    void emitsAuditChecksForBoundaryAndQualityResults() {
        var response = OpsShardReadinessCodeWalkthroughQualityAuditRegistryTestSupport.registry();

        assertThat(response.checks())
                .contains(
                        "code-walkthrough-quality-audit-no-shallow-version-found",
                        "code-walkthrough-quality-audit-no-write-routing",
                        "code-walkthrough-quality-audit-no-credential-value",
                        "code-walkthrough-quality-audit-no-raw-endpoint-url",
                        "code-walkthrough-quality-audit-no-upstream-autostart"
                );
    }
}
