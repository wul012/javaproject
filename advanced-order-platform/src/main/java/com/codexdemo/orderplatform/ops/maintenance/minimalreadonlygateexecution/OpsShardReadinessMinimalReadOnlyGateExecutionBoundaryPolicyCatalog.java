package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution;

import java.util.List;

final class OpsShardReadinessMinimalReadOnlyGateExecutionBoundaryPolicyCatalog {

  private OpsShardReadinessMinimalReadOnlyGateExecutionBoundaryPolicyCatalog() {}

  static List<OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.BoundaryRule>
      boundaryRules() {
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

  private static OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.BoundaryRule rule(
      String code, String owner, String forbiddenAction, String rationale) {
    return new OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.BoundaryRule(
        code, owner, forbiddenAction, false, rationale);
  }
}
