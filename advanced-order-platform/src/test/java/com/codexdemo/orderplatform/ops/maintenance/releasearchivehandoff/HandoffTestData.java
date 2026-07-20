package com.codexdemo.orderplatform.ops.maintenance.releasearchivehandoff;

import com.codexdemo.orderplatform.ops.maintenance.ciarc.ArchiveTestData;

public final class HandoffTestData {

  private HandoffTestData() {}

  public static OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffService service() {
    return new OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffService(
        ArchiveTestData.service());
  }

  public static OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse registry() {
    return service().registry();
  }
}
