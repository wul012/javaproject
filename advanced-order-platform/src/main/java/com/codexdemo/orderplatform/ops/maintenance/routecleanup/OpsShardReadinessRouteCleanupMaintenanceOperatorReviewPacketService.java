package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupMaintenanceOperatorReviewPacketService {

  static final String ENDPOINT =
      RouteCleanupRoutes.BASE_PATH + RouteCleanupRoutes.MAINTENANCE_OPERATOR_REVIEW_PACKET;
  static final String PROFILE =
      "java-shard-readiness-route-cleanup-maintenance-operator-review-packet.v1";

  private final OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalogService upkeepCatalogService;
  private final OpsShardReadinessRouteCleanupMaintenanceConsumerHandoffMatrixService
      consumerHandoffMatrixService;
  private final OpsShardReadinessRouteCleanupMaintenanceCiExpectationManifestService
      ciExpectationManifestService;
  private final OpsShardReadinessRouteCleanupMaintenanceFailClosedPolicyService
      failClosedPolicyService;
  private final OpsShardReadinessRouteCleanupMaintenanceArchiveDigestLedgerService
      archiveDigestLedgerService;

  public OpsShardReadinessRouteCleanupMaintenanceOperatorReviewPacketService(
      OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalogService upkeepCatalogService,
      OpsShardReadinessRouteCleanupMaintenanceConsumerHandoffMatrixService
          consumerHandoffMatrixService,
      OpsShardReadinessRouteCleanupMaintenanceCiExpectationManifestService
          ciExpectationManifestService,
      OpsShardReadinessRouteCleanupMaintenanceFailClosedPolicyService failClosedPolicyService,
      OpsShardReadinessRouteCleanupMaintenanceArchiveDigestLedgerService
          archiveDigestLedgerService) {
    this.upkeepCatalogService = upkeepCatalogService;
    this.consumerHandoffMatrixService = consumerHandoffMatrixService;
    this.ciExpectationManifestService = ciExpectationManifestService;
    this.failClosedPolicyService = failClosedPolicyService;
    this.archiveDigestLedgerService = archiveDigestLedgerService;
  }

  @Transactional(readOnly = true)
  public OpsShardReadinessRouteCleanupMaintenanceOperatorReviewPacketResponse packet() {
    OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalogResponse catalog =
        upkeepCatalogService.catalog();
    OpsShardReadinessRouteCleanupMaintenanceConsumerHandoffMatrixResponse matrix =
        consumerHandoffMatrixService.matrix();
    OpsShardReadinessRouteCleanupMaintenanceCiExpectationManifestResponse ci =
        ciExpectationManifestService.manifest();
    OpsShardReadinessRouteCleanupMaintenanceFailClosedPolicyResponse policy =
        failClosedPolicyService.report();
    OpsShardReadinessRouteCleanupMaintenanceArchiveDigestLedgerResponse ledger =
        archiveDigestLedgerService.ledger();
    List<OpsShardReadinessRouteCleanupMaintenanceOperatorReviewPacketResponse.ReviewSection>
        sections =
            List.of(
                section(
                    "upkeep-catalog",
                    catalog.profile(),
                    catalog.endpoint(),
                    catalog.itemCount(),
                    catalog.status()),
                section(
                    "consumer-handoff-matrix",
                    matrix.profile(),
                    matrix.endpoint(),
                    matrix.matrixEntryCount(),
                    matrix.status()),
                section(
                    "ci-expectation-manifest",
                    ci.profile(),
                    ci.endpoint(),
                    ci.expectationCount(),
                    ci.status()),
                section(
                    "fail-closed-policy",
                    policy.profile(),
                    policy.endpoint(),
                    policy.policyCount(),
                    policy.status()),
                section(
                    "archive-digest-ledger",
                    ledger.profile(),
                    ledger.endpoint(),
                    ledger.ledgerEntryCount(),
                    ledger.status()));
    List<String> checks =
        List.of(
            "review-section-count-" + sections.size(),
            "review-packet-consumes-typed-maintenance-services",
            "review-packet-keeps-runtime-boundaries-closed",
            "review-packet-includes-ci-and-digest-evidence",
            "operator-review-packet-remains-read-only");
    return new OpsShardReadinessRouteCleanupMaintenanceOperatorReviewPacketResponse(
        "advanced-order-platform",
        "Java v501",
        true,
        false,
        ENDPOINT,
        PROFILE,
        sections.size(),
        catalog.itemCount(),
        matrix.matrixEntryCount(),
        ci.expectationCount(),
        policy.policyCount(),
        ledger.ledgerEntryCount(),
        sections,
        checks,
        status(sections));
  }

  private OpsShardReadinessRouteCleanupMaintenanceOperatorReviewPacketResponse.ReviewSection
      section(
          String name, String sourceProfile, String sourceEndpoint, int itemCount, String status) {
    return new OpsShardReadinessRouteCleanupMaintenanceOperatorReviewPacketResponse.ReviewSection(
        name, sourceProfile, sourceEndpoint, itemCount, status);
  }

  private String status(
      List<OpsShardReadinessRouteCleanupMaintenanceOperatorReviewPacketResponse.ReviewSection>
          sections) {
    boolean passed =
        sections.size() == 5
            && sections.stream().allMatch(section -> section.itemCount() > 0)
            && sections.stream().allMatch(section -> "passed".equals(section.status()));
    return passed ? "passed" : "blocked";
  }
}
