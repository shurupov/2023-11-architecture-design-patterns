package ru.shurupov.otus.architecture.mq.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shurupov.otus.architecture.mq.service.MessageService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/message")
public class MessageController {

  private final MessageService messageService;

  @PostMapping
  public void send(@RequestBody String message) {
    messageService.send(message);
  }

}
