package com.codexdemo.orderplatform.ops.maintenance.candidatedocument;

import java.util.List;

final class OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffPolicyCatalog {

  private OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffPolicyCatalog() {}

  static List<
          OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffResponse.PolicyLock>
      policyLocks(
          OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckResponse sourcePrecheck) {
    return sourcePrecheck.validators().stream()
        .map(
            validator ->
                new OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffResponse
                    .PolicyLock(
                    "policy-lock-" + validator.checkpointCode(),
                    validator.code(),
                    validator.rejectionCode(),
                    "Keep material submission blocked until archived precheck evidence is reviewed",
                    validator.enforcement(),
                    "passed"))
        .toList();
  }
}
