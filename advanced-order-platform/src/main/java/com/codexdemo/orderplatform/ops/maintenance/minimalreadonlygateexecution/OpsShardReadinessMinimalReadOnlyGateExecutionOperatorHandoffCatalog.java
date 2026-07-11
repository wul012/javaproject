package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution;

import java.util.List;

final class OpsShardReadinessMinimalReadOnlyGateExecutionOperatorHandoffCatalog {

  private OpsShardReadinessMinimalReadOnlyGateExecutionOperatorHandoffCatalog() {}

  static List<OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.OperatorHandoff>
      operatorHandoffs() {
    return List.of(
        handoff(
            "confirm-external-read-window",
            "release-operator",
            "Confirm Java and mini-kv were started outside Node before read probes."),
        handoff(
            "keep-actions-disabled",
            "release-operator",
            "Keep UPSTREAM_ACTIONS_ENABLED=false while read probes are enabled."),
        handoff(
            "run-focused-grouped-build-smoke",
            "ci-operator",
            "Run focused tests, grouped route tests, build validation, then read-only smoke."),
        handoff(
            "archive-read-target-and-check-results",
            "evidence-operator",
            "Archive 5/5 read target results and 20/20 gate check results."),
        handoff(
            "stop-on-invalid-read-contract",
            "release-operator",
            "If invalid-read-contract appears, stop and request Java/mini-kv read-only fixes."));
  }

  private static OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.OperatorHandoff
      handoff(String step, String owner, String instruction) {
    return new OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.OperatorHandoff(
        step, owner, instruction, true);
  }
}
