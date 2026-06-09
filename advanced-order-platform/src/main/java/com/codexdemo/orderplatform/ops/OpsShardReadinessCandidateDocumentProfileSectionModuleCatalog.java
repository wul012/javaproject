package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessCandidateDocumentProfileSectionModuleCatalog {

    private OpsShardReadinessCandidateDocumentProfileSectionModuleCatalog() {
    }

    static List<OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse.ModuleEntry> modules() {
        return List.of(
                module(219, "candidate-document-profile-section-types",
                        "defines candidate document profile section records"),
                module(220, "candidate-document-profile-section-source-catalog",
                        "collects the five route-facing candidate document sources"),
                module(221, "candidate-document-profile-section-field-catalog",
                        "locks endpoint, profile, version, status, and boundary fields"),
                module(222, "candidate-document-profile-section-renderer",
                        "renders stable Markdown for the extracted section group"),
                module(223, "candidate-document-profile-section-registry-route",
                        "exposes the registry without changing runtime behavior")
        );
    }

    private static OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse.ModuleEntry module(
            int order,
            String code,
            String responsibility
    ) {
        return new OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse.ModuleEntry(
                order,
                code,
                responsibility,
                "passed"
        );
    }
}
