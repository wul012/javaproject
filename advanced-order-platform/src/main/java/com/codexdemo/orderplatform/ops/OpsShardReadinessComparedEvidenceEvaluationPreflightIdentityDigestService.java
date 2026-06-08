package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessComparedEvidenceEvaluationPreflightIdentityDigestService {

    static final String ENDPOINT = OpsShardReadinessRoutePaths.BASE_PATH + OpsShardReadinessRoutePaths
            .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_COMPARED_EVIDENCE_EVALUATION_PREFLIGHT_IDENTITY_DIGEST;
    static final String PROFILE = "java-shard-readiness-compared-evidence-evaluation-preflight-identity-digest.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessComparedEvidenceEvaluationPreflightResponse identityDigest() {
        return OpsShardReadinessComparedEvidenceEvaluationPreflightCatalogService.response(
                "Java v1052",
                ENDPOINT,
                PROFILE,
                OpsShardReadinessComparedEvidenceEvaluationPreflightIdentityDigestRuleCatalog.identityDigestRules(),
                OpsShardReadinessComparedEvidenceEvaluationPreflightGuardCatalog.identityDigestGuards(),
                List.of("compared-evidence-evaluation-preflight-identity-digest-rules"));
    }
}
