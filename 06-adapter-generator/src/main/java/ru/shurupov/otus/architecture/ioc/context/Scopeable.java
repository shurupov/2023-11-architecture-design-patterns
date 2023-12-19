package ru.shurupov.otus.architecture.ioc.context;

import static ru.shurupov.otus.architecture.ioc.strategy.ScopedIoCStrategy.CURRENT_SCOPE_KEY;

import java.util.Map;

public abstract class Scopeable {
  protected ThreadLocal<String> getCurrentScopeThreadLocal(Map<String, Object> meta) {
    return (ThreadLocal<String>) meta.get(CURRENT_SCOPE_KEY);
  }
}
