package com.codexdemo.orderplatform.ops;

import java.util.List;
import java.util.function.Supplier;

record ReleaseApprovalVerificationHintContribution(
        Supplier<List<String>> warningDigestWarningInputNames,
        Supplier<List<String>> warningDigestBoundaryInputNames,
        Supplier<List<String>> proofClaims,
        Supplier<List<String>> nodeVerificationActions
) {
    List<String> warningDigestWarningInputValues() {
        return warningDigestWarningInputNames.get();
    }

    List<String> warningDigestBoundaryInputValues() {
        return warningDigestBoundaryInputNames.get();
    }

    List<String> proofClaimValues() {
        return proofClaims.get();
    }

    List<String> nodeVerificationActionValues() {
        return nodeVerificationActions.get();
    }
}
