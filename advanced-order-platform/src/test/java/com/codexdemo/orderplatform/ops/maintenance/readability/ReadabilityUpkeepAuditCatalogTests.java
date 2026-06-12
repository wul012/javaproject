package com.codexdemo.orderplatform.ops.maintenance.readability;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ReadabilityUpkeepAuditCatalogTests {

    @Test
    void catalogsDescribeReadabilityAuditScope() {
        var topics = ReadabilityUpkeepAuditTopicCatalog.topics();
        var routeMaps = ReadabilityRouteServiceTestMapCatalog.routeMaps();
        var pressures = ReadabilityRootPackagePressureCatalog.pressures();
        var boundaries = ReadabilityUpkeepAuditBoundaryCatalog.boundaryRules();
        var verificationSteps = ReadabilityUpkeepAuditVerificationCatalog.verificationSteps();

        assertThat(topics)
                .hasSize(5)
                .allSatisfy(topic -> assertThat(topic.required()).isTrue());
        assertThat(topics)
                .extracting(ReadabilityUpkeepAuditResponse.AuditTopic::code)
                .contains(
                        "route-service-test-map",
                        "root-package-pressure",
                        "registry-template-follow-through",
                        "walkthrough-depth-guard"
                );
        assertThat(routeMaps)
                .hasSize(3)
                .allSatisfy(routeMap -> assertThat(routeMap.readOnly()).isTrue());
        assertThat(routeMaps)
                .extracting(ReadabilityUpkeepAuditResponse.RouteServiceTestMap::route)
                .contains(
                        "/api/v1/ops/readability/upkeep-registry",
                        "/api/v1/ops/readability/upkeep-audit",
                        "docs/ops/route-service-test-map.md"
                );
        assertThat(pressures)
                .hasSize(4)
                .allSatisfy(pressure -> assertThat(pressure.migrationRequiredNow()).isFalse());
        assertThat(pressures)
                .extracting(ReadabilityUpkeepAuditResponse.RootPackagePressure::currentLocation)
                .contains(
                        "com.codexdemo.orderplatform.ops",
                        "com.codexdemo.orderplatform.ops.maintenance.readability"
                );
        assertThat(boundaries)
                .hasSize(8)
                .allSatisfy(boundary -> assertThat(boundary.allowed()).isFalse());
        assertThat(verificationSteps)
                .hasSize(8)
                .allSatisfy(step -> assertThat(step.required()).isTrue());
    }
}
