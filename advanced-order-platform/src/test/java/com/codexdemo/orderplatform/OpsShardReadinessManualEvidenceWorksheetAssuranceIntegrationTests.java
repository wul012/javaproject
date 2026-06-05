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

    @Test
    void manualEvidenceWorksheetRouteProfileSummaryReturnsGetOnlyProfile() throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/manual-evidence-worksheet-route-profile-summary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v574"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.readyForOperatorEntryWorksheet").value(true))
                .andExpect(jsonPath("$.readyForManualEvidenceEntry").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/manual-evidence-worksheet-route-profile-summary"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-manual-evidence-worksheet-route-profile-summary.v1"))
                .andExpect(jsonPath("$.itemCount").value(4))
                .andExpect(jsonPath("$.items[0].name").value("foundation-route-profile"))
                .andExpect(jsonPath("$.items[3].name").value("route-boundary-profile"))
                .andExpect(jsonPath("$.checks[9]").value("route-profile-summary-get-only"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void manualEvidenceWorksheetArchivePlanReturnsExternalCapturePlan() throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/manual-evidence-worksheet-archive-plan"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v576"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.readyForOperatorEntryWorksheet").value(true))
                .andExpect(jsonPath("$.readyForManualEvidenceEntry").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/manual-evidence-worksheet-archive-plan"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-manual-evidence-worksheet-archive-plan.v1"))
                .andExpect(jsonPath("$.itemCount").value(4))
                .andExpect(jsonPath("$.items[0].name").value("route-json-capture"))
                .andExpect(jsonPath("$.items[3].name").value("no-file-write"))
                .andExpect(jsonPath("$.checks[8]").value("archive-plan-does-not-write-files"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void manualEvidenceWorksheetOperatorHandoffReturnsSeparatedOwners() throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/manual-evidence-worksheet-operator-handoff"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v578"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.readyForOperatorEntryWorksheet").value(true))
                .andExpect(jsonPath("$.readyForManualEvidenceEntry").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/manual-evidence-worksheet-operator-handoff"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-manual-evidence-worksheet-operator-handoff.v1"))
                .andExpect(jsonPath("$.itemCount").value(5))
                .andExpect(jsonPath("$.items[0].name").value("worksheet-owner"))
                .andExpect(jsonPath("$.items[4].name").value("runtime-boundary-owner"))
                .andExpect(jsonPath("$.checks[9]").value("operator-handoff-no-runtime-approval"))
                .andExpect(jsonPath("$.status").value("passed"));
    }
}
