package com.codexdemo.orderplatform.ops;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessCandidateDocumentRequestPackageSupport {

    static final String PROJECT = "advanced-order-platform";
    static final String SOURCE_PLAN = "Node v1386";
    static final String SOURCE_NODE_CANDIDATE_INTAKE_VERSION = "Node v1371";
    static final String SOURCE_JAVA_CANDIDATE_INTAKE_VERSION = "Java v1079";
    static final String REQUEST_PACKAGE_STATE = "waiting-for-reviewed-real-compared-package-evidence-candidate-document";
    static final int REQUESTED_CANDIDATE_FIELD_COUNT = 20;
    static final int GATE_COUNT = 38;

    private OpsShardReadinessCandidateDocumentRequestPackageSupport() {
    }

    static OpsShardReadinessCandidateDocumentRequestPackageResponse response(
            String version,
            String endpoint,
            String profile,
            List<OpsShardReadinessCandidateDocumentRequestPackageResponse.RequestItem> requestItems,
            List<OpsShardReadinessCandidateDocumentRequestPackageResponse.AcceptanceCheck> acceptanceChecks,
            List<String> gates,
            List<String> additionalChecks
    ) {
        var itemCopy = List.copyOf(requestItems);
        var checkCopy = List.copyOf(acceptanceChecks);
        var gateCopy = List.copyOf(gates);
        int passedItemCount = (int) itemCopy.stream().filter(item -> "passed".equals(item.status())).count();
        int passedCheckCount = (int) checkCopy.stream().filter(check -> "passed".equals(check.status())).count();
        List<String> checks = new ArrayList<>();
        checks.add("candidate-document-request-package-item-count-" + itemCopy.size());
        checks.add("candidate-document-request-package-acceptance-check-count-" + checkCopy.size());
        checks.add("candidate-document-request-package-requested-field-count-" + REQUESTED_CANDIDATE_FIELD_COUNT);
        checks.add("candidate-document-request-package-gate-count-" + gateCopy.size());
        checks.add("candidate-document-request-package-source-plan-" + SOURCE_PLAN);
        checks.add("candidate-document-request-package-source-node-" + SOURCE_NODE_CANDIDATE_INTAKE_VERSION);
        checks.add("candidate-document-request-package-source-java-" + SOURCE_JAVA_CANDIDATE_INTAKE_VERSION);
        checks.add("candidate-document-request-package-all-document-counts-zero");
        checks.add("candidate-document-request-package-no-payload-import");
        checks.add("candidate-document-request-package-no-evaluation");
        checks.add("candidate-document-request-package-no-approval-grant");
        checks.add("candidate-document-request-package-no-signed-approval-capture");
        checks.add("candidate-document-request-package-no-runtime");
        checks.add("candidate-document-request-package-no-write");
        checks.add("candidate-document-request-package-no-sibling-mutation");
        checks.addAll(additionalChecks);

        return new OpsShardReadinessCandidateDocumentRequestPackageResponse(
                PROJECT,
                version,
                true,
                false,
                true,
                SOURCE_PLAN,
                SOURCE_NODE_CANDIDATE_INTAKE_VERSION,
                SOURCE_JAVA_CANDIDATE_INTAKE_VERSION,
                REQUEST_PACKAGE_STATE,
                itemCopy.size(),
                passedItemCount,
                checkCopy.size(),
                passedCheckCount,
                REQUESTED_CANDIDATE_FIELD_COUNT,
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
                endpoint,
                profile,
                itemCopy,
                checkCopy,
                gateCopy,
                List.copyOf(checks),
                itemCopy.size() == 15 && passedItemCount == itemCopy.size()
                        && checkCopy.size() == 15 && passedCheckCount == checkCopy.size()
                        && gateCopy.size() == GATE_COUNT
                        ? "passed"
                        : "blocked"
        );
    }

    static OpsShardReadinessCandidateDocumentRequestPackageResponse.RequestItem item(
            String code,
            String sourceIntakeSlot,
            String requestedFields,
            String instruction,
            String owner,
            String sourceEndpoint
    ) {
        return new OpsShardReadinessCandidateDocumentRequestPackageResponse.RequestItem(
                code,
                sourceIntakeSlot,
                requestedFields,
                instruction,
                owner,
                sourceEndpoint,
                "passed"
        );
    }

    static OpsShardReadinessCandidateDocumentRequestPackageResponse.AcceptanceCheck check(
            String code,
            String category,
            String check,
            String rejectionCode
    ) {
        return new OpsShardReadinessCandidateDocumentRequestPackageResponse.AcceptanceCheck(
                code,
                category,
                check,
                rejectionCode,
                "fail-closed",
                "passed"
        );
    }
}
