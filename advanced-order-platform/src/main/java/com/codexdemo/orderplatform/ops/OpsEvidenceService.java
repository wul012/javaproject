package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.notification.FailedEventSummaryResponse;
import com.codexdemo.orderplatform.notification.FailedEventSummaryService;
import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalContractConstants;
import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalRehearsalRequest;
import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalRehearsalResponse;
import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalRehearsalResponseBuilder;
import com.codexdemo.orderplatform.order.IdempotencyStore;
import com.codexdemo.orderplatform.order.IdempotencyStoreDescriptor;
import com.codexdemo.orderplatform.order.JpaIdempotencyStore;
import com.codexdemo.orderplatform.outbox.OutboxPublisherProperties;
import com.codexdemo.orderplatform.outbox.OutboxRabbitMqProperties;
import com.codexdemo.orderplatform.outbox.OutboxRepository;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsEvidenceService {

  static final String EVIDENCE_VERSION = "java-ops-evidence.v1";

  static final String RELEASE_APPROVAL_REHEARSAL_APPROVAL_HANDOFF_MARKER_SCHEMA_VERSION =
      "java-release-approval-rehearsal-response-schema.v10";

  static final String RELEASE_APPROVAL_REHEARSAL_MANAGED_AUDIT_ADAPTER_BOUNDARY_SCHEMA_VERSION =
      "java-release-approval-rehearsal-response-schema.v11";

  static final String RELEASE_APPROVAL_REHEARSAL_PRODUCTION_ADAPTER_PREREQUISITE_SCHEMA_VERSION =
      "java-release-approval-rehearsal-response-schema.v12";

  static final String RELEASE_APPROVAL_REHEARSAL_OPS_EVIDENCE_SERVICE_QUALITY_SPLIT_SCHEMA_VERSION =
      "java-release-approval-rehearsal-response-schema.v13";

  static final String
      RELEASE_APPROVAL_REHEARSAL_MANAGED_AUDIT_ADAPTER_IMPLEMENTATION_GUARD_SCHEMA_VERSION =
          "java-release-approval-rehearsal-response-schema.v14";

  static final String
      RELEASE_APPROVAL_REHEARSAL_MANAGED_AUDIT_EXTERNAL_ADAPTER_MIGRATION_GUARD_SCHEMA_VERSION =
          "java-release-approval-rehearsal-response-schema.v15";

  public static final String
      RELEASE_APPROVAL_REHEARSAL_MANAGED_AUDIT_SANDBOX_CONNECTION_PRECHECK_PACKET_ECHO_RECEIPT_SCHEMA_VERSION =
          ReleaseApprovalContractConstants
              .RELEASE_APPROVAL_REHEARSAL_MANAGED_AUDIT_SANDBOX_CONNECTION_PRECHECK_PACKET_ECHO_RECEIPT_SCHEMA_VERSION;

  static final String
      RELEASE_APPROVAL_REHEARSAL_MANAGED_AUDIT_SANDBOX_ENDPOINT_CREDENTIAL_RESOLVER_ABORT_ROLLBACK_SEMANTICS_CONTRACT_ECHO_RECEIPT_SCHEMA_VERSION =
          "java-release-approval-rehearsal-response-schema.v50";

  static final String NODE_V214_RESTORE_DRILL_ARCHIVE_VERIFICATION_PROFILE_VERSION =
      "managed-audit-restore-drill-archive-verification.v1";

  static final String NODE_V214_RESTORE_DRILL_ARCHIVE_VERIFICATION_STATE =
      "verified-restore-drill-archive";

  static final String NODE_V214_RESTORE_DRILL_ARCHIVE_VERIFICATION_ENDPOINT =
      "/api/v1/audit/managed-audit-restore-drill-archive-verification";

  static final String NODE_V215_MANAGED_AUDIT_DRY_RUN_ADAPTER_CANDIDATE_VERSION = "Node v215";

  static final String NODE_V215_MANAGED_AUDIT_DRY_RUN_ADAPTER_CANDIDATE_PROFILE =
      "managed-audit-dry-run-adapter-candidate.v1";

  static final String NODE_V216_DRY_RUN_ADAPTER_ARCHIVE_VERIFICATION_PROFILE_VERSION =
      "managed-audit-dry-run-adapter-archive-verification.v1";

  static final String NODE_V216_DRY_RUN_ADAPTER_ARCHIVE_VERIFICATION_STATE =
      "verified-dry-run-adapter-archive";

  static final String NODE_V216_DRY_RUN_ADAPTER_ARCHIVE_VERIFICATION_ENDPOINT =
      "/api/v1/audit/managed-audit-dry-run-adapter-archive-verification";

  static final String NODE_V217_PRODUCTION_HARDENING_READINESS_GATE_VERSION = "Node v217";

  static final String NODE_V217_PRODUCTION_HARDENING_READINESS_GATE_PROFILE =
      "managed-audit-adapter-production-hardening-readiness-gate.v1";

  static final String NODE_V218_AUDIT_ROUTE_MANAGED_AUDIT_HELPER_QUALITY_PASS_VERSION = "Node v218";

  static final String NODE_V218_AUDIT_ROUTE_MANAGED_AUDIT_HELPER_QUALITY_PASS_PROFILE =
      "audit-route-managed-audit-helper-quality-pass.v1";

  static final String NODE_V219_MANAGED_AUDIT_ADAPTER_IMPLEMENTATION_PRECHECK_VERSION = "Node v219";

  static final String NODE_V219_MANAGED_AUDIT_ADAPTER_IMPLEMENTATION_PRECHECK_PROFILE =
      "managed-audit-adapter-implementation-precheck-packet.v1";

  static final String NODE_V220_MANAGED_AUDIT_ADAPTER_DISABLED_SHELL_VERSION = "Node v220";

  static final String NODE_V220_MANAGED_AUDIT_ADAPTER_DISABLED_SHELL_PROFILE =
      "managed-audit-adapter-disabled-shell.v1";

  static final String NODE_V220_MANAGED_AUDIT_ADAPTER_DISABLED_SHELL_ENDPOINT =
      "/api/v1/audit/managed-audit-adapter-disabled-shell";

  static final String NODE_V221_MANAGED_AUDIT_LOCAL_ADAPTER_CANDIDATE_DRY_RUN_VERSION = "Node v221";

  static final String NODE_V221_MANAGED_AUDIT_LOCAL_ADAPTER_CANDIDATE_DRY_RUN_PROFILE =
      "managed-audit-local-adapter-candidate-dry-run.v1";

  static final String NODE_V222_MANAGED_AUDIT_LOCAL_ADAPTER_CANDIDATE_VERIFICATION_REPORT_VERSION =
      "Node v222";

  static final String NODE_V222_MANAGED_AUDIT_LOCAL_ADAPTER_CANDIDATE_VERIFICATION_REPORT_PROFILE =
      "managed-audit-local-adapter-candidate-verification-report.v1";

  static final String NODE_V222_MANAGED_AUDIT_LOCAL_ADAPTER_CANDIDATE_VERIFICATION_REPORT_ENDPOINT =
      "/api/v1/audit/managed-audit-local-adapter-candidate-verification-report";

  static final String NODE_V223_MANAGED_AUDIT_EXTERNAL_ADAPTER_CONNECTION_READINESS_REVIEW_VERSION =
      "Node v223";

  static final String NODE_V223_MANAGED_AUDIT_EXTERNAL_ADAPTER_CONNECTION_READINESS_REVIEW_PROFILE =
      "managed-audit-external-adapter-connection-readiness-review.v1";

  static final String
      NODE_V265_SANDBOX_ENDPOINT_CREDENTIAL_RESOLVER_TEST_ONLY_SHELL_UPSTREAM_ECHO_VERIFICATION_MARKDOWN_ENDPOINT =
          "/api/v1/audit/managed-audit-manual-sandbox-connection-sandbox-endpoint-credential-resolver-test-only-shell-upstream-echo-verification?format=markdown";

  static final String
      NODE_V269_CREDENTIAL_RESOLVER_PRODUCTION_READINESS_BLOCKED_DECISION_UPSTREAM_ECHO_VERIFICATION_ENDPOINT =
          "/api/v1/audit/managed-audit-manual-sandbox-connection-credential-resolver-production-readiness-blocked-decision-upstream-echo-verification";

  static final String
      NODE_V269_CREDENTIAL_RESOLVER_PRODUCTION_READINESS_BLOCKED_DECISION_UPSTREAM_ECHO_VERIFICATION_MARKDOWN_ENDPOINT =
          "/api/v1/audit/managed-audit-manual-sandbox-connection-credential-resolver-production-readiness-blocked-decision-upstream-echo-verification?format=markdown";

  static final String
      NODE_V272_CREDENTIAL_RESOLVER_PRE_IMPLEMENTATION_PLAN_INTAKE_UPSTREAM_ECHO_VERIFICATION_ENDPOINT =
          "/api/v1/audit/managed-audit-manual-sandbox-connection-credential-resolver-pre-implementation-plan-intake-upstream-echo-verification";

  static final String
      NODE_V272_CREDENTIAL_RESOLVER_PRE_IMPLEMENTATION_PLAN_INTAKE_UPSTREAM_ECHO_VERIFICATION_MARKDOWN_ENDPOINT =
          "/api/v1/audit/managed-audit-manual-sandbox-connection-credential-resolver-pre-implementation-plan-intake-upstream-echo-verification?format=markdown";

  static final String
      NODE_V272_CREDENTIAL_RESOLVER_PRE_IMPLEMENTATION_PLAN_INTAKE_UPSTREAM_ECHO_VERIFICATION_STATE =
          "credential-resolver-pre-implementation-plan-intake-upstream-echo-verification-ready";

  private static final String REAL_REPLAY_ENDPOINT = "/api/v1/failed-events/{id}/replay";

  private final Instant startedAt = Instant.now();

  private final FailedEventSummaryService failedEventSummaryService;

  private final OutboxRepository outboxRepository;

  private final OutboxPublisherProperties outboxPublisherProperties;

  private final OutboxRabbitMqProperties outboxRabbitMqProperties;

  private final IdempotencyStore idempotencyStore;

  private final Environment environment;

  public OpsEvidenceService(
      FailedEventSummaryService failedEventSummaryService,
      OutboxRepository outboxRepository,
      OutboxPublisherProperties outboxPublisherProperties,
      OutboxRabbitMqProperties outboxRabbitMqProperties,
      IdempotencyStore idempotencyStore,
      Environment environment) {
    this.failedEventSummaryService = failedEventSummaryService;
    this.outboxRepository = outboxRepository;
    this.outboxPublisherProperties = outboxPublisherProperties;
    this.outboxRabbitMqProperties = outboxRabbitMqProperties;
    this.idempotencyStore = idempotencyStore;
    this.environment = environment;
  }

  @Transactional(readOnly = true)
  public OpsEvidenceResponse evidence() {
    Instant sampledAt = Instant.now();
    FailedEventSummaryResponse failedEventSummary = failedEventSummaryService.summary();
    long pendingOutboxEvents = outboxRepository.countByPublishedAtIsNull();
    List<String> outboxBlockers = outboxBlockers();
    List<String> executionBlockers = executionBlockers(failedEventSummary);
    List<String> blockers = blockers(outboxBlockers, executionBlockers);
    OpsEvidenceStaticReleaseDispatchTable.StaticReleaseEvidence staticReleaseEvidence =
        OpsEvidenceStaticReleaseDispatchTable.build();

    return new OpsEvidenceResponse(
        sampledAt,
        EVIDENCE_VERSION,
        service(sampledAt),
        healthProbe(false),
        true,
        false,
        readOnlyWindow(true),
        orderIdempotency(),
        staticReleaseEvidence.releaseVerification(),
        staticReleaseEvidence.deploymentRollback(),
        staticReleaseEvidence.releaseBundle(),
        staticReleaseEvidence.releaseHandoffChecklistFixture(),
        staticReleaseEvidence.releaseAuditRetentionFixture(),
        staticReleaseEvidence.releaseOperatorSignoffFixture(),
        staticReleaseEvidence.rollbackApproverEvidenceFixture(),
        staticReleaseEvidence.rollbackApprovalHandoff(),
        staticReleaseEvidence.rollbackApprovalRecordFixture(),
        staticReleaseEvidence.rollbackSqlReviewGate(),
        staticReleaseEvidence.productionSecretSourceContract(),
        staticReleaseEvidence.productionDeploymentRunbookContract(),
        failedEventReplay(failedEventSummary),
        outbox(pendingOutboxEvents, outboxBlockers),
        approvalExecution(executionBlockers),
        blockers,
        warnings(failedEventSummary, pendingOutboxEvents),
        evidenceEndpoints());
  }

  @Transactional(readOnly = true)
  public ReleaseApprovalRehearsalResponse releaseApprovalRehearsal() {
    return releaseApprovalRehearsal(ReleaseApprovalRehearsalRequest.empty());
  }

  @Transactional(readOnly = true)
  public ReleaseApprovalRehearsalResponse releaseApprovalRehearsal(
      ReleaseApprovalRehearsalRequest request) {
    return new ReleaseApprovalRehearsalResponseBuilder().build(evidence(), request);
  }

  private OpsEvidenceResponse.Service service(Instant sampledAt) {
    return new OpsEvidenceResponse.Service(
        environment.getProperty("spring.application.name", "advanced-order-platform"),
        environment.getProperty("info.app.version", "0.1.0-SNAPSHOT"),
        profiles(),
        startedAt,
        Math.max(Duration.between(startedAt, sampledAt).toSeconds(), 0));
  }

  private OpsEvidenceResponse.HealthProbe healthProbe(boolean staticSampleOnly) {
    List<String> additionalProbeEndpoints = new ArrayList<>();
    additionalProbeEndpoints.add("/api/v1/ops/overview");
    additionalProbeEndpoints.addAll(OpsShardReadinessEvidenceEndpoints.liveEndpoints());
    additionalProbeEndpoints.add(
        ReleaseApprovalContractConstants.RELEASE_APPROVAL_REHEARSAL_ENDPOINT);
    additionalProbeEndpoints.addAll(OpsShardReadinessEvidenceEndpoints.fixtureEndpoints());
    additionalProbeEndpoints.addAll(
        OpsEvidenceStaticReleaseDispatchTable.staticContractEndpoints(false));

    return new OpsEvidenceResponse.HealthProbe(
        "/actuator/health",
        "GET",
        "UP",
        "/api/v1/ops/evidence",
        List.copyOf(additionalProbeEndpoints),
        true,
        staticSampleOnly);
  }

  private OpsEvidenceResponse.ReadOnlyWindow readOnlyWindow(boolean readyForReadOnlyLiveProbe) {
    List<String> allowedProbeEndpoints = new ArrayList<>();
    allowedProbeEndpoints.add("GET /actuator/health");
    allowedProbeEndpoints.add("GET /api/v1/ops/overview");
    allowedProbeEndpoints.add("GET /api/v1/ops/evidence");
    allowedProbeEndpoints.addAll(OpsShardReadinessEvidenceEndpoints.liveProbeEndpoints());
    allowedProbeEndpoints.add(
        "GET " + ReleaseApprovalContractConstants.RELEASE_APPROVAL_REHEARSAL_ENDPOINT);
    allowedProbeEndpoints.addAll(OpsShardReadinessEvidenceEndpoints.fixtureProbeEndpoints());
    allowedProbeEndpoints.addAll(
        OpsEvidenceStaticReleaseDispatchTable.staticContractProbeEndpoints(false));

    return new OpsEvidenceResponse.ReadOnlyWindow(
        "java-read-only-window.v1",
        true,
        false,
        true,
        false,
        readyForReadOnlyLiveProbe,
        false,
        List.copyOf(allowedProbeEndpoints),
        List.of(
            "POST /api/v1/orders",
            "POST /api/v1/failed-events/{id}/replay",
            "RabbitMQ replay publish",
            "Outbox mutation",
            "Any non-GET Node upstream action"),
        List.of("UPSTREAM_PROBES_ENABLED=true", "UPSTREAM_ACTIONS_ENABLED=false"),
        "Node real-read window must not call POST /api/v1/failed-events/{id}/replay");
  }

  private OpsEvidenceResponse.OrderIdempotency orderIdempotency() {
    IdempotencyStoreDescriptor descriptor = idempotencyStore.descriptor();
    return new OpsEvidenceResponse.OrderIdempotency(
        "java-order-idempotency-boundary.v1",
        descriptor.abstractionVersion(),
        "/api/v1/orders",
        "POST",
        "Idempotency-Key",
        120,
        "order-create-request-sha256.v1",
        "customerId plus aggregated productId:quantity pairs sorted by productId",
        "HTTP 200 replay of the existing order without a second inventory reservation or outbox event",
        "HTTP 409 conflict before inventory reservation and before outbox mutation",
        "IDEMPOTENCY_KEY_REUSED_WITH_DIFFERENT_REQUEST",
        descriptor.activeStore(),
        descriptor.activeImplementation(),
        descriptor.activeMode(),
        descriptor.authoritativeStore()
            + " via "
            + descriptor.keyColumn()
            + " and "
            + descriptor.fingerprintColumn(),
        List.of(
            new OpsEvidenceResponse.IdempotencyStoreCandidate(
                descriptor.activeStore(),
                "ORDER_CREATE_IDEMPOTENCY_AUTHORITY",
                true,
                true,
                descriptor.activeMode(),
                "Default Java database-backed idempotency store"),
            new OpsEvidenceResponse.IdempotencyStoreCandidate(
                JpaIdempotencyStore.MINI_KV_CANDIDATE,
                "TTL_TOKEN_CANDIDATE",
                descriptor.miniKvAdapterEnabled(),
                descriptor.miniKvConnected(),
                descriptor.miniKvCandidateMode(),
                descriptor.disabledCandidateReason())),
        descriptor.miniKvConnected(),
        descriptor.externalTokenStoreConnected(),
        descriptor.changesPaymentOrInventoryTransaction());
  }

  private List<String> profiles() {
    String[] activeProfiles = environment.getActiveProfiles();
    if (activeProfiles.length > 0) {
      return List.copyOf(Arrays.asList(activeProfiles));
    }
    return List.copyOf(Arrays.asList(environment.getDefaultProfiles()));
  }

  private OpsEvidenceResponse.FailedEventReplay failedEventReplay(
      FailedEventSummaryResponse summary) {
    return new OpsEvidenceResponse.FailedEventReplay(
        summary.totalFailedEvents(),
        summary.replayBacklog(),
        summary.pendingReplayApprovals(),
        summary.approvedReplayApprovals(),
        summary.rejectedReplayApprovals(),
        summary.latestFailedAt(),
        summary.latestApprovalAt(),
        REAL_REPLAY_ENDPOINT,
        false);
  }

  private OpsEvidenceResponse.Outbox outbox(long pendingOutboxEvents, List<String> outboxBlockers) {
    return new OpsEvidenceResponse.Outbox(
        pendingOutboxEvents,
        outboxPublisherProperties.isEnabled(),
        outboxRabbitMqProperties.isEnabled(),
        outboxRabbitMqProperties.getExchange(),
        outboxRabbitMqProperties.getQueue(),
        outboxRabbitMqProperties.getDeadLetterQueue(),
        outboxBlockers);
  }

  private OpsEvidenceResponse.ApprovalExecution approvalExecution(List<String> executionBlockers) {
    return new OpsEvidenceResponse.ApprovalExecution(
        "APPROVED",
        "contractDigest must match latest approval-status/readiness evidence before POST /replay",
        true,
        true,
        executionBlockers,
        List.of(
            "GET /api/v1/failed-events/summary",
            "GET /api/v1/failed-events/{id}/replay-readiness",
            "GET /api/v1/failed-events/{id}/replay-execution-contract"));
  }

  private List<String> outboxBlockers() {
    List<String> blockers = new ArrayList<>();
    if (!outboxPublisherProperties.isEnabled()) {
      blockers.add("OUTBOX_PUBLISHER_DISABLED");
    }
    if (!outboxRabbitMqProperties.isEnabled()) {
      blockers.add("RABBITMQ_OUTBOX_DISABLED");
    }
    return List.copyOf(blockers);
  }

  private List<String> executionBlockers(FailedEventSummaryResponse summary) {
    List<String> blockers = new ArrayList<>();
    blockers.add("READ_ONLY_EVIDENCE_ENDPOINT");
    if (summary.pendingReplayApprovals() > 0) {
      blockers.add("REPLAY_APPROVAL_PENDING");
    }
    if (summary.rejectedReplayApprovals() > 0) {
      blockers.add("REPLAY_APPROVAL_REJECTED");
    }
    if (summary.replayBacklog() > 0) {
      blockers.add("REPLAY_BACKLOG_PRESENT");
    }
    return List.copyOf(blockers);
  }

  private List<String> blockers(List<String> outboxBlockers, List<String> executionBlockers) {
    List<String> blockers = new ArrayList<>();
    blockers.addAll(executionBlockers);
    blockers.addAll(outboxBlockers);
    return List.copyOf(blockers);
  }

  private List<String> warnings(FailedEventSummaryResponse summary, long pendingOutboxEvents) {
    List<String> warnings = new ArrayList<>();
    if (pendingOutboxEvents > 0) {
      warnings.add("OUTBOX_PENDING_EVENTS");
    }
    if (summary.approvedReplayApprovals() > 0) {
      warnings.add("APPROVED_REPLAY_REQUIRES_DIGEST_CHECK");
    }
    return List.copyOf(warnings);
  }

  private List<String> evidenceEndpoints() {
    List<String> endpoints = new ArrayList<>();
    endpoints.add("/api/v1/ops/overview");
    endpoints.add("/api/v1/ops/evidence");
    endpoints.addAll(OpsShardReadinessEvidenceEndpoints.liveEndpoints());
    endpoints.add(ReleaseApprovalContractConstants.RELEASE_APPROVAL_REHEARSAL_ENDPOINT);
    endpoints.addAll(OpsShardReadinessEvidenceEndpoints.fixtureEndpoints());
    endpoints.addAll(OpsEvidenceStaticReleaseDispatchTable.staticContractEndpoints(true));
    endpoints.addAll(
        List.of(
            "/api/v1/failed-events/summary",
            "/api/v1/failed-events/{id}/approval-status",
            "/api/v1/failed-events/{id}/replay-readiness",
            "/api/v1/failed-events/{id}/replay-execution-contract",
            "/api/v1/failed-events/replay-evidence-index",
            "/contracts/failed-event-replay-execution-contract-approved.sample.json",
            "/contracts/failed-event-replay-execution-contract-blocked.sample.json"));
    return List.copyOf(endpoints);
  }
}
