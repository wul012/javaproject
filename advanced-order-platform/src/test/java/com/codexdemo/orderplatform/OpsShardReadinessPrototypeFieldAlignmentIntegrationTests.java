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
class OpsShardReadinessPrototypeFieldAlignmentIntegrationTests
        extends OpsOverviewIntegrationTestSupport {

    @Test
    void opsShardReadinessPrototypeFieldAlignmentReturnsFrozenFields()
            throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/prototype-field-alignment"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.version").value("Java v413"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.entryKey").value("prototype-field-alignment"))
                .andExpect(jsonPath("$.requiredFields[0]").value("project"))
                .andExpect(jsonPath("$.requiredFields[4]").value("shardEnabled"))
                .andExpect(jsonPath("$.requiredFields[9]").value("status"))
                .andExpect(jsonPath("$.checks[0]").value("field-project-present"))
                .andExpect(jsonPath("$.checks[9]").value("field-status-passed"))
                .andExpect(jsonPath("$.status").value("passed"));
    }
}
