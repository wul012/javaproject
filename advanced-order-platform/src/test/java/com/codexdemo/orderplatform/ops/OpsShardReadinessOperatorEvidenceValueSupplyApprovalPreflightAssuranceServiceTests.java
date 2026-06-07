package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightAssuranceServiceTests {

    @Test
    void buildsValueRejectionWithoutAcceptingMalformedMissingOrTypedValueBodies() {
        OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse rejection =
                new OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightValueRejectionService()
                        .rejection();

        assertThat(rejection.version()).isEqualTo("Java v698");
        assertThat(rejection.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/operator-evidence-value-supply-approval-preflight-value-rejection");
        assertThat(rejection.profile()).isEqualTo(
                "java-shard-readiness-operator-evidence-value-supply-approval-preflight-value-rejection.v1");
        assertThat(rejection.acceptedValueState()).isEqualTo("not-accepted");
        assertThat(rejection.malformedValueState()).isEqualTo("rejected");
        assertThat(rejection.readyForOperatorValueSubmission()).isFalse();
        assertThat(rejection.readyForEvidenceImport()).isFalse();
        assertThat(rejection.itemCount()).isEqualTo(3);
        assertThat(rejection.policyCount()).isEqualTo(3);
        assertThat(rejection.items())
                .extracting(OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse.ApprovalItem::code)
                .containsExactly(
                        "VALUE_SUPPLY_APPROVAL_PACKET_17_TYPED_VALUE_ENVELOPE_REFERENCE",
                        "VALUE_SUPPLY_APPROVAL_PACKET_18_MALFORMED_VALUE_REJECTION",
                        "VALUE_SUPPLY_APPROVAL_PACKET_19_MISSING_VALUE_REJECTION"
                );
        assertThat(rejection.policies())
                .extracting(OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse.ApprovalPolicy::code)
                .containsExactly(
                        "APPROVAL_PREFLIGHT_POLICY_13_TYPED_VALUE_ENVELOPE_REFERENCE_ONLY",
                        "APPROVAL_PREFLIGHT_POLICY_14_MALFORMED_VALUES_REJECTED",
                        "APPROVAL_PREFLIGHT_POLICY_15_MISSING_VALUES_REJECTED"
                );
        assertThat(rejection.checks()).contains(
                "value-supply-approval-preflight-value-envelope-reference-only",
                "value-supply-approval-preflight-malformed-values-rejected",
                "value-supply-approval-preflight-missing-values-rejected",
                "value-supply-approval-preflight-value-import-still-locked"
        );
        assertThat(rejection.status()).isEqualTo("passed");
    }

    @Test
    void buildsZeroValueLedgerForSuppliedAcceptedAndImportedCounts() {
        OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse ledger =
                new OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightZeroValueLedgerService()
                        .ledger();

        assertThat(ledger.version()).isEqualTo("Java v700");
        assertThat(ledger.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/operator-evidence-value-supply-approval-preflight-zero-value-ledger");
        assertThat(ledger.profile()).isEqualTo(
                "java-shard-readiness-operator-evidence-value-supply-approval-preflight-zero-value-ledger.v1");
        assertThat(ledger.acceptedValueState()).isEqualTo("not-accepted");
        assertThat(ledger.readyForOperatorValueSubmission()).isFalse();
        assertThat(ledger.readyForEvidenceImport()).isFalse();
        assertThat(ledger.readyForProductionExecution()).isFalse();
        assertThat(ledger.itemCount()).isEqualTo(3);
        assertThat(ledger.policyCount()).isEqualTo(1);
        assertThat(ledger.items())
                .extracting(OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse.ApprovalItem::code)
                .containsExactly(
                        "VALUE_SUPPLY_APPROVAL_PACKET_20_ZERO_SUPPLIED_VALUE_COUNT",
                        "VALUE_SUPPLY_APPROVAL_PACKET_21_ZERO_ACCEPTED_VALUE_COUNT",
                        "VALUE_SUPPLY_APPROVAL_PACKET_22_ZERO_IMPORTED_VALUE_COUNT"
                );
        assertThat(ledger.policies().get(0).code())
                .isEqualTo("APPROVAL_PREFLIGHT_POLICY_16_ZERO_VALUE_COUNTS_REQUIRED");
        assertThat(ledger.checks()).contains(
                "value-supply-approval-preflight-zero-supplied-value-count",
                "value-supply-approval-preflight-zero-accepted-value-count",
                "value-supply-approval-preflight-zero-imported-value-count",
                "value-supply-approval-preflight-zero-counts-import-locked"
        );
        assertThat(ledger.status()).isEqualTo("passed");
    }

    @Test
    void buildsCleanupReceiptWithoutWritingFilesOrStartingProcesses() {
        OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse receipt =
                new OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightCleanupReceiptService()
                        .receipt();

        assertThat(receipt.version()).isEqualTo("Java v702");
        assertThat(receipt.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/operator-evidence-value-supply-approval-preflight-cleanup-receipt");
        assertThat(receipt.profile()).isEqualTo(
                "java-shard-readiness-operator-evidence-value-supply-approval-preflight-cleanup-receipt.v1");
        assertThat(receipt.receiptState()).isEqualTo("required-before-import");
        assertThat(receipt.readOnly()).isTrue();
        assertThat(receipt.executionAllowed()).isFalse();
        assertThat(receipt.readyForEvidenceImport()).isFalse();
        assertThat(receipt.itemCount()).isEqualTo(1);
        assertThat(receipt.policyCount()).isEqualTo(1);
        assertThat(receipt.items().get(0).code()).isEqualTo("VALUE_SUPPLY_APPROVAL_PACKET_23_CLEANUP_RECEIPT_ID");
        assertThat(receipt.policies().get(0).code()).isEqualTo("APPROVAL_PREFLIGHT_POLICY_17_CLEANUP_RECEIPT_REQUIRED");
        assertThat(receipt.checks()).contains(
                "value-supply-approval-preflight-cleanup-receipt-id-required",
                "value-supply-approval-preflight-cleanup-receipt-no-file-write",
                "value-supply-approval-preflight-cleanup-receipt-no-process-start",
                "value-supply-approval-preflight-cleanup-receipt-import-still-locked"
        );
        assertThat(receipt.status()).isEqualTo("passed");
    }

    @Test
    void buildsImportFirewallWithoutImportRuntimeOrProductionReadiness() {
        OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse firewall =
                new OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightImportFirewallService()
                        .firewall();

        assertThat(firewall.version()).isEqualTo("Java v704");
        assertThat(firewall.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/operator-evidence-value-supply-approval-preflight-import-firewall");
        assertThat(firewall.profile()).isEqualTo(
                "java-shard-readiness-operator-evidence-value-supply-approval-preflight-import-firewall.v1");
        assertThat(firewall.importState()).isEqualTo("locked");
        assertThat(firewall.readyForEvidenceImport()).isFalse();
        assertThat(firewall.readyForRuntimePayload()).isFalse();
        assertThat(firewall.readyForLiveExecution()).isFalse();
        assertThat(firewall.readyForProductionExecution()).isFalse();
        assertThat(firewall.itemCount()).isEqualTo(5);
        assertThat(firewall.policyCount()).isEqualTo(2);
        assertThat(firewall.items().get(0).code()).isEqualTo("VALUE_SUPPLY_APPROVAL_PACKET_21_ZERO_ACCEPTED_VALUE_COUNT");
        assertThat(firewall.items().get(4).code()).isEqualTo("VALUE_SUPPLY_APPROVAL_PACKET_25_CLOSEOUT_LOCKS_HELD");
        assertThat(firewall.policies())
                .extracting(OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse.ApprovalPolicy::code)
                .containsExactly(
                        "APPROVAL_PREFLIGHT_POLICY_18_IMPORT_FIREWALL_LOCKED",
                        "APPROVAL_PREFLIGHT_POLICY_19_RUNTIME_EXECUTION_LOCKED"
                );
        assertThat(firewall.checks()).contains(
                "value-supply-approval-preflight-import-firewall-no-import-preview",
                "value-supply-approval-preflight-import-firewall-no-evidence-import",
                "value-supply-approval-preflight-import-firewall-no-runtime-payload",
                "value-supply-approval-preflight-import-firewall-no-production-execution"
        );
        assertThat(firewall.status()).isEqualTo("passed");
    }

    @Test
    void buildsDigestBlueprintWithoutApprovalCaptureValueHashOrImportReadiness() {
        OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse blueprint =
                new OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightDigestBlueprintService()
                        .blueprint();

        assertThat(blueprint.version()).isEqualTo("Java v706");
        assertThat(blueprint.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/operator-evidence-value-supply-approval-preflight-digest-blueprint");
        assertThat(blueprint.profile()).isEqualTo(
                "java-shard-readiness-operator-evidence-value-supply-approval-preflight-digest-blueprint.v1");
        assertThat(blueprint.readyForSignedApprovalCapture()).isFalse();
        assertThat(blueprint.readyForOperatorValueSubmission()).isFalse();
        assertThat(blueprint.readyForEvidenceImport()).isFalse();
        assertThat(blueprint.itemCount()).isEqualTo(25);
        assertThat(blueprint.policyCount()).isEqualTo(20);
        assertThat(blueprint.items().get(0).code()).isEqualTo("VALUE_SUPPLY_APPROVAL_PACKET_01_PACKET_ID");
        assertThat(blueprint.items().get(24).code()).isEqualTo("VALUE_SUPPLY_APPROVAL_PACKET_25_CLOSEOUT_LOCKS_HELD");
        assertThat(blueprint.checks()).contains(
                "value-supply-approval-preflight-digest-blueprint-no-value-hash",
                "value-supply-approval-preflight-digest-blueprint-no-approval-capture",
                "value-supply-approval-preflight-digest-blueprint-zero-value-counts",
                "value-supply-approval-preflight-digest-blueprint-import-firewall-covered"
        );
        assertThat(blueprint.status()).isEqualTo("passed");
    }

    @Test
    void buildsArchivePlanWithoutWritingFilesStartingProcessesOrImportingValues() {
        OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse plan =
                new OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightArchivePlanService()
                        .plan();

        assertThat(plan.version()).isEqualTo("Java v708");
        assertThat(plan.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/operator-evidence-value-supply-approval-preflight-archive-plan");
        assertThat(plan.profile()).isEqualTo(
                "java-shard-readiness-operator-evidence-value-supply-approval-preflight-archive-plan.v1");
        assertThat(plan.readOnly()).isTrue();
        assertThat(plan.executionAllowed()).isFalse();
        assertThat(plan.readyForSignedApprovalCapture()).isFalse();
        assertThat(plan.readyForEvidenceImport()).isFalse();
        assertThat(plan.readyForRuntimePayload()).isFalse();
        assertThat(plan.itemCount()).isEqualTo(5);
        assertThat(plan.policyCount()).isEqualTo(20);
        assertThat(plan.items().get(0).code()).isEqualTo("VALUE_SUPPLY_APPROVAL_PACKET_21_ZERO_ACCEPTED_VALUE_COUNT");
        assertThat(plan.items().get(4).code()).isEqualTo("VALUE_SUPPLY_APPROVAL_PACKET_25_CLOSEOUT_LOCKS_HELD");
        assertThat(plan.checks()).contains(
                "value-supply-approval-preflight-archive-plan-external-capture",
                "value-supply-approval-preflight-archive-plan-no-file-write",
                "value-supply-approval-preflight-archive-plan-no-process-start",
                "value-supply-approval-preflight-archive-plan-no-import-or-runtime"
        );
        assertThat(plan.status()).isEqualTo("passed");
    }
}
