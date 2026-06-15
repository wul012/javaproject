package com.codexdemo.orderplatform.ops.maintenance.candidatedocument;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffGateAggregateTests {

  @Test
  void gateCatalogIsDistinctAndMatchesAggregateCount() {
    var handoff =
        OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffTestSupport.handoff();

    assertThat(handoff.gates())
        .hasSize(42)
        .doesNotHaveDuplicates()
        .allSatisfy(
            gate ->
                assertThat(gate)
                    .startsWith(
                        "candidate-document-material-submission-precheck-handoff-no-material-gate-"));
    assertThat(handoff.gateCount()).isEqualTo(handoff.gates().size());
    assertThat(handoff.sourceGateCount()).isEqualTo(41);
  }

  @Test
  void aggregateListSizesMatchPublishedCounts() {
    var handoff =
        OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffTestSupport.handoff();

    assertThat(handoff.sourceLineageCount()).isEqualTo(handoff.sourceLineage().size());
    assertThat(handoff.moduleCount()).isEqualTo(handoff.modules().size());
    assertThat(handoff.archiveHandleCount()).isEqualTo(handoff.archiveHandles().size());
    assertThat(handoff.policyLockCount()).isEqualTo(handoff.policyLocks().size());
    assertThat(handoff.artifactReferenceCount()).isEqualTo(handoff.artifactReferences().size());
    assertThat(handoff.consumerRuleCount()).isEqualTo(handoff.consumerRules().size());
  }
}
