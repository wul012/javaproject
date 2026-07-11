package com.codexdemo.orderplatform.ops.maintenance.releaseapproval;

import java.util.ArrayList;
import java.util.List;

final class ReleaseApprovalManagedAuditSandboxConnectionPreflightEchoMarkerBuilder {

  private static final String MANUAL_WINDOW_FLAG_NAME =
      "ORDEROPS_MANAGED_AUDIT_MANUAL_SANDBOX_WINDOW_APPROVED";
  private static final String OWNER_APPROVAL_ARTIFACT_ID_FIELD =
      "ORDEROPS_MANAGED_AUDIT_OWNER_APPROVAL_ARTIFACT_ID";
  private static final String CREDENTIAL_HANDLE_NAME_FIELD =
      "ORDEROPS_MANAGED_AUDIT_SANDBOX_CREDENTIAL_HANDLE";
  private static final String SCHEMA_REHEARSAL_ID_FIELD =
      "ORDEROPS_MANAGED_AUDIT_SCHEMA_REHEARSAL_ID";
  private static final String ROLLBACK_PATH_ID_FIELD = "ORDEROPS_MANAGED_AUDIT_ROLLBACK_PATH_ID";
  private static final String MANUAL_ABORT_MARKER_FIELD = "ORDEROPS_MANAGED_AUDIT_MANUAL_ABORT";
  private static final int TIMEOUT_BUDGET_MS = 15000;

  private static final List<String> WARNING_DIGEST_WARNING_INPUT_NAMES =
      List.of("managedAuditSandboxConnectionPreflightEchoMarkerWarnings");

  private static final List<String> WARNING_DIGEST_BOUNDARY_INPUT_NAMES =
      List.of(
          "sandboxConnectionPreflightEchoMarkerDigest",
          "sandboxConnectionPreflightManualWindowOpenedByJava",
          "sandboxConnectionPreflightManualWindowOpenByDefault",
          "sandboxConnectionPreflightCredentialValueReadByJava",
          "sandboxConnectionPreflightSchemaMigrationSqlExecutedByJava",
          "sandboxConnectionPreflightExternalManagedAuditConnectionOpenedByJava",
          "sandboxConnectionPreflightNodeAutoStartAllowed");

  private static final List<String> PROOF_CLAIMS =
      List.of(
          "managedAuditSandboxConnectionPreflightEchoMarker.sandboxConnectionWindowBoundary.manualWindowOpenedByJava=false",
          "managedAuditSandboxConnectionPreflightEchoMarker.sandboxConnectionWindowBoundary.manualWindowOpenByDefault=false",
          "managedAuditSandboxConnectionPreflightEchoMarker.preflightFieldBoundary.allRequiredPreflightFieldsRecognizedByJava=true",
          "managedAuditSandboxConnectionPreflightEchoMarker.preflightFieldBoundary.preflightGateReadOnly=true",
          "managedAuditSandboxConnectionPreflightEchoMarker.credentialBoundary.credentialValueReadByJava=false",
          "managedAuditSandboxConnectionPreflightEchoMarker.schemaRehearsalBoundary.schemaMigrationSqlExecutedByJava=false",
          "managedAuditSandboxConnectionPreflightEchoMarker.rollbackPathBoundary.rollbackExecutionAllowedByJava=false",
          "managedAuditSandboxConnectionPreflightEchoMarker.javaExecutionBoundary.externalManagedAuditConnectionOpenedByJava=false",
          "managedAuditSandboxConnectionPreflightEchoMarker.javaExecutionBoundary.approvalLedgerWrittenByJava=false",
          "managedAuditSandboxConnectionPreflightEchoMarker.javaExecutionBoundary.sqlExecutedByJava=false");

