package com.codexdemo.orderplatform.ops.maintenance.candidatedocument;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessCandidateDocumentIntakePacketServiceTests {

  @Test
  void buildsReadOnlyIntakePacketFromSubmissionPrecheck() {
    var response = service().intakePacket();

    assertThat(response.project()).isEqualTo("advanced-order-platform");
    assertThat(response.version()).isEqualTo("Java v1142");
    assertThat(response.readOnly()).isTrue();
    assertThat(response.executionAllowed()).isFalse();
    assertThat(response.readyForIntakePacket()).isTrue();
    assertThat(response.sourcePlan()).isEqualTo("Node v1421");
    assertThat(response.sourceNodeSubmissionPrecheckVersion()).isEqualTo("Node v1411");
    assertThat(response.sourceJavaSubmissionPrecheckVersion()).isEqualTo("Java v1117");
    assertThat(response.sourcePrecheckEndpoint())
        .endsWith("candidate-document-submission-precheck");
    assertThat(response.intakeSlotCount()).isEqualTo(10);
    assertThat(response.passedIntakeSlotCount()).isEqualTo(10);
    assertThat(response.intakeGuardCount()).isEqualTo(10);
    assertThat(response.passedIntakeGuardCount()).isEqualTo(10);
    assertThat(response.coveredSourceCheckpointCount()).isEqualTo(25);
    assertThat(response.coveredSourceValidatorCount()).isEqualTo(25);
    assertThat(response.carriedCandidateFieldCount()).isEqualTo(20);
    assertThat(response.artifactCount()).isEqualTo(8);
    assertThat(response.gateCount()).isEqualTo(35);
    assertThat(response.status()).isEqualTo("passed");
  }

  @Test
  void keepsMaterialPayloadAndMutationPathsClosed() {
    var response = service().intakePacket();

    assertThat(response.realDocumentCount()).isZero();
    assertThat(response.syntheticDocumentCount()).isZero();
    assertThat(response.stagedDocumentCount()).isZero();
    assertThat(response.importedDocumentCount()).isZero();
    assertThat(response.evaluatedDocumentCount()).isZero();
    assertThat(response.acceptedDocumentCount()).isZero();
    assertThat(response.rejectedDocumentCount()).isZero();
    assertThat(response.payloadCount()).isZero();
    assertThat(response.materialAccepted()).isFalse();
    assertThat(response.importAllowed()).isFalse();
    assertThat(response.evaluationAllowed()).isFalse();
    assertThat(response.approvalGrantAllowed()).isFalse();
    assertThat(response.signedApprovalCaptureAllowed()).isFalse();
    assertThat(response.runtimePayloadAllowed()).isFalse();
    assertThat(response.writeAllowed()).isFalse();
    assertThat(response.siblingMutationAllowed()).isFalse();
  }

  @Test
  void intakeSlotsCompactAllSourceCheckpointsAndCarryFields() {
    var response = service().intakePacket();

    assertThat(response.intakeSlots())
        .extracting(OpsShardReadinessCandidateDocumentIntakePacketResponse.IntakeSlot::order)
        .containsExactly(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    assertThat(response.intakeSlots())
        .extracting(
            OpsShardReadinessCandidateDocumentIntakePacketResponse.IntakeSlot
                ::coveredCheckpointCount)
        .containsExactly(3, 3, 3, 3, 3, 2, 2, 2, 2, 2);
    assertThat(response.intakeSlots())
        .allSatisfy(
            slot -> {
              assertThat(slot.code()).startsWith("candidate-intake-slot-");
              assertThat(slot.carriedFieldCount()).isEqualTo(2);
              assertThat(slot.envelopePlaceholder())
                  .startsWith("reviewed-real-document-envelope-placeholder-");
              assertThat(slot.status()).isEqualTo("passed");
            });
  }

  @Test
  void intakeGuardsMirrorSlotsAndStayFailClosed() {
    var response = service().intakePacket();

    assertThat(response.intakeGuards())
        .extracting(OpsShardReadinessCandidateDocumentIntakePacketResponse.IntakeGuard::slotCode)
        .containsExactlyElementsOf(
            response.intakeSlots().stream()
                .map(OpsShardReadinessCandidateDocumentIntakePacketResponse.IntakeSlot::code)
                .toList());
    assertThat(response.intakeGuards())
        .allSatisfy(
            guard -> {
              assertThat(guard.code()).endsWith("-guard");
              assertThat(guard.rejectionCode())
                  .startsWith("reject-intake-packet-candidate-intake-slot-");
              assertThat(guard.guard()).contains("reviewed real candidate document material");
              assertThat(guard.enforcement()).isEqualTo("fail-closed");
              assertThat(guard.status()).isEqualTo("passed");
            });
  }

  @Test
  void sourceLineageAndModulesKeepMaintenanceBoundary() {
    var response = service().intakePacket();

    assertThat(response.sourceLineage())
        .extracting(OpsShardReadinessCandidateDocumentIntakePacketResponse.SourceLineage::code)
        .containsExactly(
            "node-intake-packet-plan",
            "node-submission-precheck",
            "java-submission-precheck",
            "java-submission-precheck-profile",
            "future-reviewed-real-material");
    assertThat(response.sourceLineage())
        .anySatisfy(
            source -> {
              assertThat(source.code()).isEqualTo("future-reviewed-real-material");
              assertThat(source.version()).isEqualTo("blocked");
              assertThat(source.source()).isEqualTo("not-supplied");
            });
    assertThat(response.modules())
        .extracting(OpsShardReadinessCandidateDocumentIntakePacketResponse.ModuleEntry::order)
        .containsExactly(199, 200, 201, 202, 203);
    assertThat(response.modules())
        .extracting(OpsShardReadinessCandidateDocumentIntakePacketResponse.ModuleEntry::code)
        .containsExactly(
            "source-lineage",
            "intake-slots",
            "intake-guards",
            "artifact-handles",
            "route-closeout");
  }

  @Test
  void artifactsAndGatesStayVersionedAndEvidenceOnly() {
    var response = service().intakePacket();

    assertThat(response.artifacts())
        .extracting(OpsShardReadinessCandidateDocumentIntakePacketResponse.Artifact::code)
        .containsExactly(
            "source-node-plan",
            "source-submission-precheck",
            "source-lineage",
            "modules",
            "intake-slots",
            "intake-guards",
            "route-evidence",
            "closeout");
    assertThat(response.artifacts())
        .allSatisfy(
            artifact -> {
              assertThat(artifact.reference()).startsWith("e/1142/");
              assertThat(artifact.status()).isEqualTo("passed");
            });
    assertThat(response.gates())
        .hasSize(35)
        .first()
        .isEqualTo("candidate-document-intake-packet-no-material-gate-1");
    assertThat(response.gates())
        .last()
        .isEqualTo("candidate-document-intake-packet-no-material-gate-35");
  }

  @Test
  void checksRecordCoverageCountsAndDisabledBoundaries() {
    var response = service().intakePacket();

    assertThat(response.checks())
        .contains(
            "candidate-document-intake-packet-source-plan-Node v1421",
            "candidate-document-intake-packet-source-java-precheck-Java v1117",
            "candidate-document-intake-packet-slot-count-10",
            "candidate-document-intake-packet-guard-count-10",
            "candidate-document-intake-packet-covered-checkpoint-count-25",
            "candidate-document-intake-packet-covered-validator-count-25",
            "candidate-document-intake-packet-carried-field-count-20",
            "candidate-document-intake-packet-no-material-accepted",
            "candidate-document-intake-packet-import-disabled",
            "candidate-document-intake-packet-runtime-disabled",
            "candidate-document-intake-packet-sibling-mutation-disabled",
            "candidate-document-intake-packet-service-assembled-from-submission-precheck");
  }

  @Test
  void carriedFieldsRemainPlaceholdersUntilReviewedMaterialExists() {
    var response = service().intakePacket();

    assertThat(response.intakeSlots())
        .allSatisfy(
            slot -> {
              assertThat(slot.carriedFieldCount()).isEqualTo(2);
              assertThat(slot.envelopePlaceholder()).contains("placeholder");
              assertThat(slot.envelopePlaceholder()).doesNotContain("accepted");
              assertThat(slot.envelopePlaceholder()).doesNotContain("imported");
            });
    assertThat(response.carriedCandidateFieldCount())
        .isEqualTo(
            response.intakeSlots().stream()
                .mapToInt(
                    OpsShardReadinessCandidateDocumentIntakePacketResponse.IntakeSlot
                        ::carriedFieldCount)
                .sum());
  }

  @Test
  void stopConditionWaitsForReviewedRealMaterial() {
    var response = service().intakePacket();

    assertThat(response.intakePacketState())
        .isEqualTo("waiting-for-reviewed-real-compared-package-evidence-candidate-document");
    assertThat(response.sourceLineage())
        .anySatisfy(
            source -> {
              assertThat(source.code()).isEqualTo("future-reviewed-real-material");
              assertThat(source.version()).isEqualTo("blocked");
              assertThat(source.role()).contains("actual reviewed material intake out of scope");
            });
  }

  private OpsShardReadinessCandidateDocumentIntakePacketService service() {
    var requestPackageService = new OpsShardReadinessCandidateDocumentRequestPackageService();
    var handoffService =
        new OpsShardReadinessCandidateDocumentHandoffService(requestPackageService);
    var precheckService =
        new OpsShardReadinessCandidateDocumentSubmissionPrecheckService(
            requestPackageService, handoffService);
    return new OpsShardReadinessCandidateDocumentIntakePacketService(precheckService);
  }
}
