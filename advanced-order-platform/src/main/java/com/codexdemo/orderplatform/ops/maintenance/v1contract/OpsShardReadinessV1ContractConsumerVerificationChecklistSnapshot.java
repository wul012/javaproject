package com.codexdemo.orderplatform.ops.maintenance.v1contract;

import java.util.List;

final class OpsShardReadinessV1ContractConsumerVerificationChecklistSnapshot {

  private OpsShardReadinessV1ContractConsumerVerificationChecklistSnapshot() {}

  static OpsShardReadinessV1ContractConsumerVerificationChecklistResponse v215Checklist() {
    OpsShardReadinessV1ContractConsumerHandoffBundleResponse bundle =
        OpsShardReadinessV1ContractConsumerHandoffBundleSnapshot.v211Bundle();
    return new OpsShardReadinessV1ContractConsumerVerificationChecklistResponse(
        "advanced-order-platform",
        "Java v215",
        bundle.contractName(),
        true,
        false,
        false,
        OpsShardReadinessV1ContractConsumerVerificationChecklistService.ENDPOINT,
        OpsShardReadinessV1ContractConsumerVerificationChecklistService.FIXTURE_ENDPOINT,
        bundle.handoffBundleEndpoint(),
        bundle.handoffBundleFixtureEndpoint(),
        bundle.evidencePath(),
        bundle.receiptId(),
        bundle.catalogedArtifactCount(),
        v215VerificationItems(),
        v215RequiredEvidence(bundle),
        bundle.blockedOperations(),
        v215VerificationChecks(bundle),
        bundle.probesAreGetOnly(),
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        "java-shard-readiness-v1-contract-consumer-verification-checklist-receipt-v215",
        OpsShardReadinessV1ContractConsumerVerificationChecklistService.EVIDENCE_PATH,
        "passed");
  }

  static List<String> v215VerificationItems() {
    return List.of(
        "read-v208-endpoint-catalog-before-consuming-v211-bundle",
        "confirm-v211-bundle-required-evidence-count-is-nine",
        "confirm-v211-bundle-handoff-evidence-count-is-four",
        "confirm-probes-are-get-only",
        "confirm-upstream-actions-remain-disabled",
        "confirm-node-does-not-start-or-stop-java-or-mini-kv",
        "archive-v215-checklist-receipt-before-any-node-consumption");
  }

  static List<String> v215RequiredEvidence(
      OpsShardReadinessV1ContractConsumerHandoffBundleResponse bundle) {
    return List.of(
        bundle.endpointCatalogEvidencePath(),
        bundle.evidencePath(),
        OpsShardReadinessV1ContractConsumerVerificationChecklistService
            .HANDOFF_BUNDLE_SNAPSHOT_FREEZE_EVIDENCE_PATH,
        OpsShardReadinessV1ContractConsumerVerificationChecklistService
            .HANDOFF_BUNDLE_HISTORICAL_COMPATIBILITY_EVIDENCE_PATH,
        OpsShardReadinessV1ContractConsumerVerificationChecklistService
            .HANDOFF_BUNDLE_INTEGRITY_EVIDENCE_PATH);
  }

  static List<String> v215VerificationChecks(
      OpsShardReadinessV1ContractConsumerHandoffBundleResponse bundle) {
    return List.of(
        "bundle-version:" + bundle.version(),
        "cataloged-artifact-count:" + bundle.catalogedArtifactCount(),
        "required-evidence-count:" + bundle.requiredEvidence().size(),
        "handoff-evidence-count:" + bundle.handoffEvidence().size(),
        "probes-are-get-only:" + bundle.probesAreGetOnly(),
        "upstream-actions-allowed:" + bundle.upstreamActionsAllowed(),
        "node-may-start-or-stop-java-or-mini-kv:" + bundle.nodeMayStartOrStopJavaOrMiniKv());
  }
}