  private static final List<String> NODE_VERIFICATION_ACTIONS =
      List.of(
          "Compare managedAuditSandboxConnectionPreflightEchoMarker.consumedByNodePreflightGateProfile with Node v230",
          "Require managedAuditSandboxConnectionPreflightEchoMarker.readyForNodeV231ManualSandboxConnectionPreflightVerification=true before Node v231",
          "Compare managedAuditSandboxConnectionPreflightEchoMarker.requiredPreflightFields with Node v230 preflightFields",
          "Keep managedAuditSandboxConnectionPreflightEchoMarker.sandboxConnectionWindowBoundary.manualWindowOpenedByJava=false",
          "Keep managedAuditSandboxConnectionPreflightEchoMarker.sandboxConnectionWindowBoundary.manualWindowOpenByDefault=false",
          "Keep managedAuditSandboxConnectionPreflightEchoMarker.credentialBoundary.credentialValueReadByJava=false",
          "Keep managedAuditSandboxConnectionPreflightEchoMarker.schemaRehearsalBoundary.schemaMigrationSqlExecutedByJava=false",
          "Keep managedAuditSandboxConnectionPreflightEchoMarker.javaExecutionBoundary.externalManagedAuditConnectionOpenedByJava=false");

  ReleaseApprovalRehearsalSandboxConnectionResponseRecords
          .RehearsalManagedAuditSandboxConnectionPreflightEchoMarker
      build(
          ReleaseApprovalRehearsalSandboxConnectionResponseRecords
                  .RehearsalManagedAuditSandboxConnectionOperatorHandoffMarker
              sandboxConnectionOperatorHandoffMarker) {
    boolean sourceMarkerAccepted = sourceMarkerAccepted(sandboxConnectionOperatorHandoffMarker);

    WindowFlags windowFlags = WindowFlags.nodeV230ReadOnlyManualWindow();
    PreflightFieldFlags preflightFieldFlags = PreflightFieldFlags.nodeV230RequiredFields();
    CredentialFlags credentialFlags = CredentialFlags.handleOnly();
    SchemaFlags schemaFlags = SchemaFlags.rehearsalIdOnly();
    RollbackFlags rollbackFlags = RollbackFlags.rollbackEvidenceOnly();
    JavaExecutionFlags javaExecutionFlags = JavaExecutionFlags.noExecution();

    ReleaseApprovalRehearsalSandboxConnectionResponseRecords
            .RehearsalSandboxConnectionPreflightWindowBoundary
        sandboxConnectionWindowBoundary = windowFlags.toBoundary();
    ReleaseApprovalRehearsalSandboxConnectionResponseRecords
            .RehearsalSandboxConnectionPreflightFieldBoundary
        preflightFieldBoundary = preflightFieldFlags.toBoundary();
    ReleaseApprovalRehearsalSandboxConnectionResponseRecords
            .RehearsalSandboxConnectionPreflightCredentialBoundary
        credentialBoundary = credentialFlags.toBoundary();
    ReleaseApprovalRehearsalSandboxConnectionResponseRecords
            .RehearsalSandboxConnectionPreflightSchemaBoundary
        schemaRehearsalBoundary = schemaFlags.toBoundary();
    ReleaseApprovalRehearsalSandboxConnectionResponseRecords
            .RehearsalSandboxConnectionPreflightRollbackBoundary
        rollbackPathBoundary = rollbackFlags.toBoundary();
    ReleaseApprovalRehearsalSandboxConnectionResponseRecords
            .RehearsalSandboxConnectionPreflightJavaExecutionBoundary
        javaExecutionBoundary = javaExecutionFlags.toBoundary();

    List<String> markerWarnings = new ArrayList<>();
    if (!sourceMarkerAccepted) {
      markerWarnings.add("NODE_V231_SOURCE_SANDBOX_CONNECTION_OPERATOR_HANDOFF_MARKER_NOT_READY");
    }

    boolean readyForNodeV231ManualSandboxConnectionPreflightVerification =
        sourceMarkerAccepted
            && sandboxConnectionWindowBoundary.manualWindowFlagRequired()
            && !sandboxConnectionWindowBoundary.manualWindowOpenByDefault()
            && !sandboxConnectionWindowBoundary.manualWindowOpenedByJava()
            && !sandboxConnectionWindowBoundary.connectionExecutionAllowed()
            && !sandboxConnectionWindowBoundary.nodeAutoStartAllowed()
            && !sandboxConnectionWindowBoundary.javaStartsManagedAuditService()
            && preflightFieldBoundary.allRequiredPreflightFieldsRecognizedByJava()
            && preflightFieldBoundary.preflightGateReadOnly()
            && !preflightFieldBoundary.gateCreatesConnectionCommand()
            && credentialBoundary.credentialHandleNameRecognizedByJava()
            && !credentialBoundary.credentialValueRequiredByJava()
            && !credentialBoundary.credentialValueReadByJava()
            && !credentialBoundary.credentialValueStoredByJava()
            && !credentialBoundary.productionCredentialAllowed()
            && schemaRehearsalBoundary.schemaRehearsalIdRequired()
            && !schemaRehearsalBoundary.schemaMigrationExecutionAllowed()
            && !schemaRehearsalBoundary.schemaMigrationSqlExecutedByJava()
            && !schemaRehearsalBoundary.schemaMigrationAppliedByJava()
            && rollbackPathBoundary.rollbackPathIdRequired()
            && rollbackPathBoundary.manualAbortMarkerRequired()
            && !rollbackPathBoundary.rollbackExecutionAllowedByJava()
            && !rollbackPathBoundary.restoreExecutionAllowedByJava()
            && !javaExecutionBoundary.approvalDecisionCreatedByJava()
            && !javaExecutionBoundary.approvalLedgerWrittenByJava()
            && !javaExecutionBoundary.approvalRecordPersistedByJava()
            && !javaExecutionBoundary.managedAuditStoreWrittenByJava()
            && !javaExecutionBoundary.externalManagedAuditConnectionOpenedByJava()
            && !javaExecutionBoundary.sqlExecutedByJava()
            && !javaExecutionBoundary.deploymentTriggeredByJava()
            && !javaExecutionBoundary.rollbackTriggeredByJava()
            && !javaExecutionBoundary.restoreExecutedByJava();

    String markerDigest =
        ReleaseApprovalDigestSupport.digest(
            List.of(
                ReleaseApprovalDigestSupport.line(
                    "markerVersion",
                    ReleaseApprovalContractConstants
                        .RELEASE_APPROVAL_REHEARSAL_MANAGED_AUDIT_SANDBOX_CONNECTION_PREFLIGHT_ECHO_MARKER_VERSION),
                ReleaseApprovalDigestSupport.line(
                    "sourceSandboxConnectionOperatorHandoffMarkerVersion",
                    sandboxConnectionOperatorHandoffMarker.markerVersion()),
                ReleaseApprovalDigestSupport.line(
                    "sourceSandboxConnectionOperatorHandoffSchemaVersion",
                    ReleaseApprovalContractConstants
                        .RELEASE_APPROVAL_REHEARSAL_MANAGED_AUDIT_SANDBOX_CONNECTION_OPERATOR_HANDOFF_MARKER_SCHEMA_VERSION),
                ReleaseApprovalDigestSupport.line(
                    "consumedByNodePreflightGateProfile",
                    ReleaseApprovalUpstreamContractConstants
                        .NODE_V230_MANUAL_SANDBOX_CONNECTION_PREFLIGHT_GATE_PROFILE),
                ReleaseApprovalDigestSupport.line(
                    "consumedByNodePreflightGateState",
                    ReleaseApprovalUpstreamContractConstants
                        .NODE_V230_MANUAL_SANDBOX_CONNECTION_PREFLIGHT_GATE_STATE),
                ReleaseApprovalDigestSupport.line("manualWindowFlagName", MANUAL_WINDOW_FLAG_NAME),
                ReleaseApprovalDigestSupport.line(
                    "ownerApprovalArtifactIdField", OWNER_APPROVAL_ARTIFACT_ID_FIELD),
                ReleaseApprovalDigestSupport.line(
                    "credentialHandleNameField", CREDENTIAL_HANDLE_NAME_FIELD),
                ReleaseApprovalDigestSupport.line(
                    "schemaRehearsalIdField", SCHEMA_REHEARSAL_ID_FIELD),
                ReleaseApprovalDigestSupport.line("rollbackPathIdField", ROLLBACK_PATH_ID_FIELD),
                ReleaseApprovalDigestSupport.line(
                    "manualAbortMarkerField", MANUAL_ABORT_MARKER_FIELD),
                ReleaseApprovalDigestSupport.line("timeoutBudgetMs", TIMEOUT_BUDGET_MS),
                ReleaseApprovalDigestSupport.line(
                    "manualWindowOpenByDefault",
                    sandboxConnectionWindowBoundary.manualWindowOpenByDefault()),
                ReleaseApprovalDigestSupport.line(
                    "manualWindowOpenedByJava",
                    sandboxConnectionWindowBoundary.manualWindowOpenedByJava()),
                ReleaseApprovalDigestSupport.line(
                    "credentialValueReadByJava", credentialBoundary.credentialValueReadByJava()),
                ReleaseApprovalDigestSupport.line(
                    "schemaMigrationSqlExecutedByJava",
                    schemaRehearsalBoundary.schemaMigrationSqlExecutedByJava()),
                ReleaseApprovalDigestSupport.line(
                    "externalManagedAuditConnectionOpenedByJava",
                    javaExecutionBoundary.externalManagedAuditConnectionOpenedByJava()),
                ReleaseApprovalDigestSupport.line(
                    "readyForNodeV231ManualSandboxConnectionPreflightVerification",
                    readyForNodeV231ManualSandboxConnectionPreflightVerification)));

    return new ReleaseApprovalRehearsalSandboxConnectionResponseRecords
        .RehearsalManagedAuditSandboxConnectionPreflightEchoMarker(
        ReleaseApprovalContractConstants
            .RELEASE_APPROVAL_REHEARSAL_MANAGED_AUDIT_SANDBOX_CONNECTION_PREFLIGHT_ECHO_MARKER_VERSION,
        sandboxConnectionOperatorHandoffMarker.markerVersion(),
        ReleaseApprovalContractConstants
            .RELEASE_APPROVAL_REHEARSAL_MANAGED_AUDIT_SANDBOX_CONNECTION_OPERATOR_HANDOFF_MARKER_SCHEMA_VERSION,
        ReleaseApprovalUpstreamContractConstants
            .NODE_V230_MANUAL_SANDBOX_CONNECTION_PREFLIGHT_GATE_VERSION,
        ReleaseApprovalUpstreamContractConstants
            .NODE_V230_MANUAL_SANDBOX_CONNECTION_PREFLIGHT_GATE_PROFILE,
        ReleaseApprovalUpstreamContractConstants
            .NODE_V230_MANUAL_SANDBOX_CONNECTION_PREFLIGHT_GATE_ENDPOINT,
        ReleaseApprovalUpstreamContractConstants
            .NODE_V230_MANUAL_SANDBOX_CONNECTION_PREFLIGHT_GATE_STATE,
        ReleaseApprovalUpstreamContractConstants
            .NODE_V231_MANUAL_SANDBOX_CONNECTION_PREFLIGHT_VERIFICATION_VERSION,
        ReleaseApprovalUpstreamContractConstants
            .NODE_V231_MANUAL_SANDBOX_CONNECTION_PREFLIGHT_VERIFICATION_PROFILE,
        true,
        sandboxConnectionWindowBoundary,
        preflightFieldBoundary,
        credentialBoundary,
        schemaRehearsalBoundary,
        rollbackPathBoundary,
        javaExecutionBoundary,
        readyForNodeV231ManualSandboxConnectionPreflightVerification,
        false,
        false,
        false,
        false,
        markerDigest,
        requiredPreflightFields(),
        forbiddenPreflightOperations(),
        nodeV231Prerequisites(),
        List.copyOf(markerWarnings),
        NODE_VERIFICATION_ACTIONS);
  }

