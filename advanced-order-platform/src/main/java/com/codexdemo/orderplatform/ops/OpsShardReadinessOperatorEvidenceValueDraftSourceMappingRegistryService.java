package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceValueDraftSourceMappingRegistryService {

    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_DRAFT_SOURCE_MAPPING_REGISTRY;
    static final String PROFILE =
            "java-shard-readiness-operator-evidence-value-draft-source-mapping-registry.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessOperatorEvidenceValueDraftResponse registry() {
        return OpsShardReadinessOperatorEvidenceValueDraftSupport.response(
                "Java v620",
                ENDPOINT,
                PROFILE,
                OpsShardReadinessOperatorEvidenceValueDraftSlotCatalog.slots(17, 21),
                List.of(
                        "value-draft-source-mapping-slice-18-21",
                        "value-draft-source-mapping-preflight-endpoints-only",
                        "value-draft-source-mapping-no-fresh-node-evidence"
                )
        );
    }
}
