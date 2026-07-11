package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.prototype.OpsShardReadinessPrototypeEvidenceService.PrototypeRoutes;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessEvidenceHandoffService;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessEvidenceIndexService;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessEvidenceVerificationService;
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
                    OpsShardReadinessRoutePaths.EVIDENCE_INDEX,
                    OpsShardReadinessEvidenceIndexService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.EVIDENCE_VERIFICATION,
                    OpsShardReadinessEvidenceVerificationService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.EVIDENCE_HANDOFF,
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
                    OpsShardReadinessV1ContractConsumerReadinessHandoffService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.ROUTE_CLEANUP_EVIDENCE_CATALOG,
                    OpsShardReadinessRouteCleanupEvidenceService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.ROUTE_CLEANUP_PHASE_SUMMARY,
                    OpsShardReadinessRouteCleanupPhaseSummaryService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.ROUTE_CLEANUP_BOUNDARY_MATRIX,
                    OpsShardReadinessRouteCleanupBoundaryMatrixService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.ROUTE_CLEANUP_HANDOFF_CHECKLIST,
                    OpsShardReadinessRouteCleanupHandoffChecklistService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.ROUTE_CLEANUP_ARCHIVE_PLAN,
                    OpsShardReadinessRouteCleanupArchivePlanService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.ROUTE_CLEANUP_DIGEST,
                    OpsShardReadinessRouteCleanupDigestService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.ROUTE_CLEANUP_SOURCE_PLAN_ALIGNMENT,
                    OpsShardReadinessRouteCleanupSourcePlanAlignmentService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.ROUTE_CLEANUP_RELEASE_HANDOFF,
                    OpsShardReadinessRouteCleanupReleaseHandoffService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.ROUTE_CLEANUP_OPERATOR_RUNBOOK,
                    OpsShardReadinessRouteCleanupOperatorRunbookService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.ROUTE_CLEANUP_READ_ONLY_GATE,
                    OpsShardReadinessRouteCleanupReadOnlyGateService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.ROUTE_CLEANUP_SUITE_CLOSEOUT,
                    OpsShardReadinessRouteCleanupSuiteCloseoutService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.ROUTE_CLEANUP_ARCHIVE_VERIFICATION,
                    OpsShardReadinessRouteCleanupArchiveVerificationService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.ROUTE_CLEANUP_CONSUMER_PACKET,
                    OpsShardReadinessRouteCleanupConsumerPacketService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.ROUTE_CLEANUP_CI_EVIDENCE,
                    OpsShardReadinessRouteCleanupCiEvidenceService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.ROUTE_CLEANUP_ENDPOINT_MANIFEST,
                    OpsShardReadinessRouteCleanupEndpointManifestService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.ROUTE_CLEANUP_REGRESSION_GUARD,
                    OpsShardReadinessRouteCleanupRegressionGuardService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.ROUTE_CLEANUP_HANDOFF_BUNDLE,
                    OpsShardReadinessRouteCleanupHandoffBundleService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.ROUTE_CLEANUP_CONTINUITY_REPORT,
                    OpsShardReadinessRouteCleanupContinuityReportService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.ROUTE_CLEANUP_CONSUMER_CHECKLIST,
                    OpsShardReadinessRouteCleanupConsumerChecklistService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.ROUTE_CLEANUP_FINAL_DIGEST,
                    OpsShardReadinessRouteCleanupFinalDigestService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.ROUTE_CLEANUP_EXTENDED_CLOSEOUT,
                    OpsShardReadinessRouteCleanupExtendedCloseoutService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.ROUTE_CLEANUP_AUDIT_TRAIL,
                    OpsShardReadinessRouteCleanupAuditTrailService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.ROUTE_CLEANUP_ACCEPTANCE_RECEIPT,
                    OpsShardReadinessRouteCleanupAcceptanceReceiptService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.ROUTE_CLEANUP_EVIDENCE_REGISTER,
                    OpsShardReadinessRouteCleanupEvidenceRegisterService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.ROUTE_CLEANUP_OPERATIONAL_SNAPSHOT,
                    OpsShardReadinessRouteCleanupOperationalSnapshotService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.ROUTE_CLEANUP_POLICY_GUARD,
                    OpsShardReadinessRouteCleanupPolicyGuardService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.ROUTE_CLEANUP_REVIEWER_PACKET,
                    OpsShardReadinessRouteCleanupReviewerPacketService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.ROUTE_CLEANUP_TRANSITION_BRIEF,
                    OpsShardReadinessRouteCleanupTransitionBriefService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.ROUTE_CLEANUP_FINAL_VERIFICATION,
                    OpsShardReadinessRouteCleanupFinalVerificationService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.ROUTE_CLEANUP_FINAL_ARCHIVE_PLAN,
                    OpsShardReadinessRouteCleanupFinalArchivePlanService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.ROUTE_CLEANUP_THIRD_RUN_CLOSEOUT,
                    OpsShardReadinessRouteCleanupThirdRunCloseoutService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.ROUTE_CLEANUP_COMPLETION_INDEX,
                    OpsShardReadinessRouteCleanupCompletionIndexService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.ROUTE_CLEANUP_COMPLETION_CERTIFICATE,
                    OpsShardReadinessRouteCleanupCompletionCertificateService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.ROUTE_CLEANUP_POST_PUSH_CLOSEOUT,
                    OpsShardReadinessRouteCleanupPostPushCloseoutService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.ROUTE_CLEANUP_CI_RUN_ATTESTATION,
                    OpsShardReadinessRouteCleanupCiRunAttestationService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.ROUTE_CLEANUP_TAG_MANIFEST,
                    OpsShardReadinessRouteCleanupTagManifestService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.ROUTE_CLEANUP_RELEASE_EVIDENCE_BUNDLE,
                    OpsShardReadinessRouteCleanupReleaseEvidenceBundleService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.ROUTE_CLEANUP_CONSUMER_SIGNOFF_PACKET,
                    OpsShardReadinessRouteCleanupConsumerSignoffPacketService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.ROUTE_CLEANUP_ARCHIVE_HANDOFF_RECEIPT,
                    OpsShardReadinessRouteCleanupArchiveHandoffReceiptService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_BOUNDARY_REPORT,
                    OpsShardReadinessRouteCleanupMaintenanceBoundaryReportService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.ROUTE_CLEANUP_FIXTURE_COVERAGE_INDEX,
                    OpsShardReadinessRouteCleanupFixtureCoverageIndexService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.ROUTE_CLEANUP_COMPLETION_AUDIT_DIGEST,
                    OpsShardReadinessRouteCleanupCompletionAuditDigestService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.ROUTE_CLEANUP_POST_COMPLETION_CLOSEOUT,
                    OpsShardReadinessRouteCleanupPostCompletionCloseoutService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_SEGMENT_CATALOG,
                    OpsShardReadinessRouteCleanupMaintenanceSegmentCatalogService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_CONTINUITY,
                    OpsShardReadinessRouteCleanupMaintenanceContinuityService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_LATEST_SIBLING_REPORT,
                    OpsShardReadinessRouteCleanupMaintenanceLatestSiblingService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_HANDOFF_PAIR_AUDIT,
                    OpsShardReadinessRouteCleanupMaintenanceHandoffPairAuditService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_BOUNDARY_DRIFT,
                    OpsShardReadinessRouteCleanupMaintenanceBoundaryDriftService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_SOURCE_PLAN_ALIGNMENT,
                    OpsShardReadinessRouteCleanupMaintenanceSourcePlanAlignmentService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_TEST_BUDGET_PLAN,
                    OpsShardReadinessRouteCleanupMaintenanceTestBudgetPlanService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_ARCHIVE_MANIFEST,
                    OpsShardReadinessRouteCleanupMaintenanceArchiveManifestService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_CLOSEOUT,
                    OpsShardReadinessRouteCleanupMaintenanceCloseoutService.ENDPOINT)))
        .allSatisfy(
            (route, endpoint) ->
                assertThat(endpoint).isEqualTo(OpsShardReadinessRoutePaths.BASE_PATH + route));
  }

  @Test
  void prototypeRoutesKeepTheirFamilyOwnedBytes() {
    assertThat(PrototypeRoutes.BASE_PATH).isEqualTo(OpsShardReadinessRoutePaths.BASE_PATH);
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
