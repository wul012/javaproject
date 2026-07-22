package com.codexdemo.orderplatform.ops.maintenance.candidatedocument;

import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentIntakePacketResponse.Artifact;
import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentIntakePacketResponse.IntakeGuard;
import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentIntakePacketResponse.IntakeSlot;
import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentIntakePacketResponse.ModuleEntry;
import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentIntakePacketResponse.SourceLineage;
import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentSubmissionPrecheckResponse.Checkpoint;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

final class IntakeCatalog {

  private static final List<Integer> GROUP_SIZES = List.of(3, 3, 3, 3, 3, 2, 2, 2, 2, 2);

  private IntakeCatalog() {}

  static Evidence from(OpsShardReadinessCandidateDocumentSubmissionPrecheckResponse source) {
    var slots = slots(source);
    return new Evidence(
        sourceLineage(source), modules(), slots, guards(slots), artifacts(), gates());
  }

  private static List<SourceLineage> sourceLineage(
      OpsShardReadinessCandidateDocumentSubmissionPrecheckResponse source) {
    return List.of(
        source(
            "node-intake-packet-plan",
            "Node v1421",
            "D:/nodeproj/orderops-node/docs/plans3/v1421-controlled-read-only-shard-preview-candidate-document-intake-packet-closeout-roadmap.md",
            "defines ten intake slots, ten guards, and no-material stop condition"),
        source(
            "node-submission-precheck",
            "Node v1411",
            "controlled read-only shard preview candidate document submission precheck",
            "freezes the source checkpoint and validator counts"),
        source(
            "java-submission-precheck",
            source.version(),
            source.endpoint(),
            "provides twenty-five checkpoints and validators for slot grouping"),
        source(
            "java-submission-precheck-profile",
            source.profile(),
            source.endpoint(),
            "pins the route response consumed by this intake packet"),
        source(
            "future-reviewed-real-material",
            "blocked",
            "not-supplied",
            "keeps actual reviewed material intake out of scope"));
  }

  private static SourceLineage source(String code, String version, String source, String role) {
    return new SourceLineage(code, version, source, role, "passed");
  }

  private static List<ModuleEntry> modules() {
    return List.of(
        module(
            199,
            "source-lineage",
            "locks Node v1421, Node v1411, Java v1117, and future-material boundary"),
        module(
            200,
            "intake-slots",
            "groups twenty-five precheck checkpoints into ten compact intake slots"),
        module(
            201,
            "intake-guards",
            "maps each slot to a fail-closed guard before material is supplied"),
        module(
            202,
            "artifact-handles",
            "names archive references without accepting or importing material"),
        module(203, "route-closeout", "exposes the read-only route and final stop condition"));
  }

  private static ModuleEntry module(int order, String code, String responsibility) {
    return new ModuleEntry(order, code, responsibility, "java shard readiness owner", "passed");
  }

  private static List<IntakeSlot> slots(
      OpsShardReadinessCandidateDocumentSubmissionPrecheckResponse source) {
    List<IntakeSlot> slots = new ArrayList<>();
    int cursor = 0;
    for (int index = 0; index < GROUP_SIZES.size(); index++) {
      int groupSize = GROUP_SIZES.get(index);
      var checkpointCodes =
          source.checkpoints().subList(cursor, cursor + groupSize).stream()
              .map(Checkpoint::code)
              .toList();
      slots.add(slot(index + 1, groupSize, checkpointCodes));
      cursor += groupSize;
    }
    return slots;
  }

  private static IntakeSlot slot(
      int order, int coveredCheckpointCount, List<String> checkpointCodes) {
    return new IntakeSlot(
        order,
        "candidate-intake-slot-" + order,
        order <= 5 ? "request-material" : "boundary-material",
        String.join(",", checkpointCodes),
        coveredCheckpointCount,
        2,
        "reviewed-real-document-envelope-placeholder-" + order,
        "passed");
  }

  private static List<IntakeGuard> guards(List<IntakeSlot> slots) {
    return slots.stream().map(IntakeCatalog::guard).toList();
  }

  private static IntakeGuard guard(IntakeSlot slot) {
    return new IntakeGuard(
        slot.code() + "-guard",
        slot.code(),
        "reject-intake-packet-" + slot.code(),
        "Reject intake until reviewed real candidate document material exists for " + slot.code(),
        "fail-closed",
        "passed");
  }

  private static List<Artifact> artifacts() {
    return List.of(
        artifact(
            "source-node-plan",
            "e/1142/source/node-v1421-intake-packet-plan.md",
            "pins the Node intake packet roadmap"),
        artifact(
            "source-submission-precheck",
            "e/1142/source/java-v1117-submission-precheck.json",
            "pins the Java submission precheck consumed by this packet"),
        artifact(
            "source-lineage",
            "e/1142/lineage/candidate-document-intake-packet-source-lineage.json",
            "records source plan, precheck, and future material boundary"),
        artifact(
            "modules",
            "e/1142/modules/candidate-document-intake-packet-modules.json",
            "records the five-way maintenance split"),
        artifact(
            "intake-slots",
            "e/1142/intake/candidate-document-intake-packet-slots.json",
            "lists ten compact intake slots"),
        artifact(
            "intake-guards",
            "e/1142/intake/candidate-document-intake-packet-guards.json",
            "lists ten fail-closed guards"),
        artifact(
            "route-evidence",
            "e/1142/routes/candidate-document-intake-packet-route.json",
            "records read-only route and response profile"),
        artifact(
            "closeout",
            "e/1142/closeout/candidate-document-intake-packet-closeout.md",
            "records no-material stop condition"));
  }

  private static List<String> gates() {
    return IntStream.rangeClosed(
            1, OpsShardReadinessCandidateDocumentIntakePacketSupport.EXPECTED_GATE_COUNT)
        .mapToObj(index -> "candidate-document-intake-packet-no-material-gate-" + index)
        .toList();
  }

  private static Artifact artifact(String code, String reference, String purpose) {
    return new Artifact(code, reference, purpose, "passed");
  }

  record Evidence(
      List<SourceLineage> sourceLineage,
      List<ModuleEntry> modules,
      List<IntakeSlot> slots,
      List<IntakeGuard> guards,
      List<Artifact> artifacts,
      List<String> gates) {
    Evidence {
      sourceLineage = List.copyOf(sourceLineage);
      modules = List.copyOf(modules);
      slots = List.copyOf(slots);
      guards = List.copyOf(guards);
      artifacts = List.copyOf(artifacts);
      gates = List.copyOf(gates);
    }
  }
}
