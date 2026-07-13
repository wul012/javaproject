package com.codexdemo.orderplatform.ops.maintenance.evidencecore;

import static com.codexdemo.orderplatform.ops.maintenance.evidencecore.StaticReleaseCatalog.Artifact.DEPLOYMENT_ROLLBACK_EVIDENCE;
import static com.codexdemo.orderplatform.ops.maintenance.evidencecore.StaticReleaseCatalog.Artifact.PRODUCTION_DEPLOYMENT_RUNBOOK_CONTRACT;
import static com.codexdemo.orderplatform.ops.maintenance.evidencecore.StaticReleaseCatalog.Artifact.PRODUCTION_SECRET_SOURCE_CONTRACT;
import static com.codexdemo.orderplatform.ops.maintenance.evidencecore.StaticReleaseCatalog.Artifact.RELEASE_AUDIT_RETENTION_FIXTURE;
import static com.codexdemo.orderplatform.ops.maintenance.evidencecore.StaticReleaseCatalog.Artifact.RELEASE_BUNDLE_MANIFEST;
import static com.codexdemo.orderplatform.ops.maintenance.evidencecore.StaticReleaseCatalog.Artifact.RELEASE_HANDOFF_CHECKLIST_FIXTURE;
import static com.codexdemo.orderplatform.ops.maintenance.evidencecore.StaticReleaseCatalog.Artifact.RELEASE_OPERATOR_SIGNOFF_FIXTURE;
import static com.codexdemo.orderplatform.ops.maintenance.evidencecore.StaticReleaseCatalog.Artifact.RELEASE_VERIFICATION_MANIFEST;
import static com.codexdemo.orderplatform.ops.maintenance.evidencecore.StaticReleaseCatalog.Artifact.ROLLBACK_APPROVAL_HANDOFF;
import static com.codexdemo.orderplatform.ops.maintenance.evidencecore.StaticReleaseCatalog.Artifact.ROLLBACK_APPROVAL_RECORD_FIXTURE;
import static com.codexdemo.orderplatform.ops.maintenance.evidencecore.StaticReleaseCatalog.Artifact.ROLLBACK_APPROVER_EVIDENCE_FIXTURE;
import static com.codexdemo.orderplatform.ops.maintenance.evidencecore.StaticReleaseCatalog.Artifact.ROLLBACK_SQL_REVIEW_GATE;
import static com.codexdemo.orderplatform.ops.maintenance.evidencecore.StaticReleaseCatalog.staticContractEndpoints;

import com.codexdemo.orderplatform.ops.OpsEvidenceResponse;
import com.codexdemo.orderplatform.ops.maintenance.evidencecore.StaticReleaseCatalog.Artifact;
import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalContractConstants;
import java.util.List;

final class StaticReleaseSections {

  private static final List<String> RELEASE_VERIFICATION_CHECKS =
      List.of(
          "focused-maven-tests",
          "non-docker-regression-tests",
          "maven-package",
          "http-smoke",
          "static-contract-json-validation");

  private static final List<String> MIGRATION_DIRECTION_OPTIONS =
      List.of("forward-only", "rollback-script-reviewed", "no-database-change");

  private StaticReleaseSections() {}

  static OpsEvidenceResponse.ReleaseVerification releaseVerification() {
    return new OpsEvidenceResponse.ReleaseVerification(
        version(RELEASE_VERIFICATION_MANIFEST),
        endpoint(RELEASE_VERIFICATION_MANIFEST),
        "LOCAL_OPERATOR_EXECUTES_AND_ARCHIVES_RESULTS",
        RELEASE_VERIFICATION_CHECKS,
        staticContractEndpoints(true),
        false,
        false,
        false,
        false);
  }

  static OpsEvidenceResponse.DeploymentRollback deploymentRollback() {
    return new OpsEvidenceResponse.DeploymentRollback(
        version(DEPLOYMENT_ROLLBACK_EVIDENCE),
        endpoint(DEPLOYMENT_ROLLBACK_EVIDENCE),
        "READ_ONLY_BOUNDARY_SAMPLE",
        List.of("java-package", "runtime-configuration", "database-migrations", "static-contracts"),
        List.of(
            "artifact-version-target",
            "deployment-window-owner",
            "rollback-approver",
            "configuration-secret-source",
            "production-secret-source-contract",
            "production-deployment-runbook-contract",
            "database-migration-direction",
            "release-handoff-checklist-fixture",
            "release-audit-retention-fixture",
            "release-operator-signoff-fixture",
            "rollback-approver-evidence-fixture",
            "rollback-approval-handoff",
            "rollback-approval-record-fixture",
            "rollback-sql-review-gate"),
        true,
        true,
        false,
        true,
        false,
        false,
        false);
  }

