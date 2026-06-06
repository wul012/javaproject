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
}
