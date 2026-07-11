package com.codexdemo.orderplatform.ops.maintenance.sandboxconnection;

import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalRehearsalResponse;
import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierWarningCatalog {

  private OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierWarningCatalog() {}

  static List<OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse.WarningEcho>
      warnings(ReleaseApprovalRehearsalResponse rehearsal) {
    var warnings =
        new ArrayList<
            OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse.WarningEcho>();
    rehearsal
        .requestContext()
        .contextWarnings()
        .forEach(warning -> warnings.add(warning("requestContext", warning)));
    rehearsal
        .managedAuditSandboxConnectionPreconditionReceipt()
        .receiptWarnings()
        .forEach(warning -> warnings.add(warning("preconditionReceipt", warning)));
    return List.copyOf(warnings);
  }

  private static OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse
          .WarningEcho
      warning(String source, String warning) {
    return new OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse.WarningEcho(
        source, warning, true);
  }
}