  List<String> warningDigestWarningInputNames() {
    return WARNING_DIGEST_WARNING_INPUT_NAMES;
  }

  List<String> warningDigestBoundaryInputNames() {
    return WARNING_DIGEST_BOUNDARY_INPUT_NAMES;
  }

  List<String> proofClaims() {
    return PROOF_CLAIMS;
  }

  List<String> nodeVerificationActions() {
    return NODE_VERIFICATION_ACTIONS;
  }

  List<String> warningDigestWarningLines(
      ReleaseApprovalRehearsalSandboxConnectionResponseRecords
              .RehearsalManagedAuditSandboxConnectionPreflightEchoMarker
          marker) {
    return List.of(
        ReleaseApprovalDigestSupport.line(
            "managedAuditSandboxConnectionPreflightEchoMarkerWarnings", marker.markerWarnings()));
  }

  List<String> warningDigestBoundaryLines(
      ReleaseApprovalRehearsalSandboxConnectionResponseRecords
              .RehearsalManagedAuditSandboxConnectionPreflightEchoMarker
          marker) {
    return List.of(
        ReleaseApprovalDigestSupport.line(
            "sandboxConnectionPreflightEchoMarkerDigest", marker.markerDigest()),
        ReleaseApprovalDigestSupport.line(
            "sandboxConnectionPreflightManualWindowOpenedByJava",
            marker.sandboxConnectionWindowBoundary().manualWindowOpenedByJava()),
        ReleaseApprovalDigestSupport.line(
            "sandboxConnectionPreflightManualWindowOpenByDefault",
            marker.sandboxConnectionWindowBoundary().manualWindowOpenByDefault()),
        ReleaseApprovalDigestSupport.line(
            "sandboxConnectionPreflightCredentialValueReadByJava",
            marker.credentialBoundary().credentialValueReadByJava()),
        ReleaseApprovalDigestSupport.line(
            "sandboxConnectionPreflightSchemaMigrationSqlExecutedByJava",
            marker.schemaRehearsalBoundary().schemaMigrationSqlExecutedByJava()),
        ReleaseApprovalDigestSupport.line(
            "sandboxConnectionPreflightExternalManagedAuditConnectionOpenedByJava",
            marker.javaExecutionBoundary().externalManagedAuditConnectionOpenedByJava()),
        ReleaseApprovalDigestSupport.line(
            "sandboxConnectionPreflightNodeAutoStartAllowed",
            marker.sandboxConnectionWindowBoundary().nodeAutoStartAllowed()));
  }

