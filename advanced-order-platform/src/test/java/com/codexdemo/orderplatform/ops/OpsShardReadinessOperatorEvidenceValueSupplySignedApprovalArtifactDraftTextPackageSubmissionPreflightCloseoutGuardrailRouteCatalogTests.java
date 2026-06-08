package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutGuardrailRouteCatalogTests {

    @Test
    void exposesFailClosedGuardrailsAndReadOnlyRouteEvidence() {
        var guardrails = OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutGuardrailCatalog
                .allGuardrails();
        var routes = OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutRouteEvidenceCatalog
                .allRoutes();

        assertThat(guardrails).hasSize(12);
        assertThat(guardrails).extracting(
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutResponse
                        .Guardrail::enforcement
        ).containsOnly("fail-closed");
        assertThat(routes).hasSize(11);
        assertThat(routes).extracting(
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutResponse
                        .RouteEvidence::method
        ).containsOnly("GET");
        assertThat(OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutAcceptanceStateCatalog
                .acceptanceChecks()).contains("submitted-package-acceptance-state-not-accepted");
    }
}

