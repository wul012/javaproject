package com.codexdemo.orderplatform.ops.maintenance.candidatedocument;

import java.util.List;
import java.util.stream.IntStream;

final class OpsShardReadinessCandidateDocumentSubmissionArtifactCatalog {

  private OpsShardReadinessCandidateDocumentSubmissionArtifactCatalog() {}

  static List<OpsShardReadinessCandidateDocumentSubmissionPrecheckResponse.Artifact> artifacts() {
    return List.of(
        artifact(
            "source-node-plan",
            "e/1117/source/node-v1411-submission-precheck-plan.md",
            "pins the Node submission precheck roadmap"),
        artifact(
            "source-request-package",
            "e/1117/source/java-v1081-request-package.json",
            "pins the Java request package consumed by this precheck"),
        artifact(
            "source-handoff",
            "e/1117/source/java-v1107-handoff.json",
            "pins the Java handoff consumed by this precheck"),
        artifact(
            "checkpoints",
            "e/1117/precheck/submission-checkpoints.json",
            "lists all twenty-five submission checkpoints"),
        artifact(
            "validators",
            "e/1117/precheck/submission-validators.json",
            "lists all twenty-five fail-closed validators"),
        artifact(
            "disabled-boundaries",
            "e/1117/policy/submission-disabled-boundaries.json",
            "records import, evaluation, runtime, write, and sibling mutation locks"),
        artifact(
            "route-evidence",
            "e/1117/routes/candidate-document-submission-precheck-route.json",
            "records the read-only route and profile"),
        artifact(
            "closeout",
            "e/1117/closeout/candidate-document-submission-precheck-closeout.md",
            "records the stop condition before real reviewed document intake"));
  }

  static List<String> gates() {
    return IntStream.rangeClosed(
            1, OpsShardReadinessCandidateDocumentSubmissionPrecheckSupport.EXPECTED_GATE_COUNT)
        .mapToObj(index -> "candidate-document-submission-precheck-read-only-gate-" + index)
        .toList();
  }

  private static OpsShardReadinessCandidateDocumentSubmissionPrecheckResponse.Artifact artifact(
      String code, String reference, String purpose) {
    return new OpsShardReadinessCandidateDocumentSubmissionPrecheckResponse.Artifact(
        code, reference, purpose, "passed");
  }
}
