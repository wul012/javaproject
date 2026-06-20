package com.codexdemo.orderplatform.ops.maintenance.signedapprovaldraftprofilesection;

import java.util.List;

final class OpsShardReadinessSignedApprovalDraftProfileSectionCatalog {

  private OpsShardReadinessSignedApprovalDraftProfileSectionCatalog() {}

  static List<
          OpsShardReadinessSignedApprovalDraftProfileSectionRegistryResponse.DraftProfileSection>
      sections(
          List<
                  OpsShardReadinessSignedApprovalDraftProfileSectionRegistryResponse
                      .DraftSectionSource>
              sources) {
    return sources.stream()
        .map(
            source ->
                new OpsShardReadinessSignedApprovalDraftProfileSectionRegistryResponse
                    .DraftProfileSection(
                    source.order(),
                    source.code() + "-section",
                    heading(source.code()),
                    source.javaVersion(),
                    source.nodeVersionMarker(),
                    source.endpoint(),
                    source.profile(),
                    source.sourceGateCount(),
                    source.sourceStatus(),
                    6,
                    "signed-approval-draft-profile-section-renderer",
                    "passed"))
        .toList();
  }

  private static String heading(String code) {
    return switch (code) {
      case "signed-approval-artifact-draft-preflight" -> "Signed Approval Artifact Draft Preflight";
      case "signed-approval-artifact-draft-readiness" -> "Signed Approval Artifact Draft Readiness";
      case "signed-approval-artifact-draft-review-package-preflight" ->
          "Signed Approval Artifact Draft Review Package Preflight";
      case "signed-approval-artifact-draft-authoring-readiness" ->
          "Signed Approval Artifact Draft Authoring Readiness";
      case "signed-approval-artifact-draft-instruction-preflight" ->
          "Signed Approval Artifact Draft Instruction Preflight";
      default -> "Signed Approval Draft Section";
    };
  }
}
