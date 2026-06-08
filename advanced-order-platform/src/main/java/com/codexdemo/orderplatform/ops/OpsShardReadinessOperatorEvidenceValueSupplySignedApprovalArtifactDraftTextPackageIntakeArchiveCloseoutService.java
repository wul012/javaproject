package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeArchiveCloseoutService {

    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths
                    .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_INTAKE_ARCHIVE_CLOSEOUT;
    static final String PROFILE =
            "java-shard-readiness-operator-evidence-value-supply-signed-approval-artifact-draft-text-package-intake-archive-closeout.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeResponse archiveCloseout() {
        return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeSupport.response(
                "Java v929",
                ENDPOINT,
                PROFILE,
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeFieldCatalog
                        .allFields(),
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeGuardCatalog
                        .allGuards(),
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeGateCatalog
                        .allGates(),
                List.of(
                        "signed-approval-artifact-draft-text-package-intake-archive-closeout-field-present",
                        "signed-approval-artifact-draft-text-package-intake-final-guard-summary-passed",
                        "signed-approval-artifact-draft-text-package-intake-closeout-before-package-review"
                )
        );
    }
}
