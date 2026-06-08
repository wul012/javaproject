package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckPolicyExecutionArchiveService {

    static final String ENDPOINT = OpsShardReadinessRoutePaths.BASE_PATH + OpsShardReadinessRoutePaths
            .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_COMPARISON_ACCEPTANCE_PRECHECK_POLICY_EXECUTION_ARCHIVE;
    static final String PROFILE =
            "java-shard-readiness-operator-evidence-value-supply-signed-approval-artifact-draft-text-package-comparison-acceptance-precheck-policy-execution-archive.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckResponse
    policyExecutionArchive() {
        return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckCatalogService
                .response("Java v1012", ENDPOINT, PROFILE,
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckCheckpointCatalog
                                .allCheckpoints().subList(6, 10),
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckGuardCatalog
                                .allGuards().subList(6, 10),
                        List.of("draft-text-package-comparison-acceptance-precheck-policy-execution-archive"));
    }
}

