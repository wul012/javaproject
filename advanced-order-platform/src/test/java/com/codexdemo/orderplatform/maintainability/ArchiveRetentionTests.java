package com.codexdemo.orderplatform.maintainability;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
import java.util.Set;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class ArchiveRetentionTests {

  private static final Path ROOT = Path.of("").toAbsolutePath();
  private static final Path MANIFEST =
      ROOT.resolve(Path.of("docs", "archive-retention-manifest.txt"));
  private static final List<String> FIXED_ROOTS =
      List.of("a", "b", "c", "d", "d_runtime_screenshot_archive_next", "e", "f");
  private static final Set<String> TEXT_EXTS = Set.of(".md", ".json", ".html");
  private static final int FILE_CAP = 1678;
  private static final long BYTE_CAP = 19_819_450L;

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
  void textHashIgnoresLineEnding(@TempDir Path temp) throws Exception {
    Path lfText = temp.resolve("lf.md");
    Path crlfText = temp.resolve("crlf.md");
    Path lfBinary = temp.resolve("lf.png");
    Path crlfBinary = temp.resolve("crlf.png");
    byte[] lf = "first\nsecond\n".getBytes(StandardCharsets.UTF_8);
    byte[] crlf = "first\r\nsecond\r\n".getBytes(StandardCharsets.UTF_8);

    Files.write(lfText, lf);
    Files.write(crlfText, crlf);
    Files.write(lfBinary, lf);
    Files.write(crlfBinary, crlf);

    assertThat(sha256(lfText)).isEqualTo(sha256(crlfText));
    assertThat(sha256(lfBinary)).isNotEqualTo(sha256(crlfBinary));
  }

  @Test
  void policyKeepsBoundaries() throws IOException {
    String policy = read(ROOT.resolve(Path.of("docs", "archive-retention-policy.md")));
    String script = read(ROOT.resolve(Path.of("scripts", "archive-retention-census.ps1")));

    assertThat(policy)
        .contains(
            "-WriteManifest",
            "SHA-256",
            "文件数和原始总字节数是只减不增的上限",
            "不移动、不重命名、不压缩、不删除历史文件",
            "Node 已固定的绝对路径");
    assertThat(script)
        .contains(
            "$walkthroughPrefix",
            "$textExtensions",
            "Get-CanonicalBytes",
            "[BitConverter]::ToString",
            "[StringComparer]::Ordinal");
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
    byte[] source = Files.readAllBytes(file);
    byte[] canonical = isText(file) ? canonicalText(source) : source;
    return HexFormat.of().formatHex(digest.digest(canonical));
  }

  private static boolean isText(Path file) {
    String name = file.getFileName().toString();
    int separator = name.lastIndexOf('.');
    String extension = separator < 0 ? "" : name.substring(separator).toLowerCase();
    return TEXT_EXTS.contains(extension);
  }

  private static byte[] canonicalText(byte[] source) {
    ByteArrayOutputStream target = new ByteArrayOutputStream(source.length);
    for (int index = 0; index < source.length; index++) {
      if (source[index] == '\r' && index + 1 < source.length && source[index + 1] == '\n') {
        target.write('\n');
        index++;
      } else {
        target.write(source[index]);
      }
    }
    return target.toByteArray();
  }

  private static String relative(Path file) {
    return ROOT.relativize(file).toString().replace('\\', '/');
  }

  private static String read(Path path) throws IOException {
    return Files.readString(path, StandardCharsets.UTF_8);
  }
}
