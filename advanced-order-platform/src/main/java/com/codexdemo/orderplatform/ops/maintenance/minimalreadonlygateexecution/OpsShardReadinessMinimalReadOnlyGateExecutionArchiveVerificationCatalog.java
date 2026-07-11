package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution;

import java.util.List;

final class OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationCatalog {

  private OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationCatalog() {}

  static List<OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.ArchiveRequirement>
      archiveRequirements() {
    return List.of(
        requirement("v367-json", "Node v367", "5/5 read target JSON evidence"),
        requirement("v367-markdown", "Node v367", "operator-readable gate markdown"),
        requirement("v367-summary", "Node v367", "20/20 check summary"),
        requirement("v367-screenshot", "Node v367", "read window execution screenshot"),
        requirement("v367-walkthrough", "Node v367", "operator walkthrough transcript"),
        requirement("v367-gate-manifest", "Java v1312", "Java read-only registry manifest"));
  }

  private static OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.ArchiveRequirement
      requirement(String artifact, String producer, String evidence) {
    return new OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.ArchiveRequirement(
        artifact, producer, evidence, true);
  }
}
