package ru.shurupov.otus.architecture.mq.client.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;
import ru.shurupov.otus.architecture.mq.model.GameMessage;

@Slf4j
@RequiredArgsConstructor
@Service
public class MessageService {

  private final AmqpTemplate amqp;

  public void send(GameMessage message) {
    log.info("received and sent: {}", message);
    amqp.convertAndSend(message);
  }
}
