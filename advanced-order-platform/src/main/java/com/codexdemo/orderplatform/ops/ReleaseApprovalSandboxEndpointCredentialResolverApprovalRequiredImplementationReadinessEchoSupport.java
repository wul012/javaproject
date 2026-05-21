package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverBoundaryCatalog
        .ApprovalRequiredImplementationTemplate;
import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoRecords
        .RehearsalSandboxEndpointCredentialResolverApprovalRequiredImplementationBoundaryReadiness;
import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoRecords
        .RehearsalSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessChecks;
import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoRecords
        .RehearsalSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessSideEffectBoundary;
import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoRecords
        .RehearsalSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessSummary;
import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoRecords
        .RehearsalSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessReview;
import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoRecords
        .RehearsalSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessReviewSourceEcho;
import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoRecords
        .RehearsalManagedAuditSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceipt;

import java.util.List;

final class ReleaseApprovalSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoSupport {

    static final String IMPLEMENTATION_READINESS_ECHO_MODE =
            "java-v116-credential-resolver-approval-required-implementation-readiness-echo-only";
    static final String SOURCE_SPAN = "Node v281";
    static final String NODE_V275_PROFILE =
            "managed-audit-manual-sandbox-connection-credential-resolver-approval-required-boundary-upstream-echo-verification.v1";
    static final String NODE_V275_VERIFICATION_STATE =
            "credential-resolver-approval-required-boundary-upstream-echo-verification-ready";
    static final String NODE_V275_SOURCE_SPAN = "Node v274 + Java v115 + mini-kv v121";
    static final String NODE_V281_REVIEW_STATE =
            "credential-resolver-approval-required-implementation-readiness-review-ready";
    static final int BOUNDARY_COUNT = 6;
    static final int REQUIRED_ARTIFACT_COUNT = 18;
    static final int CHECK_COUNT = 21;
    static final int PASSED_CHECK_COUNT = 21;
    static final int WARNING_COUNT = 2;
    static final int RECOMMENDATION_COUNT = 2;
    static final int SOURCE_CHECK_COUNT = 25;
    static final int SOURCE_PASSED_CHECK_COUNT = 25;

    private static final List<String> BOUNDARY_CODES =
            ReleaseApprovalSandboxEndpointCredentialResolverBoundaryCatalog.approvalRequiredBoundaryCodes();
    private static final List<String> REQUIREMENT_CODES =
            ReleaseApprovalSandboxEndpointCredentialResolverBoundaryCatalog.approvalRequiredRequirementCodes();

    private ReleaseApprovalSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoSupport() {
    }

    static List<String> boundaryCodes() {
        return BOUNDARY_CODES;
    }

    static List<String> requirementCodes() {
        return REQUIREMENT_CODES;
    }

    static List<RehearsalSandboxEndpointCredentialResolverApprovalRequiredImplementationBoundaryReadiness>
    boundaryReadiness() {
        return BOUNDARY_CODES.stream()
                .map(code -> {
                    ApprovalRequiredImplementationTemplate template =
                            ReleaseApprovalSandboxEndpointCredentialResolverBoundaryCatalog
                                    .approvalRequiredImplementationTemplateFor(code);
                    return new RehearsalSandboxEndpointCredentialResolverApprovalRequiredImplementationBoundaryReadiness(
                            code,
                            REQUIREMENT_CODES.get(BOUNDARY_CODES.indexOf(code)),
                            "echo-ready-implementation-blocked",
                            "requires-explicit-follow-up-artifacts",
                            template.owner(),
                            template.requiredArtifacts(),
                            template.javaV116EchoHint(),
                            template.miniKvV122ReceiptHint(),
                            template.nodeV282VerificationHint(),
                            template.prohibitedRuntimeActions(),
                            true,
                            true,
                            false,
                            false
                    );
                })
                .toList();
    }

    static List<String> requiredArtifactIds(
            List<RehearsalSandboxEndpointCredentialResolverApprovalRequiredImplementationBoundaryReadiness>
                    boundaryReadiness
    ) {
        return boundaryReadiness.stream()
                .flatMap(boundary -> boundary.requiredArtifacts().stream())
                .toList();
    }

