package ru.shurupov.otus.architecture.interpreter.expression;

import lombok.Value;
import ru.shurupov.otus.architecture.control.ControlAction;
import ru.shurupov.otus.architecture.ioc.IoC;

@Value
public class ActionNodeImpl implements ActionNode {

  String objectId;
  String actionName;
  String actionParameter;

  @Override
  public ControlAction interpret(IoC ioc) {
    return ioc.resolve("Object.Action", objectId, actionName);
  }
}
