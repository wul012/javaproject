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
class OpsShardReadinessPrototypeRouteCleanupBridgeIntegrationTests
        extends OpsOverviewIntegrationTestSupport {

    @Test
    void opsShardReadinessPrototypeRouteCleanupBridgeReturnsCloseoutLinkage()
            throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/prototype-route-cleanup-bridge"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.version").value("Java v417"))
                .andExpect(jsonPath("$.entryKey").value("prototype-route-cleanup-bridge"))
                .andExpect(jsonPath("$.routeCleanupCloseoutVersion").value("Java v408"))
                .andExpect(jsonPath("$.checks[0]").value("route-cleanup-v408-closeout-present"))
                .andExpect(jsonPath("$.checks[3]").value("route-cleanup-does-not-enable-active-shard-router"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.status").value("passed"));
    }
}
