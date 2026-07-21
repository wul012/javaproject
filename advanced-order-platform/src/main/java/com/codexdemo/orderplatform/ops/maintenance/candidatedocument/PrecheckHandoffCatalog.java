package com.codexdemo.orderplatform.ops.maintenance.candidatedocument;

import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffResponse.ArchiveHandle;
import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffResponse.ArtifactReference;
import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffResponse.ConsumerRule;
import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffResponse.ModuleEntry;
import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffResponse.PolicyLock;
import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffResponse.SourceLineage;
import java.util.List;
import java.util.stream.IntStream;

final class PrecheckHandoffCatalog {

  private PrecheckHandoffCatalog() {}

  static Evidence from(
      OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckResponse sourcePrecheck) {
    return new Evidence(
        sourceLineage(sourcePrecheck),
        modules(),
        archiveHandles(sourcePrecheck),
        policyLocks(sourcePrecheck),
        artifactReferences(sourcePrecheck),
        consumerRules(sourcePrecheck),
        gates());
  }

  private static List<SourceLineage> sourceLineage(
      OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckResponse sourcePrecheck) {
    return List.of(
        lineage(
            1,
            "node-material-submission-precheck-plan",
            OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffSupport.SOURCE_PLAN,
            "D:/nodeproj/orderops-node/docs/plans3/"
                + "v1456-controlled-read-only-shard-preview-candidate-document-material-"
                + "submission-precheck-roadmap.md"),
        lineage(
            2,
            "java-material-submission-precheck-route",
            sourcePrecheck.version(),
            sourcePrecheck.endpoint()),
        lineage(
            3,
            "java-material-submission-precheck-profile",
            sourcePrecheck.profile(),
            "read-only profile"),
        lineage(
            4,
            "java-material-submission-precheck-checkpoints",
            Integer.toString(sourcePrecheck.checkpointCount()),
            "checkpoint catalog"),
        lineage(
            5,
            "java-material-submission-precheck-validators",
            Integer.toString(sourcePrecheck.validatorCount()),
            "validator catalog"),
        lineage(
            6,
            "java-material-submission-precheck-artifacts-and-gates",
            sourcePrecheck.artifactCount() + "/" + sourcePrecheck.gateCount(),
            "artifact and gate closeout"));
  }

  private static SourceLineage lineage(int order, String code, String source, String reference) {
    return new SourceLineage(order, code, source, reference, "passed");
  }

  private static List<ModuleEntry> modules() {
    return List.of(
        module(
            214, "material-submission-precheck-handoff-types", "defines archive handoff records"),
        module(
            215,
            "material-submission-precheck-handoff-source",
            "pins the source Java v1162 precheck lineage"),
        module(
            216,
            "material-submission-precheck-handoff-archive-policy",
            "maps checkpoints and validators to archive handles and locks"),
        module(
            217,
            "material-submission-precheck-handoff-consumer",
            "declares read-only consumer rules"),
        module(
            218,
            "material-submission-precheck-handoff-route",
            "exposes the handoff route without accepting material"));
  }

  private static ModuleEntry module(int order, String code, String responsibility) {
    return new ModuleEntry(order, code, responsibility, "passed");
  }

  private static List<ArchiveHandle> archiveHandles(
      OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckResponse sourcePrecheck) {
    return sourcePrecheck.checkpoints().stream()
        .map(
            checkpoint ->
                new ArchiveHandle(
                    "archive-" + checkpoint.code(),
                    checkpoint.code(),
                    "e/1187/archive/" + checkpoint.code() + ".json",
                    "read-only candidate material submission precheck archive",
                    "passed"))
        .toList();
  }

  private static List<PolicyLock> policyLocks(
      OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckResponse sourcePrecheck) {
    return sourcePrecheck.validators().stream()
        .map(
            validator ->
                new PolicyLock(
                    "policy-lock-" + validator.checkpointCode(),
                    validator.code(),
                    validator.rejectionCode(),
                    "Keep material submission blocked until archived precheck evidence is reviewed",
                    validator.enforcement(),
                    "passed"))
        .toList();
  }

  private static List<ArtifactReference> artifactReferences(
      OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckResponse sourcePrecheck) {
    return sourcePrecheck.artifacts().stream()
        .map(
            artifact ->
                new ArtifactReference(
                    "handoff-" + artifact.code(),
                    artifact.reference(),
                    "e/1187/artifacts/" + artifact.code() + ".json",
                    "archive reference for " + artifact.purpose(),
                    "passed"))
        .toList();
  }

  private static List<ConsumerRule> consumerRules(
      OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckResponse sourcePrecheck) {
    return sourcePrecheck.checkpoints().stream()
        .map(
            checkpoint ->
                new ConsumerRule(
                    "consumer-read-only-" + checkpoint.code(),
                    checkpoint.code(),
                    "read archive handle and policy lock",
                    "submit, import, evaluate, approve, sign, execute, write, or mutate material",
                    "passed"))
        .toList();
  }

  private static List<String> gates() {
    return IntStream.rangeClosed(
            1,
            OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffSupport
                .EXPECTED_GATE_COUNT)
        .mapToObj(
            index ->
                "candidate-document-material-submission-precheck-handoff-no-material-gate-" + index)
        .toList();
  }

  record Evidence(
      List<SourceLineage> sourceLineage,
      List<ModuleEntry> modules,
      List<ArchiveHandle> archiveHandles,
      List<PolicyLock> policyLocks,
      List<ArtifactReference> artifactReferences,
      List<ConsumerRule> consumerRules,
      List<String> gates) {

    Evidence {
      sourceLineage = List.copyOf(sourceLineage);
      modules = List.copyOf(modules);
      archiveHandles = List.copyOf(archiveHandles);
      policyLocks = List.copyOf(policyLocks);
      artifactReferences = List.copyOf(artifactReferences);
      consumerRules = List.copyOf(consumerRules);
      gates = List.copyOf(gates);
    }
  }
}
