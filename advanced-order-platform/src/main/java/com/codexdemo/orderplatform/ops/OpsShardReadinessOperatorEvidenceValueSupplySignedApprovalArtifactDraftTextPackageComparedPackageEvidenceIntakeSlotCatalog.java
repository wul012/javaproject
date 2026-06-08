package com.codexdemo.orderplatform.ops;

import java.util.List;
import java.util.stream.Stream;

final class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeSlotCatalog {

    private OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeSlotCatalog() {
    }

    static List<OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeResponse
            .EvidenceSlot> allSlots() {
        return Stream.of(
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeSourceSubmissionSlotCatalog
                        .sourceSubmissionSlots(),
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeComparisonIdentitySlotCatalog
                        .comparisonIdentitySlots(),
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeDigestSignatureSlotCatalog
                        .digestSignatureSlots(),
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeAssuranceSlotCatalog
                        .assuranceSlots()
        ).flatMap(List::stream).toList();
    }
}

