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

    private OpsShardReadinessRoutePaths() {
    }
}
