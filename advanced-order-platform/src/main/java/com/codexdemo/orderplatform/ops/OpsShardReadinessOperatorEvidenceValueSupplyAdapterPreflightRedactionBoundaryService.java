package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightRedactionBoundaryService {

    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_ADAPTER_PREFLIGHT_REDACTION_BOUNDARY;
    static final String PROFILE =
            "java-shard-readiness-operator-evidence-value-supply-adapter-preflight-redaction-boundary.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse boundary() {
        return OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightSupport.response(
                "Java v666",
                ENDPOINT,
                PROFILE,
                OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightSlotCatalog.slots(4, 8),
                OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightRuleCatalog.rules(4, 7),
                List.of(
                        "value-supply-adapter-preflight-redaction-slice-5-8",
                        "value-supply-adapter-preflight-redaction-credential-values-blocked",
                        "value-supply-adapter-preflight-redaction-raw-endpoints-blocked",
                        "value-supply-adapter-preflight-redaction-secret-material-blocked"
                )
        );
    }
}
