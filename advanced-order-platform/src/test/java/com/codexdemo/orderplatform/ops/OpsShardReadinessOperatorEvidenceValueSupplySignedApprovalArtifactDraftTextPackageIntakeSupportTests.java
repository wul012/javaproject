package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeSupportTests {

    @Test
    void buildsDraftTextPackageIntakeResponseWithoutAcceptingPackageMaterial() {
        var response = OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeSupport
                .response(
                        "Java v910",
                        "/ops/shard-readiness/draft-text-package-intake-sample",
                        "sample.signed-approval-artifact-draft-text-package-intake.v1",
                        List.of(OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeSupport
                                .field(
                                        "DRAFT_TEXT_PACKAGE_INTAKE_REQUEST_MANIFEST_ID_FIELD",
                                        "Node v1212-v1215",
                                        "requestManifestId",
                                        "bind request manifest",
                                        "expected only",
                                        "DRAFT_TEXT_PACKAGE_INTAKE_REQUEST_MANIFEST_ID_GUARD",
                                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightCatalogService
                                                .ENDPOINT)),
                        List.of(OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeSupport
                                .guard(
                                        "DRAFT_TEXT_PACKAGE_INTAKE_REQUEST_MANIFEST_ID_GUARD",
                                        "identity",
                                        "reject missing request manifest id",
                                        "REJECT_DRAFT_TEXT_PACKAGE_INTAKE_REQUEST_MANIFEST_ID_MISSING",
                                        "fail-closed")),
                        List.of(OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeSupport
                                .gate(
                                        "DRAFT_TEXT_PACKAGE_INTAKE_GATE_01",
                                        "intake",
                                        "intake remains expected-fields-only",
                                        "fail-closed")),
                        List.of("sample-draft-text-package-intake-check")
                );

        assertThat(response.project()).isEqualTo("advanced-order-platform");
        assertThat(response.version()).isEqualTo("Java v910");
        assertThat(response.sourcePlan()).isEqualTo("Node v1236");
        assertThat(response.sourceNodeInstructionPreflightVersion()).isEqualTo("Node v1211");
        assertThat(response.sourceJavaInstructionPreflightVersion()).isEqualTo("Java v909");
        assertThat(response.draftTextPackageIntakeState()).isEqualTo("expected-fields-only");
        assertThat(response.draftTextArtifactState()).isEqualTo("not-accepted");
        assertThat(response.signedDraftState()).isEqualTo("not-accepted");
        assertThat(response.signatureEnvelopeState()).isEqualTo("not-accepted");
        assertThat(response.readyForDraftTextPackageIntake()).isTrue();
        assertThat(response.readyForDraftTextPackageReview()).isFalse();
        assertThat(response.readyForSignedDraftText()).isFalse();
        assertThat(response.readyForDetachedSignature()).isFalse();
        assertThat(response.readyForApprovalGrant()).isFalse();
        assertThat(response.readyForOperatorValueSubmission()).isFalse();
        assertThat(response.readyForEvidenceImport()).isFalse();
        assertThat(response.readyForRuntimePayload()).isFalse();
        assertThat(response.siblingMutationAllowed()).isFalse();
        assertThat(response.fieldCount()).isEqualTo(1);
        assertThat(response.passedFieldCount()).isEqualTo(1);
        assertThat(response.guardCount()).isEqualTo(1);
        assertThat(response.passedGuardCount()).isEqualTo(1);
        assertThat(response.gateCount()).isEqualTo(1);
        assertThat(response.checks()).contains(
                "signed-approval-artifact-draft-text-package-intake-no-draft-text-acceptance",
                "signed-approval-artifact-draft-text-package-intake-no-detached-signature-acceptance",
                "signed-approval-artifact-draft-text-package-intake-no-approval-grant",
                "sample-draft-text-package-intake-check"
        );
        assertThat(response.status()).isEqualTo("passed");
    }
}
