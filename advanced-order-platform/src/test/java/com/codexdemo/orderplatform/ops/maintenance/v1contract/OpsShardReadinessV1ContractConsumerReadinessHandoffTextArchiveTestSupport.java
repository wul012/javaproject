package com.codexdemo.orderplatform.ops.maintenance.v1contract;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

final class OpsShardReadinessV1ContractConsumerReadinessHandoffTextArchiveTestSupport {

  private OpsShardReadinessV1ContractConsumerReadinessHandoffTextArchiveTestSupport() {}

  static String readme() throws IOException {
    return Files.readString(Path.of("e", "README.md"), StandardCharsets.UTF_8);
  }

  static List<String> readmeLines() throws IOException {
    return Files.readAllLines(Path.of("e", "README.md"), StandardCharsets.UTF_8);
  }

  static String readmePrefix(
      OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.Receipt
          receipt) {
    return "- `" + receipt.version() + "/`:";
  }

  static String normalizeScopeText(String value) {
    return value.toLowerCase().replace('-', ' ').replace('/', ' ');
  }

  static String scopeSlug(
      OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.Receipt
          receipt) {
    return receipt.scope().replace(' ', '-').replace('/', '-').toLowerCase();
  }

  static List<String> walkthroughFileNames() throws IOException {
    try (Stream<Path> files = Files.walk(walkthroughRoot())) {
      return files.filter(Files::isRegularFile).map(path -> path.getFileName().toString()).toList();
    }
  }

  static List<String> lowercaseWalkthroughFileNames() throws IOException {
    return walkthroughFileNames().stream().map(String::toLowerCase).toList();
  }

  private static Path walkthroughRoot() throws IOException {
    try (Stream<Path> roots = Files.list(Path.of(""))) {
      return roots
          .filter(Files::isDirectory)
          .filter(
              OpsShardReadinessV1ContractConsumerReadinessHandoffTextArchiveTestSupport
                  ::containsWalkthroughFile)
          .findFirst()
          .orElseThrow(() -> new AssertionError("Missing code walkthrough archive directory"));
    }
  }

  private static boolean containsWalkthroughFile(Path directory) {
    try (Stream<Path> files = Files.walk(directory, 3)) {
      return files
          .filter(Files::isRegularFile)
          .map(path -> path.getFileName().toString())
          .anyMatch(fileName -> fileName.contains("version-226-"));
    } catch (IOException ex) {
      throw new AssertionError("Unable to inspect walkthrough archive tree " + directory, ex);
    }
  }
}
