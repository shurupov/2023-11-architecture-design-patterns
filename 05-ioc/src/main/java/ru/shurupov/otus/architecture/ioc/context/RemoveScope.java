package ru.shurupov.otus.architecture.ioc.context;

import static ru.shurupov.otus.architecture.ioc.strategy.ScopedIoCStrategy.META_KEY;
import static ru.shurupov.otus.architecture.ioc.strategy.ScopedIoCStrategy.ROOT_SCOPE_NAME;
import static ru.shurupov.otus.architecture.ioc.strategy.ScopedIoCStrategy.SCOPE_NAMES_KEY;

import java.util.List;
import java.util.Map;

public class RemoveScope extends Scopeable implements ContextProcessor {

  @Override
  public void process(Map<String, Object> context, Object[] args) {
    String scopeName = (String) args[0];

    Map<String, Object> meta = (Map<String, Object>) context.get(META_KEY);

    meta.remove(scopeName);
    ((List<String>) meta.get(SCOPE_NAMES_KEY)).remove(scopeName);

    if (getCurrentScopeThreadLocal(meta).get().equals(scopeName)) {
      getCurrentScopeThreadLocal(meta).set(ROOT_SCOPE_NAME);
    }
  }
}
