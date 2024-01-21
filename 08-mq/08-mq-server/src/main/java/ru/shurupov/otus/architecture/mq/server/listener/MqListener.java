package ru.shurupov.otus.architecture.mq.server.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.shurupov.otus.architecture.mq.model.GameMessage;
import ru.shurupov.otus.architecture.mq.server.service.MessageService;

@Slf4j
@RequiredArgsConstructor
@Component
public class MqListener {

  private final MessageService messageService;

  @RabbitListener(queues = "game")
  public void processGameQueue(GameMessage message) {
    log.info("Received message: {}", message);
    messageService.processMessage(message);
  }
}
