package com.codexdemo.orderplatform.ops;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessCandidateDocumentMaterialRequestSupport {

    static final String PROJECT = "advanced-order-platform";
    static final String SOURCE_PLAN = "Node v1446";
    static final String SOURCE_NODE_INTAKE_PACKET_VERSION = "Node v1421";
    static final String MATERIAL_REQUEST_STATE =
            "waiting-for-external-reviewed-real-candidate-document-material";
    static final int EXPECTED_MODULE_COUNT = 5;
    static final int EXPECTED_REQUEST_ITEM_COUNT = 25;
    static final int EXPECTED_ACCEPTANCE_CHECK_COUNT = 25;
    static final int EXPECTED_SOURCE_SLOT_COUNT = 10;
    static final int EXPECTED_SOURCE_GUARD_COUNT = 10;
    static final int EXPECTED_REQUESTED_MATERIAL_FIELD_COUNT = 20;
    static final int EXPECTED_ARTIFACT_COUNT = 8;
    static final int EXPECTED_GATE_COUNT = 40;

    private OpsShardReadinessCandidateDocumentMaterialRequestSupport() {
    }

    static OpsShardReadinessCandidateDocumentMaterialRequestResponse response(
            String version,
            String endpoint,
            String profile,
            OpsShardReadinessCandidateDocumentIntakePacketResponse sourcePacket,
            List<OpsShardReadinessCandidateDocumentMaterialRequestResponse.ModuleEntry> modules,
            List<OpsShardReadinessCandidateDocumentMaterialRequestResponse.RequestItem> requestItems,
            List<OpsShardReadinessCandidateDocumentMaterialRequestResponse.AcceptanceCheck> acceptanceChecks,
            List<OpsShardReadinessCandidateDocumentMaterialRequestResponse.Artifact> artifacts,
            List<String> gates,
            List<String> additionalChecks
    ) {
        var moduleCopy = List.copyOf(modules);
        var requestCopy = List.copyOf(requestItems);
        var acceptanceCopy = List.copyOf(acceptanceChecks);
        var artifactCopy = List.copyOf(artifacts);
        var gateCopy = List.copyOf(gates);
        int passedRequestItemCount = (int) requestCopy.stream()
                .filter(item -> "passed".equals(item.status()))
                .count();
        int passedAcceptanceCheckCount = (int) acceptanceCopy.stream()
                .filter(check -> "passed".equals(check.status()))
                .count();
        List<String> checks = new ArrayList<>();
        checks.add("candidate-document-material-request-source-plan-" + SOURCE_PLAN);
        checks.add("candidate-document-material-request-source-node-" + SOURCE_NODE_INTAKE_PACKET_VERSION);
        checks.add("candidate-document-material-request-source-java-intake-packet-" + sourcePacket.version());
        checks.add("candidate-document-material-request-module-count-" + moduleCopy.size());
        checks.add("candidate-document-material-request-item-count-" + requestCopy.size());
        checks.add("candidate-document-material-request-acceptance-check-count-" + acceptanceCopy.size());
        checks.add("candidate-document-material-request-source-slot-count-" + sourcePacket.intakeSlotCount());
        checks.add("candidate-document-material-request-source-guard-count-" + sourcePacket.intakeGuardCount());
        checks.add("candidate-document-material-request-field-count-" + EXPECTED_REQUESTED_MATERIAL_FIELD_COUNT);
        checks.add("candidate-document-material-request-artifact-count-" + artifactCopy.size());
        checks.add("candidate-document-material-request-gate-count-" + gateCopy.size());
        checks.add("candidate-document-material-request-zero-documents");
        checks.add("candidate-document-material-request-zero-payloads");
        checks.add("candidate-document-material-request-no-material-accepted");
        checks.add("candidate-document-material-request-import-disabled");
        checks.add("candidate-document-material-request-evaluation-disabled");
        checks.add("candidate-document-material-request-approval-disabled");
        checks.add("candidate-document-material-request-signature-capture-disabled");
        checks.add("candidate-document-material-request-runtime-disabled");
        checks.add("candidate-document-material-request-write-disabled");
        checks.add("candidate-document-material-request-sibling-mutation-disabled");
        checks.addAll(additionalChecks);

        return new OpsShardReadinessCandidateDocumentMaterialRequestResponse(
                PROJECT,
                version,
                true,
                false,
                true,
                SOURCE_PLAN,
                SOURCE_NODE_INTAKE_PACKET_VERSION,
                sourcePacket.version(),
                sourcePacket.endpoint(),
                MATERIAL_REQUEST_STATE,
                endpoint,
                profile,
                moduleCopy.size(),
                requestCopy.size(),
                passedRequestItemCount,
                acceptanceCopy.size(),
                passedAcceptanceCheckCount,
                sourcePacket.intakeSlotCount(),
                sourcePacket.intakeGuardCount(),
                EXPECTED_REQUESTED_MATERIAL_FIELD_COUNT,
                artifactCopy.size(),
                gateCopy.size(),
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                moduleCopy,
                requestCopy,
                acceptanceCopy,
                artifactCopy,
                gateCopy,
                List.copyOf(checks),
                isComplete(sourcePacket, moduleCopy, requestCopy, acceptanceCopy, artifactCopy, gateCopy)
                        && passedRequestItemCount == requestCopy.size()
                        && passedAcceptanceCheckCount == acceptanceCopy.size()
                        ? "passed"
                        : "blocked"
        );
    }

    private static boolean isComplete(
            OpsShardReadinessCandidateDocumentIntakePacketResponse sourcePacket,
            List<OpsShardReadinessCandidateDocumentMaterialRequestResponse.ModuleEntry> modules,
            List<OpsShardReadinessCandidateDocumentMaterialRequestResponse.RequestItem> requestItems,
            List<OpsShardReadinessCandidateDocumentMaterialRequestResponse.AcceptanceCheck> acceptanceChecks,
            List<OpsShardReadinessCandidateDocumentMaterialRequestResponse.Artifact> artifacts,
            List<String> gates
    ) {
        return modules.size() == EXPECTED_MODULE_COUNT
                && requestItems.size() == EXPECTED_REQUEST_ITEM_COUNT
                && acceptanceChecks.size() == EXPECTED_ACCEPTANCE_CHECK_COUNT
                && sourcePacket.intakeSlotCount() == EXPECTED_SOURCE_SLOT_COUNT
                && sourcePacket.intakeGuardCount() == EXPECTED_SOURCE_GUARD_COUNT
                && artifacts.size() == EXPECTED_ARTIFACT_COUNT
                && gates.size() == EXPECTED_GATE_COUNT;
    }
}
