package org.wahlzeit.model;

import java.util.Map;
import java.util.WeakHashMap;

public class ValueObjectManager<T> {
  private Map<T, T> instances = new WeakHashMap<>();

  public synchronized T getSharedValueObject(T needle) {
    if(instances.containsKey(needle)) {
      return instances.get(needle);
    }

    instances.put(needle, needle);
    return needle;
  }
}
