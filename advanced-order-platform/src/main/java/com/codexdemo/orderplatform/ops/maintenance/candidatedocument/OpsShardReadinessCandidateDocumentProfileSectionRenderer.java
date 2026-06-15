package com.codexdemo.orderplatform.ops.maintenance.candidatedocument;

import java.util.List;

final class OpsShardReadinessCandidateDocumentProfileSectionRenderer {

  private OpsShardReadinessCandidateDocumentProfileSectionRenderer() {}

  static List<OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse.RenderedSection>
      render(
          List<OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse.ProfileSection>
              sections,
          List<OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse.FieldEntry>
              fieldEntries) {
    return sections.stream()
        .map(
            section ->
                new OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse
                    .RenderedSection(
                    section.order(),
                    section.code(),
                    "### " + section.heading(),
                    markdownBody(section, fieldEntries),
                    "passed"))
        .toList();
  }

  private static String markdownBody(
      OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse.ProfileSection section,
      List<OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse.FieldEntry>
          fieldEntries) {
    var lines =
        fieldEntries.stream()
            .filter(entry -> entry.sectionCode().equals(section.code()))
            .map(entry -> "- " + entry.fieldName() + ": " + entry.fieldValue())
            .toList();
    return String.join("\n", lines);
  }
}
