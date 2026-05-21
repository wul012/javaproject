package com.codexdemo.orderplatform.ops;

import java.util.List;

final class ReleaseApprovalSandboxEndpointCredentialResolverBoundaryCatalog {

    record ApprovalRequiredImplementationTemplate(
            String owner,
            List<String> requiredArtifacts,
            String javaV116EchoHint,
            String miniKvV122ReceiptHint,
            String nodeV282VerificationHint,
            List<String> prohibitedRuntimeActions
    ) {
    }

    record ImplementationPlanInterfaceBoundaryTemplate(
            String code,
            String sourceBoundary,
            String title,
            String owner,
            List<String> allowedInputs,
            List<String> allowedOutputs,
            List<String> prohibitedActions,
            List<String> requiredArtifacts,
            String verificationRule
    ) {
    }

    record ImplementationPlanUpstreamEchoRequirementTemplate(
            String id,
            String project,
            String expectedVersion,
            String requirement
    ) {
    }

    private static final List<String> BOUNDARY_CODES = List.of(
            "PLAN_DOCUMENT",
            "CREDENTIAL_HANDLE",
            "ENDPOINT_HANDLE",
            "DISABLED_SECRET_PROVIDER_STUB",
            "OPERATOR_APPROVAL",
            "ROLLBACK_BOUNDARY",
            "REDACTION_POLICY",
            "EXTERNAL_REQUEST_SIMULATION",
            "SCHEMA_MIGRATION_POLICY",
            "AUDIT_LEDGER_WRITE_POLICY"
    );

    private static final List<String> REQUIREMENT_CODES = List.of(
            "REAL_RESOLVER_PRE_IMPLEMENTATION_PLAN_MISSING",
            "CREDENTIAL_HANDLE_BOUNDARY_MISSING",
            "ENDPOINT_HANDLE_BOUNDARY_MISSING",
            "SECRET_PROVIDER_STUB_MISSING",
            "OPERATOR_APPROVAL_BOUNDARY_MISSING",
            "ROLLBACK_BOUNDARY_MISSING",
            "REDACTION_POLICY_MISSING",
            "EXTERNAL_REQUEST_SIMULATION_PLAN_MISSING",
            "SCHEMA_MIGRATION_POLICY_MISSING",
            "AUDIT_LEDGER_WRITE_POLICY_MISSING"
    );

    private static final List<String> CANDIDATE_READY_BOUNDARY_CODES = List.of(
            "PLAN_DOCUMENT",
            "DISABLED_SECRET_PROVIDER_STUB",
            "REDACTION_POLICY",
            "EXTERNAL_REQUEST_SIMULATION"
    );

    private static final List<String> APPROVAL_REQUIRED_BOUNDARY_CODES = List.of(
            "CREDENTIAL_HANDLE",
            "ENDPOINT_HANDLE",
            "OPERATOR_APPROVAL",
            "ROLLBACK_BOUNDARY",
            "SCHEMA_MIGRATION_POLICY",
            "AUDIT_LEDGER_WRITE_POLICY"
    );

