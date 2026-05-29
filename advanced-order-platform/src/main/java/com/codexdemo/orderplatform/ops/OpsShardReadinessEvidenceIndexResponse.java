package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessEvidenceIndexResponse(
        String project,
        String version,
        boolean readOnly,
        boolean executionAllowed,
        String lastConsumedByNodeVersion,
        List<String> requiredContractFields,
        List<EvidenceEntry> evidenceEntries,
        List<String> fallbackPolicy,
        List<String> compatibilityGuarantees,
        String evidencePath,
        String status
) {

    public record EvidenceEntry(
            String evidenceVersion,
            String evidenceRole,
            String endpoint,
            String fixtureEndpoint,
            String archivePath,
            boolean frozen,
            boolean rollingCurrentPointer,
            String consumerBoundary
    ) {
    }
}
