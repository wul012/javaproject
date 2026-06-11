package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveSourceCatalog {

    private OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveSourceCatalog() {
    }

    static List<OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveResponse.SourceReceipt>
            receipts(ReleaseApprovalRehearsalResponse rehearsal) {
        var receipt = rehearsal.managedAuditSandboxEndpointCredentialResolverImplementationPlanEchoReceipt();
        var review = receipt.implementationPlanReview();
        return List.of(new OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveResponse
                .SourceReceipt(
                "managedAuditSandboxEndpointCredentialResolverImplementationPlanEchoReceipt",
                receipt.receiptVersion(),
                receipt.receiptDigest(),
                receipt.consumedByNodeCredentialResolverImplementationPlanDraftVersion(),
                receipt.consumedByNodeCredentialResolverImplementationPlanDraftProfile(),
                receipt.consumedByNodeCredentialResolverImplementationPlanDraftState(),
                receipt.sourceSpan(),
                review.nextJavaEchoVersion(),
                review.nextMiniKvReceiptVersion(),
                review.nextNodeVerificationVersion(),
                review.fakeHarnessDeferredUntil(),
                receipt.readyForNodeV284CredentialResolverImplementationPlanEchoVerification(),
                receipt.readyForJavaV121MiniKvV126Echo(),
                receipt.readyForTestOnlyFakeHarnessPrecheck(),
                receipt.readyForManagedAuditResolverImplementation()
        ));
    }
}