    private static final List<ImplementationPlanInterfaceBoundaryTemplate>
            IMPLEMENTATION_PLAN_INTERFACE_BOUNDARY_TEMPLATES = List.of(
                    new ImplementationPlanInterfaceBoundaryTemplate(
                            "CONFIG_HANDLE_CONTRACT",
                            "PLAN_DOCUMENT",
                            "Config handle contract",
                            "node",
                            List.of(
                                    "ORDEROPS_MANAGED_AUDIT_RESOLVER_CONFIG_HANDLE",
                                    "ORDEROPS_MANAGED_AUDIT_RESOLVER_POLICY_HANDLE"
                            ),
                            List.of("configHandle", "policyHandle", "reviewStatus"),
                            List.of(
                                    "read-secret-env-value",
                                    "render-secret-env-value",
                                    "instantiate-runtime-client"
                            ),
                            List.of(
                                    "config-handle-review-id",
                                    "resolver-policy-handle-review-id",
                                    "config-redaction-contract"
                            ),
                            "Only named handles may appear in profile output; no raw config values or external client objects are created."
                    ),
                    new ImplementationPlanInterfaceBoundaryTemplate(
                            "CREDENTIAL_HANDLE_CONTRACT",
                            "CREDENTIAL_HANDLE",
                            "Credential handle contract",
                            "security",
                            List.of("credentialHandle", "credentialReviewStatus"),
                            List.of("credentialHandle", "credentialReviewStatus", "credentialValuePresent=false"),
                            List.of("read-credential-value", "store-credential-value", "render-credential-value"),
                            List.of(
                                    "credential-handle-review-id",
                                    "credential-value-redaction-contract",
                                    "operator-visible-secret-value-prohibition"
                            ),
                            "Profiles may reference credential handles only; credential values stay outside Node, Java, and mini-kv."
                    ),
                    new ImplementationPlanInterfaceBoundaryTemplate(
                            "ENDPOINT_HANDLE_CONTRACT",
                            "ENDPOINT_HANDLE",
                            "Endpoint handle contract",
                            "security",
                            List.of("endpointHandle", "allowlistReviewStatus"),
                            List.of("endpointHandle", "allowlistReviewStatus", "rawEndpointUrlPresent=false"),
                            List.of(
                                    "parse-raw-endpoint-url",
                                    "render-raw-endpoint-url",
                                    "dial-managed-audit-endpoint"
                            ),
                            List.of(
                                    "endpoint-handle-review-id",
                                    "allowlist-review-status",
                                    "raw-endpoint-redaction-contract"
                            ),
                            "Endpoint evidence may name handles and review status only; raw URLs stay out of logs, digests, and Markdown."
                    ),
                    new ImplementationPlanInterfaceBoundaryTemplate(
                            "APPROVAL_ARTIFACT_CONTRACT",
                            "OPERATOR_APPROVAL",
                            "Approval artifact contract",
                            "operator",
                            List.of("operatorIdentityBinding", "approvalCorrelationId", "manualWindowMarker"),
                            List.of("approvalArtifactDigest", "approvalState", "manualWindowStatus"),
                            List.of(
                                    "auto-approve-operation",
                                    "execute-without-operator-marker",
                                    "write-approval-ledger"
                            ),
                            List.of(
                                    "operator-identity-binding",
                                    "approval-correlation-marker",
                                    "manual-window-open-marker"
                            ),
                            "A later fake harness may only read approval artifacts; real ledger writes stay blocked until a separate write gate."
                    ),
                    new ImplementationPlanInterfaceBoundaryTemplate(
                            "FAILURE_TAXONOMY_CONTRACT",
                            "EXTERNAL_REQUEST_SIMULATION",
                            "Failure taxonomy contract",
                            "node",
                            List.of("simulatedFailureClass", "dryRunAdapterResult", "blockedReason"),
                            List.of("failureClass", "operatorVisibleReason", "retryDisposition"),
                            List.of(
                                    "send-external-request",
                                    "connect-managed-audit",
                                    "mask-unclassified-error"
                            ),
                            List.of(
                                    "failure-taxonomy-id",
                                    "operator-visible-failure-map",
                                    "retry-policy-review-id"
                            ),
                            "Future fake harness errors must be classified without contacting managed audit or exposing secret/endpoint material."
                    ),
                    new ImplementationPlanInterfaceBoundaryTemplate(
                            "ROLLBACK_GUARD_CONTRACT",
                            "ROLLBACK_BOUNDARY",
                            "Rollback guard contract",
                            "release-manager",
                            List.of(
                                    "rollbackAbortMarker",
                                    "restorePointReviewId",
                                    "manualRollbackRunbookReference"
                            ),
                            List.of("rollbackGuardState", "abortRequired=true", "executionAllowed=false"),
                            List.of(
                                    "execute-rollback",
                                    "deploy-resolver-without-abort-marker",
                                    "auto-start-upstream"
                            ),
                            List.of(
                                    "rollback-abort-marker",
                                    "restore-point-review-id",
                                    "manual-rollback-runbook-reference"
                            ),
                            "Resolver implementation remains blocked unless rollback guard evidence exists; this plan executes no rollback."
                    ),
                    new ImplementationPlanInterfaceBoundaryTemplate(
                            "TEST_ONLY_FAKE_HARNESS_CONTRACT",
                            "DISABLED_SECRET_PROVIDER_STUB",
                            "Test-only fake harness contract",
                            "node",
                            List.of("fakeCredentialHandle", "fakeEndpointHandle", "testOnlyHarnessToggle=false"),
                            List.of("fakeHarnessPlan", "sideEffectBoundary", "runtimeToggleState"),
                            List.of(
                                    "instantiate-real-secret-provider",
                                    "resolve-real-credential",
                                    "send-real-http-request"
                            ),
                            List.of(
                                    "test-only-fake-harness-plan-id",
                                    "fake-harness-disabled-toggle",
                                    "fake-harness-side-effect-contract"
                            ),
                            "Node v285 may define a disabled fake harness precheck only after Java v121, mini-kv v126, and Node v284 align."
                    )
            );

