package com.codexdemo.orderplatform.ops.maintenance.releaseapproval;

import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoRecords.RehearsalSandboxEndpointCredentialResolverApprovalRequiredBoundaryExplanation;
import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoRecords.RehearsalSandboxEndpointCredentialResolverDisabledCandidateBoundaryDecision;
import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoRecords.RehearsalSandboxEndpointCredentialResolverDisabledCandidateInterfaceShape;
import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoRecords.RehearsalSandboxEndpointCredentialResolverDisabledFakeWiringReview;
import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoRecords.RehearsalSandboxEndpointCredentialResolverDisabledImplementationCandidate;
import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoRecords.RehearsalSandboxEndpointCredentialResolverDisabledImplementationCandidateChecks;
import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoRecords.RehearsalSandboxEndpointCredentialResolverDisabledImplementationCandidateSideEffectBoundary;
import java.util.List;

final
class ReleaseApprovalSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoSupport {

  static final String CANDIDATE_ECHO_MODE =
      "java-v115-credential-resolver-approval-required-boundary-echo-refinement-only";
  static final String SOURCE_SPAN = "Node v273 disabled implementation candidate review";
  static final String NODE_V272_SOURCE_SPAN = "Node v270 + Java v112 + mini-kv v119";
  static final String NODE_V273_CANDIDATE_VERSION =
      "node-v273-credential-resolver-disabled-implementation-candidate-review.v1";
  static final String NODE_V273_CANDIDATE_MODE = "disabled-interface-and-fake-wiring-review-only";
  static final String NODE_V273_REVIEW_STATE =
      "credential-resolver-disabled-implementation-candidate-review-ready";
  static final String NODE_V272_VERIFICATION_STATE =
      "credential-resolver-pre-implementation-plan-intake-upstream-echo-verification-ready";
  static final String NODE_V272_PROFILE =
      "managed-audit-manual-sandbox-connection-credential-resolver-pre-implementation-plan-intake-upstream-echo-verification.v1";
  static final int REQUIRED_BOUNDARY_COUNT = 10;
  static final int CANDIDATE_READY_DECISION_COUNT = 4;
  static final int APPROVAL_REQUIRED_DECISION_COUNT = 6;
  static final int CHECK_COUNT = 21;
  static final int PASSED_CHECK_COUNT = 21;
  static final int SOURCE_CHECK_COUNT = 22;
  static final int SOURCE_PASSED_CHECK_COUNT = 22;
  static final int NODE_V272_SOURCE_CHECK_COUNT = 26;
  static final int NODE_V272_SOURCE_PASSED_CHECK_COUNT = 26;
  static final int WARNING_COUNT = 2;
  static final int RECOMMENDATION_COUNT = 2;

  private static final List<String> BOUNDARY_CODES =
      ReleaseApprovalSandboxEndpointCredentialResolverBoundaryCatalog.boundaryCodes();

  private static final List<String> REQUIREMENT_CODES =
      ReleaseApprovalSandboxEndpointCredentialResolverBoundaryCatalog.requirementCodes();

  private static final List<String> CANDIDATE_READY_BOUNDARY_CODES =
      ReleaseApprovalSandboxEndpointCredentialResolverBoundaryCatalog.candidateReadyBoundaryCodes();

  private static final List<String> APPROVAL_REQUIRED_BOUNDARY_CODES =
      ReleaseApprovalSandboxEndpointCredentialResolverBoundaryCatalog
          .approvalRequiredBoundaryCodes();

  private
  ReleaseApprovalSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoSupport() {}

  static List<String> boundaryCodes() {
    return BOUNDARY_CODES;
  }

  static List<String> requirementCodes() {
    return REQUIREMENT_CODES;
  }

  static List<String> candidateReadyBoundaryCodes() {
    return CANDIDATE_READY_BOUNDARY_CODES;
  }

  static List<String> approvalRequiredBoundaryCodes() {
    return APPROVAL_REQUIRED_BOUNDARY_CODES;
  }

