package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightAssuranceController {

    private final OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightValueRejectionService
            valueRejectionService;
    private final OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightZeroValueLedgerService
            zeroValueLedgerService;
    private final OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightCleanupReceiptService
            cleanupReceiptService;

    public OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightAssuranceController(
            OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightValueRejectionService valueRejectionService,
            OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightZeroValueLedgerService zeroValueLedgerService,
            OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightCleanupReceiptService cleanupReceiptService
    ) {
        this.valueRejectionService = valueRejectionService;
        this.zeroValueLedgerService = zeroValueLedgerService;
        this.cleanupReceiptService = cleanupReceiptService;
    }

    @GetMapping(OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_APPROVAL_PREFLIGHT_VALUE_REJECTION)
    public OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse valueRejection() {
        return valueRejectionService.rejection();
    }

    @GetMapping(OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_APPROVAL_PREFLIGHT_ZERO_VALUE_LEDGER)
    public OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse zeroValueLedger() {
        return zeroValueLedgerService.ledger();
    }

    @GetMapping(OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_APPROVAL_PREFLIGHT_CLEANUP_RECEIPT)
    public OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse cleanupReceipt() {
        return cleanupReceiptService.receipt();
    }
}