  boolean noWriteCredentialConnectionSchemaRollbackOrServiceStartProved(
      ReleaseApprovalRehearsalSandboxConnectionResponseRecords
              .RehearsalManagedAuditSandboxConnectionPreflightEchoMarker
          marker) {
    return !marker.sandboxConnectionWindowBoundary().manualWindowOpenByDefault()
        && !marker.sandboxConnectionWindowBoundary().manualWindowOpenedByJava()
        && !marker.sandboxConnectionWindowBoundary().connectionExecutionAllowed()
        && !marker.sandboxConnectionWindowBoundary().nodeAutoStartAllowed()
        && !marker.sandboxConnectionWindowBoundary().javaStartsManagedAuditService()
        && !marker.preflightFieldBoundary().gateCreatesConnectionCommand()
        && !marker.credentialBoundary().credentialValueRequiredByJava()
        && !marker.credentialBoundary().credentialValueReadByJava()
        && !marker.credentialBoundary().credentialValueStoredByJava()
        && !marker.credentialBoundary().productionCredentialAllowed()
        && !marker.schemaRehearsalBoundary().schemaMigrationExecutionAllowed()
        && !marker.schemaRehearsalBoundary().schemaMigrationSqlExecutedByJava()
        && !marker.schemaRehearsalBoundary().schemaMigrationAppliedByJava()
        && !marker.rollbackPathBoundary().rollbackExecutionAllowedByJava()
        && !marker.rollbackPathBoundary().restoreExecutionAllowedByJava()
        && !marker.javaExecutionBoundary().approvalDecisionCreatedByJava()
        && !marker.javaExecutionBoundary().approvalLedgerWrittenByJava()
        && !marker.javaExecutionBoundary().approvalRecordPersistedByJava()
        && !marker.javaExecutionBoundary().managedAuditStoreWrittenByJava()
        && !marker.javaExecutionBoundary().externalManagedAuditConnectionOpenedByJava()
        && !marker.javaExecutionBoundary().sqlExecutedByJava()
        && !marker.javaExecutionBoundary().deploymentTriggeredByJava()
        && !marker.javaExecutionBoundary().rollbackTriggeredByJava()
        && !marker.javaExecutionBoundary().restoreExecutedByJava();
  }

