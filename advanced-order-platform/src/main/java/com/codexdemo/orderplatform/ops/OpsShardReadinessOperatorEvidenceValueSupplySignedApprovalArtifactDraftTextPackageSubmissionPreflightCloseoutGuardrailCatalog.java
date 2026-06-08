package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutGuardrailCatalog {

    private OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutGuardrailCatalog() {
    }

    static List<OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutResponse
            .Guardrail> allGuardrails() {
        return List.of(
                guardrail("submission-closeout-no-package-acceptance", "submission",
                        "submitted package material is not accepted by closeout evidence"),
                guardrail("submission-closeout-no-signed-draft-text-parse", "draft-text",
                        "signed draft text remains opaque and unparsed"),
                guardrail("submission-closeout-no-detached-signature-parse", "signature",
                        "detached signature payload remains opaque and unparsed"),
                guardrail("submission-closeout-no-approval-grant", "approval",
                        "approval grant is not emitted or inferred"),
                guardrail("submission-closeout-no-operator-value-import", "value",
                        "operator value handles are compared as references only"),
                guardrail("submission-closeout-no-runtime-payload", "runtime",
                        "runtime payload remains locked and unavailable"),
                guardrail("submission-closeout-no-write-routing", "routing",
                        "write routing and active shard router stay out of scope"),
                guardrail("submission-closeout-no-sibling-mutation", "sibling",
                        "Java, Node, and mini-kv sibling state are not mutated"),
                guardrail("submission-closeout-no-managed-audit-connection", "audit",
                        "managed audit connections are not opened"),
                guardrail("submission-closeout-no-deployment-rollback", "deployment",
                        "deployment and rollback decisions are not represented"),
                guardrail("submission-closeout-no-service-start-stop", "process",
                        "closeout evidence does not start or stop project services"),
                guardrail("submission-closeout-fail-closed-on-missing-material", "comparison",
                        "missing, unsubmitted, or incomparable material remains fail-closed")
        );
    }

    private static OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutResponse
            .Guardrail guardrail(String code, String category, String rule) {
        return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutSupport
                .guardrail(code, category, rule);
    }
}