    private static final List<ImplementationPlanUpstreamEchoRequirementTemplate>
            JAVA_V121_IMPLEMENTATION_PLAN_ECHO_REQUIREMENT_TEMPLATES = List.of(
                    new ImplementationPlanUpstreamEchoRequirementTemplate(
                            "java-v121-consumes-node-v283-plan",
                            "java",
                            "Java v121",
                            "Java v121 must identify Node v283 planDigest and planVersion without deriving credential values."
                    ),
                    new ImplementationPlanUpstreamEchoRequirementTemplate(
                            "java-v121-approval-artifact-boundary",
                            "java",
                            "Java v121",
                            "Java v121 must describe required operator approval and ledger policy artifacts without writing approval ledger state."
                    ),
                    new ImplementationPlanUpstreamEchoRequirementTemplate(
                            "java-v121-schema-migration-boundary",
                            "java",
                            "Java v121",
                            "Java v121 must keep schema migration review-only and prove no SQL execution."
                    ),
                    new ImplementationPlanUpstreamEchoRequirementTemplate(
                            "java-v121-failure-taxonomy-echo",
                            "java",
                            "Java v121",
                            "Java v121 must echo failure taxonomy expectations for future Node v284 verification."
                    )
            );

    private static final List<ImplementationPlanUpstreamEchoRequirementTemplate>
            MINI_KV_V126_IMPLEMENTATION_PLAN_RECEIPT_REQUIREMENT_TEMPLATES = List.of(
                    new ImplementationPlanUpstreamEchoRequirementTemplate(
                            "mini-kv-v126-consumes-node-v283-plan",
                            "mini-kv",
                            "mini-kv v126",
                            "mini-kv v126 must identify Node v283 planDigest and remain non-participating."
                    ),
                    new ImplementationPlanUpstreamEchoRequirementTemplate(
                            "mini-kv-v126-no-storage-backend",
                            "mini-kv",
                            "mini-kv v126",
                            "mini-kv v126 must prove it is not a managed audit storage backend and not authoritative for audit/order state."
                    ),
                    new ImplementationPlanUpstreamEchoRequirementTemplate(
                            "mini-kv-v126-no-secret-or-endpoint",
                            "mini-kv",
                            "mini-kv v126",
                            "mini-kv v126 must prove no credential resolver, no secret provider, and no raw endpoint parser."
                    ),
                    new ImplementationPlanUpstreamEchoRequirementTemplate(
                            "mini-kv-v126-no-write-command",
                            "mini-kv",
                            "mini-kv v126",
                            "mini-kv v126 must keep write/admin commands out of this plan echo receipt."
                    )
            );

    private ReleaseApprovalSandboxEndpointCredentialResolverBoundaryCatalog() {
    }

    static List<String> boundaryCodes() {
        return BOUNDARY_CODES;
    }

    static List<String> requirementCodes() {
        return REQUIREMENT_CODES;
    }

    static String requirementCodeFor(String code) {
        int index = BOUNDARY_CODES.indexOf(code);
        if (index < 0) {
            throw new IllegalArgumentException("Unknown boundary code: " + code);
        }
        return REQUIREMENT_CODES.get(index);
    }

    static List<String> candidateReadyBoundaryCodes() {
        return CANDIDATE_READY_BOUNDARY_CODES;
    }

    static List<String> approvalRequiredBoundaryCodes() {
        return APPROVAL_REQUIRED_BOUNDARY_CODES;
    }

    static List<String> approvalRequiredRequirementCodes() {
        return APPROVAL_REQUIRED_BOUNDARY_CODES.stream()
                .map(ReleaseApprovalSandboxEndpointCredentialResolverBoundaryCatalog::requirementCodeFor)
                .toList();
    }

