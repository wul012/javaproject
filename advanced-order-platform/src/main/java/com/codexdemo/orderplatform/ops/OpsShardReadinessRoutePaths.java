package com.codexdemo.orderplatform.ops;

final class OpsShardReadinessRoutePaths {

    static final String BASE_PATH = "/api/v1/ops/shard-readiness";

    static final String READ_ONLY_EVIDENCE_CATALOG = "/read-only-evidence-catalog";
    static final String READ_ONLY_EVIDENCE_CATALOG_HANDOFF = "/read-only-evidence-catalog-handoff";
    static final String READ_ONLY_EVIDENCE_CATALOG_HANDOFF_VERIFICATION =
            "/read-only-evidence-catalog-handoff-verification";
    static final String READ_ONLY_ENDPOINT_REGISTRY_INTEGRITY =
            "/read-only-endpoint-registry-integrity";

    static final String EVIDENCE_INDEX = "/evidence-index";
    static final String EVIDENCE_VERIFICATION = "/evidence-verification";
    static final String EVIDENCE_HANDOFF = "/evidence-handoff";

    static final String V1_CONTRACT_ALIGNMENT = "/v1-contract-alignment";
    static final String V1_CONTRACT_ALIGNMENT_HANDOFF = "/v1-contract-alignment-handoff";
    static final String V1_CONTRACT_EVIDENCE_PACKET = "/v1-contract-evidence-packet";
    static final String V1_CONTRACT_OPERATOR_CHECKLIST = "/v1-contract-operator-checklist";
    static final String V1_CONTRACT_HANDOFF_MANIFEST = "/v1-contract-handoff-manifest";
    static final String V1_CONTRACT_CONSUMER_PROBE_PLAN = "/v1-contract-consumer-probe-plan";
    static final String V1_CONTRACT_ENDPOINT_CATALOG = "/v1-contract-endpoint-catalog";
    static final String V1_CONTRACT_CONSUMER_HANDOFF_BUNDLE = "/v1-contract-consumer-handoff-bundle";
    static final String V1_CONTRACT_CONSUMER_VERIFICATION_CHECKLIST =
            "/v1-contract-consumer-verification-checklist";
    static final String V1_CONTRACT_CONSUMER_EVIDENCE_DIGEST =
            "/v1-contract-consumer-evidence-digest";
    static final String V1_CONTRACT_CONSUMER_READINESS_HANDOFF =
            "/v1-contract-consumer-readiness-handoff";