  private boolean sourceMarkerAccepted(
      ReleaseApprovalRehearsalSandboxConnectionResponseRecords
              .RehearsalManagedAuditSandboxConnectionOperatorHandoffMarker
          marker) {
    return ReleaseApprovalContractConstants
            .RELEASE_APPROVAL_REHEARSAL_MANAGED_AUDIT_SANDBOX_CONNECTION_OPERATOR_HANDOFF_MARKER_VERSION
            .equals(marker.markerVersion())
        && marker.readyForNodeV229ManualSandboxConnectionPacketVerification()
        && !marker.readyForManagedAuditSandboxAdapterConnection()
        && !marker.readyForProductionAudit()
        && !marker.readyForProductionWindow()
        && !marker.nodeMayTreatAsProductionAuditRecord()
        && !marker.sandboxConnectionWindowBoundary().manualSandboxConnectionWindowOpenedByJava()
        && !marker.sandboxConnectionWindowBoundary().javaStartsManagedAuditService()
        && !marker.sandboxConnectionWindowBoundary().nodeAutoStartAllowed()
        && !marker.sandboxConnectionWindowBoundary().connectionExecutionAllowed()
        && !marker.credentialBoundary().credentialValueRequiredByJava()
        && !marker.credentialBoundary().credentialValueReadByJava()
        && !marker.credentialBoundary().credentialValueStoredByJava()
        && !marker.schemaRehearsalBoundary().schemaMigrationExecutionAllowed()
        && !marker.schemaRehearsalBoundary().schemaMigrationSqlExecutedByJava()
        && !marker.schemaRehearsalBoundary().schemaMigrationAppliedByJava()
        && !marker.rollbackPathBoundary().rollbackExecutionAllowedByJava()
        && !marker.rollbackPathBoundary().restoreExecutionAllowedByJava()
        && !marker.javaExecutionBoundary().approvalDecisionCreatedByJava()
        && !marker.javaExecutionBoundary().approvalLedgerWrittenByJava()
        && !marker.javaExecutionBoundary().approvalRecordPersistedByJava()
        && !marker.javaExecutionBoundary().managedAuditStoreWrittenByJava()
        && !marker.javaExecutionBoundary().externalManagedAuditConnectionOpenedByJava()
        && !marker.javaExecutionBoundary().sqlExecutedByJava()
        && !marker.javaExecutionBoundary().deploymentTriggeredByJava()
        && !marker.javaExecutionBoundary().rollbackTriggeredByJava()
        && !marker.javaExecutionBoundary().restoreExecutedByJava();
  }

