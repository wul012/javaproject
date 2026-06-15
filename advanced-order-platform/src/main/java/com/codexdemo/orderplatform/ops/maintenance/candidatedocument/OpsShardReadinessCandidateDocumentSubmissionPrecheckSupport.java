package com.codexdemo.orderplatform.ops.maintenance.candidatedocument;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessCandidateDocumentSubmissionPrecheckSupport {

  static final String PROJECT = "advanced-order-platform";
  static final String SOURCE_PLAN = "Node v1411";
  static final String SOURCE_NODE_REQUEST_PACKAGE_VERSION = "Node v1386";
  static final String PRECHECK_STATE =
      "waiting-for-reviewed-real-compared-package-evidence-candidate-document";
  static final int EXPECTED_CHECKPOINT_COUNT = 25;
  static final int EXPECTED_VALIDATOR_COUNT = 25;
  static final int REQUESTED_CANDIDATE_FIELD_COUNT = 20;
  static final int EXPECTED_ARTIFACT_COUNT = 8;
  static final int EXPECTED_GATE_COUNT = 40;

  private OpsShardReadinessCandidateDocumentSubmissionPrecheckSupport() {}

  static OpsShardReadinessCandidateDocumentSubmissionPrecheckResponse response(
      String version,
      String endpoint,
      String profile,
      OpsShardReadinessCandidateDocumentRequestPackageResponse sourcePackage,
      OpsShardReadinessCandidateDocumentHandoffResponse sourceHandoff,
      List<OpsShardReadinessCandidateDocumentSubmissionPrecheckResponse.Checkpoint> checkpoints,
      List<OpsShardReadinessCandidateDocumentSubmissionPrecheckResponse.Validator> validators,
      List<OpsShardReadinessCandidateDocumentSubmissionPrecheckResponse.Artifact> artifacts,
      List<String> gates,
      List<String> additionalChecks) {
    var checkpointCopy = List.copyOf(checkpoints);
    var validatorCopy = List.copyOf(validators);
    var artifactCopy = List.copyOf(artifacts);
    var gateCopy = List.copyOf(gates);
    int passedCheckpointCount =
        (int)
            checkpointCopy.stream()
                .filter(checkpoint -> "passed".equals(checkpoint.status()))
                .count();
    int passedValidatorCount =
        (int)
            validatorCopy.stream().filter(validator -> "passed".equals(validator.status())).count();
    List<String> checks = new ArrayList<>();
    checks.add("candidate-document-submission-precheck-source-plan-" + SOURCE_PLAN);
    checks.add(
        "candidate-document-submission-precheck-source-node-"
            + SOURCE_NODE_REQUEST_PACKAGE_VERSION);
    checks.add(
        "candidate-document-submission-precheck-source-java-request-package-"
            + sourcePackage.version());
    checks.add(
        "candidate-document-submission-precheck-source-java-handoff-" + sourceHandoff.version());
    checks.add("candidate-document-submission-precheck-checkpoint-count-" + checkpointCopy.size());
    checks.add("candidate-document-submission-precheck-validator-count-" + validatorCopy.size());
    checks.add(
        "candidate-document-submission-precheck-requested-field-count-"
            + REQUESTED_CANDIDATE_FIELD_COUNT);
    checks.add("candidate-document-submission-precheck-artifact-count-" + artifactCopy.size());
    checks.add("candidate-document-submission-precheck-gate-count-" + gateCopy.size());
    checks.add("candidate-document-submission-precheck-zero-documents");
    checks.add("candidate-document-submission-precheck-zero-payloads");
    checks.add("candidate-document-submission-precheck-import-disabled");
    checks.add("candidate-document-submission-precheck-evaluation-disabled");
    checks.add("candidate-document-submission-precheck-approval-disabled");
    checks.add("candidate-document-submission-precheck-signature-capture-disabled");
    checks.add("candidate-document-submission-precheck-runtime-disabled");
    checks.add("candidate-document-submission-precheck-write-disabled");
    checks.add("candidate-document-submission-precheck-sibling-mutation-disabled");
    checks.addAll(additionalChecks);

    return new OpsShardReadinessCandidateDocumentSubmissionPrecheckResponse(
        PROJECT,
        version,
        true,
        false,
        true,
        SOURCE_PLAN,
        SOURCE_NODE_REQUEST_PACKAGE_VERSION,
        sourcePackage.version(),
        sourceHandoff.version(),
        sourcePackage.endpoint(),
        sourceHandoff.endpoint(),
        PRECHECK_STATE,
        endpoint,
        profile,
        checkpointCopy.size(),
        passedCheckpointCount,
        validatorCopy.size(),
        passedValidatorCount,
        REQUESTED_CANDIDATE_FIELD_COUNT,
        artifactCopy.size(),
        gateCopy.size(),
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        checkpointCopy,
        validatorCopy,
        artifactCopy,
        gateCopy,
        List.copyOf(checks),
        isComplete(checkpointCopy, validatorCopy, artifactCopy, gateCopy)
                && passedCheckpointCount == checkpointCopy.size()
                && passedValidatorCount == validatorCopy.size()
            ? "passed"
            : "blocked");
  }

  private static boolean isComplete(
      List<OpsShardReadinessCandidateDocumentSubmissionPrecheckResponse.Checkpoint> checkpoints,
      List<OpsShardReadinessCandidateDocumentSubmissionPrecheckResponse.Validator> validators,
      List<OpsShardReadinessCandidateDocumentSubmissionPrecheckResponse.Artifact> artifacts,
      List<String> gates) {
    return checkpoints.size() == EXPECTED_CHECKPOINT_COUNT
        && validators.size() == EXPECTED_VALIDATOR_COUNT
        && artifacts.size() == EXPECTED_ARTIFACT_COUNT
        && gates.size() == EXPECTED_GATE_COUNT;
  }
}
