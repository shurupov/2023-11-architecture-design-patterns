package ru.shurupov.otus.architecture.ioc.strategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ru.shurupov.otus.architecture.ioc.handler.resolve.IocResolveHandler;

public class SimpleIoCStrategy extends AbstractIoCStrategy {

  public SimpleIoCStrategy(Map<String, Object> initContext, List<IocResolveHandler> resolveHandlers) {
    super(new HashMap<>(initContext), resolveHandlers);
  }

  @Override
  public <T> T resolve(String key, Object... args) {
    Object result = get(key);

    if (result == null) {
      return null;
    }

    for (IocResolveHandler resolveHandler : resolveHandlers) {
      if (resolveHandler.canHandle(result)) {
        return (T) resolveHandler.resolve(getContext(), result, args);
      }
    }

    return (T) result;
  }
  protected Object get(String key) {
    return context.get(key);
  }

  @Override
  protected Map<String, Object> getContext() {
    return context;
  }

}
