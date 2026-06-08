package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessComparedPackageReviewIdentityDigestSlotCatalog {

    private OpsShardReadinessComparedPackageReviewIdentityDigestSlotCatalog() {
    }

    static List<OpsShardReadinessComparedPackageReviewResponse.ReviewSlot> identityDigestSlots() {
        return List.of(
                OpsShardReadinessComparedPackageReviewSupport.slot(
                        "identity-binding-review",
                        "Java v1024",
                        "identity digest",
                        "Identity binding is expected as a review handle, not a principal switch.",
                        "Does the packet bind identity without changing runtime identity?",
                        "reject-missing-identity-binding-review",
                        OpsShardReadinessComparedPackageEvidenceIntakeEndpointRefs.IDENTITY_DIGEST_SIGNATURE
                ),
                OpsShardReadinessComparedPackageReviewSupport.slot(
                        "digest-match-summary-review",
                        "Java v1024",
                        "identity digest",
                        "Digest match summary must be reviewable before any material is accepted.",
                        "Is the digest summary present without storing the compared package body?",
                        "reject-missing-digest-match-summary-review",
                        OpsShardReadinessComparedPackageEvidenceIntakeEndpointRefs.IDENTITY_DIGEST_SIGNATURE
                ),
                OpsShardReadinessComparedPackageReviewSupport.slot(
                        "detached-signature-observation-review",
                        "Java v1024",
                        "identity digest",
                        "Detached signature observation remains unparsed and operator-supplied.",
                        "Is the detached signature mentioned without verification side effects?",
                        "reject-missing-detached-signature-observation-review",
                        OpsShardReadinessComparedPackageEvidenceIntakeEndpointRefs.IDENTITY_DIGEST_SIGNATURE
                )
        );
    }
}
