package ru.shurupov.otus.architecture.game.server.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.shurupov.otus.architecture.game.server.controller.request.CreateGameRequest;
import ru.shurupov.otus.architecture.game.server.controller.request.GameCommandMessage;
import ru.shurupov.otus.architecture.game.server.controller.request.JoinGameRequest;
import ru.shurupov.otus.architecture.game.server.controller.response.CreateGameResponse;
import ru.shurupov.otus.architecture.game.server.controller.response.JoinGameResponse;
import ru.shurupov.otus.architecture.game.server.service.dto.GamePlayer;

@ExtendWith(MockitoExtension.class)
class GameAccessServiceTest {

  @Mock
  private GameService gameService;
  @Mock
  private JwtService jwtService;

  private GameAccessService gameAccessService;

  @BeforeEach
  public void init() {
    gameAccessService = new GameAccessService(gameService, jwtService);
  }

  @Test
  void givenParticipants_whenCreateGame_thenCreatedAndIdReturned() {
    CreateGameRequest createGameRequest = new CreateGameRequest(
        List.of("player1", "player2")
    );

    when(gameService.addGame(eq(List.of("player1", "player2")))).thenReturn("12345");

    CreateGameResponse game = gameAccessService.createGame(createGameRequest);

    assertThat(game.getGameId()).isEqualTo("12345");

    verify(gameService, times(1)).addGame(eq(List.of("player1", "player2")));
  }

  @Test
  void givenRequest_whenJoinGame_thenJwtReturned() {
    JoinGameRequest request = new JoinGameRequest("gameId", "username");

    when(gameService.containsGame("gameId")).thenReturn(true);
    when(gameService.containsParticipant("gameId", "username")).thenReturn(true);
    when(jwtService.generateToken(eq(request))).thenReturn("test_jwt");

    JoinGameResponse joinGameResponse = gameAccessService.joinGame(request);

    assertThat(joinGameResponse.getToken()).isEqualTo("test_jwt");
  }

  @Test
  void givenPlayerAndMessage_whenAddCommand_thenAdded() {
    GamePlayer player = new GamePlayer("gameIdtest", "username_test");
    GameCommandMessage message = new GameCommandMessage("payload");

    when(gameService.containsGame("gameIdtest")).thenReturn(true);
    when(gameService.containsParticipant("gameIdtest", "username_test")).thenReturn(true);

    gameAccessService.addCommand(player, message);

    verify(gameService, times(1)).passCommand(eq(player), eq(message));
  }
}