package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessCandidateDocumentHandoffPolicyCatalog {

    private OpsShardReadinessCandidateDocumentHandoffPolicyCatalog() {
    }

    static List<OpsShardReadinessCandidateDocumentHandoffResponse.PolicyLock> policyLocks(
            OpsShardReadinessCandidateDocumentRequestPackageResponse sourcePackage
    ) {
        return sourcePackage.acceptanceChecks().stream()
                .map(OpsShardReadinessCandidateDocumentHandoffPolicyCatalog::lock)
                .toList();
    }

    private static OpsShardReadinessCandidateDocumentHandoffResponse.PolicyLock lock(
            OpsShardReadinessCandidateDocumentRequestPackageResponse.AcceptanceCheck check
    ) {
        return new OpsShardReadinessCandidateDocumentHandoffResponse.PolicyLock(
                check.code(),
                check.category(),
                check.rejectionCode(),
                "freeze-until-reviewed-real-document-is-present",
                check.enforcement(),
                "passed"
        );
    }
}
