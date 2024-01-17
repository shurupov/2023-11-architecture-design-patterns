package ru.shurupov.otus.architecture.mq.server.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MqListener {
  @RabbitListener(queues = "game")
  public void processGameQueue(String message) {
    log.info("Received message: {}", message);
  }
}
