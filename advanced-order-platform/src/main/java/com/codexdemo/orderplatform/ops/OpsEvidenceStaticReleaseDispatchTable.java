package com.codexdemo.orderplatform.ops;

import static com.codexdemo.orderplatform.ops.OpsEvidenceStaticReleaseArtifact.DEPLOYMENT_ROLLBACK_EVIDENCE;
import static com.codexdemo.orderplatform.ops.OpsEvidenceStaticReleaseArtifact.PRODUCTION_DEPLOYMENT_RUNBOOK_CONTRACT;
import static com.codexdemo.orderplatform.ops.OpsEvidenceStaticReleaseArtifact.PRODUCTION_SECRET_SOURCE_CONTRACT;
import static com.codexdemo.orderplatform.ops.OpsEvidenceStaticReleaseArtifact.RELEASE_AUDIT_RETENTION_FIXTURE;
import static com.codexdemo.orderplatform.ops.OpsEvidenceStaticReleaseArtifact.RELEASE_BUNDLE_MANIFEST;
import static com.codexdemo.orderplatform.ops.OpsEvidenceStaticReleaseArtifact.RELEASE_HANDOFF_CHECKLIST_FIXTURE;
import static com.codexdemo.orderplatform.ops.OpsEvidenceStaticReleaseArtifact.RELEASE_OPERATOR_SIGNOFF_FIXTURE;
import static com.codexdemo.orderplatform.ops.OpsEvidenceStaticReleaseArtifact.RELEASE_VERIFICATION_MANIFEST;
import static com.codexdemo.orderplatform.ops.OpsEvidenceStaticReleaseArtifact.ROLLBACK_APPROVAL_HANDOFF;
import static com.codexdemo.orderplatform.ops.OpsEvidenceStaticReleaseArtifact.ROLLBACK_APPROVAL_RECORD_FIXTURE;
import static com.codexdemo.orderplatform.ops.OpsEvidenceStaticReleaseArtifact.ROLLBACK_APPROVER_EVIDENCE_FIXTURE;
import static com.codexdemo.orderplatform.ops.OpsEvidenceStaticReleaseArtifact.ROLLBACK_SQL_REVIEW_GATE;

import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalContractConstants;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

final class OpsEvidenceStaticReleaseDispatchTable {

  private static final List<String> RELEASE_VERIFICATION_CHECKS =
      List.of(
          "focused-maven-tests",
          "non-docker-regression-tests",
          "maven-package",
          "http-smoke",
          "static-contract-json-validation");

  private static final List<String> MIGRATION_DIRECTION_OPTIONS =
      List.of("forward-only", "rollback-script-reviewed", "no-database-change");

  private static final List<ReleaseEvidenceDispatchEntry> DISPATCH_TABLE =
      List.of(
          new ReleaseEvidenceDispatchEntry(
              ReleaseEvidenceSection.RELEASE_VERIFICATION,
              OpsEvidenceStaticReleaseDispatchTable::releaseVerification),
          new ReleaseEvidenceDispatchEntry(
              ReleaseEvidenceSection.DEPLOYMENT_ROLLBACK,
              OpsEvidenceStaticReleaseDispatchTable::deploymentRollback),
          new ReleaseEvidenceDispatchEntry(
              ReleaseEvidenceSection.RELEASE_BUNDLE,
              OpsEvidenceStaticReleaseDispatchTable::releaseBundle),
          new ReleaseEvidenceDispatchEntry(
              ReleaseEvidenceSection.RELEASE_HANDOFF_CHECKLIST_FIXTURE,
              OpsEvidenceStaticReleaseDispatchTable::releaseHandoffChecklistFixture),
          new ReleaseEvidenceDispatchEntry(
              ReleaseEvidenceSection.RELEASE_AUDIT_RETENTION_FIXTURE,
              OpsEvidenceStaticReleaseDispatchTable::releaseAuditRetentionFixture),
          new ReleaseEvidenceDispatchEntry(
              ReleaseEvidenceSection.RELEASE_OPERATOR_SIGNOFF_FIXTURE,
              OpsEvidenceStaticReleaseDispatchTable::releaseOperatorSignoffFixture),
          new ReleaseEvidenceDispatchEntry(
              ReleaseEvidenceSection.ROLLBACK_APPROVER_EVIDENCE_FIXTURE,
              OpsEvidenceStaticReleaseDispatchTable::rollbackApproverEvidenceFixture),
          new ReleaseEvidenceDispatchEntry(
              ReleaseEvidenceSection.ROLLBACK_APPROVAL_HANDOFF,
              OpsEvidenceStaticReleaseDispatchTable::rollbackApprovalHandoff),
          new ReleaseEvidenceDispatchEntry(
              ReleaseEvidenceSection.ROLLBACK_APPROVAL_RECORD_FIXTURE,
              OpsEvidenceStaticReleaseDispatchTable::rollbackApprovalRecordFixture),
          new ReleaseEvidenceDispatchEntry(
              ReleaseEvidenceSection.ROLLBACK_SQL_REVIEW_GATE,
              OpsEvidenceStaticReleaseDispatchTable::rollbackSqlReviewGate),
          new ReleaseEvidenceDispatchEntry(
              ReleaseEvidenceSection.PRODUCTION_SECRET_SOURCE_CONTRACT,
              OpsEvidenceStaticReleaseDispatchTable::productionSecretSourceContract),
          new ReleaseEvidenceDispatchEntry(
              ReleaseEvidenceSection.PRODUCTION_DEPLOYMENT_RUNBOOK_CONTRACT,
              OpsEvidenceStaticReleaseDispatchTable::productionDeploymentRunbookContract));

