package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceValueSupplyEnvelopeTemplateService {

    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_ENVELOPE_TEMPLATE;
    static final String PROFILE =
            "java-shard-readiness-operator-evidence-value-supply-envelope-template.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessOperatorEvidenceValueSupplyResponse template() {
        return OpsShardReadinessOperatorEvidenceValueSupplySupport.response(
                "Java v638",
                ENDPOINT,
                PROFILE,
                OpsShardReadinessOperatorEvidenceValueSupplySlotCatalog.slots(0, 4),
                List.of(
                        "value-supply-template-foundation-slice-1-4",
                        "value-supply-template-metadata-only",
                        "value-supply-template-no-value-field"
                )
        );
    }
}
