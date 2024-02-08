package ru.shurupov.otus.architecture.game.server.controller.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateGameRequest {
  private List<String> participants;
}
