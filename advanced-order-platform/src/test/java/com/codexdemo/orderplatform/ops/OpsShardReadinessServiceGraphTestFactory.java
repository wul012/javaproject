package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.runtimeexecution.OpsShardReadinessRuntimeExecutionPassEvidenceCloseoutService;
import com.codexdemo.orderplatform.ops.maintenance.runtimeexecution.OpsShardReadinessRuntimeExecutionTestSupport;

final class OpsShardReadinessServiceGraphTestFactory {

  private OpsShardReadinessServiceGraphTestFactory() {}

  static OpsShardReadinessReadOnlyEvidenceCatalogService readOnlyEvidenceCatalogService() {
    OpsShardReadinessEvidenceIndexService indexService =
        new OpsShardReadinessEvidenceIndexService();
    OpsShardReadinessEvidenceVerificationService verificationService =
        new OpsShardReadinessEvidenceVerificationService(indexService);
    OpsShardReadinessEvidenceHandoffService handoffService =
        new OpsShardReadinessEvidenceHandoffService(indexService, verificationService);
    OpsShardReadinessEchoService echoService =
        new OpsShardReadinessEchoService(
            new OpsShardReadinessService(),
            new OpsShardReadinessHardeningService(),
            indexService,
            handoffService);
    return new OpsShardReadinessReadOnlyEvidenceCatalogService(
        echoService, OpsShardReadinessRuntimeExecutionTestSupport.passEvidenceCloseoutService());
  }

  static OpsShardReadinessReadOnlyEvidenceCatalogHandoffService
      readOnlyEvidenceCatalogHandoffService() {
    return new OpsShardReadinessReadOnlyEvidenceCatalogHandoffService(
        readOnlyEvidenceCatalogService());
  }

  static OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationService
      readOnlyEvidenceCatalogHandoffVerificationService() {
    OpsShardReadinessReadOnlyEvidenceCatalogService catalogService =
        readOnlyEvidenceCatalogService();
    return new OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationService(
        catalogService, new OpsShardReadinessReadOnlyEvidenceCatalogHandoffService(catalogService));
  }

  static OpsShardReadinessRuntimeExecutionPassEvidenceCloseoutService
      passEvidenceCloseoutService() {
    return OpsShardReadinessRuntimeExecutionTestSupport.passEvidenceCloseoutService();
  }
}
