package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessComparedEvidenceCandidateBlueprintCatalogService {

    static final String ENDPOINT = OpsShardReadinessRoutePaths.BASE_PATH + OpsShardReadinessRoutePaths
            .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_COMPARED_EVIDENCE_CANDIDATE_BLUEPRINT_CATALOG;
    static final String PROFILE = "java-shard-readiness-compared-evidence-candidate-blueprint-catalog.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessComparedEvidenceCandidateBlueprintResponse catalog() {
        return response("Java v1060", ENDPOINT, PROFILE,
                OpsShardReadinessComparedEvidenceCandidateBlueprintSectionCatalog.allSections(),
                OpsShardReadinessComparedEvidenceCandidateBlueprintBlockerCatalog.allBlockers(),
                List.of("compared-evidence-candidate-blueprint-catalog-full"));
    }

    static OpsShardReadinessComparedEvidenceCandidateBlueprintResponse response(
            String version,
            String endpoint,
            String profile,
            List<OpsShardReadinessComparedEvidenceCandidateBlueprintResponse.CandidateSection> sections,
            List<OpsShardReadinessComparedEvidenceCandidateBlueprintResponse.CandidateBlocker> blockers,
            List<String> checks
    ) {
        return OpsShardReadinessComparedEvidenceCandidateBlueprintSupport
                .response(version, endpoint, profile, sections, blockers, checks);
    }
}