  private OpsEvidenceStaticReleaseDispatchTable() {}

  private static String version(OpsEvidenceStaticReleaseArtifact artifact) {
    return artifact.version();
  }

  private static String endpoint(OpsEvidenceStaticReleaseArtifact artifact) {
    return artifact.endpoint();
  }

  static StaticReleaseEvidence build() {
    EnumMap<ReleaseEvidenceSection, Object> sections = new EnumMap<>(ReleaseEvidenceSection.class);
    for (ReleaseEvidenceDispatchEntry entry : DISPATCH_TABLE) {
      sections.put(entry.section(), entry.build().get());
    }
    return new StaticReleaseEvidence(
        section(
            sections,
            ReleaseEvidenceSection.RELEASE_VERIFICATION,
            OpsEvidenceResponse.ReleaseVerification.class),
        section(
            sections,
            ReleaseEvidenceSection.DEPLOYMENT_ROLLBACK,
            OpsEvidenceResponse.DeploymentRollback.class),
        section(
            sections,
            ReleaseEvidenceSection.RELEASE_BUNDLE,
            OpsEvidenceResponse.ReleaseBundle.class),
        section(
            sections,
            ReleaseEvidenceSection.RELEASE_HANDOFF_CHECKLIST_FIXTURE,
            OpsEvidenceResponse.ReleaseHandoffChecklistFixture.class),
        section(
            sections,
            ReleaseEvidenceSection.RELEASE_AUDIT_RETENTION_FIXTURE,
            OpsEvidenceResponse.ReleaseAuditRetentionFixture.class),
        section(
            sections,
            ReleaseEvidenceSection.RELEASE_OPERATOR_SIGNOFF_FIXTURE,
            OpsEvidenceResponse.ReleaseOperatorSignoffFixture.class),
        section(
            sections,
            ReleaseEvidenceSection.ROLLBACK_APPROVER_EVIDENCE_FIXTURE,
            OpsEvidenceResponse.RollbackApproverEvidenceFixture.class),
        section(
            sections,
            ReleaseEvidenceSection.ROLLBACK_APPROVAL_HANDOFF,
            OpsEvidenceResponse.RollbackApprovalHandoff.class),
        section(
            sections,
            ReleaseEvidenceSection.ROLLBACK_APPROVAL_RECORD_FIXTURE,
            OpsEvidenceResponse.RollbackApprovalRecordFixture.class),
        section(
            sections,
            ReleaseEvidenceSection.ROLLBACK_SQL_REVIEW_GATE,
            OpsEvidenceResponse.RollbackSqlReviewGate.class),
        section(
            sections,
            ReleaseEvidenceSection.PRODUCTION_SECRET_SOURCE_CONTRACT,
            OpsEvidenceResponse.ProductionSecretSourceContract.class),
        section(
            sections,
            ReleaseEvidenceSection.PRODUCTION_DEPLOYMENT_RUNBOOK_CONTRACT,
            OpsEvidenceResponse.ProductionDeploymentRunbookContract.class));
  }

