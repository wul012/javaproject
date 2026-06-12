package com.codexdemo.orderplatform.ops.maintenance.readability;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ReadabilityUpkeepAuditBoundaryTests {

    @Test
    void keepsReadabilityAuditReadOnly() {
        var response = ReadabilityUpkeepAuditTestSupport.audit();

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
        assertThat(response.rootPackagePressures())
                .allSatisfy(pressure -> assertThat(pressure.migrationRequiredNow()).isFalse());
    }
}