    static List<ImplementationPlanInterfaceBoundaryTemplate> implementationPlanInterfaceBoundaryTemplates() {
        return IMPLEMENTATION_PLAN_INTERFACE_BOUNDARY_TEMPLATES;
    }

    static List<ImplementationPlanUpstreamEchoRequirementTemplate>
    javaV121ImplementationPlanEchoRequirementTemplates() {
        return JAVA_V121_IMPLEMENTATION_PLAN_ECHO_REQUIREMENT_TEMPLATES;
    }

    static List<ImplementationPlanUpstreamEchoRequirementTemplate>
    miniKvV126ImplementationPlanReceiptRequirementTemplates() {
        return MINI_KV_V126_IMPLEMENTATION_PLAN_RECEIPT_REQUIREMENT_TEMPLATES;
    }

    static String candidateRuleFor(String code) {
        if (CANDIDATE_READY_BOUNDARY_CODES.contains(code)) {
            return code + " may be represented in the disabled interface or fake wiring review only.";
        }
        return code + " remains approval-required and cannot move into runtime behavior in v273.";
    }

    static String ownerFor(String code) {
        return switch (code) {
            case "PLAN_DOCUMENT", "ROLLBACK_BOUNDARY", "SCHEMA_MIGRATION_POLICY", "AUDIT_LEDGER_WRITE_POLICY" ->
                    "release-manager";
            case "CREDENTIAL_HANDLE", "ENDPOINT_HANDLE", "REDACTION_POLICY" -> "security";
            case "OPERATOR_APPROVAL" -> "operator";
            case "DISABLED_SECRET_PROVIDER_STUB", "EXTERNAL_REQUEST_SIMULATION" -> "node";
            default -> throw new IllegalArgumentException("Unknown boundary code: " + code);
        };
    }

    static List<String> prohibitedRuntimeActionsFor(String code) {
        return switch (code) {
            case "PLAN_DOCUMENT" -> List.of("implement-real-resolver", "open-managed-audit-connection");
            case "CREDENTIAL_HANDLE" -> List.of("read-credential-value", "store-credential-value");
            case "ENDPOINT_HANDLE" -> List.of("parse-raw-endpoint-url", "render-raw-endpoint-url");
            case "DISABLED_SECRET_PROVIDER_STUB" -> List.of("instantiate-secret-provider-runtime", "load-secret-value");
            case "OPERATOR_APPROVAL" -> List.of("execute-without-operator-marker", "auto-approve-operation");
            case "ROLLBACK_BOUNDARY" -> List.of("execute-rollback", "deploy-resolver-without-abort-marker");
            case "REDACTION_POLICY" -> List.of("log-secret-material", "log-raw-endpoint");
            case "EXTERNAL_REQUEST_SIMULATION" -> List.of("send-external-request", "connect-managed-audit");
            case "SCHEMA_MIGRATION_POLICY" -> List.of("execute-schema-migration", "execute-sql");
            case "AUDIT_LEDGER_WRITE_POLICY" -> List.of("write-approval-ledger", "write-managed-audit-state");
            default -> throw new IllegalArgumentException("Unknown boundary code: " + code);
        };
    }

    static String approvalRequiredEvidenceAllowedFor(String code) {
        return switch (code) {
            case "CREDENTIAL_HANDLE", "ENDPOINT_HANDLE", "OPERATOR_APPROVAL", "ROLLBACK_BOUNDARY",
                    "SCHEMA_MIGRATION_POLICY", "AUDIT_LEDGER_WRITE_POLICY" ->
                    "approval-required-read-only-evidence";
            default -> throw new IllegalArgumentException("Unknown approval-required boundary code: " + code);
        };
    }

    static String approvalReasonFor(String code) {
        return switch (code) {
            case "CREDENTIAL_HANDLE" ->
                    "Credential handle may be echoed only as an identifier; the credential value remains unread.";
            case "ENDPOINT_HANDLE" ->
                    "Endpoint handle may be echoed only as an identifier; the raw endpoint URL remains unparsed.";
            case "OPERATOR_APPROVAL" ->
                    "Operator approval must be supplied by a human-reviewed marker before resolver execution.";
            case "ROLLBACK_BOUNDARY" ->
                    "Rollback handling stays outside the disabled candidate and cannot execute from this receipt.";
            case "SCHEMA_MIGRATION_POLICY" ->
                    "Schema migration policy requires approval and cannot execute SQL from this receipt.";
            case "AUDIT_LEDGER_WRITE_POLICY" ->
                    "Audit ledger writes require approval and cannot be produced by this read-only evidence.";
            default -> throw new IllegalArgumentException("Unknown approval-required boundary code: " + code);
        };
    }

