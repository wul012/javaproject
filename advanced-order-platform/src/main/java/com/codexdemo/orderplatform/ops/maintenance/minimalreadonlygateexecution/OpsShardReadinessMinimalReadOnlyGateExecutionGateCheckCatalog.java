package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution;

import java.util.List;

final class OpsShardReadinessMinimalReadOnlyGateExecutionGateCheckCatalog {

  private OpsShardReadinessMinimalReadOnlyGateExecutionGateCheckCatalog() {}

  static List<OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.GateCheck>
      gateChecks() {
    return List.of(
        check("java-health-read-target-passed", "read-target", "Java health read target passed"),
        check(
            "java-ops-overview-read-target-passed",
            "read-target",
            "Java ops overview read target passed"),
        check("mini-kv-health-read-target-passed", "read-target", "mini-kv HEALTH passed"),
        check("mini-kv-infojson-read-target-passed", "read-target", "mini-kv INFOJSON passed"),
        check("mini-kv-statsjson-read-target-passed", "read-target", "mini-kv STATSJSON passed"),
        check("upstream-actions-disabled", "runtime-boundary", "UPSTREAM_ACTIONS_ENABLED=false"),
        check("credential-value-not-read", "runtime-boundary", "credential handles only"),
        check("raw-endpoint-url-not-resolved", "runtime-boundary", "endpoint handles only"),
        check(
            "managed-audit-http-not-called", "runtime-boundary", "managed audit remains disabled"),
        check("runtime-shell-not-called", "runtime-boundary", "runtime shell remains disabled"),
        check("json-evidence-archived", "archive", "Node v367 JSON evidence present"),
        check("markdown-evidence-archived", "archive", "Node v367 Markdown evidence present"),
        check("summary-evidence-archived", "archive", "Node v367 summary present"),
        check("screenshot-evidence-archived", "archive", "Node v367 screenshot present"),
        check("walkthrough-evidence-archived", "archive", "Node v367 walkthrough present"),
        check("v349-smoke-lane-reused", "lineage", "v367 reused v349 smoke lane"),
        check("v365-regular-gate-consumed", "lineage", "v367 consumed v365 regular gate"),
        check(
            "v366-read-window-decision-honored",
            "lineage",
            "v367 honored v366 read-window decision"),
        check("read-targets-five-of-five", "lineage", "5/5 read targets passed"),
        check("gate-checks-twenty-of-twenty", "lineage", "20/20 checks passed"));
  }

  private static OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.GateCheck check(
      String code, String group, String evidence) {
    return new OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.GateCheck(
        code, group, evidence, true);
  }
}
