package ru.shurupov.otus.architecture.ioc.context;

import java.util.Map;

public class IocUnregister implements ContextProcessor {

  @Override
  public void process(Map<String, Object> context, Object[] args) {
    String key = (String) args[0];
    context.remove(key);
  }
}
