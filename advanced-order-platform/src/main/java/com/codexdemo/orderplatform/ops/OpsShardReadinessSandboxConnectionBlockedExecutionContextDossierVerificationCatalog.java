package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierVerificationCatalog {

    private OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierVerificationCatalog() {
    }

    static List<OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse.VerificationGate> gates(
            List<OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse.SourceReceipt>
                    sourceReceipts,
            List<OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse.ContextField>
                    contextFields,
            List<OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse.PreconditionEvidence>
                    preconditionEvidence,
            List<OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse.BoundarySnapshot>
                    boundarySnapshots,
            List<OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse.ExecutionGuard>
                    executionGuards,
            List<OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse.WarningEcho>
                    warningEchoes,
            List<OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse.DownstreamIntakeGate>
                    downstreamIntakeGates
    ) {
        return List.of(
                gate("node-v1982-source-plan-pinned",
                        OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierSupport.SOURCE_PLAN,
                        true),
                gate("node-v234-source-receipt-pinned",
                        "consumedByNodeBlockedExecutionRehearsalVersion=Node v234",
                        sourceReceipts.stream().allMatch(source -> "Node v234".equals(source.consumedNodeVersion()))),
                gate("java-v90-context-fields-present",
                        "requestId/operatorIdentity/auditCorrelationId archived",
                        contextFields.size()
                                == OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierSupport
                                .EXPECTED_CONTEXT_FIELD_COUNT),
                gate("java-v91-precondition-evidence-present",
                        "requiredPreconditionEvidence.size=6",
                        preconditionEvidence.size()
                                == OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierSupport
                                .EXPECTED_PRECONDITION_EVIDENCE_COUNT),
                gate("all-precondition-boundaries-closed",
                        "owner/credential/schema/rollback/execution closed",
                        boundarySnapshots.stream().allMatch(snapshot -> snapshot.required() && snapshot.closed())),
                gate("execution-guards-passed",
                        "no write, connection, sql, deployment, rollback, or startup",
                        executionGuards.stream().allMatch(
                                OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse
                                        .ExecutionGuard::passed)),
                gate("warnings-archived",
                        "request context and precondition warnings remain visible",
                        warningEchoes.size()
                                == OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierSupport
                                .EXPECTED_WARNING_ECHO_COUNT
                                && warningEchoes.stream().allMatch(
                                OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse
                                        .WarningEcho::archived)),
                gate("downstream-intake-gates-ready",
                        "Node v235 inputs are present without enabling execution",
                        downstreamIntakeGates.stream().allMatch(
                                OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse
                                        .DownstreamIntakeGate::ready)),
                gate("production-audit-still-blocked",
                        "nodeMayTreatAsProductionAuditRecord=false",
                        sourceReceipts.stream().allMatch(source -> !source.nodeMayTreatAsProductionAuditRecord())),
                gate("managed-audit-adapter-still-blocked",
                        "readyForManagedAuditSandboxAdapterConnection=false",
                        sourceReceipts.stream().allMatch(source -> !source.readyForManagedAuditSandboxAdapterConnection()))
        );
    }

    private static OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse.VerificationGate gate(
            String name,
            String evidence,
            boolean passed
    ) {
        return new OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse
                .VerificationGate(name, evidence, passed);
    }
}
