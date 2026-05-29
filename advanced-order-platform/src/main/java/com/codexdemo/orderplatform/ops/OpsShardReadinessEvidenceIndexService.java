package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessEvidenceIndexService {

    static final String ENDPOINT = "/api/v1/ops/shard-readiness/evidence-index";
    static final String FIXTURE_ENDPOINT = "/contracts/java-shard-readiness-evidence-index-v155.fixture.json";
    static final String EVIDENCE_PATH = "e/155/evidence/java-shard-readiness-evidence-index-v155.json";

    @Transactional(readOnly = true)
    public OpsShardReadinessEvidenceIndexResponse evidenceIndex() {
        return new OpsShardReadinessEvidenceIndexResponse(
                "advanced-order-platform",
                "Java v155",
                true,
                false,
                "Node v376",
                requiredContractFields(),
                evidenceEntries(),
                fallbackPolicy(),
                compatibilityGuarantees(),
                EVIDENCE_PATH,
                "passed"
        );
    }

    private List<String> requiredContractFields() {
        return List.of(
                "project",
                "version",
                "readOnly",
                "executionAllowed",
                "shardEnabled",
                "shardCount",
                "slotCount",
                "routingMode",
                "status"
        );
    }

    private List<OpsShardReadinessEvidenceIndexResponse.EvidenceEntry> evidenceEntries() {
        return List.of(
                entry(
                        "Java v153",
                        "shard-readiness-core-contract",
                        OpsShardReadinessService.ENDPOINT,
                        OpsShardReadinessService.FIXTURE_ENDPOINT,
                        OpsShardReadinessService.EVIDENCE_PATH,
                        "Node v370-v376 core field source"
                ),
                entry(
                        "Java v154",
                        "shard-readiness-hardening",
                        OpsShardReadinessHardeningService.ENDPOINT,
                        OpsShardReadinessHardeningService.FIXTURE_ENDPOINT,
                        OpsShardReadinessHardeningService.EVIDENCE_PATH,
                        "Node v376 additive hardening source"
                )
        );
    }

    private List<String> fallbackPolicy() {
        return List.of(
                "use-versioned-fixture-endpoints-only",
                "use-versioned-archive-paths-only",
                "do-not-read-rolling-current-files-for-historical-baselines",
                "fail-closed-if-versioned-source-is-missing"
        );
    }

    private List<String> compatibilityGuarantees() {
        return List.of(
                "v153-core-contract-remains-frozen",
                "v154-hardening-remains-additive",
                "v155-index-does-not-enable-sharding",
                "no-node-v370-v376-archive-mutation",
                "no-order-payment-inventory-ledger-sql-change"
        );
    }

    private OpsShardReadinessEvidenceIndexResponse.EvidenceEntry entry(
            String evidenceVersion,
            String evidenceRole,
            String endpoint,
            String fixtureEndpoint,
            String archivePath,
            String consumerBoundary
    ) {
        return new OpsShardReadinessEvidenceIndexResponse.EvidenceEntry(
                evidenceVersion,
                evidenceRole,
                endpoint,
                fixtureEndpoint,
                archivePath,
                true,
                false,
                consumerBoundary
        );
    }
}
