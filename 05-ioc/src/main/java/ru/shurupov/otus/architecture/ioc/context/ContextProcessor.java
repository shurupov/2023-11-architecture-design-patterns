package ru.shurupov.otus.architecture.ioc.context;

import java.util.Map;

public interface ContextProcessor {
  void process(Map<String, Object> context, Object[] args);
}
