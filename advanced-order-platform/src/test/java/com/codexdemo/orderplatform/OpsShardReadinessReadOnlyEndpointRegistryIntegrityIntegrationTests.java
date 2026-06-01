package com.codexdemo.orderplatform;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
        "order.expiration.enabled=false",
        "outbox.publisher.enabled=false"
})
@AutoConfigureMockMvc
class OpsShardReadinessReadOnlyEndpointRegistryIntegrityIntegrationTests
        extends OpsOverviewIntegrationTestSupport {

    @Test
    void opsShardReadinessReadOnlyEndpointRegistryIntegrityReturnsReceipt()
            throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/read-only-endpoint-registry-integrity"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v184"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.pairCount").value(23))
                .andExpect(jsonPath("$.liveEndpointCount").value(23))
                .andExpect(jsonPath("$.fixtureEndpointCount").value(23))
                .andExpect(jsonPath("$.pairCountsAligned").value(true))
                .andExpect(jsonPath("$.liveEndpointsDistinct").value(true))
                .andExpect(jsonPath("$.fixtureEndpointsDistinct").value(true))
                .andExpect(jsonPath("$.endpointRegistryIncludesIntegrity").value(true))
                .andExpect(jsonPath("$.fixtureRegistryIncludesIntegrity").value(true))
                .andExpect(jsonPath("$.nodeMayStartOrStopJavaOrMiniKv").value(false))
                .andExpect(jsonPath("$.receiptId")
                        .value("java-shard-readiness-read-only-endpoint-registry-integrity-receipt-v184"))
                .andExpect(jsonPath("$.verificationChecks[0]").value("endpoint-pairs-count:23"))
                .andExpect(jsonPath("$.evidencePath")
                        .value("e/184/evidence/"
                                + "java-shard-readiness-read-only-endpoint-registry-integrity-v184.json"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void staticShardReadinessReadOnlyEndpointRegistryIntegrityFixtureMatchesContractFields()
            throws Exception {
        mockMvc.perform(get(
                        "/contracts/java-shard-readiness-read-only-endpoint-registry-integrity-v184.fixture.json"
                ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v184"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.pairCount").value(23))
                .andExpect(jsonPath("$.liveEndpointCount").value(23))
                .andExpect(jsonPath("$.fixtureEndpointCount").value(23))
                .andExpect(jsonPath("$.pairCountsAligned").value(true))
                .andExpect(jsonPath("$.receiptId")
                        .value("java-shard-readiness-read-only-endpoint-registry-integrity-receipt-v184"))
                .andExpect(jsonPath("$.status").value("passed"));
    }
}
