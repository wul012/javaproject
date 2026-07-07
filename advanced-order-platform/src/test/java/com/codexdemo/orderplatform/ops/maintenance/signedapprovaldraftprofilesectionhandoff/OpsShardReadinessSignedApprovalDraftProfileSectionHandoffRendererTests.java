package com.codexdemo.orderplatform.ops.maintenance.signedapprovaldraftprofilesectionhandoff;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessSignedApprovalDraftProfileSectionHandoffRendererTests {

  @Test
  void rendererEmitsOneMarkdownBlockPerHandoffSection() {
    var response = OpsShardReadinessSignedApprovalDraftProfileSectionHandoffTestSupport.handoff();

    assertThat(response.renderedHandoffs()).hasSize(5);
    assertThat(response.renderedHandoffs())
        .allSatisfy(
            handoff -> {
              assertThat(handoff.markdownHeading())
                  .startsWith("### Signed Approval Artifact Draft");
              assertThat(handoff.markdownBody())
                  .contains("- java-version: ", "- endpoint: ", "- profile: ");
              assertThat(handoff.status()).isEqualTo("passed");
            });
  }

  @Test
  void rendererKeepsConsumerBoundaryVisible() {
    var response = OpsShardReadinessSignedApprovalDraftProfileSectionHandoffTestSupport.handoff();

    assertThat(response.renderedHandoffs())
        .allSatisfy(
            handoff ->
                assertThat(handoff.markdownBody())
                    .contains(
                        "- node-marker: Node v",
                        "- route-field-count: 6",
                        "- consumer-boundary: read-only-consumer-no-execution"));
  }
}
