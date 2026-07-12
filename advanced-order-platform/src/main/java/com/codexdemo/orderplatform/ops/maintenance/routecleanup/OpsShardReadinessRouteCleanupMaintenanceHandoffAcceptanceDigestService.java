package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupMaintenanceHandoffAcceptanceDigestService {

  static final String ENDPOINT =
      RouteCleanupRoutes.BASE_PATH + RouteCleanupRoutes.MAINTENANCE_HANDOFF_ACCEPTANCE_DIGEST;
  static final String PROFILE =
      "java-shard-readiness-route-cleanup-maintenance-handoff-acceptance-digest.v1";

  private final OpsShardReadinessRouteCleanupMaintenanceOwnershipRegisterService
      ownershipRegisterService;
  private final OpsShardReadinessRouteCleanupMaintenanceRiskLedgerService riskLedgerService;
  private final OpsShardReadinessRouteCleanupMaintenanceFreshnessWindowService
      freshnessWindowService;

  public OpsShardReadinessRouteCleanupMaintenanceHandoffAcceptanceDigestService(
      OpsShardReadinessRouteCleanupMaintenanceOwnershipRegisterService ownershipRegisterService,
      OpsShardReadinessRouteCleanupMaintenanceRiskLedgerService riskLedgerService,
      OpsShardReadinessRouteCleanupMaintenanceFreshnessWindowService freshnessWindowService) {
    this.ownershipRegisterService = ownershipRegisterService;
    this.riskLedgerService = riskLedgerService;
    this.freshnessWindowService = freshnessWindowService;
  }

  @Transactional(readOnly = true)
  public OpsShardReadinessRouteCleanupMaintenanceHandoffAcceptanceDigestResponse digest() {
    OpsShardReadinessRouteCleanupMaintenanceOwnershipRegisterResponse ownership =
        ownershipRegisterService.register();
    OpsShardReadinessRouteCleanupMaintenanceRiskLedgerResponse risks = riskLedgerService.ledger();
    OpsShardReadinessRouteCleanupMaintenanceFreshnessWindowResponse freshness =
        freshnessWindowService.window();
    List<OpsShardReadinessRouteCleanupMaintenanceHandoffAcceptanceDigestResponse.AcceptanceSection>
        sections =
            List.of(
                section(
                    "owner-coverage",
                    ownership.endpoint(),
                    "operator-handoff-reviewer",
                    "distinct-owner-count-" + ownership.distinctOwnerCount(),
                    ownership.status()),
                section(
                    "risk-closure",
                    risks.endpoint(),
                    "release-reviewer",
                    "mitigated-risk-count-" + risks.mitigatedRiskCount(),
                    risks.status()),
                section(
                    "evidence-freshness",
                    freshness.endpoint(),
                    "archive-reviewer",
                    "stale-evidence-count-" + freshness.staleEvidenceCount(),
                    freshness.status()),
                section(
                    "runtime-boundary",
                    risks.endpoint(),
                    "runtime-boundary-reviewer",
                    "executionAllowed-" + risks.executionAllowed(),
                    risks.status()),
                section(
                    "handoff-readiness",
                    ownership.endpoint(),
                    "catalog-maintainer",
                    "owner-entry-count-" + ownership.ownerEntryCount(),
                    ownership.status()));
    int accepted =
        (int) sections.stream().filter(section -> "passed".equals(section.status())).count();
    int blocked = sections.size() - accepted;
    List<String> checks =
        List.of(
            "acceptance-section-count-" + sections.size(),
            "accepted-section-count-" + accepted,
            "blocked-section-count-" + blocked,
            "handoff-digest-derived-from-read-only-sources",
            "handoff-acceptance-digest-remains-read-only");
    return new OpsShardReadinessRouteCleanupMaintenanceHandoffAcceptanceDigestResponse(
        "advanced-order-platform",
        "Java v522",
        true,
        false,
        ENDPOINT,
        PROFILE,
        sections.size(),
        accepted,
        blocked,
        sections,
        checks,
        blocked == 0 ? "passed" : "blocked");
  }

  private OpsShardReadinessRouteCleanupMaintenanceHandoffAcceptanceDigestResponse.AcceptanceSection
      section(String name, String sourceEndpoint, String owner, String evidence, String status) {
    return new OpsShardReadinessRouteCleanupMaintenanceHandoffAcceptanceDigestResponse
        .AcceptanceSection(name, sourceEndpoint, owner, evidence, status);
  }
}
