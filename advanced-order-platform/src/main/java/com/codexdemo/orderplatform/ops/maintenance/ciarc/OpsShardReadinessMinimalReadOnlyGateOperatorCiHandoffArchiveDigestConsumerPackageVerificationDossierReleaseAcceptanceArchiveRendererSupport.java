package com.codexdemo.orderplatform.ops.maintenance.ciarc;

import java.util.List;

final
class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRendererSupport {

  private
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRendererSupport() {}

  static
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse
          .MarkdownSection
      section(String heading, List<String> lines) {
    return new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse
        .MarkdownSection(heading, List.copyOf(lines));
  }

  static String flag(String name, boolean value) {
    return name + "=" + value;
  }

  static String statusLine(String status) {
    return "status=" + status;
  }
}
