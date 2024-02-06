package ru.shurupov.otus.architecture.game.server;

import ru.shurupov.otus.architecture.game.server.handler.RequestHandler;

public class ServerApp {

  public static void main(String[] args) {
    new RequestHandler().init();
  }
}