package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessV1ContractConsumerVerificationChecklistService {

    public static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.V1_CONTRACT_CONSUMER_VERIFICATION_CHECKLIST;

    public static final String FIXTURE_ENDPOINT =
            "/contracts/java-shard-readiness-v1-contract-consumer-verification-checklist-v215.fixture.json";

    public static final String EVIDENCE_PATH =
            "e/215/evidence/java-shard-readiness-v1-contract-consumer-verification-checklist-v215.json";

    static final String HANDOFF_BUNDLE_SNAPSHOT_FREEZE_EVIDENCE_PATH =
            "e/212/evidence/java-shard-readiness-v211-consumer-handoff-bundle-snapshot-freeze-v212.json";

    static final String HANDOFF_BUNDLE_HISTORICAL_COMPATIBILITY_EVIDENCE_PATH =
            "e/213/evidence/java-shard-readiness-v211-consumer-handoff-bundle-historical-compatibility-v213.json";

    static final String HANDOFF_BUNDLE_INTEGRITY_EVIDENCE_PATH =
            "e/214/evidence/java-shard-readiness-v1-contract-consumer-handoff-bundle-integrity-v214.json";

    @Transactional(readOnly = true)
    public OpsShardReadinessV1ContractConsumerVerificationChecklistResponse checklist() {
        OpsShardReadinessV1ContractConsumerHandoffBundleResponse bundle =
                OpsShardReadinessV1ContractConsumerHandoffBundleSnapshot.v211Bundle();
        return new OpsShardReadinessV1ContractConsumerVerificationChecklistResponse(
                "advanced-order-platform",
                "Java v215",
                bundle.contractName(),
                true,
                false,
                false,
                ENDPOINT,
                FIXTURE_ENDPOINT,
                bundle.handoffBundleEndpoint(),
                bundle.handoffBundleFixtureEndpoint(),
                bundle.evidencePath(),
                bundle.receiptId(),
                bundle.catalogedArtifactCount(),
                verificationItems(),
                requiredEvidence(bundle),
                bundle.blockedOperations(),
                verificationChecks(bundle),
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
                EVIDENCE_PATH,
                "passed"
        );
    }

    private List<String> verificationItems() {
        return List.of(
                "read-v208-endpoint-catalog-before-consuming-v211-bundle",
                "confirm-v211-bundle-required-evidence-count-is-nine",
                "confirm-v211-bundle-handoff-evidence-count-is-four",
                "confirm-probes-are-get-only",
                "confirm-upstream-actions-remain-disabled",
                "confirm-node-does-not-start-or-stop-java-or-mini-kv",
                "archive-v215-checklist-receipt-before-any-node-consumption"
        );
    }

    private List<String> requiredEvidence(OpsShardReadinessV1ContractConsumerHandoffBundleResponse bundle) {
        return List.of(
                bundle.endpointCatalogEvidencePath(),
                bundle.evidencePath(),
                HANDOFF_BUNDLE_SNAPSHOT_FREEZE_EVIDENCE_PATH,
                HANDOFF_BUNDLE_HISTORICAL_COMPATIBILITY_EVIDENCE_PATH,
                HANDOFF_BUNDLE_INTEGRITY_EVIDENCE_PATH
        );
    }

    private List<String> verificationChecks(OpsShardReadinessV1ContractConsumerHandoffBundleResponse bundle) {
        return List.of(
                "bundle-version:" + bundle.version(),
                "cataloged-artifact-count:" + bundle.catalogedArtifactCount(),
                "required-evidence-count:" + bundle.requiredEvidence().size(),
                "handoff-evidence-count:" + bundle.handoffEvidence().size(),
                "probes-are-get-only:" + bundle.probesAreGetOnly(),
                "upstream-actions-allowed:" + bundle.upstreamActionsAllowed(),
                "node-may-start-or-stop-java-or-mini-kv:" + bundle.nodeMayStartOrStopJavaOrMiniKv()
        );
    }
}
