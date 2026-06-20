package com.codexdemo.orderplatform.ops.maintenance.signedapprovaldrafttextpackageprofilesection;

import java.util.List;

final class OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionSubmissionRenderer {

  private OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionSubmissionRenderer() {}

  static List<
          OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryResponse
              .RenderedSection>
      render(
          List<
                  OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryResponse
                      .TextPackageProfileSection>
              sections,
          List<
                  OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryResponse
                      .FieldEntry>
              fieldEntries) {
    return sections.stream()
        .filter(section -> "submission".equals(section.rendererGroup()))
        .map(section -> renderedSection(section, fieldEntries))
        .toList();
  }

  private static OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryResponse
          .RenderedSection
      renderedSection(
          OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryResponse
                  .TextPackageProfileSection
              section,
          List<
                  OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryResponse
                      .FieldEntry>
              fieldEntries) {
    return new OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryResponse
        .RenderedSection(
        section.order(),
        section.code(),
        section.rendererGroup(),
        "### " + section.heading(),
        OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRendererSupport.markdownBody(
            section, fieldEntries),
        "passed");
  }
}
