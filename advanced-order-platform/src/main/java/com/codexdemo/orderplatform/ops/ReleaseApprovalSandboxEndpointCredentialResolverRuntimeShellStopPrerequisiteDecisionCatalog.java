package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellStopPrerequisiteDecisionEchoRecords
        .RehearsalRuntimeShellChainNoGoCondition;
import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellStopPrerequisiteDecisionEchoRecords
        .RehearsalRuntimeShellChainPrerequisite;
import java.util.List;

final class ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellStopPrerequisiteDecisionCatalog {

    private static final List<PrerequisiteTemplate> REQUIRED_PREREQUISITE_TEMPLATES = List.of(
            new PrerequisiteTemplate(
                    "operator-approval-artifact",
                    "Operator approval artifact",
                    "missing: no signed operator approval artifact has been produced for runtime shell implementation"
            ),
            new PrerequisiteTemplate(
                    "credential-handle-readiness",
                    "Credential handle readiness",
                    "missing: only credential handle/review status can be referenced; credential value reading remains forbidden"
            ),
            new PrerequisiteTemplate(
                    "raw-endpoint-allowlist-review",
                    "Raw endpoint allowlist review",
                    "missing: endpoint handle can be reviewed, but raw endpoint URL parsing/rendering remains forbidden"
            ),
            new PrerequisiteTemplate(
                    "no-network-test-fixture",
                    "No-network safety tests",
                    "missing: no test has proven runtime shell code cannot dial managed audit before explicit approval"
            ),
            new PrerequisiteTemplate(
                    "manual-abort-and-rollback-semantics",
                    "Manual abort and rollback semantics",
                    "missing: abort semantics are documented as required, but no executable runtime shell abort contract exists"
            ),
            new PrerequisiteTemplate(
                    "java-mini-kv-prerequisite-echo",
                    "Java/mini-kv prerequisite echo",
                    "missing: Java v141 and mini-kv v134 have not yet echoed this stop/prerequisite decision"
            )
    );

    private static final List<NoGoTemplate> NO_GO_TEMPLATES = List.of(
            new NoGoTemplate("RUNTIME_SHELL_IMPLEMENTATION_REQUESTED",
                    "Any next step asks Node to implement runtime shell code."),
            new NoGoTemplate("RUNTIME_SHELL_INVOCATION_REQUESTED",
                    "Any next step asks Node to invoke a runtime shell."),
            new NoGoTemplate("CREDENTIAL_VALUE_READ_REQUESTED",
                    "Any next step asks Node, Java, or mini-kv to read credential values."),
            new NoGoTemplate("RAW_ENDPOINT_URL_PARSE_REQUESTED",
                    "Any next step asks Node to parse or render a raw endpoint URL."),
            new NoGoTemplate("PROVIDER_CLIENT_INSTANTIATION_REQUESTED",
                    "Any next step asks Node to instantiate providers or resolver clients."),
            new NoGoTemplate("EXTERNAL_REQUEST_REQUESTED",
                    "Any next step asks Node to send HTTP/TCP to managed audit."),
            new NoGoTemplate("LEDGER_OR_SCHEMA_WRITE_REQUESTED",
                    "Any next step asks Java or Node to write ledger/schema state."),
            new NoGoTemplate("MINIKV_WRITE_OR_AUTHORITY_REQUESTED",
                    "Any next step asks mini-kv to run LOAD/COMPACT/RESTORE/SETNXEX or become authority.")
    );

    private static final List<String> PROOF_CLAIMS = List.of(
            "managedAuditSandboxEndpointCredentialResolverRuntimeShellStopPrerequisiteDecisionEchoReceipt.consumedByNodeRuntimeShellChainStopPrerequisiteDecisionRecordState=runtime-shell-chain-stop-or-prerequisite-decision-record-ready",
            "managedAuditSandboxEndpointCredentialResolverRuntimeShellStopPrerequisiteDecisionEchoReceipt.decisionRecord.decision=require-explicit-approval-prerequisites-before-runtime-shell",
            "managedAuditSandboxEndpointCredentialResolverRuntimeShellStopPrerequisiteDecisionEchoReceipt.decisionRecord.prerequisiteCount=6",
            "managedAuditSandboxEndpointCredentialResolverRuntimeShellStopPrerequisiteDecisionEchoReceipt.decisionRecord.missingRuntimePrerequisiteCount=6",
            "managedAuditSandboxEndpointCredentialResolverRuntimeShellStopPrerequisiteDecisionEchoReceipt.decisionRecord.noGoConditionCount=8",
            "managedAuditSandboxEndpointCredentialResolverRuntimeShellStopPrerequisiteDecisionEchoReceipt.decisionRecord.allowsDisabledRuntimeShellImplementation=false",
            "managedAuditSandboxEndpointCredentialResolverRuntimeShellStopPrerequisiteDecisionEchoReceipt.decisionRecord.allowsMiniKvWriteOrAuthority=false",
            "managedAuditSandboxEndpointCredentialResolverRuntimeShellStopPrerequisiteDecisionEchoReceipt.readyForNodeV305StopPrerequisiteUpstreamEchoVerification=true"
    );

