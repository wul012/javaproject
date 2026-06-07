package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightAssuranceControllerTests {

    @Test
    void exposesValueRejectionThroughAssuranceControllerWithoutImportReadiness() {
        OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightAssuranceController controller = controller();

        OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse rejection =
                controller.valueRejection();

        assertThat(rejection.version()).isEqualTo("Java v698");
        assertThat(rejection.endpoint()).isEqualTo(
                OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightValueRejectionService.ENDPOINT);
        assertThat(rejection.acceptedValueState()).isEqualTo("not-accepted");
        assertThat(rejection.malformedValueState()).isEqualTo("rejected");
        assertThat(rejection.readyForOperatorValueSubmission()).isFalse();
        assertThat(rejection.readyForEvidenceImport()).isFalse();
        assertThat(rejection.itemCount()).isEqualTo(3);
        assertThat(rejection.policyCount()).isEqualTo(3);
        assertThat(rejection.status()).isEqualTo("passed");
    }

    @Test
    void exposesZeroValueLedgerThroughAssuranceControllerWithoutAcceptedValues() {
        OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightAssuranceController controller = controller();

        OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse ledger =
                controller.zeroValueLedger();

        assertThat(ledger.version()).isEqualTo("Java v700");
        assertThat(ledger.endpoint()).isEqualTo(
                OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightZeroValueLedgerService.ENDPOINT);
        assertThat(ledger.acceptedValueState()).isEqualTo("not-accepted");
        assertThat(ledger.readyForOperatorValueSubmission()).isFalse();
        assertThat(ledger.readyForEvidenceImport()).isFalse();
        assertThat(ledger.itemCount()).isEqualTo(3);
        assertThat(ledger.policyCount()).isEqualTo(1);
        assertThat(ledger.status()).isEqualTo("passed");
    }

    @Test
    void exposesCleanupReceiptThroughAssuranceControllerWithoutFileWrites() {
        OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightAssuranceController controller = controller();

        OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse receipt =
                controller.cleanupReceipt();

        assertThat(receipt.version()).isEqualTo("Java v702");
        assertThat(receipt.endpoint()).isEqualTo(
                OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightCleanupReceiptService.ENDPOINT);
        assertThat(receipt.receiptState()).isEqualTo("required-before-import");
        assertThat(receipt.readOnly()).isTrue();
        assertThat(receipt.executionAllowed()).isFalse();
        assertThat(receipt.readyForEvidenceImport()).isFalse();
        assertThat(receipt.itemCount()).isEqualTo(1);
        assertThat(receipt.policyCount()).isEqualTo(1);
        assertThat(receipt.status()).isEqualTo("passed");
    }

    @Test
    void exposesImportFirewallThroughAssuranceControllerWithoutRuntimeReadiness() {
        OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightAssuranceController controller = controller();

        OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse firewall =
                controller.importFirewall();

        assertThat(firewall.version()).isEqualTo("Java v704");
        assertThat(firewall.endpoint()).isEqualTo(
                OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightImportFirewallService.ENDPOINT);
        assertThat(firewall.importState()).isEqualTo("locked");
        assertThat(firewall.readyForEvidenceImport()).isFalse();
        assertThat(firewall.readyForRuntimePayload()).isFalse();
        assertThat(firewall.readyForProductionExecution()).isFalse();
        assertThat(firewall.itemCount()).isEqualTo(5);
        assertThat(firewall.policyCount()).isEqualTo(2);
        assertThat(firewall.status()).isEqualTo("passed");
    }

    private OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightAssuranceController controller() {
        return new OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightAssuranceController(
                new OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightValueRejectionService(),
                new OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightZeroValueLedgerService(),
                new OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightCleanupReceiptService(),
                new OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightImportFirewallService()
        );
    }
}
