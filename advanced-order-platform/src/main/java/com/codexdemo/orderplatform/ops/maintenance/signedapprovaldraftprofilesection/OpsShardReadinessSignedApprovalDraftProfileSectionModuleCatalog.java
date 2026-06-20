package com.codexdemo.orderplatform.ops.maintenance.signedapprovaldraftprofilesection;

import java.util.List;

final class OpsShardReadinessSignedApprovalDraftProfileSectionModuleCatalog {

  private OpsShardReadinessSignedApprovalDraftProfileSectionModuleCatalog() {}

  static List<OpsShardReadinessSignedApprovalDraftProfileSectionRegistryResponse.ModuleEntry>
      modules() {
    return List.of(
        module(
            224,
            "signed-approval-draft-profile-section-types",
            "defines the signed approval draft profile section registry records"),
        module(
            225,
            "signed-approval-draft-profile-section-source-catalog",
            "collects the five signed approval draft route sources"),
        module(
            226,
            "signed-approval-draft-profile-section-module-catalog",
            "records the Java renderer boundary modules after Node v1506"),
        module(
            227,
            "signed-approval-draft-profile-section-section-catalog",
            "maps draft sources to stable profile sections"),
        module(
            228,
            "signed-approval-draft-profile-section-field-catalog",
            "locks route-facing endpoint, profile, version, marker, and status fields"),
        module(
            229,
            "signed-approval-draft-profile-section-renderer",
            "renders stable Markdown for the extracted signed approval draft section group"),
        module(
            230,
            "signed-approval-draft-profile-section-route-lock-catalog",
            "freezes section route fields without opening runtime behavior"),
        module(
            231,
            "signed-approval-draft-profile-section-registry-route",
            "exposes the read-only registry as a Java evidence endpoint"));
  }

  private static OpsShardReadinessSignedApprovalDraftProfileSectionRegistryResponse.ModuleEntry
      module(int order, String code, String responsibility) {
    return new OpsShardReadinessSignedApprovalDraftProfileSectionRegistryResponse.ModuleEntry(
        order, code, responsibility, "passed");
  }
}
