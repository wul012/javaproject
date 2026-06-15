package com.codexdemo.orderplatform.ops.maintenance.candidatedocument;

import java.util.List;

final class OpsShardReadinessCandidateDocumentHandoffModuleCatalog {

  private OpsShardReadinessCandidateDocumentHandoffModuleCatalog() {}

  static List<OpsShardReadinessCandidateDocumentHandoffResponse.ModuleEntry> modules() {
    return List.of(
        module(
            190,
            "source-lineage",
            "locks Node plan, Node intake, Java intake, and Java request package references"),
        module(
            191,
            "artifact-handles",
            "maps each request item to a versioned evidence, digest, and archive handle"),
        module(
            192,
            "policy-locks",
            "keeps each acceptance check fail-closed before real document intake exists"),
        module(
            193,
            "archive-closeout",
            "names durable handoff archive entries without writing documents or payloads"),
        module(
            194,
            "consumer-rules",
            "records how a future consumer can read this handoff without opening execution"));
  }

  private static OpsShardReadinessCandidateDocumentHandoffResponse.ModuleEntry module(
      int order, String code, String responsibility) {
    return new OpsShardReadinessCandidateDocumentHandoffResponse.ModuleEntry(
        order, code, responsibility, "java shard readiness owner", "passed");
  }
}
