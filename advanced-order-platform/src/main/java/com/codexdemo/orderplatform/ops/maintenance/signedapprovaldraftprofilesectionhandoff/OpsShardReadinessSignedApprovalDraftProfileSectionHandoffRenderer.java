package com.codexdemo.orderplatform.ops.maintenance.signedapprovaldraftprofilesectionhandoff;

import java.util.List;

final class OpsShardReadinessSignedApprovalDraftProfileSectionHandoffRenderer {

  private OpsShardReadinessSignedApprovalDraftProfileSectionHandoffRenderer() {}

  static List<OpsShardReadinessSignedApprovalDraftProfileSectionHandoffResponse.RenderedHandoff>
      render(
          List<OpsShardReadinessSignedApprovalDraftProfileSectionHandoffResponse.SectionHandoff>
              handoffs) {
    return handoffs.stream()
        .map(
            handoff ->
                new OpsShardReadinessSignedApprovalDraftProfileSectionHandoffResponse
                    .RenderedHandoff(
                    handoff.order(),
                    handoff.sectionCode(),
                    "### " + handoff.heading(),
                    markdownBody(handoff),
                    "passed"))
        .toList();
  }

  private static String markdownBody(
      OpsShardReadinessSignedApprovalDraftProfileSectionHandoffResponse.SectionHandoff handoff) {
    return String.join(
        "\n",
        "- java-version: " + handoff.javaVersion(),
        "- endpoint: " + handoff.endpoint(),
        "- profile: " + handoff.profile(),
        "- node-marker: " + handoff.nodeVersionMarker(),
        "- route-field-count: " + handoff.routeFieldCount(),
        "- consumer-boundary: " + handoff.consumerBoundary());
  }
}
