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
class OpsShardReadinessPrototypeCloseoutIntegrationTests
        extends OpsOverviewIntegrationTestSupport {

    @Test
    void opsShardReadinessPrototypeCloseoutReturnsFinalHandoffEvidence()
            throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/prototype-closeout"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.version").value("Java v427"))
                .andExpect(jsonPath("$.entryKey").value("prototype-closeout"))
                .andExpect(jsonPath("$.checks[0]").value("closeout-entry-count-10"))
                .andExpect(jsonPath("$.checks[2]").value("closeout-latest-entry-v427"))
                .andExpect(jsonPath("$.checks[4]").value("closeout-ready-for-node-consumer-gate"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.status").value("passed"));
    }
}
