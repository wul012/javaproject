package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverDisabledPrecheckEchoRecords
        .RehearsalManagedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker;
import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverDisabledPrecheckEchoRecords
        .RehearsalSandboxEndpointCredentialResolverEnvHandle;
import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverDisabledPrecheckEchoRecords
        .RehearsalSandboxEndpointCredentialResolverFailureClass;
import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverFakeShellArchiveEchoRecords
        .RehearsalManagedAuditSandboxEndpointCredentialResolverFakeShellArchiveEchoReceipt;
import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverTestOnlyShellEchoRecords
        .RehearsalManagedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarker;
import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverTestOnlyShellEchoRecords
        .RehearsalSandboxEndpointCredentialResolverTestOnlyShellFailureMapping;
import org.junit.jupiter.api.Test;

class OpsEvidenceServiceCredentialResolverEarlyEchoTests extends OpsEvidenceServiceRehearsalTestSupport {

    @Test
    void releaseApprovalRehearsalAddsSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker() {
        OpsEvidenceService service = readOnlyFixtureService();

        ReleaseApprovalRehearsalResponse rehearsal =
                service.releaseApprovalRehearsal(headerBackedRehearsalRequest());

        RehearsalManagedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker marker =
                rehearsal.managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker();
        assertThat(marker.markerVersion())
                .isEqualTo(
                        "java-release-approval-rehearsal-managed-audit-sandbox-endpoint-credential-resolver-disabled-precheck-echo-marker.v1"
                );
        assertThat(marker.sourceCredentialResolverDecisionEchoMarkerSchemaVersion())
                .isEqualTo("java-release-approval-rehearsal-response-schema.v27");
        assertThat(marker.consumedByNodeSandboxEndpointCredentialResolverDisabledPrecheckVersion())
                .isEqualTo("Node v262");
        assertThat(marker.consumedByNodeSandboxEndpointCredentialResolverDisabledPrecheckProfile())
                .isEqualTo(
                        "managed-audit-manual-sandbox-connection-sandbox-endpoint-credential-resolver-disabled-precheck.v1"
                );
        assertThat(marker.consumedByNodeSandboxEndpointCredentialResolverDisabledPrecheckEndpoint())
                .isEqualTo(
                        "/api/v1/audit/managed-audit-manual-sandbox-connection-sandbox-endpoint-credential-resolver-disabled-precheck"
                );
        assertThat(marker.consumedByNodeSandboxEndpointCredentialResolverDisabledPrecheckMarkdownEndpoint())
                .isEqualTo(
                        "/api/v1/audit/managed-audit-manual-sandbox-connection-sandbox-endpoint-credential-resolver-disabled-precheck?format=markdown"
                );
        assertThat(marker.consumedByNodeSandboxEndpointCredentialResolverDisabledPrecheckState())
                .isEqualTo("sandbox-endpoint-credential-resolver-disabled-precheck-ready");
        assertThat(marker.sourceNodeSandboxEndpointCredentialResolverUpstreamEchoVerificationVersion())
                .isEqualTo("Node v261");
        assertThat(marker.sourceNodeSandboxEndpointCredentialResolverUpstreamEchoVerificationProfile())
                .isEqualTo(
                        "managed-audit-manual-sandbox-connection-sandbox-endpoint-credential-resolver-upstream-echo-verification.v1"
                );
        assertThat(marker.sourceNodeSandboxEndpointCredentialResolverUpstreamEchoVerificationState())
                .isEqualTo("sandbox-endpoint-credential-resolver-upstream-echo-verification-ready");
        assertThat(marker.nextNodeSandboxEndpointCredentialResolverDisabledPrecheckUpstreamEchoVerificationVersion())
                .isEqualTo("Node v263");
        assertThat(marker.nodeV263MayConsume()).isTrue();
        assertThat(marker.precheckMode()).isEqualTo("sandbox-endpoint-credential-resolver-disabled-precheck-only");
        assertThat(marker.sourceSpan()).isEqualTo("Node v261 credential resolver upstream echo verification");
        assertThat(marker.sourceNodeV261().sourceVersion()).isEqualTo("Node v261");
        assertThat(marker.sourceNodeV261().verificationMode())
                .isEqualTo("java-v105-plus-mini-kv-v114-credential-resolver-upstream-echo-verification-only");
        assertThat(marker.sourceNodeV261().sourceSpan()).isEqualTo("Node v260 + Java v105 + mini-kv v114");
        assertThat(marker.sourceNodeV261().sourceNodeV260Ready()).isTrue();
        assertThat(marker.sourceNodeV261().javaV105EchoReady()).isTrue();
        assertThat(marker.sourceNodeV261().miniKvV114NonParticipationReady()).isTrue();
        assertThat(marker.sourceNodeV261().decisionRecordAligned()).isTrue();
        assertThat(marker.sourceNodeV261().credentialBoundaryAligned()).isTrue();
        assertThat(marker.sourceNodeV261().rawEndpointBoundaryAligned()).isTrue();
        assertThat(marker.sourceNodeV261().connectionBoundaryAligned()).isTrue();
        assertThat(marker.sourceNodeV261().writeBoundaryAligned()).isTrue();
        assertThat(marker.sourceNodeV261().autoStartBoundaryAligned()).isTrue();
        assertThat(marker.sourceNodeV261().upstreamActionsStillDisabled()).isTrue();
        assertThat(marker.sourceNodeV261().credentialResolverExecutionAllowed()).isFalse();
        assertThat(marker.sourceNodeV261().credentialValueRead()).isFalse();
        assertThat(marker.sourceNodeV261().credentialValueLoaded()).isFalse();
        assertThat(marker.sourceNodeV261().rawEndpointUrlParsed()).isFalse();
        assertThat(marker.sourceNodeV261().externalRequestSent()).isFalse();
        assertThat(marker.sourceNodeV261().connectsManagedAudit()).isFalse();
        assertThat(marker.sourceNodeV261().schemaMigrationExecuted()).isFalse();
        assertThat(marker.sourceNodeV261().automaticUpstreamStart()).isFalse();
        assertThat(marker.sourceNodeV261().checkCount()).isEqualTo(20);
        assertThat(marker.sourceNodeV261().passedCheckCount()).isEqualTo(20);
        assertThat(marker.sourceNodeV261().productionBlockerCount()).isZero();
        assertThat(marker.sourceNodeV261().warningCount()).isEqualTo(2);
        assertThat(marker.sourceNodeV261().recommendationCount()).isEqualTo(2);
        assertThat(marker.sourceNodeV261().readyForNodeV262CredentialResolverDisabledPrecheck()).isTrue();
        assertThat(marker.disabledPrecheck().precheckDigest()).startsWith("sha256:");
        assertThat(marker.disabledPrecheck().precheckMode())
                .isEqualTo("sandbox-endpoint-credential-resolver-disabled-precheck-only");
        assertThat(marker.disabledPrecheck().resolverImplementationStatus()).isEqualTo("not-implemented");
        assertThat(marker.disabledPrecheck().secretProviderImplementationStatus()).isEqualTo("not-implemented");
        assertThat(marker.disabledPrecheck().resolverClientMayBeInstantiated()).isFalse();
        assertThat(marker.disabledPrecheck().secretProviderMayBeInstantiated()).isFalse();
        assertThat(marker.disabledPrecheck().credentialValueMayBeLoaded()).isFalse();
        assertThat(marker.disabledPrecheck().rawEndpointUrlMayBeParsed()).isFalse();
        assertThat(marker.disabledPrecheck().externalRequestMayBeSent()).isFalse();
        assertThat(marker.disabledPrecheck().optInGateRequired()).isTrue();
        assertThat(marker.disabledPrecheck().requiredEnvHandleCount()).isEqualTo(6);
        assertThat(marker.disabledPrecheck().optInGateCount()).isEqualTo(2);
        assertThat(marker.disabledPrecheck().failureClassCount()).isEqualTo(7);
        assertThat(marker.disabledPrecheck().dryRunResponseFieldCount()).isEqualTo(12);
        assertThat(marker.disabledPrecheck().inheritedNoGoConditionCount()).isEqualTo(9);
        assertThat(marker.disabledPrecheck().requiredEnvHandles())
                .extracting(RehearsalSandboxEndpointCredentialResolverEnvHandle::name)
                .containsExactly(
                        "ORDEROPS_MANAGED_AUDIT_CREDENTIAL_RESOLVER_ENABLED",
                        "ORDEROPS_MANAGED_AUDIT_SANDBOX_ENDPOINT_RESOLUTION_ENABLED",
                        "ORDEROPS_MANAGED_AUDIT_SANDBOX_ENDPOINT_HANDLE",
                        "ORDEROPS_MANAGED_AUDIT_SANDBOX_CREDENTIAL_HANDLE",
                        "ORDEROPS_MANAGED_AUDIT_SANDBOX_CREDENTIAL_RESOLVER_POLICY_HANDLE",
                        "ORDEROPS_MANAGED_AUDIT_CREDENTIAL_RESOLVER_APPROVAL_MARKER"
                );
        assertThat(marker.disabledPrecheck().requiredEnvHandles())
                .allMatch(handle -> !handle.valueRequiredForPrecheck()
                        && !handle.credentialValue()
                        && !handle.rawEndpointValue());
        assertThat(marker.disabledPrecheck().optInGates())
                .allMatch(gate -> "true".equals(gate.requiredValueForFutureResolver())
                        && "false".equals(gate.currentDefault())
                        && gate.precheckTreatsEnabledAsBlocked()
                        && gate.operatorApprovalRequired());
        assertThat(marker.disabledPrecheck().failureTaxonomy())
                .extracting(RehearsalSandboxEndpointCredentialResolverFailureClass::code)
                .containsExactly(
                        "RESOLVER_DISABLED",
                        "APPROVAL_MARKER_MISSING",
                        "CREDENTIAL_HANDLE_MISSING",
                        "CREDENTIAL_VALUE_REQUESTED",
                        "RAW_ENDPOINT_URL_REQUESTED",
                        "EXTERNAL_REQUEST_REQUESTED",
                        "SCHEMA_MIGRATION_REQUESTED"
                );
        assertThat(marker.disabledPrecheck().dryRunResponseShape().fields())
                .containsExactly(
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
                        "nextAction"
                );
        assertThat(marker.disabledPrecheck().dryRunResponseShape().readyState())
                .isEqualTo("sandbox-endpoint-credential-resolver-disabled-precheck-ready");
        assertThat(marker.disabledPrecheck().dryRunResponseShape().resolverClientInstantiated()).isFalse();
        assertThat(marker.disabledPrecheck().dryRunResponseShape().secretProviderInstantiated()).isFalse();
        assertThat(marker.disabledPrecheck().dryRunResponseShape().credentialValueRead()).isFalse();
        assertThat(marker.disabledPrecheck().dryRunResponseShape().credentialValueLoaded()).isFalse();
        assertThat(marker.disabledPrecheck().dryRunResponseShape().rawEndpointUrlParsed()).isFalse();
        assertThat(marker.disabledPrecheck().dryRunResponseShape().externalRequestSent()).isFalse();
        assertThat(marker.disabledPrecheck().dryRunResponseShape().connectsManagedAudit()).isFalse();
        assertThat(marker.disabledPrecheck().dryRunResponseShape().schemaMigrationExecuted()).isFalse();
        assertThat(marker.disabledPrecheck().inheritedNoGoConditions())
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
        assertThat(marker.sideEffectBoundary().readOnlyDisabledPrecheck()).isTrue();
        assertThat(marker.sideEffectBoundary().disabledCredentialResolverPrecheckOnly()).isTrue();
        assertThat(marker.sideEffectBoundary().credentialResolverExecutionAllowed()).isFalse();
        assertThat(marker.sideEffectBoundary().connectsManagedAudit()).isFalse();
        assertThat(marker.sideEffectBoundary().readsManagedAuditCredential()).isFalse();
        assertThat(marker.sideEffectBoundary().credentialValueRead()).isFalse();
        assertThat(marker.sideEffectBoundary().credentialValueLoaded()).isFalse();
        assertThat(marker.sideEffectBoundary().rawEndpointUrlParsed()).isFalse();
        assertThat(marker.sideEffectBoundary().externalRequestSent()).isFalse();
        assertThat(marker.sideEffectBoundary().secretProviderInstantiated()).isFalse();
        assertThat(marker.sideEffectBoundary().resolverClientInstantiated()).isFalse();
        assertThat(marker.sideEffectBoundary().schemaMigrationExecuted()).isFalse();
        assertThat(marker.sideEffectBoundary().automaticUpstreamStart()).isFalse();
        assertThat(marker.sourceNodeV261Echoed()).isTrue();
        assertThat(marker.envHandlesEchoed()).isTrue();
        assertThat(marker.optInGatesEchoed()).isTrue();
        assertThat(marker.failureTaxonomyEchoed()).isTrue();
        assertThat(marker.dryRunResponseShapeEchoed()).isTrue();
        assertThat(marker.inheritedNoGoConditionsEchoed()).isTrue();
        assertThat(marker.resolverImplementationAbsentEchoed()).isTrue();
        assertThat(marker.secretProviderAbsentEchoed()).isTrue();
        assertThat(marker.sideEffectBoundaryEchoed()).isTrue();
        assertThat(marker.upstreamActionsStillDisabledEchoed()).isTrue();
        assertThat(marker.readyForNodeV263SandboxEndpointCredentialResolverDisabledPrecheckUpstreamEchoVerification())
                .isTrue();
        assertThat(marker.readyForManagedAuditSandboxAdapterConnection()).isFalse();
        assertThat(marker.readyForProductionAudit()).isFalse();
        assertThat(marker.readyForProductionWindow()).isFalse();
        assertThat(marker.nodeMayTreatAsProductionAuditRecord()).isFalse();
        assertThat(marker.nodeWarningCodes())
                .containsExactly("DISABLED_PRECHECK_ONLY", "UPSTREAM_ECHO_REQUIRED_NEXT");
        assertThat(marker.nodeRecommendationCodes())
                .containsExactly("ASK_JAVA_MINI_KV_FOR_ECHO_NEXT", "KEEP_REAL_RESOLVER_OUT_OF_SCOPE");
        assertThat(marker.nextRequiredEchoVersions())
                .contains(
                        "Java v106 sandbox endpoint credential resolver disabled precheck echo marker",
                        "mini-kv v115 sandbox endpoint credential resolver disabled precheck non-participation receipt"
                );
        assertThat(marker.markerWarnings()).isEmpty();
        assertThat(marker.markerDigest()).startsWith("sha256:");
        assertThat(rehearsal.verificationHint().schemaFields())
                .contains("managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker");
        assertThat(rehearsal.verificationHint().warningDigestInputs())
                .contains(
                        "managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarkerWarnings",
                        "sandboxEndpointCredentialResolverDisabledPrecheckEchoMarkerDigest",
                        "sandboxEndpointCredentialResolverDisabledPrecheckRawEndpointUrlMayBeParsed"
                );
        assertThat(rehearsal.verificationHint().proofClaims())
                .contains(
                        "managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker.disabledPrecheck.requiredEnvHandleCount=6",
                        "managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker.disabledPrecheck.resolverClientMayBeInstantiated=false",
                        "managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker.readyForManagedAuditSandboxAdapterConnection=false"
                );
        assertThat(rehearsal.verificationHint().nodeVerificationActions())
                .contains(
                        "Compare managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker.consumedByNodeSandboxEndpointCredentialResolverDisabledPrecheckProfile with Node v262",
                        "Require managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker.readyForNodeV263SandboxEndpointCredentialResolverDisabledPrecheckUpstreamEchoVerification=true before Node v263",
                        "Keep managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker.sideEffectBoundary.externalRequestSent=false"
                );

        ReleaseApprovalRehearsalResponse repeated =
                service.releaseApprovalRehearsal(paddedHeaderBackedRehearsalRequest());
        assertThat(repeated.managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker().markerDigest())
                .isEqualTo(marker.markerDigest());
    }


