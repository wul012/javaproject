package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexCriteriaCatalog {

    private OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexCriteriaCatalog() {
    }

    static List<OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexResponse
            .CriteriaEcho> echoes(
            OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutReceiptResponse source
    ) {
        return source.acceptedCriteria().stream()
                .map(criterion -> new OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexResponse
                        .CriteriaEcho(criterion.name(), criterion.evidence(), criterion.status()))
                .toList();
    }
}
