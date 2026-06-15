package com.codexdemo.orderplatform.ops.maintenance.candidatedocument;

import java.util.List;

final class OpsShardReadinessCandidateDocumentProfileSectionRouteLockCatalog {

  private OpsShardReadinessCandidateDocumentProfileSectionRouteLockCatalog() {}

  static List<OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse.RouteFieldLock>
      routeFieldLocks(
          List<OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse.ProfileSection>
              sections) {
    return sections.stream()
        .map(
            section ->
                new OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse.RouteFieldLock(
                    section.code(),
                    section.endpoint(),
                    section.profile(),
                    section.sourceVersion(),
                    3,
                    "fail-closed-route-facing-fields",
                    "passed"))
        .toList();
  }
}
