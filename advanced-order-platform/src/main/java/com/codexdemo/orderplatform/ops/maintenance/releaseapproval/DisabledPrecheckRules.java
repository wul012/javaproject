package com.codexdemo.orderplatform.ops.maintenance.releaseapproval;

import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalSandboxEndpointCredentialResolverDisabledPrecheckEchoRecords.RehearsalSandboxEndpointCredentialResolverDisabledPrecheckRecord;
import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalSandboxEndpointCredentialResolverDisabledPrecheckEchoRecords.RehearsalSandboxEndpointCredentialResolverDisabledPrecheckSideEffectBoundary;
import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalSandboxEndpointCredentialResolverDisabledPrecheckEchoRecords.RehearsalSandboxEndpointCredentialResolverDisabledPrecheckSourceEcho;
import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalSandboxEndpointCredentialResolverDisabledPrecheckEchoRecords.RehearsalSandboxEndpointCredentialResolverDryRunResponseShape;
import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalSandboxEndpointCredentialResolverDisabledPrecheckEchoRecords.RehearsalSandboxEndpointCredentialResolverEnvHandle;
import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalSandboxEndpointCredentialResolverDisabledPrecheckEchoRecords.RehearsalSandboxEndpointCredentialResolverFailureClass;
import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalSandboxEndpointCredentialResolverDisabledPrecheckEchoRecords.RehearsalSandboxEndpointCredentialResolverOptInGate;
import java.util.List;

final class DisabledPrecheckRules {

  static final String PRECHECK_MODE = "sandbox-endpoint-credential-resolver-disabled-precheck-only";
  static final String RESOLVER_IMPLEMENTATION_STATUS = "not-implemented";
  static final String SECRET_PROVIDER_IMPLEMENTATION_STATUS = "not-implemented";
  static final String SOURCE_SPAN = "Node v261 credential resolver upstream echo verification";
  static final String VERIFICATION_MODE =
      "java-v105-plus-mini-kv-v114-credential-resolver-upstream-echo-verification-only";
  static final String NODE_V261_SOURCE_SPAN = "Node v260 + Java v105 + mini-kv v114";
  static final String DRY_RUN_READY_STATE =
      "sandbox-endpoint-credential-resolver-disabled-precheck-ready";
  static final int SOURCE_CHECK_COUNT = 20;
  static final int SOURCE_PASSED_CHECK_COUNT = 20;
  static final int SOURCE_PRODUCTION_BLOCKER_COUNT = 0;
  static final int SOURCE_WARNING_COUNT = 2;
  static final int SOURCE_RECOMMENDATION_COUNT = 2;

  static final List<String> REQUIRED_ENV_HANDLE_NAMES =
      List.of(
          "ORDEROPS_MANAGED_AUDIT_CREDENTIAL_RESOLVER_ENABLED",
          "ORDEROPS_MANAGED_AUDIT_SANDBOX_ENDPOINT_RESOLUTION_ENABLED",
          "ORDEROPS_MANAGED_AUDIT_SANDBOX_ENDPOINT_HANDLE",
          "ORDEROPS_MANAGED_AUDIT_SANDBOX_CREDENTIAL_HANDLE",
          "ORDEROPS_MANAGED_AUDIT_SANDBOX_CREDENTIAL_RESOLVER_POLICY_HANDLE",
          "ORDEROPS_MANAGED_AUDIT_CREDENTIAL_RESOLVER_APPROVAL_MARKER");

  static final List<String> OPT_IN_GATE_NAMES =
      List.of(
          "ORDEROPS_MANAGED_AUDIT_CREDENTIAL_RESOLVER_ENABLED",
          "ORDEROPS_MANAGED_AUDIT_SANDBOX_ENDPOINT_RESOLUTION_ENABLED");

  static final List<String> FAILURE_CLASS_CODES =
      List.of(
          "RESOLVER_DISABLED",
          "APPROVAL_MARKER_MISSING",
          "CREDENTIAL_HANDLE_MISSING",
          "CREDENTIAL_VALUE_REQUESTED",
          "RAW_ENDPOINT_URL_REQUESTED",
          "EXTERNAL_REQUEST_REQUESTED",
          "SCHEMA_MIGRATION_REQUESTED");

