package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.OpsShardReadinessReleaseAcceptanceRoutePaths;
import org.junit.jupiter.api.Test;

class OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffControllerMarkdownTests {

  @Test
  void controllerRouteExposesArchiveVerificationHandoff() {
    assertThat(OpsShardReadinessRoutePaths.RELEASE_ACCEPTANCE_ARCHIVE_VERIFICATION_HANDOFF_REGISTRY)
        .isEqualTo("/release-acceptance-archive-verification-handoff-registry");
    assertThat(
            OpsShardReadinessReleaseAcceptanceRoutePaths
                .RELEASE_ACCEPTANCE_ARCHIVE_VERIFICATION_HANDOFF_REGISTRY)
        .isEqualTo(
            OpsShardReadinessRoutePaths.RELEASE_ACCEPTANCE_ARCHIVE_VERIFICATION_HANDOFF_REGISTRY);

    var response =
        new OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffController(
                OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffTestSupport.service())
            .registry();

    assertThat(response.version()).isEqualTo("Java v1547");
    assertThat(response.sourceArchiveVersion()).isEqualTo("Java v1522");
    assertThat(response.endpoint())
        .isEqualTo(
            "/api/v1/ops/shard-readiness/release-acceptance-archive-verification-handoff-registry");
    assertThat(response.executionAllowed()).isFalse();
  }

  @Test
  void rendersStableArchiveVerificationHandoffMarkdownAndChecks() {
    var response =
        OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffTestSupport.registry();

    assertThat(response.markdownSectionCount()).isEqualTo(10);
    assertThat(response.markdownSections())
        .extracting(
            OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.MarkdownSection
                ::heading)
        .containsExactly(
            "Source Archive",
            "Verification Requirements",
            "Artifact Cross Checks",
            "Route Handoffs",
            "Operator Instructions",
            "CI Proofs",
            "Boundary Guards",
            "Retention Guards",
            "Closeout Handoffs",
            "Scorecard");
    assertThat(response.checks()).hasSize(33);
    assertThat(response.checks())
        .contains(
            "release-acceptance-archive-verification-handoff-source-archive-version-Java v1522",
            "release-acceptance-archive-verification-handoff-requirement-count-8",
            "release-acceptance-archive-verification-handoff-ci-proof-count-5",
            "release-acceptance-archive-verification-handoff-consumes-archive-registry",
            "release-acceptance-archive-verification-handoff-no-runtime-execution",
            "release-acceptance-archive-verification-handoff-no-deployment-rollback");
  }
}
