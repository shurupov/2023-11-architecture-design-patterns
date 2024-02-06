package ru.shurupov.otus.architecture.game.server.handler;

import static spark.Spark.post;

import com.google.gson.Gson;
import java.util.function.BiFunction;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import ru.shurupov.otus.architecture.game.server.controller.GameController;
import ru.shurupov.otus.architecture.game.server.controller.request.CreateGameRequest;
import ru.shurupov.otus.architecture.game.server.controller.request.GameCommandMessage;
import ru.shurupov.otus.architecture.game.server.controller.request.JoinGameRequest;
import ru.shurupov.otus.architecture.game.server.service.GameService;
import ru.shurupov.otus.architecture.game.server.service.JwtService;
import spark.Request;

@RequiredArgsConstructor
public class RequestHandler {

  private final Gson gson = new Gson();

  public void init() {

    GameController gameController = new GameController(new GameService(new JwtService()));

    post("/create", (req, res) -> handle(req, gameController::createGame, CreateGameRequest.class));
    post("/join", (req, res) -> handle(req, gameController::joinGame, JoinGameRequest.class));
    post("/commands", (req, res) -> securedHandle(req, gameController::addCommandMessage, GameCommandMessage.class));
  }

  private <R> Object handle(Request request, Function<R, ?> method, Class<R> rclass) {
    R parsedRequest = gson.fromJson(request.body(), rclass);
    Object response = method.apply(parsedRequest);
    return gson.toJson(response);
  }

  private <R> Object securedHandle(Request request, BiFunction<String, R, ?> method, Class<R> rclass) {
    R parsedRequest = gson.fromJson(request.body(), rclass);
    String jwtToken = request.headers("x-api-token");
    Object response = method.apply(jwtToken, parsedRequest);
    return gson.toJson(response);
  }
}
