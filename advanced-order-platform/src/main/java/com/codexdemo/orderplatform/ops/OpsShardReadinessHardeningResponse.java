package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessHardeningResponse(
        String project,
        String version,
        boolean readOnly,
        boolean executionAllowed,
        String sourceEvidenceVersion,
        String sourceEndpoint,
        String sourceFixtureEndpoint,
        String sourceEvidencePath,
        List<FieldExplanation> fieldExplanations,
        List<ErrorSemantic> errorSemantics,
        List<String> compatibilityGuarantees,
        List<String> forbiddenChanges,
        String evidencePath,
        String status
) {

    public record FieldExplanation(
            String field,
            String producer,
            String consumer,
            String meaning,
            String compatibility
    ) {
    }

    public record ErrorSemantic(
            String condition,
            String status,
            String nodeInterpretation
    ) {
    }
}