  static OpsEvidenceResponse.ReleaseBundle releaseBundle() {
    return new OpsEvidenceResponse.ReleaseBundle(
        version(RELEASE_BUNDLE_MANIFEST),
        endpoint(RELEASE_BUNDLE_MANIFEST),
        "READ_ONLY_RELEASE_BUNDLE",
        "target/advanced-order-platform-0.1.0-SNAPSHOT.jar",
        staticContractEndpoints(true),
        RELEASE_VERIFICATION_CHECKS,
        true,
        false,
        false,
        false,
        false);
  }

  static OpsEvidenceResponse.ReleaseHandoffChecklistFixture releaseHandoffChecklistFixture() {
    return new OpsEvidenceResponse.ReleaseHandoffChecklistFixture(
        version(RELEASE_HANDOFF_CHECKLIST_FIXTURE),
        endpoint(RELEASE_HANDOFF_CHECKLIST_FIXTURE),
        "READ_ONLY_RELEASE_HANDOFF_CHECKLIST_FIXTURE",
        "release-operator-placeholder",
        "rollback-approver-placeholder",
        "release-tag-or-artifact-version-placeholder",
        MIGRATION_DIRECTION_OPTIONS,
        "no-database-change",
        endpoint(PRODUCTION_SECRET_SOURCE_CONTRACT),
        List.of(
            "release-operator",
            "rollback-approver",
            "artifact-target",
            "database-migration-direction",
            "secret-source-confirmation",
            "deployment-runbook-contract",
            "rollback-approval-record-fixture",
            "release-audit-retention-fixture",
            "release-operator-signoff-fixture",
            "rollback-approver-evidence-fixture",
            "no-secret-value-boundary"),
        releaseHandoffChecklistArtifacts(),
        List.of(
            "checklist-fixture-stores-metadata-only",
            "secret-values-must-not-be-read",
            "secret-values-must-not-be-embedded-in-handoff-checklist",
            "node-may-render-release-handoff-review-only"),
        true,
        false,
        false,
        false,
        false,
        false,
        false,
        false);
  }

  static OpsEvidenceResponse.ReleaseAuditRetentionFixture releaseAuditRetentionFixture() {
    return new OpsEvidenceResponse.ReleaseAuditRetentionFixture(
        version(RELEASE_AUDIT_RETENTION_FIXTURE),
        endpoint(RELEASE_AUDIT_RETENTION_FIXTURE),
        "READ_ONLY_RELEASE_AUDIT_RETENTION_FIXTURE",
        "release-retention-record-placeholder",
        "release-operator-placeholder",
        "release-tag-or-artifact-version-placeholder",
        180,
        releaseAuditRetentionEndpoints(),
        List.of(
            "retention-id",
            "release-operator",
            "artifact-target",
            "retention-days",
            "evidence-endpoints",
            "release-operator-signoff-fixture",
            "rollback-approver-evidence-fixture",
            "audit-export-location-placeholder",
            "no-secret-value-boundary"),
        releaseAuditRetentionArtifacts(),
        List.of(
            "retention-fixture-stores-metadata-only",
            "secret-values-must-not-be-read",
            "secret-values-must-not-be-embedded-in-retention-record",
            "node-may-render-retention-gate-only"),
        true,
        false,
        false,
        true,
        false,
        false,
        false,
        false,
        false);
  }

