package ru.shurupov.otus.architecture.mq.client.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shurupov.otus.architecture.mq.client.service.MessageService;
import ru.shurupov.otus.architecture.mq.model.GameMessage;

@RequiredArgsConstructor
@RestController
@RequestMapping("/message")
public class MessageController {

  private final MessageService messageService;

  @PostMapping
  public void send(@RequestBody GameMessage message) {
    messageService.send(message);
  }

}
