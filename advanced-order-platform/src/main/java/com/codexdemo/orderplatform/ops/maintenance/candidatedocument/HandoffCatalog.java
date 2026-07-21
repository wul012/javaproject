package com.codexdemo.orderplatform.ops.maintenance.candidatedocument;

import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentHandoffResponse.ArchiveEntry;
import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentHandoffResponse.ArtifactHandle;
import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentHandoffResponse.ConsumerRule;
import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentHandoffResponse.ModuleEntry;
import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentHandoffResponse.PolicyLock;
import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentHandoffResponse.SourceLineage;
import java.util.List;
import java.util.Locale;
import java.util.stream.IntStream;

final class HandoffCatalog {

  private HandoffCatalog() {}

  static Evidence from(OpsShardReadinessCandidateDocumentRequestPackageResponse sourcePackage) {
    return new Evidence(
        sourceLineage(sourcePackage),
        modules(),
        artifactHandles(sourcePackage),
        policyLocks(sourcePackage),
        archiveEntries(),
        consumerRules(),
        gates());
  }

  private static List<SourceLineage> sourceLineage(
      OpsShardReadinessCandidateDocumentRequestPackageResponse sourcePackage) {
    return List.of(
        lineage(
            "node-request-plan",
            OpsShardReadinessCandidateDocumentHandoffSupport.SOURCE_PLAN,
            "D:/nodeproj/orderops-node/docs/plans3/v1386-controlled-read-only-shard-preview-candidate-document-request-package-closeout-roadmap.md",
            "defines request package counts and stop condition"),
        lineage(
            "node-candidate-intake",
            sourcePackage.sourceNodeCandidateIntakeVersion(),
            "controlled read-only shard preview compared evidence candidate intake preflight",
            "freezes the original fifteen request item inputs"),
        lineage(
            "java-candidate-intake",
            sourcePackage.sourceJavaCandidateIntakeVersion(),
            OpsShardReadinessCandidateDocumentRoutePaths.BASE_PATH
                + OpsShardReadinessCandidateDocumentRoutePaths
                    .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_COMPARED_EVIDENCE_CANDIDATE_INTAKE_PREFLIGHT_CATALOG,
            "preserves Java-side intake slots and guards"),
        lineage(
            "java-request-package",
            sourcePackage.version(),
            sourcePackage.endpoint(),
            "converts intake slots into request items and acceptance checks"),
        lineage(
            "java-request-package-profile",
            sourcePackage.profile(),
            sourcePackage.endpoint(),
            "pins the response contract consumed by this handoff"),
        lineage(
            "future-real-document-intake",
            "blocked",
            "not-opened",
            "keeps real document intake closed until a reviewed artifact exists"));
  }

  private static SourceLineage lineage(String code, String version, String source, String role) {
    return new SourceLineage(
        code, version, source, source.startsWith("/") ? source : "", role, "passed");
  }

  private static List<ModuleEntry> modules() {
    return List.of(
        module(
            190,
            "source-lineage",
            "locks Node plan, Node intake, Java intake, and Java request package references"),
        module(
            191,
            "artifact-handles",
            "maps each request item to a versioned evidence, digest, and archive handle"),
        module(
            192,
            "policy-locks",
            "keeps each acceptance check fail-closed before real document intake exists"),
        module(
            193,
            "archive-closeout",
            "names durable handoff archive entries without writing documents or payloads"),
        module(
            194,
            "consumer-rules",
            "records how a future consumer can read this handoff without opening execution"));
  }

  private static ModuleEntry module(int order, String code, String responsibility) {
    return new ModuleEntry(order, code, responsibility, "java shard readiness owner", "passed");
  }

  private static List<ArtifactHandle> artifactHandles(
      OpsShardReadinessCandidateDocumentRequestPackageResponse sourcePackage) {
    return sourcePackage.requestItems().stream().map(HandoffCatalog::handle).toList();
  }

  private static ArtifactHandle handle(
      OpsShardReadinessCandidateDocumentRequestPackageResponse.RequestItem item) {
    String slug = slug(item.code());
    return new ArtifactHandle(
        item.code(),
        item.sourceIntakeSlot(),
        item.requestedFields(),
        "candidate-document-request-package/evidence/" + slug + ".json",
        "candidate-document-request-package/digests/" + slug + ".sha256",
        "candidate-document-request-package/archive/" + slug + ".md",
        "waiting-for-reviewed-real-document",
        "passed");
  }

