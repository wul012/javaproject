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
class OpsShardReadinessOperatorEvidenceImportPreflightAssuranceIntegrationTests
        extends OpsOverviewIntegrationTestSupport {

    @Test
    void operatorEvidenceImportPreflightDigestBlueprintReturnsValueFreeDigestPlan() throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/operator-evidence-import-preflight-digest-blueprint"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v597"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.readyForOperatorEvidenceImportPreflight").value(true))
                .andExpect(jsonPath("$.readyForEvidenceImport").value(false))
                .andExpect(jsonPath("$.readyForLiveExecution").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/operator-evidence-import-preflight-digest-blueprint"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-operator-evidence-import-preflight-digest-blueprint.v1"))
                .andExpect(jsonPath("$.sourcePlan").value("Node v886"))
                .andExpect(jsonPath("$.itemCount").value(4))
                .andExpect(jsonPath("$.items[0].name").value("slot-count-digest"))
                .andExpect(jsonPath("$.items[3].name").value("source-plan-digest"))
                .andExpect(jsonPath("$.checks[8]").value("digest-blueprint-does-not-hash-values"))
                .andExpect(jsonPath("$.checks[9]").value("digest-blueprint-covers-lock-flags"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void operatorEvidenceImportPreflightRouteProfileSummaryReturnsGetOnlyProfile() throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/operator-evidence-import-preflight-route-profile-summary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v599"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.readyForOperatorEvidenceImportPreflight").value(true))
                .andExpect(jsonPath("$.readyForEvidenceImport").value(false))
                .andExpect(jsonPath("$.readyForManualEvidenceEntry").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/operator-evidence-import-preflight-route-profile-summary"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-operator-evidence-import-preflight-route-profile-summary.v1"))
                .andExpect(jsonPath("$.itemCount").value(4))
                .andExpect(jsonPath("$.items[0].name").value("foundation-route-profile"))
                .andExpect(jsonPath("$.items[3].name").value("get-only-boundary"))
                .andExpect(jsonPath("$.checks[8]").value("import-preflight-route-profile-foundation-routes-6"))
                .andExpect(jsonPath("$.checks[10]").value("import-preflight-route-profile-get-only"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void operatorEvidenceImportPreflightArchivePlanReturnsExternalCapturePlan() throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/operator-evidence-import-preflight-archive-plan"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v601"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.readyForOperatorEvidenceImportPreflight").value(true))
                .andExpect(jsonPath("$.readyForEvidenceImport").value(false))
                .andExpect(jsonPath("$.readyForProductionExecution").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/operator-evidence-import-preflight-archive-plan"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-operator-evidence-import-preflight-archive-plan.v1"))
                .andExpect(jsonPath("$.itemCount").value(4))
                .andExpect(jsonPath("$.items[0].name").value("json-capture-plan"))
                .andExpect(jsonPath("$.items[3].name").value("no-file-write"))
                .andExpect(jsonPath("$.checks[8]").value("import-preflight-archive-plan-external-capture"))
                .andExpect(jsonPath("$.checks[9]").value("import-preflight-archive-plan-no-file-write"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void operatorEvidenceImportPreflightOperatorHandoffReturnsSeparatedOwners() throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/operator-evidence-import-preflight-operator-handoff"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v603"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.readyForOperatorEvidenceImportPreflight").value(true))
                .andExpect(jsonPath("$.readyForEvidenceImport").value(false))
                .andExpect(jsonPath("$.readyForLiveExecution").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/operator-evidence-import-preflight-operator-handoff"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-operator-evidence-import-preflight-operator-handoff.v1"))
                .andExpect(jsonPath("$.itemCount").value(5))
                .andExpect(jsonPath("$.items[0].name").value("preflight-owner"))
                .andExpect(jsonPath("$.items[4].name").value("runtime-boundary-owner"))
                .andExpect(jsonPath("$.checks[9]").value("import-preflight-operator-handoff-no-values"))
                .andExpect(jsonPath("$.checks[10]").value("import-preflight-operator-handoff-no-execution-approval"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void operatorEvidenceImportPreflightCiBudgetReturnsFocusedGatePlan() throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/operator-evidence-import-preflight-ci-budget"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v605"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.readyForOperatorEvidenceImportPreflight").value(true))
                .andExpect(jsonPath("$.readyForEvidenceImport").value(false))
                .andExpect(jsonPath("$.readyForProductionExecution").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/operator-evidence-import-preflight-ci-budget"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-operator-evidence-import-preflight-ci-budget.v1"))
                .andExpect(jsonPath("$.itemCount").value(5))
                .andExpect(jsonPath("$.items[0].name").value("support-unit-test"))
                .andExpect(jsonPath("$.items[4].name").value("full-maven-gate"))
                .andExpect(jsonPath("$.checks[8]").value("import-preflight-ci-budget-focused-first"))
                .andExpect(jsonPath("$.checks[10]").value("import-preflight-ci-budget-full-maven-last"))
                .andExpect(jsonPath("$.status").value("passed"));
    }
}
