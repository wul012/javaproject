package com.codexdemo.orderplatform.ops.maintenance.candidatedocument;

import java.util.List;

final class OpsShardReadinessCandidateDocumentHandoffConsumerCatalog {

  private OpsShardReadinessCandidateDocumentHandoffConsumerCatalog() {}

  static List<OpsShardReadinessCandidateDocumentHandoffResponse.ConsumerRule> consumerRules() {
    return List.of(
        rule(
            "consume-versioned-route-only",
            "Consumers read the handoff route response and tag, not a rolling filesystem pointer."),
        rule(
            "require-reviewed-real-document",
            "A future intake remains blocked until a reviewed real compared package evidence document exists."),
        rule("reject-missing-document", "Missing candidate documents remain a fail-closed state."),
        rule(
            "reject-synthetic-document",
            "Synthetic or generated stand-ins cannot satisfy the request package."),
        rule(
            "quarantine-unreviewed-document",
            "Unreviewed source material stays outside import and evaluation paths."),
        rule("do-not-import-payload", "The handoff cannot import or stage document payloads."),
        rule(
            "do-not-evaluate-candidate",
            "The handoff cannot score, accept, reject, approve, or sign a candidate."),
        rule("do-not-open-runtime", "Runtime execution and active shard routing remain closed."),
        rule(
            "do-not-write-routing",
            "Write routing, managed audit connection, and rollback paths remain out of scope."),
        rule(
            "do-not-mutate-siblings",
            "The Java handoff cannot start, stop, or mutate Node or mini-kv workspaces."));
  }

  private static OpsShardReadinessCandidateDocumentHandoffResponse.ConsumerRule rule(
      String code, String rule) {
    return new OpsShardReadinessCandidateDocumentHandoffResponse.ConsumerRule(
        code, rule, "fail-closed", "passed");
  }
}
