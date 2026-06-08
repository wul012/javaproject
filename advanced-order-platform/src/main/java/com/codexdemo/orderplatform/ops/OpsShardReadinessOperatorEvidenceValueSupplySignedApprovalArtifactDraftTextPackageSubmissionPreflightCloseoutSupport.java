package com.codexdemo.orderplatform.ops;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutSupport {

    static final String PROJECT = "advanced-order-platform";
    static final String SOURCE_PLAN = "Node v1286";
    static final String SOURCE_JAVA_SUBMISSION_PREFLIGHT_VERSION = "Java v969";
    static final String CLOSEOUT_STATE = "closeout-evidence-only";
    static final String SUBMITTED_PACKAGE_ACCEPTANCE_STATE = "not-accepted";
    static final String SIGNED_DRAFT_TEXT_PARSE_STATE = "not-parsed";
    static final String DETACHED_SIGNATURE_PARSE_STATE = "not-parsed";
    static final String APPROVAL_GRANT_STATE = "not-emitted";
    static final String RUNTIME_PAYLOAD_STATE = "locked";
    static final String SIBLING_MUTATION_STATE = "locked";

    private OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutSupport() {
    }

    static OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutResponse
    response(
            String version,
            String endpoint,
            String profile,
            List<OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutResponse
                    .HandoffItem> handoffItems,
            List<OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutResponse
                    .Guardrail> guardrails,
            List<OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutResponse
                    .RouteEvidence> routeEvidence,
            List<String> additionalChecks
    ) {
        var handoffCopy = List.copyOf(handoffItems);
        var guardrailCopy = List.copyOf(guardrails);
        var routeCopy = List.copyOf(routeEvidence);
        int passedHandoffCount = (int) handoffCopy.stream().filter(item -> "passed".equals(item.status())).count();
        int passedGuardrailCount = (int) guardrailCopy.stream().filter(rule -> "passed".equals(rule.status())).count();
        int passedRouteCount = (int) routeCopy.stream().filter(route -> "passed".equals(route.status())).count();
        List<String> checks = new ArrayList<>();
        checks.add("signed-approval-artifact-draft-text-package-submission-preflight-closeout-handoff-count-"
                + handoffCopy.size());
        checks.add("signed-approval-artifact-draft-text-package-submission-preflight-closeout-guardrail-count-"
                + guardrailCopy.size());
        checks.add("signed-approval-artifact-draft-text-package-submission-preflight-closeout-route-evidence-count-"
                + routeCopy.size());
        checks.add("signed-approval-artifact-draft-text-package-submission-preflight-closeout-source-plan-"
                + SOURCE_PLAN);
        checks.add("signed-approval-artifact-draft-text-package-submission-preflight-closeout-source-java-"
                + SOURCE_JAVA_SUBMISSION_PREFLIGHT_VERSION);
        checks.add("signed-approval-artifact-draft-text-package-submission-preflight-closeout-no-package-acceptance");
        checks.add("signed-approval-artifact-draft-text-package-submission-preflight-closeout-no-draft-text-parsing");
        checks.add("signed-approval-artifact-draft-text-package-submission-preflight-closeout-no-signature-parsing");
        checks.add("signed-approval-artifact-draft-text-package-submission-preflight-closeout-no-approval-grant");
        checks.add("signed-approval-artifact-draft-text-package-submission-preflight-closeout-no-runtime");
        checks.add("signed-approval-artifact-draft-text-package-submission-preflight-closeout-no-sibling-mutation");
        checks.addAll(additionalChecks);

        return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutResponse(
                PROJECT,
                version,
                true,
                false,
                true,
                SOURCE_PLAN,
                SOURCE_JAVA_SUBMISSION_PREFLIGHT_VERSION,
                CLOSEOUT_STATE,
                SUBMITTED_PACKAGE_ACCEPTANCE_STATE,
                SIGNED_DRAFT_TEXT_PARSE_STATE,
                DETACHED_SIGNATURE_PARSE_STATE,
                APPROVAL_GRANT_STATE,
                RUNTIME_PAYLOAD_STATE,
                SIBLING_MUTATION_STATE,
                false,
                false,
                false,
                false,
                false,
                false,
                endpoint,
                profile,
                handoffCopy.size(),
                passedHandoffCount,
                guardrailCopy.size(),
                passedGuardrailCount,
                routeCopy.size(),
                passedRouteCount,
                handoffCopy,
                guardrailCopy,
                routeCopy,
                List.copyOf(checks),
                passedHandoffCount == handoffCopy.size()
                        && passedGuardrailCount == guardrailCopy.size()
                        && passedRouteCount == routeCopy.size()
                        ? "passed"
                        : "blocked"
        );
    }

    static OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutResponse
            .HandoffItem handoff(String code, String category, String item, String evidence, String sourceEndpoint) {
        return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutResponse
                .HandoffItem(code, category, item, evidence, sourceEndpoint, "passed");
    }

    static OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutResponse
            .Guardrail guardrail(String code, String category, String rule) {
        return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutResponse
                .Guardrail(code, category, rule, "fail-closed", "passed");
    }

    static OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutResponse
            .RouteEvidence route(String code, String route, String purpose) {
        return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutResponse
                .RouteEvidence(code, route, purpose, "GET", "passed");
    }
}

