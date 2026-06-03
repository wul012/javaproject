package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.Test;

class OpsShardReadinessRoutePathsTests {

    @Test
    void evidenceServiceEndpointsUseSharedRouteConstants() {
        assertThat(Map.ofEntries(
                Map.entry(
                        OpsShardReadinessRoutePaths.READ_ONLY_EVIDENCE_CATALOG,
                        OpsShardReadinessReadOnlyEvidenceCatalogService.ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths.READ_ONLY_EVIDENCE_CATALOG_HANDOFF,
                        OpsShardReadinessReadOnlyEvidenceCatalogHandoffService.ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths.READ_ONLY_EVIDENCE_CATALOG_HANDOFF_VERIFICATION,
                        OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationService.ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths.READ_ONLY_ENDPOINT_REGISTRY_INTEGRITY,
                        OpsShardReadinessReadOnlyEndpointRegistryIntegrityService.ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths.EVIDENCE_INDEX,
                        OpsShardReadinessEvidenceIndexService.ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths.EVIDENCE_VERIFICATION,
                        OpsShardReadinessEvidenceVerificationService.ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths.EVIDENCE_HANDOFF,
                        OpsShardReadinessEvidenceHandoffService.ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths.V1_CONTRACT_ALIGNMENT,
                        OpsShardReadinessV1ContractAlignmentService.ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths.V1_CONTRACT_ALIGNMENT_HANDOFF,
                        OpsShardReadinessV1ContractAlignmentHandoffService.ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths.V1_CONTRACT_EVIDENCE_PACKET,
                        OpsShardReadinessV1ContractEvidencePacketService.ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths.V1_CONTRACT_OPERATOR_CHECKLIST,
                        OpsShardReadinessV1ContractOperatorChecklistService.ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths.V1_CONTRACT_HANDOFF_MANIFEST,
                        OpsShardReadinessV1ContractHandoffManifestService.ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths.V1_CONTRACT_CONSUMER_PROBE_PLAN,
                        OpsShardReadinessV1ContractConsumerProbePlanService.ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths.V1_CONTRACT_ENDPOINT_CATALOG,
                        OpsShardReadinessV1ContractEndpointCatalogService.ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths.V1_CONTRACT_CONSUMER_HANDOFF_BUNDLE,
                        OpsShardReadinessV1ContractConsumerHandoffBundleService.ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths.V1_CONTRACT_CONSUMER_VERIFICATION_CHECKLIST,
                        OpsShardReadinessV1ContractConsumerVerificationChecklistService.ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths.V1_CONTRACT_CONSUMER_EVIDENCE_DIGEST,
                        OpsShardReadinessV1ContractConsumerEvidenceDigestService.ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths.V1_CONTRACT_CONSUMER_READINESS_HANDOFF,
                        OpsShardReadinessV1ContractConsumerReadinessHandoffService.ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths.ROUTE_CLEANUP_EVIDENCE_CATALOG,
                        OpsShardReadinessRouteCleanupEvidenceService.ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths.ROUTE_CLEANUP_PHASE_SUMMARY,
                        OpsShardReadinessRouteCleanupPhaseSummaryService.ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths.ROUTE_CLEANUP_BOUNDARY_MATRIX,
                        OpsShardReadinessRouteCleanupBoundaryMatrixService.ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths.ROUTE_CLEANUP_HANDOFF_CHECKLIST,
                        OpsShardReadinessRouteCleanupHandoffChecklistService.ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths.ROUTE_CLEANUP_ARCHIVE_PLAN,
                        OpsShardReadinessRouteCleanupArchivePlanService.ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths.ROUTE_CLEANUP_DIGEST,
                        OpsShardReadinessRouteCleanupDigestService.ENDPOINT
                )
        )).allSatisfy((route, endpoint) ->
                assertThat(endpoint).isEqualTo(OpsShardReadinessRoutePaths.BASE_PATH + route));
    }
}
