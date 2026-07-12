package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupEvidenceResponse.Entry;
import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessRouteCleanupEvidenceCatalog {

  private OpsShardReadinessRouteCleanupEvidenceCatalog() {}

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

  static Entry entry(
      int javaVersion,
      String sourceNodePlan,
      String phase,
      String evidenceType,
      String evidenceSlug) {
    return new Entry(
        javaVersion,
        sourceNodePlan,
        phase,
        evidenceType,
        "e/" + javaVersion + "/evidence/" + evidenceSlug + ".json",
        true,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        "passed");
  }
}