  static List<RehearsalSandboxEndpointCredentialResolverApprovalRequiredBoundaryExplanation>
      approvalRequiredBoundaryExplanations() {
    return APPROVAL_REQUIRED_BOUNDARY_CODES.stream()
        .map(
            code ->
                new RehearsalSandboxEndpointCredentialResolverApprovalRequiredBoundaryExplanation(
                    code,
                    REQUIREMENT_CODES.get(BOUNDARY_CODES.indexOf(code)),
                    approvalRequiredEvidenceAllowedFor(code),
                    approvalReasonFor(code),
                    prohibitedRuntimeActionsFor(code),
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false))
        .toList();
  }

  static RehearsalSandboxEndpointCredentialResolverDisabledImplementationCandidate candidate(
      String candidateDigest) {
    return new RehearsalSandboxEndpointCredentialResolverDisabledImplementationCandidate(
        NODE_V273_CANDIDATE_VERSION,
        NODE_V273_CANDIDATE_MODE,
        "Node v272",
        candidateDigest,
        REQUIRED_BOUNDARY_COUNT,
        CANDIDATE_READY_DECISION_COUNT,
        APPROVAL_REQUIRED_DECISION_COUNT,
        true,
        true,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        decisions(),
        interfaceShape(),
        fakeWiringReview());
  }

  static RehearsalSandboxEndpointCredentialResolverDisabledImplementationCandidateChecks checks(
      boolean sourceNodeV272Ready,
      RehearsalSandboxEndpointCredentialResolverDisabledImplementationCandidate candidate) {
    boolean decisionsCovered =
        candidate.candidateDecisionCount() == REQUIRED_BOUNDARY_COUNT
            && candidate.decisions().stream()
                .map(
                    RehearsalSandboxEndpointCredentialResolverDisabledCandidateBoundaryDecision
                        ::code)
                .toList()
                .equals(BOUNDARY_CODES);
    boolean readyScopeLimited =
        candidate.candidateReadyDecisionCount() == CANDIDATE_READY_DECISION_COUNT
            && candidate.decisions().stream()
                .filter(decision -> "disabled-candidate-ready".equals(decision.disposition()))
                .map(
                    RehearsalSandboxEndpointCredentialResolverDisabledCandidateBoundaryDecision
                        ::code)
                .toList()
                .equals(CANDIDATE_READY_BOUNDARY_CODES);
    boolean approvalScopePreserved =
        candidate.approvalRequiredDecisionCount() == APPROVAL_REQUIRED_DECISION_COUNT
            && candidate.decisions().stream()
                .filter(decision -> "approval-required".equals(decision.disposition()))
                .map(
                    RehearsalSandboxEndpointCredentialResolverDisabledCandidateBoundaryDecision
                        ::code)
                .toList()
                .equals(APPROVAL_REQUIRED_BOUNDARY_CODES);
    boolean interfaceHandleOnly =
        candidate.interfaceShape().handleOnlyRequest()
            && !candidate.interfaceShape().includesCredentialValue()
            && !candidate.interfaceShape().includesRawEndpointUrl()
            && !candidate.interfaceShape().sendsExternalRequest()
            && !candidate.interfaceShape().instantiatesSecretProvider()
            && !candidate.interfaceShape().instantiatesResolverClient();
    boolean fakeWiringOnly =
        candidate.fakeWiringReview().fakeWiringReviewOnly()
            && !candidate.fakeWiringReview().fakeRuntimeInstantiated()
            && !candidate.fakeWiringReview().realSecretProviderAllowed()
            && !candidate.fakeWiringReview().realManagedAuditTransportAllowed()
            && !candidate.fakeWiringReview().externalRequestAllowed()
            && candidate.fakeWiringReview().cleanupArtifactCount() == 0;
    return checksWithReadiness(
        new RehearsalSandboxEndpointCredentialResolverDisabledImplementationCandidateChecks(
            sourceNodeV272Ready,
            true,
            true,
            true,
            decisionsCovered,
            readyScopeLimited,
            approvalScopePreserved,
            interfaceHandleOnly,
            fakeWiringOnly,
            !candidate.credentialValueReadAllowed(),
            !candidate.rawEndpointUrlParseAllowed(),
            !candidate.secretProviderRuntimeAllowed(),
            !candidate.realResolverImplementationAllowed(),
            !candidate.externalRequestAllowed(),
            !candidate.schemaMigrationAllowed(),
            !candidate.approvalLedgerWriteAllowed(),
            true,
            true,
            true,
            true,
            false));
  }