    @Test
    void releaseApprovalRehearsalAddsSandboxEndpointCredentialResolverTestOnlyShellEchoMarker() {
        OpsEvidenceService service = readOnlyFixtureService();

        ReleaseApprovalRehearsalResponse rehearsal =
                service.releaseApprovalRehearsal(headerBackedRehearsalRequest());

        RehearsalManagedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarker marker =
                rehearsal.managedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarker();
        assertThat(marker.markerVersion())
                .isEqualTo(
                        "java-release-approval-rehearsal-managed-audit-sandbox-endpoint-credential-resolver-test-only-shell-echo-marker.v1"
                );
        assertThat(marker.sourceDisabledPrecheckEchoMarkerSchemaVersion())
                .isEqualTo("java-release-approval-rehearsal-response-schema.v28");
        assertThat(marker.consumedByNodeSandboxEndpointCredentialResolverTestOnlyShellContractVersion())
                .isEqualTo("Node v264");
        assertThat(marker.consumedByNodeSandboxEndpointCredentialResolverTestOnlyShellContractProfile())
                .isEqualTo(
                        "managed-audit-manual-sandbox-connection-sandbox-endpoint-credential-resolver-test-only-shell-contract.v1"
                );
        assertThat(marker.consumedByNodeSandboxEndpointCredentialResolverTestOnlyShellContractState())
                .isEqualTo("sandbox-endpoint-credential-resolver-test-only-shell-contract-ready");
        assertThat(marker.sourceNodeSandboxEndpointCredentialResolverDisabledPrecheckUpstreamEchoVerificationVersion())
                .isEqualTo("Node v263");
        assertThat(marker.sourceNodeSandboxEndpointCredentialResolverDisabledPrecheckUpstreamEchoVerificationState())
                .isEqualTo(
                        "sandbox-endpoint-credential-resolver-disabled-precheck-upstream-echo-verification-ready"
                );
        assertThat(marker.nextNodeSandboxEndpointCredentialResolverTestOnlyShellUpstreamEchoVerificationVersion())
                .isEqualTo("Node v265");
        assertThat(marker.nodeV265MayConsume()).isTrue();
        assertThat(marker.shellMode()).isEqualTo("test-only-fake-resolver-contract");
        assertThat(marker.sourceSpan()).isEqualTo("Node v264 credential resolver test-only shell contract");
        assertThat(marker.sourceNodeV263().sourceVersion()).isEqualTo("Node v263");
        assertThat(marker.sourceNodeV263().verificationMode())
                .isEqualTo(
                        "java-v106-plus-mini-kv-v115-disabled-credential-resolver-precheck-upstream-echo-verification-only"
                );
        assertThat(marker.sourceNodeV263().sourceSpan()).isEqualTo("Node v262 + Java v106 + mini-kv v115");
        assertThat(marker.sourceNodeV263().sourceNodeV262Ready()).isTrue();
        assertThat(marker.sourceNodeV263().javaV106EchoReady()).isTrue();
        assertThat(marker.sourceNodeV263().miniKvV115NonParticipationReady()).isTrue();
        assertThat(marker.sourceNodeV263().disabledPrecheckAligned()).isTrue();
        assertThat(marker.sourceNodeV263().requiredEnvHandlesAligned()).isTrue();
        assertThat(marker.sourceNodeV263().optInGatesAligned()).isTrue();
        assertThat(marker.sourceNodeV263().failureTaxonomyAligned()).isTrue();
        assertThat(marker.sourceNodeV263().dryRunResponseShapeAligned()).isTrue();
        assertThat(marker.sourceNodeV263().inheritedNoGoConditionsAligned()).isTrue();
        assertThat(marker.sourceNodeV263().credentialBoundaryAligned()).isTrue();
        assertThat(marker.sourceNodeV263().rawEndpointBoundaryAligned()).isTrue();
        assertThat(marker.sourceNodeV263().connectionBoundaryAligned()).isTrue();
        assertThat(marker.sourceNodeV263().writeBoundaryAligned()).isTrue();
        assertThat(marker.sourceNodeV263().autoStartBoundaryAligned()).isTrue();
        assertThat(marker.sourceNodeV263().upstreamActionsStillDisabled()).isTrue();
        assertThat(marker.sourceNodeV263().credentialResolverExecutionAllowed()).isFalse();
        assertThat(marker.sourceNodeV263().credentialValueRead()).isFalse();
        assertThat(marker.sourceNodeV263().credentialValueLoaded()).isFalse();
        assertThat(marker.sourceNodeV263().credentialValueStored()).isFalse();
        assertThat(marker.sourceNodeV263().rawEndpointUrlParsed()).isFalse();
        assertThat(marker.sourceNodeV263().rawEndpointUrlIncluded()).isFalse();
        assertThat(marker.sourceNodeV263().externalRequestSent()).isFalse();
        assertThat(marker.sourceNodeV263().secretProviderInstantiated()).isFalse();
        assertThat(marker.sourceNodeV263().resolverClientInstantiated()).isFalse();
        assertThat(marker.sourceNodeV263().connectsManagedAudit()).isFalse();
        assertThat(marker.sourceNodeV263().schemaMigrationExecuted()).isFalse();
        assertThat(marker.sourceNodeV263().automaticUpstreamStart()).isFalse();
        assertThat(marker.sourceNodeV263().failureClassCount()).isEqualTo(7);
        assertThat(marker.sourceNodeV263().requiredEnvHandleCount()).isEqualTo(6);
        assertThat(marker.sourceNodeV263().optInGateCount()).isEqualTo(2);
        assertThat(marker.sourceNodeV263().dryRunResponseFieldCount()).isEqualTo(12);
        assertThat(marker.sourceNodeV263().inheritedNoGoConditionCount()).isEqualTo(9);
        assertThat(marker.sourceNodeV263().checkCount()).isEqualTo(19);
        assertThat(marker.sourceNodeV263().passedCheckCount()).isEqualTo(19);
        assertThat(marker.sourceNodeV263().productionBlockerCount()).isZero();
        assertThat(marker.sourceNodeV263().warningCount()).isEqualTo(2);
        assertThat(marker.sourceNodeV263().recommendationCount()).isEqualTo(2);
        assertThat(marker.sourceNodeV263().readyForNodeV264CredentialResolverTestOnlyShellContract()).isTrue();
        assertThat(marker.resolverShellContract().contractDigest()).startsWith("sha256:");
        assertThat(marker.resolverShellContract().shellName())
                .isEqualTo("ManagedAuditSandboxEndpointCredentialResolverTestOnlyShell");
        assertThat(marker.resolverShellContract().shellMode()).isEqualTo("test-only-fake-resolver-contract");
        assertThat(marker.resolverShellContract().resolverKind()).isEqualTo("fake-in-memory");
        assertThat(marker.resolverShellContract().realResolverImplemented()).isFalse();
        assertThat(marker.resolverShellContract().realSecretProviderAllowed()).isFalse();
        assertThat(marker.resolverShellContract().fakeResolverOnly()).isTrue();
        assertThat(marker.resolverShellContract().requestShapeFieldCount()).isEqualTo(9);
        assertThat(marker.resolverShellContract().responseShapeFieldCount()).isEqualTo(13);
        assertThat(marker.resolverShellContract().failureMappingCount()).isEqualTo(7);
        assertThat(marker.resolverShellContract().guardConditionCount()).isEqualTo(10);
        assertThat(marker.resolverShellContract().requestShape().fields())
                .containsExactly(
                        "requestId",
                        "operation",
                        "credentialHandle",
                        "endpointHandle",
                        "resolverPolicyHandle",
                        "approvalMarker",
                        "approvalCorrelationId",
                        "dryRun",
                        "fakeResolverOnly"
                );
        assertThat(marker.resolverShellContract().requestShape().credentialHandleOnly()).isTrue();
        assertThat(marker.resolverShellContract().requestShape().credentialValueAccepted()).isFalse();
        assertThat(marker.resolverShellContract().requestShape().endpointHandleOnly()).isTrue();
        assertThat(marker.resolverShellContract().requestShape().rawEndpointUrlAccepted()).isFalse();
        assertThat(marker.resolverShellContract().requestShape().resolverPolicyHandleRequired()).isTrue();
        assertThat(marker.resolverShellContract().requestShape().approvalMarkerRequired()).isTrue();
        assertThat(marker.resolverShellContract().requestShape().payloadMayContainSecrets()).isFalse();
        assertThat(marker.resolverShellContract().responseShape().fields())
                .containsExactly(
                        "requestId",
                        "status",
                        "code",
                        "fakeResolverOnly",
                        "resolverClientInstantiated",
                        "secretProviderInstantiated",
                        "credentialValueRead",
                        "credentialValueLoaded",
                        "rawEndpointUrlParsed",
                        "externalRequestSent",
                        "connectsManagedAudit",
                        "schemaMigrationExecuted",
                        "productionRecordWritten"
                );
        assertThat(marker.resolverShellContract().responseShape().fakeResolverResponseOnly()).isTrue();
        assertThat(marker.resolverShellContract().responseShape().resolverClientInstantiated()).isFalse();
        assertThat(marker.resolverShellContract().responseShape().secretProviderInstantiated()).isFalse();
        assertThat(marker.resolverShellContract().responseShape().credentialValueRead()).isFalse();
        assertThat(marker.resolverShellContract().responseShape().credentialValueLoaded()).isFalse();
        assertThat(marker.resolverShellContract().responseShape().rawEndpointUrlParsed()).isFalse();
        assertThat(marker.resolverShellContract().responseShape().externalRequestSent()).isFalse();
        assertThat(marker.resolverShellContract().responseShape().connectsManagedAudit()).isFalse();
        assertThat(marker.resolverShellContract().responseShape().schemaMigrationExecuted()).isFalse();
        assertThat(marker.resolverShellContract().responseShape().productionRecordWritten()).isFalse();
        assertThat(marker.resolverShellContract().failureMapping())
                .extracting(RehearsalSandboxEndpointCredentialResolverTestOnlyShellFailureMapping::sourceFailureCode)
                .containsExactly(
                        "RESOLVER_DISABLED",
                        "APPROVAL_MARKER_MISSING",
                        "CREDENTIAL_HANDLE_MISSING",
                        "CREDENTIAL_VALUE_REQUESTED",
                        "RAW_ENDPOINT_URL_REQUESTED",
                        "EXTERNAL_REQUEST_REQUESTED",
                        "SCHEMA_MIGRATION_REQUESTED"
                );
        assertThat(marker.resolverShellContract().failureMapping())
                .allMatch(mapping -> mapping.shellFailureCode().startsWith("TEST_ONLY_") && !mapping.retryable());
        assertThat(marker.resolverShellContract().guardConditions())
                .allMatch(condition -> condition.required() && condition.value());
        assertThat(marker.resolverShellContract().fakeResolverProbe().requestId())
                .isEqualTo("managed-audit-v264-test-only-resolver-shell-probe");
        assertThat(marker.resolverShellContract().fakeResolverProbe().resolverKind()).isEqualTo("fake-in-memory");
        assertThat(marker.resolverShellContract().fakeResolverProbe().acceptedByFakeResolver()).isTrue();
        assertThat(marker.resolverShellContract().fakeResolverProbe().responseCode())
                .isEqualTo("TEST_ONLY_FAKE_RESOLVER");
        assertThat(marker.resolverShellContract().fakeResolverProbe().credentialValueRead()).isFalse();
        assertThat(marker.resolverShellContract().fakeResolverProbe().credentialValueLoaded()).isFalse();
        assertThat(marker.resolverShellContract().fakeResolverProbe().externalRequestSent()).isFalse();
        assertThat(marker.resolverShellContract().fakeResolverProbe().connectsManagedAudit()).isFalse();
        assertThat(marker.resolverShellContract().fakeResolverProbe().schemaMigrationExecuted()).isFalse();
        assertThat(marker.resolverShellContract().fakeResolverProbe().productionRecordWritten()).isFalse();
        assertThat(marker.resolverShellContract().fakeResolverProbe().probeDigest()).startsWith("sha256:");
        assertThat(marker.sideEffectBoundary().testOnlyShell()).isTrue();
        assertThat(marker.sideEffectBoundary().readOnlyContract()).isTrue();
        assertThat(marker.sideEffectBoundary().fakeResolverOnly()).isTrue();
        assertThat(marker.sideEffectBoundary().handleOnlyRequest()).isTrue();
        assertThat(marker.sideEffectBoundary().credentialResolverExecutionAllowed()).isFalse();
        assertThat(marker.sideEffectBoundary().connectsManagedAudit()).isFalse();
        assertThat(marker.sideEffectBoundary().readsManagedAuditCredential()).isFalse();
        assertThat(marker.sideEffectBoundary().credentialValueRead()).isFalse();
        assertThat(marker.sideEffectBoundary().rawEndpointUrlParsed()).isFalse();
        assertThat(marker.sideEffectBoundary().externalRequestSent()).isFalse();
        assertThat(marker.sideEffectBoundary().secretProviderInstantiated()).isFalse();
        assertThat(marker.sideEffectBoundary().resolverClientInstantiated()).isFalse();
        assertThat(marker.sourceNodeV263Echoed()).isTrue();
        assertThat(marker.requestShapeEchoed()).isTrue();
        assertThat(marker.responseShapeEchoed()).isTrue();
        assertThat(marker.failureMappingEchoed()).isTrue();
        assertThat(marker.guardConditionsEchoed()).isTrue();
        assertThat(marker.fakeResolverProbeEchoed()).isTrue();
        assertThat(marker.fakeResolverOnlyEchoed()).isTrue();
        assertThat(marker.handleOnlyRequestEchoed()).isTrue();
        assertThat(marker.sideEffectBoundaryEchoed()).isTrue();
        assertThat(marker.upstreamActionsStillDisabledEchoed()).isTrue();
        assertThat(marker.readyForNodeV265SandboxEndpointCredentialResolverTestOnlyShellUpstreamEchoVerification())
                .isTrue();
        assertThat(marker.readyForManagedAuditSandboxAdapterConnection()).isFalse();
        assertThat(marker.readyForProductionAudit()).isFalse();
        assertThat(marker.readyForProductionWindow()).isFalse();
        assertThat(marker.nodeMayTreatAsProductionAuditRecord()).isFalse();
        assertThat(marker.nodeWarningCodes())
                .containsExactly("TEST_ONLY_SHELL_NOT_A_REAL_RESOLVER", "UPSTREAM_ECHO_REQUIRED_NEXT");
        assertThat(marker.nodeRecommendationCodes())
                .containsExactly("ASK_JAVA_MINI_KV_FOR_ECHO_NEXT", "KEEP_REAL_RESOLVER_OUT_OF_SCOPE");
        assertThat(marker.nextRequiredEchoVersions())
                .contains(
                        "Java v107 sandbox endpoint credential resolver test-only shell echo marker",
                        "mini-kv v116 sandbox endpoint credential resolver test-only shell non-participation receipt"
                );
        assertThat(marker.markerWarnings()).isEmpty();
        assertThat(marker.markerDigest()).startsWith("sha256:");
        assertThat(rehearsal.verificationHint().schemaFields())
                .contains("managedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarker");
        assertThat(rehearsal.verificationHint().warningDigestInputs())
                .contains(
                        "managedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarkerWarnings",
                        "sandboxEndpointCredentialResolverTestOnlyShellEchoMarkerDigest",
                        "sandboxEndpointCredentialResolverTestOnlyShellRawEndpointUrlAccepted"
                );
        assertThat(rehearsal.verificationHint().proofClaims())
                .contains(
                        "managedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarker.resolverShellContract.requestShapeFieldCount=9",
                        "managedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarker.resolverShellContract.requestShape.credentialValueAccepted=false",
                        "managedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarker.readyForManagedAuditSandboxAdapterConnection=false"
                );
        assertThat(rehearsal.verificationHint().nodeVerificationActions())
                .contains(
                        "Compare managedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarker.consumedByNodeSandboxEndpointCredentialResolverTestOnlyShellContractProfile with Node v264",
                        "Require managedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarker.readyForNodeV265SandboxEndpointCredentialResolverTestOnlyShellUpstreamEchoVerification=true before Node v265",
                        "Keep managedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarker.sideEffectBoundary.connectsManagedAudit=false"
                );

        ReleaseApprovalRehearsalResponse repeated =
                service.releaseApprovalRehearsal(paddedHeaderBackedRehearsalRequest());
        assertThat(repeated.managedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarker().markerDigest())
                .isEqualTo(marker.markerDigest());
    }


