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
class OpsShardReadinessDeclaredOperatorLifecycleIntegrationTests extends OpsOverviewIntegrationTestSupport {

    @Test
    void opsShardReadinessDeclaredOperatorLifecycleReturnsDeclaredEvidence() throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/declared-operator-lifecycle"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v161"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.operatorOwned").value(true))
                .andExpect(jsonPath("$.operatorLifecycleDeclared").value(true))
                .andExpect(jsonPath("$.startupCommandDeclared").value(true))
                .andExpect(jsonPath("$.portDeclared").value(true))
                .andExpect(jsonPath("$.getOnlySmokeDeclared").value(true))
                .andExpect(jsonPath("$.cleanupDeclared").value(true))
                .andExpect(jsonPath("$.failClosedDeclared").value(true))
                .andExpect(jsonPath("$.runtimeProbeAllowed").value(false))
                .andExpect(jsonPath("$.nodeMayStartService").value(false))
                .andExpect(jsonPath("$.nodeMayStopService").value(false))
                .andExpect(jsonPath("$.sourceLifecycleEvidenceVersion").value("Java v160"))
                .andExpect(jsonPath("$.lastVerifiedByNodeVersion").value("Node v387"))
                .andExpect(jsonPath("$.nextNodeConsumerHint").value("Node v388"))
                .andExpect(jsonPath("$.javaServiceOwner").value("java-platform-operator"))
                .andExpect(jsonPath("$.declaredStartupCommand")
                        .value("mvn spring-boot:run -Dspring-boot.run.profiles=local"))
                .andExpect(jsonPath("$.declaredPorts[0]").value("8080"))
                .andExpect(jsonPath("$.javaBaseUrlHandle").value("java-local-readonly-base-url"))
                .andExpect(jsonPath("$.getOnlySmokeTargets[1]")
                        .value("GET /api/v1/ops/shard-readiness/declared-operator-lifecycle"))
                .andExpect(jsonPath("$.failClosedRules[5]").value("failed-java-smoke-blocks-node-consumption"))
                .andExpect(jsonPath("$.runtimeGatePrerequisites[0]")
                        .value("mini-kv-declared-operator-lifecycle-evidence"))
                .andExpect(jsonPath("$.stopConditions[3]")
                        .value("request-would-run-runtime-probe-before-mini-kv-declared-lifecycle"))
                .andExpect(jsonPath("$.evidencePath")
                        .value("e/161/evidence/java-shard-readiness-declared-operator-lifecycle-v161.json"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void staticShardReadinessDeclaredOperatorLifecycleFixtureMatchesContractFields() throws Exception {
        mockMvc.perform(get("/contracts/java-shard-readiness-declared-operator-lifecycle-v161.fixture.json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v161"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.operatorLifecycleDeclared").value(true))
                .andExpect(jsonPath("$.startupCommandDeclared").value(true))
                .andExpect(jsonPath("$.portDeclared").value(true))
                .andExpect(jsonPath("$.runtimeProbeAllowed").value(false))
                .andExpect(jsonPath("$.sourceLifecycleEvidenceVersion").value("Java v160"))
                .andExpect(jsonPath("$.lastVerifiedByNodeVersion").value("Node v387"))
                .andExpect(jsonPath("$.declaredPorts[0]").value("8080"))
                .andExpect(jsonPath("$.getOnlySmokeTargets[2]")
                        .value("GET /api/v1/ops/shard-readiness/operator-service-lifecycle"))
                .andExpect(jsonPath("$.cleanupResponsibilities[1]")
                        .value("node-must-not-stop-java-from-declared-evidence"))
                .andExpect(jsonPath("$.status").value("passed"));
    }
}
