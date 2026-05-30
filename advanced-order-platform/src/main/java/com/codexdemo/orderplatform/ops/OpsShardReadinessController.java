package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ops")
public class OpsShardReadinessController {

    private final OpsShardReadinessService opsShardReadinessService;

    private final OpsShardReadinessHardeningService opsShardReadinessHardeningService;

    private final OpsShardReadinessEvidenceIndexService opsShardReadinessEvidenceIndexService;

    private final OpsShardReadinessEvidenceVerificationService opsShardReadinessEvidenceVerificationService;

    private final OpsShardReadinessEvidenceHandoffService opsShardReadinessEvidenceHandoffService;

    private final OpsShardReadinessActiveShardPlanHandoffService opsShardReadinessActiveShardPlanHandoffService;

    private final OpsShardReadinessLiveReadGatePlanService opsShardReadinessLiveReadGatePlanService;

    private final OpsShardReadinessOperatorServiceLifecycleService opsShardReadinessOperatorServiceLifecycleService;

    private final OpsShardReadinessDeclaredOperatorLifecycleService opsShardReadinessDeclaredOperatorLifecycleService;

    private final OpsShardReadinessRuntimeExecutionArtifactCandidateService
            opsShardReadinessRuntimeExecutionArtifactCandidateService;

    public OpsShardReadinessController(
            OpsShardReadinessService opsShardReadinessService,
            OpsShardReadinessHardeningService opsShardReadinessHardeningService,
            OpsShardReadinessEvidenceIndexService opsShardReadinessEvidenceIndexService,
            OpsShardReadinessEvidenceVerificationService opsShardReadinessEvidenceVerificationService,
            OpsShardReadinessEvidenceHandoffService opsShardReadinessEvidenceHandoffService,
            OpsShardReadinessActiveShardPlanHandoffService opsShardReadinessActiveShardPlanHandoffService,
            OpsShardReadinessLiveReadGatePlanService opsShardReadinessLiveReadGatePlanService,
            OpsShardReadinessOperatorServiceLifecycleService opsShardReadinessOperatorServiceLifecycleService,
            OpsShardReadinessDeclaredOperatorLifecycleService opsShardReadinessDeclaredOperatorLifecycleService,
            OpsShardReadinessRuntimeExecutionArtifactCandidateService
                    opsShardReadinessRuntimeExecutionArtifactCandidateService
    ) {
        this.opsShardReadinessService = opsShardReadinessService;
        this.opsShardReadinessHardeningService = opsShardReadinessHardeningService;
        this.opsShardReadinessEvidenceIndexService = opsShardReadinessEvidenceIndexService;
        this.opsShardReadinessEvidenceVerificationService = opsShardReadinessEvidenceVerificationService;
        this.opsShardReadinessEvidenceHandoffService = opsShardReadinessEvidenceHandoffService;
        this.opsShardReadinessActiveShardPlanHandoffService = opsShardReadinessActiveShardPlanHandoffService;
        this.opsShardReadinessLiveReadGatePlanService = opsShardReadinessLiveReadGatePlanService;
        this.opsShardReadinessOperatorServiceLifecycleService = opsShardReadinessOperatorServiceLifecycleService;
        this.opsShardReadinessDeclaredOperatorLifecycleService = opsShardReadinessDeclaredOperatorLifecycleService;
        this.opsShardReadinessRuntimeExecutionArtifactCandidateService =
                opsShardReadinessRuntimeExecutionArtifactCandidateService;
    }

    @GetMapping("/shard-readiness")
    public OpsShardReadinessResponse shardReadiness() {
        return opsShardReadinessService.readiness();
    }

    @GetMapping("/shard-readiness/hardening")
    public OpsShardReadinessHardeningResponse shardReadinessHardening() {
        return opsShardReadinessHardeningService.hardening();
    }

    @GetMapping("/shard-readiness/evidence-index")
    public OpsShardReadinessEvidenceIndexResponse shardReadinessEvidenceIndex() {
        return opsShardReadinessEvidenceIndexService.evidenceIndex();
    }

    @GetMapping("/shard-readiness/evidence-verification")
    public OpsShardReadinessEvidenceVerificationResponse shardReadinessEvidenceVerification() {
        return opsShardReadinessEvidenceVerificationService.verification();
    }

    @GetMapping("/shard-readiness/evidence-handoff")
    public OpsShardReadinessEvidenceHandoffResponse shardReadinessEvidenceHandoff() {
        return opsShardReadinessEvidenceHandoffService.handoff();
    }

    @GetMapping("/shard-readiness/active-shard-plan-handoff")
    public OpsShardReadinessActiveShardPlanHandoffResponse shardReadinessActiveShardPlanHandoff() {
        return opsShardReadinessActiveShardPlanHandoffService.handoff();
    }

    @GetMapping("/shard-readiness/live-read-gate-plan")
    public OpsShardReadinessLiveReadGatePlanResponse shardReadinessLiveReadGatePlan() {
        return opsShardReadinessLiveReadGatePlanService.plan();
    }

    @GetMapping("/shard-readiness/operator-service-lifecycle")
    public OpsShardReadinessOperatorServiceLifecycleResponse shardReadinessOperatorServiceLifecycle() {
        return opsShardReadinessOperatorServiceLifecycleService.lifecycle();
    }

    @GetMapping("/shard-readiness/declared-operator-lifecycle")
    public OpsShardReadinessDeclaredOperatorLifecycleResponse shardReadinessDeclaredOperatorLifecycle() {
        return opsShardReadinessDeclaredOperatorLifecycleService.lifecycle();
    }

    @GetMapping("/shard-readiness/runtime-execution-artifact-candidate")
    public OpsShardReadinessRuntimeExecutionArtifactCandidateResponse
            shardReadinessRuntimeExecutionArtifactCandidate() {
        return opsShardReadinessRuntimeExecutionArtifactCandidateService.candidate();
    }
}
