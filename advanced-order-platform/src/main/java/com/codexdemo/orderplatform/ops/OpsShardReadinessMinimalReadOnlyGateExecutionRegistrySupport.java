package com.codexdemo.orderplatform.ops;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessMinimalReadOnlyGateExecutionRegistrySupport {

    static final String PROJECT = "advanced-order-platform";
    static final String SOURCE_PLAN = "Node v367";
    static final String SOURCE_GATE_DECISION = "Node v366";
    static final String SOURCE_REGULAR_GATE = "Node v365";
    static final String SOURCE_SMOKE_LANE = "Node v349";
    static final String REGISTRY_STATE =
            "minimal-read-only-gate-execution-archived-with-no-new-runtime";
    static final int EXPECTED_SOURCE_PLAN_COUNT = 5;
    static final int EXPECTED_READ_TARGET_COUNT = 5;
    static final int EXPECTED_GATE_CHECK_COUNT = 20;
    static final int EXPECTED_BOUNDARY_RULE_COUNT = 10;
    static final int EXPECTED_CI_BATCH_COUNT = 4;
    static final int EXPECTED_ARCHIVE_REQUIREMENT_COUNT = 6;
    static final int EXPECTED_OPERATOR_HANDOFF_COUNT = 5;

    private OpsShardReadinessMinimalReadOnlyGateExecutionRegistrySupport() {
    }

    static OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse response(
            String version,
            String endpoint,
            String profile,
            List<OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.SourcePlanEntry>
                    sourcePlans,
            List<OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.ReadTarget>
                    readTargets,
            List<OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.GateCheck>
                    gateChecks,
            List<OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.BoundaryRule>
                    boundaryRules,
            List<OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.CiBatch>
                    ciBatches,
            List<OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.ArchiveRequirement>
                    archiveRequirements,
            List<OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.OperatorHandoff>
                    operatorHandoffs,
            List<OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.MarkdownSection>
                    markdownSections
    ) {
        var sourcePlanCopy = List.copyOf(sourcePlans);
        var readTargetCopy = List.copyOf(readTargets);
        var gateCheckCopy = List.copyOf(gateChecks);
        var boundaryRuleCopy = List.copyOf(boundaryRules);
        var ciBatchCopy = List.copyOf(ciBatches);
        var archiveRequirementCopy = List.copyOf(archiveRequirements);
        var operatorHandoffCopy = List.copyOf(operatorHandoffs);
        var markdownSectionCopy = List.copyOf(markdownSections);
        int passedReadTargetCount = (int) readTargetCopy.stream()
                .filter(target -> "passed".equals(target.status()))
                .count();
        int passedGateCheckCount = (int) gateCheckCopy.stream()
                .filter(OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.GateCheck::passed)
                .count();
        int deniedBoundaryRuleCount = (int) boundaryRuleCopy.stream()
                .filter(rule -> !rule.allowed())
                .count();
        List<String> checks = new ArrayList<>();
        checks.add("minimal-read-only-gate-execution-source-plan-" + SOURCE_PLAN);
        checks.add("minimal-read-only-gate-execution-source-gate-decision-" + SOURCE_GATE_DECISION);
        checks.add("minimal-read-only-gate-execution-source-regular-gate-" + SOURCE_REGULAR_GATE);
        checks.add("minimal-read-only-gate-execution-source-smoke-lane-" + SOURCE_SMOKE_LANE);
        checks.add("minimal-read-only-gate-execution-source-plan-count-" + sourcePlanCopy.size());
        checks.add("minimal-read-only-gate-execution-read-target-count-" + readTargetCopy.size());
        checks.add("minimal-read-only-gate-execution-passed-read-target-count-" + passedReadTargetCount);
        checks.add("minimal-read-only-gate-execution-gate-check-count-" + gateCheckCopy.size());
        checks.add("minimal-read-only-gate-execution-passed-gate-check-count-" + passedGateCheckCount);
        checks.add("minimal-read-only-gate-execution-boundary-rule-count-" + boundaryRuleCopy.size());
        checks.add("minimal-read-only-gate-execution-denied-boundary-rule-count-" + deniedBoundaryRuleCount);
        checks.add("minimal-read-only-gate-execution-ci-batch-count-" + ciBatchCopy.size());
        checks.add("minimal-read-only-gate-execution-archive-requirement-count-"
                + archiveRequirementCopy.size());
        checks.add("minimal-read-only-gate-execution-operator-handoff-count-"
                + operatorHandoffCopy.size());
        checks.add("minimal-read-only-gate-execution-markdown-section-count-"
                + markdownSectionCopy.size());
        checks.add("minimal-read-only-gate-execution-no-upstream-autostart");
        checks.add("minimal-read-only-gate-execution-no-write-routing");
        checks.add("minimal-read-only-gate-execution-no-secret-value");
        checks.add("minimal-read-only-gate-execution-no-raw-endpoint-resolution");
        checks.add("minimal-read-only-gate-execution-no-managed-audit-http");

        String status = passedReadTargetCount == readTargetCopy.size()
                && passedGateCheckCount == gateCheckCopy.size()
                && deniedBoundaryRuleCount == boundaryRuleCopy.size()
                ? "passed"
                : "blocked";

        return new OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse(
                PROJECT,
                version,
                true,
                false,
                false,
                false,
                false,
                false,
                false,
                endpoint,
                profile,
                SOURCE_PLAN,
                SOURCE_SMOKE_LANE,
                REGISTRY_STATE,
                sourcePlanCopy.size(),
                readTargetCopy.size(),
                passedReadTargetCount,
                gateCheckCopy.size(),
                passedGateCheckCount,
                boundaryRuleCopy.size(),
                deniedBoundaryRuleCount,
                ciBatchCopy.size(),
                archiveRequirementCopy.size(),
                operatorHandoffCopy.size(),
                sourcePlanCopy,
                readTargetCopy,
                gateCheckCopy,
                boundaryRuleCopy,
                ciBatchCopy,
                archiveRequirementCopy,
                operatorHandoffCopy,
                markdownSectionCopy,
                List.copyOf(checks),
                status
        );
    }
}
