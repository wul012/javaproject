package com.codexdemo.orderplatform.ops.maintenance.candidatedocument;

import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentSubmissionPrecheckResponse.Artifact;
import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentSubmissionPrecheckResponse.Checkpoint;
import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentSubmissionPrecheckResponse.Validator;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

final class SubmissionCatalog {

  private SubmissionCatalog() {}

  static Evidence from(
      OpsShardReadinessCandidateDocumentRequestPackageResponse sourcePackage,
      OpsShardReadinessCandidateDocumentHandoffResponse sourceHandoff) {
    var checkpoints = checkpoints(sourcePackage, sourceHandoff);
    return new Evidence(checkpoints, validators(checkpoints), artifacts(), gates());
  }

  private static List<Checkpoint> checkpoints(
      OpsShardReadinessCandidateDocumentRequestPackageResponse sourcePackage,
      OpsShardReadinessCandidateDocumentHandoffResponse sourceHandoff) {
    var requestCheckpoints =
        sourcePackage.requestItems().stream()
            .map(
                item ->
                    checkpoint(
                        "request-" + item.code(),
                        item.code(),
                        "request-item",
                        "Precheck submitted document against request item: " + item.instruction()))
            .toList();
    var consumerCheckpoints =
        sourceHandoff.consumerRules().stream()
            .map(
                rule ->
                    checkpoint(
                        "consumer-" + rule.code(),
                        rule.code(),
                        "consumer-rule",
                        "Precheck submitted document against handoff rule: " + rule.rule()))
            .toList();
    return Stream.concat(requestCheckpoints.stream(), consumerCheckpoints.stream()).toList();
  }

  private static Checkpoint checkpoint(
      String code, String sourceCode, String category, String instruction) {
    return new Checkpoint(
        code,
        sourceCode,
        category,
        instruction,
        "candidate document submission precheck owner",
        "passed");
  }

  private static List<Validator> validators(List<Checkpoint> checkpoints) {
    return checkpoints.stream().map(SubmissionCatalog::validator).toList();
  }

  private static Validator validator(Checkpoint checkpoint) {
    return new Validator(
        checkpoint.code() + "-validator",
        checkpoint.code(),
        "reject-submission-precheck-" + checkpoint.sourceCode(),
        "Reject submission precheck until " + checkpoint.instruction(),
        "fail-closed",
        "passed");
  }

  private static List<Artifact> artifacts() {
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

  private static List<String> gates() {
    return IntStream.rangeClosed(
            1, OpsShardReadinessCandidateDocumentSubmissionPrecheckSupport.EXPECTED_GATE_COUNT)
        .mapToObj(index -> "candidate-document-submission-precheck-read-only-gate-" + index)
        .toList();
  }

  private static Artifact artifact(String code, String reference, String purpose) {
    return new Artifact(code, reference, purpose, "passed");
  }

  record Evidence(
      List<Checkpoint> checkpoints,
      List<Validator> validators,
      List<Artifact> artifacts,
      List<String> gates) {
    Evidence {
      checkpoints = List.copyOf(checkpoints);
      validators = List.copyOf(validators);
      artifacts = List.copyOf(artifacts);
      gates = List.copyOf(gates);
    }
  }
}
