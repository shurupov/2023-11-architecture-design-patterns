package ru.shurupov.otus.architecture.ioc.handler.resolve;

import java.util.Map;
import java.util.function.BiFunction;

public class IocContextFunctionResolver<R> implements IocResolveHandler<BiFunction<Map<String, Object>, Object[], R>, R> {

  @Override
  public boolean canHandle(Object value) {
    return value instanceof BiFunction;
  }

  @Override
  public R resolve(Map<String, Object> context, BiFunction<Map<String, Object>, Object[], R> function, Object[] args) {
    return (R) function.apply(context, args);
  }
}