  private static List<String> requiredPreflightFields() {
    return List.of(
        OWNER_APPROVAL_ARTIFACT_ID_FIELD,
        CREDENTIAL_HANDLE_NAME_FIELD,
        SCHEMA_REHEARSAL_ID_FIELD,
        ROLLBACK_PATH_ID_FIELD,
        "timeoutBudgetMs=15000",
        MANUAL_ABORT_MARKER_FIELD,
        MANUAL_WINDOW_FLAG_NAME);
  }

  private static List<String> forbiddenPreflightOperations() {
    return List.of(
        "Read or print a managed audit credential value during Java v88 preflight echo",
        "Open a managed audit sandbox connection during Java v88 preflight echo",
        "Execute schema migration SQL during Java v88 preflight echo",
        "Write approval ledger or managed audit state during Java v88 preflight echo",
        "Trigger deployment, rollback, or restore during Java v88 preflight echo",
        "Start Java, mini-kv, or external audit services automatically");
  }

  private static List<String> nodeV231Prerequisites() {
    return List.of(
        "Node v230 manual sandbox connection preflight gate must be archived",
        "Java v88 sandbox connection preflight echo marker must be ready",
        "mini-kv v97 no-start guard receipt must be ready",
        "Node v231 must compare all seven preflight fields and no-start/no-write boundaries",
        "UPSTREAM_ACTIONS_ENABLED must remain false");
  }

