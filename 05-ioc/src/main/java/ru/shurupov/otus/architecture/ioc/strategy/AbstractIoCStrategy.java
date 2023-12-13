package ru.shurupov.otus.architecture.ioc.strategy;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import ru.shurupov.otus.architecture.ioc.handler.resolve.IocResolveHandler;

@RequiredArgsConstructor
public abstract class AbstractIoCStrategy implements IoCStrategy {

  protected final Map<String, Object> context;
  protected final List<IocResolveHandler> resolveHandlers;

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

  protected abstract Object get(String key);

  protected abstract Map<String, Object> getContext();
}
