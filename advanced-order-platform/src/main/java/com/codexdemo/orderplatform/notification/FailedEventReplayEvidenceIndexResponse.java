package com.codexdemo.orderplatform.notification;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public record FailedEventReplayEvidenceIndexResponse(
        Instant sampledAt,
        String evidenceVersion,
        boolean readOnly,
        boolean executionAllowed,
        List<LiveEvidenceEndpoint> liveEvidenceEndpoints,
        List<StaticEvidenceSample> staticEvidenceSamples,
        OperatorAuthBoundary operatorAuthBoundary,
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

    public record OperatorAuthBoundary(
            String identitySource,
            List<String> requiredHeaders,
            boolean anonymousAllowed,
            boolean javaAuthenticatesCredentials,
            String enforcementMode,
            List<String> globalAllowedRoles,
            Map<FailedEventOperatorAction, List<String>> allowedRolesByAction,
            List<String> normalizationRules,
            List<String> productionAuthGaps
    ) {
    }
}
