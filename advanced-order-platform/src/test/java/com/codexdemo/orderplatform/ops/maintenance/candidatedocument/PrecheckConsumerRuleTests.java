package com.codexdemo.orderplatform.ops.maintenance.candidatedocument;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class PrecheckConsumerRuleTests {

  @Test
  void consumerRulesCoverEveryCheckpointWithoutGrantingSubmission() {
    var sourcePrecheck =
        OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffTestSupport
            .sourcePrecheck();
    var consumerRules = PrecheckHandoffCatalog.from(sourcePrecheck).consumerRules();

    assertThat(consumerRules)
        .extracting(
            OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffResponse.ConsumerRule
                ::checkpointCode)
        .containsExactlyElementsOf(
            sourcePrecheck.checkpoints().stream()
                .map(
                    OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckResponse
                            .SubmissionCheckpoint
                        ::code)
                .toList());
    assertThat(consumerRules)
        .allSatisfy(
            rule ->
                assertThat(rule.blockedAction())
                    .contains("submit", "approve", "sign", "execute", "mutate"));
  }
}
