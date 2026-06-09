package com.codexdemo.orderplatform.ops;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessSignedApprovalDraftProfileSectionFieldCatalog {

    private OpsShardReadinessSignedApprovalDraftProfileSectionFieldCatalog() {
    }

    static List<OpsShardReadinessSignedApprovalDraftProfileSectionRegistryResponse.FieldEntry> fieldEntries(
            List<OpsShardReadinessSignedApprovalDraftProfileSectionRegistryResponse.DraftProfileSection> sections
    ) {
        List<OpsShardReadinessSignedApprovalDraftProfileSectionRegistryResponse.FieldEntry> entries =
                new ArrayList<>();
        int order = 1;
        for (var section : sections) {
            entries.add(entry(order++, section.code(), "java-version", section.javaVersion(), true));
            entries.add(entry(order++, section.code(), "endpoint", section.endpoint(), true));
            entries.add(entry(order++, section.code(), "profile", section.profile(), true));
            entries.add(entry(order++, section.code(), "node-marker", section.nodeVersionMarker(), true));
            entries.add(entry(order++, section.code(), "source-status", section.sourceStatus(), true));
            entries.add(entry(order++, section.code(), "boundary", "read-only-no-runtime", false));
        }
        return List.copyOf(entries);
    }

    private static OpsShardReadinessSignedApprovalDraftProfileSectionRegistryResponse.FieldEntry entry(
            int order,
            String sectionCode,
            String fieldName,
            String fieldValue,
            boolean routeFacing
    ) {
        return new OpsShardReadinessSignedApprovalDraftProfileSectionRegistryResponse.FieldEntry(
                order,
                sectionCode,
                fieldName,
                fieldValue,
                routeFacing,
                "passed"
        );
    }
}
