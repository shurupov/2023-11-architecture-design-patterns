package ru.shurupov.otus.architecture.command;

import java.util.concurrent.BlockingQueue;
import lombok.RequiredArgsConstructor;
import ru.shurupov.otus.architecture.exception.CommandException;
import ru.shurupov.otus.architecture.exception.InterpretCommandException;
import ru.shurupov.otus.architecture.ioc.IoC;
import ru.shurupov.otus.architecture.mq.model.GameMessage;

@RequiredArgsConstructor
public class InterpretCommand implements Command {

  private final IoC ioc;
  private final GameMessage message;

  @Override
  public void execute() throws CommandException {
    try {
      ioc.resolve("Scope.Select", message.getGameId());

      Object[] args = new Object[message.getArgs().size() + 1];
      args[0] = message.getObjectId();
      for (int i = 0; i < message.getArgs().size(); i++) {
        args[i + 1] = message.getArgs().get(i);
      }

      Command command = ioc.resolve("Command.Create." + message.getOperation(), args);

      BlockingQueue<Command> queue =  ioc.resolve("Event.Loop.Queue");

      queue.put(command);
    } catch (Throwable e) {
      throw new InterpretCommandException(e);
    }
  }
}
