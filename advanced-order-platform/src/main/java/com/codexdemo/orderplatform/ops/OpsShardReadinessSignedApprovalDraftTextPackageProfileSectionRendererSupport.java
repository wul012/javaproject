package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRendererSupport {

    private OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRendererSupport() {
    }

    static String markdownBody(
            OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryResponse.TextPackageProfileSection
                    section,
            List<OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryResponse.FieldEntry>
                    fieldEntries
    ) {
        var lines = fieldEntries.stream()
                .filter(entry -> entry.sectionCode().equals(section.code()))
                .map(entry -> "- " + entry.fieldName() + ": " + entry.fieldValue())
                .toList();
        return String.join("\n", lines);
    }
}
