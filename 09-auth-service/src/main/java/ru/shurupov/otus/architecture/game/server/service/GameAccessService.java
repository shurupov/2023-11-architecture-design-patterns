package ru.shurupov.otus.architecture.game.server.service;

import static spark.Spark.halt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.shurupov.otus.architecture.game.server.controller.request.CreateGameRequest;
import ru.shurupov.otus.architecture.game.server.controller.request.GameCommandMessage;
import ru.shurupov.otus.architecture.game.server.controller.request.JoinGameRequest;
import ru.shurupov.otus.architecture.game.server.controller.response.AddCommandResponse;
import ru.shurupov.otus.architecture.game.server.controller.response.JoinGameResponse;
import ru.shurupov.otus.architecture.game.server.service.dto.GamePlayer;

@Slf4j
@RequiredArgsConstructor
public class GameAccessService {

  private final GameService gameService;
  private final JwtService jwtService;

  public String createGame(CreateGameRequest createGameRequest) {
    return gameService.addGame(createGameRequest.getParticipants());
  }

  public JoinGameResponse joinGame(JoinGameRequest joinGameRequest) {
    checkGameAvailability(joinGameRequest.getGameId(), joinGameRequest.getUsername());
    String token = jwtService.generateToken(joinGameRequest);
    return new JoinGameResponse(token);
  }

  public AddCommandResponse addCommand(GamePlayer player, GameCommandMessage command) {
    checkGameAvailability(player.getGameId(), player.getUsername());
    gameService.passCommand(player, command);
    return new AddCommandResponse(String.format("Added command %s for user %s", command, player.getUsername()));
  }

  private void checkGameAvailability(String gameId, String username) {
    if (!gameService.containsGame(gameId)) {
      halt(500, "There is no game with this id");
    }
    if (!gameService.containsParticipant(gameId, username)) {
      halt(403, "You are not included to participants");
    }
  }
}
