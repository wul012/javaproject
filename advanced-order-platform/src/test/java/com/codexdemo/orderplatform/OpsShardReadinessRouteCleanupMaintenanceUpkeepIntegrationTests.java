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
class OpsShardReadinessRouteCleanupMaintenanceUpkeepIntegrationTests
        extends OpsOverviewIntegrationTestSupport {

    @Test
    void routeCleanupMaintenanceUpkeepCatalogReturnsVersionedMaintenanceItems()
            throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/route-cleanup-maintenance-upkeep-catalog"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v489"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/route-cleanup-maintenance-upkeep-catalog"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-route-cleanup-maintenance-upkeep-catalog.v1"))
                .andExpect(jsonPath("$.itemCount").value(9))
                .andExpect(jsonPath("$.firstServiceVersion").value(471))
                .andExpect(jsonPath("$.latestRouteVersion").value(488))
                .andExpect(jsonPath("$.items[0].name").value("segment-catalog"))
                .andExpect(jsonPath("$.items[8].name").value("closeout"))
                .andExpect(jsonPath("$.items[8].routeVersion").value(488))
                .andExpect(jsonPath("$.checks[4]").value("upkeep-catalog-remains-read-only"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void routeCleanupMaintenanceConsumerHandoffMatrixReturnsConsumerBoundaries()
            throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/route-cleanup-maintenance-consumer-handoff-matrix"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v491"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/route-cleanup-maintenance-consumer-handoff-matrix"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-route-cleanup-maintenance-consumer-handoff-matrix.v1"))
                .andExpect(jsonPath("$.matrixEntryCount").value(9))
                .andExpect(jsonPath("$.consumerCount").value(9))
                .andExpect(jsonPath("$.forbiddenOperationCount").value(7))
                .andExpect(jsonPath("$.matrix[4].consumer").value("runtime-boundary-reviewer"))
                .andExpect(jsonPath("$.matrix[4].boundary").value("read-only-boundary"))
                .andExpect(jsonPath("$.forbiddenOperations[4]").value("managed-audit-connection"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void routeCleanupMaintenanceCiExpectationManifestReturnsRegressionPlan()
            throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/route-cleanup-maintenance-ci-expectation-manifest"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v493"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/route-cleanup-maintenance-ci-expectation-manifest"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-route-cleanup-maintenance-ci-expectation-manifest.v1"))
                .andExpect(jsonPath("$.expectationCount").value(9))
                .andExpect(jsonPath("$.laneCount").value(4))
                .andExpect(jsonPath("$.startsJavaService").value(false))
                .andExpect(jsonPath("$.startsMiniKvService").value(false))
                .andExpect(jsonPath("$.expectations[0].focusedTestClass")
                        .value("OpsShardReadinessRouteCleanupMaintenanceSegmentCatalogServiceTests"))
                .andExpect(jsonPath("$.expectations[8].focusedTestClass")
                        .value("OpsShardReadinessRouteCleanupMaintenanceCloseoutServiceTests"))
                .andExpect(jsonPath("$.checks[4]").value("ci-manifest-does-not-start-upstreams"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void routeCleanupMaintenanceRouteTopologyIndexReturnsNeighborMap()
            throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/route-cleanup-maintenance-route-topology-index"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v495"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/route-cleanup-maintenance-route-topology-index"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-route-cleanup-maintenance-route-topology-index.v1"))
                .andExpect(jsonPath("$.routeCount").value(9))
                .andExpect(jsonPath("$.firstRouteVersion").value(472))
                .andExpect(jsonPath("$.latestRouteVersion").value(488))
                .andExpect(jsonPath("$.routes[0].previousEndpoint").value("none"))
                .andExpect(jsonPath("$.routes[0].nextEndpoint")
                        .value("/api/v1/ops/shard-readiness/route-cleanup-maintenance-continuity"))
                .andExpect(jsonPath("$.routes[8].nextEndpoint").value("none"))
                .andExpect(jsonPath("$.checks[4]").value("topology-index-remains-read-only"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void routeCleanupMaintenanceFailClosedPolicyReturnsZeroViolationGuards()
            throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/route-cleanup-maintenance-fail-closed-policy"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v497"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/route-cleanup-maintenance-fail-closed-policy"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-route-cleanup-maintenance-fail-closed-policy.v1"))
                .andExpect(jsonPath("$.policyCount").value(7))
                .andExpect(jsonPath("$.protectedItemCount").value(9))
                .andExpect(jsonPath("$.zeroViolationCount").value(7))
                .andExpect(jsonPath("$.policies[4].operation").value("managed-audit-connection"))
                .andExpect(jsonPath("$.policies[4].violationCount").value(0))
                .andExpect(jsonPath("$.checks[4]").value("fail-closed-policy-remains-read-only"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

}
