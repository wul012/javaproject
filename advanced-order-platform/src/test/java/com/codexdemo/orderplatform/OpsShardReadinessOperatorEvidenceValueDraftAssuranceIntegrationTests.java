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
class OpsShardReadinessOperatorEvidenceValueDraftAssuranceIntegrationTests
        extends OpsOverviewIntegrationTestSupport {

    @Test
    void operatorEvidenceValueDraftBlockedReasonLedgerKeepsImportLocked() throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/operator-evidence-value-draft-blocked-reason-ledger"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v622"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.readyForOperatorEvidenceValueDraft").value(true))
                .andExpect(jsonPath("$.actualValueState").value("not-supplied"))
                .andExpect(jsonPath("$.readyForEvidenceImport").value(false))
                .andExpect(jsonPath("$.readyForProductionExecution").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/operator-evidence-value-draft-blocked-reason-ledger"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-operator-evidence-value-draft-blocked-reason-ledger.v1"))
                .andExpect(jsonPath("$.slotCount").value(4))
                .andExpect(jsonPath("$.slots[0].code").value("VALUE_DRAFT_22_PREVIEW_WINDOW_SCOPE"))
                .andExpect(jsonPath("$.slots[3].code").value("VALUE_DRAFT_25_CLOSEOUT_LOCKS_HELD"))
                .andExpect(jsonPath("$.checks[10]").value("value-draft-blocked-reason-draft-ready-not-import-ready"))
                .andExpect(jsonPath("$.checks[11]").value("value-draft-blocked-reason-production-execution-locked"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void operatorEvidenceValueDraftDigestBlueprintReturnsValueFreeDigestPlan() throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/operator-evidence-value-draft-digest-blueprint"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v624"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.readyForOperatorEvidenceValueDraft").value(true))
                .andExpect(jsonPath("$.actualValueState").value("not-supplied"))
                .andExpect(jsonPath("$.readyForEvidenceImport").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/operator-evidence-value-draft-digest-blueprint"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-operator-evidence-value-draft-digest-blueprint.v1"))
                .andExpect(jsonPath("$.slotCount").value(25))
                .andExpect(jsonPath("$.slots[0].code").value("VALUE_DRAFT_01_SOURCE_WORKSHEET_CLOSEOUT"))
                .andExpect(jsonPath("$.slots[24].code").value("VALUE_DRAFT_25_CLOSEOUT_LOCKS_HELD"))
                .andExpect(jsonPath("$.checks[9]").value("value-draft-digest-blueprint-slot-count-25"))
                .andExpect(jsonPath("$.checks[10]").value("value-draft-digest-blueprint-no-value-hash"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void operatorEvidenceValueDraftRouteProfileSummaryReturnsGetOnlyProfile() throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/operator-evidence-value-draft-route-profile-summary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v626"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.readyForOperatorEvidenceValueDraft").value(true))
                .andExpect(jsonPath("$.actualValueState").value("not-supplied"))
                .andExpect(jsonPath("$.readyForEvidenceImport").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/operator-evidence-value-draft-route-profile-summary"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-operator-evidence-value-draft-route-profile-summary.v1"))
                .andExpect(jsonPath("$.slotCount").value(4))
                .andExpect(jsonPath("$.checks[9]").value("value-draft-route-profile-foundation-routes-6"))
                .andExpect(jsonPath("$.checks[10]").value("value-draft-route-profile-assurance-routes-6"))
                .andExpect(jsonPath("$.checks[11]").value("value-draft-route-profile-get-only"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void operatorEvidenceValueDraftArchivePlanReturnsExternalCapturePlan() throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/operator-evidence-value-draft-archive-plan"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v628"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.readyForOperatorEvidenceValueDraft").value(true))
                .andExpect(jsonPath("$.actualValueState").value("not-supplied"))
                .andExpect(jsonPath("$.readyForEvidenceImport").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/operator-evidence-value-draft-archive-plan"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-operator-evidence-value-draft-archive-plan.v1"))
                .andExpect(jsonPath("$.slotCount").value(4))
                .andExpect(jsonPath("$.checks[9]").value("value-draft-archive-plan-external-capture"))
                .andExpect(jsonPath("$.checks[10]").value("value-draft-archive-plan-no-file-write"))
                .andExpect(jsonPath("$.checks[11]").value("value-draft-archive-plan-no-runtime-process"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void operatorEvidenceValueDraftOperatorHandoffReturnsNoApprovalHandoff() throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/operator-evidence-value-draft-operator-handoff"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v630"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.readyForOperatorEvidenceValueDraft").value(true))
                .andExpect(jsonPath("$.actualValueState").value("not-supplied"))
                .andExpect(jsonPath("$.readyForEvidenceImport").value(false))
                .andExpect(jsonPath("$.readyForLiveExecution").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/operator-evidence-value-draft-operator-handoff"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-operator-evidence-value-draft-operator-handoff.v1"))
                .andExpect(jsonPath("$.slotCount").value(5))
                .andExpect(jsonPath("$.checks[10]").value("value-draft-operator-handoff-no-values"))
                .andExpect(jsonPath("$.checks[11]").value("value-draft-operator-handoff-no-execution-approval"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void operatorEvidenceValueDraftCloseoutReturnsLockedTwentyFiveSlotPackage() throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/operator-evidence-value-draft-closeout"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v632"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.readyForOperatorEvidenceValueDraft").value(true))
                .andExpect(jsonPath("$.actualValueState").value("not-supplied"))
                .andExpect(jsonPath("$.readyForEvidenceImport").value(false))
                .andExpect(jsonPath("$.readyForManualEvidenceEntry").value(false))
                .andExpect(jsonPath("$.readyForLiveExecution").value(false))
                .andExpect(jsonPath("$.readyForProductionExecution").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/operator-evidence-value-draft-closeout"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-operator-evidence-value-draft-closeout.v1"))
                .andExpect(jsonPath("$.slotCount").value(25))
                .andExpect(jsonPath("$.slots[0].code").value("VALUE_DRAFT_01_SOURCE_WORKSHEET_CLOSEOUT"))
                .andExpect(jsonPath("$.slots[24].code").value("VALUE_DRAFT_25_CLOSEOUT_LOCKS_HELD"))
                .andExpect(jsonPath("$.checks[9]").value("value-draft-closeout-versions-v609-v633"))
                .andExpect(jsonPath("$.checks[10]").value("value-draft-closeout-slot-count-25"))
                .andExpect(jsonPath("$.checks[12]").value("value-draft-closeout-import-remains-locked"))
                .andExpect(jsonPath("$.status").value("passed"));
    }
}
