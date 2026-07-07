package com.codexdemo.orderplatform.ops.maintenance.signedapprovaldraftprofilesectionhandoff;

import com.codexdemo.orderplatform.ops.maintenance.signedapprovaldraftprofilesection.OpsShardReadinessSignedApprovalDraftProfileSectionRegistryResponse;
import java.util.List;

final class OpsShardReadinessSignedApprovalDraftProfileSectionHandoffSectionCatalog {

  private OpsShardReadinessSignedApprovalDraftProfileSectionHandoffSectionCatalog() {}

  static List<OpsShardReadinessSignedApprovalDraftProfileSectionHandoffResponse.SectionHandoff>
      handoffs(
          List<
                  OpsShardReadinessSignedApprovalDraftProfileSectionRegistryResponse
                      .DraftProfileSection>
              sections) {
    return sections.stream()
        .map(
            section ->
                new OpsShardReadinessSignedApprovalDraftProfileSectionHandoffResponse
                    .SectionHandoff(
                    section.order(),
                    section.code(),
                    section.heading(),
                    section.javaVersion(),
                    section.nodeVersionMarker(),
                    section.endpoint(),
                    section.profile(),
                    section.fieldEntryCount(),
                    "handoff-section-metadata-only",
                    "read-only-consumer-no-execution",
                    "passed"))
        .toList();
  }
}
