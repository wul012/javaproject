package com.codexdemo.orderplatform.ops.maintenance.candidatedocument;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessCandidateDocumentProfileSectionFieldCatalog {

  private OpsShardReadinessCandidateDocumentProfileSectionFieldCatalog() {}

  static List<OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse.FieldEntry>
      fieldEntries(
          List<OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse.ProfileSection>
              sections) {
    List<OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse.FieldEntry> entries =
        new ArrayList<>();
    int order = 1;
    for (var section : sections) {
      entries.add(entry(order++, section.code(), "version", section.sourceVersion(), true));
      entries.add(entry(order++, section.code(), "endpoint", section.endpoint(), true));
      entries.add(entry(order++, section.code(), "profile", section.profile(), true));
      entries.add(entry(order++, section.code(), "status", section.status(), true));
      entries.add(entry(order++, section.code(), "boundary", "read-only-no-runtime", false));
    }
    return List.copyOf(entries);
  }

  private static OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse.FieldEntry entry(
      int order, String sectionCode, String fieldName, String fieldValue, boolean routeFacing) {
    return new OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse.FieldEntry(
        order, sectionCode, fieldName, fieldValue, routeFacing, "passed");
  }
}
