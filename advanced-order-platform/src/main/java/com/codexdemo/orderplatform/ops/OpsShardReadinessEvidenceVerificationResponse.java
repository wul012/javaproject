package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessEvidenceVerificationResponse(
        String project,
        String version,
        boolean readOnly,
        boolean executionAllowed,
        String sourceIndexVersion,
        String sourceIndexEndpoint,
        String sourceIndexFixtureEndpoint,
        String sourceIndexEvidencePath,
        int verifiedEntryCount,
        List<String> verifiedEvidenceVersions,
        List<VerificationCheck> checks,
        List<String> fallbackPolicy,
        String evidencePath,
        String status
) {

    public record VerificationCheck(
            String checkId,
            String subject,
            boolean passed,
            String detail
    ) {
    }
}
