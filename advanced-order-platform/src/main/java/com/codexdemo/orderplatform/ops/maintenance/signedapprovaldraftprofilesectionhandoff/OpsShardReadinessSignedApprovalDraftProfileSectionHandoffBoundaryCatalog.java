package com.codexdemo.orderplatform.ops.maintenance.signedapprovaldraftprofilesectionhandoff;

import java.util.List;

final class OpsShardReadinessSignedApprovalDraftProfileSectionHandoffBoundaryCatalog {

  private OpsShardReadinessSignedApprovalDraftProfileSectionHandoffBoundaryCatalog() {}

  static List<OpsShardReadinessSignedApprovalDraftProfileSectionHandoffResponse.BoundaryDecision>
      decisions() {
    return List.of(
        decision(
            "draft-artifact-materialization",
            "artifact",
            "blocked",
            "handoff carries metadata only"),
        decision(
            "signed-approval-capture", "approval", "blocked", "no captured signature is accepted"),
        decision("approval-grant", "approval", "blocked", "handoff is not an approval decision"),
        decision(
            "value-import", "evidence-value", "blocked", "handoff does not import operator values"),
        decision("runtime-payload", "runtime", "blocked", "handoff exposes no payload writer"),
        decision("write-routing", "router", "blocked", "handoff has no write route"),
        decision(
            "sibling-mutation", "registry", "blocked", "handoff cannot mutate registry siblings"));
  }

  private static OpsShardReadinessSignedApprovalDraftProfileSectionHandoffResponse.BoundaryDecision
      decision(String code, String boundary, String decision, String evidence) {
    return new OpsShardReadinessSignedApprovalDraftProfileSectionHandoffResponse.BoundaryDecision(
        code, boundary, decision, evidence, "passed");
  }
}
