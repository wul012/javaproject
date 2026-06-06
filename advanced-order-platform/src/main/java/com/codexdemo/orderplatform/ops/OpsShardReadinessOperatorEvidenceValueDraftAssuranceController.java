package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessOperatorEvidenceValueDraftAssuranceController {

    private final OpsShardReadinessOperatorEvidenceValueDraftBlockedReasonLedgerService blockedReasonLedgerService;

    public OpsShardReadinessOperatorEvidenceValueDraftAssuranceController(
            OpsShardReadinessOperatorEvidenceValueDraftBlockedReasonLedgerService blockedReasonLedgerService
    ) {
        this.blockedReasonLedgerService = blockedReasonLedgerService;
    }

    @GetMapping(OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_DRAFT_BLOCKED_REASON_LEDGER)
    public OpsShardReadinessOperatorEvidenceValueDraftResponse blockedReasonLedger() {
        return blockedReasonLedgerService.ledger();
    }
}
