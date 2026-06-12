package com.codexdemo.orderplatform.ops.maintenance.readability;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ReadabilityUpkeepBoundaryTests {

    @Test
    void keepsReadabilityUpkeepReadOnly() {
        var response = ReadabilityUpkeepRegistryTestSupport.registry();

        assertThat(response.readOnly()).isTrue();
        assertThat(response.executionAllowed()).isFalse();
        assertThat(response.startsJavaService()).isFalse();
        assertThat(response.startsMiniKvService()).isFalse();
        assertThat(response.writesBusinessState()).isFalse();
        assertThat(response.readsCredentialValue()).isFalse();
        assertThat(response.resolvesRawEndpointUrl()).isFalse();
        assertThat(response.managedAuditConnectionAllowed()).isFalse();
        assertThat(response.boundaryRules())
                .allSatisfy(rule -> assertThat(rule.allowed()).isFalse());
        assertThat(response.boundaryRules())
                .extracting(ReadabilityUpkeepRegistryResponse.BoundaryRule::code)
                .contains(
                        "no-write-routing",
                        "no-active-shard-router",
                        "no-credential-value",
                        "no-raw-endpoint-url",
                        "no-managed-audit-connection",
                        "no-deployment-or-rollback",
                        "no-java-autostart",
                        "no-minikv-autostart"
                );
    }
}
