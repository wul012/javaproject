package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceValueDraftCatalogService {

    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_DRAFT_CATALOG;
    static final String PROFILE =
            "java-shard-readiness-operator-evidence-value-draft-catalog.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessOperatorEvidenceValueDraftResponse catalog() {
        return OpsShardReadinessOperatorEvidenceValueDraftSupport.response(
                "Java v610",
                ENDPOINT,
                PROFILE,
                OpsShardReadinessOperatorEvidenceValueDraftSlotCatalog.allSlots(),
                List.of(
                        "value-draft-catalog-slot-count-25",
                        "value-draft-catalog-actual-values-not-supplied",
                        "value-draft-catalog-source-import-preflight-v608"
                )
        );
    }
}
