package com.codexdemo.orderplatform.ops.maintenance.candidatedocument;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

final class OpsShardReadinessCandidateDocumentMaterialRequestCatalog {

  private OpsShardReadinessCandidateDocumentMaterialRequestCatalog() {}

  static List<OpsShardReadinessCandidateDocumentMaterialRequestResponse.ModuleEntry> modules() {
    return List.of(
        module(204, "material-request-types", "defines request item and acceptance check records"),
        module(205, "material-request-catalog", "derives requests from intake slots and guards"),
        module(206, "material-request-builder", "assembles the read-only response from Java v1142"),
        module(
            207,
            "material-request-artifacts",
            "names archive references without accepting material"),
        module(
            208, "material-request-route", "exposes the route and no-material closeout evidence"));
  }

  static List<OpsShardReadinessCandidateDocumentMaterialRequestResponse.RequestItem> requestItems(
      OpsShardReadinessCandidateDocumentIntakePacketResponse sourcePacket) {
    List<OpsShardReadinessCandidateDocumentMaterialRequestResponse.RequestItem> items =
        new ArrayList<>();
    sourcePacket
        .intakeSlots()
        .forEach(
            slot ->
                items.add(
                    item(
                        "material-slot-" + slot.code(),
                        slot.code(),
                        "intake-slot-material",
                        "reviewer identity, source uri",
                        "Request external reviewed material for " + slot.code())));
    sourcePacket
        .intakeGuards()
        .forEach(
            guard ->
                items.add(
                    item(
                        "material-guard-" + guard.slotCode(),
                        guard.code(),
                        "guard-attestation",
                        "absence attestation, boundary confirmation",
                        "Request reviewer attestation for " + guard.slotCode())));
    items.add(
        item(
            "reviewer-identity-request",
            "external-material",
            "external-material",
            "reviewer identity, review timestamp",
            "Request reviewer identity and review timestamp."));
    items.add(
        item(
            "source-uri-digest-request",
            "external-material",
            "external-material",
            "source uri, digest",
            "Request source URI and digest lineage."));
    items.add(
        item(
            "canonical-body-field-table-request",
            "external-material",
            "external-material",
            "canonical body, field table",
            "Request canonical body and material field table."));
    items.add(
        item(
            "comparison-signature-request",
            "external-material",
            "external-material",
            "comparison binding, signature attestation",
            "Request comparison binding and detached signature attestation."));
    items.add(
        item(
            "redaction-archive-closeout-request",
            "external-material",
            "external-material",
            "redaction log, archive index, absence attestations",
            "Request redaction log, archive index, and absence attestations."));
    return List.copyOf(items);
  }

  static List<OpsShardReadinessCandidateDocumentMaterialRequestResponse.AcceptanceCheck>
      acceptanceChecks(
          List<OpsShardReadinessCandidateDocumentMaterialRequestResponse.RequestItem>
              requestItems) {
    return requestItems.stream()
        .map(
            item ->
                new OpsShardReadinessCandidateDocumentMaterialRequestResponse.AcceptanceCheck(
                    item.code() + "-acceptance-check",
                    item.code(),
                    "reject-material-request-" + item.code(),
                    "Reject material request until external reviewed material satisfies "
                        + item.instruction(),
                    "fail-closed",
                    "passed"))
        .toList();
  }

  static List<OpsShardReadinessCandidateDocumentMaterialRequestResponse.Artifact> artifacts() {
    return List.of(
        artifact("source-node-plan", "e/1152/source/node-v1446-material-request-plan.md"),
        artifact("source-intake-packet", "e/1152/source/java-v1142-intake-packet.json"),
        artifact("modules", "e/1152/modules/candidate-document-material-request-modules.json"),
        artifact("request-items", "e/1152/material/candidate-document-material-request-items.json"),
        artifact(
            "acceptance-checks",
            "e/1152/material/candidate-document-material-acceptance-checks.json"),
        artifact(
            "disabled-boundaries",
            "e/1152/policy/candidate-document-material-request-boundaries.json"),
        artifact("route-evidence", "e/1152/routes/candidate-document-material-request-route.json"),
        artifact("closeout", "e/1152/closeout/candidate-document-material-request-closeout.md"));
  }

  static List<String> gates() {
    return IntStream.rangeClosed(
            1, OpsShardReadinessCandidateDocumentMaterialRequestSupport.EXPECTED_GATE_COUNT)
        .mapToObj(index -> "candidate-document-material-request-no-material-gate-" + index)
        .toList();
  }

  private static OpsShardReadinessCandidateDocumentMaterialRequestResponse.ModuleEntry module(
      int order, String code, String responsibility) {
    return new OpsShardReadinessCandidateDocumentMaterialRequestResponse.ModuleEntry(
        order, code, responsibility, "passed");
  }

  private static OpsShardReadinessCandidateDocumentMaterialRequestResponse.RequestItem item(
      String code, String sourceCode, String category, String requestedFields, String instruction) {
    return new OpsShardReadinessCandidateDocumentMaterialRequestResponse.RequestItem(
        code,
        sourceCode,
        category,
        requestedFields,
        instruction,
        "external material reviewer",
        "passed");
  }

  private static OpsShardReadinessCandidateDocumentMaterialRequestResponse.Artifact artifact(
      String code, String reference) {
    return new OpsShardReadinessCandidateDocumentMaterialRequestResponse.Artifact(
        code, reference, "material request evidence only", "passed");
  }
}
