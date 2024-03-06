package ru.shurupov.otus.architecture.interpreter.expression;

import ru.shurupov.otus.architecture.interpreter.interpretation.Action;

public interface ActionNode extends AstNode<Action> {
  String getActionParameter();
}
