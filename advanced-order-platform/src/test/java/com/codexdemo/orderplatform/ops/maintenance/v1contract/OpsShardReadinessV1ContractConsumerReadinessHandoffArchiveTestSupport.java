package com.codexdemo.orderplatform.ops.maintenance.v1contract;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

final class OpsShardReadinessV1ContractConsumerReadinessHandoffArchiveTestSupport {

  private static final Path ARCHIVE_ROOT = Path.of("e");
  private static final Path WORKING_ROOT = Path.of("").toAbsolutePath();

  private OpsShardReadinessV1ContractConsumerReadinessHandoffArchiveTestSupport() {}

  static Path versionedArtifact(int version, String suffix) throws IOException {
    String versionToken = "-v" + version;
    try (Stream<Path> paths = Files.walk(ARCHIVE_ROOT.resolve(String.valueOf(version)))) {
      return paths
          .filter(Files::isRegularFile)
          .filter(
              path -> {
                String name = path.getFileName().toString();
                return name.contains(versionToken) && name.endsWith(suffix);
              })
          .findFirst()
          .orElseThrow(() -> new AssertionError("Missing " + suffix + " artifact for v" + version));
    }
  }

  static Path evidenceJson(
      OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.Receipt
          receipt) {
    return WORKING_ROOT.resolve(receipt.evidencePath());
  }

  static Path htmlArchive(
      OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.Receipt receipt)
      throws IOException {
    return versionedArtifact(receipt.version(), evidenceStem(evidenceJson(receipt)) + ".html");
  }

  static Path explanation(
      OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.Receipt receipt)
      throws IOException {
    try (Stream<Path> paths = Files.walk(ARCHIVE_ROOT.resolve(String.valueOf(receipt.version())))) {
      return paths
          .filter(Files::isRegularFile)
          .filter(
              path -> {
                String name = path.getFileName().toString();
                return name.endsWith(".md") && !name.endsWith("-browser-snapshot.md");
              })
          .findFirst()
          .orElseThrow(
              () -> new AssertionError("Missing explanation artifact for v" + receipt.version()));
    }
  }

  static Path browserSnapshot(
      OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.Receipt
          receipt) {
    Path evidencePath = evidenceJson(receipt);
    return evidencePath.getParent().resolve(evidenceStem(evidencePath) + "-browser-snapshot.md");
  }

  static Path screenshot(
      OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.Receipt receipt)
      throws IOException {
    return versionedArtifact(receipt.version(), evidenceStem(evidenceJson(receipt)) + ".png");
  }

  private static String evidenceStem(Path evidencePath) {
    String fileName = evidencePath.getFileName().toString();
    return fileName.substring(0, fileName.length() - ".json".length());
  }
}
