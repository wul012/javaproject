package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightDigestSignatureLaneCatalog {

    private OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightDigestSignatureLaneCatalog() {
    }

    static List<OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightResponse
            .ComparisonLane> digestSignatureLanes() {
        return List.of(
                lane("comparison-lane-digest-pin-set", "v1291",
                        "compare digest pin identifiers against submitted digest lane",
                        "Are the digest pins present without hashing signed draft text?",
                        "reject-package-digest-pin-missing"),
                lane("comparison-lane-digest-algorithm", "v1292",
                        "compare digest algorithm declaration against expected control",
                        "Is the algorithm declaration comparable and non-executable?",
                        "reject-package-digest-algorithm-mismatch"),
                lane("comparison-lane-digest-source-handle", "v1293",
                        "compare digest source handle against submission preflight evidence",
                        "Does the source handle remain a reference instead of imported content?",
                        "reject-package-digest-source-unbound"),
                lane("comparison-lane-digest-recheck-control", "v1294",
                        "compare digest recheck controls against fail-closed acceptance rule",
                        "Would missing digest recheck material block acceptance?",
                        "reject-package-digest-recheck-missing"),
                lane("comparison-lane-detached-signature-metadata", "v1295",
                        "compare detached signature metadata against expected envelope slot",
                        "Is metadata present without parsing the detached signature payload?",
                        "reject-package-detached-signature-metadata-missing"),
                lane("comparison-lane-detached-signature-envelope", "v1296",
                        "compare detached signature envelope labels against submission controls",
                        "Does the package preserve envelope labels as opaque material?",
                        "reject-package-detached-signature-envelope-uncomparable"),
                lane("comparison-lane-detached-signature-absence-lock", "v1297",
                        "compare signature parse lock against closeout guardrails",
                        "Does the package keep detached signature parsing locked?",
                        "reject-package-detached-signature-lock-open")
        );
    }

    private static OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightResponse
            .ComparisonLane lane(
                    String code,
                    String versionRange,
                    String comparisonLane,
                    String comparisonQuestion,
                    String acceptanceControl
    ) {
        return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightSupport
                .lane(code, versionRange, comparisonLane, comparisonQuestion, acceptanceControl,
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightDigestSignatureService
                                .ENDPOINT);
    }
}

