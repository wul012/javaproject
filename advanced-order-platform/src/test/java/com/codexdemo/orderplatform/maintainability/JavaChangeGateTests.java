package com.codexdemo.orderplatform.maintainability;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class JavaChangeGateTests {

  @Test
  void addedJavaFilesUseShortNames() throws IOException {
    for (Path source : GitChangeSet.addedJavaFiles()) {
      assertThat(JavaSourceNames.stem(source).length())
          .as(source.toString())
          .isLessThanOrEqualTo(JavaSourceNames.NAME_BUDGET);
    }
  }

  @Test
  void sourceGrowthStaysBounded() throws IOException {
    GitChangeSet.SourceDelta delta = GitChangeSet.mainSourceDelta();
    assertThat(delta.added() <= 400 || delta.added() <= delta.deleted())
        .as("production source delta +%s/-%s", delta.added(), delta.deleted())
        .isTrue();
  }

  @Test
  void newFamiliesHaveDesignNotes() throws IOException {
    Map<Path, List<Path>> families =
        GitChangeSet.addedJavaFiles().stream().collect(Collectors.groupingBy(Path::getParent));
    Set<Path> largeFamilies =
        families.entrySet().stream()
            .filter(entry -> entry.getValue().size() >= 3)
            .map(Map.Entry::getKey)
            .collect(Collectors.toSet());
    if (largeFamilies.isEmpty()) {
      return;
    }

    assertThat(GitChangeSet.changedMarkdownFiles())
        .as("new Java families: %s", largeFamilies)
        .anySatisfy(path -> assertThat(hasDesignNote(path)).as(path.toString()).isTrue());
  }

  private static boolean hasDesignNote(Path path) throws IOException {
    List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
    int start = lines.indexOf("## Family design");
    if (start < 0) {
      return false;
    }
    int end = start + 1;
    while (end < lines.size() && !lines.get(end).startsWith("## ")) {
      end++;
    }
    List<String> note = lines.subList(start + 1, end);
    String text = String.join("\n", note);
    return note.size() <= 10
        && text.contains("Abstraction:")
        && text.contains("Data boundary:")
        && text.contains("Behavior boundary:");
  }
}
