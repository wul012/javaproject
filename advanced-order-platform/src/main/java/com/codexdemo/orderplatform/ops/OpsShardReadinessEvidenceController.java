package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessEvidenceHandoffResponse;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessEvidenceHandoffService;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessEvidenceIndexResponse;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessEvidenceIndexService;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessEvidenceVerificationResponse;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessEvidenceVerificationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessEvidenceController {

  private final OpsShardReadinessEvidenceIndexService evidenceIndexService;

  private final OpsShardReadinessEvidenceVerificationService evidenceVerificationService;

  private final OpsShardReadinessEvidenceHandoffService evidenceHandoffService;

  public OpsShardReadinessEvidenceController(
      OpsShardReadinessEvidenceIndexService evidenceIndexService,
      OpsShardReadinessEvidenceVerificationService evidenceVerificationService,
      OpsShardReadinessEvidenceHandoffService evidenceHandoffService) {
    this.evidenceIndexService = evidenceIndexService;
    this.evidenceVerificationService = evidenceVerificationService;
    this.evidenceHandoffService = evidenceHandoffService;
  }

  @GetMapping(OpsShardReadinessRoutePaths.EVIDENCE_INDEX)
  public OpsShardReadinessEvidenceIndexResponse evidenceIndex() {
    return evidenceIndexService.evidenceIndex();
  }

  @GetMapping(OpsShardReadinessRoutePaths.EVIDENCE_VERIFICATION)
  public OpsShardReadinessEvidenceVerificationResponse evidenceVerification() {
    return evidenceVerificationService.verification();
  }

  @GetMapping(OpsShardReadinessRoutePaths.EVIDENCE_HANDOFF)
  public OpsShardReadinessEvidenceHandoffResponse evidenceHandoff() {
    return evidenceHandoffService.handoff();
  }
}
