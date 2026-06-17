package com.codexdemo.orderplatform.ops.maintenance.manualevidenceworksheet;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessManualEvidenceWorksheetSupport {

  static final String PROJECT = "advanced-order-platform";
  static final String SOURCE_PLAN = "Node v861";

  private OpsShardReadinessManualEvidenceWorksheetSupport() {}

  static OpsShardReadinessManualEvidenceWorksheetResponse response(
      String version,
      String endpoint,
      String profile,
      List<OpsShardReadinessManualEvidenceWorksheetResponse.WorksheetItem> items,
      List<String> additionalChecks) {
    int passed = (int) items.stream().filter(item -> "passed".equals(item.status())).count();
    List<String> checks = new ArrayList<>();
    checks.add("item-count-" + items.size());
    checks.add("passed-item-count-" + passed);
    checks.add("source-plan-" + SOURCE_PLAN);
    checks.add("operator-entry-worksheet-ready");
    checks.add("manual-evidence-entry-locked");
    checks.add("live-execution-locked");
    checks.add("production-execution-locked");
    checks.addAll(additionalChecks);
    return new OpsShardReadinessManualEvidenceWorksheetResponse(
        PROJECT,
        version,
        true,
        false,
        true,
        false,
        false,
        false,
        endpoint,
        profile,
        SOURCE_PLAN,
        items.size(),
        passed,
        List.copyOf(items),
        List.copyOf(checks),
        passed == items.size() ? "passed" : "blocked");
  }

  static OpsShardReadinessManualEvidenceWorksheetResponse.WorksheetItem item(
      String name, String owner, String evidence, String sourceEndpoint) {
    return new OpsShardReadinessManualEvidenceWorksheetResponse.WorksheetItem(
        name, owner, evidence, sourceEndpoint, "passed");
  }
}
