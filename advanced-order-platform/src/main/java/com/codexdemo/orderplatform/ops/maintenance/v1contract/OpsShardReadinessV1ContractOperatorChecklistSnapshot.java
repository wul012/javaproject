package com.codexdemo.orderplatform.ops.maintenance.v1contract;

import java.util.List;

final class OpsShardReadinessV1ContractOperatorChecklistSnapshot {

  private OpsShardReadinessV1ContractOperatorChecklistSnapshot() {}

  static OpsShardReadinessV1ContractOperatorChecklistResponse v196Checklist() {
    OpsShardReadinessV1ContractEvidencePacketResponse packet =
        OpsShardReadinessV1ContractEvidencePacketSnapshot.v193Packet();
    return new OpsShardReadinessV1ContractOperatorChecklistResponse(
        "advanced-order-platform",
        "Java v196",
        packet.contractName(),
        true,
        false,
        false,
        OpsShardReadinessV1ContractOperatorChecklistService.ENDPOINT,
        OpsShardReadinessV1ContractOperatorChecklistService.FIXTURE_ENDPOINT,
        packet.packetEndpoint(),
        packet.packetFixtureEndpoint(),
        packet.evidencePath(),
        OpsShardReadinessV1ContractOperatorChecklistService.SNAPSHOT_FREEZE_EVIDENCE_PATH,
        OpsShardReadinessV1ContractOperatorChecklistService.HISTORICAL_COMPATIBILITY_EVIDENCE_PATH,
        v196OperatorChecklistItems(),
        v196RequiredReadOnlyEvidence(packet),
        v196NodeResponsibilities(),
        v196JavaResponsibilities(),
        packet.blockedOperations(),
        v196VerificationChecks(packet),
        packet.minimalFieldsFrozen(),
        packet.historicalSnapshotsProtected(),
        packet.writeRoutingAllowed(),
        packet.activeShardRouterAllowed(),
        packet.credentialValueRead(),
        packet.rawEndpointParsed(),
        packet.managedAuditConnectionAllowed(),
        packet.deploymentOrRollbackAllowed(),
        packet.nodeMayStartOrStopJavaOrMiniKv(),
        "java-shard-readiness-v1-contract-operator-checklist-receipt-v196",
        OpsShardReadinessV1ContractOperatorChecklistService.EVIDENCE_PATH,
        "passed");
  }

  static List<String> v196OperatorChecklistItems() {
    return List.of(
        "confirm-java-v193-packet-endpoint-is-readable",
        "confirm-java-v194-packet-snapshot-freeze-evidence-is-archived",
        "confirm-java-v195-historical-snapshot-compatibility-evidence-is-archived",
        "confirm-node-consumes-only-read-only-get-endpoints",
        "confirm-no-write-routing-or-active-shard-router-is-enabled",
        "confirm-no-credential-value-or-raw-endpoint-is-read",
        "confirm-no-java-or-mini-kv-process-control-is-delegated-to-node");
  }

  static List<String> v196RequiredReadOnlyEvidence(
      OpsShardReadinessV1ContractEvidencePacketResponse packet) {
    return List.of(
        packet.evidencePath(),
        OpsShardReadinessV1ContractOperatorChecklistService.SNAPSHOT_FREEZE_EVIDENCE_PATH,
        OpsShardReadinessV1ContractOperatorChecklistService.HISTORICAL_COMPATIBILITY_EVIDENCE_PATH,
        OpsShardReadinessV1ContractOperatorChecklistService.EVIDENCE_PATH);
  }

  static List<String> v196NodeResponsibilities() {
    return List.of(
        "read-checklist-endpoint-with-get-only",
        "read-packet-endpoint-with-get-only",
        "persist-observed-receipts-outside-java",
        "avoid-java-or-mini-kv-process-start-stop",
        "leave-write-routing-and-active-shard-router-disabled");
  }

  static List<String> v196JavaResponsibilities() {
    return List.of(
        "serve-checklist-as-read-only-json",
        "serve-static-fixture-for-contract-review",
        "keep-packet-and-historical-snapshot-references-stable",
        "keep-execution-allowed-false",
        "keep-shard-enabled-false");
  }

  static List<String> v196VerificationChecks(
      OpsShardReadinessV1ContractEvidencePacketResponse packet) {
    return List.of(
        "contract-name:" + packet.contractName(),
        "packet-endpoint:" + packet.packetEndpoint(),
        "packet-evidence-path:" + packet.evidencePath(),
        "required-read-only-evidence-count:4",
        "operator-checklist-item-count:7",
        "node-responsibility-count:5",
        "java-responsibility-count:5",
        "packet-frozen:" + packet.minimalFieldsFrozen(),
        "historical-snapshots-protected:" + packet.historicalSnapshotsProtected(),
        "execution-allowed:false");
  }
}
