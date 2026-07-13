package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.prototype.OpsShardReadinessPrototypeEvidenceService.PrototypeRoutes;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessEvidenceHandoffService;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessEvidenceIndexService;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessEvidenceVerificationService;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessService;
import com.codexdemo.orderplatform.ops.maintenance.readonlyevidence.OpsShardReadinessReadOnlyEndpointRegistryIntegrityService;
import com.codexdemo.orderplatform.ops.maintenance.readonlyevidence.OpsShardReadinessReadOnlyEvidenceCatalogHandoffService;
import com.codexdemo.orderplatform.ops.maintenance.readonlyevidence.OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationService;
import com.codexdemo.orderplatform.ops.maintenance.readonlyevidence.OpsShardReadinessReadOnlyEvidenceCatalogService;
import com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1ContractAlignmentHandoffService;
import com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1ContractAlignmentService;
import com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1ContractConsumerEvidenceDigestService;
import com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1ContractConsumerHandoffBundleService;
import com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1ContractConsumerProbePlanService;
import com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1ContractConsumerReadinessHandoffService;
import com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1ContractConsumerVerificationChecklistService;
import com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1ContractEndpointCatalogService;
import com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1ContractEvidencePacketService;
import com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1ContractHandoffManifestService;
import com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1ContractOperatorChecklistService;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class OpsShardReadinessRoutePathsTests {

  @Test
  void evidenceServiceEndpointsUseSharedRouteConstants() {
    assertThat(
            Map.ofEntries(
                Map.entry(
                    OpsShardReadinessRoutePaths.READ_ONLY_EVIDENCE_CATALOG,
                    OpsShardReadinessReadOnlyEvidenceCatalogService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.READ_ONLY_EVIDENCE_CATALOG_HANDOFF,
                    OpsShardReadinessReadOnlyEvidenceCatalogHandoffService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.READ_ONLY_EVIDENCE_CATALOG_HANDOFF_VERIFICATION,
                    OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.READ_ONLY_ENDPOINT_REGISTRY_INTEGRITY,
                    OpsShardReadinessReadOnlyEndpointRegistryIntegrityService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessService.EVIDENCE_INDEX_PATH,
                    OpsShardReadinessEvidenceIndexService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessService.EVIDENCE_VERIFICATION_PATH,
                    OpsShardReadinessEvidenceVerificationService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessService.EVIDENCE_HANDOFF_PATH,
                    OpsShardReadinessEvidenceHandoffService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.V1_CONTRACT_ALIGNMENT,
                    OpsShardReadinessV1ContractAlignmentService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.V1_CONTRACT_ALIGNMENT_HANDOFF,
                    OpsShardReadinessV1ContractAlignmentHandoffService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.V1_CONTRACT_EVIDENCE_PACKET,
                    OpsShardReadinessV1ContractEvidencePacketService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.V1_CONTRACT_OPERATOR_CHECKLIST,
                    OpsShardReadinessV1ContractOperatorChecklistService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.V1_CONTRACT_HANDOFF_MANIFEST,
                    OpsShardReadinessV1ContractHandoffManifestService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.V1_CONTRACT_CONSUMER_PROBE_PLAN,
                    OpsShardReadinessV1ContractConsumerProbePlanService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.V1_CONTRACT_ENDPOINT_CATALOG,
                    OpsShardReadinessV1ContractEndpointCatalogService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.V1_CONTRACT_CONSUMER_HANDOFF_BUNDLE,
                    OpsShardReadinessV1ContractConsumerHandoffBundleService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.V1_CONTRACT_CONSUMER_VERIFICATION_CHECKLIST,
                    OpsShardReadinessV1ContractConsumerVerificationChecklistService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.V1_CONTRACT_CONSUMER_EVIDENCE_DIGEST,
                    OpsShardReadinessV1ContractConsumerEvidenceDigestService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.V1_CONTRACT_CONSUMER_READINESS_HANDOFF,
                    OpsShardReadinessV1ContractConsumerReadinessHandoffService.ENDPOINT)))
        .allSatisfy(
            (route, endpoint) ->
                assertThat(endpoint).isEqualTo(OpsShardReadinessService.BASE_PATH + route));
  }

  @Test
  void prototypeRoutesKeepTheirFamilyOwnedBytes() {
    assertThat(PrototypeRoutes.BASE_PATH).isEqualTo(OpsShardReadinessService.BASE_PATH);
    assertThat(
            List.of(
                PrototypeRoutes.CATALOG,
                PrototypeRoutes.FIXTURE_ECHO,
                PrototypeRoutes.FIELD_ALIGNMENT,
                PrototypeRoutes.READ_ONLY_BRIDGE,
                PrototypeRoutes.CLEANUP_BRIDGE,
                PrototypeRoutes.READ_WINDOW_HANDOFF,
                PrototypeRoutes.CONSUMER_GATE_PACKET,
                PrototypeRoutes.OPERATOR_CI_HANDOFF,
                PrototypeRoutes.AUDIT_DIGEST,
                PrototypeRoutes.CLOSEOUT,
                PrototypeRoutes.HANDOFF_CATALOG,
                PrototypeRoutes.HANDOFF_ENDPOINT_INVENTORY,
                PrototypeRoutes.HANDOFF_BOUNDARY_MATRIX,
                PrototypeRoutes.HANDOFF_CONSUMER_CHECKLIST,
                PrototypeRoutes.HANDOFF_READ_WINDOW_CHECKLIST,
                PrototypeRoutes.HANDOFF_DIGEST_MANIFEST,
                PrototypeRoutes.HANDOFF_CI_MANIFEST,
                PrototypeRoutes.HANDOFF_ARCHIVE_MANIFEST,
                PrototypeRoutes.HANDOFF_OPERATOR_SIGNOFF,
                PrototypeRoutes.HANDOFF_CLOSEOUT,
                PrototypeRoutes.CONSUMER_CATALOG,
                PrototypeRoutes.CONSUMER_SOURCE_INVENTORY,
                PrototypeRoutes.CONSUMER_FIELD_CHECKLIST,
                PrototypeRoutes.CONSUMER_ROUTE_PREVIEW,
                PrototypeRoutes.CONSUMER_BOUNDARY_MATRIX,
                PrototypeRoutes.CONSUMER_DIGEST_ACCEPTANCE,
                PrototypeRoutes.CONSUMER_CI_PLAN,
                PrototypeRoutes.CONSUMER_ARCHIVE_MANIFEST,
                PrototypeRoutes.CONSUMER_OPERATOR_SIGNOFF,
                PrototypeRoutes.CONSUMER_CLOSEOUT))
        .containsExactly(
            "/prototype-catalog",
            "/prototype-fixture-echo",
            "/prototype-field-alignment",
            "/prototype-read-only-integration-bridge",
            "/prototype-route-cleanup-bridge",
            "/prototype-read-window-handoff",
            "/prototype-consumer-gate-packet",
            "/prototype-operator-ci-handoff",
            "/prototype-audit-digest",
            "/prototype-closeout",
            "/prototype-handoff-catalog",
            "/prototype-handoff-endpoint-inventory",
            "/prototype-handoff-boundary-matrix",
            "/prototype-handoff-consumer-verification-checklist",
            "/prototype-handoff-read-window-checklist",
            "/prototype-handoff-digest-manifest",
            "/prototype-handoff-ci-manifest",
            "/prototype-handoff-archive-manifest",
            "/prototype-handoff-operator-signoff-packet",
            "/prototype-handoff-closeout",
            "/prototype-consumer-gate-catalog",
            "/prototype-consumer-gate-source-inventory",
            "/prototype-consumer-gate-minimal-field-checklist",
            "/prototype-consumer-gate-route-topology-preview",
            "/prototype-consumer-gate-boundary-matrix",
            "/prototype-consumer-gate-digest-acceptance",
            "/prototype-consumer-gate-ci-batch-plan",
            "/prototype-consumer-gate-archive-manifest",
            "/prototype-consumer-gate-operator-signoff",
            "/prototype-consumer-gate-closeout");
  }
}
