package com.codexdemo.orderplatform.ops;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeSupport {

    static final String PROJECT = "advanced-order-platform";
    static final String SOURCE_PLAN = "Node v1331";
    static final String SOURCE_NODE_ACCEPTANCE_PRECHECK_VERSION = "Node v1321";
    static final String SOURCE_JAVA_ACCEPTANCE_PRECHECK_VERSION = "Java v1014";
    static final String INTAKE_CONTRACT_STATE = "evidence-slot-contract-only";
    static final String COMPARED_EVIDENCE_STATE = "not-accepted";
    static final String SIGNED_DRAFT_TEXT_PARSE_STATE = "not-parsed";
    static final String DETACHED_SIGNATURE_PARSE_STATE = "not-parsed";
    static final String APPROVAL_GRANT_STATE = "not-emitted";
    static final String RUNTIME_PAYLOAD_STATE = "locked";
    static final String SIBLING_MUTATION_STATE = "locked";

    private OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeSupport() {
    }

    static OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeResponse
    response(
            String version,
            String endpoint,
            String profile,
            List<OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeResponse
                    .EvidenceSlot> evidenceSlots,
            List<OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeResponse
                    .IntakeGuard> guards,
            List<String> additionalChecks
    ) {
        var slotCopy = List.copyOf(evidenceSlots);
        var guardCopy = List.copyOf(guards);
        int passedSlotCount = (int) slotCopy.stream().filter(slot -> "passed".equals(slot.status())).count();
        int passedGuardCount = (int) guardCopy.stream().filter(guard -> "passed".equals(guard.status())).count();
        List<String> checks = new ArrayList<>();
        checks.add("signed-approval-artifact-draft-text-package-compared-package-evidence-intake-slot-count-"
                + slotCopy.size());
        checks.add("signed-approval-artifact-draft-text-package-compared-package-evidence-intake-guard-count-"
                + guardCopy.size());
        checks.add("signed-approval-artifact-draft-text-package-compared-package-evidence-intake-source-plan-"
                + SOURCE_PLAN);
        checks.add("signed-approval-artifact-draft-text-package-compared-package-evidence-intake-source-node-"
                + SOURCE_NODE_ACCEPTANCE_PRECHECK_VERSION);
        checks.add("signed-approval-artifact-draft-text-package-compared-package-evidence-intake-source-java-"
                + SOURCE_JAVA_ACCEPTANCE_PRECHECK_VERSION);
        checks.add("signed-approval-artifact-draft-text-package-compared-package-evidence-intake-no-evidence-fabrication");
        checks.add("signed-approval-artifact-draft-text-package-compared-package-evidence-intake-no-evidence-acceptance");
        checks.add("signed-approval-artifact-draft-text-package-compared-package-evidence-intake-no-draft-text-parsing");
        checks.add("signed-approval-artifact-draft-text-package-compared-package-evidence-intake-no-signature-parsing");
        checks.add("signed-approval-artifact-draft-text-package-compared-package-evidence-intake-no-approval-grant");
        checks.add("signed-approval-artifact-draft-text-package-compared-package-evidence-intake-no-runtime");
        checks.add("signed-approval-artifact-draft-text-package-compared-package-evidence-intake-no-sibling-mutation");
        checks.addAll(additionalChecks);

        return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeResponse(
                PROJECT,
                version,
                true,
                false,
                true,
                SOURCE_PLAN,
                SOURCE_NODE_ACCEPTANCE_PRECHECK_VERSION,
                SOURCE_JAVA_ACCEPTANCE_PRECHECK_VERSION,
                INTAKE_CONTRACT_STATE,
                COMPARED_EVIDENCE_STATE,
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
                slotCopy.size(),
                passedSlotCount,
                guardCopy.size(),
                passedGuardCount,
                slotCopy,
                guardCopy,
                List.copyOf(checks),
                passedSlotCount == slotCopy.size() && passedGuardCount == guardCopy.size()
                        ? "passed"
                        : "blocked"
        );
    }

    static OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeResponse
            .EvidenceSlot slot(
                    String code,
                    String sourceVersion,
                    String evidenceSlot,
                    String evidenceQuestion,
                    String missingEvidenceGuard,
                    String sourceEndpoint
    ) {
        return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeResponse
                .EvidenceSlot(code, sourceVersion, evidenceSlot, evidenceQuestion, missingEvidenceGuard,
                sourceEndpoint, "passed");
    }

    static OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeResponse
            .IntakeGuard guard(String code, String category, String guard, String rejectionCode) {
        return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeResponse
                .IntakeGuard(code, category, guard, rejectionCode, "fail-closed", "passed");
    }
}

