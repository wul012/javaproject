package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionComparedEvidenceRenderer {

    private OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionComparedEvidenceRenderer() {
    }

    static List<OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryResponse.RenderedSection>
    render(
            List<OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryResponse
                    .TextPackageProfileSection> sections,
            List<OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryResponse.FieldEntry>
                    fieldEntries
    ) {
        return sections.stream()
                .filter(section -> "compared-evidence".equals(section.rendererGroup()))
                .map(section -> renderedSection(section, fieldEntries))
                .toList();
    }

    private static OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryResponse.RenderedSection
    renderedSection(
            OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryResponse.TextPackageProfileSection
                    section,
            List<OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryResponse.FieldEntry>
                    fieldEntries
    ) {
        return new OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryResponse.RenderedSection(
                section.order(),
                section.code(),
                section.rendererGroup(),
                "### " + section.heading(),
                OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRendererSupport
                        .markdownBody(section, fieldEntries),
                "passed"
        );
    }
}
