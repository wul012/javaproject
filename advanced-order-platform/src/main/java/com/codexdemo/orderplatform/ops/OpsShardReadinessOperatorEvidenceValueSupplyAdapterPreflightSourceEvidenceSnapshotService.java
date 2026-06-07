package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightSourceEvidenceSnapshotService {

    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_ADAPTER_PREFLIGHT_SOURCE_EVIDENCE_SNAPSHOT;
    static final String PROFILE =
            "java-shard-readiness-operator-evidence-value-supply-adapter-preflight-source-evidence-snapshot.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse snapshot() {
        return OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightSupport.response(
                "Java v672",
                ENDPOINT,
                PROFILE,
                OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightSlotCatalog.slots(16, 20),
                OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightRuleCatalog.rules(12, 15),
                List.of(
                        "value-supply-adapter-preflight-source-evidence-slice-17-20",
                        "value-supply-adapter-preflight-source-evidence-fresh-sibling-read-only",
                        "value-supply-adapter-preflight-source-evidence-historical-fallback-explicit",
                        "value-supply-adapter-preflight-source-evidence-no-automatic-sibling-import",
                        "value-supply-adapter-preflight-source-evidence-synthetic-blocked",
                        "value-supply-adapter-preflight-source-evidence-runtime-payload-blocked"
                )
        );
    }
}
