package com.codexdemo.orderplatform.ops.maintenance.signedapprovaldraftprofilesectionhandoff;

import com.codexdemo.orderplatform.ops.maintenance.signedapprovaldraftprofilesection.OpsShardReadinessSignedApprovalDraftProfileSectionRegistryResponse;
import java.util.List;

final class OpsShardReadinessSignedApprovalDraftProfileSectionHandoffSourceCatalog {

  private OpsShardReadinessSignedApprovalDraftProfileSectionHandoffSourceCatalog() {}

  static List<OpsShardReadinessSignedApprovalDraftProfileSectionHandoffResponse.HandoffSource>
      sources(OpsShardReadinessSignedApprovalDraftProfileSectionRegistryResponse registry) {
    return List.of(
        new OpsShardReadinessSignedApprovalDraftProfileSectionHandoffResponse.HandoffSource(
            1,
            "signed-approval-draft-profile-section-registry",
            registry.version(),
            registry.endpoint(),
            registry.profile(),
            registry.registryState(),
            registry.sectionCount(),
            "passed"));
  }
}
