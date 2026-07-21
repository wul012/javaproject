package com.codexdemo.orderplatform.ops.maintenance.signedapprovaldraftprofilesectionhandoff;

import com.codexdemo.orderplatform.ops.maintenance.signedapprovaldraftprofilesectionhandoff.OpsShardReadinessSignedApprovalDraftProfileSectionHandoffResponse.RenderedHandoff;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovaldraftprofilesectionhandoff.OpsShardReadinessSignedApprovalDraftProfileSectionHandoffResponse.SectionHandoff;
import java.util.List;

final class HandoffRenderer {

  private HandoffRenderer() {}

  static List<RenderedHandoff> render(List<SectionHandoff> handoffs) {
    return handoffs.stream()
        .map(
            handoff ->
                new RenderedHandoff(
                    handoff.order(),
                    handoff.sectionCode(),
                    "### " + handoff.heading(),
                    markdownBody(handoff),
                    "passed"))
        .toList();
  }

  private static String markdownBody(SectionHandoff handoff) {
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
