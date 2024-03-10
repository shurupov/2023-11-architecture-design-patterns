package ru.shurupov.otus.architecture.interpreter.expression;

import lombok.Value;
import ru.shurupov.otus.architecture.control.ObjectMeta;
import ru.shurupov.otus.architecture.ioc.IoC;

@Value
public class CheckObjectNodeImpl implements CheckObjectNode {

  String objectId;
  String objectType;

  @Override
  public Boolean interpret(IoC ioc) {
    ObjectMeta object = ioc.resolve("Object.Meta.Get", objectId);
    return (object != null && objectType.equals(object.getType()));
  }
}
