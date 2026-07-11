package com.codexdemo.orderplatform.ops.maintenance.readonlyevidence;

import com.codexdemo.orderplatform.ops.OpsShardReadinessEchoService;
import com.codexdemo.orderplatform.ops.OpsShardReadinessEvidenceHandoffService;
import com.codexdemo.orderplatform.ops.OpsShardReadinessEvidenceIndexService;
import com.codexdemo.orderplatform.ops.OpsShardReadinessEvidenceVerificationService;
import com.codexdemo.orderplatform.ops.OpsShardReadinessHardeningService;
import com.codexdemo.orderplatform.ops.OpsShardReadinessService;
import com.codexdemo.orderplatform.ops.maintenance.runtimeexecution.OpsShardReadinessRuntimeExecutionTestSupport;
import java.util.List;

public final class OpsShardReadinessReadOnlyEvidenceTestSupport {

  private OpsShardReadinessReadOnlyEvidenceTestSupport() {}

  public static OpsShardReadinessReadOnlyEvidenceCatalogService catalogService() {
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

  public static OpsShardReadinessReadOnlyEvidenceCatalogHandoffService handoffService() {
    return new OpsShardReadinessReadOnlyEvidenceCatalogHandoffService(catalogService());
  }

  public static OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationService
      handoffVerificationService() {
    OpsShardReadinessReadOnlyEvidenceCatalogService catalogService = catalogService();
    return new OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationService(
        catalogService, new OpsShardReadinessReadOnlyEvidenceCatalogHandoffService(catalogService));
  }

  public static OpsShardReadinessReadOnlyEndpointRegistryIntegrityService integrityService() {
    return new OpsShardReadinessReadOnlyEndpointRegistryIntegrityService();
  }

  public static List<String> v175LiveEndpoints() {
    return OpsShardReadinessReadOnlyEvidenceCatalogSnapshot.v175LiveEndpoints();
  }

  public static List<String> v175FixtureEndpoints() {
    return OpsShardReadinessReadOnlyEvidenceCatalogSnapshot.v175FixtureEndpoints();
  }

  public static List<String> v179LiveEndpoints() {
    return OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationSnapshot.v179LiveEndpoints();
  }

  public static List<String> v179FixtureEndpoints() {
    return OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationSnapshot
        .v179FixtureEndpoints();
  }

  public static List<String> v184LiveEndpoints() {
    return OpsShardReadinessReadOnlyEndpointRegistryIntegritySnapshot.v184LiveEndpoints();
  }

  public static List<String> v184FixtureEndpoints() {
    return OpsShardReadinessReadOnlyEndpointRegistryIntegritySnapshot.v184FixtureEndpoints();
  }
}
