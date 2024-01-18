package ru.shurupov.otus.architecture.mq.server.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.shurupov.otus.architecture.mq.model.GameMessage;

@Slf4j
@Component
public class MqListener {
  @RabbitListener(queues = "game")
  public void processGameQueue(GameMessage message) {
    log.info("Received message: {}", message);
  }
}
