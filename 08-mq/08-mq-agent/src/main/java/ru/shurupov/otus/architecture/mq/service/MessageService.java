package ru.shurupov.otus.architecture.mq.service;

import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MessageService {

  private final AmqpTemplate amqp;

  public void send(String message) {
    amqp.send(
        MessageBuilder
            .withBody(message.getBytes(StandardCharsets.UTF_8))
            .build()
    );
  }
}
