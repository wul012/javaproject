package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessRouteCleanupConsumerSignoffPacketResponse(
        String project,
        String version,
        boolean readOnly,
        boolean executionAllowed,
        String consumerSignoffPacketEndpoint,
        String consumerSignoffPacketProfile,
        String releaseEvidenceBundleEndpoint,
        String policyGuardEndpoint,
        int signoffItemCount,
        List<SignoffItem> signoffItems,
        String consumerInstruction,
        String status
) {

    public record SignoffItem(
            String name,
            String evidence,
            String status
    ) {
    }
}
