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

    private OpsShardReadinessRoutePaths() {
    }
}
