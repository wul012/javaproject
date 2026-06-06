package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceValueDraftSafetyGateMatrixService {

    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_DRAFT_SAFETY_GATE_MATRIX;
    static final String PROFILE =
            "java-shard-readiness-operator-evidence-value-draft-safety-gate-matrix.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessOperatorEvidenceValueDraftResponse matrix() {
        return OpsShardReadinessOperatorEvidenceValueDraftSupport.response(
                "Java v618",
                ENDPOINT,
                PROFILE,
                OpsShardReadinessOperatorEvidenceValueDraftSlotCatalog.slots(13, 17),
                List.of(
                        "value-draft-safety-gate-redaction-slice-14-17",
                        "value-draft-safety-gate-no-secret-values",
                        "value-draft-safety-gate-no-synthetic-evidence"
                )
        );
    }
}
