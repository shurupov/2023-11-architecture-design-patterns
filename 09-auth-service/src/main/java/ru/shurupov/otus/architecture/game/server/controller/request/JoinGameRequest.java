package ru.shurupov.otus.architecture.game.server.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JoinGameRequest {
  private String gameId;
  private String username;
}
