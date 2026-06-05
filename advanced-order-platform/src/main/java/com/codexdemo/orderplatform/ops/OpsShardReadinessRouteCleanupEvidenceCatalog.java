package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.OpsShardReadinessRouteCleanupEvidenceResponse.Entry;
import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessRouteCleanupEvidenceCatalog {

    private OpsShardReadinessRouteCleanupEvidenceCatalog() {
    }

    static List<Entry> entries() {
        List<Entry> entries = new ArrayList<>();
        entries.addAll(OpsShardReadinessRouteCleanupLatestSiblingEvidenceCatalog.entries());
        entries.addAll(OpsShardReadinessRouteCleanupReadinessSeedEvidenceCatalog.entries());
        entries.addAll(OpsShardReadinessRouteCleanupHandoffCoreEvidenceCatalog.entries());
        entries.addAll(OpsShardReadinessRouteCleanupHandoffAssuranceEvidenceCatalog.entries());
        entries.addAll(OpsShardReadinessRouteCleanupHandoffGovernanceEvidenceCatalog.entries());
        entries.addAll(OpsShardReadinessRouteCleanupPostCompletionEvidenceCatalog.entries());
        return List.copyOf(entries);
    }
}