  static RehearsalSandboxEndpointCredentialResolverDisabledImplementationCandidateChecks
      checksWithReadiness(
          RehearsalSandboxEndpointCredentialResolverDisabledImplementationCandidateChecks checks) {
    boolean ready =
        checks.sourceNodeV272Ready()
            && checks.sourceNodeV272KeepsReadOnlyEchoOnly()
            && checks.sourceNodeV272KeepsRealResolverBlocked()
            && checks.sourceNodeV272KeepsBoundaryAlignment()
            && checks.allCandidateDecisionsCovered()
            && checks.candidateReadyBoundariesLimited()
            && checks.approvalRequiredBoundariesPreserved()
            && checks.interfaceShapeHandleOnly()
            && checks.fakeWiringReviewOnly()
            && checks.credentialValueStillForbidden()
            && checks.rawEndpointStillForbidden()
            && checks.secretProviderRuntimeStillDisabled()
            && checks.resolverClientStillDisabled()
            && checks.externalRequestStillBlocked()
            && checks.schemaMigrationStillBlocked()
            && checks.ledgerWriteStillBlocked()
            && checks.upstreamProbesStillDisabled()
            && checks.upstreamActionsStillDisabled()
            && checks.productionAuditStillBlocked()
            && checks.productionWindowStillBlocked();
    return new RehearsalSandboxEndpointCredentialResolverDisabledImplementationCandidateChecks(
        checks.sourceNodeV272Ready(),
        checks.sourceNodeV272KeepsReadOnlyEchoOnly(),
        checks.sourceNodeV272KeepsRealResolverBlocked(),
        checks.sourceNodeV272KeepsBoundaryAlignment(),
        checks.allCandidateDecisionsCovered(),
        checks.candidateReadyBoundariesLimited(),
        checks.approvalRequiredBoundariesPreserved(),
        checks.interfaceShapeHandleOnly(),
        checks.fakeWiringReviewOnly(),
        checks.credentialValueStillForbidden(),
        checks.rawEndpointStillForbidden(),
        checks.secretProviderRuntimeStillDisabled(),
        checks.resolverClientStillDisabled(),
        checks.externalRequestStillBlocked(),
        checks.schemaMigrationStillBlocked(),
        checks.ledgerWriteStillBlocked(),
        checks.upstreamProbesStillDisabled(),
        checks.upstreamActionsStillDisabled(),
        checks.productionAuditStillBlocked(),
        checks.productionWindowStillBlocked(),
        ready);
  }

  static RehearsalSandboxEndpointCredentialResolverDisabledImplementationCandidateSideEffectBoundary
      sideEffectBoundary() {
    return new RehearsalSandboxEndpointCredentialResolverDisabledImplementationCandidateSideEffectBoundary(
        true, true, true, false, false, false, false, false, false, false, false, false, false,
        false, false, false, false, false, false, false, false, false, false, false, false, false,
        false);
  }

