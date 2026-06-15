package com.codexdemo.orderplatform.ops.maintenance.candidatedocument;

import java.util.List;

final class OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffArtifactCatalog {

  private OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffArtifactCatalog() {}

  static List<
          OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffResponse
              .ArtifactReference>
      artifactReferences(
          OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckResponse sourcePrecheck) {
    return sourcePrecheck.artifacts().stream()
        .map(
            artifact ->
                new OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffResponse
                    .ArtifactReference(
                    "handoff-" + artifact.code(),
                    artifact.reference(),
                    "e/1187/artifacts/" + artifact.code() + ".json",
                    "archive reference for " + artifact.purpose(),
                    "passed"))
        .toList();
  }
}
