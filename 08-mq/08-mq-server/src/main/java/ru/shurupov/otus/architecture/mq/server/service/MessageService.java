package ru.shurupov.otus.architecture.mq.server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.shurupov.otus.architecture.ioc.IoC;
import ru.shurupov.otus.architecture.mq.model.GameMessage;

@RequiredArgsConstructor
@Service
public class MessageService {

  private final IoC ioc;

  public void processMessage(GameMessage message) {
    ioc.resolve("Scope.Select", message.getGameId());
  }

}
