package ru.shurupov.otus.architecture.ioc.strategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ru.shurupov.otus.architecture.ioc.context.IocRegister;
import ru.shurupov.otus.architecture.ioc.handler.resolve.IocContextProcessorHandler;
import ru.shurupov.otus.architecture.ioc.handler.resolve.IocFunctionResolver;
import ru.shurupov.otus.architecture.ioc.handler.resolve.IocResolveHandler;
import ru.shurupov.otus.architecture.ioc.handler.resolve.IocSupplierResolver;

public class IoCStrategySimple implements IoCStrategy {

  private Map<String, Object> context;

  private List<IocResolveHandler> resolveHandlers;

  public IoCStrategySimple() {
    //TODO Вытащить
    resolveHandlers = List.of(
        new IocContextProcessorHandler(),
        new IocFunctionResolver<>(),
        new IocSupplierResolver<>()
    );

    context = new HashMap<>();
    context.put("IoC.Register", new IocRegister());
  }

  @Override
  public <T> T resolve(String key, Object... args) {
    Object result = get(key);

    if (result == null) {
      return null;
    }

    for (IocResolveHandler resolveHandler : resolveHandlers) {
      if (resolveHandler.canHandle(result)) {
        return (T) resolveHandler.<T>resolve(context, result, args);
      }
    }

    return (T) result;
  }
  private Object get(String key) {
    return context.get(key);
  }

}
