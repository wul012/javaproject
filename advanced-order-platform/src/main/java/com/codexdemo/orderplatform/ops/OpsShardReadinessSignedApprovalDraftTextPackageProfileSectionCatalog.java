package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionCatalog {

    private OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionCatalog() {
    }

    static List<OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryResponse
            .TextPackageProfileSection> sections(
            List<OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryResponse
                    .TextPackageSectionSource> sources
    ) {
        return sources.stream()
                .map(source -> new OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryResponse
                        .TextPackageProfileSection(
                                source.order(),
                                source.code() + "-section",
                                heading(source.code()),
                                source.javaVersion(),
                                source.nodeVersionMarker(),
                                source.rendererGroup(),
                                source.endpoint(),
                                source.profile(),
                                7,
                                rendererOwner(source.rendererGroup()),
                                "passed"))
                .toList();
    }

    private static String rendererOwner(String rendererGroup) {
        return switch (rendererGroup) {
            case "submission" -> "signed-approval-draft-text-package-submission-profile-section-renderer";
            case "compared-evidence" ->
                    "signed-approval-draft-text-package-compared-evidence-profile-section-renderer";
            default -> "signed-approval-draft-text-package-profile-section-renderer";
        };
    }

    private static String heading(String code) {
        return switch (code) {
            case "signed-approval-artifact-draft-text-package-intake" ->
                    "Signed Approval Artifact Draft Text Package Intake";
            case "signed-approval-artifact-draft-text-package-review-preflight" ->
                    "Signed Approval Artifact Draft Text Package Review Preflight";
            case "signed-approval-artifact-draft-text-package-submission-preflight" ->
                    "Signed Approval Artifact Draft Text Package Submission Preflight";
            case "signed-approval-artifact-draft-text-package-comparison-preflight" ->
                    "Signed Approval Artifact Draft Text Package Comparison Preflight";
            case "signed-approval-artifact-draft-text-package-comparison-acceptance-precheck" ->
                    "Signed Approval Artifact Draft Text Package Comparison Acceptance Precheck";
            case "signed-approval-artifact-draft-text-package-compared-package-evidence-intake" ->
                    "Signed Approval Artifact Draft Text Package Compared Package Evidence Intake";
            case "signed-approval-artifact-draft-text-package-compared-evidence-evaluation-preflight" ->
                    "Signed Approval Artifact Draft Text Package Compared Evidence Evaluation Preflight";
            case "signed-approval-artifact-draft-text-package-compared-evidence-candidate" ->
                    "Signed Approval Artifact Draft Text Package Compared Evidence Candidate";
            case "signed-approval-artifact-draft-text-package-compared-evidence-candidate-intake" ->
                    "Signed Approval Artifact Draft Text Package Compared Evidence Candidate Intake";
            default -> "Signed Approval Draft Text Package Section";
        };
    }
}
