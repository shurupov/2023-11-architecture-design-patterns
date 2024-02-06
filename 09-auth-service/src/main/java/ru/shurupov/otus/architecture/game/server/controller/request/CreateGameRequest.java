package ru.shurupov.otus.architecture.game.server.controller.request;

import java.util.List;
import lombok.Data;

@Data
public class CreateGameRequest {
  private List<String> participants;
}
