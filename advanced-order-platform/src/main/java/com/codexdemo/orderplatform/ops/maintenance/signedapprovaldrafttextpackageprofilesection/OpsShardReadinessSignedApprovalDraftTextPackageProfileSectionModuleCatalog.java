package com.codexdemo.orderplatform.ops.maintenance.signedapprovaldrafttextpackageprofilesection;

import java.util.List;

final class OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionModuleCatalog {

  private OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionModuleCatalog() {}

  static List<
          OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryResponse.ModuleEntry>
      modules() {
    return List.of(
        module(
            248,
            "signed-approval-draft-text-package-profile-section-types",
            "defines the signed approval draft text package profile section records"),
        module(
            249,
            "signed-approval-draft-text-package-profile-section-source-catalog",
            "collects the nine text package route sources"),
        module(
            250,
            "signed-approval-draft-text-package-profile-section-module-catalog",
            "records the Java renderer boundary modules after Node v1531"),
        module(
            251,
            "signed-approval-draft-text-package-profile-section-section-catalog",
            "maps text package sources to stable profile sections"),
        module(
            252,
            "signed-approval-draft-text-package-profile-section-field-catalog",
            "locks endpoint, profile, version, marker, renderer group, and status fields"),
        module(
            253,
            "signed-approval-draft-text-package-profile-section-submission-renderer",
            "renders the first five submission-side text package sections"),
        module(
            254,
            "signed-approval-draft-text-package-profile-section-compared-evidence-renderer",
            "renders the compared evidence and candidate text package sections"),
        module(
            255,
            "signed-approval-draft-text-package-profile-section-aggregate-renderer",
            "preserves the full route-facing text package section order"),
        module(
            256,
            "signed-approval-draft-text-package-profile-section-route-lock-catalog",
            "freezes route-facing fields without opening runtime behavior"),
        module(
            257,
            "signed-approval-draft-text-package-profile-section-registry-route",
            "exposes the read-only text package registry endpoint"));
  }

  private static OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryResponse
          .ModuleEntry
      module(int order, String code, String responsibility) {
    return new OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryResponse
        .ModuleEntry(order, code, responsibility, "passed");
  }
}
