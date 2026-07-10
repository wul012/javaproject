package com.codexdemo.orderplatform.notification;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class FailedEventCommandArchitectureTests {

  private static final Path SOURCE_ROOT =
      Path.of("src", "main", "java", "com", "codexdemo", "orderplatform", "notification");

  @Test
  void publicFacadeOwnsTransactionsButNotCommandInfrastructure() throws IOException {
    String facade = read("FailedEventMessageService.java");

    assertThat(facade)
        .contains(
            "failedEventRecorder.record(message, deadLetterQueue)",
            "failedEventManagementService.markManagementStatus(request, operatorContext)",
            "failedEventReplayApprovalService.requestReplayApproval(id, request, operatorContext)",
            "failedEventReplayApprovalService.reviewReplayApproval(id, request, operatorContext)",
            "failedEventReplayService.replay(id, request, operatorContext)")
        .doesNotContain(
            "Repository",
            "RabbitTemplate",
            "OutboxRabbitMqProperties",
            "MessageDigest",
            "DataIntegrityViolationException");
    assertThat(lineCount("FailedEventMessageService.java")).isLessThanOrEqualTo(250);
  }

  @Test
  void commandCollaboratorsRemainNarrowPackagePrivateComponents() throws IOException {
    assertPackagePrivateComponent("FailedEventRecorder.java", "final class FailedEventRecorder");
    assertPackagePrivateComponent(
        "FailedEventManagementService.java", "final class FailedEventManagementService");
    assertPackagePrivateComponent(
        "FailedEventReplayApprovalService.java", "final class FailedEventReplayApprovalService");
    assertPackagePrivateComponent(
        "FailedEventReplayService.java", "final class FailedEventReplayService");
  }

  @Test
  void replayMessageHeaderAndAttemptPersistenceOrderStayExplicit() throws IOException {
    String replayService = read("FailedEventReplayService.java");

    assertThat(replayService)
        .containsSubsequence(
            "setMessageId(event.eventId())",
            "setHeader(\"eventId\", event.eventId())",
            "setHeader(\"aggregateType\", event.aggregateType())",
            "setHeader(\"aggregateId\", event.aggregateId())",
            "setHeader(\"eventType\", event.eventType())",
            "setHeader(\"replayedFromFailedEventId\", failedMessage.getId())",
            "setHeader(\"replayedFromMessageId\", failedMessage.getMessageId())")
        .contains(
            "FailedEventReplayAttemptStatus.SKIPPED_ALREADY_REPLAYED",
            "FailedEventReplayAttemptStatus.SUCCEEDED",
            "FailedEventReplayAttemptStatus.FAILED",
            "failedEventReplayAttemptRepository.save(");
  }

  private static void assertPackagePrivateComponent(String fileName, String classDeclaration)
      throws IOException {
    String source = read(fileName);
    assertThat(source)
        .contains("@Component", classDeclaration)
        .doesNotContain("public " + classDeclaration);
  }

  private static String read(String fileName) throws IOException {
    return Files.readString(SOURCE_ROOT.resolve(fileName), StandardCharsets.UTF_8);
  }

  private static long lineCount(String fileName) throws IOException {
    try (var lines = Files.lines(SOURCE_ROOT.resolve(fileName), StandardCharsets.UTF_8)) {
      return lines.count();
    }
  }
}
