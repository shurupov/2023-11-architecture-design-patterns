package ru.shurupov.otus.architecture.game.server.handler;

import static spark.Spark.halt;
import static spark.Spark.post;

import com.google.gson.Gson;
import java.util.function.BiFunction;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import ru.shurupov.otus.architecture.game.server.controller.GameController;
import ru.shurupov.otus.architecture.game.server.controller.request.CreateGameRequest;
import ru.shurupov.otus.architecture.game.server.controller.request.GameCommandMessage;
import ru.shurupov.otus.architecture.game.server.controller.request.JoinGameRequest;
import ru.shurupov.otus.architecture.game.server.service.GameAccessService;
import ru.shurupov.otus.architecture.game.server.service.GameService;
import ru.shurupov.otus.architecture.game.server.service.JwtService;
import ru.shurupov.otus.architecture.game.server.service.dto.GamePlayer;
import spark.Request;

@Slf4j
public class RequestHandler {

  private final Gson gson;
  private final JwtService jwtService;
  private final GameController gameController;

  public RequestHandler(GameService gameService) {
    gson = new Gson();
    jwtService = new JwtService();
    GameAccessService gameAccessService = new GameAccessService(gameService, jwtService);
    gameController = new GameController(gameAccessService);
  }

  public void init() {
    post("/create", (req, res) -> handle(req, gameController::createGame, CreateGameRequest.class));
    post("/join", (req, res) -> handle(req, gameController::joinGame, JoinGameRequest.class));
    post("/commands", (req, res) -> securedHandle(req, gameController::addCommandMessage, GameCommandMessage.class));
  }

  private <R> Object handle(Request request, Function<R, ?> method, Class<R> rclass) {
    Object response = method.apply(parseRequest(request, rclass));
    return gson.toJson(response);
  }

  private <R> Object securedHandle(Request request, BiFunction<GamePlayer, R, ?> method, Class<R> rclass) {
    String jwtToken = request.headers("x-api-token");
    Object response = method.apply(jwtService.getPlayer(jwtToken), parseRequest(request, rclass));
    return gson.toJson(response);
  }

  private <R> R parseRequest(Request request, Class<R> rclass) {
    try {
      return gson.fromJson(request.body(), rclass);
    } catch (Throwable e) {
      log.error("Parsing exception", e);
      halt(400, "Wrong request");
      return null;
    }
  }
}
