package com.codexdemo.orderplatform;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

final class OpsReleaseApprovalSandboxConnectionEchoTestSupport {

    private static final String REHEARSAL_ENDPOINT = "/api/v1/ops/release-approval-rehearsal";

    private OpsReleaseApprovalSandboxConnectionEchoTestSupport() {
    }

    static MockHttpServletRequestBuilder rehearsalRequest() {
        return get(REHEARSAL_ENDPOINT);
    }

    static MockHttpServletRequestBuilder rehearsalRequestWithSandboxHeaders() {
        return rehearsalRequest()
                .header("X-Rehearsal-Request-Id", "rehearsal-v67-001")
                .header("X-Operator-Identity", "release-operator@example.test")
                .header("X-Audit-Correlation-Id", "audit-correlation-v67")
                .header("x-orderops-operator-id", "operator-198")
                .header("x-orderops-roles", "operator,auditor")
                .header("x-orderops-operator-verified", "true")
                .header("x-orderops-approval-correlation-id", "approval-v198-operator-window")
                .header("x-orderops-ci-manifest-version",
                        "real-read-window-ci-archive-artifact-manifest.v1")
                .header("x-orderops-ci-manifest-digest", "sha256:node-v200-manifest-digest")
                .header("x-orderops-ci-manifest-endpoint",
                        "/api/v1/production/real-read-window-ci-archive-artifact-manifest")
                .header("x-orderops-ci-artifact-record-count", "9")
                .header("x-orderops-ci-approval-correlation-id", "approval-v198-operator-window")
                .header("x-orderops-ci-upload-contract-version",
                        "real-read-window-ci-artifact-upload-dry-run-contract.v1")
                .header("x-orderops-ci-upload-contract-digest",
                        "sha256:node-v202-upload-contract-digest")
                .header("x-orderops-ci-artifact-name", "orderops-real-read-window-evidence-v191-v201")
                .header("x-orderops-ci-artifact-root", "c/")
                .header("x-orderops-ci-retention-days", "30")
                .header("x-orderops-ci-upload-mode", "dry-run-contract-only")
                .header("x-orderops-runtime-preflight-version",
                        "three-project-real-read-runtime-smoke-preflight.v1")
                .header("x-orderops-runtime-preflight-digest",
                        "sha256:node-v204-preflight-digest")
                .header("x-orderops-runtime-smoke-session-id", "runtime-smoke-v205-session-001")
                .header("x-orderops-runtime-read-target-id", "java-release-approval-rehearsal")
                .header("x-orderops-runtime-window-mode", "manual-open-window-plan")
                .header("x-orderops-managed-audit-candidate-version",
                        "managed-audit-persistence-boundary-candidate.v1")
                .header("x-orderops-managed-audit-candidate-digest",
                        "sha256:node-v208-managed-audit-candidate-digest")
                .header("x-orderops-managed-audit-sink-mode", "file-or-sqlite-dry-run-candidate")
                .header("x-orderops-managed-audit-retention-days", "30")
                .header("x-orderops-managed-audit-rotation-policy",
                        "size-and-age-rotation-candidate")
                .header("x-orderops-approval-binding-contract-version",
                        "managed-audit-identity-approval-binding-contract.v1")
                .header("x-orderops-approval-binding-contract-digest",
                        "sha256:node-v210-approval-binding-digest")
                .header("x-orderops-approval-request-id", "approval-request-v210-001")
                .header("x-orderops-approval-decision-state", "APPROVED_DRY_RUN_ONLY")
                .header("x-orderops-approval-record-correlation-id",
                        "approval-record-correlation-v210");
    }
}