  private static String slug(String value) {
    return value.toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9]+", "-").replaceAll("(^-|-$)", "");
  }

  private static List<PolicyLock> policyLocks(
      OpsShardReadinessCandidateDocumentRequestPackageResponse sourcePackage) {
    return sourcePackage.acceptanceChecks().stream().map(HandoffCatalog::lock).toList();
  }

  private static PolicyLock lock(
      OpsShardReadinessCandidateDocumentRequestPackageResponse.AcceptanceCheck check) {
    return new PolicyLock(
        check.code(),
        check.category(),
        check.rejectionCode(),
        "freeze-until-reviewed-real-document-is-present",
        check.enforcement(),
        "passed");
  }

  private static List<ArchiveEntry> archiveEntries() {
    return List.of(
        entry(
            "source-plan",
            "e/1107/source/node-v1386-request-package-plan.md",
            "records the Node plan consumed by this Java handoff"),
        entry(
            "source-request-package",
            "e/1107/source/java-v1081-request-package.json",
            "pins the Java request package response consumed by this handoff"),
        entry(
            "source-lineage",
            "e/1107/lineage/candidate-document-handoff-source-lineage.json",
            "lists upstream plan, intake, and request package sources"),
        entry(
            "artifact-handles",
            "e/1107/artifacts/candidate-document-handoff-artifact-handles.json",
            "lists evidence, digest, and archive handles for each request item"),
        entry(
            "policy-locks",
            "e/1107/policy/candidate-document-handoff-policy-locks.json",
            "lists fail-closed acceptance locks"),
        entry(
            "consumer-rules",
            "e/1107/handoff/candidate-document-handoff-consumer-rules.json",
            "states consumer read rules and forbidden transitions"),
        entry(
            "route-evidence",
            "e/1107/routes/candidate-document-request-package-handoff-route.json",
            "captures read-only route and endpoint profile"),
        entry(
            "closeout",
            "e/1107/closeout/candidate-document-handoff-closeout.md",
            "summarizes the handoff stop condition"));
  }

  private static ArchiveEntry entry(String code, String path, String purpose) {
    return new ArchiveEntry(code, path, "retained-with-version-tag", purpose, "passed");
  }

  private static List<ConsumerRule> consumerRules() {
    return List.of(
        rule(
            "consume-versioned-route-only",
            "Consumers read the handoff route response and tag, not a rolling filesystem pointer."),
        rule(
            "require-reviewed-real-document",
            "A future intake remains blocked until a reviewed real compared package evidence document exists."),
        rule("reject-missing-document", "Missing candidate documents remain a fail-closed state."),
        rule(
            "reject-synthetic-document",
            "Synthetic or generated stand-ins cannot satisfy the request package."),
        rule(
            "quarantine-unreviewed-document",
            "Unreviewed source material stays outside import and evaluation paths."),
        rule("do-not-import-payload", "The handoff cannot import or stage document payloads."),
        rule(
            "do-not-evaluate-candidate",
            "The handoff cannot score, accept, reject, approve, or sign a candidate."),
        rule("do-not-open-runtime", "Runtime execution and active shard routing remain closed."),
        rule(
            "do-not-write-routing",
            "Write routing, managed audit connection, and rollback paths remain out of scope."),
        rule(
            "do-not-mutate-siblings",
            "The Java handoff cannot start, stop, or mutate Node or mini-kv workspaces."));
  }

  private static ConsumerRule rule(String code, String rule) {
    return new ConsumerRule(code, rule, "fail-closed", "passed");
  }

  private static List<String> gates() {
    return IntStream.rangeClosed(
            1, OpsShardReadinessCandidateDocumentHandoffSupport.EXPECTED_GATE_COUNT)
        .mapToObj(index -> "candidate-document-handoff-read-only-gate-" + index)
        .toList();
  }

  record Evidence(
      List<SourceLineage> sourceLineage,
      List<ModuleEntry> modules,
      List<ArtifactHandle> artifactHandles,
      List<PolicyLock> policyLocks,
      List<ArchiveEntry> archiveEntries,
      List<ConsumerRule> consumerRules,
      List<String> gates) {

    Evidence {
      sourceLineage = List.copyOf(sourceLineage);
      modules = List.copyOf(modules);
      artifactHandles = List.copyOf(artifactHandles);
      policyLocks = List.copyOf(policyLocks);
      archiveEntries = List.copyOf(archiveEntries);
      consumerRules = List.copyOf(consumerRules);
      gates = List.copyOf(gates);
    }
  }
}
