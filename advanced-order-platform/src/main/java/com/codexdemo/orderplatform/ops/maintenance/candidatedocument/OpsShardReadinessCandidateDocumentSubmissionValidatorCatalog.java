package com.codexdemo.orderplatform.ops.maintenance.candidatedocument;

import java.util.List;

final class OpsShardReadinessCandidateDocumentSubmissionValidatorCatalog {

  private OpsShardReadinessCandidateDocumentSubmissionValidatorCatalog() {}

  static List<OpsShardReadinessCandidateDocumentSubmissionPrecheckResponse.Validator> validators(
      List<OpsShardReadinessCandidateDocumentSubmissionPrecheckResponse.Checkpoint> checkpoints) {
    return checkpoints.stream()
        .map(OpsShardReadinessCandidateDocumentSubmissionValidatorCatalog::validator)
        .toList();
  }

  private static OpsShardReadinessCandidateDocumentSubmissionPrecheckResponse.Validator validator(
      OpsShardReadinessCandidateDocumentSubmissionPrecheckResponse.Checkpoint checkpoint) {
    return new OpsShardReadinessCandidateDocumentSubmissionPrecheckResponse.Validator(
        checkpoint.code() + "-validator",
        checkpoint.code(),
        "reject-submission-precheck-" + checkpoint.sourceCode(),
        "Reject submission precheck until " + checkpoint.instruction(),
        "fail-closed",
        "passed");
  }
}
