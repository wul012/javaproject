package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution;

import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution.OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.ArchiveRequirement;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution.OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.BoundaryRule;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution.OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.CiBatch;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution.OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.GateCheck;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution.OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.OperatorHandoff;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution.OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.ReadTarget;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution.OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.SourcePlanEntry;
import java.util.List;

final class RegistryCatalog {

  private RegistryCatalog() {}

  static Evidence evidence() {
    return new Evidence(
        sourcePlans(),
        readTargets(),
        gateChecks(),
        boundaryRules(),
        ciBatches(),
        archiveRequirements(),
        operatorHandoffs());
  }

  private static List<SourcePlanEntry> sourcePlans() {
    return List.of(
        plan(
            "Node v349",
            "minimal read-only integration smoke rerun archive",
            "previous smoke lane",
            "5/5 read targets passed",
            5,
            5),
        plan(
            "Node v364",
            "minimal read-only integration regular gate",
            "regular gate source",
            "34/34 checks passed",
            34,
            34),
        plan(
            "Node v365",
            "regular gate archive and CI/operator check",
            "CI batch source",
            "40/40 checks passed",
            40,
            40),
        plan(
            "Node v366",
            "explicit read-window gate execution decision",
            "external read-window decision",
            "22/22 checks passed",
            22,
            22),
        plan(
            "Node v367",
            "minimal read-only integration gate execution",
            "current execution evidence",
            "5/5 read targets and 20/20 checks passed",
            20,
            20));
  }

  private static SourcePlanEntry plan(
      String nodeVersion,
      String title,
      String role,
      String result,
      int expectedChecks,
      int passedChecks) {
    return new SourcePlanEntry(nodeVersion, title, role, result, expectedChecks, passedChecks);
  }

  private static List<ReadTarget> readTargets() {
    return List.of(
        target(
            "java-health",
            "java-operator",
            "HTTP GET",
            "ORDER_PLATFORM_URL handle",
            "GET /actuator/health"),
        target(
            "java-ops-overview",
            "java-operator",
            "HTTP GET",
            "ORDER_PLATFORM_URL handle",
            "GET /api/v1/ops/overview"),
        target(
            "mini-kv-health",
            "mini-kv-operator",
            "TCP command",
            "MINIKV_HOST/MINIKV_PORT handle",
            "HEALTH"),
        target(
            "mini-kv-infojson",
            "mini-kv-operator",
            "TCP command",
            "MINIKV_HOST/MINIKV_PORT handle",
            "INFOJSON"),
        target(
            "mini-kv-statsjson",
            "mini-kv-operator",
            "TCP command",
            "MINIKV_HOST/MINIKV_PORT handle",
            "STATSJSON"));
  }

  private static ReadTarget target(
      String target, String owner, String protocol, String addressHandle, String commandOrRoute) {
    return new ReadTarget(
        target, owner, protocol, addressHandle, commandOrRoute, true, true, "passed");
  }

  private static List<GateCheck> gateChecks() {
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

  private static GateCheck check(String code, String group, String evidence) {
    return new GateCheck(code, group, evidence, true);
  }

  private static List<BoundaryRule> boundaryRules() {
    return List.of(
        rule(
            "no-write-routing",
            "route-owner",
            "write routing",
            "read-only gate evidence cannot enable write routes"),
        rule(
            "no-active-shard-router",
            "shard-owner",
            "active shard router",
            "shard router remains out of scope"),
        rule(
            "no-credential-value",
            "security-owner",
            "credential value",
            "only credential handles and review status are allowed"),
        rule(
            "no-raw-endpoint-url",
            "security-owner",
            "raw endpoint URL resolution",
            "only endpoint handles are allowed"),
        rule(
            "no-managed-audit-connection",
            "audit-owner",
            "managed audit HTTP/TCP",
            "managed audit connections remain disabled"),
        rule(
            "no-deployment-rollback",
            "release-owner",
            "deployment or rollback",
            "gate execution evidence cannot deploy or roll back"),
        rule(
            "no-java-autostart",
            "java-operator",
            "Java autostart",
            "Java must be externally started by an operator"),
        rule(
            "no-mini-kv-autostart",
            "mini-kv-operator",
            "mini-kv autostart",
            "mini-kv must be externally started by an operator"),
        rule(
            "no-mini-kv-write-admin",
            "mini-kv-operator",
            "mini-kv write/admin command",
            "LOAD/COMPACT/RESTORE/SET/DEL style commands stay forbidden"),
        rule(
            "no-java-ledger-or-sql-write",
            "java-operator",
            "Java ledger/schema/SQL write",
            "approval ledger, schema, and SQL writes stay forbidden"));
  }

  private static BoundaryRule rule(
      String code, String owner, String forbiddenAction, String rationale) {
    return new BoundaryRule(code, owner, forbiddenAction, false, rationale);
  }

  private static List<CiBatch> ciBatches() {
    return List.of(
        batch(
            "focused-registry-tests", 1, "focused", "new registry service and catalog tests", true),
        batch("grouped-route-tests", 2, "grouped", "controller and route evidence tests", true),
        batch("build-validation", 3, "build", "Maven compile and non-Docker regression", true),
        batch("read-only-smoke", 4, "smoke", "read-only gate output smoke", false));
  }

  private static CiBatch batch(
      String name, int order, String commandFamily, String scope, boolean blocksNextBatch) {
    return new CiBatch(name, order, commandFamily, scope, blocksNextBatch);
  }

  private static List<ArchiveRequirement> archiveRequirements() {
    return List.of(
        requirement("v367-json", "Node v367", "5/5 read target JSON evidence"),
        requirement("v367-markdown", "Node v367", "operator-readable gate markdown"),
        requirement("v367-summary", "Node v367", "20/20 check summary"),
        requirement("v367-screenshot", "Node v367", "read window execution screenshot"),
        requirement("v367-walkthrough", "Node v367", "operator walkthrough transcript"),
        requirement("v367-gate-manifest", "Java v1312", "Java read-only registry manifest"));
  }

  private static ArchiveRequirement requirement(String artifact, String producer, String evidence) {
    return new ArchiveRequirement(artifact, producer, evidence, true);
  }

  private static List<OperatorHandoff> operatorHandoffs() {
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

  private static OperatorHandoff handoff(String step, String owner, String instruction) {
    return new OperatorHandoff(step, owner, instruction, true);
  }

  record Evidence(
      List<SourcePlanEntry> sourcePlans,
      List<ReadTarget> readTargets,
      List<GateCheck> gateChecks,
      List<BoundaryRule> boundaryRules,
      List<CiBatch> ciBatches,
      List<ArchiveRequirement> archiveRequirements,
      List<OperatorHandoff> operatorHandoffs) {
    Evidence {
      sourcePlans = List.copyOf(sourcePlans);
      readTargets = List.copyOf(readTargets);
      gateChecks = List.copyOf(gateChecks);
      boundaryRules = List.copyOf(boundaryRules);
      ciBatches = List.copyOf(ciBatches);
      archiveRequirements = List.copyOf(archiveRequirements);
      operatorHandoffs = List.copyOf(operatorHandoffs);
    }
  }
}
