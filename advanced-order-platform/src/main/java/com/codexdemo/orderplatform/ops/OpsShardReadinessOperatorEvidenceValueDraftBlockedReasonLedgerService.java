package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceValueDraftBlockedReasonLedgerService {

    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_DRAFT_BLOCKED_REASON_LEDGER;
    static final String PROFILE =
            "java-shard-readiness-operator-evidence-value-draft-blocked-reason-ledger.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessOperatorEvidenceValueDraftResponse ledger() {
        return OpsShardReadinessOperatorEvidenceValueDraftSupport.response(
                "Java v622",
                ENDPOINT,
                PROFILE,
                OpsShardReadinessOperatorEvidenceValueDraftSlotCatalog.slots(21, 25),
                List.of(
                        "value-draft-blocked-reason-slice-22-25",
                        "value-draft-blocked-reason-draft-ready-not-import-ready",
                        "value-draft-blocked-reason-production-execution-locked"
                )
        );
    }
}