  static List<String> staticContractEndpoints(boolean includeFieldGuide) {
    List<String> endpoints = new ArrayList<>();
    endpoints.add("/contracts/ops-read-only-evidence.sample.json");
    if (includeFieldGuide) {
      endpoints.add("/contracts/ops-evidence-field-guide.sample.json");
    }
    endpoints.addAll(
        List.of(
            "/contracts/order-idempotency-boundary.sample.json",
            "/contracts/order-idempotency-store-abstraction.sample.json",
            endpoint(RELEASE_VERIFICATION_MANIFEST),
            endpoint(DEPLOYMENT_ROLLBACK_EVIDENCE),
            endpoint(RELEASE_BUNDLE_MANIFEST),
            endpoint(RELEASE_HANDOFF_CHECKLIST_FIXTURE),
            endpoint(RELEASE_AUDIT_RETENTION_FIXTURE),
            endpoint(RELEASE_OPERATOR_SIGNOFF_FIXTURE),
            endpoint(ROLLBACK_APPROVER_EVIDENCE_FIXTURE),
            endpoint(ROLLBACK_APPROVAL_HANDOFF),
            endpoint(ROLLBACK_APPROVAL_RECORD_FIXTURE),
            endpoint(ROLLBACK_SQL_REVIEW_GATE),
            endpoint(PRODUCTION_SECRET_SOURCE_CONTRACT),
            endpoint(PRODUCTION_DEPLOYMENT_RUNBOOK_CONTRACT)));
    return List.copyOf(endpoints);
  }

  static List<String> staticContractProbeEndpoints(boolean includeFieldGuide) {
    return staticContractEndpoints(includeFieldGuide).stream()
        .map(endpoint -> "GET " + endpoint)
        .toList();
  }

  private static OpsEvidenceResponse.ReleaseVerification releaseVerification() {
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

  private static OpsEvidenceResponse.DeploymentRollback deploymentRollback() {
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

  private static OpsEvidenceResponse.ReleaseBundle releaseBundle() {
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

  private static OpsEvidenceResponse.ReleaseHandoffChecklistFixture
      releaseHandoffChecklistFixture() {
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

  private static OpsEvidenceResponse.ReleaseAuditRetentionFixture releaseAuditRetentionFixture() {
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

  private static OpsEvidenceResponse.ReleaseOperatorSignoffFixture releaseOperatorSignoffFixture() {
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

  private static OpsEvidenceResponse.RollbackApproverEvidenceFixture
      rollbackApproverEvidenceFixture() {
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

  private static OpsEvidenceResponse.RollbackApprovalHandoff rollbackApprovalHandoff() {
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

  private static OpsEvidenceResponse.RollbackApprovalRecordFixture rollbackApprovalRecordFixture() {
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

  private static OpsEvidenceResponse.RollbackSqlReviewGate rollbackSqlReviewGate() {
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

  private static OpsEvidenceResponse.ProductionSecretSourceContract
      productionSecretSourceContract() {
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

  private static OpsEvidenceResponse.ProductionDeploymentRunbookContract
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

  private static <T> T section(
      EnumMap<ReleaseEvidenceSection, Object> sections,
      ReleaseEvidenceSection section,
      Class<T> type) {
    return type.cast(sections.get(section));
  }

  record StaticReleaseEvidence(
      OpsEvidenceResponse.ReleaseVerification releaseVerification,
      OpsEvidenceResponse.DeploymentRollback deploymentRollback,
      OpsEvidenceResponse.ReleaseBundle releaseBundle,
      OpsEvidenceResponse.ReleaseHandoffChecklistFixture releaseHandoffChecklistFixture,
      OpsEvidenceResponse.ReleaseAuditRetentionFixture releaseAuditRetentionFixture,
      OpsEvidenceResponse.ReleaseOperatorSignoffFixture releaseOperatorSignoffFixture,
      OpsEvidenceResponse.RollbackApproverEvidenceFixture rollbackApproverEvidenceFixture,
      OpsEvidenceResponse.RollbackApprovalHandoff rollbackApprovalHandoff,
      OpsEvidenceResponse.RollbackApprovalRecordFixture rollbackApprovalRecordFixture,
      OpsEvidenceResponse.RollbackSqlReviewGate rollbackSqlReviewGate,
      OpsEvidenceResponse.ProductionSecretSourceContract productionSecretSourceContract,
      OpsEvidenceResponse.ProductionDeploymentRunbookContract
          productionDeploymentRunbookContract) {}

  private record ReleaseEvidenceDispatchEntry(
      ReleaseEvidenceSection section, Supplier<Object> build) {}

  private enum ReleaseEvidenceSection {
    RELEASE_VERIFICATION,
    DEPLOYMENT_ROLLBACK,
    RELEASE_BUNDLE,
    RELEASE_HANDOFF_CHECKLIST_FIXTURE,
    RELEASE_AUDIT_RETENTION_FIXTURE,
    RELEASE_OPERATOR_SIGNOFF_FIXTURE,
    ROLLBACK_APPROVER_EVIDENCE_FIXTURE,
    ROLLBACK_APPROVAL_HANDOFF,
    ROLLBACK_APPROVAL_RECORD_FIXTURE,
    ROLLBACK_SQL_REVIEW_GATE,
    PRODUCTION_SECRET_SOURCE_CONTRACT,
    PRODUCTION_DEPLOYMENT_RUNBOOK_CONTRACT
  }
}
