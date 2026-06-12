package com.codexdemo.orderplatform.ops.maintenance.readability;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ReadabilityUpkeepAuditServiceTests {

    @Test
    void buildsReadabilityUpkeepAuditRegistry() {
        var response = ReadabilityUpkeepAuditTestSupport.audit();

        assertThat(response.project()).isEqualTo("advanced-order-platform");
        assertThat(response.version()).isEqualTo("Java v1788");
        assertThat(response.endpoint()).isEqualTo("/api/v1/ops/readability/upkeep-audit");
        assertThat(response.profile()).isEqualTo("java-ops-readability-upkeep-audit.v1");
        assertThat(response.docsRoot()).isEqualTo("docs/ops");
        assertThat(response.packageRoot())
                .isEqualTo("com.codexdemo.orderplatform.ops.maintenance.readability");
        assertThat(response.sourceRegistryEndpoint())
                .isEqualTo("/api/v1/ops/readability/upkeep-registry");
        assertThat(response.auditState())
                .isEqualTo("readability-upkeep-audit-registry-active-v1788");
        assertThat(response.topicCount()).isEqualTo(5);
        assertThat(response.routeServiceTestMapCount()).isEqualTo(3);
        assertThat(response.rootPackagePressureCount()).isEqualTo(4);
        assertThat(response.boundaryRuleCount()).isEqualTo(8);
        assertThat(response.deniedBoundaryRuleCount()).isEqualTo(8);
        assertThat(response.verificationStepCount()).isEqualTo(8);
        assertThat(response.markdownSectionCount()).isEqualTo(5);
        assertThat(response.status()).isEqualTo("passed");
    }

    @Test
    void emitsAuditChecks() {
        var response = ReadabilityUpkeepAuditTestSupport.audit();

        assertThat(response.checks())
                .contains(
                        "readability-upkeep-audit-docs-root-docs/ops",
                        "readability-upkeep-audit-source-registry-/api/v1/ops/readability/upkeep-registry",
                        "readability-upkeep-audit-route-service-test-map-present",
                        "readability-upkeep-audit-root-package-pressure-present",
                        "readability-upkeep-audit-no-migration-now",
                        "readability-upkeep-audit-no-write-routing",
                        "readability-upkeep-audit-no-credential-value",
                        "readability-upkeep-audit-no-upstream-autostart"
                );
        assertThat(response.routeServiceTestMaps())
                .extracting(ReadabilityUpkeepAuditResponse.RouteServiceTestMap::route)
                .contains("/api/v1/ops/readability/upkeep-audit");
    }
}
