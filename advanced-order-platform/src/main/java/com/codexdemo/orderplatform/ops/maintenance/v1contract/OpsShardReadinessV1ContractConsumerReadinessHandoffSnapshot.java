package com.codexdemo.orderplatform.ops.maintenance.v1contract;

import java.util.List;

final class OpsShardReadinessV1ContractConsumerReadinessHandoffSnapshot {

  private OpsShardReadinessV1ContractConsumerReadinessHandoffSnapshot() {}

  static OpsShardReadinessV1ContractConsumerReadinessHandoffResponse v225Handoff() {
    OpsShardReadinessV1ContractConsumerEvidenceDigestResponse digest =
        OpsShardReadinessV1ContractConsumerEvidenceDigestSnapshot.v220Digest();
    return new OpsShardReadinessV1ContractConsumerReadinessHandoffResponse(
        "advanced-order-platform",
        "Java v225",
        digest.contractName(),
        true,
        false,
        false,
        OpsShardReadinessV1ContractConsumerReadinessHandoffService.ENDPOINT,
        OpsShardReadinessV1ContractConsumerReadinessHandoffService.FIXTURE_ENDPOINT,
        digest.evidenceDigestEndpoint(),
        digest.evidenceDigestFixtureEndpoint(),
        digest.evidencePath(),
        digest.receiptId(),
        digest.digestEvidence().size(),
        digest.digestChecks().size(),
        digest.digestEvidence(),
        v225HandoffGuardEvidence(),
        v225HandoffChecks(digest),
        digest.blockedOperations(),
        digest.probesAreGetOnly(),
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
        "java-shard-readiness-v1-contract-consumer-readiness-handoff-receipt-v225",
        OpsShardReadinessV1ContractConsumerReadinessHandoffService.EVIDENCE_PATH,
        "passed");
  }

  static List<String> v225HandoffGuardEvidence() {
    return List.of(
        OpsShardReadinessV1ContractConsumerEvidenceDigestService
            .CONSUMER_EVIDENCE_DIGEST_SNAPSHOT_FREEZE_EVIDENCE_PATH,
        OpsShardReadinessV1ContractConsumerEvidenceDigestService
            .CONSUMER_EVIDENCE_DIGEST_HISTORICAL_COMPATIBILITY_EVIDENCE_PATH,
        OpsShardReadinessV1ContractConsumerEvidenceDigestService
            .CONSUMER_EVIDENCE_DIGEST_INTEGRITY_EVIDENCE_PATH,
        OpsShardReadinessV1ContractConsumerEvidenceDigestService
            .CONSUMER_EVIDENCE_DIGEST_READINESS_COMPLETION_EVIDENCE_PATH);
  }

  static List<String> v225HandoffChecks(
      OpsShardReadinessV1ContractConsumerEvidenceDigestResponse digest) {
    return List.of(
        "digest-version:" + digest.version(),
        "digest-evidence-count:" + digest.digestEvidence().size(),
        "digest-check-count:" + digest.digestChecks().size(),
        "handoff-guard-evidence-count:" + v225HandoffGuardEvidence().size(),
        "probes-are-get-only:" + digest.probesAreGetOnly(),
        "upstream-actions-allowed:" + digest.upstreamActionsAllowed(),
        "node-may-start-or-stop-java-or-mini-kv:" + digest.nodeMayStartOrStopJavaOrMiniKv());
  }
}
