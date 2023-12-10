package ru.shurupov.otus.architecture.ioc.strategy;

import com.sun.source.tree.Scope;
import java.util.List;
import java.util.Map;
import ru.shurupov.otus.architecture.ioc.handler.resolve.IocResolveHandler;

public class IoCStrategyScoped implements IoCStrategy {

  private List<Scope> scopes;
  private Map<String, Map<String, Object>> contexts;

  private List<IocResolveHandler> resolveHandlers;

  @Override
  public <T> T resolve(String key, Object... args) {
    return null;
  }
}
