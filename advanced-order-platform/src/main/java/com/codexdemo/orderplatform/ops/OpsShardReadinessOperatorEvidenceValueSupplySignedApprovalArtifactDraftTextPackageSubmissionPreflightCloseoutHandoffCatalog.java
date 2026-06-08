package com.codexdemo.orderplatform.ops;

import java.util.List;
import java.util.stream.Stream;

final class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutHandoffCatalog {

    private OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutHandoffCatalog() {
    }

    static List<OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutResponse
            .HandoffItem> allItems() {
        return Stream.concat(
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutFoundationHandoffCatalog
                        .foundationItems().stream(),
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutAssuranceHandoffCatalog
                        .assuranceItems().stream()
        ).toList();
    }

    static List<OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutResponse
            .HandoffItem> foundationItems() {
        return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutFoundationHandoffCatalog
                .foundationItems();
    }

    static List<OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutResponse
            .HandoffItem> assuranceItems() {
        return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutAssuranceHandoffCatalog
                .assuranceItems();
    }
}

