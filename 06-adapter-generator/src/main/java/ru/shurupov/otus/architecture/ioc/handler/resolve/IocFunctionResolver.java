package ru.shurupov.otus.architecture.ioc.handler.resolve;

import java.util.Map;
import java.util.function.Function;

public class IocFunctionResolver<R> implements IocResolveHandler<Function<Object[], R>, R> {

  @Override
  public boolean canHandle(Object value) {
    return value instanceof Function;
  }

  @Override
  public R resolve(Map<String, Object> context, Function<Object[], R> function, Object[] args) {
    return function.apply(args);
  }
}
