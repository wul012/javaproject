package com.codexdemo.orderplatform.ops.maintenance.operatorcidossier;

import java.util.List;

final
class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRendererSupport {

  private
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRendererSupport() {}

  static
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
          .MarkdownSection
      section(String heading, List<String> lines) {
    return new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
        .MarkdownSection(heading, List.copyOf(lines));
  }

  static String statusLine(String name, String status) {
    return name + " -> " + status;
  }
}