  static final List<String> DRY_RUN_RESPONSE_FIELDS =
      List.of(
          "readyState",
          "resolverMode",
          "resolverClientInstantiated",
          "secretProviderInstantiated",
          "credentialValueRead",
          "credentialValueLoaded",
          "rawEndpointUrlParsed",
          "externalRequestSent",
          "connectsManagedAudit",
          "schemaMigrationExecuted",
          "failureClassCount",
          "nextAction");

  static final List<String> INHERITED_NO_GO_CONDITIONS =
      List.of(
          "CREDENTIAL_VALUE_REQUIRED",
          "RAW_ENDPOINT_URL_REQUIRED",
          "REAL_CONNECTION_REQUIRED",
          "EXTERNAL_REQUEST_REQUIRED",
          "SCHEMA_MIGRATION_REQUIRED",
          "UPSTREAM_WRITE_REQUIRED",
          "AUTO_START_REQUIRED",
          "MINI_KV_BACKEND_REQUIRED",
          "PRODUCTION_WINDOW_REQUIRED");

  static final List<String> NODE_WARNING_CODES =
      List.of("DISABLED_PRECHECK_ONLY", "UPSTREAM_ECHO_REQUIRED_NEXT");

  static final List<String> NODE_RECOMMENDATION_CODES =
      List.of("ASK_JAVA_MINI_KV_FOR_ECHO_NEXT", "KEEP_REAL_RESOLVER_OUT_OF_SCOPE");

  static final List<String> NEXT_REQUIRED_ECHO_VERSIONS =
      List.of(
          "Java v106 sandbox endpoint credential resolver disabled precheck echo marker",
          "mini-kv v115 sandbox endpoint credential resolver disabled precheck non-participation receipt");

  static RehearsalSandboxEndpointCredentialResolverDisabledPrecheckSourceEcho sourceNodeV261(
      SourceGate sourceGate) {
    return new RehearsalSandboxEndpointCredentialResolverDisabledPrecheckSourceEcho(
        ReleaseApprovalUpstreamContractConstants
            .NODE_V261_SANDBOX_ENDPOINT_CREDENTIAL_RESOLVER_UPSTREAM_ECHO_VERIFICATION_VERSION,
        ReleaseApprovalUpstreamContractConstants
            .NODE_V261_SANDBOX_ENDPOINT_CREDENTIAL_RESOLVER_UPSTREAM_ECHO_VERIFICATION_PROFILE,
        ReleaseApprovalUpstreamContractConstants
            .NODE_V261_SANDBOX_ENDPOINT_CREDENTIAL_RESOLVER_UPSTREAM_ECHO_VERIFICATION_STATE,
        VERIFICATION_MODE,
        NODE_V261_SOURCE_SPAN,
        sourceGate.sourceAccepted(),
        sourceGate.sourceAccepted(),
        sourceGate.sourceAccepted(),
        sourceGate.sourceAccepted(),
        sourceGate.sourceAccepted(),
        sourceGate.sourceAccepted(),
        sourceGate.sourceAccepted(),
        sourceGate.sourceAccepted(),
        sourceGate.sourceAccepted(),
        sourceGate.sourceAccepted(),
        sourceGate.sourceAccepted(),
        sourceGate.sourceAccepted(),
        sourceGate.sourceAccepted(),
        sourceGate.sourceAccepted(),
        sourceGate.sourceAccepted(),
        sourceGate.sourceAccepted(),
        sourceGate.sourceAccepted(),
        sourceGate.sourceAccepted(),
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        SOURCE_CHECK_COUNT,
        SOURCE_PASSED_CHECK_COUNT,
        SOURCE_PRODUCTION_BLOCKER_COUNT,
        SOURCE_WARNING_COUNT,
        SOURCE_RECOMMENDATION_COUNT,
        sourceGate.sourceAccepted());
  }

