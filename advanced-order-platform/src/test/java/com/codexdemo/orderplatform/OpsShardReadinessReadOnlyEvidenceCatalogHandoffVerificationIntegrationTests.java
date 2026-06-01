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
class OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationIntegrationTests
        extends OpsOverviewIntegrationTestSupport {

    @Test
    void opsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationReturnsReceipt()
            throws Exception {
        mockMvc.perform(get(
                        "/api/v1/ops/shard-readiness/read-only-evidence-catalog-handoff-verification"
                ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v179"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.sourceCatalogVersion").value("Java v175"))
                .andExpect(jsonPath("$.sourceHandoffVersion").value("Java v177"))
                .andExpect(jsonPath("$.sourceCatalogPassed").value(true))
                .andExpect(jsonPath("$.sourceHandoffPassed").value(true))
                .andExpect(jsonPath("$.sourceCatalogFrozen").value(true))
                .andExpect(jsonPath("$.frozenCatalogLiveEndpointCount").value(20))
                .andExpect(jsonPath("$.currentLiveEndpointCount").value(22))
                .andExpect(jsonPath("$.currentRegistryIncludesVerification").value(true))
                .andExpect(jsonPath("$.futureEndpointGrowthPreservesV175Catalog").value(true))
                .andExpect(jsonPath("$.sourceBoundariesHeld").value(true))
                .andExpect(jsonPath("$.nodeMayStartOrStopJavaOrMiniKv").value(false))
                .andExpect(jsonPath("$.receiptId")
                        .value("java-shard-readiness-read-only-evidence-catalog-handoff-verification-receipt-v179"))
                .andExpect(jsonPath("$.verificationChecks[8]")
                        .value("v175-catalog-does-not-include-v179-verification:true"))
                .andExpect(jsonPath("$.blockedOperations[6]").value("node-start-or-stop-java-or-mini-kv"))
                .andExpect(jsonPath("$.evidencePath")
                        .value("e/179/evidence/"
                                + "java-shard-readiness-read-only-evidence-catalog-handoff-verification-v179.json"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void staticShardReadinessReadOnlyEvidenceCatalogHandoffVerificationFixtureMatchesContractFields()
            throws Exception {
        mockMvc.perform(get(
                        "/contracts/"
                                + "java-shard-readiness-read-only-evidence-catalog-handoff-verification-v179.fixture.json"
                ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v179"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.sourceCatalogFrozen").value(true))
                .andExpect(jsonPath("$.frozenCatalogLiveEndpointCount").value(20))
                .andExpect(jsonPath("$.currentLiveEndpointCount").value(22))
                .andExpect(jsonPath("$.receiptId")
                        .value("java-shard-readiness-read-only-evidence-catalog-handoff-verification-receipt-v179"))
                .andExpect(jsonPath("$.status").value("passed"));
    }
}
