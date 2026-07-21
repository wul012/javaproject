package com.codexdemo.orderplatform.ops.maintenance.signedapprovaldraftprofilesectionhandoff;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.rendering.MarkdownOracle;
import org.junit.jupiter.api.Test;

class HandoffMarkdownTests {

  @Test
  void rendererEmitsOneMarkdownBlockPerHandoffSection() {
    var response = HandoffTestData.handoff();

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
    var response = HandoffTestData.handoff();

    assertThat(response.renderedHandoffs())
        .allSatisfy(
            handoff ->
                assertThat(handoff.markdownBody())
                    .contains(
                        "- node-marker: Node v",
                        "- route-field-count: 6",
                        "- consumer-boundary: read-only-consumer-no-execution"));
    assertThat(response.renderedHandoffs())
        .extracting(handoff -> handoff.markdownBody().lines().count())
        .containsExactly(6L, 6L, 6L, 6L, 6L);
    assertThat(
            MarkdownOracle.sha256(
                response.renderedHandoffs(),
                OpsShardReadinessSignedApprovalDraftProfileSectionHandoffResponse.RenderedHandoff
                    ::markdownHeading,
                handoff -> handoff.markdownBody().lines().toList()))
        .isEqualTo("2cfaf4917eaecff8e5d09dc9f787c785d3067f56f2fa16baa3699f9ccc508d9a");
  }

  @Test
  void rendererKeepsHandoffSectionOrderStable() {
    var response = HandoffTestData.handoff();

    assertThat(response.renderedHandoffs())
        .extracting(
            OpsShardReadinessSignedApprovalDraftProfileSectionHandoffResponse.RenderedHandoff
                ::markdownHeading)
        .containsExactly(
            "### Signed Approval Artifact Draft Preflight",
            "### Signed Approval Artifact Draft Readiness",
            "### Signed Approval Artifact Draft Review Package Preflight",
            "### Signed Approval Artifact Draft Authoring Readiness",
            "### Signed Approval Artifact Draft Instruction Preflight");
  }

  @Test
  void rendererKeepsRouteContractFieldsVisible() {
    var response = HandoffTestData.handoff();

    assertThat(response.renderedHandoffs())
        .allSatisfy(
            handoff ->
                assertThat(handoff.markdownBody())
                    .contains(
                        "- java-version: ",
                        "- endpoint: ",
                        "- profile: ",
                        "- node-marker: ",
                        "- route-field-count: ",
                        "- consumer-boundary: "));
  }
}