  static OpsEvidenceResponse.ReleaseOperatorSignoffFixture releaseOperatorSignoffFixture() {
    return new OpsEvidenceResponse.ReleaseOperatorSignoffFixture(
        version(RELEASE_OPERATOR_SIGNOFF_FIXTURE),
        endpoint(RELEASE_OPERATOR_SIGNOFF_FIXTURE),
        "READ_ONLY_RELEASE_OPERATOR_SIGNOFF_FIXTURE",
        "release-operator-placeholder",
        "rollback-approver-placeholder",
        "release-window-placeholder",
        "release-tag-or-artifact-version-placeholder",
        "operator-signoff-placeholder",
        List.of(
            "release-operator",
            "rollback-approver",
            "release-window",
            "artifact-target",
            "operator-signoff-placeholder",
            "release-audit-retention-fixture",
            "rollback-approver-evidence-fixture",
            "no-secret-value-boundary"),
        releaseOperatorSignoffArtifacts(),
        List.of(
            "signoff-fixture-stores-metadata-only",
            "secret-values-must-not-be-read",
            "secret-values-must-not-be-embedded-in-signoff",
            "node-may-render-approval-prerequisite-gate-only"),
        true,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false);
  }

  static OpsEvidenceResponse.RollbackApproverEvidenceFixture rollbackApproverEvidenceFixture() {
    return new OpsEvidenceResponse.RollbackApproverEvidenceFixture(
        version(ROLLBACK_APPROVER_EVIDENCE_FIXTURE),
        endpoint(ROLLBACK_APPROVER_EVIDENCE_FIXTURE),
        "READ_ONLY_ROLLBACK_APPROVER_EVIDENCE_FIXTURE",
        "rollback-approver-placeholder",
        MIGRATION_DIRECTION_OPTIONS,
        "no-database-change",
        "rollback-sql-artifact-reference-placeholder",
        "production-database-connection-outside-this-fixture",
        List.of(
            "rollback-approver",
            "database-migration-direction",
            "rollback-sql-artifact-reference",
            "production-database-access-boundary",
            "rollback-sql-review-gate",
            "no-secret-value-boundary"),
        rollbackApproverEvidenceArtifacts(),
        List.of(
            "rollback-approver-fixture-stores-metadata-only",
            "secret-values-must-not-be-read",
            "secret-values-must-not-be-embedded-in-approver-evidence",
            "node-may-render-decision-rehearsal-input-only"),
        true,
        false,
        false,
        false,
        false,
        false,
        false,
        false);
  }

  static OpsEvidenceResponse.RollbackApprovalHandoff rollbackApprovalHandoff() {
    return new OpsEvidenceResponse.RollbackApprovalHandoff(
        version(ROLLBACK_APPROVAL_HANDOFF),
        endpoint(ROLLBACK_APPROVAL_HANDOFF),
        "OPERATOR_CONFIRMATION_REQUIRED",
        List.of(
            "artifact-version-target",
            "deployment-window-owner",
            "rollback-approver",
            "runtime-config-profile",
            "configuration-secret-source",
            "production-secret-source-contract",
            "production-deployment-runbook-contract",
            "database-migration-direction",
            "release-handoff-checklist-fixture",
            "release-audit-retention-fixture",
            "release-operator-signoff-fixture",
            "rollback-approver-evidence-fixture",
            "rollback-approval-record-fixture",
            "rollback-sql-review-gate",
            "release-bundle-manifest",
            "deployment-rollback-evidence"),
        List.of(
            endpoint(RELEASE_HANDOFF_CHECKLIST_FIXTURE),
            endpoint(RELEASE_AUDIT_RETENTION_FIXTURE),
            endpoint(RELEASE_OPERATOR_SIGNOFF_FIXTURE),
            endpoint(ROLLBACK_APPROVER_EVIDENCE_FIXTURE),
            endpoint(RELEASE_BUNDLE_MANIFEST),
            endpoint(DEPLOYMENT_ROLLBACK_EVIDENCE),
            endpoint(ROLLBACK_APPROVAL_RECORD_FIXTURE),
            endpoint(ROLLBACK_SQL_REVIEW_GATE),
            endpoint(PRODUCTION_SECRET_SOURCE_CONTRACT),
            endpoint(PRODUCTION_DEPLOYMENT_RUNBOOK_CONTRACT),
            endpoint(RELEASE_VERIFICATION_MANIFEST)),
        true,
        false,
        false,
        false,
        false,
        false);
  }