  static RehearsalSandboxEndpointCredentialResolverDisabledPrecheckRecord disabledPrecheck() {
    List<RehearsalSandboxEndpointCredentialResolverEnvHandle> requiredEnvHandles =
        requiredEnvHandles();
    List<RehearsalSandboxEndpointCredentialResolverOptInGate> optInGates = optInGates();
    List<RehearsalSandboxEndpointCredentialResolverFailureClass> failureTaxonomy =
        failureTaxonomy();
    RehearsalSandboxEndpointCredentialResolverDryRunResponseShape dryRunResponseShape =
        dryRunResponseShape();
    String precheckDigest =
        ReleaseApprovalDigestSupport.digest(
            List.of(
                ReleaseApprovalDigestSupport.line("precheckMode", PRECHECK_MODE),
                ReleaseApprovalDigestSupport.line("requiredEnvHandles", requiredEnvHandles),
                ReleaseApprovalDigestSupport.line("optInGates", optInGates),
                ReleaseApprovalDigestSupport.line("failureTaxonomy", failureTaxonomy),
                ReleaseApprovalDigestSupport.line("dryRunResponseShape", dryRunResponseShape),
                ReleaseApprovalDigestSupport.line(
                    "inheritedNoGoConditions", INHERITED_NO_GO_CONDITIONS)));
    return new RehearsalSandboxEndpointCredentialResolverDisabledPrecheckRecord(
        precheckDigest,
        PRECHECK_MODE,
        RESOLVER_IMPLEMENTATION_STATUS,
        SECRET_PROVIDER_IMPLEMENTATION_STATUS,
        false,
        false,
        false,
        false,
        false,
        true,
        REQUIRED_ENV_HANDLE_NAMES.size(),
        OPT_IN_GATE_NAMES.size(),
        FAILURE_CLASS_CODES.size(),
        DRY_RUN_RESPONSE_FIELDS.size(),
        INHERITED_NO_GO_CONDITIONS.size(),
        requiredEnvHandles,
        optInGates,
        failureTaxonomy,
        dryRunResponseShape,
        INHERITED_NO_GO_CONDITIONS);
  }

  static List<RehearsalSandboxEndpointCredentialResolverEnvHandle> requiredEnvHandles() {
    return List.of(
        envHandle(
            "ORDEROPS_MANAGED_AUDIT_CREDENTIAL_RESOLVER_ENABLED",
            "future opt-in gate for the credential resolver",
            false),
        envHandle(
            "ORDEROPS_MANAGED_AUDIT_SANDBOX_ENDPOINT_RESOLUTION_ENABLED",
            "future opt-in gate for sandbox endpoint resolution",
            false),
        envHandle(
            "ORDEROPS_MANAGED_AUDIT_SANDBOX_ENDPOINT_HANDLE",
            "handle for the approved sandbox endpoint",
            true),
        envHandle(
            "ORDEROPS_MANAGED_AUDIT_SANDBOX_CREDENTIAL_HANDLE",
            "handle for the approved sandbox credential",
            true),
        envHandle(
            "ORDEROPS_MANAGED_AUDIT_SANDBOX_CREDENTIAL_RESOLVER_POLICY_HANDLE",
            "handle for the resolver policy review",
            true),
        envHandle(
            "ORDEROPS_MANAGED_AUDIT_CREDENTIAL_RESOLVER_APPROVAL_MARKER",
            "operator approval marker for future resolver design",
            true));
  }

  static RehearsalSandboxEndpointCredentialResolverEnvHandle envHandle(
      String name, String purpose, boolean requiredBeforeRealResolver) {
    return new RehearsalSandboxEndpointCredentialResolverEnvHandle(
        name, purpose, false, false, false, requiredBeforeRealResolver);
  }

  static List<RehearsalSandboxEndpointCredentialResolverOptInGate> optInGates() {
    return OPT_IN_GATE_NAMES.stream()
        .map(
            gateName ->
                new RehearsalSandboxEndpointCredentialResolverOptInGate(
                    gateName, "true", "false", true, true))
        .toList();
  }

  static List<RehearsalSandboxEndpointCredentialResolverFailureClass> failureTaxonomy() {
    return List.of(
        failureClass("RESOLVER_DISABLED", "configuration", false, "pause-and-review"),
        failureClass("APPROVAL_MARKER_MISSING", "operator-boundary", false, "pause-and-review"),
        failureClass("CREDENTIAL_HANDLE_MISSING", "credential-boundary", false, "pause-and-review"),
        failureClass(
            "CREDENTIAL_VALUE_REQUESTED", "credential-boundary", false, "pause-and-do-not-resolve"),
        failureClass(
            "RAW_ENDPOINT_URL_REQUESTED", "endpoint-boundary", false, "pause-and-do-not-resolve"),
        failureClass(
            "EXTERNAL_REQUEST_REQUESTED", "network-boundary", false, "pause-and-do-not-resolve"),
        failureClass(
            "SCHEMA_MIGRATION_REQUESTED", "schema-boundary", false, "pause-and-do-not-resolve"));
  }

