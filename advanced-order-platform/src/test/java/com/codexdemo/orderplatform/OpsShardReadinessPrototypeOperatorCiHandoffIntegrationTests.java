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
class OpsShardReadinessPrototypeOperatorCiHandoffIntegrationTests
        extends OpsOverviewIntegrationTestSupport {

    @Test
    void opsShardReadinessPrototypeOperatorCiHandoffReturnsRegularGateOrder()
            throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/prototype-operator-ci-handoff"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.version").value("Java v423"))
                .andExpect(jsonPath("$.entryKey").value("prototype-operator-ci-handoff"))
                .andExpect(jsonPath("$.checks[0]").value("ci-step-focused-service-tests"))
                .andExpect(jsonPath("$.checks[1]").value("ci-step-controller-route-tests"))
                .andExpect(jsonPath("$.checks[2]").value("ci-step-full-maven-test"))
                .andExpect(jsonPath("$.checks[4]").value("ci-step-no-owned-process-left-running"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.status").value("passed"));
    }
}
