package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessSignedApprovalDraftProfileSectionHandoffMarkdownStabilityTests {

    @Test
    void renderedMarkdownKeepsHandoffSectionOrderStable() {
        var response = OpsShardReadinessSignedApprovalDraftProfileSectionHandoffTestSupport.handoff();

        assertThat(response.renderedHandoffs())
                .extracting(OpsShardReadinessSignedApprovalDraftProfileSectionHandoffResponse
                        .RenderedHandoff::markdownHeading)
                .containsExactly(
                        "### Signed Approval Artifact Draft Preflight",
                        "### Signed Approval Artifact Draft Readiness",
                        "### Signed Approval Artifact Draft Review Package Preflight",
                        "### Signed Approval Artifact Draft Authoring Readiness",
                        "### Signed Approval Artifact Draft Instruction Preflight");
    }

    @Test
    void renderedMarkdownKeepsRouteContractFieldsVisible() {
        var response = OpsShardReadinessSignedApprovalDraftProfileSectionHandoffTestSupport.handoff();

        assertThat(response.renderedHandoffs())
                .allSatisfy(handoff -> assertThat(handoff.markdownBody())
                        .contains(
                                "- java-version: ",
                                "- endpoint: ",
                                "- profile: ",
                                "- node-marker: ",
                                "- route-field-count: ",
                                "- consumer-boundary: "));
    }
}
