package ru.shurupov.otus.architecture.mq.server.service;

import java.util.concurrent.BlockingQueue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.shurupov.otus.architecture.command.Command;
import ru.shurupov.otus.architecture.command.InterpretCommand;
import ru.shurupov.otus.architecture.ioc.IoC;
import ru.shurupov.otus.architecture.mq.model.GameMessage;

@RequiredArgsConstructor
@Service
public class MessageService {

  private final IoC ioc;

  public void processMessage(GameMessage message) throws InterruptedException {
    ioc.resolve("Scope.Select", message.getGameId());
    InterpretCommand interpretCommand = new InterpretCommand(ioc, message);
    BlockingQueue<Command> queue = ioc.resolve("Event.Loop.Queue");
    queue.put(interpretCommand);
  }

}