  private record WindowFlags(
      String manualWindowFlagName,
      boolean manualWindowFlagRequired,
      boolean manualWindowOpenByDefault,
      boolean manualWindowOpenedByJava,
      boolean connectionExecutionAllowed,
      boolean nodeAutoStartAllowed,
      boolean javaStartsManagedAuditService) {

    static WindowFlags nodeV230ReadOnlyManualWindow() {
      return new WindowFlags(MANUAL_WINDOW_FLAG_NAME, true, false, false, false, false, false);
    }

    ReleaseApprovalRehearsalSandboxConnectionResponseRecords
            .RehearsalSandboxConnectionPreflightWindowBoundary
        toBoundary() {
      return new ReleaseApprovalRehearsalSandboxConnectionResponseRecords
          .RehearsalSandboxConnectionPreflightWindowBoundary(
          manualWindowFlagName,
          manualWindowFlagRequired,
          manualWindowOpenByDefault,
          manualWindowOpenedByJava,
          connectionExecutionAllowed,
          nodeAutoStartAllowed,
          javaStartsManagedAuditService);
    }
  }

  private record PreflightFieldFlags(
      String ownerApprovalArtifactIdField,
      String schemaRehearsalIdField,
      String rollbackPathIdField,
      int timeoutBudgetMs,
      String manualAbortMarkerField,
      boolean allRequiredPreflightFieldsRecognizedByJava,
      boolean preflightGateReadOnly,
      boolean gateCreatesConnectionCommand) {

    static PreflightFieldFlags nodeV230RequiredFields() {
      return new PreflightFieldFlags(
          OWNER_APPROVAL_ARTIFACT_ID_FIELD,
          SCHEMA_REHEARSAL_ID_FIELD,
          ROLLBACK_PATH_ID_FIELD,
          TIMEOUT_BUDGET_MS,
          MANUAL_ABORT_MARKER_FIELD,
          true,
          true,
          false);
    }

    ReleaseApprovalRehearsalSandboxConnectionResponseRecords
            .RehearsalSandboxConnectionPreflightFieldBoundary
        toBoundary() {
      return new ReleaseApprovalRehearsalSandboxConnectionResponseRecords
          .RehearsalSandboxConnectionPreflightFieldBoundary(
          ownerApprovalArtifactIdField,
          schemaRehearsalIdField,
          rollbackPathIdField,
          timeoutBudgetMs,
          manualAbortMarkerField,
          allRequiredPreflightFieldsRecognizedByJava,
          preflightGateReadOnly,
          gateCreatesConnectionCommand);
    }
  }

  private record CredentialFlags(
      String credentialHandleNameField,
      boolean credentialHandleNameRecognizedByJava,
      boolean credentialValueRequiredByJava,
      boolean credentialValueReadByJava,
      boolean credentialValueStoredByJava,
      boolean productionCredentialAllowed) {

    static CredentialFlags handleOnly() {
      return new CredentialFlags(CREDENTIAL_HANDLE_NAME_FIELD, true, false, false, false, false);
    }

    ReleaseApprovalRehearsalSandboxConnectionResponseRecords
            .RehearsalSandboxConnectionPreflightCredentialBoundary
        toBoundary() {
      return new ReleaseApprovalRehearsalSandboxConnectionResponseRecords
          .RehearsalSandboxConnectionPreflightCredentialBoundary(
          credentialHandleNameField,
          credentialHandleNameRecognizedByJava,
          credentialValueRequiredByJava,
          credentialValueReadByJava,
          credentialValueStoredByJava,
          productionCredentialAllowed);
    }
  }

