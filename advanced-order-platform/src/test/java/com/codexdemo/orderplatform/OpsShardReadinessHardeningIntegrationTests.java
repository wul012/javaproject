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
class OpsShardReadinessHardeningIntegrationTests extends OpsOverviewIntegrationTestSupport {

    @Test
    void opsShardReadinessHardeningReturnsAdditiveEvidence() throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/hardening"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v154"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.sourceEvidenceVersion").value("Java v153"))
                .andExpect(jsonPath("$.sourceEndpoint").value("/api/v1/ops/shard-readiness"))
                .andExpect(jsonPath("$.sourceEvidencePath").value("e/153/evidence/java-shard-readiness-v153.json"))
                .andExpect(jsonPath("$.fieldExplanations.length()").value(5))
                .andExpect(jsonPath("$.fieldExplanations[3].field").value("routingMode"))
                .andExpect(jsonPath("$.errorSemantics.length()").value(3))
                .andExpect(jsonPath("$.errorSemantics[1].status").value("FAIL_CLOSED"))
                .andExpect(jsonPath("$.compatibilityGuarantees[1]")
                        .value("v370-v373-node-archive-chain-not-mutated"))
                .andExpect(jsonPath("$.forbiddenChanges[0]").value("mutate-e-153-archive"))
                .andExpect(jsonPath("$.evidencePath")
                        .value("e/154/evidence/java-shard-readiness-hardening-v154.json"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void staticShardReadinessHardeningFixtureMatchesContractFields() throws Exception {
        mockMvc.perform(get("/contracts/java-shard-readiness-hardening-v154.fixture.json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v154"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.sourceEvidenceVersion").value("Java v153"))
                .andExpect(jsonPath("$.fieldExplanations.length()").value(5))
                .andExpect(jsonPath("$.errorSemantics.length()").value(3))
                .andExpect(jsonPath("$.compatibilityGuarantees[2]")
                        .value("hardening-output-is-additive-sibling-evidence"))
                .andExpect(jsonPath("$.status").value("passed"));
    }
}
