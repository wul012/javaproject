package com.codexdemo.orderplatform.ops;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

final class OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckCatalog {

    private static final int[] SOURCE_GROUP_SIZES = {3, 2, 3, 2, 3, 2, 3, 2, 3, 2};
    private static final List<CheckpointSpec> CHECKPOINT_SPECS = List.of(
            spec("material-source-package-submission-checkpoint", "material-source-package",
                    "source package, material index", "source package, material index"),
            spec("reviewer-identity-submission-checkpoint", "reviewer-identity",
                    "reviewer identity, review timestamp", "reviewer identity, review timestamp"),
            spec("document-origin-submission-checkpoint", "document-origin",
                    "source uri, origin attestation", "source uri, origin attestation"),
            spec("digest-canonical-body-submission-checkpoint", "digest-canonical-body",
                    "digest, canonical body", "digest, canonical body"),
            spec("field-table-submission-checkpoint", "field-table",
                    "field table, field coverage", "field table, field coverage"),
            spec("comparison-binding-submission-checkpoint", "comparison-binding",
                    "comparison binding, lineage key", "comparison binding, lineage key"),
            spec("signature-attestation-submission-checkpoint", "signature-attestation",
                    "signature attestation, signer identity", "signature attestation, signer identity"),
            spec("redaction-secret-boundary-submission-checkpoint", "redaction-secret-boundary",
                    "redaction log, secret boundary", "redaction log, secret boundary"),
            spec("runtime-import-freeze-submission-checkpoint", "runtime-import-freeze",
                    "runtime freeze, import freeze", "runtime freeze, import freeze"),
            spec("closeout-archive-submission-checkpoint", "closeout-archive",
                    "archive index, absence attestation", "archive index, absence attestation")
    );

    private OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckCatalog() {
    }

    static List<OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckResponse.ModuleEntry> modules() {
        return List.of(
                module(209, "material-submission-precheck-types",
                        "defines submission checkpoint and validator records"),
                module(210, "material-submission-precheck-catalog",
                        "groups request items and acceptance checks into checkpoints"),
                module(211, "material-submission-precheck-builder",
                        "assembles the read-only response from Java v1152"),
                module(212, "material-submission-precheck-artifacts",
                        "names archive references without accepting submitted material"),
                module(213, "material-submission-precheck-route",
                        "exposes the route and no-submission closeout evidence")
        );
    }

    static List<OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckResponse.SubmissionCheckpoint> checkpoints(
            OpsShardReadinessCandidateDocumentMaterialRequestResponse sourceRequest
    ) {
        List<OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckResponse.SubmissionCheckpoint> checkpoints =
                new ArrayList<>();
        int requestOffset = 0;
        int checkOffset = 0;
        for (int index = 0; index < CHECKPOINT_SPECS.size(); index++) {
            var spec = CHECKPOINT_SPECS.get(index);
            int groupSize = SOURCE_GROUP_SIZES[index];
            var sourceRequestCodes = sourceRequest.requestItems()
                    .subList(requestOffset, requestOffset + groupSize)
                    .stream()
                    .map(OpsShardReadinessCandidateDocumentMaterialRequestResponse.RequestItem::code)
                    .toList();
            var sourceAcceptanceCheckCodes = sourceRequest.acceptanceChecks()
                    .subList(checkOffset, checkOffset + groupSize)
                    .stream()
                    .map(OpsShardReadinessCandidateDocumentMaterialRequestResponse.AcceptanceCheck::code)
                    .toList();
            checkpoints.add(checkpoint(
                    index + 1,
                    spec,
                    sourceRequestCodes,
                    sourceAcceptanceCheckCodes));
            requestOffset += groupSize;
            checkOffset += groupSize;
        }
        return List.copyOf(checkpoints);
    }

    static List<OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckResponse.Validator> validators(
            List<OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckResponse.SubmissionCheckpoint> checkpoints
    ) {
        return checkpoints.stream()
                .map(checkpoint -> new OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckResponse.Validator(
                        checkpoint.code() + "-validator",
                        checkpoint.code(),
                        "reject-material-submission-precheck-" + checkpoint.code(),
                        "Reject material submission until " + checkpoint.submissionMaterialFields()
                                + " satisfy " + checkpoint.precheck(),
                        "fail-closed",
                        "passed"))
                .toList();
    }

    static List<OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckResponse.Artifact> artifacts() {
        return List.of(
                artifact("source-node-plan", "e/1162/source/node-v1456-material-submission-precheck-plan.md"),
                artifact("source-material-request", "e/1162/source/java-v1152-material-request.json"),
                artifact("modules", "e/1162/modules/candidate-document-material-submission-precheck-modules.json"),
                artifact("checkpoints", "e/1162/material/candidate-document-material-submission-checkpoints.json"),
                artifact("validators", "e/1162/material/candidate-document-material-submission-validators.json"),
                artifact("disabled-boundaries",
                        "e/1162/policy/candidate-document-material-submission-precheck-boundaries.json"),
                artifact("route-evidence",
                        "e/1162/routes/candidate-document-material-submission-precheck-route.json"),
                artifact("closeout", "e/1162/closeout/candidate-document-material-submission-precheck-closeout.md")
        );
    }

    static List<String> gates() {
        return IntStream.rangeClosed(
                        1,
                        OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckSupport.EXPECTED_GATE_COUNT)
                .mapToObj(index -> "candidate-document-material-submission-precheck-no-material-gate-" + index)
                .toList();
    }

    private static OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckResponse.ModuleEntry module(
            int order,
            String code,
            String responsibility
    ) {
        return new OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckResponse.ModuleEntry(
                order,
                code,
                responsibility,
                "passed"
        );
    }

    private static CheckpointSpec spec(
            String code,
            String category,
            String requiredMaterialFields,
            String submissionMaterialFields
    ) {
        return new CheckpointSpec(code, category, requiredMaterialFields, submissionMaterialFields);
    }

    private static OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckResponse.SubmissionCheckpoint checkpoint(
            int order,
            CheckpointSpec spec,
            List<String> sourceRequestCodes,
            List<String> sourceAcceptanceCheckCodes
    ) {
        return new OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckResponse.SubmissionCheckpoint(
                order,
                spec.code(),
                spec.category(),
                List.copyOf(sourceRequestCodes),
                List.copyOf(sourceAcceptanceCheckCodes),
                spec.requiredMaterialFields(),
                spec.submissionMaterialFields(),
                "precheck " + spec.category() + " shape before material submission is accepted",
                "external material reviewer",
                "passed"
        );
    }

    private static OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckResponse.Artifact artifact(
            String code,
            String reference
    ) {
        return new OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckResponse.Artifact(
                code,
                reference,
                "material submission precheck evidence only",
                "passed"
        );
    }

    private record CheckpointSpec(
            String code,
            String category,
            String requiredMaterialFields,
            String submissionMaterialFields
    ) {
    }
}
