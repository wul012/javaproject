package com.codexdemo.orderplatform.ops;

import java.util.List;

final class ReleaseApprovalSandboxEndpointCredentialResolverBoundaryCatalog {

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
}