    static final String SHARD_READINESS_PROTOTYPE_CATALOG =
            "/prototype-catalog";
    static final String SHARD_READINESS_PROTOTYPE_FIXTURE_ECHO =
            "/prototype-fixture-echo";
    static final String SHARD_READINESS_PROTOTYPE_FIELD_ALIGNMENT =
            "/prototype-field-alignment";
    static final String SHARD_READINESS_PROTOTYPE_READ_ONLY_INTEGRATION_BRIDGE =
            "/prototype-read-only-integration-bridge";
    static final String SHARD_READINESS_PROTOTYPE_ROUTE_CLEANUP_BRIDGE =
            "/prototype-route-cleanup-bridge";
    static final String SHARD_READINESS_PROTOTYPE_READ_WINDOW_HANDOFF =
            "/prototype-read-window-handoff";
    static final String SHARD_READINESS_PROTOTYPE_CONSUMER_GATE_PACKET =
            "/prototype-consumer-gate-packet";
    static final String SHARD_READINESS_PROTOTYPE_OPERATOR_CI_HANDOFF =
            "/prototype-operator-ci-handoff";
    static final String SHARD_READINESS_PROTOTYPE_AUDIT_DIGEST =
            "/prototype-audit-digest";
    static final String SHARD_READINESS_PROTOTYPE_CLOSEOUT =
            "/prototype-closeout";
    static final String SHARD_READINESS_PROTOTYPE_HANDOFF_CATALOG =
            "/prototype-handoff-catalog";
    static final String SHARD_READINESS_PROTOTYPE_HANDOFF_ENDPOINT_INVENTORY =
            "/prototype-handoff-endpoint-inventory";
    static final String SHARD_READINESS_PROTOTYPE_HANDOFF_BOUNDARY_MATRIX =
            "/prototype-handoff-boundary-matrix";
    static final String SHARD_READINESS_PROTOTYPE_HANDOFF_CONSUMER_VERIFICATION_CHECKLIST =
            "/prototype-handoff-consumer-verification-checklist";
    static final String SHARD_READINESS_PROTOTYPE_HANDOFF_READ_WINDOW_CHECKLIST =
            "/prototype-handoff-read-window-checklist";
    static final String SHARD_READINESS_PROTOTYPE_HANDOFF_DIGEST_MANIFEST =
            "/prototype-handoff-digest-manifest";
    static final String SHARD_READINESS_PROTOTYPE_HANDOFF_CI_MANIFEST =
            "/prototype-handoff-ci-manifest";
    static final String SHARD_READINESS_PROTOTYPE_HANDOFF_ARCHIVE_MANIFEST =
            "/prototype-handoff-archive-manifest";
    static final String SHARD_READINESS_PROTOTYPE_HANDOFF_OPERATOR_SIGNOFF_PACKET =
            "/prototype-handoff-operator-signoff-packet";
    static final String SHARD_READINESS_PROTOTYPE_HANDOFF_CLOSEOUT =
            "/prototype-handoff-closeout";
    static final String SHARD_READINESS_PROTOTYPE_CONSUMER_GATE_CATALOG =
            "/prototype-consumer-gate-catalog";
    static final String SHARD_READINESS_PROTOTYPE_CONSUMER_GATE_SOURCE_INVENTORY =
            "/prototype-consumer-gate-source-inventory";
    static final String SHARD_READINESS_PROTOTYPE_CONSUMER_GATE_MINIMAL_FIELD_CHECKLIST =
            "/prototype-consumer-gate-minimal-field-checklist";
    static final String SHARD_READINESS_PROTOTYPE_CONSUMER_GATE_ROUTE_TOPOLOGY_PREVIEW =
            "/prototype-consumer-gate-route-topology-preview";
    static final String SHARD_READINESS_PROTOTYPE_CONSUMER_GATE_BOUNDARY_MATRIX =
            "/prototype-consumer-gate-boundary-matrix";
    static final String SHARD_READINESS_PROTOTYPE_CONSUMER_GATE_DIGEST_ACCEPTANCE =
            "/prototype-consumer-gate-digest-acceptance";
    static final String SHARD_READINESS_PROTOTYPE_CONSUMER_GATE_CI_BATCH_PLAN =
            "/prototype-consumer-gate-ci-batch-plan";
    static final String SHARD_READINESS_PROTOTYPE_CONSUMER_GATE_ARCHIVE_MANIFEST =
            "/prototype-consumer-gate-archive-manifest";
    static final String SHARD_READINESS_PROTOTYPE_CONSUMER_GATE_OPERATOR_SIGNOFF =
            "/prototype-consumer-gate-operator-signoff";
    static final String SHARD_READINESS_PROTOTYPE_CONSUMER_GATE_CLOSEOUT =
            "/prototype-consumer-gate-closeout";