  private record SchemaFlags(
      String schemaRehearsalIdField,
      boolean schemaRehearsalIdRequired,
      boolean schemaMigrationExecutionAllowed,
      boolean schemaMigrationSqlExecutedByJava,
      boolean schemaMigrationAppliedByJava) {

    static SchemaFlags rehearsalIdOnly() {
      return new SchemaFlags(SCHEMA_REHEARSAL_ID_FIELD, true, false, false, false);
    }

    ReleaseApprovalRehearsalSandboxConnectionResponseRecords
            .RehearsalSandboxConnectionPreflightSchemaBoundary
        toBoundary() {
      return new ReleaseApprovalRehearsalSandboxConnectionResponseRecords
          .RehearsalSandboxConnectionPreflightSchemaBoundary(
          schemaRehearsalIdField,
          schemaRehearsalIdRequired,
          schemaMigrationExecutionAllowed,
          schemaMigrationSqlExecutedByJava,
          schemaMigrationAppliedByJava);
    }
  }

  private record RollbackFlags(
      String rollbackPathIdField,
      String manualAbortMarkerField,
      int timeoutBudgetMs,
      boolean rollbackPathIdRequired,
      boolean manualAbortMarkerRequired,
      boolean rollbackExecutionAllowedByJava,
      boolean restoreExecutionAllowedByJava) {

    static RollbackFlags rollbackEvidenceOnly() {
      return new RollbackFlags(
          ROLLBACK_PATH_ID_FIELD,
          MANUAL_ABORT_MARKER_FIELD,
          TIMEOUT_BUDGET_MS,
          true,
          true,
          false,
          false);
    }

    ReleaseApprovalRehearsalSandboxConnectionResponseRecords
            .RehearsalSandboxConnectionPreflightRollbackBoundary
        toBoundary() {
      return new ReleaseApprovalRehearsalSandboxConnectionResponseRecords
          .RehearsalSandboxConnectionPreflightRollbackBoundary(
          rollbackPathIdField,
          manualAbortMarkerField,
          timeoutBudgetMs,
          rollbackPathIdRequired,
          manualAbortMarkerRequired,
          rollbackExecutionAllowedByJava,
          restoreExecutionAllowedByJava);
    }
  }

  private record JavaExecutionFlags(
      boolean approvalDecisionCreatedByJava,
      boolean approvalLedgerWrittenByJava,
      boolean approvalRecordPersistedByJava,
      boolean managedAuditStoreWrittenByJava,
      boolean externalManagedAuditConnectionOpenedByJava,
      boolean sqlExecutedByJava,
      boolean deploymentTriggeredByJava,
      boolean rollbackTriggeredByJava,
      boolean restoreExecutedByJava) {

    static JavaExecutionFlags noExecution() {
      return new JavaExecutionFlags(false, false, false, false, false, false, false, false, false);
    }

    ReleaseApprovalRehearsalSandboxConnectionResponseRecords
            .RehearsalSandboxConnectionPreflightJavaExecutionBoundary
        toBoundary() {
      return new ReleaseApprovalRehearsalSandboxConnectionResponseRecords
          .RehearsalSandboxConnectionPreflightJavaExecutionBoundary(
          approvalDecisionCreatedByJava,
          approvalLedgerWrittenByJava,
          approvalRecordPersistedByJava,
          managedAuditStoreWrittenByJava,
          externalManagedAuditConnectionOpenedByJava,
          sqlExecutedByJava,
          deploymentTriggeredByJava,
          rollbackTriggeredByJava,
          restoreExecutedByJava);
    }
  }
}
