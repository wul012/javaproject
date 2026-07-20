package com.codexdemo.orderplatform.ops.maintenance.releaseacceptancepackage;

import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.OpsShardReadinessReleaseAcceptanceRoutePaths;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public
class OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexService {

  static final String RESPONSE_VERSION = "Java v1652";
  static final String ENDPOINT =
      OpsShardReadinessReleaseAcceptanceRoutePaths.BASE_PATH
          + OpsShardReadinessReleaseAcceptanceRoutePaths
              .RELEASE_ACCEPTANCE_ROUTE_PATH_SPLIT_SUSTAINMENT_ACCEPTANCE_PACKAGE_CLOSEOUT_ARCHIVE_INDEX;

  private final
  OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutReceiptService
      sourceService;

  public
  OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexService(
      OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutReceiptService
          sourceService) {
    this.sourceService = sourceService;
  }

  @Transactional(readOnly = true)
  public
  OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexResponse
      index() {
    var source = sourceService.receipt();
    var sourceSnapshots =
        OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexSourceCatalog
            .snapshots(source);
    var criteriaEchoes =
        OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexCriteriaCatalog
            .echoes(source);
    var archiveItems =
        OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexItemCatalog
            .items(source);
    var verificationGates =
        OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexVerificationCatalog
            .gates(source);
    var handoffNotes =
        OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexHandoffCatalog
            .notes(source);
    var markdownSections =
        ArchiveIndexRenderer.render(
            sourceSnapshots, criteriaEchoes, archiveItems, verificationGates, handoffNotes);
    return OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexSupport
        .response(
            RESPONSE_VERSION,
            ENDPOINT,
            source,
            sourceSnapshots,
            criteriaEchoes,
            archiveItems,
            verificationGates,
            handoffNotes,
            markdownSections);
  }
}
