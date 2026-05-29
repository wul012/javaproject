package com.codexdemo.orderplatform;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
        "order.expiration.enabled=false",
        "outbox.publisher.enabled=false"
})
@AutoConfigureMockMvc
class OpsShardReadinessOperatorServiceLifecycleIntegrationTests extends OpsOverviewIntegrationTestSupport {

    @Test
    void opsShardReadinessOperatorServiceLifecycleReturnsOperatorOwnedEvidence() throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/operator-service-lifecycle"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v160"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.operatorOwned").value(true))
                .andExpect(jsonPath("$.runtimeProbeAllowed").value(false))
                .andExpect(jsonPath("$.nodeMayStartService").value(false))
                .andExpect(jsonPath("$.nodeMayStopService").value(false))
                .andExpect(jsonPath("$.sourceGatePlanVersion").value("Java v159"))
                .andExpect(jsonPath("$.lastVerifiedByNodeVersion").value("Node v385"))
                .andExpect(jsonPath("$.nextNodeConsumerHint").value("Node v386"))
                .andExpect(jsonPath("$.javaServiceOwner").value("java-service-operator-placeholder"))
                .andExpect(jsonPath("$.javaPortDeclaration").value("operator-declared-port-before-window"))
                .andExpect(jsonPath("$.javaBaseUrlTemplate").value("http://127.0.0.1:{java-port}"))
                .andExpect(jsonPath("$.operatorPrerequisites.length()").value(5))
                .andExpect(jsonPath("$.getOnlySmokeTargets[1]")
                        .value("GET /api/v1/ops/shard-readiness/operator-service-lifecycle"))
                .andExpect(jsonPath("$.failClosedRules[3]").value("non-get-smoke-target-blocks-runtime-probe"))
                .andExpect(jsonPath("$.cleanupResponsibilities[1]")
                        .value("node-must-not-stop-java-from-this-evidence"))
                .andExpect(jsonPath("$.stopConditions[5]")
                        .value("request-would-read-credential-or-raw-endpoint-value"))
                .andExpect(jsonPath("$.evidencePath")
                        .value("e/160/evidence/java-shard-readiness-operator-service-lifecycle-v160.json"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void staticShardReadinessOperatorServiceLifecycleFixtureMatchesContractFields() throws Exception {
        mockMvc.perform(get("/contracts/java-shard-readiness-operator-service-lifecycle-v160.fixture.json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v160"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.operatorOwned").value(true))
                .andExpect(jsonPath("$.runtimeProbeAllowed").value(false))
                .andExpect(jsonPath("$.sourceGatePlanVersion").value("Java v159"))
                .andExpect(jsonPath("$.lastVerifiedByNodeVersion").value("Node v385"))
                .andExpect(jsonPath("$.nodeMayStartService").value(false))
                .andExpect(jsonPath("$.nodeMayStopService").value(false))
                .andExpect(jsonPath("$.operatorPrerequisites[4]")
                        .value("operator-confirms-no-credential-or-raw-endpoint-value-read"))
                .andExpect(jsonPath("$.getOnlySmokeTargets[2]")
                        .value("GET /api/v1/ops/shard-readiness/live-read-gate-plan"))
                .andExpect(jsonPath("$.failClosedRules[4]").value("failed-smoke-blocks-node-consumption"))
                .andExpect(jsonPath("$.cleanupResponsibilities[2]")
                        .value("node-may-clean-only-processes-started-by-a-separate-approved-runtime-plan"))
                .andExpect(jsonPath("$.status").value("passed"));
    }
}
