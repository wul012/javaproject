package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessComparedEvidenceCandidateIntakePreflightComparisonService {

    static final String ENDPOINT = OpsShardReadinessRoutePaths.BASE_PATH + OpsShardReadinessRoutePaths
            .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_COMPARED_EVIDENCE_CANDIDATE_INTAKE_PREFLIGHT_COMPARISON;
    static final String PROFILE = "java-shard-readiness-compared-evidence-candidate-intake-preflight-comparison.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessComparedEvidenceCandidateIntakePreflightResponse comparison() {
        return OpsShardReadinessComparedEvidenceCandidateIntakePreflightCatalogService.response(
                "Java v1077",
                ENDPOINT,
                PROFILE,
                OpsShardReadinessComparedEvidenceCandidateIntakePreflightComparisonSlotCatalog.comparisonSlots(),
                OpsShardReadinessComparedEvidenceCandidateIntakePreflightGuardCatalog.comparisonGuards(),
                OpsShardReadinessComparedEvidenceCandidateIntakePreflightGateCatalog.allGates(),
                List.of("compared-evidence-candidate-intake-preflight-comparison-slots"));
    }
}
