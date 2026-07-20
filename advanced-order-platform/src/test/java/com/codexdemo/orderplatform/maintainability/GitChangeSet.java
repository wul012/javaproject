package com.codexdemo.orderplatform.maintainability;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

final class GitChangeSet {

  private static final Path ROOT = Path.of("").toAbsolutePath();
  private static final List<String> JAVA_ROOTS = List.of("src/main/java", "src/test/java");

  private GitChangeSet() {}

  static Set<Path> addedJavaFiles() throws IOException {
    return addedFiles(JAVA_ROOTS, ".java");
  }

  static Set<Path> changedMarkdownFiles() throws IOException {
    return changedFiles(List.of("docs"), ".md");
  }

  static SourceDelta mainSourceDelta() throws IOException {
    List<String> working = git("diff", "--numstat", "HEAD", "--", "src/main/java");
    Set<Path> untracked = untracked(List.of("src/main/java"), ".java");
    if (!working.isEmpty() || !untracked.isEmpty()) {
      return delta(working, untracked);
    }
    return delta(git("diff", "--numstat", "HEAD^", "HEAD", "--", "src/main/java"), Set.of());
  }

  static Optional<String> priorFile(Path file) throws IOException {
    String path = repositoryPath(file);
    boolean changed = !git("status", "--porcelain=v1", "--", file.toString()).isEmpty();
    String revision = changed ? "HEAD" : "HEAD^";
    GitResult result = gitResult("show", revision + ":" + path);
    return result.exitCode() == 0
        ? Optional.of(String.join("\n", result.lines()) + "\n")
        : Optional.empty();
  }

  private static Set<Path> changedFiles(List<String> roots, String suffix) throws IOException {
    Set<Path> working =
        paths(gitWithRoots(List.of("diff", "--name-only", "HEAD", "--"), roots), suffix);
    working.addAll(untracked(roots, suffix));
    working.removeIf(path -> !Files.isRegularFile(path));
    if (!working.isEmpty()) {
      return working;
    }
    Set<Path> committed =
        paths(gitWithRoots(List.of("diff", "--name-only", "HEAD^", "HEAD", "--"), roots), suffix);
    committed.removeIf(path -> !Files.isRegularFile(path));
    return committed;
  }

  private static Set<Path> addedFiles(List<String> roots, String suffix) throws IOException {
    Set<Path> working =
        paths(
            gitWithRoots(List.of("diff", "--name-only", "--diff-filter=A", "HEAD", "--"), roots),
            suffix);
    working.addAll(untracked(roots, suffix));
    if (!working.isEmpty()) {
      return working;
    }
    return paths(
        gitWithRoots(
            List.of("diff", "--name-only", "--diff-filter=A", "HEAD^", "HEAD", "--"), roots),
        suffix);
  }

  private static Set<Path> untracked(List<String> roots, String suffix) throws IOException {
    return paths(
        gitWithRoots(List.of("ls-files", "--others", "--exclude-standard", "--"), roots), suffix);
  }

  private static Set<Path> paths(List<String> names, String suffix) throws IOException {
    Set<Path> paths = new LinkedHashSet<>();
    String prefix = git("rev-parse", "--show-prefix").get(0);
    names.stream()
        .map(name -> name.replace('\\', '/'))
        .map(name -> name.startsWith(prefix) ? name.substring(prefix.length()) : name)
        .filter(name -> name.endsWith(suffix))
        .map(ROOT::resolve)
        .forEach(paths::add);
    return paths;
  }

  private static SourceDelta delta(List<String> rows, Set<Path> untracked) throws IOException {
    long added = 0;
    long deleted = 0;
    for (String row : rows) {
      String[] parts = row.split("\\t", 3);
      if (parts.length == 3 && !parts[0].equals("-") && !parts[1].equals("-")) {
        added += Long.parseLong(parts[0]);
        deleted += Long.parseLong(parts[1]);
      }
    }
    for (Path path : untracked) {
      try (var lines = Files.lines(path, StandardCharsets.UTF_8)) {
        added += lines.count();
      }
    }
    return new SourceDelta(added, deleted);
  }

  private static List<String> gitWithRoots(List<String> args, List<String> roots)
      throws IOException {
    List<String> command = new ArrayList<>(args);
    command.addAll(roots);
    return git(command.toArray(String[]::new));
  }

  private static String repositoryPath(Path file) throws IOException {
    String prefix = git("rev-parse", "--show-prefix").get(0);
    Path absolute = file.isAbsolute() ? file : ROOT.resolve(file);
    return prefix + ROOT.relativize(absolute).toString().replace('\\', '/');
  }

  private static List<String> git(String... args) throws IOException {
    GitResult result = gitResult(args);
    if (result.exitCode() != 0) {
      throw new IOException("git failed: " + String.join(" ", args));
    }
    return result.lines();
  }

  private static GitResult gitResult(String... args) throws IOException {
    List<String> command = new ArrayList<>();
    command.add("git");
    command.addAll(List.of(args));
    Process process =
        new ProcessBuilder(command).directory(ROOT.toFile()).redirectErrorStream(true).start();
    List<String> output;
    try (var reader = process.inputReader(StandardCharsets.UTF_8)) {
      output = reader.lines().toList();
    }
    try {
      return new GitResult(process.waitFor(), output);
    } catch (InterruptedException exception) {
      Thread.currentThread().interrupt();
      throw new IOException("git interrupted", exception);
    }
  }

  private record GitResult(int exitCode, List<String> lines) {}

  record SourceDelta(long added, long deleted) {}
}
