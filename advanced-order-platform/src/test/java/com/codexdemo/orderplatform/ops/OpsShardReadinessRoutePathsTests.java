package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.Test;

class OpsShardReadinessRoutePathsTests {

    @Test
    void evidenceServiceEndpointsUseSharedRouteConstants() {
        assertThat(Map.of(
                OpsShardReadinessRoutePaths.READ_ONLY_EVIDENCE_CATALOG,
                OpsShardReadinessReadOnlyEvidenceCatalogService.ENDPOINT,
                OpsShardReadinessRoutePaths.READ_ONLY_EVIDENCE_CATALOG_HANDOFF,
                OpsShardReadinessReadOnlyEvidenceCatalogHandoffService.ENDPOINT,
                OpsShardReadinessRoutePaths.READ_ONLY_EVIDENCE_CATALOG_HANDOFF_VERIFICATION,
                OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationService.ENDPOINT,
                OpsShardReadinessRoutePaths.READ_ONLY_ENDPOINT_REGISTRY_INTEGRITY,
                OpsShardReadinessReadOnlyEndpointRegistryIntegrityService.ENDPOINT,
                OpsShardReadinessRoutePaths.EVIDENCE_INDEX,
                OpsShardReadinessEvidenceIndexService.ENDPOINT,
                OpsShardReadinessRoutePaths.EVIDENCE_VERIFICATION,
                OpsShardReadinessEvidenceVerificationService.ENDPOINT,
                OpsShardReadinessRoutePaths.EVIDENCE_HANDOFF,
                OpsShardReadinessEvidenceHandoffService.ENDPOINT
        )).allSatisfy((route, endpoint) ->
                assertThat(endpoint).isEqualTo(OpsShardReadinessRoutePaths.BASE_PATH + route));
    }
}
