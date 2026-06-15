package com.codexdemo.orderplatform.ops.maintenance.candidatedocument;

import java.util.List;

final class OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffConsumerCatalog {

  private OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffConsumerCatalog() {}

  static List<
          OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffResponse.ConsumerRule>
      consumerRules(
          OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckResponse sourcePrecheck) {
    return sourcePrecheck.checkpoints().stream()
        .map(
            checkpoint ->
                new OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffResponse
                    .ConsumerRule(
                    "consumer-read-only-" + checkpoint.code(),
                    checkpoint.code(),
                    "read archive handle and policy lock",
                    "submit, import, evaluate, approve, sign, execute, write, or mutate material",
                    "passed"))
        .toList();
  }
}
