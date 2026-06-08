package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeSignatureEnvelopeService {

    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths
                    .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_INTAKE_SIGNATURE_ENVELOPE;
    static final String PROFILE =
            "java-shard-readiness-operator-evidence-value-supply-signed-approval-artifact-draft-text-package-intake-signature-envelope.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeResponse signatureEnvelope() {
        return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeSupport.response(
                "Java v924",
                ENDPOINT,
                PROFILE,
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeFieldCatalog
                        .fields(8, 11),
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeGuardCatalog
                        .guards(8, 11),
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeGateCatalog
                        .gates(2, 5),
                List.of(
                        "signed-approval-artifact-draft-text-package-intake-signature-envelope-metadata-only",
                        "signed-approval-artifact-draft-text-package-intake-no-detached-signature-material"
                )
        );
    }
}
