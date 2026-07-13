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

import com.codexdemo.orderplatform.ops.OpsEvidenceResponse;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

public final class StaticReleaseCatalog {

  private static final List<DispatchEntry> DISPATCH_TABLE =
      List.of(
          new DispatchEntry(
              Section.RELEASE_VERIFICATION, StaticReleaseSections::releaseVerification),
          new DispatchEntry(Section.DEPLOYMENT_ROLLBACK, StaticReleaseSections::deploymentRollback),
          new DispatchEntry(Section.RELEASE_BUNDLE, StaticReleaseSections::releaseBundle),
          new DispatchEntry(
              Section.RELEASE_HANDOFF_CHECKLIST_FIXTURE,
              StaticReleaseSections::releaseHandoffChecklistFixture),
          new DispatchEntry(
              Section.RELEASE_AUDIT_RETENTION_FIXTURE,
              StaticReleaseSections::releaseAuditRetentionFixture),
          new DispatchEntry(
              Section.RELEASE_OPERATOR_SIGNOFF_FIXTURE,
              StaticReleaseSections::releaseOperatorSignoffFixture),
          new DispatchEntry(
              Section.ROLLBACK_APPROVER_EVIDENCE_FIXTURE,
              StaticReleaseSections::rollbackApproverEvidenceFixture),
          new DispatchEntry(
              Section.ROLLBACK_APPROVAL_HANDOFF, StaticReleaseSections::rollbackApprovalHandoff),
          new DispatchEntry(
              Section.ROLLBACK_APPROVAL_RECORD_FIXTURE,
              StaticReleaseSections::rollbackApprovalRecordFixture),
          new DispatchEntry(
              Section.ROLLBACK_SQL_REVIEW_GATE, StaticReleaseSections::rollbackSqlReviewGate),
          new DispatchEntry(
              Section.PRODUCTION_SECRET_SOURCE_CONTRACT,
              StaticReleaseSections::productionSecretSourceContract),
          new DispatchEntry(
              Section.PRODUCTION_DEPLOYMENT_RUNBOOK_CONTRACT,
              StaticReleaseSections::productionDeploymentRunbookContract));

  private StaticReleaseCatalog() {}

  public static StaticReleaseEvidence build() {
    EnumMap<Section, Object> sections = new EnumMap<>(Section.class);
    for (DispatchEntry entry : DISPATCH_TABLE) {
      sections.put(entry.section(), entry.build().get());
    }
    return new StaticReleaseEvidence(
        section(
            sections, Section.RELEASE_VERIFICATION, OpsEvidenceResponse.ReleaseVerification.class),
        section(
            sections, Section.DEPLOYMENT_ROLLBACK, OpsEvidenceResponse.DeploymentRollback.class),
        section(sections, Section.RELEASE_BUNDLE, OpsEvidenceResponse.ReleaseBundle.class),
        section(
            sections,
            Section.RELEASE_HANDOFF_CHECKLIST_FIXTURE,
            OpsEvidenceResponse.ReleaseHandoffChecklistFixture.class),
        section(
            sections,
            Section.RELEASE_AUDIT_RETENTION_FIXTURE,
            OpsEvidenceResponse.ReleaseAuditRetentionFixture.class),
        section(
            sections,
            Section.RELEASE_OPERATOR_SIGNOFF_FIXTURE,
            OpsEvidenceResponse.ReleaseOperatorSignoffFixture.class),
        section(
            sections,
            Section.ROLLBACK_APPROVER_EVIDENCE_FIXTURE,
            OpsEvidenceResponse.RollbackApproverEvidenceFixture.class),
        section(
            sections,
            Section.ROLLBACK_APPROVAL_HANDOFF,
            OpsEvidenceResponse.RollbackApprovalHandoff.class),
        section(
            sections,
            Section.ROLLBACK_APPROVAL_RECORD_FIXTURE,
            OpsEvidenceResponse.RollbackApprovalRecordFixture.class),
        section(
            sections,
            Section.ROLLBACK_SQL_REVIEW_GATE,
            OpsEvidenceResponse.RollbackSqlReviewGate.class),
        section(
            sections,
            Section.PRODUCTION_SECRET_SOURCE_CONTRACT,
            OpsEvidenceResponse.ProductionSecretSourceContract.class),
        section(
            sections,
            Section.PRODUCTION_DEPLOYMENT_RUNBOOK_CONTRACT,
            OpsEvidenceResponse.ProductionDeploymentRunbookContract.class));
  }

  public static List<String> staticContractEndpoints(boolean includeFieldGuide) {
    List<String> endpoints = new ArrayList<>();
    endpoints.add("/contracts/ops-read-only-evidence.sample.json");
    if (includeFieldGuide) {
      endpoints.add("/contracts/ops-evidence-field-guide.sample.json");
    }
    endpoints.addAll(
        List.of(
            "/contracts/order-idempotency-boundary.sample.json",
            "/contracts/order-idempotency-store-abstraction.sample.json",
            RELEASE_VERIFICATION_MANIFEST.endpoint(),
            DEPLOYMENT_ROLLBACK_EVIDENCE.endpoint(),
            RELEASE_BUNDLE_MANIFEST.endpoint(),
            RELEASE_HANDOFF_CHECKLIST_FIXTURE.endpoint(),
            RELEASE_AUDIT_RETENTION_FIXTURE.endpoint(),
            RELEASE_OPERATOR_SIGNOFF_FIXTURE.endpoint(),
            ROLLBACK_APPROVER_EVIDENCE_FIXTURE.endpoint(),
            ROLLBACK_APPROVAL_HANDOFF.endpoint(),
            ROLLBACK_APPROVAL_RECORD_FIXTURE.endpoint(),
            ROLLBACK_SQL_REVIEW_GATE.endpoint(),
            PRODUCTION_SECRET_SOURCE_CONTRACT.endpoint(),
            PRODUCTION_DEPLOYMENT_RUNBOOK_CONTRACT.endpoint()));
    return List.copyOf(endpoints);
  }

  public static List<String> staticContractProbeEndpoints(boolean includeFieldGuide) {
    return staticContractEndpoints(includeFieldGuide).stream()
        .map(endpoint -> "GET " + endpoint)
        .toList();
  }

  private static <T> T section(EnumMap<Section, Object> sections, Section section, Class<T> type) {
    return type.cast(sections.get(section));
  }

  public record StaticReleaseEvidence(
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

  public enum Artifact {
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
        "java-release-audit-retention-fixture.v1",
        "/contracts/release-audit-retention.fixture.json"),
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

    Artifact(String version, String endpoint) {
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

  private record DispatchEntry(Section section, Supplier<Object> build) {}

  private enum Section {
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
