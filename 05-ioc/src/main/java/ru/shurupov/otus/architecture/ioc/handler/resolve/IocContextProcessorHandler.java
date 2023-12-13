package ru.shurupov.otus.architecture.ioc.handler.resolve;

import java.util.Map;
import ru.shurupov.otus.architecture.ioc.context.ContextProcessor;

public class IocContextProcessorHandler implements IocResolveHandler<ContextProcessor, Boolean> {

  @Override
  public boolean canHandle(Object value) {
    return value instanceof ContextProcessor;
  }

  @Override
  public Boolean resolve(Map<String, Object> context, ContextProcessor contextProcessor, Object[] args) {
    try {
      contextProcessor.process(context, args);
      return Boolean.TRUE;
    } catch (Throwable e) {
      return Boolean.FALSE;
    }
  }
}
