package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessComparedEvidenceCandidateBlueprintCloseoutService {

    static final String ENDPOINT = OpsShardReadinessRoutePaths.BASE_PATH + OpsShardReadinessRoutePaths
            .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_COMPARED_EVIDENCE_CANDIDATE_BLUEPRINT_CLOSEOUT;
    static final String PROFILE = "java-shard-readiness-compared-evidence-candidate-blueprint-closeout.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessComparedEvidenceCandidateBlueprintResponse closeout() {
        return OpsShardReadinessComparedEvidenceCandidateBlueprintCatalogService.response(
                "Java v1064",
                ENDPOINT,
                PROFILE,
                OpsShardReadinessComparedEvidenceCandidateBlueprintCloseoutSectionCatalog.closeoutSections(),
                OpsShardReadinessComparedEvidenceCandidateBlueprintBlockerCatalog.closeoutBlockers(),
                List.of("compared-evidence-candidate-blueprint-closeout-sections"));
    }
}
