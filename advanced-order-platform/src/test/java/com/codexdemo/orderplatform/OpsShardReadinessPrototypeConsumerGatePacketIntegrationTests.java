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
class OpsShardReadinessPrototypeConsumerGatePacketIntegrationTests
        extends OpsOverviewIntegrationTestSupport {

    @Test
    void opsShardReadinessPrototypeConsumerGatePacketReturnsFailClosedRules()
            throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/prototype-consumer-gate-packet"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.version").value("Java v421"))
                .andExpect(jsonPath("$.entryKey").value("prototype-consumer-gate-packet"))
                .andExpect(jsonPath("$.checks[0]").value("consumer-must-check-contract-name"))
                .andExpect(jsonPath("$.checks[2]").value("consumer-must-check-executionAllowed-false"))
                .andExpect(jsonPath("$.checks[4]").value("consumer-must-fail-closed-on-missing-evidence"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.status").value("passed"));
    }
}
