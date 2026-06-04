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
class OpsShardReadinessPrototypeFixtureEchoIntegrationTests
        extends OpsOverviewIntegrationTestSupport {

    @Test
    void opsShardReadinessPrototypeFixtureEchoReturnsReadOnlyEcho()
            throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/prototype-fixture-echo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v411"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/prototype-fixture-echo"))
                .andExpect(jsonPath("$.entryKey").value("prototype-fixture-echo"))
                .andExpect(jsonPath("$.contractName").value("shard-readiness.v1"))
                .andExpect(jsonPath("$.shardEnabled").value(false))
                .andExpect(jsonPath("$.shardCount").value(0))
                .andExpect(jsonPath("$.slotCount").value(0))
                .andExpect(jsonPath("$.routingMode").value("fixture"))
                .andExpect(jsonPath("$.rootReadinessVersion").value("Java v153"))
                .andExpect(jsonPath("$.echoVersion").value("Java v174"))
                .andExpect(jsonPath("$.routeCleanupCloseoutVersion").value("Java v408"))
                .andExpect(jsonPath("$.digestValue").exists())
                .andExpect(jsonPath("$.status").value("passed"));
    }
}
