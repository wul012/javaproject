package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessComparedEvidenceCandidateBlueprintSourceService {

    static final String ENDPOINT = OpsShardReadinessRoutePaths.BASE_PATH + OpsShardReadinessRoutePaths
            .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_COMPARED_EVIDENCE_CANDIDATE_BLUEPRINT_SOURCE;
    static final String PROFILE = "java-shard-readiness-compared-evidence-candidate-blueprint-source.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessComparedEvidenceCandidateBlueprintResponse source() {
        return OpsShardReadinessComparedEvidenceCandidateBlueprintCatalogService.response(
                "Java v1061",
                ENDPOINT,
                PROFILE,
                OpsShardReadinessComparedEvidenceCandidateBlueprintSourceSectionCatalog.sourceSections(),
                OpsShardReadinessComparedEvidenceCandidateBlueprintBlockerCatalog.sourceBlockers(),
                List.of("compared-evidence-candidate-blueprint-source-sections"));
    }
}
