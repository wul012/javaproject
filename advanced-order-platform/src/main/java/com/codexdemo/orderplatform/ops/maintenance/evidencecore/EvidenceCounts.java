package com.codexdemo.orderplatform.ops.maintenance.evidencecore;

import java.util.List;
import java.util.function.Predicate;

public final class EvidenceCounts {

  private EvidenceCounts() {}

  public static <T> int matching(List<T> entries, Predicate<? super T> predicate) {
    return Math.toIntExact(entries.stream().filter(predicate).count());
  }
}
