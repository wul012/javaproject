package com.codexdemo.orderplatform.maintainability;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HexFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class ArchiveRetentionTests {

  private static final Path ROOT = Path.of("").toAbsolutePath();
  private static final Path MANIFEST =
      ROOT.resolve(Path.of("docs", "archive-retention-manifest.txt"));
  private static final List<String> FIXED_ROOTS =
      List.of("a", "b", "c", "d", "d_runtime_screenshot_archive_next", "e", "f");
  private static final int FILE_CAP = 1678;
  private static final long BYTE_CAP = 19_819_092L;

  @Test
  void manifestMatchesArchiveBytes() throws Exception {
    Map<String, String> expected = manifest();
    List<Path> files = archiveFiles();
    Map<String, String> actual = new LinkedHashMap<>();

    for (Path file : files) {
      actual.put(relative(file), sha256(file));
    }

    assertThat(actual).containsExactlyInAnyOrderEntriesOf(expected);
  }

  @Test
  void archiveBudgetOnlyShrinks() throws IOException {
    List<Path> files = archiveFiles();
    long bytes = 0;
    for (Path file : files) {
      bytes += Files.size(file);
    }

    assertThat(files).hasSizeLessThanOrEqualTo(FILE_CAP);
    assertThat(bytes).isLessThanOrEqualTo(BYTE_CAP);
    assertThat(manifest()).hasSize(FILE_CAP);
  }

  @Test
  void policyKeepsBoundaries() throws IOException {
    String policy = read(ROOT.resolve(Path.of("docs", "archive-retention-policy.md")));
    String script = read(ROOT.resolve(Path.of("scripts", "archive-retention-census.ps1")));

    assertThat(policy)
        .contains(
            "-WriteManifest",
            "SHA-256",
            "文件数和总字节数是只减不增的上限",
            "不移动、不重命名、不压缩、不删除历史文件",
            "Node 已固定的绝对路径");
    assertThat(script)
        .contains("$walkthroughPrefix", "[BitConverter]::ToString", "[StringComparer]::Ordinal");
  }

  private static Map<String, String> manifest() throws IOException {
    Map<String, String> entries = new LinkedHashMap<>();
    for (String line : Files.readAllLines(MANIFEST, StandardCharsets.UTF_8)) {
      String[] parts = line.split("\\t", 2);
      assertThat(parts).as(line).hasSize(2);
      assertThat(entries.put(parts[0], parts[1])).as(parts[0]).isNull();
    }
    return entries;
  }

  private static List<Path> archiveFiles() throws IOException {
    List<Path> files = new ArrayList<>();
    for (String archiveRoot : archiveRoots()) {
      Path root = ROOT.resolve(archiveRoot);
      assertThat(root).as(archiveRoot).isDirectory();
      try (Stream<Path> paths = Files.walk(root)) {
        paths.filter(Files::isRegularFile).forEach(files::add);
      }
    }
    return files;
  }

  private static List<String> archiveRoots() throws IOException {
    List<String> roots = new ArrayList<>(FIXED_ROOTS);
    try (Stream<Path> paths = Files.list(ROOT)) {
      paths
          .filter(Files::isDirectory)
          .map(path -> path.getFileName().toString())
          .filter(name -> name.startsWith("代码讲解记录"))
          .forEach(roots::add);
    }
    return roots;
  }

  private static String sha256(Path file) throws IOException, NoSuchAlgorithmException {
    MessageDigest digest = MessageDigest.getInstance("SHA-256");
    try (InputStream input = Files.newInputStream(file)) {
      byte[] buffer = new byte[16 * 1024];
      for (int read = input.read(buffer); read >= 0; read = input.read(buffer)) {
        digest.update(buffer, 0, read);
      }
    }
    return HexFormat.of().formatHex(digest.digest());
  }

  private static String relative(Path file) {
    return ROOT.relativize(file).toString().replace('\\', '/');
  }

  private static String read(Path path) throws IOException {
    return Files.readString(path, StandardCharsets.UTF_8);
  }
}
