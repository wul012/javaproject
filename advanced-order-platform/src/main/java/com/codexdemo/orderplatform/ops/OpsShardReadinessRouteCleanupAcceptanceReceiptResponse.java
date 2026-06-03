package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessRouteCleanupAcceptanceReceiptResponse(
        String project,
        String version,
        boolean readOnly,
        boolean executionAllowed,
        String receiptEndpoint,
        String receiptProfile,
        String auditTrailEndpoint,
        String closeoutEndpoint,
        int acceptedCriteriaCount,
        List<AcceptedCriterion> acceptedCriteria,
        String receipt,
        String status
) {

    public record AcceptedCriterion(
            String name,
            String evidence,
            boolean required,
            String status
    ) {
    }
}
