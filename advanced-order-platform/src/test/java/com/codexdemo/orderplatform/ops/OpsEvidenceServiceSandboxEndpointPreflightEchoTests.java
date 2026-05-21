package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsEvidenceServiceSandboxEndpointPreflightEchoTests extends OpsEvidenceServiceRehearsalTestSupport {

    @Test
    void releaseApprovalRehearsalExposesSandboxEndpointHandlePreflightEchoMarker() {
        OpsEvidenceService service = readOnlyFixtureService();

        ReleaseApprovalRehearsalResponse rehearsal =
                service.releaseApprovalRehearsal(headerBackedRehearsalRequest());

        ReleaseApprovalSandboxConnectionAdapterPreflightEchoRecords.RehearsalManagedAuditSandboxEndpointHandlePreflightEchoMarker
                marker = rehearsal.managedAuditSandboxEndpointHandlePreflightEchoMarker();
        assertThat(marker.markerVersion())
                .isEqualTo(
                        "java-release-approval-rehearsal-managed-audit-sandbox-endpoint-handle-preflight-echo-marker.v1"
                );
        assertThat(marker.sourceFakeTransportDryRunPacketEchoMarkerSchemaVersion())
                .isEqualTo("java-release-approval-rehearsal-response-schema.v25");
        assertThat(marker.consumedByNodeSandboxEndpointHandlePreflightReviewVersion()).isEqualTo("Node v258");
        assertThat(marker.consumedByNodeSandboxEndpointHandlePreflightReviewProfile())
                .isEqualTo(
                        "managed-audit-manual-sandbox-connection-sandbox-endpoint-handle-preflight-review.v1"
                );
        assertThat(marker.consumedByNodeSandboxEndpointHandlePreflightReviewEndpoint())
                .isEqualTo(
                        "/api/v1/audit/managed-audit-manual-sandbox-connection-sandbox-endpoint-handle-preflight-review"
                );
        assertThat(marker.consumedByNodeSandboxEndpointHandlePreflightReviewMarkdownEndpoint())
                .isEqualTo(
                        "/api/v1/audit/managed-audit-manual-sandbox-connection-sandbox-endpoint-handle-preflight-review?format=markdown"
                );
        assertThat(marker.consumedByNodeSandboxEndpointHandlePreflightReviewState())
                .isEqualTo("sandbox-endpoint-handle-preflight-review-ready");
        assertThat(marker.sourceNodeFakeTransportPacketUpstreamEchoVerificationVersion()).isEqualTo("Node v257");
        assertThat(marker.sourceNodeFakeTransportPacketUpstreamEchoVerificationProfile())
                .isEqualTo(
                        "managed-audit-manual-sandbox-connection-fake-transport-packet-upstream-echo-verification.v1"
                );
        assertThat(marker.sourceNodeFakeTransportPacketUpstreamEchoVerificationEndpoint())
                .isEqualTo(
                        "/api/v1/audit/managed-audit-manual-sandbox-connection-fake-transport-packet-upstream-echo-verification"
                );
        assertThat(marker.sourceNodeFakeTransportPacketUpstreamEchoVerificationState())
                .isEqualTo("fake-transport-packet-upstream-echo-verification-ready");
        assertThat(marker.nextNodeSandboxEndpointHandleUpstreamEchoVerificationVersion()).isEqualTo("Node v259");
        assertThat(marker.nextNodeSandboxEndpointHandleUpstreamEchoVerificationProfile())
                .isEqualTo(
                        "managed-audit-manual-sandbox-connection-sandbox-endpoint-handle-upstream-echo-verification.v1"
                );
        assertThat(marker.nodeV259MayConsume()).isTrue();
        assertThat(marker.reviewMode()).isEqualTo("sandbox-endpoint-handle-preflight-review-only");
        assertThat(marker.sourceSpan()).isEqualTo("Node v257");
        assertThat(marker.sourceNodeV257().readyForUpstreamEchoVerification()).isTrue();
        assertThat(marker.sourceNodeV257().requestShapeAligned()).isTrue();
        assertThat(marker.sourceNodeV257().responseShapeAligned()).isTrue();
        assertThat(marker.sourceNodeV257().timeoutBoundaryAligned()).isTrue();
        assertThat(marker.sourceNodeV257().failureMappingAligned()).isTrue();
        assertThat(marker.sourceNodeV257().cleanupBoundaryAligned()).isTrue();
        assertThat(marker.sourceNodeV257().archiveNoRerunAligned()).isTrue();
        assertThat(marker.sourceNodeV257().credentialBoundaryAligned()).isTrue();
        assertThat(marker.sourceNodeV257().connectionBoundaryAligned()).isTrue();
        assertThat(marker.sourceNodeV257().writeBoundaryAligned()).isTrue();
        assertThat(marker.sourceNodeV257().autoStartBoundaryAligned()).isTrue();
        assertThat(marker.sourceNodeV257().upstreamActionsStillDisabled()).isTrue();
        assertThat(marker.sourceNodeV257().readyForManagedAuditSandboxAdapterConnection()).isFalse();
        assertThat(marker.sourceNodeV257().connectsManagedAudit()).isFalse();
        assertThat(marker.sourceNodeV257().readsManagedAuditCredential()).isFalse();
        assertThat(marker.sourceNodeV257().storesManagedAuditCredential()).isFalse();
        assertThat(marker.sourceNodeV257().schemaMigrationExecuted()).isFalse();
        assertThat(marker.sourceNodeV257().automaticUpstreamStart()).isFalse();
        assertThat(marker.sourceNodeV257().evidenceFileCount()).isEqualTo(6);
        assertThat(marker.sourceNodeV257().matchedSnippetCount()).isEqualTo(33);
        assertThat(marker.sourceNodeV257().readyForNodeV258PreflightReview()).isTrue();
        assertThat(marker.preflightReview().endpointHandle())
                .isEqualTo("ORDEROPS_MANAGED_AUDIT_SANDBOX_ENDPOINT_HANDLE");
        assertThat(marker.preflightReview().credentialHandle())
                .isEqualTo("ORDEROPS_MANAGED_AUDIT_SANDBOX_CREDENTIAL_HANDLE");
        assertThat(marker.preflightReview().ownerApprovalArtifactId())
                .isEqualTo("owner-approval-artifact-review-only");
        assertThat(marker.preflightReview().schemaRehearsalId())
                .isEqualTo("schema-migration-rehearsal-review-only");
        assertThat(marker.preflightReview().operatorWindowMarker())
                .isEqualTo("manual-sandbox-endpoint-window-review-only");
        assertThat(marker.preflightReview().requiredReviewItemCount()).isEqualTo(7);
        assertThat(marker.preflightReview().completedReviewItemCount()).isEqualTo(7);
        assertThat(marker.preflightReview().forbiddenOperationCount()).isEqualTo(7);
        assertThat(marker.preflightReview().readOnlyPreflightReview()).isTrue();
        assertThat(marker.preflightReview().endpointHandleOnly()).isTrue();
        assertThat(marker.preflightReview().credentialHandleOnly()).isTrue();
        assertThat(marker.networkAllowlistReview().allowlistHandle())
                .isEqualTo("ORDEROPS_MANAGED_AUDIT_SANDBOX_NETWORK_ALLOWLIST_HANDLE");
        assertThat(marker.networkAllowlistReview().rawHostIncluded()).isFalse();
        assertThat(marker.networkAllowlistReview().cidrIncluded()).isFalse();
        assertThat(marker.networkAllowlistReview().reviewed()).isTrue();
        assertThat(marker.tlsPolicyReview().policyHandle())
                .isEqualTo("ORDEROPS_MANAGED_AUDIT_SANDBOX_TLS_POLICY_HANDLE");
        assertThat(marker.tlsPolicyReview().certificateMaterialIncluded()).isFalse();
        assertThat(marker.tlsPolicyReview().privateKeyIncluded()).isFalse();
        assertThat(marker.tlsPolicyReview().reviewed()).isTrue();
        assertThat(marker.redactionPolicy().policyHandle())
                .isEqualTo("ORDEROPS_MANAGED_AUDIT_SANDBOX_REDACTION_POLICY_HANDLE");
        assertThat(marker.redactionPolicy().credentialValueRedacted()).isTrue();
        assertThat(marker.redactionPolicy().rawEndpointUrlRedacted()).isTrue();
        assertThat(marker.redactionPolicy().payloadSecretRedacted()).isTrue();
        assertThat(marker.redactionPolicy().reviewed()).isTrue();
        assertThat(marker.operatorWindow().manualWindowRequired()).isTrue();
        assertThat(marker.operatorWindow().windowOpen()).isFalse();
        assertThat(marker.operatorWindow().executionBlockedUntilWindowOpen()).isTrue();
        assertThat(marker.operatorWindow().operatorIdentityRequired()).isTrue();
        assertThat(marker.operatorWindow().approvalCorrelationRequired()).isTrue();
        assertThat(marker.operatorWindow().reviewed()).isTrue();
        assertThat(marker.sideEffectBoundary().rawEndpointUrlParsed()).isFalse();
        assertThat(marker.sideEffectBoundary().rawEndpointUrlIncluded()).isFalse();
        assertThat(marker.sideEffectBoundary().credentialValueRead()).isFalse();
        assertThat(marker.sideEffectBoundary().externalRequestSent()).isFalse();
        assertThat(marker.sideEffectBoundary().schemaMigrationExecuted()).isFalse();
        assertThat(marker.sideEffectBoundary().automaticUpstreamStart()).isFalse();
        assertThat(marker.sideEffectBoundary().connectsManagedAudit()).isFalse();
        assertThat(marker.sideEffectBoundary().readsManagedAuditCredential()).isFalse();
        assertThat(marker.sideEffectBoundary().storesManagedAuditCredential()).isFalse();
        assertThat(marker.sideEffectBoundary().executionAllowed()).isFalse();
        assertThat(marker.sideEffectBoundary().approvalLedgerWritten()).isFalse();
        assertThat(marker.sideEffectBoundary().javaStarted()).isFalse();
        assertThat(marker.sideEffectBoundary().miniKvStarted()).isFalse();
        assertThat(marker.sideEffectBoundary().externalAuditServiceStarted()).isFalse();
        assertThat(marker.sideEffectBoundary().productionAuditAllowed()).isFalse();
        assertThat(marker.sideEffectBoundary().productionWindowAllowed()).isFalse();
        assertThat(marker.sourceNodeV257Echoed()).isTrue();
        assertThat(marker.endpointHandleReviewEchoed()).isTrue();
        assertThat(marker.credentialHandleReviewEchoed()).isTrue();
        assertThat(marker.ownerApprovalArtifactReviewEchoed()).isTrue();
        assertThat(marker.networkAllowlistReviewEchoed()).isTrue();
        assertThat(marker.tlsPolicyReviewEchoed()).isTrue();
        assertThat(marker.redactionPolicyEchoed()).isTrue();
        assertThat(marker.operatorWindowReviewEchoed()).isTrue();
        assertThat(marker.sideEffectBoundaryEchoed()).isTrue();
        assertThat(marker.readyForNodeV259SandboxEndpointHandleUpstreamEchoVerification()).isTrue();
        assertThat(marker.readyForManagedAuditSandboxAdapterConnection()).isFalse();
        assertThat(marker.readyForProductionAudit()).isFalse();
        assertThat(marker.readyForProductionWindow()).isFalse();
        assertThat(marker.nodeMayTreatAsProductionAuditRecord()).isFalse();
        assertThat(marker.requiredReviewItems())
                .containsExactly(
                        "endpoint handle review",
                        "credential handle review",
                        "owner approval artifact review",
                        "network allowlist review",
                        "TLS policy review",
                        "redaction policy review",
                        "operator window review"
                );
        assertThat(marker.forbiddenOperations())
                .contains(
                        "read credential value",
                        "parse raw endpoint URL",
                        "send real managed audit request",
                        "write approval ledger",
                        "start Java or mini-kv"
                );
        assertThat(marker.nextRequiredEchoVersions())
                .contains(
                        "Java v104 sandbox endpoint handle preflight echo marker",
                        "mini-kv v113 sandbox endpoint handle non-participation receipt"
                );
        assertThat(marker.markerWarnings()).isEmpty();
        assertThat(marker.markerDigest()).startsWith("sha256:");
        assertThat(rehearsal.verificationHint().schemaFields())
                .contains("managedAuditSandboxEndpointHandlePreflightEchoMarker");
        assertThat(rehearsal.verificationHint().warningDigestInputs())
                .contains(
                        "managedAuditSandboxEndpointHandlePreflightEchoMarkerWarnings",
                        "sandboxEndpointHandlePreflightEchoMarkerDigest",
                        "sandboxEndpointHandlePreflightRawEndpointUrlParsed"
                );
        assertThat(rehearsal.verificationHint().proofClaims())
                .contains(
                        "managedAuditSandboxEndpointHandlePreflightEchoMarker.preflightReview.requiredReviewItemCount=7",
                        "managedAuditSandboxEndpointHandlePreflightEchoMarker.sideEffectBoundary.rawEndpointUrlParsed=false",
                        "managedAuditSandboxEndpointHandlePreflightEchoMarker.readyForManagedAuditSandboxAdapterConnection=false"
                );
        assertThat(rehearsal.verificationHint().nodeVerificationActions())
                .contains(
                        "Compare managedAuditSandboxEndpointHandlePreflightEchoMarker.consumedByNodeSandboxEndpointHandlePreflightReviewProfile with Node v258",
                        "Require managedAuditSandboxEndpointHandlePreflightEchoMarker.readyForNodeV259SandboxEndpointHandleUpstreamEchoVerification=true before Node v259",
                        "Keep managedAuditSandboxEndpointHandlePreflightEchoMarker.sideEffectBoundary.externalRequestSent=false"
                );

        ReleaseApprovalRehearsalResponse repeated =
                service.releaseApprovalRehearsal(paddedHeaderBackedRehearsalRequest());
        assertThat(repeated.managedAuditSandboxEndpointHandlePreflightEchoMarker().markerDigest())
                .isEqualTo(marker.markerDigest());
    }


    @Test
    void releaseApprovalRehearsalExposesSandboxEndpointCredentialResolverDecisionEchoMarker() {
        OpsEvidenceService service = readOnlyFixtureService();

        ReleaseApprovalRehearsalResponse rehearsal =
                service.releaseApprovalRehearsal(headerBackedRehearsalRequest());

        ReleaseApprovalSandboxEndpointCredentialResolverDecisionEchoRecords.RehearsalManagedAuditSandboxEndpointCredentialResolverDecisionEchoMarker marker =
                rehearsal.managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker();
        assertThat(marker.markerVersion())
                .isEqualTo(
                        "java-release-approval-rehearsal-managed-audit-sandbox-endpoint-credential-resolver-decision-echo-marker.v1"
                );
        assertThat(marker.sourceEndpointHandlePreflightEchoMarkerSchemaVersion())
                .isEqualTo("java-release-approval-rehearsal-response-schema.v26");
        assertThat(marker.consumedByNodeSandboxEndpointCredentialResolverDecisionRecordVersion())
                .isEqualTo("Node v260");
        assertThat(marker.consumedByNodeSandboxEndpointCredentialResolverDecisionRecordProfile())
                .isEqualTo(
                        "managed-audit-manual-sandbox-connection-sandbox-endpoint-credential-resolver-decision-record.v1"
                );
        assertThat(marker.consumedByNodeSandboxEndpointCredentialResolverDecisionRecordEndpoint())
                .isEqualTo(
                        "/api/v1/audit/managed-audit-manual-sandbox-connection-sandbox-endpoint-credential-resolver-decision-record"
                );
        assertThat(marker.consumedByNodeSandboxEndpointCredentialResolverDecisionRecordMarkdownEndpoint())
                .isEqualTo(
                        "/api/v1/audit/managed-audit-manual-sandbox-connection-sandbox-endpoint-credential-resolver-decision-record?format=markdown"
                );
        assertThat(marker.consumedByNodeSandboxEndpointCredentialResolverDecisionRecordState())
                .isEqualTo("sandbox-endpoint-credential-resolver-decision-record-ready");
        assertThat(marker.sourceNodeSandboxEndpointHandleUpstreamEchoVerificationVersion())
                .isEqualTo("Node v259");
        assertThat(marker.sourceNodeSandboxEndpointHandleUpstreamEchoVerificationProfile())
                .isEqualTo(
                        "managed-audit-manual-sandbox-connection-sandbox-endpoint-handle-upstream-echo-verification.v1"
                );
        assertThat(marker.sourceNodeSandboxEndpointHandleUpstreamEchoVerificationEndpoint())
                .isEqualTo(
                        "/api/v1/audit/managed-audit-manual-sandbox-connection-sandbox-endpoint-handle-upstream-echo-verification"
                );
        assertThat(marker.sourceNodeSandboxEndpointHandleUpstreamEchoVerificationState())
                .isEqualTo("sandbox-endpoint-handle-upstream-echo-verification-ready");
        assertThat(marker.nextNodeSandboxEndpointCredentialResolverUpstreamEchoVerificationVersion())
                .isEqualTo("Node v261");
        assertThat(marker.nextNodeSandboxEndpointCredentialResolverUpstreamEchoVerificationProfile())
                .isEqualTo(
                        "managed-audit-manual-sandbox-connection-sandbox-endpoint-credential-resolver-upstream-echo-verification.v1"
                );
        assertThat(marker.nodeV261MayConsume()).isTrue();
        assertThat(marker.recordMode()).isEqualTo("sandbox-endpoint-credential-resolver-decision-record-only");
        assertThat(marker.sourceSpan()).isEqualTo("Node v259 sandbox endpoint handle upstream echo verification");
        assertThat(marker.sourceNodeV259().sourceVersion()).isEqualTo("Node v259");
        assertThat(marker.sourceNodeV259().verificationState())
                .isEqualTo("sandbox-endpoint-handle-upstream-echo-verification-ready");
        assertThat(marker.sourceNodeV259().endpointHandleAligned()).isTrue();
        assertThat(marker.sourceNodeV259().credentialHandleAligned()).isTrue();
        assertThat(marker.sourceNodeV259().reviewCountsAligned()).isTrue();
        assertThat(marker.sourceNodeV259().policyReviewsAligned()).isTrue();
        assertThat(marker.sourceNodeV259().operatorWindowAligned()).isTrue();
        assertThat(marker.sourceNodeV259().credentialBoundaryAligned()).isTrue();
        assertThat(marker.sourceNodeV259().rawEndpointBoundaryAligned()).isTrue();
        assertThat(marker.sourceNodeV259().connectionBoundaryAligned()).isTrue();
        assertThat(marker.sourceNodeV259().writeBoundaryAligned()).isTrue();
        assertThat(marker.sourceNodeV259().autoStartBoundaryAligned()).isTrue();
        assertThat(marker.sourceNodeV259().miniKvNonParticipationAligned()).isTrue();
        assertThat(marker.sourceNodeV259().nodeV259BlocksRealConnection()).isTrue();
        assertThat(marker.sourceNodeV259().evidenceFileCount()).isEqualTo(6);
        assertThat(marker.sourceNodeV259().matchedSnippetCount()).isEqualTo(39);
        assertThat(marker.sourceNodeV259().checkCount()).isEqualTo(19);
        assertThat(marker.sourceNodeV259().passedCheckCount()).isEqualTo(19);
        assertThat(marker.sourceNodeV259().productionBlockerCount()).isZero();
        assertThat(marker.sourceNodeV259().warningCount()).isEqualTo(2);
        assertThat(marker.sourceNodeV259().recommendationCount()).isEqualTo(2);
        assertThat(marker.sourceNodeV259().sourceNodeV258Ready()).isTrue();
        assertThat(marker.sourceNodeV259().javaV104Ready()).isTrue();
        assertThat(marker.sourceNodeV259().miniKvV113Ready()).isTrue();
        assertThat(marker.sourceNodeV259().readyForNodeV260CredentialResolverDecisionRecord()).isTrue();
        assertThat(marker.decisionRecord().decisionDigest()).startsWith("sha256:");
        assertThat(marker.decisionRecord().recordMode())
                .isEqualTo("sandbox-endpoint-credential-resolver-decision-record-only");
        assertThat(marker.decisionRecord().decisionScope())
                .isEqualTo("managed-audit-sandbox-endpoint-credential-resolver");
        assertThat(marker.decisionRecord().decisionStatus())
                .isEqualTo("human-review-required-before-credential-resolution");
        assertThat(marker.decisionRecord().endpointHandle())
                .isEqualTo("ORDEROPS_MANAGED_AUDIT_SANDBOX_ENDPOINT_HANDLE");
        assertThat(marker.decisionRecord().credentialHandle())
                .isEqualTo("ORDEROPS_MANAGED_AUDIT_SANDBOX_CREDENTIAL_HANDLE");
        assertThat(marker.decisionRecord().resolverPolicyHandle())
                .isEqualTo("ORDEROPS_MANAGED_AUDIT_SANDBOX_CREDENTIAL_RESOLVER_POLICY_HANDLE");
        assertThat(marker.decisionRecord().approvalMarker())
                .isEqualTo("ORDEROPS_MANAGED_AUDIT_CREDENTIAL_RESOLVER_APPROVAL_MARKER");
        assertThat(marker.decisionRecord().operatorIdentityRequired()).isTrue();
        assertThat(marker.decisionRecord().approvalCorrelationRequired()).isTrue();
        assertThat(marker.decisionRecord().resolverMode()).isEqualTo("policy-record-only-no-value-read");
        assertThat(marker.decisionRecord().resolverCandidateImplementation()).isEqualTo("not-implemented");
        assertThat(marker.decisionRecord().requiredDecisionFieldCount()).isEqualTo(8);
        assertThat(marker.decisionRecord().explicitNoGoConditionCount()).isEqualTo(9);
        assertThat(marker.decisionRecord().requiredDecisionFields())
                .extracting(ReleaseApprovalSandboxEndpointCredentialResolverDecisionEchoRecords.RehearsalSandboxEndpointCredentialResolverDecisionField::id)
                .containsExactly(
                        "endpoint-handle",
                        "credential-handle",
                        "resolver-policy-handle",
                        "approval-marker",
                        "operator-identity",
                        "approval-correlation",
                        "redaction-policy",
                        "fallback-rotation-plan"
                );
        assertThat(marker.decisionRecord().requiredDecisionFields())
                .allMatch(field -> field.required() && !field.nodeMayReadValue());
        assertThat(marker.decisionRecord().explicitNoGoConditions())
                .extracting(ReleaseApprovalSandboxEndpointCredentialResolverDecisionEchoRecords.RehearsalSandboxEndpointCredentialResolverNoGoCondition::code)
                .containsExactly(
                        "CREDENTIAL_VALUE_REQUIRED",
                        "RAW_ENDPOINT_URL_REQUIRED",
                        "REAL_CONNECTION_REQUIRED",
                        "EXTERNAL_REQUEST_REQUIRED",
                        "SCHEMA_MIGRATION_REQUIRED",
                        "UPSTREAM_WRITE_REQUIRED",
                        "AUTO_START_REQUIRED",
                        "MINI_KV_BACKEND_REQUIRED",
                        "PRODUCTION_WINDOW_REQUIRED"
                );
        assertThat(marker.decisionRecord().explicitNoGoConditions())
                .allMatch(noGoCondition -> !noGoCondition.allowed());
        assertThat(marker.decisionRecord().credentialValueMayBeRead()).isFalse();
        assertThat(marker.decisionRecord().credentialValueMayBeLoaded()).isFalse();
        assertThat(marker.decisionRecord().credentialValueMayBeStored()).isFalse();
        assertThat(marker.decisionRecord().rawEndpointUrlMayBeParsed()).isFalse();
        assertThat(marker.decisionRecord().managedAuditConnectionMayOpen()).isFalse();
        assertThat(marker.decisionRecord().schemaMigrationMayExecute()).isFalse();
        assertThat(marker.decisionRecord().externalRequestMayBeSent()).isFalse();
        assertThat(marker.decisionRecord().nodeMayStartJavaOrMiniKv()).isFalse();
        assertThat(marker.decisionRecord().miniKvMayActAsManagedAuditStorage()).isFalse();
        assertThat(marker.decisionRecord().approvalLedgerMayBeWritten()).isFalse();
        assertThat(marker.sideEffectBoundary().readOnlyDecisionRecord()).isTrue();
        assertThat(marker.sideEffectBoundary().credentialResolverDecisionOnly()).isTrue();
        assertThat(marker.sideEffectBoundary().executionAllowed()).isFalse();
        assertThat(marker.sideEffectBoundary().connectsManagedAudit()).isFalse();
        assertThat(marker.sideEffectBoundary().readsManagedAuditCredential()).isFalse();
        assertThat(marker.sideEffectBoundary().credentialValueRead()).isFalse();
        assertThat(marker.sideEffectBoundary().credentialValueLoaded()).isFalse();
        assertThat(marker.sideEffectBoundary().rawEndpointUrlParsed()).isFalse();
        assertThat(marker.sideEffectBoundary().externalRequestSent()).isFalse();
        assertThat(marker.sideEffectBoundary().schemaMigrationExecuted()).isFalse();
        assertThat(marker.sideEffectBoundary().automaticUpstreamStart()).isFalse();
        assertThat(marker.sideEffectBoundary().approvalLedgerWritten()).isFalse();
        assertThat(marker.sideEffectBoundary().javaStarted()).isFalse();
        assertThat(marker.sideEffectBoundary().miniKvStarted()).isFalse();
        assertThat(marker.sourceNodeV259Echoed()).isTrue();
        assertThat(marker.decisionFieldsEchoed()).isTrue();
        assertThat(marker.endpointHandleEchoed()).isTrue();
        assertThat(marker.credentialHandleEchoed()).isTrue();
        assertThat(marker.resolverPolicyEchoed()).isTrue();
        assertThat(marker.approvalMarkerEchoed()).isTrue();
        assertThat(marker.operatorIdentityRequirementEchoed()).isTrue();
        assertThat(marker.approvalCorrelationRequirementEchoed()).isTrue();
        assertThat(marker.redactionPolicyEchoed()).isTrue();
        assertThat(marker.fallbackRotationPlanEchoed()).isTrue();
        assertThat(marker.explicitNoGoConditionsEchoed()).isTrue();
        assertThat(marker.sideEffectBoundaryEchoed()).isTrue();
        assertThat(marker.readyForNodeV261SandboxEndpointCredentialResolverUpstreamEchoVerification()).isTrue();
        assertThat(marker.readyForManagedAuditSandboxAdapterConnection()).isFalse();
        assertThat(marker.readyForProductionAudit()).isFalse();
        assertThat(marker.readyForProductionWindow()).isFalse();
        assertThat(marker.nodeMayTreatAsProductionAuditRecord()).isFalse();
        assertThat(marker.requiredDecisionFieldIds()).containsExactlyElementsOf(
                marker.decisionRecord().requiredDecisionFields().stream()
                        .map(ReleaseApprovalSandboxEndpointCredentialResolverDecisionEchoRecords.RehearsalSandboxEndpointCredentialResolverDecisionField::id)
                        .toList()
        );
        assertThat(marker.explicitNoGoConditionCodes()).containsExactlyElementsOf(
                marker.decisionRecord().explicitNoGoConditions().stream()
                        .map(ReleaseApprovalSandboxEndpointCredentialResolverDecisionEchoRecords.RehearsalSandboxEndpointCredentialResolverNoGoCondition::code)
                        .toList()
        );
        assertThat(marker.nodeWarningCodes())
                .containsExactly("DECISION_RECORD_ONLY", "REAL_CREDENTIAL_STILL_ABSENT");
        assertThat(marker.nodeRecommendationCodes())
                .containsExactly("START_POST_V260_PLAN", "DESIGN_DISABLED_RESOLVER_PRECHECK_LATER");
        assertThat(marker.nextRequiredEchoVersions())
                .contains(
                        "Java v105 sandbox endpoint credential resolver decision echo marker",
                        "mini-kv v114 sandbox endpoint credential resolver non-participation receipt"
                );
        assertThat(marker.markerWarnings()).isEmpty();
        assertThat(marker.markerDigest()).startsWith("sha256:");
        assertThat(rehearsal.verificationHint().schemaFields())
                .contains("managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker");
        assertThat(rehearsal.verificationHint().warningDigestInputs())
                .contains(
                        "managedAuditSandboxEndpointCredentialResolverDecisionEchoMarkerWarnings",
                        "sandboxEndpointCredentialResolverDecisionEchoMarkerDigest",
                        "sandboxEndpointCredentialResolverDecisionRawEndpointUrlMayBeParsed"
                );
        assertThat(rehearsal.verificationHint().proofClaims())
                .contains(
                        "managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker.decisionRecord.requiredDecisionFieldCount=8",
                        "managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker.decisionRecord.credentialValueMayBeRead=false",
                        "managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker.readyForManagedAuditSandboxAdapterConnection=false"
                );
        assertThat(rehearsal.verificationHint().nodeVerificationActions())
                .contains(
                        "Compare managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker.consumedByNodeSandboxEndpointCredentialResolverDecisionRecordProfile with Node v260",
                        "Require managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker.readyForNodeV261SandboxEndpointCredentialResolverUpstreamEchoVerification=true before Node v261",
                        "Keep managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker.sideEffectBoundary.externalRequestSent=false"
                );

        ReleaseApprovalRehearsalResponse repeated =
                service.releaseApprovalRehearsal(paddedHeaderBackedRehearsalRequest());
        assertThat(repeated.managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker().markerDigest())
                .isEqualTo(marker.markerDigest());
    }

}
