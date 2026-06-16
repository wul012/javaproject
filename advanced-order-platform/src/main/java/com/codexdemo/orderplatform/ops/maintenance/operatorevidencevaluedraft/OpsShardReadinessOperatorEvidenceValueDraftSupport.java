package com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluedraft;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessOperatorEvidenceValueDraftSupport {

  static final String PROJECT = "advanced-order-platform";
  static final String SOURCE_PLAN = "Node v911";
  static final String ACTUAL_VALUE_STATE = "not-supplied";
  static final String DRAFT_VALUE_STATE = "awaiting-operator-value";
  static final String IMPORT_VALUE_STATE = "blocked";

  private OpsShardReadinessOperatorEvidenceValueDraftSupport() {}

  static OpsShardReadinessOperatorEvidenceValueDraftResponse response(
      String version,
      String endpoint,
      String profile,
      List<OpsShardReadinessOperatorEvidenceValueDraftResponse.DraftSlot> slots,
      List<String> additionalChecks) {
    int passed = (int) slots.stream().filter(slot -> "passed".equals(slot.status())).count();
    List<String> checks = new ArrayList<>();
    checks.add("slot-count-" + slots.size());
    checks.add("passed-slot-count-" + passed);
    checks.add("source-plan-" + SOURCE_PLAN);
    checks.add("operator-evidence-value-draft-ready");
    checks.add("actual-value-state-" + ACTUAL_VALUE_STATE);
    checks.add("evidence-import-locked");
    checks.add("manual-evidence-entry-locked");
    checks.add("live-execution-locked");
    checks.add("production-execution-locked");
    checks.addAll(additionalChecks);
    return new OpsShardReadinessOperatorEvidenceValueDraftResponse(
        PROJECT,
        version,
        true,
        false,
        true,
        ACTUAL_VALUE_STATE,
        DRAFT_VALUE_STATE,
        false,
        false,
        false,
        false,
        endpoint,
        profile,
        SOURCE_PLAN,
        slots.size(),
        passed,
        List.copyOf(slots),
        List.copyOf(checks),
        passed == slots.size() ? "passed" : "blocked");
  }

  static OpsShardReadinessOperatorEvidenceValueDraftResponse.DraftSlot slot(
      String code,
      String sourceSlot,
      String instruction,
      String draftValueBoundary,
      String sourceEndpoint) {
    return new OpsShardReadinessOperatorEvidenceValueDraftResponse.DraftSlot(
        code,
        sourceSlot,
        instruction,
        draftValueBoundary,
        IMPORT_VALUE_STATE,
        sourceEndpoint,
        "passed");
  }
}
