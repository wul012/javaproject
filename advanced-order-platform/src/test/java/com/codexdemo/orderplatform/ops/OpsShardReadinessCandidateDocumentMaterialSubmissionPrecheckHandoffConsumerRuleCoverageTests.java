package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffConsumerRuleCoverageTests {

    @Test
    void consumerRulesCoverEveryCheckpointWithoutGrantingSubmission() {
        var sourcePrecheck = OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffTestSupport
                .sourcePrecheck();
        var consumerRules = OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffConsumerCatalog
                .consumerRules(sourcePrecheck);

        assertThat(consumerRules)
                .extracting(OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffResponse.ConsumerRule::checkpointCode)
                .containsExactlyElementsOf(sourcePrecheck.checkpoints().stream()
                        .map(OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckResponse.SubmissionCheckpoint::code)
                        .toList());
        assertThat(consumerRules)
                .allSatisfy(rule -> assertThat(rule.blockedAction())
                        .contains("submit", "approve", "sign", "execute", "mutate"));
    }
}
