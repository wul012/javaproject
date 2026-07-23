package com.codexdemo.orderplatform.ops.maintenance.operatorcidossier;

import static com.codexdemo.orderplatform.ops.maintenance.evidencecore.EvidenceCounts.matching;

import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorciconsumerpackage.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse;
import com.codexdemo.orderplatform.ops.maintenance.operatorcidossier.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse.AcceptanceGate;
import com.codexdemo.orderplatform.ops.maintenance.operatorcidossier.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse.AudienceRoute;
import com.codexdemo.orderplatform.ops.maintenance.operatorcidossier.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse.BoundaryAudit;
import com.codexdemo.orderplatform.ops.maintenance.operatorcidossier.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse.CiLane;
import com.codexdemo.orderplatform.ops.maintenance.operatorcidossier.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse.HandoffReceipt;
import com.codexdemo.orderplatform.ops.maintenance.operatorcidossier.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse.MarkdownSection;
import com.codexdemo.orderplatform.ops.maintenance.operatorcidossier.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse.ReleaseChecklistItem;
import java.util.List;

final class DossierSupport {

  static final String PROJECT = "advanced-order-platform";
  static final String SOURCE_PLAN = "Node v367";
  static final String ARCHIVE_PLAN = "Node v368";
  static final String HANDOFF_PLAN = "Node v369";
  static final String DOSSIER_STATE =
      "minimal-read-only-gate-operator-ci-handoff-archive-digest-consumer-package-verification-dossier-ready";
  static final int MARKDOWN_COUNT = 10;

  private DossierSupport() {}

  static
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
      response(
          String version,
          String endpoint,
          String profile,
          OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
              source,
          DossierCatalog.Evidence evidence,
          List<MarkdownSection> markdownSections) {
    var markdown = List.copyOf(markdownSections);
    var summary = Summary.from(evidence);
    var checks = checks(source, evidence, summary, markdown.size());
    String status = passed(source, evidence, summary, markdown.size()) ? "passed" : "blocked";

    return new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse(
        PROJECT,
        version,
        true,
        false,
        false,
        false,
        false,
        false,
        false,
        endpoint,
        profile,
        SOURCE_PLAN,
        ARCHIVE_PLAN,
        HANDOFF_PLAN,
        source.version(),
        source.endpoint(),
        source.consumerPackageState(),
        DOSSIER_STATE,
        evidence.sourcePackages().size(),
        evidence.provenance().size(),
        summary.provenance(),
        evidence.sectionDigests().size(),
        summary.digests(),
        evidence.audienceRoutes().size(),
        summary.audiences(),
        evidence.ciLanes().size(),
        summary.ciLanes(),
        evidence.acceptanceGates().size(),
        summary.gates(),
        evidence.boundaryAudits().size(),
        summary.audits(),
        evidence.releaseChecklist().size(),
        summary.checklist(),
        evidence.handoffReceipts().size(),
        summary.receipts(),
        evidence.scorecard().size(),
        summary.scorecard(),
        markdown.size(),
        evidence.sourcePackages(),
        evidence.provenance(),
        evidence.sectionDigests(),
        evidence.audienceRoutes(),
        evidence.ciLanes(),
        evidence.acceptanceGates(),
        evidence.boundaryAudits(),
        evidence.releaseChecklist(),
        evidence.handoffReceipts(),
        evidence.scorecard(),
        markdown,
        checks,
        status);
  }

