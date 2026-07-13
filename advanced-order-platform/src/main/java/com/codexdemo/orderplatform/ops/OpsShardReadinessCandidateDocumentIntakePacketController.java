package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentIntakePacketResponse;
import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentIntakePacketService;
import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentRoutePaths;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessService.BASE_PATH)
public class OpsShardReadinessCandidateDocumentIntakePacketController {

  private final OpsShardReadinessCandidateDocumentIntakePacketService service;

  public OpsShardReadinessCandidateDocumentIntakePacketController(
      OpsShardReadinessCandidateDocumentIntakePacketService service) {
    this.service = service;
  }

  @GetMapping(OpsShardReadinessCandidateDocumentRoutePaths.CANDIDATE_DOCUMENT_INTAKE_PACKET)
  public OpsShardReadinessCandidateDocumentIntakePacketResponse intakePacket() {
    return service.intakePacket();
  }
}
