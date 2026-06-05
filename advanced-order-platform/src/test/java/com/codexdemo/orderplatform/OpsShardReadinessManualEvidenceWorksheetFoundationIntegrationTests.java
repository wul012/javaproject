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
class OpsShardReadinessManualEvidenceWorksheetFoundationIntegrationTests
        extends OpsOverviewIntegrationTestSupport {

    @Test
    void manualEvidenceWorksheetCatalogReturnsReadOnlyCatalog() throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/manual-evidence-worksheet-catalog"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v560"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.readyForOperatorEntryWorksheet").value(true))
                .andExpect(jsonPath("$.readyForManualEvidenceEntry").value(false))
                .andExpect(jsonPath("$.readyForLiveExecution").value(false))
                .andExpect(jsonPath("$.readyForProductionExecution").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/manual-evidence-worksheet-catalog"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-manual-evidence-worksheet-catalog.v1"))
                .andExpect(jsonPath("$.sourcePlan").value("Node v861"))
                .andExpect(jsonPath("$.itemCount").value(4))
                .andExpect(jsonPath("$.items[0].name").value("source-review-package"))
                .andExpect(jsonPath("$.items[3].name").value("fail-closed-flags"))
                .andExpect(jsonPath("$.checks[7]").value("worksheet-catalog-slot-count-25"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void manualEvidenceWorksheetSlotTemplateReturnsBlankTemplate() throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/manual-evidence-worksheet-slot-template"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v562"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.readyForOperatorEntryWorksheet").value(true))
                .andExpect(jsonPath("$.readyForManualEvidenceEntry").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/manual-evidence-worksheet-slot-template"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-manual-evidence-worksheet-slot-template.v1"))
                .andExpect(jsonPath("$.itemCount").value(4))
                .andExpect(jsonPath("$.items[0].name").value("slot-identity"))
                .andExpect(jsonPath("$.items[3].name").value("validation-hint"))
                .andExpect(jsonPath("$.checks[8]").value("slot-template-no-secret-placeholder"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void manualEvidenceWorksheetValidationRulesReturnsFailClosedRules() throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/manual-evidence-worksheet-validation-rules"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v564"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.readyForOperatorEntryWorksheet").value(true))
                .andExpect(jsonPath("$.readyForManualEvidenceEntry").value(false))
                .andExpect(jsonPath("$.readyForLiveExecution").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/manual-evidence-worksheet-validation-rules"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-manual-evidence-worksheet-validation-rules.v1"))
                .andExpect(jsonPath("$.itemCount").value(4))
                .andExpect(jsonPath("$.items[0].name").value("required-slot-id"))
                .andExpect(jsonPath("$.items[2].name").value("rejected-runtime-value"))
                .andExpect(jsonPath("$.checks[8]").value("validation-rules-reject-runtime-payload"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void manualEvidenceWorksheetRedactionRulesReturnsSecretBoundaries() throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/manual-evidence-worksheet-redaction-rules"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v566"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.readyForOperatorEntryWorksheet").value(true))
                .andExpect(jsonPath("$.readyForManualEvidenceEntry").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/manual-evidence-worksheet-redaction-rules"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-manual-evidence-worksheet-redaction-rules.v1"))
                .andExpect(jsonPath("$.itemCount").value(4))
                .andExpect(jsonPath("$.items[0].name").value("credential-value-ban"))
                .andExpect(jsonPath("$.items[1].name").value("raw-endpoint-ban"))
                .andExpect(jsonPath("$.checks[7]").value("redaction-rules-ban-credential-values"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void manualEvidenceWorksheetMissingValuePolicyReturnsBlankStatePolicy() throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/manual-evidence-worksheet-missing-value-policy"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v568"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.readyForOperatorEntryWorksheet").value(true))
                .andExpect(jsonPath("$.readyForManualEvidenceEntry").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/manual-evidence-worksheet-missing-value-policy"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-manual-evidence-worksheet-missing-value-policy.v1"))
                .andExpect(jsonPath("$.itemCount").value(4))
                .andExpect(jsonPath("$.items[0].name").value("missing-manual-value"))
                .andExpect(jsonPath("$.items[3].name").value("missing-import-source"))
                .andExpect(jsonPath("$.checks[9]").value("missing-value-policy-blocks-importer"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void manualEvidenceWorksheetTargetScopeRegistryReturnsPreviewScopes() throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/manual-evidence-worksheet-target-scope-registry"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v570"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.readyForOperatorEntryWorksheet").value(true))
                .andExpect(jsonPath("$.readyForProductionExecution").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/manual-evidence-worksheet-target-scope-registry"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-manual-evidence-worksheet-target-scope-registry.v1"))
                .andExpect(jsonPath("$.itemCount").value(4))
                .andExpect(jsonPath("$.items[0].name").value("order-read-model"))
                .andExpect(jsonPath("$.items[3].name").value("operator-entry-slot"))
                .andExpect(jsonPath("$.checks[8]").value("target-scope-registry-no-write-routing"))
                .andExpect(jsonPath("$.status").value("passed"));
    }
}
