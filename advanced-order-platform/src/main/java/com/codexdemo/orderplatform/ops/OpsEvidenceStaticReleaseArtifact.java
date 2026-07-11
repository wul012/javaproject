package com.codexdemo.orderplatform.ops;

public enum OpsEvidenceStaticReleaseArtifact {
  RELEASE_VERIFICATION_MANIFEST(
      "java-release-verification-manifest.v1",
      "/contracts/release-verification-manifest.sample.json"),
  DEPLOYMENT_ROLLBACK_EVIDENCE(
      "java-deployment-rollback-evidence.v1",
      "/contracts/deployment-rollback-evidence.sample.json"),
  RELEASE_BUNDLE_MANIFEST(
      "java-release-bundle-manifest.v1", "/contracts/release-bundle-manifest.sample.json"),
  RELEASE_HANDOFF_CHECKLIST_FIXTURE(
      "java-release-handoff-checklist-fixture.v1",
      "/contracts/release-handoff-checklist.fixture.json"),
  RELEASE_AUDIT_RETENTION_FIXTURE(
      "java-release-audit-retention-fixture.v1", "/contracts/release-audit-retention.fixture.json"),
  RELEASE_OPERATOR_SIGNOFF_FIXTURE(
      "java-release-operator-signoff-fixture.v1",
      "/contracts/release-operator-signoff.fixture.json"),
  ROLLBACK_APPROVER_EVIDENCE_FIXTURE(
      "java-rollback-approver-evidence-fixture.v1",
      "/contracts/rollback-approver-evidence.fixture.json"),
  ROLLBACK_APPROVAL_HANDOFF(
      "java-rollback-approval-handoff.v1", "/contracts/rollback-approval-handoff.sample.json"),
  ROLLBACK_APPROVAL_RECORD_FIXTURE(
      "java-rollback-approval-record-fixture.v1",
      "/contracts/rollback-approval-record.fixture.json"),
  ROLLBACK_SQL_REVIEW_GATE(
      "java-rollback-sql-review-gate.v1", "/contracts/rollback-sql-review-gate.sample.json"),
  PRODUCTION_SECRET_SOURCE_CONTRACT(
      "java-production-secret-source-contract.v1",
      "/contracts/production-secret-source-contract.sample.json"),
  PRODUCTION_DEPLOYMENT_RUNBOOK_CONTRACT(
      "java-production-deployment-runbook-contract.v1",
      "/contracts/production-deployment-runbook-contract.sample.json");

  private final String version;
  private final String endpoint;

  OpsEvidenceStaticReleaseArtifact(String version, String endpoint) {
    this.version = version;
    this.endpoint = endpoint;
  }

  public String version() {
    return version;
  }

  public String endpoint() {
    return endpoint;
  }
}
