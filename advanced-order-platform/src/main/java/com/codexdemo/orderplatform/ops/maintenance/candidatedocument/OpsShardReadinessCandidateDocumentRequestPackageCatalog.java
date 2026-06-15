package com.codexdemo.orderplatform.ops.maintenance.candidatedocument;

import java.util.List;
import java.util.stream.IntStream;

final class OpsShardReadinessCandidateDocumentRequestPackageCatalog {

  private static final String SOURCE_ENDPOINT =
      OpsShardReadinessCandidateDocumentRoutePaths.BASE_PATH
          + OpsShardReadinessCandidateDocumentRoutePaths
              .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_COMPARED_EVIDENCE_CANDIDATE_INTAKE_PREFLIGHT_CATALOG;

  private OpsShardReadinessCandidateDocumentRequestPackageCatalog() {}

  static List<OpsShardReadinessCandidateDocumentRequestPackageResponse.RequestItem> requestItems() {
    return List.of(
        item(
            "source-readiness-request",
            "source-intake-readiness-document",
            "source intake readiness, artifact shape",
            "Provide reviewed source readiness document."),
        item(
            "operator-provenance-request",
            "operator-provenance-document",
            "operator provenance, source evidence handle",
            "Provide reviewed operator provenance document."),
        item(
            "manual-submission-request",
            "manual-submission-document",
            "manual submission reference, operator value handle",
            "Provide reviewed manual submission document."),
        item(
            "offline-comparison-request",
            "offline-comparison-document",
            "offline comparison result, mismatch summary",
            "Provide reviewed offline comparison document."),
        item(
            "identity-digest-request",
            "identity-digest-document",
            "identity binding, digest lineage",
            "Provide reviewed identity digest document."),
        item(
            "signature-envelope-request",
            "signature-envelope-document",
            "signature envelope metadata, detached signature observation",
            "Provide reviewed signature envelope document."),
        item(
            "policy-execution-request",
            "policy-execution-lock-document",
            "policy assertion, execution lock",
            "Provide reviewed policy execution lock document."),
        item(
            "approval-archive-request",
            "approval-archive-document",
            "approval grant separation, archive reference",
            "Provide reviewed approval archive document."),
        item(
            "exclusion-boundary-request",
            "exclusion-boundary-document",
            "secret, synthetic, runtime, write, and sibling exclusions",
            "Provide reviewed exclusion boundary document."),
        item(
            "candidate-closeout-request",
            "candidate-closeout-document",
            "reviewer traceability, candidate blueprint closeout",
            "Provide reviewed candidate closeout document."),
        item(
            "missing-document-freeze-request",
            "all candidate slots",
            "missing document rejection",
            "Confirm missing documents remain fail-closed."),
        item(
            "synthetic-document-freeze-request",
            "all candidate slots",
            "synthetic document rejection",
            "Confirm synthetic documents remain blocked."),
        item(
            "unreviewed-document-quarantine-request",
            "all candidate slots",
            "unreviewed document quarantine",
            "Confirm unreviewed documents cannot be imported."),
        item(
            "payload-import-freeze-request",
            "all candidate slots",
            "payload import rejection",
            "Confirm payload import remains disabled."),
        item(
            "approval-runtime-write-freeze-request",
            "all candidate slots",
            "approval, runtime, write, and sibling mutation rejection",
            "Confirm request package cannot approve, execute, write, or mutate siblings."));
  }

  static List<OpsShardReadinessCandidateDocumentRequestPackageResponse.AcceptanceCheck>
      acceptanceChecks() {
    return requestItems().stream()
        .map(
            item ->
                OpsShardReadinessCandidateDocumentRequestPackageSupport.check(
                    item.code() + "-acceptance-check",
                    item.sourceIntakeSlot(),
                    "Reject package readiness until " + item.instruction(),
                    "reject-request-package-" + item.code()))
        .toList();
  }

  static List<String> gates() {
    return IntStream.rangeClosed(
            1, OpsShardReadinessCandidateDocumentRequestPackageSupport.GATE_COUNT)
        .mapToObj(index -> "candidate-document-request-package-gate-" + index)
        .toList();
  }

  private static OpsShardReadinessCandidateDocumentRequestPackageResponse.RequestItem item(
      String code, String sourceIntakeSlot, String requestedFields, String instruction) {
    return OpsShardReadinessCandidateDocumentRequestPackageSupport.item(
        code,
        sourceIntakeSlot,
        requestedFields,
        instruction,
        "candidate document request owner",
        SOURCE_ENDPOINT);
  }
}
