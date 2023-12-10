package ru.shurupov.otus.architecture.ioc.context;

import static ru.shurupov.otus.architecture.ioc.strategy.ScopedIoCStrategy.CURRENT_SCOPE_KEY;
import static ru.shurupov.otus.architecture.ioc.strategy.ScopedIoCStrategy.PARENT_SCOPE_KEY;
import static ru.shurupov.otus.architecture.ioc.strategy.ScopedIoCStrategy.SCOPE_NAME_KEY;

import java.util.Map;

public class SelectParentScope implements ContextProcessor {

  @Override
  public void process(Map<String, Object> context, Object[] args) {
    Map<String, Object> currentScope = (Map<String, Object>) context.get(context.get(CURRENT_SCOPE_KEY));

    if (currentScope.containsKey(PARENT_SCOPE_KEY)) {
      Map<String, Object> parentScope = (Map<String, Object>) context.get((String) currentScope.get(PARENT_SCOPE_KEY));
      String parentScopeName = (String) parentScope.get(SCOPE_NAME_KEY);
      context.put(CURRENT_SCOPE_KEY, parentScopeName);
    }
  }
}
