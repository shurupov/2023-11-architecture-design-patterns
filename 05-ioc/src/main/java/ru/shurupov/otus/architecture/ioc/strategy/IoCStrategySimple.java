package ru.shurupov.otus.architecture.ioc.strategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ru.shurupov.otus.architecture.ioc.handler.resolve.IocResolveHandler;

public class IoCStrategySimple implements IoCStrategy {

  private final Map<String, Object> context;

  private final List<IocResolveHandler> resolveHandlers;

  public IoCStrategySimple(Map<String, Object> initContext, List<IocResolveHandler> resolveHandlers) {
    this.resolveHandlers = resolveHandlers;
    this.context = new HashMap<>(initContext);
  }

  @Override
  public <T> T resolve(String key, Object... args) {
    Object result = get(key);

    if (result == null) {
      return null;
    }

    for (IocResolveHandler resolveHandler : resolveHandlers) {
      if (resolveHandler.canHandle(result)) {
        return (T) resolveHandler.resolve(context, result, args);
      }
    }

    return (T) result;
  }
  private Object get(String key) {
    return context.get(key);
  }

}
