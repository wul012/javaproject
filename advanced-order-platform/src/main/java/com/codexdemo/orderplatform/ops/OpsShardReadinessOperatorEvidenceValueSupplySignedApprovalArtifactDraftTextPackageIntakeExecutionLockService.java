package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeExecutionLockService {

    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths
                    .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_INTAKE_EXECUTION_LOCK;
    static final String PROFILE =
            "java-shard-readiness-operator-evidence-value-supply-signed-approval-artifact-draft-text-package-intake-execution-lock.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeResponse executionLock() {
        return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeSupport.response(
                "Java v928",
                ENDPOINT,
                PROFILE,
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeFieldCatalog
                        .fields(19, 24),
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeGuardCatalog
                        .guards(19, 24),
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeGateCatalog
                        .gates(5, 10),
                List.of(
                        "signed-approval-artifact-draft-text-package-intake-execution-locks-closed",
                        "signed-approval-artifact-draft-text-package-intake-no-runtime-startup"
                )
        );
    }
}
