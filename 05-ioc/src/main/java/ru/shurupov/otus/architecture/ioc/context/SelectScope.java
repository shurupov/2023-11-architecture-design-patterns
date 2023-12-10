package ru.shurupov.otus.architecture.ioc.context;

import static ru.shurupov.otus.architecture.ioc.strategy.ScopedIoCStrategy.CURRENT_SCOPE_KEY;
import static ru.shurupov.otus.architecture.ioc.strategy.ScopedIoCStrategy.META_KEY;
import static ru.shurupov.otus.architecture.ioc.strategy.ScopedIoCStrategy.SCOPE_NAMES_KEY;

import java.util.List;
import java.util.Map;

public class SelectScope implements ContextProcessor {

  @Override
  public void process(Map<String, Object> context, Object[] args) {
    String scopeName = (String) args[0];
    Map<String, Object> meta = (Map<String, Object>) context.get(META_KEY);
    List<String> scopes = (List<String>) meta.get(SCOPE_NAMES_KEY);
    if (scopes.contains(scopeName)) {
      meta.put(CURRENT_SCOPE_KEY, scopeName);
    }
  }
}
