package ru.shurupov.otus.architecture.game.server;

import ru.shurupov.otus.architecture.game.server.handler.RequestHandler;
import ru.shurupov.otus.architecture.game.server.service.GameServiceMapSetImpl;

public class ServerApp {

  public static void main(String[] args) {
    /*
    * We are placing this implementation, but then we can pass implementation with using IoC
    * */
    new RequestHandler(
        new GameServiceMapSetImpl(),
        "gameserverjwttokensecretgameserverjwttokensecretgameserverjwttokensecretgameserverjwttokensecret",
        120
    ).init();
  }
}