package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessCandidateDocumentSubmissionCheckpointCatalog {

    private OpsShardReadinessCandidateDocumentSubmissionCheckpointCatalog() {
    }

    static List<OpsShardReadinessCandidateDocumentSubmissionPrecheckResponse.Checkpoint> checkpoints(
            OpsShardReadinessCandidateDocumentRequestPackageResponse sourcePackage,
            OpsShardReadinessCandidateDocumentHandoffResponse sourceHandoff
    ) {
        var requestCheckpoints = sourcePackage.requestItems().stream()
                .map(item -> checkpoint(
                        "request-" + item.code(),
                        item.code(),
                        "request-item",
                        "Precheck submitted document against request item: " + item.instruction()))
                .toList();
        var consumerCheckpoints = sourceHandoff.consumerRules().stream()
                .map(rule -> checkpoint(
                        "consumer-" + rule.code(),
                        rule.code(),
                        "consumer-rule",
                        "Precheck submitted document against handoff rule: " + rule.rule()))
                .toList();
        return java.util.stream.Stream.concat(requestCheckpoints.stream(), consumerCheckpoints.stream()).toList();
    }

    private static OpsShardReadinessCandidateDocumentSubmissionPrecheckResponse.Checkpoint checkpoint(
            String code,
            String sourceCode,
            String category,
            String instruction
    ) {
        return new OpsShardReadinessCandidateDocumentSubmissionPrecheckResponse.Checkpoint(
                code,
                sourceCode,
                category,
                instruction,
                "candidate document submission precheck owner",
                "passed"
        );
    }
}
