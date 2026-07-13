package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.codexdemo.orderplatform.notification.FailedEventSummaryService;
import com.codexdemo.orderplatform.ops.maintenance.credentialresolver.OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveResponse;
import com.codexdemo.orderplatform.ops.maintenance.credentialresolver.OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveService;
import com.codexdemo.orderplatform.ops.maintenance.credentialresolver.OpsShardReadinessCredentialResolverRoutePaths;
import com.codexdemo.orderplatform.order.IdempotencyStore;
import com.codexdemo.orderplatform.outbox.OutboxRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveServiceControllerTests {

  private static OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveService
      service() {
    return new OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveService(
        OpsEvidenceServiceTestFixtures.readOnlyFixtureService(
            Mockito.mock(FailedEventSummaryService.class),
            Mockito.mock(OutboxRepository.class),
            Mockito.mock(IdempotencyStore.class)));
  }

  private static OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveResponse
      archive() {
    return service().archive();
  }

  @Test
  void routeAndControllerExposeArchive() {
    assertThat(
            OpsShardReadinessCredentialResolverRoutePaths
                .CREDENTIAL_RESOLVER_DISABLED_FAKE_HARNESS_EVIDENCE_ARCHIVE)
        .isEqualTo(
            OpsShardReadinessCredentialResolverRoutePaths
                .CREDENTIAL_RESOLVER_DISABLED_FAKE_HARNESS_EVIDENCE_ARCHIVE);

    var response =
        new OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveController(
                service())
            .archive();

    assertThat(response.endpoint())
        .isEqualTo(
            "/api/v1/ops/shard-readiness/credential-resolver-disabled-fake-harness-evidence-archive");
    assertThat(response.checks())
        .contains(
            "credential-resolver-disabled-fake-harness-evidence-archive-source-plan-Node v1967",
            "credential-resolver-disabled-fake-harness-evidence-archive-fake-harness-deferred-until-Node v285",
            "credential-resolver-disabled-fake-harness-evidence-archive-ready-for-retention");
  }

  @Test
  void runtimeGuardsVerificationAndMarkdownStayClosed() {
    var response = archive();

    assertThat(response.runtimeGuards()).allSatisfy(guard -> assertThat(guard.passed()).isTrue());
    assertThat(response.verificationGates()).allSatisfy(gate -> assertThat(gate.passed()).isTrue());
    assertThat(response.handoffNotes()).allSatisfy(note -> assertThat(note.ready()).isTrue());
    assertThat(response.markdownSections())
        .extracting(
            OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveResponse
                    .MarkdownSection
                ::heading)
        .containsExactly(
            "Source Receipt",
            "Java Requirements",
            "mini-kv Requirements",
            "Fake Harness Boundary",
            "Runtime Guards",
            "Verification Gates");
  }

  @Test
  void archiveListsAreImmutable() {
    var response = archive();

    assertThatThrownBy(() -> response.checks().add("mutate"))
        .isInstanceOf(UnsupportedOperationException.class);
    assertThatThrownBy(() -> response.javaRequirements().clear())
        .isInstanceOf(UnsupportedOperationException.class);
    assertThatThrownBy(() -> response.markdownSections().get(0).lines().add("mutate"))
        .isInstanceOf(UnsupportedOperationException.class);
  }
}
