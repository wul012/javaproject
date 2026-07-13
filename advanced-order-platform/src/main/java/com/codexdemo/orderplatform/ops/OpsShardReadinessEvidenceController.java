package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessEvidenceHandoffResponse;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessEvidenceHandoffService;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessEvidenceIndexResponse;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessEvidenceIndexService;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessEvidenceVerificationResponse;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessEvidenceVerificationService;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessService.BASE_PATH)
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

  @GetMapping(OpsShardReadinessService.EVIDENCE_INDEX_PATH)
  public OpsShardReadinessEvidenceIndexResponse evidenceIndex() {
    return evidenceIndexService.evidenceIndex();
  }

  @GetMapping(OpsShardReadinessService.EVIDENCE_VERIFICATION_PATH)
  public OpsShardReadinessEvidenceVerificationResponse evidenceVerification() {
    return evidenceVerificationService.verification();
  }

  @GetMapping(OpsShardReadinessService.EVIDENCE_HANDOFF_PATH)
  public OpsShardReadinessEvidenceHandoffResponse evidenceHandoff() {
    return evidenceHandoffService.handoff();
  }
}
