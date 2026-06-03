package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessRouteCleanupCompletionCertificateResponse(
        String project,
        String version,
        boolean readOnly,
        boolean executionAllowed,
        String completionCertificateEndpoint,
        String completionCertificateProfile,
        String completionIndexEndpoint,
        String thirdRunCloseoutEndpoint,
        String finalArchivePlanEndpoint,
        int certificateClaimCount,
        List<CertificateClaim> certificateClaims,
        String certificateId,
        String decision,
        String status
) {

    public record CertificateClaim(
            String name,
            String evidence,
            String status
    ) {
    }
}
