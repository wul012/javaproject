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
class OpsShardReadinessPrototypeReadWindowHandoffIntegrationTests
        extends OpsOverviewIntegrationTestSupport {

    @Test
    void opsShardReadinessPrototypeReadWindowHandoffReturnsWindowRules()
            throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/prototype-read-window-handoff"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.version").value("Java v419"))
                .andExpect(jsonPath("$.entryKey").value("prototype-read-window-handoff"))
                .andExpect(jsonPath("$.checks[0]").value("java-health-read-window-required"))
                .andExpect(jsonPath("$.checks[2]").value("java-shard-readiness-read-window-required"))
                .andExpect(jsonPath("$.checks[4]").value("node-upstream-actions-must-remain-disabled"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.status").value("passed"));
    }
}
