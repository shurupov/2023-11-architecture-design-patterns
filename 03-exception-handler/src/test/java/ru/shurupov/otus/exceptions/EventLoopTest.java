package ru.shurupov.otus.exceptions;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.LinkedList;
import java.util.Queue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.shurupov.otus.exceptions.command.Command;
import ru.shurupov.otus.exceptions.command.ExceptionLogCommand;
import ru.shurupov.otus.exceptions.command.RetryCommand;
import ru.shurupov.otus.exceptions.command.SecondRetryCommand;
import ru.shurupov.otus.exceptions.exception.Type1Exception;
import ru.shurupov.otus.exceptions.handler.generator.DefaultHandlerGenerator;

@ExtendWith(MockitoExtension.class)
class EventLoopTest {

  @Mock
  private Command command;

  @Test
  void givenCommand_thenStrategyJustLog_thenSuccess() {
    Queue<Command> commandQueue = new LinkedList<>();
    commandQueue.add(command);

    doThrow(new Type1Exception()).when(command).execute();

    HandlerSelector handlerSelector = new HandlerSelector();

    EventLoop eventLoop = new EventLoop(commandQueue, handlerSelector);

    DefaultHandlerGenerator defaultHandlerGenerator = spy(new DefaultHandlerGenerator());

    handlerSelector.setDefaultGenerator(defaultHandlerGenerator);

    eventLoop.update();

    verify(command, times(1)).execute();
    verify(defaultHandlerGenerator, times(1)).apply(any(Type1Exception.class), eq(command));
  }

  @Test
  void givenCommand_thenStrategyLogLater_thenSuccess() {
    Queue<Command> commandQueue = new LinkedList<>();
    commandQueue.add(command);

    doThrow(new Type1Exception()).when(command).execute();

    HandlerSelector handlerSelector = new HandlerSelector();

    EventLoop eventLoop = new EventLoop(commandQueue, handlerSelector);

    Command[] commands = new Command[1];

    handlerSelector.addHandler(Type1Exception.class, command.getClass(), (e, c) -> () -> {
      Command exceptionLogCommand = spy(new ExceptionLogCommand(e));
      commandQueue.add(exceptionLogCommand);
      commands[0] = exceptionLogCommand;
    });

    eventLoop.update();

    verify(command, times(1)).execute();
    verify(commands[0], times(1)).execute();
  }

  @Test
  void givenCommand_thenStrategyRetry_thenSuccess() {
    Queue<Command> commandQueue = new LinkedList<>();
    commandQueue.add(command);

    doThrow(new Type1Exception()).when(command).execute();

    HandlerSelector handlerSelector = new HandlerSelector();

    EventLoop eventLoop = new EventLoop(commandQueue, handlerSelector);

    handlerSelector.addHandler(
        Type1Exception.class,
        command.getClass(),
        (e, c) -> () -> commandQueue.add(new RetryCommand(c))
    );

    eventLoop.update();

    verify(command, times(2)).execute();
  }

  @Test
  void givenCommand_thenStrategyRetryThenLog_thenSuccess() {
    Queue<Command> commandQueue = new LinkedList<>();
    commandQueue.add(command);

    doThrow(new Type1Exception()).when(command).execute();

    HandlerSelector handlerSelector = new HandlerSelector();

    EventLoop eventLoop = new EventLoop(commandQueue, handlerSelector);

    handlerSelector.addHandler(
        Type1Exception.class,
        command.getClass(),
        (e, c) -> () -> commandQueue.add(new RetryCommand(c))
    );

    Command[] commands = new Command[1];
    handlerSelector.addHandler(Type1Exception.class, RetryCommand.class, (e, c) -> () -> {
      Command exceptionLogCommand = spy(new ExceptionLogCommand(e));
      commandQueue.add(exceptionLogCommand);
      commands[0] = exceptionLogCommand;
    });

    eventLoop.update();

    verify(command, times(2)).execute();
    verify(commands[0], times(1)).execute();
  }

  @Test
  void givenCommand_thenStrategyRetryTwiceThenLog_thenSuccess() {
    Queue<Command> commandQueue = new LinkedList<>();
    commandQueue.add(command);

    doThrow(new Type1Exception()).when(command).execute();

    HandlerSelector handlerSelector = new HandlerSelector();

    EventLoop eventLoop = new EventLoop(commandQueue, handlerSelector);

    handlerSelector.addHandler(
        Type1Exception.class,
        command.getClass(),
        (e, c) -> () -> commandQueue.add(new RetryCommand(c))
    );

    Command[] commands = new Command[2];
    handlerSelector.addHandler(
        Type1Exception.class,
        RetryCommand.class,
        (e, c) -> () -> commandQueue.add(new SecondRetryCommand(c))
    );

    handlerSelector.addHandler(Type1Exception.class, SecondRetryCommand.class, (e, c) -> () -> {
      Command exceptionLogCommand = spy(new ExceptionLogCommand(e));
      commandQueue.add(exceptionLogCommand);
      commands[0] = exceptionLogCommand;
    });

    eventLoop.update();

    verify(command, times(3)).execute();
    verify(commands[0], times(1)).execute();
  }

}