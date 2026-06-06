package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueDraftAssuranceServiceTests {

    @Test
    void buildsBlockedReasonLedgerWithImportStillLocked() {
        OpsShardReadinessOperatorEvidenceValueDraftResponse ledger =
                new OpsShardReadinessOperatorEvidenceValueDraftBlockedReasonLedgerService().ledger();

        assertThat(ledger.version()).isEqualTo("Java v622");
        assertThat(ledger.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/operator-evidence-value-draft-blocked-reason-ledger");
        assertThat(ledger.profile()).isEqualTo(
                "java-shard-readiness-operator-evidence-value-draft-blocked-reason-ledger.v1");
        assertThat(ledger.readyForOperatorEvidenceValueDraft()).isTrue();
        assertThat(ledger.actualValueState()).isEqualTo("not-supplied");
        assertThat(ledger.readyForEvidenceImport()).isFalse();
        assertThat(ledger.readyForProductionExecution()).isFalse();
        assertThat(ledger.slotCount()).isEqualTo(4);
        assertThat(ledger.slots())
                .extracting(OpsShardReadinessOperatorEvidenceValueDraftResponse.DraftSlot::code)
                .containsExactly(
                        "VALUE_DRAFT_22_PREVIEW_WINDOW_SCOPE",
                        "VALUE_DRAFT_23_REVIEW_PACKAGE_SCOPE",
                        "VALUE_DRAFT_24_OPERATOR_SLOT_SCOPE",
                        "VALUE_DRAFT_25_CLOSEOUT_LOCKS_HELD"
                );
        assertThat(ledger.checks()).contains(
                "value-draft-blocked-reason-slice-22-25",
                "value-draft-blocked-reason-draft-ready-not-import-ready",
                "value-draft-blocked-reason-production-execution-locked"
        );
        assertThat(ledger.status()).isEqualTo("passed");
    }

    @Test
    void buildsDigestBlueprintWithoutValueHash() {
        OpsShardReadinessOperatorEvidenceValueDraftResponse blueprint =
                new OpsShardReadinessOperatorEvidenceValueDraftDigestBlueprintService().blueprint();

        assertThat(blueprint.version()).isEqualTo("Java v624");
        assertThat(blueprint.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/operator-evidence-value-draft-digest-blueprint");
        assertThat(blueprint.profile()).isEqualTo(
                "java-shard-readiness-operator-evidence-value-draft-digest-blueprint.v1");
        assertThat(blueprint.readyForOperatorEvidenceValueDraft()).isTrue();
        assertThat(blueprint.actualValueState()).isEqualTo("not-supplied");
        assertThat(blueprint.readyForEvidenceImport()).isFalse();
        assertThat(blueprint.slotCount()).isEqualTo(25);
        assertThat(blueprint.passedSlotCount()).isEqualTo(25);
        assertThat(blueprint.checks()).contains(
                "value-draft-digest-blueprint-slot-count-25",
                "value-draft-digest-blueprint-no-value-hash",
                "value-draft-digest-blueprint-lock-flags-covered"
        );
        assertThat(blueprint.status()).isEqualTo("passed");
    }

    @Test
    void buildsRouteProfileSummaryWithGetOnlyRoutes() {
        OpsShardReadinessOperatorEvidenceValueDraftResponse summary =
                new OpsShardReadinessOperatorEvidenceValueDraftRouteProfileSummaryService().summary();

        assertThat(summary.version()).isEqualTo("Java v626");
        assertThat(summary.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/operator-evidence-value-draft-route-profile-summary");
        assertThat(summary.profile()).isEqualTo(
                "java-shard-readiness-operator-evidence-value-draft-route-profile-summary.v1");
        assertThat(summary.readyForOperatorEvidenceValueDraft()).isTrue();
        assertThat(summary.actualValueState()).isEqualTo("not-supplied");
        assertThat(summary.readyForEvidenceImport()).isFalse();
        assertThat(summary.slotCount()).isEqualTo(4);
        assertThat(summary.checks()).contains(
                "value-draft-route-profile-foundation-routes-6",
                "value-draft-route-profile-assurance-routes-6",
                "value-draft-route-profile-get-only"
        );
        assertThat(summary.status()).isEqualTo("passed");
    }

    @Test
    void buildsArchivePlanWithoutFileWrites() {
        OpsShardReadinessOperatorEvidenceValueDraftResponse plan =
                new OpsShardReadinessOperatorEvidenceValueDraftArchivePlanService().plan();

        assertThat(plan.version()).isEqualTo("Java v628");
        assertThat(plan.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/operator-evidence-value-draft-archive-plan");
        assertThat(plan.profile()).isEqualTo(
                "java-shard-readiness-operator-evidence-value-draft-archive-plan.v1");
        assertThat(plan.readyForOperatorEvidenceValueDraft()).isTrue();
        assertThat(plan.actualValueState()).isEqualTo("not-supplied");
        assertThat(plan.readyForEvidenceImport()).isFalse();
        assertThat(plan.slotCount()).isEqualTo(4);
        assertThat(plan.checks()).contains(
                "value-draft-archive-plan-external-capture",
                "value-draft-archive-plan-no-file-write",
                "value-draft-archive-plan-no-runtime-process"
        );
        assertThat(plan.status()).isEqualTo("passed");
    }

    @Test
    void buildsOperatorHandoffWithoutExecutionApproval() {
        OpsShardReadinessOperatorEvidenceValueDraftResponse handoff =
                new OpsShardReadinessOperatorEvidenceValueDraftOperatorHandoffService().handoff();

        assertThat(handoff.version()).isEqualTo("Java v630");
        assertThat(handoff.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/operator-evidence-value-draft-operator-handoff");
        assertThat(handoff.profile()).isEqualTo(
                "java-shard-readiness-operator-evidence-value-draft-operator-handoff.v1");
        assertThat(handoff.readyForOperatorEvidenceValueDraft()).isTrue();
        assertThat(handoff.actualValueState()).isEqualTo("not-supplied");
        assertThat(handoff.readyForEvidenceImport()).isFalse();
        assertThat(handoff.readyForLiveExecution()).isFalse();
        assertThat(handoff.slotCount()).isEqualTo(5);
        assertThat(handoff.checks()).contains(
                "value-draft-operator-handoff-owner-count-5",
                "value-draft-operator-handoff-no-values",
                "value-draft-operator-handoff-no-execution-approval"
        );
        assertThat(handoff.status()).isEqualTo("passed");
    }
}
