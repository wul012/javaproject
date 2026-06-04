package com.codexdemo.orderplatform;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.matchesPattern;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
        "order.expiration.enabled=false",
        "outbox.publisher.enabled=false"
})
@AutoConfigureMockMvc
class OpsShardReadinessPrototypeConsumerGateCatalogIntegrationTests
        extends OpsOverviewIntegrationTestSupport {

    @Test
    void opsShardReadinessPrototypeConsumerGateCatalogReturnsReadOnlyHandoffInputs()
            throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/prototype-consumer-gate-catalog"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value(matchesPattern("Java v\\d+")))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/prototype-consumer-gate-catalog"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-prototype-consumer-gate-catalog.v1"))
                .andExpect(jsonPath("$.sourceHandoffVersion").value("Java v447"))
                .andExpect(jsonPath("$.sourceHandoffEndpoint")
                        .value("/api/v1/ops/shard-readiness/prototype-handoff-closeout"))
                .andExpect(jsonPath("$.sourceHandoffEntryCount").value(10))
                .andExpect(jsonPath("$.contractName").value("shard-readiness.v1"))
                .andExpect(jsonPath("$.entryCount").value(greaterThanOrEqualTo(2)))
                .andExpect(jsonPath("$.entries[0].javaVersion").value(449))
                .andExpect(jsonPath("$.entries[0].key").value("consumer-gate-catalog"))
                .andExpect(jsonPath("$.entries[0].nodePlanVersion").value("Node v370"))
                .andExpect(jsonPath("$.forbiddenOperations[6]")
                        .value("node-start-or-stop-java-or-mini-kv"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void opsShardReadinessPrototypeConsumerGateSourceInventoryReturnsSourceEvidence()
            throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/prototype-consumer-gate-source-inventory"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v451"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/prototype-consumer-gate-source-inventory"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-prototype-consumer-gate-source-inventory.v1"))
                .andExpect(jsonPath("$.entryKey").value("consumer-gate-source-inventory"))
                .andExpect(jsonPath("$.nodePlanVersion").value("Node v370"))
                .andExpect(jsonPath("$.sourceHandoffVersion").value("Java v447"))
                .andExpect(jsonPath("$.sourceHandoffEndpoint")
                        .value("/api/v1/ops/shard-readiness/prototype-handoff-closeout"))
                .andExpect(jsonPath("$.evidenceRefs.length()").value(3))
                .andExpect(jsonPath("$.checks[3]").value("verify-source-entry-count-10"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void opsShardReadinessPrototypeConsumerGateMinimalFieldChecklistReturnsFieldEvidence()
            throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/prototype-consumer-gate-minimal-field-checklist"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v453"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/prototype-consumer-gate-minimal-field-checklist"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-prototype-consumer-gate-minimal-field-checklist.v1"))
                .andExpect(jsonPath("$.entryKey").value("consumer-gate-minimal-field-checklist"))
                .andExpect(jsonPath("$.contractName").value("shard-readiness.v1"))
                .andExpect(jsonPath("$.checks[0]").value("field-project-required"))
                .andExpect(jsonPath("$.checks[4]")
                        .value("field-shardEnabled-shardCount-slotCount-routingMode-evidencePath-status-required"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void opsShardReadinessPrototypeConsumerGateRouteTopologyReturnsTopologyEvidence()
            throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/prototype-consumer-gate-route-topology-preview"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v455"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/prototype-consumer-gate-route-topology-preview"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-prototype-consumer-gate-route-topology-preview.v1"))
                .andExpect(jsonPath("$.entryKey").value("consumer-gate-route-topology-preview"))
                .andExpect(jsonPath("$.checks[0]").value("topology-java-health-read-target"))
                .andExpect(jsonPath("$.checks[3]").value("topology-prototype-handoff-closeout-read-target"))
                .andExpect(jsonPath("$.checks[4]").value("topology-consumer-gate-catalog-read-target"))
                .andExpect(jsonPath("$.forbiddenOperations[1]").value("active-shard-router"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void opsShardReadinessPrototypeConsumerGateBoundaryMatrixReturnsBoundaryEvidence()
            throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/prototype-consumer-gate-boundary-matrix"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v457"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/prototype-consumer-gate-boundary-matrix"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-prototype-consumer-gate-boundary-matrix.v1"))
                .andExpect(jsonPath("$.entryKey").value("consumer-gate-boundary-matrix"))
                .andExpect(jsonPath("$.checks[0]").value("boundary-forbid-write-routing"))
                .andExpect(jsonPath("$.checks[3]").value("boundary-forbid-managed-audit-connection"))
                .andExpect(jsonPath("$.forbiddenOperations[2]").value("credential-value-read"))
                .andExpect(jsonPath("$.forbiddenOperations[6]")
                        .value("node-start-or-stop-java-or-mini-kv"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void opsShardReadinessPrototypeConsumerGateDigestAcceptanceReturnsDigestEvidence()
            throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/prototype-consumer-gate-digest-acceptance"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v459"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/prototype-consumer-gate-digest-acceptance"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-prototype-consumer-gate-digest-acceptance.v1"))
                .andExpect(jsonPath("$.entryKey").value("consumer-gate-digest-acceptance"))
                .andExpect(jsonPath("$.checks[2]").value("digest-covers-handoff-closeout-digest"))
                .andExpect(jsonPath("$.checks[4]").value("digest-covers-consumer-gate-evidence-path"))
                .andExpect(jsonPath("$.digestValue").isString())
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void opsShardReadinessPrototypeConsumerGateCiBatchPlanReturnsCiEvidence()
            throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/prototype-consumer-gate-ci-batch-plan"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v461"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/prototype-consumer-gate-ci-batch-plan"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-prototype-consumer-gate-ci-batch-plan.v1"))
                .andExpect(jsonPath("$.entryKey").value("consumer-gate-ci-batch-plan"))
                .andExpect(jsonPath("$.checks[0]").value("ci-focused-consumer-gate-service-tests-first"))
                .andExpect(jsonPath("$.checks[1]").value("ci-grouped-controller-and-route-tests-second"))
                .andExpect(jsonPath("$.checks[3]").value("ci-smoke-only-with-explicit-user-window"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void opsShardReadinessPrototypeConsumerGateArchiveManifestReturnsArchiveEvidence()
            throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/prototype-consumer-gate-archive-manifest"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v463"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/prototype-consumer-gate-archive-manifest"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-prototype-consumer-gate-archive-manifest.v1"))
                .andExpect(jsonPath("$.entryKey").value("consumer-gate-archive-manifest"))
                .andExpect(jsonPath("$.evidencePath")
                        .value("e/463/evidence/java-shard-readiness-prototype-consumer-gate-archive-manifest-v463.json"))
                .andExpect(jsonPath("$.checks[2]").value("archive-node-v370-can-pin-versioned-paths"))
                .andExpect(jsonPath("$.checks[3]").value("archive-runtime-artifacts-not-required"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void opsShardReadinessPrototypeConsumerGateOperatorSignoffReturnsOperatorEvidence()
            throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/prototype-consumer-gate-operator-signoff"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v465"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/prototype-consumer-gate-operator-signoff"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-prototype-consumer-gate-operator-signoff.v1"))
                .andExpect(jsonPath("$.entryKey").value("consumer-gate-operator-signoff"))
                .andExpect(jsonPath("$.checks[0]").value("operator-owns-java-read-window"))
                .andExpect(jsonPath("$.checks[1]").value("node-does-not-start-or-stop-java"))
                .andExpect(jsonPath("$.checks[4]").value("consumer-gate-fails-closed-on-status-mismatch"))
                .andExpect(jsonPath("$.forbiddenOperations[6]")
                        .value("node-start-or-stop-java-or-mini-kv"))
                .andExpect(jsonPath("$.status").value("passed"));
    }
}
