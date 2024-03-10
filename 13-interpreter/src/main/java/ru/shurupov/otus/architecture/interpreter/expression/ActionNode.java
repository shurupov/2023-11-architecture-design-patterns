package ru.shurupov.otus.architecture.interpreter.expression;

import ru.shurupov.otus.architecture.control.ControlAction;

public interface ActionNode extends AstNode<ControlAction> {
  String getActionParameter();
}
