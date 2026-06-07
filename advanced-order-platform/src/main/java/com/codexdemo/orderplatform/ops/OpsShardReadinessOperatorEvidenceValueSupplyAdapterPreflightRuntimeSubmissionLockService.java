package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightRuntimeSubmissionLockService {

    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_ADAPTER_PREFLIGHT_RUNTIME_SUBMISSION_LOCK;
    static final String PROFILE =
            "java-shard-readiness-operator-evidence-value-supply-adapter-preflight-runtime-submission-lock.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse lock() {
        return OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightSupport.response(
                "Java v676",
                ENDPOINT,
                PROFILE,
                OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightSlotCatalog.slots(20, 24),
                OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightRuleCatalog.rules(15, 17),
                List.of(
                        "value-supply-adapter-preflight-runtime-submission-slice-21-24",
                        "value-supply-adapter-preflight-runtime-submission-operator-values-locked",
                        "value-supply-adapter-preflight-runtime-submission-import-preview-locked",
                        "value-supply-adapter-preflight-runtime-submission-no-state-write",
                        "value-supply-adapter-preflight-runtime-submission-live-execution-locked",
                        "value-supply-adapter-preflight-runtime-submission-production-locked"
                )
        );
    }
}
