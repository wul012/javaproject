package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexItemCatalog {

    private OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexItemCatalog() {
    }

    static List<OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexResponse
            .ArchiveIndexItem> items(
            OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutReceiptResponse source
    ) {
        boolean passed = "passed".equals(source.status());
        return List.of(
                item("closeout-receipt-response", source.endpoint(), "release-acceptance-archive", passed),
                item("accepted-criteria-ledger", "criteria-count=" + source.acceptedCriteriaCount(),
                        "criteria-retention", source.acceptedCriteriaCount() == 7),
                item("receipt-markdown-lines", "markdown-lines=" + source.markdownLineCount(),
                        "markdown-retention", source.markdownLineCount() == 7),
                item("receipt-checks", "checks=" + source.checks().size(), "check-retention",
                        source.checks().contains(
                                "release-acceptance-route-path-split-package-closeout-ready-for-handoff")),
                item("version-tags-v1635-v1637", source.version(), "version-lineage", "Java v1637"
                        .equals(source.version()))
        );
    }

    private static OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexResponse
            .ArchiveIndexItem item(
            String item,
            String location,
            String retention,
            boolean ready
    ) {
        return new OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexResponse
                .ArchiveIndexItem(item, location, retention, ready);
    }
}
