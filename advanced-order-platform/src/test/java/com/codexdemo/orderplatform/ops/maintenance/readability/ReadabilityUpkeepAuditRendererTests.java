package com.codexdemo.orderplatform.ops.maintenance.readability;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ReadabilityUpkeepAuditRendererTests {

    @Test
    void rendersStableAuditMarkdownSections() {
        var response = ReadabilityUpkeepAuditTestSupport.audit();

        assertThat(response.markdownSections())
                .extracting(ReadabilityUpkeepAuditResponse.MarkdownSection::heading)
                .containsExactly(
                        "Audit Topics",
                        "Route Service Test Maps",
                        "Root Package Pressure",
                        "Boundary Rules",
                        "Verification Steps"
                );
        assertThat(response.markdownSections().get(0).lines())
                .anySatisfy(line -> assertThat(line)
                        .contains("route-service-test-map",
                                "docs/ops/route-service-test-map.md"));
        assertThat(response.markdownSections().get(1).lines())
                .anySatisfy(line -> assertThat(line)
                        .contains("/api/v1/ops/readability/upkeep-audit",
                                "ReadabilityUpkeepAuditService"));
        assertThat(response.markdownSections().get(2).lines())
                .anySatisfy(line -> assertThat(line)
                        .contains("readability-upkeep",
                                "migrationRequiredNow=false"));
        assertThat(response.markdownSections().get(3).lines())
                .anySatisfy(line -> assertThat(line)
                        .contains("no-credential-value", "allowed=false"));
        assertThat(response.markdownSections().get(4).lines())
                .anySatisfy(line -> assertThat(line)
                        .contains("ReadabilityUpkeepAuditServiceTests", "required=true"));
    }
}