  static boolean candidateComplete(
      RehearsalSandboxEndpointCredentialResolverDisabledImplementationCandidate candidate) {
    return NODE_V273_CANDIDATE_VERSION.equals(candidate.candidateVersion())
        && NODE_V273_CANDIDATE_MODE.equals(candidate.candidateMode())
        && candidate.candidateDigest().startsWith("sha256:")
        && candidate.candidateDecisionCount() == REQUIRED_BOUNDARY_COUNT
        && candidate.candidateReadyDecisionCount() == CANDIDATE_READY_DECISION_COUNT
        && candidate.approvalRequiredDecisionCount() == APPROVAL_REQUIRED_DECISION_COUNT
        && candidate.disabledInterfaceCandidateAllowed()
        && candidate.fakeWiringReviewAllowed()
        && !candidate.realResolverImplementationAllowed()
        && !candidate.secretProviderRuntimeAllowed()
        && !candidate.credentialValueReadAllowed()
        && !candidate.rawEndpointUrlParseAllowed()
        && !candidate.externalRequestAllowed()
        && !candidate.schemaMigrationAllowed()
        && !candidate.approvalLedgerWriteAllowed()
        && !candidate.automaticUpstreamStartAllowed()
        && candidate.decisions().stream()
            .map(RehearsalSandboxEndpointCredentialResolverDisabledCandidateBoundaryDecision::code)
            .toList()
            .equals(BOUNDARY_CODES)
        && candidate.decisions().stream()
            .map(
                RehearsalSandboxEndpointCredentialResolverDisabledCandidateBoundaryDecision
                    ::requirementFromV268)
            .toList()
            .equals(REQUIREMENT_CODES)
        && candidate.interfaceShape().handleOnlyRequest()
        && !candidate.interfaceShape().includesCredentialValue()
        && !candidate.interfaceShape().includesRawEndpointUrl()
        && !candidate.interfaceShape().sendsExternalRequest()
        && !candidate.interfaceShape().instantiatesSecretProvider()
        && !candidate.interfaceShape().instantiatesResolverClient()
        && candidate.fakeWiringReview().fakeWiringReviewOnly()
        && !candidate.fakeWiringReview().fakeRuntimeInstantiated()
        && !candidate.fakeWiringReview().realSecretProviderAllowed()
        && !candidate.fakeWiringReview().realManagedAuditTransportAllowed()
        && !candidate.fakeWiringReview().externalRequestAllowed()
        && candidate.fakeWiringReview().cleanupArtifactCount() == 0;
  }

  static boolean approvalRequiredBoundaryExplanationsComplete(
      List<RehearsalSandboxEndpointCredentialResolverApprovalRequiredBoundaryExplanation>
          explanations) {
    return explanations.size() == APPROVAL_REQUIRED_DECISION_COUNT
        && explanations.stream()
            .map(
                RehearsalSandboxEndpointCredentialResolverApprovalRequiredBoundaryExplanation::code)
            .toList()
            .equals(APPROVAL_REQUIRED_BOUNDARY_CODES)
        && explanations.stream()
            .map(
                RehearsalSandboxEndpointCredentialResolverApprovalRequiredBoundaryExplanation
                    ::requirementFromV268)
            .toList()
            .equals(
                APPROVAL_REQUIRED_BOUNDARY_CODES.stream()
                    .map(code -> REQUIREMENT_CODES.get(BOUNDARY_CODES.indexOf(code)))
                    .toList())
        && explanations.stream()
            .allMatch(
                explanation ->
                    "approval-required-read-only-evidence".equals(explanation.evidenceAllowed())
                        && !explanation.credentialValueReadAllowed()
                        && !explanation.rawEndpointUrlParseAllowed()
                        && !explanation.managedAuditConnectionAllowed()
                        && !explanation.approvalLedgerWriteAllowed()
                        && !explanation.sqlExecutionAllowed()
                        && !explanation.rollbackExecutionAllowed()
                        && !explanation.automaticUpstreamStartAllowed());
  }

  static boolean checksClosed(
      RehearsalSandboxEndpointCredentialResolverDisabledImplementationCandidateChecks checks) {
    return checks
        .readyForManagedAuditManualSandboxConnectionCredentialResolverDisabledImplementationCandidateReview();
  }

