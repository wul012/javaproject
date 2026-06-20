package com.codexdemo.orderplatform.ops.maintenance.signedapprovaldraftprofilesection;

import java.util.List;

final class OpsShardReadinessSignedApprovalDraftProfileSectionRouteLockCatalog {

  private OpsShardReadinessSignedApprovalDraftProfileSectionRouteLockCatalog() {}

  static List<OpsShardReadinessSignedApprovalDraftProfileSectionRegistryResponse.RouteFieldLock>
      routeFieldLocks(
          List<
                  OpsShardReadinessSignedApprovalDraftProfileSectionRegistryResponse
                      .DraftProfileSection>
              sections) {
    return sections.stream()
        .map(
            section ->
                new OpsShardReadinessSignedApprovalDraftProfileSectionRegistryResponse
                    .RouteFieldLock(
                    section.code(),
                    section.endpoint(),
                    section.profile(),
                    section.javaVersion(),
                    section.nodeVersionMarker(),
                    5,
                    "java-version-endpoint-profile-node-marker-status-locked",
                    "passed"))
        .toList();
  }
}