    @Test
    void releaseApprovalRehearsalAddsSandboxEndpointCredentialResolverFakeShellArchiveEchoReceipt() {
        OpsEvidenceService service = readOnlyFixtureService();

        ReleaseApprovalRehearsalResponse rehearsal =
                service.releaseApprovalRehearsal(headerBackedRehearsalRequest());

        RehearsalManagedAuditSandboxEndpointCredentialResolverFakeShellArchiveEchoReceipt receipt =
                rehearsal.managedAuditSandboxEndpointCredentialResolverFakeShellArchiveEchoReceipt();
        assertThat(receipt.receiptVersion())
                .isEqualTo(
                        "java-release-approval-rehearsal-managed-audit-sandbox-endpoint-credential-resolver-fake-shell-archive-echo-receipt.v1"
                );
        assertThat(receipt.sourceTestOnlyShellEchoMarkerSchemaVersion())
                .isEqualTo("java-release-approval-rehearsal-response-schema.v29");
        assertThat(receipt.consumedByNodeSandboxEndpointCredentialResolverFakeShellArchiveVerificationVersion())
                .isEqualTo("Node v266");
        assertThat(receipt.consumedByNodeSandboxEndpointCredentialResolverFakeShellArchiveVerificationProfile())
                .isEqualTo(
                        "managed-audit-manual-sandbox-connection-credential-resolver-fake-shell-archive-verification.v1"
                );
        assertThat(receipt.consumedByNodeSandboxEndpointCredentialResolverFakeShellArchiveVerificationEndpoint())
                .isEqualTo(
                        "/api/v1/audit/managed-audit-manual-sandbox-connection-credential-resolver-fake-shell-archive-verification"
                );
        assertThat(receipt.consumedByNodeSandboxEndpointCredentialResolverFakeShellArchiveVerificationState())
                .isEqualTo("credential-resolver-fake-shell-archive-verification-ready");
        assertThat(receipt.sourceNodeSandboxEndpointCredentialResolverTestOnlyShellContractVersion())
                .isEqualTo("Node v264");
        assertThat(receipt.sourceNodeSandboxEndpointCredentialResolverTestOnlyShellUpstreamEchoVerificationVersion())
                .isEqualTo("Node v265");
        assertThat(receipt.sourceNodeSandboxEndpointCredentialResolverTestOnlyShellUpstreamEchoVerificationState())
                .isEqualTo("sandbox-endpoint-credential-resolver-test-only-shell-upstream-echo-verification-ready");
        assertThat(receipt.nextNodeSandboxEndpointCredentialResolverFakeShellArchiveUpstreamEchoVerificationVersion())
                .isEqualTo("Node v267");
        assertThat(receipt.nodeV267MayConsume()).isTrue();
        assertThat(receipt.archiveEchoMode())
                .isEqualTo("java-v110-credential-resolver-fake-shell-archive-echo-receipt-only");
        assertThat(receipt.sourceSpan())
                .isEqualTo("Node v264 credential resolver fake shell contract + Node v265 upstream echo archive");
        assertThat(receipt.sourceNodeV266().checkCount()).isEqualTo(28);
        assertThat(receipt.sourceNodeV266().passedCheckCount()).isEqualTo(28);
        assertThat(receipt.sourceNodeV266().archiveFileCount()).isEqualTo(9);
        assertThat(receipt.sourceNodeV266().requiredSnippetCount()).isEqualTo(24);
        assertThat(receipt.sourceNodeV266().matchedSnippetCount()).isEqualTo(24);
        assertThat(receipt.sourceNodeV266().productionBlockerCount()).isZero();
        assertThat(receipt.sourceNodeV266().warningCount()).isEqualTo(1);
        assertThat(receipt.sourceNodeV266().recommendationCount()).isEqualTo(2);
        assertThat(receipt.sourceNodeV266().sourceNodeV264Ready()).isTrue();
        assertThat(receipt.sourceNodeV266().sourceNodeV265Ready()).isTrue();
        assertThat(receipt.sourceNodeV266().sourceNodeV265ConsumesUpstreamEchoes()).isTrue();
        assertThat(receipt.sourceNodeV266().javaV107EchoReady()).isTrue();
        assertThat(receipt.sourceNodeV266().miniKvV116NonParticipationReady()).isTrue();
        assertThat(receipt.sourceNodeV266().javaV109OptimizationContextReady()).isTrue();
        assertThat(receipt.sourceNodeV266().archiveFilesPresent()).isTrue();
        assertThat(receipt.sourceNodeV266().archiveFilesNonEmpty()).isTrue();
        assertThat(receipt.sourceNodeV266().archiveSnippetsMatched()).isTrue();
        assertThat(receipt.sourceNodeV266().routeResponsesVerified()).isTrue();
        assertThat(receipt.sourceNodeV266().noArchiveVerificationFakeShellRerun()).isTrue();
        assertThat(receipt.sourceNodeV266().readOnlyArchiveVerification()).isTrue();
        assertThat(receipt.sourceNodeV266().archiveVerificationReadsFilesOnly()).isTrue();
        assertThat(receipt.sourceNodeV266().archiveVerificationRerunsFakeShellBehavior()).isFalse();
        assertThat(receipt.sourceNodeV266().upstreamActionsStillDisabled()).isTrue();
        assertThat(receipt.sourceNodeV266().credentialValueRead()).isFalse();
        assertThat(receipt.sourceNodeV266().rawEndpointUrlParsed()).isFalse();
        assertThat(receipt.sourceNodeV266().externalRequestSent()).isFalse();
        assertThat(receipt.sourceNodeV266().secretProviderInstantiated()).isFalse();
        assertThat(receipt.sourceNodeV266().resolverClientInstantiated()).isFalse();
        assertThat(receipt.sourceNodeV266().connectsManagedAudit()).isFalse();
        assertThat(receipt.sourceNodeV266().schemaMigrationExecuted()).isFalse();
        assertThat(receipt.sourceNodeV266().automaticUpstreamStart()).isFalse();
        assertThat(receipt.archiveEvidence().archiveRoots()).containsExactly("c/264/", "c/265/");
        assertThat(receipt.archiveEvidence().sourceVersions()).containsExactly("Node v264", "Node v265");
        assertThat(receipt.archiveEvidence().archiveFileCount()).isEqualTo(9);
        assertThat(receipt.archiveEvidence().requiredSnippetCount()).isEqualTo(24);
        assertThat(receipt.archiveEvidence().matchedSnippetCount()).isEqualTo(24);
        assertThat(receipt.archiveEvidence().files())
                .extracting(file -> file.id())
                .containsExactly(
                        "v264-html-archive",
                        "v264-screenshot",
                        "v264-explanation",
                        "v264-code-walkthrough",
                        "v265-html-archive",
                        "v265-screenshot",
                        "v265-explanation",
                        "v265-code-walkthrough",
                        "active-plan"
                );
        assertThat(receipt.archiveEvidence().snippets())
                .extracting(snippet -> snippet.id())
                .contains("plan-v266", "v265-walkthrough-mini-kv-v116");
        assertThat(receipt.archiveVerification().archiveVerificationReadsFilesOnly()).isTrue();
        assertThat(receipt.archiveVerification().archiveVerificationRerunsFakeShellBehavior()).isFalse();
        assertThat(receipt.archiveVerification().upstreamActionsEnabled()).isFalse();
        assertThat(receipt.archiveVerification().productionAuditAllowed()).isFalse();
        assertThat(receipt.archiveVerification().routeResponsesVerified()).isTrue();
        assertThat(receipt.archiveChecks().sourceNodeV265ConsumesUpstreamEchoes()).isTrue();
        assertThat(receipt.archiveChecks().archiveFilesPresent()).isTrue();
        assertThat(receipt.archiveChecks().archiveSnippetsMatched()).isTrue();
        assertThat(receipt.archiveChecks().noArchiveVerificationFakeShellRerun()).isTrue();
        assertThat(receipt.sideEffectBoundary().readOnlyArchiveVerification()).isTrue();
        assertThat(receipt.sideEffectBoundary().archiveVerificationReadsFilesOnly()).isTrue();
        assertThat(receipt.sideEffectBoundary().archiveVerificationRerunsFakeShellBehavior()).isFalse();
        assertThat(receipt.sideEffectBoundary().credentialValueRead()).isFalse();
        assertThat(receipt.sideEffectBoundary().rawEndpointUrlParsed()).isFalse();
        assertThat(receipt.sideEffectBoundary().externalRequestSent()).isFalse();
        assertThat(receipt.sideEffectBoundary().secretProviderInstantiated()).isFalse();
        assertThat(receipt.sideEffectBoundary().resolverClientInstantiated()).isFalse();
        assertThat(receipt.sideEffectBoundary().connectsManagedAudit()).isFalse();
        assertThat(receipt.sideEffectBoundary().approvalLedgerWritten()).isFalse();
        assertThat(receipt.sideEffectBoundary().managedAuditStoreWritten()).isFalse();
        assertThat(receipt.sideEffectBoundary().sqlExecuted()).isFalse();
        assertThat(receipt.sideEffectBoundary().schemaMigrationExecuted()).isFalse();
        assertThat(receipt.sideEffectBoundary().automaticUpstreamStart()).isFalse();
        assertThat(receipt.sourceNodeV266Echoed()).isTrue();
        assertThat(receipt.sourceNodeV264ContractEchoed()).isTrue();
        assertThat(receipt.sourceNodeV265UpstreamEchoed()).isTrue();
        assertThat(receipt.archiveEvidenceEchoed()).isTrue();
        assertThat(receipt.archiveSnippetsEchoed()).isTrue();
        assertThat(receipt.routeResponsesEchoed()).isTrue();
        assertThat(receipt.readOnlyArchiveBoundaryEchoed()).isTrue();
        assertThat(receipt.noFakeShellRerunEchoed()).isTrue();
        assertThat(receipt.sideEffectBoundaryEchoed()).isTrue();
        assertThat(receipt.upstreamActionsStillDisabledEchoed()).isTrue();
        assertThat(receipt.readyForNodeV267SandboxEndpointCredentialResolverFakeShellArchiveUpstreamEchoVerification())
                .isTrue();
        assertThat(receipt.readyForManagedAuditSandboxAdapterConnection()).isFalse();
        assertThat(receipt.readyForProductionAudit()).isFalse();
        assertThat(receipt.readyForProductionWindow()).isFalse();
        assertThat(receipt.nodeMayTreatAsProductionAuditRecord()).isFalse();
        assertThat(receipt.nodeWarningCodes()).containsExactly("ARCHIVE_VERIFICATION_ONLY");
        assertThat(receipt.nodeRecommendationCodes())
                .containsExactly("WRITE_POST_V266_PLAN", "KEEP_REAL_RESOLVER_OUT_OF_SCOPE");
        assertThat(receipt.nextRequiredEchoVersions())
                .contains(
                        "Java v110 credential resolver fake-shell archive echo receipt",
                        "mini-kv v117 credential resolver fake-shell archive non-participation receipt"
                );
        assertThat(receipt.receiptWarnings()).isEmpty();
        assertThat(receipt.receiptDigest()).startsWith("sha256:");
        assertThat(rehearsal.verificationHint().responseSchemaVersion())
                .isEqualTo("java-release-approval-rehearsal-response-schema.v39");
        assertThat(rehearsal.verificationHint().schemaFields())
                .contains("managedAuditSandboxEndpointCredentialResolverFakeShellArchiveEchoReceipt");
        assertThat(rehearsal.verificationHint().warningDigestInputs())
                .contains(
                        "managedAuditSandboxEndpointCredentialResolverFakeShellArchiveEchoReceiptWarnings",
                        "sandboxEndpointCredentialResolverFakeShellArchiveEchoReceiptDigest",
                        "sandboxEndpointCredentialResolverFakeShellArchiveExternalRequestSent"
                );
        assertThat(rehearsal.verificationHint().proofClaims())
                .contains(
                        "managedAuditSandboxEndpointCredentialResolverFakeShellArchiveEchoReceipt.archiveEvidence.archiveFileCount=9",
                        "managedAuditSandboxEndpointCredentialResolverFakeShellArchiveEchoReceipt.archiveVerification.archiveVerificationRerunsFakeShellBehavior=false",
                        "managedAuditSandboxEndpointCredentialResolverFakeShellArchiveEchoReceipt.readyForManagedAuditSandboxAdapterConnection=false"
                );
        assertThat(rehearsal.verificationHint().nodeVerificationActions())
                .contains(
                        "Compare managedAuditSandboxEndpointCredentialResolverFakeShellArchiveEchoReceipt.consumedByNodeSandboxEndpointCredentialResolverFakeShellArchiveVerificationProfile with Node v266",
                        "Require managedAuditSandboxEndpointCredentialResolverFakeShellArchiveEchoReceipt.readyForNodeV267SandboxEndpointCredentialResolverFakeShellArchiveUpstreamEchoVerification=true before Node v267",
                        "Keep managedAuditSandboxEndpointCredentialResolverFakeShellArchiveEchoReceipt.sideEffectBoundary.connectsManagedAudit=false"
                );

        ReleaseApprovalRehearsalResponse repeated =
                service.releaseApprovalRehearsal(paddedHeaderBackedRehearsalRequest());
        assertThat(repeated.managedAuditSandboxEndpointCredentialResolverFakeShellArchiveEchoReceipt().receiptDigest())
                .isEqualTo(receipt.receiptDigest());
    }

}
