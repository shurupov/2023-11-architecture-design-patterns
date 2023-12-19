package ru.shurupov.otus.architecture.ioc.handler.resolve;

import java.util.Map;
import java.util.function.Supplier;

public class IocSupplierResolver<R> implements IocResolveHandler<Supplier, R> {

  @Override
  public boolean canHandle(Object value) {
    return value instanceof Supplier;
  }

  @Override
  public R resolve(Map<String, Object> context, Supplier supplier, Object[] args) {
    return (R) supplier.get();
  }
}
