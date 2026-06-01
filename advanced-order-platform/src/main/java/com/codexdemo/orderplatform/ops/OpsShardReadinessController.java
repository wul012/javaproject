package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ops")
public class OpsShardReadinessController {

    private final OpsShardReadinessService opsShardReadinessService;

    private final OpsShardReadinessHardeningService opsShardReadinessHardeningService;

    private final OpsShardReadinessEchoService opsShardReadinessEchoService;

    private final OpsShardReadinessV1ContractAlignmentService v1ContractAlignmentService;

    private final OpsShardReadinessV1ContractAlignmentHandoffService v1ContractAlignmentHandoffService;

    private final OpsShardReadinessV1ContractEvidencePacketService v1ContractEvidencePacketService;

    private final OpsShardReadinessV1ContractOperatorChecklistService v1ContractOperatorChecklistService;

    public OpsShardReadinessController(
            OpsShardReadinessService opsShardReadinessService,
            OpsShardReadinessHardeningService opsShardReadinessHardeningService,
            OpsShardReadinessEchoService opsShardReadinessEchoService,
            OpsShardReadinessV1ContractAlignmentService v1ContractAlignmentService,
            OpsShardReadinessV1ContractAlignmentHandoffService v1ContractAlignmentHandoffService,
            OpsShardReadinessV1ContractEvidencePacketService v1ContractEvidencePacketService,
            OpsShardReadinessV1ContractOperatorChecklistService v1ContractOperatorChecklistService
    ) {
        this.opsShardReadinessService = opsShardReadinessService;
        this.opsShardReadinessHardeningService = opsShardReadinessHardeningService;
        this.opsShardReadinessEchoService = opsShardReadinessEchoService;
        this.v1ContractAlignmentService = v1ContractAlignmentService;
        this.v1ContractAlignmentHandoffService = v1ContractAlignmentHandoffService;
        this.v1ContractEvidencePacketService = v1ContractEvidencePacketService;
        this.v1ContractOperatorChecklistService = v1ContractOperatorChecklistService;
    }

    @GetMapping("/shard-readiness")
    public OpsShardReadinessResponse shardReadiness() {
        return opsShardReadinessService.readiness();
    }

    @GetMapping("/shard-readiness/hardening")
    public OpsShardReadinessHardeningResponse shardReadinessHardening() {
        return opsShardReadinessHardeningService.hardening();
    }

    @GetMapping("/shard-readiness/echo")
    public OpsShardReadinessEchoResponse shardReadinessEcho() {
        return opsShardReadinessEchoService.echo();
    }

    @GetMapping("/shard-readiness/v1-contract-alignment")
    public OpsShardReadinessV1ContractAlignmentResponse shardReadinessV1ContractAlignment() {
        return v1ContractAlignmentService.alignment();
    }

    @GetMapping("/shard-readiness/v1-contract-alignment-handoff")
    public OpsShardReadinessV1ContractAlignmentHandoffResponse shardReadinessV1ContractAlignmentHandoff() {
        return v1ContractAlignmentHandoffService.handoff();
    }

    @GetMapping("/shard-readiness/v1-contract-evidence-packet")
    public OpsShardReadinessV1ContractEvidencePacketResponse shardReadinessV1ContractEvidencePacket() {
        return v1ContractEvidencePacketService.packet();
    }

    @GetMapping("/shard-readiness/v1-contract-operator-checklist")
    public OpsShardReadinessV1ContractOperatorChecklistResponse shardReadinessV1ContractOperatorChecklist() {
        return v1ContractOperatorChecklistService.checklist();
    }

}
