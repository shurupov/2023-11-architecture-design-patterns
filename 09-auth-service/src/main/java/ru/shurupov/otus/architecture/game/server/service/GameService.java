package ru.shurupov.otus.architecture.game.server.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.shurupov.otus.architecture.game.server.controller.request.GameCommandMessage;
import ru.shurupov.otus.architecture.game.server.controller.request.JoinGameRequest;
import ru.shurupov.otus.architecture.game.server.controller.response.AddCommandResponse;
import ru.shurupov.otus.architecture.game.server.controller.response.JoinGameResponse;

@Slf4j
@RequiredArgsConstructor
public class GameService {

  private final JwtService jwtService;

  public JoinGameResponse joinGame(JoinGameRequest joinGameRequest) {
    /*
    * TODO Check whether game exists and you are in participants
    * */
    String token = jwtService.generateToken(joinGameRequest.getUsername());
    return new JoinGameResponse(token);
  }

  public AddCommandResponse addCommand(String token, GameCommandMessage command) {
    String username = jwtService.getUsername(token);
    log.info("User: {}, Comamnd: {}", username, command);
    return new AddCommandResponse(String.format("Added command %s for user %s", command, username));
  }
}
