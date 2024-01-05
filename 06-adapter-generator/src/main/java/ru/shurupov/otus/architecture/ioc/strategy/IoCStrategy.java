package ru.shurupov.otus.architecture.ioc.strategy;

public interface IoCStrategy {
  <T> T resolve(String key, Object ...args);
}
