package ru.shurupov.otus.architecture.game.server.controller;

import lombok.RequiredArgsConstructor;
import ru.shurupov.otus.architecture.game.server.controller.request.CreateGameRequest;
import ru.shurupov.otus.architecture.game.server.controller.request.GameCommandMessage;
import ru.shurupov.otus.architecture.game.server.controller.request.JoinGameRequest;
import ru.shurupov.otus.architecture.game.server.controller.response.AddCommandResponse;
import ru.shurupov.otus.architecture.game.server.controller.response.CreateGameResponse;
import ru.shurupov.otus.architecture.game.server.controller.response.JoinGameResponse;
import ru.shurupov.otus.architecture.game.server.service.GameAccessService;
import ru.shurupov.otus.architecture.game.server.service.dto.GamePlayer;

@RequiredArgsConstructor
public class GameController {

  private final GameAccessService gameAccessService;
  public CreateGameResponse createGame(CreateGameRequest createGameRequest) {
    return gameAccessService.createGame(createGameRequest);
  }

  public JoinGameResponse joinGame(JoinGameRequest joinGameRequest) {
    return gameAccessService.joinGame(joinGameRequest);
  }

  public AddCommandResponse addCommandMessage(GamePlayer player, GameCommandMessage gameCommandMessage) {
    return gameAccessService.addCommand(player, gameCommandMessage);
  }
}
