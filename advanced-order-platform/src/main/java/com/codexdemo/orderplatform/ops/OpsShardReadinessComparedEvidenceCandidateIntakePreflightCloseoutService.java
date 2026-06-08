package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessComparedEvidenceCandidateIntakePreflightCloseoutService {

    static final String ENDPOINT = OpsShardReadinessRoutePaths.BASE_PATH + OpsShardReadinessRoutePaths
            .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_COMPARED_EVIDENCE_CANDIDATE_INTAKE_PREFLIGHT_CLOSEOUT;
    static final String PROFILE = "java-shard-readiness-compared-evidence-candidate-intake-preflight-closeout.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessComparedEvidenceCandidateIntakePreflightResponse closeout() {
        return OpsShardReadinessComparedEvidenceCandidateIntakePreflightCatalogService.response(
                "Java v1079",
                ENDPOINT,
                PROFILE,
                OpsShardReadinessComparedEvidenceCandidateIntakePreflightCloseoutSlotCatalog.closeoutSlots(),
                OpsShardReadinessComparedEvidenceCandidateIntakePreflightGuardCatalog.closeoutGuards(),
                OpsShardReadinessComparedEvidenceCandidateIntakePreflightGateCatalog.allGates(),
                List.of("compared-evidence-candidate-intake-preflight-closeout-slots"));
    }
}
