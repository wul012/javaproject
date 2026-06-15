package com.codexdemo.orderplatform.ops.maintenance.candidatedocument;

import java.util.List;

final class OpsShardReadinessCandidateDocumentIntakePacketGuardCatalog {

  private OpsShardReadinessCandidateDocumentIntakePacketGuardCatalog() {}

  static List<OpsShardReadinessCandidateDocumentIntakePacketResponse.IntakeGuard> guards(
      List<OpsShardReadinessCandidateDocumentIntakePacketResponse.IntakeSlot> slots) {
    return slots.stream()
        .map(OpsShardReadinessCandidateDocumentIntakePacketGuardCatalog::guard)
        .toList();
  }

  private static OpsShardReadinessCandidateDocumentIntakePacketResponse.IntakeGuard guard(
      OpsShardReadinessCandidateDocumentIntakePacketResponse.IntakeSlot slot) {
    return new OpsShardReadinessCandidateDocumentIntakePacketResponse.IntakeGuard(
        slot.code() + "-guard",
        slot.code(),
        "reject-intake-packet-" + slot.code(),
        "Reject intake until reviewed real candidate document material exists for " + slot.code(),
        "fail-closed",
        "passed");
  }
}
