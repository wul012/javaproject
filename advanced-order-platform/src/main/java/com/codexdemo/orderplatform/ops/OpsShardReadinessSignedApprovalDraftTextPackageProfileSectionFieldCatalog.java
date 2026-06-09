package com.codexdemo.orderplatform.ops;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionFieldCatalog {

    private OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionFieldCatalog() {
    }

    static List<OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryResponse.FieldEntry>
    fieldEntries(
            List<OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryResponse
                    .TextPackageProfileSection> sections
    ) {
        List<OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryResponse.FieldEntry>
                entries = new ArrayList<>();
        int order = 1;
        for (var section : sections) {
            entries.add(entry(order++, section.code(), "java-version", section.javaVersion(), true));
            entries.add(entry(order++, section.code(), "endpoint", section.endpoint(), true));
            entries.add(entry(order++, section.code(), "profile", section.profile(), true));
            entries.add(entry(order++, section.code(), "node-marker", section.nodeVersionMarker(), true));
            entries.add(entry(order++, section.code(), "renderer-group", section.rendererGroup(), true));
            entries.add(entry(order++, section.code(), "source-status", section.status(), true));
            entries.add(entry(order++, section.code(), "boundary", "read-only-no-runtime", false));
        }
        return List.copyOf(entries);
    }

    private static OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryResponse.FieldEntry
    entry(
            int order,
            String sectionCode,
            String fieldName,
            String fieldValue,
            boolean routeFacing
    ) {
        return new OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryResponse.FieldEntry(
                order,
                sectionCode,
                fieldName,
                fieldValue,
                routeFacing,
                "passed"
        );
    }
}
