package ru.shurupov.otus.architecture.game.server.service;

import java.util.Collection;
import ru.shurupov.otus.architecture.game.server.controller.request.GameCommandMessage;
import ru.shurupov.otus.architecture.game.server.service.dto.GamePlayer;

public interface GameService {

  String addGame(Collection<String> participants);
  boolean containsGame(String gameId);
  boolean containsParticipant(String gameId, String participant);

  void passCommand(GamePlayer player, GameCommandMessage message);
}
