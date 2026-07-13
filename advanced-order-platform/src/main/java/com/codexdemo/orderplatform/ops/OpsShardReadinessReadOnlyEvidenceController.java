package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessService;
import com.codexdemo.orderplatform.ops.maintenance.readonlyevidence.OpsShardReadinessReadOnlyEndpointRegistryIntegrityResponse;
import com.codexdemo.orderplatform.ops.maintenance.readonlyevidence.OpsShardReadinessReadOnlyEndpointRegistryIntegrityService;
import com.codexdemo.orderplatform.ops.maintenance.readonlyevidence.OpsShardReadinessReadOnlyEvidenceCatalogHandoffResponse;
import com.codexdemo.orderplatform.ops.maintenance.readonlyevidence.OpsShardReadinessReadOnlyEvidenceCatalogHandoffService;
import com.codexdemo.orderplatform.ops.maintenance.readonlyevidence.OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationResponse;
import com.codexdemo.orderplatform.ops.maintenance.readonlyevidence.OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationService;
import com.codexdemo.orderplatform.ops.maintenance.readonlyevidence.OpsShardReadinessReadOnlyEvidenceCatalogResponse;
import com.codexdemo.orderplatform.ops.maintenance.readonlyevidence.OpsShardReadinessReadOnlyEvidenceCatalogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessService.BASE_PATH)
public class OpsShardReadinessReadOnlyEvidenceController {

  private final OpsShardReadinessReadOnlyEvidenceCatalogService readOnlyEvidenceCatalogService;

  private final OpsShardReadinessReadOnlyEvidenceCatalogHandoffService
      readOnlyEvidenceCatalogHandoffService;

  private final OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationService
      readOnlyEvidenceCatalogHandoffVerificationService;

  private final OpsShardReadinessReadOnlyEndpointRegistryIntegrityService
      readOnlyEndpointRegistryIntegrityService;

  public OpsShardReadinessReadOnlyEvidenceController(
      OpsShardReadinessReadOnlyEvidenceCatalogService readOnlyEvidenceCatalogService,
      OpsShardReadinessReadOnlyEvidenceCatalogHandoffService readOnlyEvidenceCatalogHandoffService,
      OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationService
          readOnlyEvidenceCatalogHandoffVerificationService,
      OpsShardReadinessReadOnlyEndpointRegistryIntegrityService
          readOnlyEndpointRegistryIntegrityService) {
    this.readOnlyEvidenceCatalogService = readOnlyEvidenceCatalogService;
    this.readOnlyEvidenceCatalogHandoffService = readOnlyEvidenceCatalogHandoffService;
    this.readOnlyEvidenceCatalogHandoffVerificationService =
        readOnlyEvidenceCatalogHandoffVerificationService;
    this.readOnlyEndpointRegistryIntegrityService = readOnlyEndpointRegistryIntegrityService;
  }

  @GetMapping(OpsShardReadinessRoutePaths.READ_ONLY_EVIDENCE_CATALOG)
  public OpsShardReadinessReadOnlyEvidenceCatalogResponse readOnlyEvidenceCatalog() {
    return readOnlyEvidenceCatalogService.catalog();
  }

  @GetMapping(OpsShardReadinessRoutePaths.READ_ONLY_EVIDENCE_CATALOG_HANDOFF)
  public OpsShardReadinessReadOnlyEvidenceCatalogHandoffResponse readOnlyEvidenceCatalogHandoff() {
    return readOnlyEvidenceCatalogHandoffService.handoff();
  }

  @GetMapping(OpsShardReadinessRoutePaths.READ_ONLY_EVIDENCE_CATALOG_HANDOFF_VERIFICATION)
  public OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationResponse
      readOnlyEvidenceCatalogHandoffVerification() {
    return readOnlyEvidenceCatalogHandoffVerificationService.verification();
  }

  @GetMapping(OpsShardReadinessRoutePaths.READ_ONLY_ENDPOINT_REGISTRY_INTEGRITY)
  public OpsShardReadinessReadOnlyEndpointRegistryIntegrityResponse
      readOnlyEndpointRegistryIntegrity() {
    return readOnlyEndpointRegistryIntegrityService.integrity();
  }
}
