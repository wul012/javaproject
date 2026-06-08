package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutAcceptanceStateCatalog {

    private OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutAcceptanceStateCatalog() {
    }

    static List<String> acceptanceChecks() {
        return List.of(
                "submitted-package-acceptance-state-not-accepted",
                "signed-draft-text-parse-state-not-parsed",
                "detached-signature-parse-state-not-parsed",
                "approval-grant-state-not-emitted",
                "runtime-payload-state-locked",
                "sibling-mutation-state-locked",
                "manual-submission-closeout-does-not-start-node-java-or-mini-kv",
                "manual-submission-closeout-does-not-open-write-routing"
        );
    }
}

