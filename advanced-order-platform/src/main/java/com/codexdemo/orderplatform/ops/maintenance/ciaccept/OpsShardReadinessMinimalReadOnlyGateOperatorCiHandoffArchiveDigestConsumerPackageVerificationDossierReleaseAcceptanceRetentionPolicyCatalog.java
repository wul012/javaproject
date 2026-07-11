package com.codexdemo.orderplatform.ops.maintenance.ciaccept;

import com.codexdemo.orderplatform.ops.maintenance.operatorcidossier.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse;
import java.util.List;

final
class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRetentionPolicyCatalog {

  private
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRetentionPolicyCatalog() {}

  static List<
          OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse
              .RetentionPolicy>
      policies(
          OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
              source) {
    boolean ready = "passed".equals(source.status());
    return List.of(
        policy("source-dossier-snapshot", source.version(), "release+2-cycles", ready),
        policy(
            "provenance-chain",
            "provenance=" + source.passedProvenanceEntryCount(),
            "release+2-cycles",
            ready),
        policy(
            "section-digests",
            "section-digests=" + source.passedSectionDigestCount(),
            "release+2-cycles",
            ready),
        policy(
            "ci-replay-lanes",
            "ci-lanes=" + source.readOnlyCiLaneCount(),
            "release+1-cycle",
            ready),
        policy(
            "boundary-controls",
            "boundaries=" + source.lockedBoundaryAuditCount(),
            "release+2-cycles",
            ready));
  }

  private static
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse
          .RetentionPolicy
      policy(String name, String sourceEvidence, String retentionWindow, boolean ready) {
    return new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse
        .RetentionPolicy(
        name, sourceEvidence, retentionWindow, ready, ready ? "passed" : "blocked");
  }
}
