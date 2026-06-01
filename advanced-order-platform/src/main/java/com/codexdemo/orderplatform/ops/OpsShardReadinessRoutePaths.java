package com.codexdemo.orderplatform.ops;

final class OpsShardReadinessRoutePaths {

    static final String BASE_PATH = "/api/v1/ops/shard-readiness";

    static final String READ_ONLY_EVIDENCE_CATALOG = "/read-only-evidence-catalog";
    static final String READ_ONLY_EVIDENCE_CATALOG_HANDOFF = "/read-only-evidence-catalog-handoff";
    static final String READ_ONLY_EVIDENCE_CATALOG_HANDOFF_VERIFICATION =
            "/read-only-evidence-catalog-handoff-verification";

    static final String EVIDENCE_INDEX = "/evidence-index";
    static final String EVIDENCE_VERIFICATION = "/evidence-verification";
    static final String EVIDENCE_HANDOFF = "/evidence-handoff";

    private OpsShardReadinessRoutePaths() {
    }
}
