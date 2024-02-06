package ru.shurupov.otus.architecture.game.server.controller.request;

import lombok.Data;

@Data
public class JoinGameRequest {
  private String gameId;
  private String username;
}
