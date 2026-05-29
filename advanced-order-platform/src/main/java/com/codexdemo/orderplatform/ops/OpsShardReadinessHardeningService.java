package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessHardeningService {

    static final String ENDPOINT = "/api/v1/ops/shard-readiness/hardening";
    static final String FIXTURE_ENDPOINT = "/contracts/java-shard-readiness-hardening-v154.fixture.json";
    static final String EVIDENCE_PATH = "e/154/evidence/java-shard-readiness-hardening-v154.json";

    @Transactional(readOnly = true)
    public OpsShardReadinessHardeningResponse hardening() {
        return new OpsShardReadinessHardeningResponse(
                "advanced-order-platform",
                "Java v154",
                true,
                false,
                "Java v153",
                OpsShardReadinessService.ENDPOINT,
                OpsShardReadinessService.FIXTURE_ENDPOINT,
                OpsShardReadinessService.EVIDENCE_PATH,
                fieldExplanations(),
                errorSemantics(),
                compatibilityGuarantees(),
                forbiddenChanges(),
                EVIDENCE_PATH,
                "passed"
        );
    }

    private List<OpsShardReadinessHardeningResponse.FieldExplanation> fieldExplanations() {
        return List.of(
                field(
                        "readOnly",
                        "Java ops control plane",
                        "Node regular gate",
                        "The endpoint is safe for GET probes and does not mutate order state.",
                        "Must remain true for shard readiness read windows."
                ),
                field(
                        "executionAllowed",
                        "Java ops control plane",
                        "Node regular gate",
                        "The endpoint is evidence only and must not authorize upstream actions.",
                        "Must remain false until a later explicit execution gate."
                ),
                field(
                        "shardEnabled",
                        "Java shard readiness fixture",
                        "Node shard readiness consumer",
                        "False means readiness evidence is present, not that Java routing is sharded.",
                        "Changing to true requires a new producer and consumer version."
                ),
                field(
                        "routingMode",
                        "Java shard readiness fixture",
                        "Node shard readiness consumer",
                        "The current routing profile is fixture-backed and non-authoritative.",
                        "Do not reinterpret as live routing."
                ),
                field(
                        "status",
                        "Java shard readiness fixture",
                        "Node regular gate",
                        "Passed means Java-side shard readiness evidence is internally complete.",
                        "Non-passed values are fail-closed for new Java hardening evidence."
                )
        );
    }

    private List<OpsShardReadinessHardeningResponse.ErrorSemantic> errorSemantics() {
        return List.of(
                error(
                        "hardening endpoint unavailable",
                        "BLOCK_NEW_JAVA_HARDENING_CONSUMPTION",
                        "Keep using archived Node v370-v373 evidence and mark Java v154 evidence missing."
                ),
                error(
                        "readOnly is false or executionAllowed is true",
                        "FAIL_CLOSED",
                        "Treat the producer output as unsafe and refuse regular-gate promotion."
                ),
                error(
                        "source Java v153 field removed or renamed",
                        "CONTRACT_BREAK",
                        "Require a new Node consumer plan before consuming this producer output."
                )
        );
    }

    private List<String> compatibilityGuarantees() {
        return List.of(
                "v153-shard-readiness-core-fields-unchanged",
                "v370-v373-node-archive-chain-not-mutated",
                "hardening-output-is-additive-sibling-evidence",
                "no-order-payment-inventory-ledger-sql-change"
        );
    }

    private List<String> forbiddenChanges() {
        return List.of(
                "mutate-e-153-archive",
                "mutate-node-v370-v373-archives",
                "change-v153-endpoint-field-names-or-types",
                "enable-shard-routing-or-execution-from-hardening-endpoint",
                "add-order-payment-inventory-ledger-sql-side-effects"
        );
    }

    private OpsShardReadinessHardeningResponse.FieldExplanation field(
            String field,
            String producer,
            String consumer,
            String meaning,
            String compatibility
    ) {
        return new OpsShardReadinessHardeningResponse.FieldExplanation(
                field,
                producer,
                consumer,
                meaning,
                compatibility
        );
    }

    private OpsShardReadinessHardeningResponse.ErrorSemantic error(
            String condition,
            String status,
            String nodeInterpretation
    ) {
        return new OpsShardReadinessHardeningResponse.ErrorSemantic(
                condition,
                status,
                nodeInterpretation
        );
    }
}
