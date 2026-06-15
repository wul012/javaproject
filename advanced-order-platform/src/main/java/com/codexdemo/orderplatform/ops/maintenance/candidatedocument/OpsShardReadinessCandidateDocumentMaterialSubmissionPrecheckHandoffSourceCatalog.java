package com.codexdemo.orderplatform.ops.maintenance.candidatedocument;

import java.util.List;

final class OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffSourceCatalog {

  private OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffSourceCatalog() {}

  static List<
          OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffResponse.SourceLineage>
      sourceLineage(
          OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckResponse sourcePrecheck) {
    return List.of(
        lineage(
            1,
            "node-material-submission-precheck-plan",
            OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffSupport.SOURCE_PLAN,
            "D:/nodeproj/orderops-node/docs/plans3/"
                + "v1456-controlled-read-only-shard-preview-candidate-document-material-"
                + "submission-precheck-roadmap.md"),
        lineage(
            2,
            "java-material-submission-precheck-route",
            sourcePrecheck.version(),
            sourcePrecheck.endpoint()),
        lineage(
            3,
            "java-material-submission-precheck-profile",
            sourcePrecheck.profile(),
            "read-only profile"),
        lineage(
            4,
            "java-material-submission-precheck-checkpoints",
            Integer.toString(sourcePrecheck.checkpointCount()),
            "checkpoint catalog"),
        lineage(
            5,
            "java-material-submission-precheck-validators",
            Integer.toString(sourcePrecheck.validatorCount()),
            "validator catalog"),
        lineage(
            6,
            "java-material-submission-precheck-artifacts-and-gates",
            sourcePrecheck.artifactCount() + "/" + sourcePrecheck.gateCount(),
            "artifact and gate closeout"));
  }

  private static OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffResponse
          .SourceLineage
      lineage(int order, String code, String source, String reference) {
    return new OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffResponse
        .SourceLineage(order, code, source, reference, "passed");
  }
}