  static OpsEvidenceResponse.RollbackApprovalRecordFixture rollbackApprovalRecordFixture() {
    return new OpsEvidenceResponse.RollbackApprovalRecordFixture(
        version(ROLLBACK_APPROVAL_RECORD_FIXTURE),
        endpoint(ROLLBACK_APPROVAL_RECORD_FIXTURE),
        "READ_ONLY_APPROVAL_RECORD_FIXTURE",
        "rollback-reviewer-placeholder",
        "approval-timestamp-placeholder",
        "release-tag-or-artifact-version-placeholder",
        MIGRATION_DIRECTION_OPTIONS,
        "no-database-change",
        List.of(
            "reviewer",
            "approval-timestamp-placeholder",
            "rollback-target",
            "database-migration-direction",
            "rollback-sql-review-gate",
            "no-secret-value-boundary"),
        List.of(
            endpoint(ROLLBACK_APPROVAL_HANDOFF),
            endpoint(ROLLBACK_APPROVER_EVIDENCE_FIXTURE),
            endpoint(ROLLBACK_SQL_REVIEW_GATE),
            endpoint(PRODUCTION_DEPLOYMENT_RUNBOOK_CONTRACT),
            endpoint(PRODUCTION_SECRET_SOURCE_CONTRACT),
            endpoint(RELEASE_BUNDLE_MANIFEST)),
        List.of(
            "record-fixture-stores-metadata-only",
            "secret-values-must-not-be-read",
            "secret-values-must-not-be-embedded-in-approval-record",
            "node-may-render-release-window-packet-only"),
        true,
        false,
        false,
        false,
        false,
        false,
        false);
  }

  static OpsEvidenceResponse.RollbackSqlReviewGate rollbackSqlReviewGate() {
    return new OpsEvidenceResponse.RollbackSqlReviewGate(
        version(ROLLBACK_SQL_REVIEW_GATE),
        endpoint(ROLLBACK_SQL_REVIEW_GATE),
        "READ_ONLY_SQL_REVIEW_GATE",
        "database-release-owner",
        List.of(
            "rollback-sql-review-owner",
            "migration-direction",
            "operator-approval-placeholder",
            "rollback-sql-artifact-reference",
            "production-database-access-boundary"),
        MIGRATION_DIRECTION_OPTIONS,
        "operator-approval-required-before-any-sql-execution",
        true,
        false,
        false,
        false,
        false);
  }

  static OpsEvidenceResponse.ProductionSecretSourceContract productionSecretSourceContract() {
    return new OpsEvidenceResponse.ProductionSecretSourceContract(
        version(PRODUCTION_SECRET_SOURCE_CONTRACT),
        endpoint(PRODUCTION_SECRET_SOURCE_CONTRACT),
        "READ_ONLY_SECRET_SOURCE_CONTRACT",
        List.of(
            "external-secret-manager", "environment-injected-secret", "platform-managed-secret"),
        "external-secret-manager",
        "platform-security-owner",
        "security-operations-owner",
        "quarterly-or-before-production-cutover",
        List.of(
            "secret-manager-or-source-type",
            "secret-manager-owner",
            "rotation-owner",
            "review-cadence",
            "secret-value-access-boundary"),
        List.of(
            "contract-records-source-metadata-only",
            "secret-values-must-not-be-read",
            "secret-values-must-not-be-embedded-in-static-contracts",
            "node-may-render-checklist-only"),
        true,
        false,
        false,
        false,
        false);
  }

  static OpsEvidenceResponse.ProductionDeploymentRunbookContract
      productionDeploymentRunbookContract() {
    return new OpsEvidenceResponse.ProductionDeploymentRunbookContract(
        version(PRODUCTION_DEPLOYMENT_RUNBOOK_CONTRACT),
        endpoint(PRODUCTION_DEPLOYMENT_RUNBOOK_CONTRACT),
        "READ_ONLY_DEPLOYMENT_RUNBOOK_CONTRACT",
        "release-window-owner",
        "rollback-approval-owner",
        MIGRATION_DIRECTION_OPTIONS,
        "no-database-change",
        endpoint(PRODUCTION_SECRET_SOURCE_CONTRACT),
        List.of(
            "deployment-window-owner",
            "rollback-approver",
            "database-migration-direction",
            "secret-source-confirmation",
            "rollback-sql-review-gate",
            "operator-approval-placeholder",
            "release-audit-retention-fixture",
            "release-operator-signoff-fixture",
            "rollback-approver-evidence-fixture"),
        List.of(
            endpoint(RELEASE_BUNDLE_MANIFEST),
            endpoint(DEPLOYMENT_ROLLBACK_EVIDENCE),
            endpoint(RELEASE_HANDOFF_CHECKLIST_FIXTURE),
            endpoint(RELEASE_AUDIT_RETENTION_FIXTURE),
            endpoint(RELEASE_OPERATOR_SIGNOFF_FIXTURE),
            endpoint(ROLLBACK_APPROVER_EVIDENCE_FIXTURE),
            endpoint(ROLLBACK_APPROVAL_HANDOFF),
            endpoint(ROLLBACK_APPROVAL_RECORD_FIXTURE),
            endpoint(ROLLBACK_SQL_REVIEW_GATE),
            endpoint(PRODUCTION_SECRET_SOURCE_CONTRACT)),
        true,
        false,
        false,
        false,
        false,
        false,
        false);
  }

