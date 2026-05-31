package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ops/shard-readiness")
public class OpsShardReadinessRuntimeExecutionController {

    private final OpsShardReadinessRuntimeExecutionArtifactCandidateService
            artifactCandidateService;

    private final OpsShardReadinessRuntimeExecutionPacketContributionService
            packetContributionService;

    private final OpsShardReadinessRuntimeExecutionApprovalGateInputService
            approvalGateInputService;

    private final OpsShardReadinessRuntimeExecutionApprovalInputContractHandoffService
            approvalInputContractHandoffService;

    private final OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityService
            approvalInputTemplateCompatibilityService;

    private final OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityIntakeService
            approvalInputTemplateCompatibilityIntakeService;

    private final OpsShardReadinessRuntimeExecutionApprovalInputValueValidationService
            approvalInputValueValidationService;

    private final OpsShardReadinessRuntimeExecutionLiveReadGateService liveReadGateService;

    private final OpsShardReadinessRuntimeExecutionPassEvidenceCloseoutService passEvidenceCloseoutService;

    public OpsShardReadinessRuntimeExecutionController(
            OpsShardReadinessRuntimeExecutionArtifactCandidateService artifactCandidateService,
            OpsShardReadinessRuntimeExecutionPacketContributionService packetContributionService,
            OpsShardReadinessRuntimeExecutionApprovalGateInputService approvalGateInputService,
            OpsShardReadinessRuntimeExecutionApprovalInputContractHandoffService
                    approvalInputContractHandoffService,
            OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityService
                    approvalInputTemplateCompatibilityService,
            OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityIntakeService
                    approvalInputTemplateCompatibilityIntakeService,
            OpsShardReadinessRuntimeExecutionApprovalInputValueValidationService
                    approvalInputValueValidationService,
            OpsShardReadinessRuntimeExecutionLiveReadGateService liveReadGateService,
            OpsShardReadinessRuntimeExecutionPassEvidenceCloseoutService passEvidenceCloseoutService
    ) {
        this.artifactCandidateService = artifactCandidateService;
        this.packetContributionService = packetContributionService;
        this.approvalGateInputService = approvalGateInputService;
        this.approvalInputContractHandoffService = approvalInputContractHandoffService;
        this.approvalInputTemplateCompatibilityService = approvalInputTemplateCompatibilityService;
        this.approvalInputTemplateCompatibilityIntakeService = approvalInputTemplateCompatibilityIntakeService;
        this.approvalInputValueValidationService = approvalInputValueValidationService;
        this.liveReadGateService = liveReadGateService;
        this.passEvidenceCloseoutService = passEvidenceCloseoutService;
    }

    @GetMapping("/runtime-execution-artifact-candidate")
    public OpsShardReadinessRuntimeExecutionArtifactCandidateResponse artifactCandidate() {
        return artifactCandidateService.candidate();
    }

    @GetMapping("/runtime-execution-packet-contribution")
    public OpsShardReadinessRuntimeExecutionPacketContributionResponse packetContribution() {
        return packetContributionService.contribution();
    }

    @GetMapping("/runtime-execution-approval-gate-input")
    public OpsShardReadinessRuntimeExecutionApprovalGateInputResponse approvalGateInput() {
        return approvalGateInputService.approvalGateInput();
    }

    @GetMapping("/runtime-execution-approval-input-contract-handoff")
    public OpsShardReadinessRuntimeExecutionApprovalInputContractHandoffResponse approvalInputContractHandoff() {
        return approvalInputContractHandoffService.handoff();
    }

    @GetMapping("/runtime-execution-approval-input-template-compatibility")
    public OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityResponse
            approvalInputTemplateCompatibility() {
        return approvalInputTemplateCompatibilityService.compatibility();
    }

    @GetMapping("/runtime-execution-approval-input-template-compatibility-intake")
    public OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityIntakeResponse
            approvalInputTemplateCompatibilityIntake() {
        return approvalInputTemplateCompatibilityIntakeService.intake();
    }

    @GetMapping("/runtime-execution-approval-input-value-validation")
    public OpsShardReadinessRuntimeExecutionApprovalInputValueValidationResponse approvalInputValueValidation() {
        return approvalInputValueValidationService.validation();
    }

    @GetMapping("/runtime-execution-live-read-gate")
    public OpsShardReadinessRuntimeExecutionLiveReadGateResponse liveReadGate() {
        return liveReadGateService.gate();
    }

    @GetMapping("/runtime-execution-pass-evidence-closeout")
    public OpsShardReadinessRuntimeExecutionPassEvidenceCloseoutResponse passEvidenceCloseout() {
        return passEvidenceCloseoutService.closeout();
    }
}
