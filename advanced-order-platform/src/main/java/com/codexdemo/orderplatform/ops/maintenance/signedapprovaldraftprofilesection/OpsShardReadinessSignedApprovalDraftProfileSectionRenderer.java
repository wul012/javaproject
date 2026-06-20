package com.codexdemo.orderplatform.ops.maintenance.signedapprovaldraftprofilesection;

import java.util.List;

final class OpsShardReadinessSignedApprovalDraftProfileSectionRenderer {

  private OpsShardReadinessSignedApprovalDraftProfileSectionRenderer() {}

  static List<OpsShardReadinessSignedApprovalDraftProfileSectionRegistryResponse.RenderedSection>
      render(
          List<
                  OpsShardReadinessSignedApprovalDraftProfileSectionRegistryResponse
                      .DraftProfileSection>
              sections,
          List<OpsShardReadinessSignedApprovalDraftProfileSectionRegistryResponse.FieldEntry>
              fieldEntries) {
    return sections.stream()
        .map(
            section ->
                new OpsShardReadinessSignedApprovalDraftProfileSectionRegistryResponse
                    .RenderedSection(
                    section.order(),
                    section.code(),
                    "### " + section.heading(),
                    markdownBody(section, fieldEntries),
                    "passed"))
        .toList();
  }

  private static String markdownBody(
      OpsShardReadinessSignedApprovalDraftProfileSectionRegistryResponse.DraftProfileSection
          section,
      List<OpsShardReadinessSignedApprovalDraftProfileSectionRegistryResponse.FieldEntry>
          fieldEntries) {
    var lines =
        fieldEntries.stream()
            .filter(entry -> entry.sectionCode().equals(section.code()))
            .map(entry -> "- " + entry.fieldName() + ": " + entry.fieldValue())
            .toList();
    return String.join("\n", lines);
  }
}
