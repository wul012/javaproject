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
class OpsShardReadinessPrototypeAuditDigestIntegrationTests
        extends OpsOverviewIntegrationTestSupport {

    @Test
    void opsShardReadinessPrototypeAuditDigestReturnsStableDigest()
            throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/prototype-audit-digest"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.version").value("Java v425"))
                .andExpect(jsonPath("$.entryKey").value("prototype-audit-digest"))
                .andExpect(jsonPath("$.checks[0]").value("digest-covers-entry-key"))
                .andExpect(jsonPath("$.digestValue").exists())
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.status").value("passed"));
    }
}
