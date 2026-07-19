package com.codexdemo.orderplatform.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ImmutableLists {

  private ImmutableLists() {}

  public static <T> List<T> copy(List<? extends T> source) {
    return source == null ? null : Collections.unmodifiableList(new ArrayList<>(source));
  }
}