  static RehearsalSandboxEndpointCredentialResolverFailureClass failureClass(
      String code, String source, boolean retryable, String action) {
    return new RehearsalSandboxEndpointCredentialResolverFailureClass(
        code, source, retryable, action);
  }

  static RehearsalSandboxEndpointCredentialResolverDryRunResponseShape dryRunResponseShape() {
    return new RehearsalSandboxEndpointCredentialResolverDryRunResponseShape(
        DRY_RUN_RESPONSE_FIELDS,
        DRY_RUN_READY_STATE,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false);
  }

  static RehearsalSandboxEndpointCredentialResolverDisabledPrecheckSideEffectBoundary
      sideEffectBoundary() {
    return new RehearsalSandboxEndpointCredentialResolverDisabledPrecheckSideEffectBoundary(
        true, true, false, false, false, false, false, false, false, false, false, false, false,
        false, false, false, false, false, false, false, false, false);
  }

  static boolean disabledPrecheckSideEffectsBlocked(
      RehearsalSandboxEndpointCredentialResolverDisabledPrecheckRecord precheck,
      RehearsalSandboxEndpointCredentialResolverDisabledPrecheckSideEffectBoundary boundary) {
    return boundary.readOnlyDisabledPrecheck()
        && boundary.disabledCredentialResolverPrecheckOnly()
        && !boundary.credentialResolverExecutionAllowed()
        && !boundary.readyForManagedAuditSandboxAdapterConnection()
        && !boundary.readyForProductionAudit()
        && !boundary.readyForProductionWindow()
        && !boundary.readyForProductionOperations()
        && !boundary.executionAllowed()
        && !boundary.connectsManagedAudit()
        && !boundary.readsManagedAuditCredential()
        && !boundary.storesManagedAuditCredential()
        && !boundary.credentialValueRead()
        && !boundary.credentialValueLoaded()
        && !boundary.credentialValueStored()
        && !boundary.credentialValueIncluded()
        && !boundary.rawEndpointUrlParsed()
        && !boundary.rawEndpointUrlIncluded()
        && !boundary.externalRequestSent()
        && !boundary.secretProviderInstantiated()
        && !boundary.resolverClientInstantiated()
        && !boundary.schemaMigrationExecuted()
        && !boundary.automaticUpstreamStart()
        && !precheck.resolverClientMayBeInstantiated()
        && !precheck.secretProviderMayBeInstantiated()
        && !precheck.credentialValueMayBeLoaded()
        && !precheck.rawEndpointUrlMayBeParsed()
        && !precheck.externalRequestMayBeSent();
  }

  static List<String> markerWarnings(EchoReadiness readiness) {
    return ReleaseApprovalDigestSupport.warnings(
        ReleaseApprovalDigestSupport.warningIf(
            !readiness.sourceNodeV261Echoed(),
            "NODE_V262_SOURCE_CREDENTIAL_RESOLVER_UPSTREAM_ECHO_NOT_READY"),
        ReleaseApprovalDigestSupport.warningIf(
            !readiness.envHandlesEchoed() || !readiness.optInGatesEchoed(),
            "NODE_V262_DISABLED_PRECHECK_HANDLE_OR_GATE_MISMATCH"),
        ReleaseApprovalDigestSupport.warningIf(
            !readiness.sideEffectBoundaryEchoed(),
            "NODE_V262_DISABLED_PRECHECK_SIDE_EFFECT_BOUNDARY_OPEN"));
  }

  record SourceGate(boolean sourceAccepted) {

    static SourceGate from(
        ReleaseApprovalSandboxEndpointCredentialResolverDecisionEchoRecords
                .RehearsalManagedAuditSandboxEndpointCredentialResolverDecisionEchoMarker
            marker) {
      return new SourceGate(
          marker.readyForNodeV261SandboxEndpointCredentialResolverUpstreamEchoVerification()
              && marker.markerWarnings().isEmpty()
              && marker.sourceNodeV259Echoed()
              && marker.decisionFieldsEchoed()
              && marker.explicitNoGoConditionsEchoed()
              && marker.sideEffectBoundaryEchoed()
              && !marker.readyForManagedAuditSandboxAdapterConnection()
              && !marker.readyForProductionAudit()
              && !marker.readyForProductionWindow()
              && !marker.nodeMayTreatAsProductionAuditRecord());
    }
  }

