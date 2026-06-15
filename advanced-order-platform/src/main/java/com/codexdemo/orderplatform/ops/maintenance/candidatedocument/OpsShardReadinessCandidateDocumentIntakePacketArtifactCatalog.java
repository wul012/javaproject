package com.codexdemo.orderplatform.ops.maintenance.candidatedocument;

import java.util.List;
import java.util.stream.IntStream;

final class OpsShardReadinessCandidateDocumentIntakePacketArtifactCatalog {

  private OpsShardReadinessCandidateDocumentIntakePacketArtifactCatalog() {}

  static List<OpsShardReadinessCandidateDocumentIntakePacketResponse.Artifact> artifacts() {
    return List.of(
        artifact(
            "source-node-plan",
            "e/1142/source/node-v1421-intake-packet-plan.md",
            "pins the Node intake packet roadmap"),
        artifact(
            "source-submission-precheck",
            "e/1142/source/java-v1117-submission-precheck.json",
            "pins the Java submission precheck consumed by this packet"),
        artifact(
            "source-lineage",
            "e/1142/lineage/candidate-document-intake-packet-source-lineage.json",
            "records source plan, precheck, and future material boundary"),
        artifact(
            "modules",
            "e/1142/modules/candidate-document-intake-packet-modules.json",
            "records the five-way maintenance split"),
        artifact(
            "intake-slots",
            "e/1142/intake/candidate-document-intake-packet-slots.json",
            "lists ten compact intake slots"),
        artifact(
            "intake-guards",
            "e/1142/intake/candidate-document-intake-packet-guards.json",
            "lists ten fail-closed guards"),
        artifact(
            "route-evidence",
            "e/1142/routes/candidate-document-intake-packet-route.json",
            "records read-only route and response profile"),
        artifact(
            "closeout",
            "e/1142/closeout/candidate-document-intake-packet-closeout.md",
            "records no-material stop condition"));
  }

  static List<String> gates() {
    return IntStream.rangeClosed(
            1, OpsShardReadinessCandidateDocumentIntakePacketSupport.EXPECTED_GATE_COUNT)
        .mapToObj(index -> "candidate-document-intake-packet-no-material-gate-" + index)
        .toList();
  }

  private static OpsShardReadinessCandidateDocumentIntakePacketResponse.Artifact artifact(
      String code, String reference, String purpose) {
    return new OpsShardReadinessCandidateDocumentIntakePacketResponse.Artifact(
        code, reference, purpose, "passed");
  }
}
