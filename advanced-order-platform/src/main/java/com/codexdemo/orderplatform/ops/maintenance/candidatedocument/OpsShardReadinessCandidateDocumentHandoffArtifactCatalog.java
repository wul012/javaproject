package com.codexdemo.orderplatform.ops.maintenance.candidatedocument;

import java.util.List;

final class OpsShardReadinessCandidateDocumentHandoffArtifactCatalog {

  private OpsShardReadinessCandidateDocumentHandoffArtifactCatalog() {}

  static List<OpsShardReadinessCandidateDocumentHandoffResponse.ArtifactHandle> artifactHandles(
      OpsShardReadinessCandidateDocumentRequestPackageResponse sourcePackage) {
    return sourcePackage.requestItems().stream()
        .map(OpsShardReadinessCandidateDocumentHandoffArtifactCatalog::handle)
        .toList();
  }

  private static OpsShardReadinessCandidateDocumentHandoffResponse.ArtifactHandle handle(
      OpsShardReadinessCandidateDocumentRequestPackageResponse.RequestItem item) {
    String slug = slug(item.code());
    return new OpsShardReadinessCandidateDocumentHandoffResponse.ArtifactHandle(
        item.code(),
        item.sourceIntakeSlot(),
        item.requestedFields(),
        "candidate-document-request-package/evidence/" + slug + ".json",
        "candidate-document-request-package/digests/" + slug + ".sha256",
        "candidate-document-request-package/archive/" + slug + ".md",
        "waiting-for-reviewed-real-document",
        "passed");
  }

  private static String slug(String value) {
    return value.toLowerCase().replaceAll("[^a-z0-9]+", "-").replaceAll("(^-|-$)", "");
  }
}
