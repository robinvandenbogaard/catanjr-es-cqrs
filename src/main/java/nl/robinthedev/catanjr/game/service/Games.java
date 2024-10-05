package nl.robinthedev.catanjr.game.service;

import nl.robinthedev.catanjr.api.dto.GameDTO;
import nl.robinthedev.catanjr.api.dto.GameId;

public interface Games {
  void save(GameId gameId, GameDTO game);

  GameDTO get(GameId gameId);
}
