package ru.shurupov.otus.architecture.ioc.strategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ru.shurupov.otus.architecture.ioc.handler.resolve.IocResolveHandler;

public class ScopedIoCStrategy extends AbstractIoCStrategy {

  public static final String CURRENT_SCOPE_KEY = "currentScope";
  public static final String SCOPE_NAME_KEY = "scopeName";
  public static final String SCOPE_NAMES_KEY = "scopes";
  public static final String ROOT_SCOPE_NAME = "root";
  public static final String PARENT_SCOPE_KEY = "parentScope";
  public static final String META_KEY = "meta";

  public ScopedIoCStrategy(Map<String, Object> initContext, List<IocResolveHandler> resolveHandlers) {
    super(new HashMap<>(), resolveHandlers);
    Map<String, Object> rootScope = new HashMap<>(initContext);
    rootScope.put(SCOPE_NAME_KEY, ROOT_SCOPE_NAME);
    rootScope.put(META_KEY, context);
    context.put(ROOT_SCOPE_NAME, rootScope);
    context.put(CURRENT_SCOPE_KEY, ROOT_SCOPE_NAME);
    List<String> scopeNames = new ArrayList<>();
    scopeNames.add(ROOT_SCOPE_NAME);
    context.put(SCOPE_NAMES_KEY, scopeNames);
  }

  protected Object get(String key) {
    Object value;
    Map<String, Object> scope = (Map<String, Object>) context.get(context.get(CURRENT_SCOPE_KEY));
    while (scope != null) {
      value = scope.get(key);
      if (value != null) {
        return value;
      }
      scope = (Map<String, Object>) scope.get(PARENT_SCOPE_KEY);
    }
    return null;
  }

  @Override
  protected Map<String, Object> getContext() {
    return (Map<String, Object>) context.get(context.get(CURRENT_SCOPE_KEY));
  }
}
