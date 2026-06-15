package com.codexdemo.orderplatform.ops.maintenance.candidatedocument;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckSupport {

  static final String PROJECT = "advanced-order-platform";
  static final String SOURCE_PLAN = "Node v1456";
  static final String SOURCE_NODE_MATERIAL_REQUEST_VERSION = "Node v1446";
  static final String MATERIAL_SUBMISSION_PRECHECK_STATE =
      "waiting-for-reviewed-real-candidate-document-material-submission";
  static final int EXPECTED_MODULE_COUNT = 5;
  static final int EXPECTED_CHECKPOINT_COUNT = 10;
  static final int EXPECTED_VALIDATOR_COUNT = 10;
  static final int EXPECTED_SOURCE_REQUEST_ITEM_COUNT = 25;
  static final int EXPECTED_SOURCE_ACCEPTANCE_CHECK_COUNT = 25;
  static final int EXPECTED_REQUIRED_MATERIAL_FIELD_COUNT = 20;
  static final int EXPECTED_SUBMISSION_MATERIAL_FIELD_COUNT = 20;
  static final int EXPECTED_ARTIFACT_COUNT = 8;
  static final int EXPECTED_GATE_COUNT = 41;

  private OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckSupport() {}

  static OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckResponse response(
      String version,
      String endpoint,
      String profile,
      OpsShardReadinessCandidateDocumentMaterialRequestResponse sourceRequest,
      List<OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckResponse.ModuleEntry>
          modules,
      List<
              OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckResponse
                  .SubmissionCheckpoint>
          checkpoints,
      List<OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckResponse.Validator>
          validators,
      List<OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckResponse.Artifact> artifacts,
      List<String> gates,
      List<String> additionalChecks) {
    var moduleCopy = List.copyOf(modules);
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
    checks.add("candidate-document-material-submission-precheck-source-plan-" + SOURCE_PLAN);
    checks.add(
        "candidate-document-material-submission-precheck-source-node-"
            + SOURCE_NODE_MATERIAL_REQUEST_VERSION);
    checks.add(
        "candidate-document-material-submission-precheck-source-java-material-request-"
            + sourceRequest.version());
    checks.add(
        "candidate-document-material-submission-precheck-source-route-" + sourceRequest.endpoint());
    checks.add("candidate-document-material-submission-precheck-module-count-" + moduleCopy.size());
    checks.add(
        "candidate-document-material-submission-precheck-checkpoint-count-"
            + checkpointCopy.size());
    checks.add(
        "candidate-document-material-submission-precheck-validator-count-" + validatorCopy.size());
    checks.add(
        "candidate-document-material-submission-precheck-source-request-item-count-"
            + sourceRequest.requestItemCount());
    checks.add(
        "candidate-document-material-submission-precheck-source-acceptance-check-count-"
            + sourceRequest.acceptanceCheckCount());
    checks.add(
        "candidate-document-material-submission-precheck-required-field-count-"
            + EXPECTED_REQUIRED_MATERIAL_FIELD_COUNT);
    checks.add(
        "candidate-document-material-submission-precheck-submission-field-count-"
            + EXPECTED_SUBMISSION_MATERIAL_FIELD_COUNT);
    checks.add(
        "candidate-document-material-submission-precheck-artifact-count-" + artifactCopy.size());
    checks.add("candidate-document-material-submission-precheck-gate-count-" + gateCopy.size());
    checks.add("candidate-document-material-submission-precheck-zero-documents");
    checks.add("candidate-document-material-submission-precheck-zero-payloads");
    checks.add("candidate-document-material-submission-precheck-material-submission-disabled");
    checks.add("candidate-document-material-submission-precheck-import-disabled");
    checks.add("candidate-document-material-submission-precheck-evaluation-disabled");
    checks.add("candidate-document-material-submission-precheck-approval-disabled");
    checks.add("candidate-document-material-submission-precheck-signature-capture-disabled");
    checks.add("candidate-document-material-submission-precheck-runtime-disabled");
    checks.add("candidate-document-material-submission-precheck-write-disabled");
    checks.add("candidate-document-material-submission-precheck-sibling-mutation-disabled");
    checks.addAll(additionalChecks);

    return new OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckResponse(
        PROJECT,
        version,
        true,
        false,
        true,
        SOURCE_PLAN,
        SOURCE_NODE_MATERIAL_REQUEST_VERSION,
        sourceRequest.version(),
        sourceRequest.endpoint(),
        MATERIAL_SUBMISSION_PRECHECK_STATE,
        endpoint,
        profile,
        moduleCopy.size(),
        checkpointCopy.size(),
        passedCheckpointCount,
        validatorCopy.size(),
        passedValidatorCount,
        sourceRequest.requestItemCount(),
        sourceRequest.acceptanceCheckCount(),
        EXPECTED_REQUIRED_MATERIAL_FIELD_COUNT,
        EXPECTED_SUBMISSION_MATERIAL_FIELD_COUNT,
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
        false,
        moduleCopy,
        checkpointCopy,
        validatorCopy,
        artifactCopy,
        gateCopy,
        List.copyOf(checks),
        isComplete(sourceRequest, moduleCopy, checkpointCopy, validatorCopy, artifactCopy, gateCopy)
                && passedCheckpointCount == checkpointCopy.size()
                && passedValidatorCount == validatorCopy.size()
            ? "passed"
            : "blocked");
  }

  private static boolean isComplete(
      OpsShardReadinessCandidateDocumentMaterialRequestResponse sourceRequest,
      List<OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckResponse.ModuleEntry>
          modules,
      List<
              OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckResponse
                  .SubmissionCheckpoint>
          checkpoints,
      List<OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckResponse.Validator>
          validators,
      List<OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckResponse.Artifact> artifacts,
      List<String> gates) {
    return modules.size() == EXPECTED_MODULE_COUNT
        && checkpoints.size() == EXPECTED_CHECKPOINT_COUNT
        && validators.size() == EXPECTED_VALIDATOR_COUNT
        && sourceRequest.requestItemCount() == EXPECTED_SOURCE_REQUEST_ITEM_COUNT
        && sourceRequest.acceptanceCheckCount() == EXPECTED_SOURCE_ACCEPTANCE_CHECK_COUNT
        && artifacts.size() == EXPECTED_ARTIFACT_COUNT
        && gates.size() == EXPECTED_GATE_COUNT;
  }
}
