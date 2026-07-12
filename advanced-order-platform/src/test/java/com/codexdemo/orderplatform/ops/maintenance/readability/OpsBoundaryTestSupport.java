package com.codexdemo.orderplatform.ops.maintenance.readability;

import static com.codexdemo.orderplatform.ops.maintenance.readability.OpsExtractionTestSupport.allJavaFiles;
import static com.codexdemo.orderplatform.ops.maintenance.readability.OpsExtractionTestSupport.read;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

final class OpsBoundaryTestSupport {

  private OpsBoundaryTestSupport() {}

  static BoundaryCensus boundaryCensus(Path sourceRoot, Path packageRoot, List<String> fileNames)
      throws IOException {
    Set<String> candidateTypes = new TreeSet<>();
    for (String fileName : fileNames) {
      candidateTypes.add(fileName.substring(0, fileName.length() - ".java".length()));
    }

    Set<Path> sources = new HashSet<>();
    Set<String> targets = new TreeSet<>();
    int edges = 0;
    for (Path path : allJavaFiles(sourceRoot)) {
      if (path.startsWith(packageRoot)) {
        continue;
      }
      String source = read(path);
      for (String target : candidateTypes) {
        if (containsType(source, target)) {
          sources.add(path);
          targets.add(target);
          edges++;
        }
      }
    }
    return new BoundaryCensus(sources.size(), edges, targets);
  }

  static List<Path> externalReaders(Path sourceRoot, Path packageRoot, String needle)
      throws IOException {
    return allJavaFiles(sourceRoot).stream()
        .filter(path -> !path.startsWith(packageRoot))
        .filter(path -> contains(path, needle))
        .sorted()
        .toList();
  }

  private static boolean contains(Path path, String needle) {
    try {
      return read(path).contains(needle);
    } catch (IOException exception) {
      throw new IllegalStateException(exception);
    }
  }

  private static boolean containsType(String source, String typeName) {
    return Pattern.compile("\\b" + Pattern.quote(typeName) + "\\b").matcher(source).find();
  }

  record BoundaryCensus(int sourceCount, int edgeCount, Set<String> targetNames) {

    BoundaryCensus {
      targetNames = Set.copyOf(targetNames);
    }
  }
}
