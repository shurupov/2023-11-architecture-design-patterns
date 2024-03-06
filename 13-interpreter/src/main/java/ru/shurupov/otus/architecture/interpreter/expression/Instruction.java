package ru.shurupov.otus.architecture.interpreter.expression;

import ru.shurupov.otus.architecture.command.Command;
import ru.shurupov.otus.architecture.ioc.IoC;

public interface Instruction extends AstNode<Command> {
  Command interpret(IoC ioc);
}
