package com.codexdemo.orderplatform.ops;

final class OpsShardReadinessReleaseAcceptanceRoutePaths {

    static final String BASE_PATH = "/api/v1/ops/shard-readiness";

    static final String MINIMAL_READ_ONLY_GATE_EXECUTION_REGISTRY =
            "/minimal-read-only-gate-execution-registry";
    static final String MINIMAL_READ_ONLY_GATE_EXECUTION_ARCHIVE_VERIFICATION_REGISTRY =
            "/minimal-read-only-gate-execution-archive-verification-registry";
    static final String MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_REGISTRY =
            "/minimal-read-only-gate-operator-ci-handoff-registry";
    static final String MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_ARCHIVE_VERIFICATION_REGISTRY =
            "/minimal-read-only-gate-operator-ci-handoff-archive-verification-registry";
    static final String MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_ARCHIVE_DIGEST_REGISTRY =
            "/minimal-read-only-gate-operator-ci-handoff-archive-digest-registry";
    static final String MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_ARCHIVE_DIGEST_CONSUMER_PACKAGE_REGISTRY =
            "/minimal-read-only-gate-operator-ci-handoff-archive-digest-consumer-package-registry";
    static final String MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_ARCHIVE_DIGEST_CONSUMER_PACKAGE_VERIFICATION_DOSSIER_REGISTRY =
            "/minimal-read-only-gate-operator-ci-handoff-archive-digest-consumer-package-verification-dossier-registry";
    static final String MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_ARCHIVE_DIGEST_CONSUMER_PACKAGE_VERIFICATION_DOSSIER_RELEASE_ACCEPTANCE_REGISTRY =
            "/minimal-read-only-gate-operator-ci-handoff-archive-digest-consumer-package-verification-dossier-release-acceptance-registry";
    static final String MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_ARCHIVE_DIGEST_CONSUMER_PACKAGE_VERIFICATION_DOSSIER_RELEASE_ACCEPTANCE_ARCHIVE_REGISTRY =
            "/minimal-read-only-gate-operator-ci-handoff-archive-digest-consumer-package-verification-dossier-release-acceptance-archive-registry";
    static final String RELEASE_ACCEPTANCE_ARCHIVE_VERIFICATION_HANDOFF_REGISTRY =
            "/release-acceptance-archive-verification-handoff-registry";
    static final String RELEASE_ACCEPTANCE_ROUTE_PATH_SPLIT_REGISTRY =
            "/release-acceptance-route-path-split-registry";
    static final String RELEASE_ACCEPTANCE_ROUTE_PATH_SPLIT_CLOSEOUT_REGISTRY =
            "/release-acceptance-route-path-split-closeout-registry";
    static final String RELEASE_ACCEPTANCE_ROUTE_PATH_SPLIT_SUSTAINMENT_REGISTRY =
            "/release-acceptance-route-path-split-sustainment-registry";
    static final String RELEASE_ACCEPTANCE_ROUTE_PATH_SPLIT_SUSTAINMENT_ACCEPTANCE_PACKAGE =
            "/release-acceptance-route-path-split-sustainment-acceptance-package";
    static final String RELEASE_ACCEPTANCE_ROUTE_PATH_SPLIT_SUSTAINMENT_ACCEPTANCE_PACKAGE_CLOSEOUT_RECEIPT =
            "/release-acceptance-route-path-split-sustainment-acceptance-package-closeout-receipt";
    static final String RELEASE_ACCEPTANCE_ROUTE_PATH_SPLIT_SUSTAINMENT_ACCEPTANCE_PACKAGE_CLOSEOUT_ARCHIVE_INDEX =
            "/release-acceptance-route-path-split-sustainment-acceptance-package-closeout-archive-index";

    private OpsShardReadinessReleaseAcceptanceRoutePaths() {
    }
}
