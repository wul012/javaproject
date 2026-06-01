package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessV1ContractConsumerReadinessHandoffService {

    public static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.V1_CONTRACT_CONSUMER_READINESS_HANDOFF;

    public static final String FIXTURE_ENDPOINT =
            "/contracts/java-shard-readiness-v1-contract-consumer-readiness-handoff-v225.fixture.json";

    public static final String EVIDENCE_PATH =
            "e/225/evidence/java-shard-readiness-v1-contract-consumer-readiness-handoff-v225.json";

    @Transactional(readOnly = true)
    public OpsShardReadinessV1ContractConsumerReadinessHandoffResponse handoff() {
        OpsShardReadinessV1ContractConsumerEvidenceDigestResponse digest =
                OpsShardReadinessV1ContractConsumerEvidenceDigestSnapshot.v220Digest();
        return new OpsShardReadinessV1ContractConsumerReadinessHandoffResponse(
                "advanced-order-platform",
                "Java v225",
                digest.contractName(),
                true,
                false,
                false,
                ENDPOINT,
                FIXTURE_ENDPOINT,
                digest.evidenceDigestEndpoint(),
                digest.evidenceDigestFixtureEndpoint(),
                digest.evidencePath(),
                digest.receiptId(),
                digest.digestEvidence().size(),
                digest.digestChecks().size(),
                digest.digestEvidence(),
                handoffGuardEvidence(),
                handoffChecks(digest),
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
                EVIDENCE_PATH,
                "passed"
        );
    }

    private List<String> handoffGuardEvidence() {
        return List.of(
                OpsShardReadinessV1ContractConsumerEvidenceDigestService
                        .CONSUMER_EVIDENCE_DIGEST_SNAPSHOT_FREEZE_EVIDENCE_PATH,
                OpsShardReadinessV1ContractConsumerEvidenceDigestService
                        .CONSUMER_EVIDENCE_DIGEST_HISTORICAL_COMPATIBILITY_EVIDENCE_PATH,
                OpsShardReadinessV1ContractConsumerEvidenceDigestService
                        .CONSUMER_EVIDENCE_DIGEST_INTEGRITY_EVIDENCE_PATH,
                OpsShardReadinessV1ContractConsumerEvidenceDigestService
                        .CONSUMER_EVIDENCE_DIGEST_READINESS_COMPLETION_EVIDENCE_PATH
        );
    }

    private List<String> handoffChecks(OpsShardReadinessV1ContractConsumerEvidenceDigestResponse digest) {
        return List.of(
                "digest-version:" + digest.version(),
                "digest-evidence-count:" + digest.digestEvidence().size(),
                "digest-check-count:" + digest.digestChecks().size(),
                "handoff-guard-evidence-count:" + handoffGuardEvidence().size(),
                "probes-are-get-only:" + digest.probesAreGetOnly(),
                "upstream-actions-allowed:" + digest.upstreamActionsAllowed(),
                "node-may-start-or-stop-java-or-mini-kv:" + digest.nodeMayStartOrStopJavaOrMiniKv()
        );
    }
}
