package ru.shurupov.otus.architecture.ioc;

import ru.shurupov.otus.architecture.ioc.strategy.IoCStrategy;

public class IoC {

  IoCStrategy strategy;


  public IoC(IoCStrategy strategy) {
    this.strategy = strategy;
  }

  public <T> T resolve(String key, Object ...args) {
    return strategy.resolve(key, args);
  }
}
