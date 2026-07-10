package com.codexdemo.orderplatform.notification;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class FailedEventQueryArchitectureTests {

  private static final Path SOURCE_ROOT =
      Path.of("src", "main", "java", "com", "codexdemo", "orderplatform", "notification");

  @Test
  void publicFacadeDelegatesQueriesWithoutOwningJpaSearchMechanics() throws IOException {
    String facade = read("FailedEventMessageService.java");

    assertThat(facade)
        .contains(
            "failedEventQueryService.searchFailedMessages(criteria)",
            "failedEventQueryService.searchReplayAttempts(criteria)",
            "failedEventQueryService.searchManagementHistory(criteria)",
            "failedEventQueryService.searchReplayApprovalHistory(criteria)")
        .doesNotContain(
            "Specification<",
            "FAILED_MESSAGE_SORT_FIELDS",
            "normalizePageRequest(",
            "normalizeExportPageRequest(");
  }

  @Test
  void queryMechanicsStayInNarrowPackagePrivateCollaborators() throws IOException {
    String queryService = read("FailedEventQueryService.java");
    String specifications = read("FailedEventSearchSpecifications.java");
    String pageSupport = read("FailedEventSearchPageSupport.java");

    assertThat(queryService)
        .contains(
            "final class FailedEventQueryService",
            "FAILED_MESSAGE_SORT_FIELDS",
            "FailedEventSearchPageSupport.NormalizedPageRequest")
        .doesNotContain("public final class FailedEventQueryService");
    assertThat(specifications)
        .contains(
            "failedMessagesMatching",
            "replayAttemptsMatching",
            "managementHistoryMatching",
            "replayApprovalHistoryMatching")
        .doesNotContain("public final class FailedEventSearchSpecifications");
    assertThat(pageSupport)
        .contains("DEFAULT_EXPORT_LIMIT = 1000", "MAX_EXPORT_LIMIT = 5000", "normalizeSort")
        .doesNotContain("public final class FailedEventSearchPageSupport");
  }

  private static String read(String fileName) throws IOException {
    return Files.readString(SOURCE_ROOT.resolve(fileName), StandardCharsets.UTF_8);
  }
}
