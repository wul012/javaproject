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
class OpsShardReadinessOperatorEvidenceImportPreflightFoundationIntegrationTests
        extends OpsOverviewIntegrationTestSupport {

    @Test
    void operatorEvidenceImportPreflightCatalogReturnsReadOnlyCatalog() throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/operator-evidence-import-preflight-catalog"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v585"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.readyForOperatorEvidenceImportPreflight").value(true))
                .andExpect(jsonPath("$.readyForEvidenceImport").value(false))
                .andExpect(jsonPath("$.readyForManualEvidenceEntry").value(false))
                .andExpect(jsonPath("$.readyForLiveExecution").value(false))
                .andExpect(jsonPath("$.readyForProductionExecution").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/operator-evidence-import-preflight-catalog"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-operator-evidence-import-preflight-catalog.v1"))
                .andExpect(jsonPath("$.sourcePlan").value("Node v886"))
                .andExpect(jsonPath("$.itemCount").value(5))
                .andExpect(jsonPath("$.items[0].name").value("source-worksheet-closeout"))
                .andExpect(jsonPath("$.items[4].name").value("no-value-ingestion"))
                .andExpect(jsonPath("$.checks[8]").value("import-preflight-catalog-slot-count-25"))
                .andExpect(jsonPath("$.checks[10]").value("import-preflight-catalog-imports-no-values"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void operatorEvidenceImportPreflightSlotNormalizationReturnsBlankValueBoundary() throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/operator-evidence-import-preflight-slot-normalization"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v587"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.readyForOperatorEvidenceImportPreflight").value(true))
                .andExpect(jsonPath("$.readyForEvidenceImport").value(false))
                .andExpect(jsonPath("$.readyForManualEvidenceEntry").value(false))
                .andExpect(jsonPath("$.readyForLiveExecution").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/operator-evidence-import-preflight-slot-normalization"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-operator-evidence-import-preflight-slot-normalization.v1"))
                .andExpect(jsonPath("$.itemCount").value(4))
                .andExpect(jsonPath("$.items[0].name").value("slot-id-normalization"))
                .andExpect(jsonPath("$.items[1].name").value("blank-value-normalization"))
                .andExpect(jsonPath("$.checks[8]").value("slot-normalization-preserves-blank-values"))
                .andExpect(jsonPath("$.checks[10]").value("slot-normalization-does-not-import-values"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void operatorEvidenceImportPreflightImportBlockerMatrixReturnsFailClosedMatrix() throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/operator-evidence-import-preflight-import-blocker-matrix"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v589"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.readyForOperatorEvidenceImportPreflight").value(true))
                .andExpect(jsonPath("$.readyForEvidenceImport").value(false))
                .andExpect(jsonPath("$.readyForLiveExecution").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/operator-evidence-import-preflight-import-blocker-matrix"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-operator-evidence-import-preflight-import-blocker-matrix.v1"))
                .andExpect(jsonPath("$.itemCount").value(5))
                .andExpect(jsonPath("$.items[2].name").value("runtime-payload-blocker"))
                .andExpect(jsonPath("$.items[4].name").value("manual-entry-lock-blocker"))
                .andExpect(jsonPath("$.checks[9]").value("import-blocker-matrix-blocks-runtime-payload"))
                .andExpect(jsonPath("$.checks[10]").value("import-blocker-matrix-keeps-import-locked"))
                .andExpect(jsonPath("$.status").value("passed"));
    }
}