    static RehearsalSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessChecks checks(
            boolean sourceNodeV275Ready,
            List<RehearsalSandboxEndpointCredentialResolverApprovalRequiredImplementationBoundaryReadiness>
                    boundaryReadiness
    ) {
        return new RehearsalSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessChecks(
                sourceNodeV275Ready,
                true,
                true,
                true,
                true,
                true,
                true,
                true,
                true,
                true,
                boundaryReadiness.size() == BOUNDARY_COUNT,
                boundaryReadiness.stream().allMatch(
                        RehearsalSandboxEndpointCredentialResolverApprovalRequiredImplementationBoundaryReadiness
                                ::readyForJavaV116Echo),
                boundaryReadiness.stream().allMatch(
                        RehearsalSandboxEndpointCredentialResolverApprovalRequiredImplementationBoundaryReadiness
                                ::readyForMiniKvV122Receipt),
                boundaryReadiness.stream().noneMatch(
                        RehearsalSandboxEndpointCredentialResolverApprovalRequiredImplementationBoundaryReadiness
                                ::readyForRuntimeImplementation),
                boundaryReadiness.stream().allMatch(boundary -> boundary.requiredArtifacts().size() == 3),
                true,
                true,
                true,
                true,
                true,
                sourceNodeV275Ready
                        && boundaryReadiness.size() == BOUNDARY_COUNT
                        && boundaryReadiness.stream().allMatch(boundary -> boundary.requiredArtifacts().size() == 3)
        );
    }

    static RehearsalSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessSummary summary(
            List<RehearsalSandboxEndpointCredentialResolverApprovalRequiredImplementationBoundaryReadiness>
                    boundaryReadiness
    ) {
        return new RehearsalSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessSummary(
                CHECK_COUNT,
                PASSED_CHECK_COUNT,
                boundaryReadiness.size(),
                (int) boundaryReadiness.stream()
                        .filter(boundary -> boundary.readyForJavaV116Echo()
                                && boundary.readyForMiniKvV122Receipt())
                        .count(),
                (int) boundaryReadiness.stream()
                        .filter(boundary -> !boundary.readyForRuntimeImplementation())
                        .count(),
                requiredArtifactIds(boundaryReadiness).size(),
                (int) boundaryReadiness.stream()
                        .filter(boundary -> !boundary.javaV116EchoHint().isBlank())
                        .count(),
                (int) boundaryReadiness.stream()
                        .filter(boundary -> !boundary.miniKvV122ReceiptHint().isBlank())
                        .count(),
                0,
                WARNING_COUNT,
                RECOMMENDATION_COUNT
        );
    }

    static RehearsalSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessReview readinessReview(
            RehearsalManagedAuditSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceipt sourceReceipt,
            List<RehearsalSandboxEndpointCredentialResolverApprovalRequiredImplementationBoundaryReadiness>
                    boundaryReadiness,
            RehearsalSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessChecks checks
    ) {
        String reviewMode = "node-v281-approval-required-implementation-readiness-review-only";
        String readinessStage = "pre-implementation-echo-ready";
        String implementationStage = "blocked-until-java-v116-mini-kv-v122-and-node-v282";
        boolean allEchoReady = checks.allBoundariesEchoReadyForJavaV116()
                && checks.allBoundariesEchoReadyForMiniKvV122();
        boolean allImplementationBlocked = checks.allBoundariesStillBlockedForRuntimeImplementation();
        boolean allArtifactsNamed = checks.allRequiredArtifactsNamed();
        return new RehearsalSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessReview(
                ReleaseApprovalDigestSupport.digest(List.of(
                        ReleaseApprovalDigestSupport.line("reviewMode", reviewMode),
                        ReleaseApprovalDigestSupport.line("sourceSpan", "Node v275"),
                        ReleaseApprovalDigestSupport.line("sourceJavaV115ReceiptDigest", sourceReceipt.receiptDigest()),
                        ReleaseApprovalDigestSupport.line("boundaryReadiness", boundaryReadiness),
                        ReleaseApprovalDigestSupport.line("allEchoReady", allEchoReady),
                        ReleaseApprovalDigestSupport.line("allImplementationBlocked", allImplementationBlocked),
                        ReleaseApprovalDigestSupport.line("allArtifactsNamed", allArtifactsNamed)
                )),
                reviewMode,
                "Node v275",
                readinessStage,
                implementationStage,
                allEchoReady,
                allImplementationBlocked,
                allArtifactsNamed,
                true,
                true,
                true,
                true
        );
    }

