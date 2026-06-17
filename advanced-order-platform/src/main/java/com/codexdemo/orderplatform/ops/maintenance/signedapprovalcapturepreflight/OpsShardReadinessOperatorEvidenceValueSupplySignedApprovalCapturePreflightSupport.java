package com.codexdemo.orderplatform.ops.maintenance.signedapprovalcapturepreflight;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightSupport {

    static final String PROJECT = "advanced-order-platform";
    static final String SOURCE_PLAN = "Node v1061";
    static final String SOURCE_TEMPLATE_VERSION = "Node v1036";
    static final String SOURCE_APPROVAL_PACKET_REVIEW_VERSION = "Node v1011";
    static final String SOURCE_APPROVAL_PREFLIGHT_VERSION = "Java v709";
    static final String CAPTURE_PREFLIGHT_STATE = "derived-only";
    static final String SIGNED_APPROVAL_CAPTURE_STATE = "not-captured";
    static final String APPROVAL_GRANT_STATE = "not-emitted";
    static final String OPERATOR_VALUE_SUBMISSION_STATE = "locked";
    static final String EVIDENCE_IMPORT_STATE = "locked";
    static final String RUNTIME_STATE = "locked";
    static final String SIBLING_MUTATION_STATE = "locked";

    private OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightSupport() {
    }

    static OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightResponse response(
            String version,
            String endpoint,
            String profile,
            List<OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightResponse.CaptureInput>
                    inputs,
            List<OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightResponse.CaptureAttestation>
                    attestations,
            List<OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightResponse.CapturePolicy>
                    policies,
            List<String> additionalChecks
    ) {
        List<OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightResponse.CaptureInput>
                inputCopy = List.copyOf(inputs);
        List<OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightResponse.CaptureAttestation>
                attestationCopy = List.copyOf(attestations);
        List<OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightResponse.CapturePolicy>
                policyCopy = List.copyOf(policies);
        int passedInputCount = (int) inputCopy.stream()
                .filter(input -> "passed".equals(input.status()))
                .count();
        int passedAttestationCount = (int) attestationCopy.stream()
                .filter(attestation -> "passed".equals(attestation.status()))
                .count();
        List<String> checks = new ArrayList<>();
        checks.add("signed-approval-capture-preflight-input-count-" + inputCopy.size());
        checks.add("signed-approval-capture-preflight-passed-input-count-" + passedInputCount);
        checks.add("signed-approval-capture-preflight-attestation-count-" + attestationCopy.size());
        checks.add("signed-approval-capture-preflight-passed-attestation-count-" + passedAttestationCount);
        checks.add("signed-approval-capture-preflight-policy-count-" + policyCopy.size());
        checks.add("signed-approval-capture-preflight-source-plan-" + SOURCE_PLAN);
        checks.add("signed-approval-capture-preflight-source-template-" + SOURCE_TEMPLATE_VERSION);
        checks.add("signed-approval-capture-preflight-source-review-" + SOURCE_APPROVAL_PACKET_REVIEW_VERSION);
        checks.add("signed-approval-capture-preflight-source-java-preflight-" + SOURCE_APPROVAL_PREFLIGHT_VERSION);
        checks.add("signed-approval-capture-preflight-derived-only");
        checks.add("signed-approval-capture-preflight-no-signed-approval-capture");
        checks.add("signed-approval-capture-preflight-no-approval-grant");
        checks.add("signed-approval-capture-preflight-no-operator-values");
        checks.add("signed-approval-capture-preflight-no-evidence-import");
        checks.add("signed-approval-capture-preflight-no-runtime-payload");
        checks.add("signed-approval-capture-preflight-no-sibling-mutation");
        checks.addAll(additionalChecks);

        return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightResponse(
                PROJECT,
                version,
                true,
                false,
                true,
                SOURCE_PLAN,
                SOURCE_TEMPLATE_VERSION,
                SOURCE_APPROVAL_PACKET_REVIEW_VERSION,
                SOURCE_APPROVAL_PREFLIGHT_VERSION,
                CAPTURE_PREFLIGHT_STATE,
                SIGNED_APPROVAL_CAPTURE_STATE,
                APPROVAL_GRANT_STATE,
                OPERATOR_VALUE_SUBMISSION_STATE,
                EVIDENCE_IMPORT_STATE,
                RUNTIME_STATE,
                SIBLING_MUTATION_STATE,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                endpoint,
                profile,
                inputCopy.size(),
                passedInputCount,
                attestationCopy.size(),
                passedAttestationCount,
                policyCopy.size(),
                inputCopy,
                attestationCopy,
                policyCopy,
                List.copyOf(checks),
                passedInputCount == inputCopy.size() && passedAttestationCount == attestationCopy.size()
                        ? "passed"
                        : "blocked"
        );
    }

    static OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightResponse.CaptureInput input(
            String code,
            String sourceTemplateField,
            String captureStage,
            String inputRequirement,
            String blockedReason,
            String evidenceFileId,
            String evidenceSnippetId,
            String sourceEndpoint
    ) {
        return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightResponse.CaptureInput(
                code,
                sourceTemplateField,
                captureStage,
                inputRequirement,
                blockedReason,
                evidenceFileId,
                evidenceSnippetId,
                sourceEndpoint,
                "passed"
        );
    }

    static OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightResponse.CaptureAttestation
    attestation(String code, String category, String attestation, String enforcement) {
        return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightResponse
                .CaptureAttestation(code, category, attestation, enforcement, "passed");
    }

    static OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightResponse.CapturePolicy policy(
            String code,
            String category,
            String policy,
            String enforcement
    ) {
        return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightResponse.CapturePolicy(
                code,
                category,
                policy,
                enforcement
        );
    }
}
