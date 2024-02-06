package ru.shurupov.otus.architecture.game.server.controller;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import ru.shurupov.otus.architecture.game.server.controller.request.CreateGameRequest;
import ru.shurupov.otus.architecture.game.server.controller.request.GameCommandMessage;
import ru.shurupov.otus.architecture.game.server.controller.request.JoinGameRequest;
import ru.shurupov.otus.architecture.game.server.controller.response.AddCommandResponse;
import ru.shurupov.otus.architecture.game.server.controller.response.JoinGameResponse;
import ru.shurupov.otus.architecture.game.server.service.GameService;

@RequiredArgsConstructor
public class GameController {

  private final GameService gameService;
  public String createGame(CreateGameRequest createGameRequest) {
    return RandomStringUtils.random(10, true, true);
  }

  public JoinGameResponse joinGame(JoinGameRequest joinGameRequest) {
    return gameService.joinGame(joinGameRequest);
  }

  public AddCommandResponse addCommandMessage(String token, GameCommandMessage gameCommandMessage) {
    return gameService.addCommand(token, gameCommandMessage);
  }
}
