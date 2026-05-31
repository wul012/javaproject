package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ops/shard-readiness")
public class OpsShardReadinessLifecyclePlanController {

    private final OpsShardReadinessActiveShardPlanHandoffService activeShardPlanHandoffService;

    private final OpsShardReadinessLiveReadGatePlanService liveReadGatePlanService;

    private final OpsShardReadinessOperatorServiceLifecycleService operatorServiceLifecycleService;

    private final OpsShardReadinessDeclaredOperatorLifecycleService declaredOperatorLifecycleService;

    public OpsShardReadinessLifecyclePlanController(
            OpsShardReadinessActiveShardPlanHandoffService activeShardPlanHandoffService,
            OpsShardReadinessLiveReadGatePlanService liveReadGatePlanService,
            OpsShardReadinessOperatorServiceLifecycleService operatorServiceLifecycleService,
            OpsShardReadinessDeclaredOperatorLifecycleService declaredOperatorLifecycleService
    ) {
        this.activeShardPlanHandoffService = activeShardPlanHandoffService;
        this.liveReadGatePlanService = liveReadGatePlanService;
        this.operatorServiceLifecycleService = operatorServiceLifecycleService;
        this.declaredOperatorLifecycleService = declaredOperatorLifecycleService;
    }

    @GetMapping("/active-shard-plan-handoff")
    public OpsShardReadinessActiveShardPlanHandoffResponse activeShardPlanHandoff() {
        return activeShardPlanHandoffService.handoff();
    }

    @GetMapping("/live-read-gate-plan")
    public OpsShardReadinessLiveReadGatePlanResponse liveReadGatePlan() {
        return liveReadGatePlanService.plan();
    }

    @GetMapping("/operator-service-lifecycle")
    public OpsShardReadinessOperatorServiceLifecycleResponse operatorServiceLifecycle() {
        return operatorServiceLifecycleService.lifecycle();
    }

    @GetMapping("/declared-operator-lifecycle")
    public OpsShardReadinessDeclaredOperatorLifecycleResponse declaredOperatorLifecycle() {
        return declaredOperatorLifecycleService.lifecycle();
    }
}