    private static final List<String> NODE_VERIFICATION_ACTIONS = List.of(
            "Compare managedAuditSandboxEndpointCredentialResolverRuntimeShellStopPrerequisiteDecisionEchoReceipt.consumedByNodeRuntimeShellChainStopPrerequisiteDecisionRecordProfile with Node v304",
            "Require managedAuditSandboxEndpointCredentialResolverRuntimeShellStopPrerequisiteDecisionEchoReceipt.decisionRecord.decision=require-explicit-approval-prerequisites-before-runtime-shell before Node v305",
            "Require managedAuditSandboxEndpointCredentialResolverRuntimeShellStopPrerequisiteDecisionEchoReceipt.decisionRecord.prerequisiteCount=6 before Node v305",
            "Require managedAuditSandboxEndpointCredentialResolverRuntimeShellStopPrerequisiteDecisionEchoReceipt.decisionRecord.noGoConditionCount=8 before Node v305",
            "Require managedAuditSandboxEndpointCredentialResolverRuntimeShellStopPrerequisiteDecisionEchoReceipt.decisionRecord.allowsParallelJavaV141MiniKvV134EchoRequest=true before Node v305",
            "Keep managedAuditSandboxEndpointCredentialResolverRuntimeShellStopPrerequisiteDecisionEchoReceipt.decisionRecord.allowsNodeV305BeforeUpstreamEcho=false",
            "Keep managedAuditSandboxEndpointCredentialResolverRuntimeShellStopPrerequisiteDecisionEchoReceipt.decisionRecord.allowsExternalRequest=false",
            "Keep managedAuditSandboxEndpointCredentialResolverRuntimeShellStopPrerequisiteDecisionEchoReceipt.decisionRecord.allowsMiniKvWriteOrAuthority=false"
    );

    private static final List<String> NODE_WARNING_CODES = List.of(
            "PREREQUISITE_DECISION_DOES_NOT_AUTHORIZE_RUNTIME"
    );

    private static final List<String> NODE_RECOMMENDATION_CODES = List.of(
            "RUN_JAVA_V141_AND_MINIKV_V134_IN_PARALLEL",
            "KEEP_RUNTIME_SHELL_BLOCKED"
    );

    private static final List<String> NEXT_REQUIRED_ECHO_VERSIONS = List.of(
            "mini-kv v134 runtime shell chain stop/prerequisite non-participation receipt",
            "Node v305 stop/prerequisite upstream echo verification"
    );

    private ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellStopPrerequisiteDecisionCatalog() {
    }

    static List<String> requiredPrerequisiteIds() {
        return REQUIRED_PREREQUISITE_TEMPLATES.stream()
                .map(PrerequisiteTemplate::id)
                .toList();
    }

    static List<RehearsalRuntimeShellChainPrerequisite> requiredPrerequisites() {
        return REQUIRED_PREREQUISITE_TEMPLATES.stream()
                .map(template -> new RehearsalRuntimeShellChainPrerequisite(
                        template.id(),
                        template.label(),
                        template.currentEvidence(),
                        "documented-missing",
                        true
                ))
                .toList();
    }

    static List<String> noGoConditionCodes() {
        return NO_GO_TEMPLATES.stream()
                .map(NoGoTemplate::code)
                .toList();
    }

    static List<RehearsalRuntimeShellChainNoGoCondition> noGoConditions() {
        return NO_GO_TEMPLATES.stream()
                .map(template -> new RehearsalRuntimeShellChainNoGoCondition(
                        template.code(),
                        template.condition(),
                        "pause-and-do-not-implement-runtime-shell"
                ))
                .toList();
    }

    static List<String> proofClaims() {
        return PROOF_CLAIMS;
    }

    static List<String> nodeVerificationActions() {
        return NODE_VERIFICATION_ACTIONS;
    }

    static List<String> nodeWarningCodes() {
        return NODE_WARNING_CODES;
    }

    static List<String> nodeRecommendationCodes() {
        return NODE_RECOMMENDATION_CODES;
    }

    static List<String> nextRequiredEchoVersions() {
        return NEXT_REQUIRED_ECHO_VERSIONS;
    }

    private record PrerequisiteTemplate(String id, String label, String currentEvidence) {
    }

    private record NoGoTemplate(String code, String condition) {
    }
}