    static ApprovalRequiredImplementationTemplate approvalRequiredImplementationTemplateFor(String code) {
        return switch (code) {
            case "CREDENTIAL_HANDLE" -> new ApprovalRequiredImplementationTemplate(
                    "security",
                    List.of(
                            "credential-handle-review-id",
                            "credential-value-redaction-contract",
                            "operator-visible-secret-value-prohibition"
                    ),
                    "Echo credential handle review id without credential value fields.",
                    "Confirm no credential value load/store/include behavior.",
                    "Verify handle-only evidence and value-redaction invariants.",
                    List.of(
                            "read-credential-value",
                            "store-credential-value",
                            "render-credential-value"
                    )
            );
            case "ENDPOINT_HANDLE" -> new ApprovalRequiredImplementationTemplate(
                    "security",
                    List.of(
                            "endpoint-handle-review-id",
                            "allowlist-review-status",
                            "raw-endpoint-redaction-contract"
                    ),
                    "Echo endpoint handle and allowlist review status without raw URL.",
                    "Confirm no raw endpoint parse/include/connect behavior.",
                    "Verify handle-only endpoint evidence and no raw URL shape drift.",
                    List.of(
                            "parse-raw-endpoint-url",
                            "render-raw-endpoint-url",
                            "connect-managed-audit"
                    )
            );
            case "OPERATOR_APPROVAL" -> new ApprovalRequiredImplementationTemplate(
                    "operator",
                    List.of(
                            "operator-identity-binding",
                            "approval-correlation-marker",
                            "manual-window-open-marker"
                    ),
                    "Echo operator approval marker and manual-window evidence without executing ledger writes.",
                    "Confirm no auto-start and no approval side effects.",
                    "Verify operator marker completeness before any later dry-run shell.",
                    List.of(
                            "execute-without-operator-marker",
                            "auto-approve-operation",
                            "auto-start-upstream"
                    )
            );
            case "ROLLBACK_BOUNDARY" -> new ApprovalRequiredImplementationTemplate(
                    "release-manager",
                    List.of(
                            "rollback-abort-marker",
                            "restore-point-review-id",
                            "manual-rollback-runbook-reference"
                    ),
                    "Echo rollback abort marker and restore review id without executing rollback.",
                    "Confirm no LOAD/RESTORE/COMPACT and no authority over rollback state.",
                    "Verify rollback guard evidence stays separate from execution.",
                    List.of(
                            "execute-rollback",
                            "deploy-resolver-without-abort-marker",
                            "write-production-record"
                    )
            );
            case "SCHEMA_MIGRATION_POLICY" -> new ApprovalRequiredImplementationTemplate(
                    "release-manager",
                    List.of(
                            "schema-migration-rehearsal-id",
                            "migration-review-status",
                            "sql-execution-prohibition-marker"
                    ),
                    "Echo schema migration rehearsal id without executing SQL.",
                    "Confirm no admin command or schema/storage mutation participates.",
                    "Verify schema migration remains review-only.",
                    List.of(
                            "execute-schema-migration",
                            "execute-sql",
                            "mutate-managed-audit-schema"
                    )
            );
            case "AUDIT_LEDGER_WRITE_POLICY" -> new ApprovalRequiredImplementationTemplate(
                    "node",
                    List.of(
                            "approval-ledger-write-policy-id",
                            "audit-store-write-prohibition-marker",
                            "write-path-owner-review"
                    ),
                    "Echo ledger write policy id without writing approval ledger.",
                    "Confirm no storage/backend/write participation.",
                    "Verify all write paths stay blocked until an explicit later plan.",
                    List.of(
                            "write-approval-ledger",
                            "write-managed-audit-state",
                            "write-storage"
                    )
            );
            default -> throw new IllegalArgumentException("Unknown approval-required boundary code: " + code);
        };
    }
}
