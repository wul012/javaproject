package com.codexdemo.orderplatform.notification;

import java.time.Instant;
import java.util.List;

public record FailedEventReplayEvidenceIndexResponse(
        Instant sampledAt,
        String evidenceVersion,
        boolean readOnly,
        boolean executionAllowed,
        List<LiveEvidenceEndpoint> liveEvidenceEndpoints,
        List<StaticEvidenceSample> staticEvidenceSamples,
        List<String> auditIdentityFields,
        List<String> executionSafetyRules,
        List<String> productionReadinessNotes
) {

    public record LiveEvidenceEndpoint(
            String name,
            String method,
            String path,
            String purpose,
            boolean readOnly,
            boolean changesReplayState
    ) {
    }

    public record StaticEvidenceSample(
            String name,
            String path,
            String scenario,
            String evidenceVersion,
            List<String> requiredFields
    ) {
    }
}