  private static boolean passed(
      OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
          source,
      DossierCatalog.Evidence evidence,
      Summary summary,
      int markdownCount) {
    return "passed".equals(source.status())
        && source.readOnly()
        && !source.executionAllowed()
        && !source.startsJavaService()
        && !source.startsMiniKvService()
        && !source.readsCredentialValue()
        && !source.resolvesRawEndpointUrl()
        && !source.managedAuditHttpAllowed()
        && source.markdownSectionCount() == DossierCatalog.DIGEST_COUNT
        && evidence.sourcePackages().size() == DossierCatalog.SOURCE_COUNT
        && evidence.provenance().size() == DossierCatalog.PROVENANCE_COUNT
        && summary.provenance() == evidence.provenance().size()
        && evidence.sectionDigests().size() == DossierCatalog.DIGEST_COUNT
        && summary.digests() == evidence.sectionDigests().size()
        && evidence.audienceRoutes().size() == DossierCatalog.AUDIENCE_COUNT
        && summary.audiences() == evidence.audienceRoutes().size()
        && evidence.ciLanes().size() == DossierCatalog.CI_COUNT
        && summary.ciLanes() == evidence.ciLanes().size()
        && evidence.acceptanceGates().size() == DossierCatalog.GATE_COUNT
        && summary.gates() == evidence.acceptanceGates().size()
        && evidence.boundaryAudits().size() == DossierCatalog.AUDIT_COUNT
        && summary.audits() == evidence.boundaryAudits().size()
        && evidence.releaseChecklist().size() == DossierCatalog.CHECKLIST_COUNT
        && summary.checklist() == evidence.releaseChecklist().size()
        && evidence.handoffReceipts().size() == DossierCatalog.RECEIPT_COUNT
        && summary.receipts() == evidence.handoffReceipts().size()
        && evidence.scorecard().size() == DossierCatalog.SCORECARD_COUNT
        && summary.scorecard() == evidence.scorecard().size()
        && markdownCount == MARKDOWN_COUNT;
  }

  private static List<String> checks(
      OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
          source,
      DossierCatalog.Evidence evidence,
      Summary summary,
      int markdownCount) {
    return List.of(
        "minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-source-plan-"
            + SOURCE_PLAN,
        "minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-required-archive-"
            + ARCHIVE_PLAN,
        "minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-operator-plan-"
            + HANDOFF_PLAN,
        "minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-source-version-"
            + source.version(),
        "minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-source-status-"
            + source.status(),
        "minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-source-markdown-count-"
            + source.markdownSectionCount(),
        "minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-source-package-count-"
            + evidence.sourcePackages().size(),
        "minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-provenance-count-"
            + evidence.provenance().size(),
        "minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-passed-provenance-count-"
            + summary.provenance(),
        "minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-section-digest-count-"
            + evidence.sectionDigests().size(),
        "minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-passed-section-digest-count-"
            + summary.digests(),
        "minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-audience-route-count-"
            + evidence.audienceRoutes().size(),
        "minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-ready-audience-route-count-"
            + summary.audiences(),
        "minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-ci-lane-count-"
            + evidence.ciLanes().size(),
        "minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-read-only-ci-lane-count-"
            + summary.ciLanes(),
        "minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-acceptance-gate-count-"
            + evidence.acceptanceGates().size(),
        "minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-passed-acceptance-gate-count-"
            + summary.gates(),
        "minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-boundary-audit-count-"
            + evidence.boundaryAudits().size(),
        "minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-locked-boundary-audit-count-"
            + summary.audits(),
        "minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-release-checklist-count-"
            + evidence.releaseChecklist().size(),
        "minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-ready-release-checklist-count-"
            + summary.checklist(),
        "minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-handoff-receipt-count-"
            + evidence.handoffReceipts().size(),
        "minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-ready-handoff-receipt-count-"
            + summary.receipts(),
        "minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-scorecard-count-"
            + evidence.scorecard().size(),
        "minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-passed-scorecard-count-"
            + summary.scorecard(),
        "minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-markdown-section-count-"
            + markdownCount,
        "minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-consumes-consumer-package",
        "minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-no-upstream-autostart",
        "minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-no-write-routing",
        "minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-no-secret-value",
        "minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-no-raw-endpoint-resolution",
        "minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-no-managed-audit-http",
        "minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-no-runtime-execution",
        "minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-no-deployment-rollback");
  }

  private record Summary(
      int provenance,
      int digests,
      int audiences,
      int ciLanes,
      int gates,
      int audits,
      int checklist,
      int receipts,
      int scorecard) {

    private static Summary from(DossierCatalog.Evidence evidence) {
      return new Summary(
          matching(evidence.provenance(), item -> "passed".equals(item.status())),
          matching(evidence.sectionDigests(), item -> "passed".equals(item.status())),
          matching(evidence.audienceRoutes(), AudienceRoute::ready),
          matching(evidence.ciLanes(), CiLane::readOnly),
          matching(evidence.acceptanceGates(), AcceptanceGate::passed),
          matching(evidence.boundaryAudits(), BoundaryAudit::locked),
          matching(evidence.releaseChecklist(), ReleaseChecklistItem::ready),
          matching(evidence.handoffReceipts(), HandoffReceipt::ready),
          matching(evidence.scorecard(), item -> "passed".equals(item.status())));
    }
  }
}
