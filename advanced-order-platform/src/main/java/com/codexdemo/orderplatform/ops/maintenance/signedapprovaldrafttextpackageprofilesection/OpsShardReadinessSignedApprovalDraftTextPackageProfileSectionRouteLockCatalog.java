package com.codexdemo.orderplatform.ops.maintenance.signedapprovaldrafttextpackageprofilesection;

import java.util.List;

final class OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRouteLockCatalog {

  private OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRouteLockCatalog() {}

  static List<
          OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryResponse
              .RouteFieldLock>
      routeFieldLocks(
          List<
                  OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryResponse
                      .TextPackageProfileSection>
              sections) {
    return sections.stream()
        .map(
            section ->
                new OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryResponse
                    .RouteFieldLock(
                    section.code(),
                    section.endpoint(),
                    section.profile(),
                    section.javaVersion(),
                    section.nodeVersionMarker(),
                    section.rendererGroup(),
                    5,
                    "java-version-endpoint-profile-node-marker-renderer-group-locked",
                    "passed"))
        .toList();
  }
}
