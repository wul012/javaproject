package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessCandidateDocumentProfileSectionCatalog {

    private OpsShardReadinessCandidateDocumentProfileSectionCatalog() {
    }

    static List<OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse.ProfileSection> sections(
            List<OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse.SectionSource> sources
    ) {
        return sources.stream()
                .map(source -> new OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse.ProfileSection(
                        source.order(),
                        source.code() + "-section",
                        heading(source.code()),
                        source.sourceVersion(),
                        source.endpoint(),
                        source.profile(),
                        5,
                        "candidate-document-profile-section-renderer",
                        "passed"))
                .toList();
    }

    private static String heading(String code) {
        return switch (code) {
            case "candidate-document-request-package" -> "Candidate Document Request Package";
            case "candidate-document-submission-precheck" -> "Candidate Document Submission Precheck";
            case "candidate-document-intake-packet" -> "Candidate Document Intake Packet";
            case "candidate-document-material-request" -> "Candidate Document Material Request";
            case "candidate-document-material-submission-precheck" ->
                    "Candidate Document Material Submission Precheck";
            default -> "Candidate Document Section";
        };
    }
}
