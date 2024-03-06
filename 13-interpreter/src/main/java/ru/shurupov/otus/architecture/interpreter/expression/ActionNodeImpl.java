package ru.shurupov.otus.architecture.interpreter.expression;

import lombok.Value;
import ru.shurupov.otus.architecture.interpreter.interpretation.Action;
import ru.shurupov.otus.architecture.ioc.IoC;

@Value
public class ActionNodeImpl implements ActionNode {

  String objectId;
  String actionName;
  String actionParameter;

  @Override
  public Action interpret(IoC ioc) {
    return ioc.resolve("Object.Action", objectId, actionName);
  }
}
