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
class OpsShardReadinessManualEvidenceWorksheetAssuranceIntegrationTests
        extends OpsOverviewIntegrationTestSupport {

    @Test
    void manualEvidenceWorksheetImporterPreflightReturnsBlockedImportStatus() throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/manual-evidence-worksheet-importer-preflight"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v572"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.readyForOperatorEntryWorksheet").value(true))
                .andExpect(jsonPath("$.readyForManualEvidenceEntry").value(false))
                .andExpect(jsonPath("$.readyForLiveExecution").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/manual-evidence-worksheet-importer-preflight"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-manual-evidence-worksheet-importer-preflight.v1"))
                .andExpect(jsonPath("$.itemCount").value(5))
                .andExpect(jsonPath("$.items[0].name").value("worksheet-structure-ready"))
                .andExpect(jsonPath("$.items[4].name").value("import-blocker"))
                .andExpect(jsonPath("$.checks[9]").value("importer-preflight-import-execution-blocked"))
                .andExpect(jsonPath("$.status").value("passed"));
    }
}
