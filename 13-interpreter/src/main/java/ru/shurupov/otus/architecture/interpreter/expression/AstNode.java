package ru.shurupov.otus.architecture.interpreter.expression;

import ru.shurupov.otus.architecture.ioc.IoC;

public interface AstNode<O>  {
  O interpret(IoC ioc);
}