  static boolean sideEffectBoundaryBlocked(
      RehearsalSandboxEndpointCredentialResolverDisabledImplementationCandidateSideEffectBoundary
          boundary) {
    return boundary.disabledImplementationCandidateReviewOnly()
        && boundary.readOnlyCandidateReview()
        && boundary.fakeWiringReviewOnly()
        && !boundary.readyForManagedAuditSandboxAdapterConnection()
        && !boundary.readyForProductionAudit()
        && !boundary.readyForProductionWindow()
        && !boundary.readyForProductionOperations()
        && !boundary.realResolverImplementationAllowed()
        && !boundary.executionAllowed()
        && !boundary.connectsManagedAudit()
        && !boundary.readsManagedAuditCredential()
        && !boundary.storesManagedAuditCredential()
        && !boundary.credentialValueRead()
        && !boundary.rawEndpointUrlParsed()
        && !boundary.rawEndpointUrlIncluded()
        && !boundary.externalRequestSent()
        && !boundary.secretProviderInstantiated()
        && !boundary.resolverClientInstantiated()
        && !boundary.fakeRuntimeInstantiated()
        && !boundary.realSecretProviderAllowed()
        && !boundary.realManagedAuditTransportAllowed()
        && !boundary.approvalLedgerWritten()
        && !boundary.managedAuditStoreWritten()
        && !boundary.sqlExecuted()
        && !boundary.schemaMigrationExecuted()
        && !boundary.automaticUpstreamStart()
        && !boundary.javaStartedNodeOrMiniKv();
  }

  private static List<RehearsalSandboxEndpointCredentialResolverDisabledCandidateBoundaryDecision>
      decisions() {
    return BOUNDARY_CODES.stream()
        .map(
            code ->
                new RehearsalSandboxEndpointCredentialResolverDisabledCandidateBoundaryDecision(
                    code,
                    REQUIREMENT_CODES.get(BOUNDARY_CODES.indexOf(code)),
                    CANDIDATE_READY_BOUNDARY_CODES.contains(code)
                        ? "disabled-candidate-ready"
                        : "approval-required",
                    ownerFor(code),
                    candidateRuleFor(code),
                    prohibitedRuntimeActionsFor(code)))
        .toList();
  }

  private static RehearsalSandboxEndpointCredentialResolverDisabledCandidateInterfaceShape
      interfaceShape() {
    return new RehearsalSandboxEndpointCredentialResolverDisabledCandidateInterfaceShape(
        "disabled-credential-resolver-interface-candidate.v1",
        List.of(
            "credentialHandle",
            "endpointHandle",
            "resolverPolicyHandle",
            "operatorIdentity",
            "approvalCorrelationId",
            "manualWindowMarker"),
        List.of(
            "resolverState",
            "resolvedCredentialValue",
            "rawEndpointUrl",
            "redactionApplied",
            "externalRequestSent",
            "failureClass",
            "auditDigest"),
        List.of(
            "disabled-by-config",
            "missing-credential-handle",
            "missing-endpoint-handle",
            "operator-approval-required",
            "manual-window-required",
            "real-runtime-forbidden"),
        true,
        false,
        false,
        false,
        false,
        false);
  }

  private static RehearsalSandboxEndpointCredentialResolverDisabledFakeWiringReview
      fakeWiringReview() {
    return new RehearsalSandboxEndpointCredentialResolverDisabledFakeWiringReview(
        "disabled-credential-resolver-fake-wiring-review.v1", true, false, false, false, false, 0);
  }

  private static String ownerFor(String code) {
    return ReleaseApprovalSandboxEndpointCredentialResolverBoundaryCatalog.ownerFor(code);
  }

  private static String candidateRuleFor(String code) {
    return ReleaseApprovalSandboxEndpointCredentialResolverBoundaryCatalog.candidateRuleFor(code);
  }

  private static List<String> prohibitedRuntimeActionsFor(String code) {
    return ReleaseApprovalSandboxEndpointCredentialResolverBoundaryCatalog
        .prohibitedRuntimeActionsFor(code);
  }

  private static String approvalRequiredEvidenceAllowedFor(String code) {
    return ReleaseApprovalSandboxEndpointCredentialResolverBoundaryCatalog
        .approvalRequiredEvidenceAllowedFor(code);
  }

  private static String approvalReasonFor(String code) {
    return ReleaseApprovalSandboxEndpointCredentialResolverBoundaryCatalog.approvalReasonFor(code);
  }
}