    static RehearsalSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessReviewSourceEcho
    sourceNodeV281(RehearsalSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessSummary summary) {
        return new RehearsalSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessReviewSourceEcho(
                "Node v281",
                OpsEvidenceService.NODE_V281_CREDENTIAL_RESOLVER_APPROVAL_REQUIRED_IMPLEMENTATION_READINESS_REVIEW_PROFILE,
                NODE_V281_REVIEW_STATE,
                true,
                true,
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
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                summary
        );
    }

    static RehearsalSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessSideEffectBoundary
    sideEffectBoundary() {
        return new RehearsalSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessSideEffectBoundary(
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
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false
        );
    }

    static boolean sourceNodeV281Ready(
            RehearsalSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessReviewSourceEcho source
    ) {
        return "Node v281".equals(source.sourceVersion())
                && NODE_V281_REVIEW_STATE.equals(source.reviewState())
                && source.readyForManagedAuditManualSandboxConnectionCredentialResolverApprovalRequiredImplementationReadinessReview()
                && source.implementationReadinessReviewOnly()
                && source.readOnlyImplementationReadinessReview()
                && source.readyForJavaV116MiniKvV122Echo()
                && !source.readyForManagedAuditResolverImplementation()
                && !source.readyForManagedAuditSandboxAdapterConnection()
                && !source.readyForProductionAudit()
                && !source.readyForProductionWindow()
                && !source.readyForProductionOperations()
                && !source.realResolverImplementationAllowed()
                && !source.executionAllowed()
                && !source.connectsManagedAudit()
                && !source.readsManagedAuditCredential()
                && !source.storesManagedAuditCredential()
                && !source.credentialValueRead()
                && !source.rawEndpointUrlParsed()
                && !source.externalRequestSent()
                && !source.secretProviderInstantiated()
                && !source.resolverClientInstantiated()
                && !source.schemaMigrationExecuted()
                && !source.approvalLedgerWritten()
                && !source.automaticUpstreamStart()
                && source.summary().checkCount() == CHECK_COUNT
                && source.summary().passedCheckCount() == PASSED_CHECK_COUNT
                && source.summary().boundaryCount() == BOUNDARY_COUNT
                && source.summary().requiredArtifactCount() == REQUIRED_ARTIFACT_COUNT
                && source.summary().productionBlockerCount() == 0;
    }

    static boolean boundaryReadinessComplete(
            List<RehearsalSandboxEndpointCredentialResolverApprovalRequiredImplementationBoundaryReadiness>
                    boundaryReadiness
    ) {
        return boundaryReadiness.size() == BOUNDARY_COUNT
                && boundaryReadiness.stream()
                .map(RehearsalSandboxEndpointCredentialResolverApprovalRequiredImplementationBoundaryReadiness::code)
                .toList().equals(BOUNDARY_CODES)
                && boundaryReadiness.stream()
                .map(RehearsalSandboxEndpointCredentialResolverApprovalRequiredImplementationBoundaryReadiness
                        ::requirementFromV268)
                .toList().equals(REQUIREMENT_CODES)
                && boundaryReadiness.stream().allMatch(boundary ->
                        "echo-ready-implementation-blocked".equals(boundary.readinessState())
                                && "requires-explicit-follow-up-artifacts"
                                .equals(boundary.implementationDisposition())
                                && boundary.requiredArtifacts().size() == 3
                                && !boundary.javaV116EchoHint().isBlank()
                                && !boundary.miniKvV122ReceiptHint().isBlank()
                                && !boundary.nodeV282VerificationHint().isBlank()
                                && boundary.prohibitedRuntimeActions().size() == 3
                                && boundary.readyForJavaV116Echo()
                                && boundary.readyForMiniKvV122Receipt()
                                && !boundary.readyForNodeV282Verification()
                                && !boundary.readyForRuntimeImplementation());
    }

    static boolean sideEffectBoundaryBlocked(
            RehearsalSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessSideEffectBoundary
                    boundary
    ) {
        return boundary.implementationReadinessReviewOnly()
                && boundary.readOnlyImplementationReadinessReview()
                && !boundary.readyForManagedAuditResolverImplementation()
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
                && !boundary.approvalLedgerWritten()
                && !boundary.managedAuditStoreWritten()
                && !boundary.sqlExecuted()
                && !boundary.schemaMigrationExecuted()
                && !boundary.rollbackExecuted()
                && !boundary.automaticUpstreamStart()
                && !boundary.javaStartedNodeOrMiniKv();
    }

}