  record EchoReadiness(
      boolean sourceNodeV261Echoed,
      boolean envHandlesEchoed,
      boolean optInGatesEchoed,
      boolean failureTaxonomyEchoed,
      boolean dryRunResponseShapeEchoed,
      boolean inheritedNoGoConditionsEchoed,
      boolean resolverImplementationAbsentEchoed,
      boolean secretProviderAbsentEchoed,
      boolean sideEffectBoundaryEchoed,
      boolean upstreamActionsStillDisabledEchoed) {

    boolean readyForNodeV263() {
      return sourceNodeV261Echoed
          && envHandlesEchoed
          && optInGatesEchoed
          && failureTaxonomyEchoed
          && dryRunResponseShapeEchoed
          && inheritedNoGoConditionsEchoed
          && resolverImplementationAbsentEchoed
          && secretProviderAbsentEchoed
          && sideEffectBoundaryEchoed
          && upstreamActionsStillDisabledEchoed;
    }

    static EchoReadiness from(
        RehearsalSandboxEndpointCredentialResolverDisabledPrecheckSourceEcho source,
        RehearsalSandboxEndpointCredentialResolverDisabledPrecheckRecord precheck,
        RehearsalSandboxEndpointCredentialResolverDisabledPrecheckSideEffectBoundary boundary) {
      return new EchoReadiness(
          sourceReady(source),
          envHandlesReady(precheck),
          optInGatesReady(precheck),
          failureTaxonomyReady(precheck),
          dryRunResponseShapeReady(precheck),
          inheritedNoGoConditionsReady(precheck),
          RESOLVER_IMPLEMENTATION_STATUS.equals(precheck.resolverImplementationStatus())
              && !precheck.resolverClientMayBeInstantiated(),
          SECRET_PROVIDER_IMPLEMENTATION_STATUS.equals(
                  precheck.secretProviderImplementationStatus())
              && !precheck.secretProviderMayBeInstantiated(),
          sideEffectBoundaryReady(boundary, precheck),
          source.upstreamActionsStillDisabled());
    }

    static boolean sourceReady(
        RehearsalSandboxEndpointCredentialResolverDisabledPrecheckSourceEcho source) {
      return ReleaseApprovalUpstreamContractConstants
              .NODE_V261_SANDBOX_ENDPOINT_CREDENTIAL_RESOLVER_UPSTREAM_ECHO_VERIFICATION_VERSION
              .equals(source.sourceVersion())
          && ReleaseApprovalUpstreamContractConstants
              .NODE_V261_SANDBOX_ENDPOINT_CREDENTIAL_RESOLVER_UPSTREAM_ECHO_VERIFICATION_PROFILE
              .equals(source.profileVersion())
          && ReleaseApprovalUpstreamContractConstants
              .NODE_V261_SANDBOX_ENDPOINT_CREDENTIAL_RESOLVER_UPSTREAM_ECHO_VERIFICATION_STATE
              .equals(source.verificationState())
          && VERIFICATION_MODE.equals(source.verificationMode())
          && NODE_V261_SOURCE_SPAN.equals(source.sourceSpan())
          && source.readyForUpstreamEchoVerification()
          && source.sourceNodeV260Ready()
          && source.javaV105EchoReady()
          && source.miniKvV114NonParticipationReady()
          && source.decisionRecordAligned()
          && source.requiredDecisionFieldsAligned()
          && source.explicitNoGoConditionsAligned()
          && source.resolverPolicyAligned()
          && source.approvalMarkerAligned()
          && source.operatorIdentityAligned()
          && source.approvalCorrelationAligned()
          && source.redactionAndFallbackAligned()
          && source.credentialBoundaryAligned()
          && source.rawEndpointBoundaryAligned()
          && source.connectionBoundaryAligned()
          && source.writeBoundaryAligned()
          && source.autoStartBoundaryAligned()
          && source.upstreamActionsStillDisabled()
          && !source.credentialResolverExecutionAllowed()
          && !source.credentialValueRead()
          && !source.credentialValueLoaded()
          && !source.rawEndpointUrlParsed()
          && !source.externalRequestSent()
          && !source.connectsManagedAudit()
          && !source.schemaMigrationExecuted()
          && !source.automaticUpstreamStart()
          && source.checkCount() == SOURCE_CHECK_COUNT
          && source.passedCheckCount() == SOURCE_PASSED_CHECK_COUNT
          && source.productionBlockerCount() == SOURCE_PRODUCTION_BLOCKER_COUNT
          && source.warningCount() == SOURCE_WARNING_COUNT
          && source.recommendationCount() == SOURCE_RECOMMENDATION_COUNT
          && source.readyForNodeV262CredentialResolverDisabledPrecheck();
    }

    static boolean envHandlesReady(
        RehearsalSandboxEndpointCredentialResolverDisabledPrecheckRecord precheck) {
      return precheck.requiredEnvHandleCount() == REQUIRED_ENV_HANDLE_NAMES.size()
          && precheck.requiredEnvHandles().stream()
              .map(RehearsalSandboxEndpointCredentialResolverEnvHandle::name)
              .toList()
              .equals(REQUIRED_ENV_HANDLE_NAMES)
          && precheck.requiredEnvHandles().stream()
              .allMatch(
                  handle ->
                      !handle.valueRequiredForPrecheck()
                          && !handle.credentialValue()
                          && !handle.rawEndpointValue());
    }

    static boolean optInGatesReady(
        RehearsalSandboxEndpointCredentialResolverDisabledPrecheckRecord precheck) {
      return precheck.optInGateRequired()
          && precheck.optInGateCount() == OPT_IN_GATE_NAMES.size()
          && precheck.optInGates().stream()
              .map(RehearsalSandboxEndpointCredentialResolverOptInGate::gateName)
              .toList()
              .equals(OPT_IN_GATE_NAMES)
          && precheck.optInGates().stream()
              .allMatch(
                  gate ->
                      "true".equals(gate.requiredValueForFutureResolver())
                          && "false".equals(gate.currentDefault())
                          && gate.precheckTreatsEnabledAsBlocked()
                          && gate.operatorApprovalRequired());
    }

    static boolean failureTaxonomyReady(
        RehearsalSandboxEndpointCredentialResolverDisabledPrecheckRecord precheck) {
      return precheck.failureClassCount() == FAILURE_CLASS_CODES.size()
          && precheck.failureTaxonomy().stream()
              .map(RehearsalSandboxEndpointCredentialResolverFailureClass::code)
              .toList()
              .equals(FAILURE_CLASS_CODES);
    }

    static boolean dryRunResponseShapeReady(
        RehearsalSandboxEndpointCredentialResolverDisabledPrecheckRecord precheck) {
      RehearsalSandboxEndpointCredentialResolverDryRunResponseShape shape =
          precheck.dryRunResponseShape();
      return precheck.dryRunResponseFieldCount() == DRY_RUN_RESPONSE_FIELDS.size()
          && shape.fields().equals(DRY_RUN_RESPONSE_FIELDS)
          && DRY_RUN_READY_STATE.equals(shape.readyState())
          && !shape.resolverClientInstantiated()
          && !shape.secretProviderInstantiated()
          && !shape.credentialValueRead()
          && !shape.credentialValueLoaded()
          && !shape.rawEndpointUrlParsed()
          && !shape.externalRequestSent()
          && !shape.connectsManagedAudit()
          && !shape.schemaMigrationExecuted();
    }

    static boolean inheritedNoGoConditionsReady(
        RehearsalSandboxEndpointCredentialResolverDisabledPrecheckRecord precheck) {
      return precheck.inheritedNoGoConditionCount() == INHERITED_NO_GO_CONDITIONS.size()
          && precheck.inheritedNoGoConditions().equals(INHERITED_NO_GO_CONDITIONS);
    }

    static boolean sideEffectBoundaryReady(
        RehearsalSandboxEndpointCredentialResolverDisabledPrecheckSideEffectBoundary boundary,
        RehearsalSandboxEndpointCredentialResolverDisabledPrecheckRecord precheck) {
      return disabledPrecheckSideEffectsBlocked(precheck, boundary);
    }
  }
}
