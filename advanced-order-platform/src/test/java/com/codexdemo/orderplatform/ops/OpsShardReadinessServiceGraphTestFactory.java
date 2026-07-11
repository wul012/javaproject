package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.readonlyevidence.OpsShardReadinessReadOnlyEvidenceCatalogHandoffService;
import com.codexdemo.orderplatform.ops.maintenance.readonlyevidence.OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationService;
import com.codexdemo.orderplatform.ops.maintenance.readonlyevidence.OpsShardReadinessReadOnlyEvidenceCatalogService;
import com.codexdemo.orderplatform.ops.maintenance.readonlyevidence.OpsShardReadinessReadOnlyEvidenceTestSupport;
import com.codexdemo.orderplatform.ops.maintenance.runtimeexecution.OpsShardReadinessRuntimeExecutionPassEvidenceCloseoutService;
import com.codexdemo.orderplatform.ops.maintenance.runtimeexecution.OpsShardReadinessRuntimeExecutionTestSupport;

final class OpsShardReadinessServiceGraphTestFactory {

  private OpsShardReadinessServiceGraphTestFactory() {}

  static OpsShardReadinessReadOnlyEvidenceCatalogService readOnlyEvidenceCatalogService() {
    return OpsShardReadinessReadOnlyEvidenceTestSupport.catalogService();
  }

  static OpsShardReadinessReadOnlyEvidenceCatalogHandoffService
      readOnlyEvidenceCatalogHandoffService() {
    return OpsShardReadinessReadOnlyEvidenceTestSupport.handoffService();
  }

  static OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationService
      readOnlyEvidenceCatalogHandoffVerificationService() {
    return OpsShardReadinessReadOnlyEvidenceTestSupport.handoffVerificationService();
  }

  static OpsShardReadinessRuntimeExecutionPassEvidenceCloseoutService
      passEvidenceCloseoutService() {
    return OpsShardReadinessRuntimeExecutionTestSupport.passEvidenceCloseoutService();
  }
}
