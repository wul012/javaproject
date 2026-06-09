package com.codexdemo.orderplatform.ops;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

final class OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRenderer {

    private OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRenderer() {
    }

    static List<OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryResponse.RenderedSection>
    render(
            List<OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryResponse
                    .TextPackageProfileSection> sections,
            List<OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryResponse.FieldEntry>
                    fieldEntries
    ) {
        return Stream.concat(
                        OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionSubmissionRenderer
                                .render(sections, fieldEntries)
                                .stream(),
                        OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionComparedEvidenceRenderer
                                .render(sections, fieldEntries)
                                .stream())
                .sorted(Comparator.comparingInt(
                        OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryResponse
                                .RenderedSection::order))
                .toList();
    }
}
