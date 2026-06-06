package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceValueDraftDigestBlueprintService {

    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_DRAFT_DIGEST_BLUEPRINT;
    static final String PROFILE =
            "java-shard-readiness-operator-evidence-value-draft-digest-blueprint.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessOperatorEvidenceValueDraftResponse blueprint() {
        return OpsShardReadinessOperatorEvidenceValueDraftSupport.response(
                "Java v624",
                ENDPOINT,
                PROFILE,
                OpsShardReadinessOperatorEvidenceValueDraftSlotCatalog.allSlots(),
                List.of(
                        "value-draft-digest-blueprint-slot-count-25",
                        "value-draft-digest-blueprint-no-value-hash",
                        "value-draft-digest-blueprint-lock-flags-covered"
                )
        );
    }
}
