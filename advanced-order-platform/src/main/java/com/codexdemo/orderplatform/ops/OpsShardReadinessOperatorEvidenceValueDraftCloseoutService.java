package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceValueDraftCloseoutService {

    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_DRAFT_CLOSEOUT;
    static final String PROFILE =
            "java-shard-readiness-operator-evidence-value-draft-closeout.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessOperatorEvidenceValueDraftResponse closeout() {
        return OpsShardReadinessOperatorEvidenceValueDraftSupport.response(
                "Java v632",
                ENDPOINT,
                PROFILE,
                OpsShardReadinessOperatorEvidenceValueDraftSlotCatalog.allSlots(),
                List.of(
                        "value-draft-closeout-versions-v609-v633",
                        "value-draft-closeout-slot-count-25",
                        "value-draft-closeout-foundation-and-assurance-split",
                        "value-draft-closeout-import-remains-locked"
                )
        );
    }
}
