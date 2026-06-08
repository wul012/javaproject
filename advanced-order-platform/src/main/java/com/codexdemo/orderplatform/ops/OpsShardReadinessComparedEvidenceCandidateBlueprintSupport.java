package com.codexdemo.orderplatform.ops;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessComparedEvidenceCandidateBlueprintSupport {

    static final String PROJECT = "advanced-order-platform";
    static final String SOURCE_PLAN = "Node v1361";
    static final String SOURCE_NODE_PREFLIGHT_VERSION = "Node v1351";
    static final String SOURCE_JAVA_PREFLIGHT_VERSION = "Java v1054";
    static final String CANDIDATE_BLUEPRINT_STATE = "section-blueprint-only";
    static final String REAL_CANDIDATE_STATE = "absent";
    static final String EVIDENCE_IMPORT_STATE = "disabled";
    static final String APPROVAL_GRANT_STATE = "not-granted";
    static final String SIGNED_APPROVAL_CAPTURE_STATE = "not-captured";
    static final String RUNTIME_PAYLOAD_STATE = "locked";
    static final String SIBLING_MUTATION_STATE = "locked";

    private OpsShardReadinessComparedEvidenceCandidateBlueprintSupport() {
    }

    static OpsShardReadinessComparedEvidenceCandidateBlueprintResponse response(
            String version,
            String endpoint,
            String profile,
            List<OpsShardReadinessComparedEvidenceCandidateBlueprintResponse.CandidateSection> sections,
            List<OpsShardReadinessComparedEvidenceCandidateBlueprintResponse.CandidateBlocker> blockers,
            List<String> additionalChecks
    ) {
        var sectionCopy = List.copyOf(sections);
        var blockerCopy = List.copyOf(blockers);
        int passedSectionCount = (int) sectionCopy.stream().filter(section -> "passed".equals(section.status())).count();
        int passedBlockerCount = (int) blockerCopy.stream().filter(blocker -> "passed".equals(blocker.status())).count();
        List<String> checks = new ArrayList<>();
        checks.add("compared-evidence-candidate-blueprint-section-count-" + sectionCopy.size());
        checks.add("compared-evidence-candidate-blueprint-blocker-count-" + blockerCopy.size());
        checks.add("compared-evidence-candidate-blueprint-source-plan-" + SOURCE_PLAN);
        checks.add("compared-evidence-candidate-blueprint-source-node-" + SOURCE_NODE_PREFLIGHT_VERSION);
        checks.add("compared-evidence-candidate-blueprint-source-java-" + SOURCE_JAVA_PREFLIGHT_VERSION);
        checks.add("compared-evidence-candidate-blueprint-no-real-candidate-import");
        checks.add("compared-evidence-candidate-blueprint-no-synthetic-evidence");
        checks.add("compared-evidence-candidate-blueprint-no-approval-grant");
        checks.add("compared-evidence-candidate-blueprint-no-signed-approval-capture");
        checks.add("compared-evidence-candidate-blueprint-no-runtime-payload");
        checks.add("compared-evidence-candidate-blueprint-no-sibling-mutation");
        checks.addAll(additionalChecks);

        return new OpsShardReadinessComparedEvidenceCandidateBlueprintResponse(
                PROJECT,
                version,
                true,
                false,
                true,
                SOURCE_PLAN,
                SOURCE_NODE_PREFLIGHT_VERSION,
                SOURCE_JAVA_PREFLIGHT_VERSION,
                CANDIDATE_BLUEPRINT_STATE,
                REAL_CANDIDATE_STATE,
                EVIDENCE_IMPORT_STATE,
                APPROVAL_GRANT_STATE,
                SIGNED_APPROVAL_CAPTURE_STATE,
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
                sectionCopy.size(),
                passedSectionCount,
                blockerCopy.size(),
                passedBlockerCount,
                sectionCopy,
                blockerCopy,
                List.copyOf(checks),
                passedSectionCount == sectionCopy.size() && passedBlockerCount == blockerCopy.size()
                        ? "passed"
                        : "blocked"
        );
    }

    static OpsShardReadinessComparedEvidenceCandidateBlueprintResponse.CandidateSection section(
            String code,
            String sourceNodeVersion,
            String section,
            String requiredFields,
            String owner,
            String sourceEndpoint,
            String blockerCode
    ) {
        return new OpsShardReadinessComparedEvidenceCandidateBlueprintResponse.CandidateSection(
                code,
                sourceNodeVersion,
                section,
                requiredFields,
                owner,
                sourceEndpoint,
                blockerCode,
                "passed"
        );
    }

    static OpsShardReadinessComparedEvidenceCandidateBlueprintResponse.CandidateBlocker blocker(
            String code,
            String category,
            String blocker,
            String rejectionCode
    ) {
        return new OpsShardReadinessComparedEvidenceCandidateBlueprintResponse.CandidateBlocker(
                code,
                category,
                blocker,
                rejectionCode,
                "fail-closed",
                "passed"
        );
    }
}
