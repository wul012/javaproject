package com.codexdemo.orderplatform.notification;

import static com.codexdemo.orderplatform.notification.FailedEventCommandSupport.firstNonBlank;
import static com.codexdemo.orderplatform.notification.FailedEventCommandSupport.truncate;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import org.springframework.amqp.core.Message;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Component
final class FailedEventRecorder {

  private final FailedEventMessageRepository failedEventMessageRepository;

  FailedEventRecorder(FailedEventMessageRepository failedEventMessageRepository) {
    this.failedEventMessageRepository = failedEventMessageRepository;
  }

  FailedEventMessage record(Message message, String deadLetterQueue) {
    String payload = new String(message.getBody(), StandardCharsets.UTF_8);
    String messageId = resolveMessageId(message, payload);
    return failedEventMessageRepository
        .findByMessageId(messageId)
        .orElseGet(() -> saveFailedMessage(message, deadLetterQueue, payload, messageId));
  }

  private FailedEventMessage saveFailedMessage(
      Message message, String deadLetterQueue, String payload, String messageId) {
    try {
      return failedEventMessageRepository.save(
          FailedEventMessage.record(
              messageId,
              header(message, "eventId"),
              header(message, "eventType"),
              header(message, "aggregateType"),
              header(message, "aggregateId"),
              header(message, "x-first-death-queue"),
              deadLetterQueue,
              truncate(
                  firstNonBlank(header(message, "x-first-death-reason"), "dead-lettered"), 500),
              payload));
    } catch (DataIntegrityViolationException ex) {
      return failedEventMessageRepository.findByMessageId(messageId).orElseThrow(() -> ex);
    }
  }

  private String resolveMessageId(Message message, String payload) {
    return firstNonBlank(
        message.getMessageProperties().getMessageId(),
        header(message, "eventId"),
        "sha256-" + sha256(payload + message.getMessageProperties().getHeaders()));
  }

  private String header(Message message, String name) {
    Object value = message.getMessageProperties().getHeaders().get(name);
    return value == null ? null : value.toString();
  }

  private String sha256(String value) {
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      return HexFormat.of().formatHex(digest.digest(value.getBytes(StandardCharsets.UTF_8)));
    } catch (NoSuchAlgorithmException ex) {
      throw new IllegalStateException("SHA-256 is not available", ex);
    }
  }
}
