package com.codexdemo.orderplatform.ops.maintenance.readability;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ReadabilityUpkeepAuditControllerTests {

    @Test
    void auditRouteExposesReadabilityEvidence() {
        var response = new ReadabilityUpkeepAuditController(
                ReadabilityUpkeepAuditTestSupport.service())
                .audit();

        assertThat(response.endpoint()).isEqualTo("/api/v1/ops/readability/upkeep-audit");
        assertThat(response.version()).isEqualTo("Java v1788");
        assertThat(response.readOnly()).isTrue();
        assertThat(response.executionAllowed()).isFalse();
        assertThat(response.sourceRegistryEndpoint())
                .isEqualTo("/api/v1/ops/readability/upkeep-registry");
    }
}