    static final String ROUTE_CLEANUP_EVIDENCE_CATALOG =
            "/route-cleanup-evidence-catalog";
    static final String ROUTE_CLEANUP_PHASE_SUMMARY =
            "/route-cleanup-phase-summary";
    static final String ROUTE_CLEANUP_BOUNDARY_MATRIX =
            "/route-cleanup-boundary-matrix";
    static final String ROUTE_CLEANUP_HANDOFF_CHECKLIST =
            "/route-cleanup-handoff-checklist";
    static final String ROUTE_CLEANUP_ARCHIVE_PLAN =
            "/route-cleanup-archive-plan";
    static final String ROUTE_CLEANUP_DIGEST =
            "/route-cleanup-digest";
    static final String ROUTE_CLEANUP_SOURCE_PLAN_ALIGNMENT =
            "/route-cleanup-source-plan-alignment";
    static final String ROUTE_CLEANUP_RELEASE_HANDOFF =
            "/route-cleanup-release-handoff";
    static final String ROUTE_CLEANUP_OPERATOR_RUNBOOK =
            "/route-cleanup-operator-runbook";
    static final String ROUTE_CLEANUP_READ_ONLY_GATE =
            "/route-cleanup-read-only-gate";
    static final String ROUTE_CLEANUP_SUITE_CLOSEOUT =
            "/route-cleanup-suite-closeout";
    static final String ROUTE_CLEANUP_ARCHIVE_VERIFICATION =
            "/route-cleanup-archive-verification";
    static final String ROUTE_CLEANUP_CONSUMER_PACKET =
            "/route-cleanup-consumer-packet";
    static final String ROUTE_CLEANUP_CI_EVIDENCE =
            "/route-cleanup-ci-evidence";
    static final String ROUTE_CLEANUP_ENDPOINT_MANIFEST =
            "/route-cleanup-endpoint-manifest";
    static final String ROUTE_CLEANUP_REGRESSION_GUARD =
            "/route-cleanup-regression-guard";
    static final String ROUTE_CLEANUP_HANDOFF_BUNDLE =
            "/route-cleanup-handoff-bundle";
    static final String ROUTE_CLEANUP_CONTINUITY_REPORT =
            "/route-cleanup-continuity-report";
    static final String ROUTE_CLEANUP_CONSUMER_CHECKLIST =
            "/route-cleanup-consumer-checklist";
    static final String ROUTE_CLEANUP_FINAL_DIGEST =
            "/route-cleanup-final-digest";
    static final String ROUTE_CLEANUP_EXTENDED_CLOSEOUT =
            "/route-cleanup-extended-closeout";
    static final String ROUTE_CLEANUP_AUDIT_TRAIL =
            "/route-cleanup-audit-trail";
    static final String ROUTE_CLEANUP_ACCEPTANCE_RECEIPT =
            "/route-cleanup-acceptance-receipt";
    static final String ROUTE_CLEANUP_EVIDENCE_REGISTER =
            "/route-cleanup-evidence-register";
    static final String ROUTE_CLEANUP_OPERATIONAL_SNAPSHOT =
            "/route-cleanup-operational-snapshot";
    static final String ROUTE_CLEANUP_POLICY_GUARD =
            "/route-cleanup-policy-guard";
    static final String ROUTE_CLEANUP_REVIEWER_PACKET =
            "/route-cleanup-reviewer-packet";
    static final String ROUTE_CLEANUP_TRANSITION_BRIEF =
            "/route-cleanup-transition-brief";
    static final String ROUTE_CLEANUP_FINAL_VERIFICATION =
            "/route-cleanup-final-verification";
    static final String ROUTE_CLEANUP_FINAL_ARCHIVE_PLAN =
            "/route-cleanup-final-archive-plan";
    static final String ROUTE_CLEANUP_THIRD_RUN_CLOSEOUT =
            "/route-cleanup-third-run-closeout";
    static final String ROUTE_CLEANUP_COMPLETION_INDEX =
            "/route-cleanup-completion-index";
    static final String ROUTE_CLEANUP_COMPLETION_CERTIFICATE =
            "/route-cleanup-completion-certificate";
    static final String ROUTE_CLEANUP_POST_PUSH_CLOSEOUT =
            "/route-cleanup-post-push-closeout";
    static final String ROUTE_CLEANUP_CI_RUN_ATTESTATION =
            "/route-cleanup-ci-run-attestation";
    static final String ROUTE_CLEANUP_TAG_MANIFEST =
            "/route-cleanup-tag-manifest";
    static final String ROUTE_CLEANUP_RELEASE_EVIDENCE_BUNDLE =
            "/route-cleanup-release-evidence-bundle";
    static final String ROUTE_CLEANUP_CONSUMER_SIGNOFF_PACKET =
            "/route-cleanup-consumer-signoff-packet";
    static final String ROUTE_CLEANUP_ARCHIVE_HANDOFF_RECEIPT =
            "/route-cleanup-archive-handoff-receipt";
    static final String ROUTE_CLEANUP_MAINTENANCE_BOUNDARY_REPORT =
            "/route-cleanup-maintenance-boundary-report";
    static final String ROUTE_CLEANUP_FIXTURE_COVERAGE_INDEX =
            "/route-cleanup-fixture-coverage-index";
    static final String ROUTE_CLEANUP_COMPLETION_AUDIT_DIGEST =
            "/route-cleanup-completion-audit-digest";
    static final String ROUTE_CLEANUP_POST_COMPLETION_CLOSEOUT =
            "/route-cleanup-post-completion-closeout";
    static final String ROUTE_CLEANUP_MAINTENANCE_SEGMENT_CATALOG =
            "/route-cleanup-maintenance-segment-catalog";
    static final String ROUTE_CLEANUP_MAINTENANCE_CONTINUITY =
            "/route-cleanup-maintenance-continuity";
    static final String ROUTE_CLEANUP_MAINTENANCE_LATEST_SIBLING_REPORT =
            "/route-cleanup-maintenance-latest-sibling-report";
    static final String ROUTE_CLEANUP_MAINTENANCE_HANDOFF_PAIR_AUDIT =
            "/route-cleanup-maintenance-handoff-pair-audit";
    static final String ROUTE_CLEANUP_MAINTENANCE_BOUNDARY_DRIFT =
            "/route-cleanup-maintenance-boundary-drift";
    static final String ROUTE_CLEANUP_MAINTENANCE_SOURCE_PLAN_ALIGNMENT =
            "/route-cleanup-maintenance-source-plan-alignment";
    static final String ROUTE_CLEANUP_MAINTENANCE_TEST_BUDGET_PLAN =
            "/route-cleanup-maintenance-test-budget-plan";
    static final String ROUTE_CLEANUP_MAINTENANCE_ARCHIVE_MANIFEST =
            "/route-cleanup-maintenance-archive-manifest";
    static final String ROUTE_CLEANUP_MAINTENANCE_CLOSEOUT =
            "/route-cleanup-maintenance-closeout";
    static final String ROUTE_CLEANUP_MAINTENANCE_UPKEEP_CATALOG =
            "/route-cleanup-maintenance-upkeep-catalog";
    static final String ROUTE_CLEANUP_MAINTENANCE_CONSUMER_HANDOFF_MATRIX =
            "/route-cleanup-maintenance-consumer-handoff-matrix";
    static final String ROUTE_CLEANUP_MAINTENANCE_CI_EXPECTATION_MANIFEST =
            "/route-cleanup-maintenance-ci-expectation-manifest";
    static final String ROUTE_CLEANUP_MAINTENANCE_ROUTE_TOPOLOGY_INDEX =
            "/route-cleanup-maintenance-route-topology-index";
    static final String ROUTE_CLEANUP_MAINTENANCE_FAIL_CLOSED_POLICY =
            "/route-cleanup-maintenance-fail-closed-policy";
    static final String ROUTE_CLEANUP_MAINTENANCE_ARCHIVE_DIGEST_LEDGER =
            "/route-cleanup-maintenance-archive-digest-ledger";
    static final String ROUTE_CLEANUP_MAINTENANCE_OPERATOR_REVIEW_PACKET =
            "/route-cleanup-maintenance-operator-review-packet";
    static final String ROUTE_CLEANUP_MAINTENANCE_VERSION_LINEAGE =
            "/route-cleanup-maintenance-version-lineage";
    static final String ROUTE_CLEANUP_MAINTENANCE_READINESS_GATE =
            "/route-cleanup-maintenance-readiness-gate";
    static final String ROUTE_CLEANUP_MAINTENANCE_UPKEEP_CLOSEOUT =
            "/route-cleanup-maintenance-upkeep-closeout";
    static final String ROUTE_CLEANUP_MAINTENANCE_RELEASE_CHECKLIST =
            "/route-cleanup-maintenance-release-checklist";
    static final String ROUTE_CLEANUP_MAINTENANCE_REMEDIATION_QUEUE =
            "/route-cleanup-maintenance-remediation-queue";
    static final String ROUTE_CLEANUP_MAINTENANCE_FRESHNESS_WINDOW =
            "/route-cleanup-maintenance-freshness-window";
    static final String ROUTE_CLEANUP_MAINTENANCE_OWNERSHIP_REGISTER =
            "/route-cleanup-maintenance-ownership-register";
    static final String ROUTE_CLEANUP_MAINTENANCE_RISK_LEDGER =
            "/route-cleanup-maintenance-risk-ledger";
    static final String ROUTE_CLEANUP_MAINTENANCE_HANDOFF_ACCEPTANCE_DIGEST =
            "/route-cleanup-maintenance-handoff-acceptance-digest";
    static final String ROUTE_CLEANUP_MAINTENANCE_DEPENDENCY_BOUNDARY_MAP =
            "/route-cleanup-maintenance-dependency-boundary-map";
    static final String ROUTE_CLEANUP_MAINTENANCE_ARCHIVE_RETENTION_CALENDAR =
            "/route-cleanup-maintenance-archive-retention-calendar";
    static final String ROUTE_CLEANUP_MAINTENANCE_TEST_EVIDENCE_ROLLUP =
            "/route-cleanup-maintenance-test-evidence-rollup";
    static final String ROUTE_CLEANUP_MAINTENANCE_OPERATIONS_SCORECARD =
            "/route-cleanup-maintenance-operations-scorecard";
    static final String ROUTE_CLEANUP_MAINTENANCE_SUSTAINMENT_CLOSEOUT =
            "/route-cleanup-maintenance-sustainment-closeout";
    static final String ROUTE_CLEANUP_MAINTENANCE_CONTRACT_FREEZE =
            "/route-cleanup-maintenance-contract-freeze";
    static final String ROUTE_CLEANUP_MAINTENANCE_GATE_HANDOFF =
            "/route-cleanup-maintenance-gate-handoff";
    static final String ROUTE_CLEANUP_MAINTENANCE_SHARD_FIELD_MAP =
            "/route-cleanup-maintenance-shard-field-map";
    static final String ROUTE_CLEANUP_MAINTENANCE_READ_WINDOW_EVIDENCE =
            "/route-cleanup-maintenance-read-window-evidence";
    static final String ROUTE_CLEANUP_MAINTENANCE_RUNTIME_BOUNDARY_CHECKLIST =
            "/route-cleanup-maintenance-runtime-boundary-checklist";
    static final String ROUTE_CLEANUP_MAINTENANCE_CONSUMER_GATE_PACKET =
            "/route-cleanup-maintenance-consumer-gate-packet";

    private OpsShardReadinessRoutePaths() {
    }
}
