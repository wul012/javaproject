package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessComparedEvidenceEvaluationPreflightSourceArtifactService {

    static final String ENDPOINT = OpsShardReadinessRoutePaths.BASE_PATH + OpsShardReadinessRoutePaths
            .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_COMPARED_EVIDENCE_EVALUATION_PREFLIGHT_SOURCE_ARTIFACT;
    static final String PROFILE = "java-shard-readiness-compared-evidence-evaluation-preflight-source-artifact.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessComparedEvidenceEvaluationPreflightResponse sourceArtifact() {
        return OpsShardReadinessComparedEvidenceEvaluationPreflightCatalogService.response(
                "Java v1051",
                ENDPOINT,
                PROFILE,
                OpsShardReadinessComparedEvidenceEvaluationPreflightSourceArtifactRuleCatalog.sourceArtifactRules(),
                OpsShardReadinessComparedEvidenceEvaluationPreflightGuardCatalog.sourceArtifactGuards(),
                List.of("compared-evidence-evaluation-preflight-source-artifact-rules"));
    }
}
