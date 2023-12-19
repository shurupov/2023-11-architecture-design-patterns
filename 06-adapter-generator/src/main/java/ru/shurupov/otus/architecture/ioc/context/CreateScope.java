package ru.shurupov.otus.architecture.ioc.context;

import static ru.shurupov.otus.architecture.ioc.strategy.ScopedIoCStrategy.META_KEY;
import static ru.shurupov.otus.architecture.ioc.strategy.ScopedIoCStrategy.PARENT_SCOPE_KEY;
import static ru.shurupov.otus.architecture.ioc.strategy.ScopedIoCStrategy.SCOPE_NAMES_KEY;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateScope extends Scopeable implements ContextProcessor {

  @Override
  public void process(Map<String, Object> context, Object[] args) {
    String scopeName = (String) args[0];

    Map<String, Object> meta = (Map<String, Object>) context.get(META_KEY);

    Map<String, Object> currentScope = (Map<String, Object>) meta.get(getCurrentScopeThreadLocal(meta).get());

    Map<String, Object> newScope = new HashMap<>();
    newScope.put(PARENT_SCOPE_KEY, currentScope);
    newScope.put(META_KEY, meta);
    ((List<String>) meta.get(SCOPE_NAMES_KEY)).add(scopeName);
    meta.put(scopeName, newScope);
  }
}