  private static List<String> releaseHandoffChecklistArtifacts() {
    return List.of(
        endpoint(RELEASE_BUNDLE_MANIFEST),
        endpoint(RELEASE_VERIFICATION_MANIFEST),
        endpoint(RELEASE_AUDIT_RETENTION_FIXTURE),
        endpoint(RELEASE_OPERATOR_SIGNOFF_FIXTURE),
        endpoint(ROLLBACK_APPROVER_EVIDENCE_FIXTURE),
        endpoint(PRODUCTION_DEPLOYMENT_RUNBOOK_CONTRACT),
        endpoint(PRODUCTION_SECRET_SOURCE_CONTRACT),
        endpoint(ROLLBACK_APPROVAL_RECORD_FIXTURE),
        endpoint(ROLLBACK_SQL_REVIEW_GATE));
  }

  private static List<String> releaseAuditRetentionEndpoints() {
    return List.of(
        "/api/v1/ops/evidence",
        ReleaseApprovalContractConstants.RELEASE_APPROVAL_REHEARSAL_ENDPOINT,
        "/api/v1/failed-events/replay-evidence-index",
        endpoint(RELEASE_VERIFICATION_MANIFEST),
        endpoint(RELEASE_BUNDLE_MANIFEST),
        endpoint(RELEASE_HANDOFF_CHECKLIST_FIXTURE),
        endpoint(RELEASE_OPERATOR_SIGNOFF_FIXTURE),
        endpoint(ROLLBACK_APPROVER_EVIDENCE_FIXTURE),
        endpoint(PRODUCTION_DEPLOYMENT_RUNBOOK_CONTRACT));
  }

  private static List<String> releaseAuditRetentionArtifacts() {
    return List.of(
        endpoint(RELEASE_VERIFICATION_MANIFEST),
        endpoint(RELEASE_BUNDLE_MANIFEST),
        endpoint(RELEASE_HANDOFF_CHECKLIST_FIXTURE),
        endpoint(RELEASE_OPERATOR_SIGNOFF_FIXTURE),
        endpoint(ROLLBACK_APPROVER_EVIDENCE_FIXTURE),
        endpoint(PRODUCTION_DEPLOYMENT_RUNBOOK_CONTRACT),
        endpoint(PRODUCTION_SECRET_SOURCE_CONTRACT));
  }

  private static List<String> releaseOperatorSignoffArtifacts() {
    return List.of(
        endpoint(RELEASE_HANDOFF_CHECKLIST_FIXTURE),
        endpoint(RELEASE_AUDIT_RETENTION_FIXTURE),
        endpoint(RELEASE_BUNDLE_MANIFEST),
        endpoint(RELEASE_VERIFICATION_MANIFEST),
        endpoint(PRODUCTION_DEPLOYMENT_RUNBOOK_CONTRACT),
        endpoint(ROLLBACK_APPROVER_EVIDENCE_FIXTURE),
        endpoint(ROLLBACK_APPROVAL_HANDOFF));
  }

  private static List<String> rollbackApproverEvidenceArtifacts() {
    return List.of(
        endpoint(ROLLBACK_SQL_REVIEW_GATE),
        endpoint(ROLLBACK_APPROVAL_HANDOFF),
        endpoint(ROLLBACK_APPROVAL_RECORD_FIXTURE),
        endpoint(PRODUCTION_DEPLOYMENT_RUNBOOK_CONTRACT),
        endpoint(PRODUCTION_SECRET_SOURCE_CONTRACT),
        endpoint(RELEASE_BUNDLE_MANIFEST));
  }

  private static String version(Artifact artifact) {
    return artifact.version();
  }

  private static String endpoint(Artifact artifact) {
    return artifact.endpoint();
  }
}
