package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessV1ContractController {

    private final OpsShardReadinessV1ContractAlignmentService alignmentService;

    private final OpsShardReadinessV1ContractAlignmentHandoffService alignmentHandoffService;

    private final OpsShardReadinessV1ContractEvidencePacketService evidencePacketService;

    private final OpsShardReadinessV1ContractOperatorChecklistService operatorChecklistService;

    private final OpsShardReadinessV1ContractHandoffManifestService handoffManifestService;

    private final OpsShardReadinessV1ContractConsumerProbePlanService consumerProbePlanService;

    private final OpsShardReadinessV1ContractEndpointCatalogService endpointCatalogService;

    private final OpsShardReadinessV1ContractConsumerHandoffBundleService consumerHandoffBundleService;

    public OpsShardReadinessV1ContractController(
            OpsShardReadinessV1ContractAlignmentService alignmentService,
            OpsShardReadinessV1ContractAlignmentHandoffService alignmentHandoffService,
            OpsShardReadinessV1ContractEvidencePacketService evidencePacketService,
            OpsShardReadinessV1ContractOperatorChecklistService operatorChecklistService,
            OpsShardReadinessV1ContractHandoffManifestService handoffManifestService,
            OpsShardReadinessV1ContractConsumerProbePlanService consumerProbePlanService,
            OpsShardReadinessV1ContractEndpointCatalogService endpointCatalogService,
            OpsShardReadinessV1ContractConsumerHandoffBundleService consumerHandoffBundleService
    ) {
        this.alignmentService = alignmentService;
        this.alignmentHandoffService = alignmentHandoffService;
        this.evidencePacketService = evidencePacketService;
        this.operatorChecklistService = operatorChecklistService;
        this.handoffManifestService = handoffManifestService;
        this.consumerProbePlanService = consumerProbePlanService;
        this.endpointCatalogService = endpointCatalogService;
        this.consumerHandoffBundleService = consumerHandoffBundleService;
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
}
