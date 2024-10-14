package nl.robinthedev.catanjr.game.service;

import io.vavr.control.Option;
import java.util.UUID;
import nl.robinthedev.catanjr.api.dto.GameId;

public interface PlayerTurn {
  Option<UUID> getCurrentPlayerAccount(GameId gameId);

  void currentPlayerChangedTo(GameId gameId, UUID uuid);
}
