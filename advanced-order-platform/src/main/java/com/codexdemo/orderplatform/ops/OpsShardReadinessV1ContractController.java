package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessService;
import com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1ContractAlignmentHandoffResponse;
import com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1ContractAlignmentHandoffService;
import com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1ContractAlignmentResponse;
import com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1ContractAlignmentService;
import com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1ContractConsumerEvidenceDigestResponse;
import com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1ContractConsumerEvidenceDigestService;
import com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1ContractConsumerHandoffBundleResponse;
import com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1ContractConsumerHandoffBundleService;
import com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1ContractConsumerProbePlanResponse;
import com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1ContractConsumerProbePlanService;
import com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1ContractConsumerReadinessHandoffResponse;
import com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1ContractConsumerReadinessHandoffService;
import com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1ContractConsumerVerificationChecklistResponse;
import com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1ContractConsumerVerificationChecklistService;
import com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1ContractEndpointCatalogResponse;
import com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1ContractEndpointCatalogService;
import com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1ContractEvidencePacketResponse;
import com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1ContractEvidencePacketService;
import com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1ContractHandoffManifestResponse;
import com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1ContractHandoffManifestService;
import com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1ContractOperatorChecklistResponse;
import com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1ContractOperatorChecklistService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessService.BASE_PATH)
public class OpsShardReadinessV1ContractController {

  private final OpsShardReadinessV1ContractAlignmentService alignmentService;

  private final OpsShardReadinessV1ContractAlignmentHandoffService alignmentHandoffService;

  private final OpsShardReadinessV1ContractEvidencePacketService evidencePacketService;

  private final OpsShardReadinessV1ContractOperatorChecklistService operatorChecklistService;

  private final OpsShardReadinessV1ContractHandoffManifestService handoffManifestService;

  private final OpsShardReadinessV1ContractConsumerProbePlanService consumerProbePlanService;

  private final OpsShardReadinessV1ContractEndpointCatalogService endpointCatalogService;

  private final OpsShardReadinessV1ContractConsumerHandoffBundleService
      consumerHandoffBundleService;

  private final OpsShardReadinessV1ContractConsumerVerificationChecklistService
      consumerVerificationChecklistService;

  private final OpsShardReadinessV1ContractConsumerEvidenceDigestService
      consumerEvidenceDigestService;

  private final OpsShardReadinessV1ContractConsumerReadinessHandoffService
      consumerReadinessHandoffService;

  public OpsShardReadinessV1ContractController(
      OpsShardReadinessV1ContractAlignmentService alignmentService,
      OpsShardReadinessV1ContractAlignmentHandoffService alignmentHandoffService,
      OpsShardReadinessV1ContractEvidencePacketService evidencePacketService,
      OpsShardReadinessV1ContractOperatorChecklistService operatorChecklistService,
      OpsShardReadinessV1ContractHandoffManifestService handoffManifestService,
      OpsShardReadinessV1ContractConsumerProbePlanService consumerProbePlanService,
      OpsShardReadinessV1ContractEndpointCatalogService endpointCatalogService,
      OpsShardReadinessV1ContractConsumerHandoffBundleService consumerHandoffBundleService,
      OpsShardReadinessV1ContractConsumerVerificationChecklistService
          consumerVerificationChecklistService,
      OpsShardReadinessV1ContractConsumerEvidenceDigestService consumerEvidenceDigestService,
      OpsShardReadinessV1ContractConsumerReadinessHandoffService consumerReadinessHandoffService) {
    this.alignmentService = alignmentService;
    this.alignmentHandoffService = alignmentHandoffService;
    this.evidencePacketService = evidencePacketService;
    this.operatorChecklistService = operatorChecklistService;
    this.handoffManifestService = handoffManifestService;
    this.consumerProbePlanService = consumerProbePlanService;
    this.endpointCatalogService = endpointCatalogService;
    this.consumerHandoffBundleService = consumerHandoffBundleService;
    this.consumerVerificationChecklistService = consumerVerificationChecklistService;
    this.consumerEvidenceDigestService = consumerEvidenceDigestService;
    this.consumerReadinessHandoffService = consumerReadinessHandoffService;
  }

  @GetMapping(OpsShardReadinessRoutePaths.V1_CONTRACT_ALIGNMENT)
  public OpsShardReadinessV1ContractAlignmentResponse alignment() {
    return alignmentService.alignment();
  }

  @GetMapping(OpsShardReadinessRoutePaths.V1_CONTRACT_ALIGNMENT_HANDOFF)
  public OpsShardReadinessV1ContractAlignmentHandoffResponse alignmentHandoff() {
    return alignmentHandoffService.handoff();
  }

  @GetMapping(OpsShardReadinessRoutePaths.V1_CONTRACT_EVIDENCE_PACKET)
  public OpsShardReadinessV1ContractEvidencePacketResponse evidencePacket() {
    return evidencePacketService.packet();
  }

  @GetMapping(OpsShardReadinessRoutePaths.V1_CONTRACT_OPERATOR_CHECKLIST)
  public OpsShardReadinessV1ContractOperatorChecklistResponse operatorChecklist() {
    return operatorChecklistService.checklist();
  }

  @GetMapping(OpsShardReadinessRoutePaths.V1_CONTRACT_HANDOFF_MANIFEST)
  public OpsShardReadinessV1ContractHandoffManifestResponse handoffManifest() {
    return handoffManifestService.manifest();
  }

  @GetMapping(OpsShardReadinessRoutePaths.V1_CONTRACT_CONSUMER_PROBE_PLAN)
  public OpsShardReadinessV1ContractConsumerProbePlanResponse consumerProbePlan() {
    return consumerProbePlanService.probePlan();
  }

  @GetMapping(OpsShardReadinessRoutePaths.V1_CONTRACT_ENDPOINT_CATALOG)
  public OpsShardReadinessV1ContractEndpointCatalogResponse endpointCatalog() {
    return endpointCatalogService.catalog();
  }

  @GetMapping(OpsShardReadinessRoutePaths.V1_CONTRACT_CONSUMER_HANDOFF_BUNDLE)
  public OpsShardReadinessV1ContractConsumerHandoffBundleResponse consumerHandoffBundle() {
    return consumerHandoffBundleService.bundle();
  }

  @GetMapping(OpsShardReadinessRoutePaths.V1_CONTRACT_CONSUMER_VERIFICATION_CHECKLIST)
  public OpsShardReadinessV1ContractConsumerVerificationChecklistResponse
      consumerVerificationChecklist() {
    return consumerVerificationChecklistService.checklist();
  }

  @GetMapping(OpsShardReadinessRoutePaths.V1_CONTRACT_CONSUMER_EVIDENCE_DIGEST)
  public OpsShardReadinessV1ContractConsumerEvidenceDigestResponse consumerEvidenceDigest() {
    return consumerEvidenceDigestService.digest();
  }

  @GetMapping(OpsShardReadinessRoutePaths.V1_CONTRACT_CONSUMER_READINESS_HANDOFF)
  public OpsShardReadinessV1ContractConsumerReadinessHandoffResponse consumerReadinessHandoff() {
    return consumerReadinessHandoffService.handoff();
  }
}
