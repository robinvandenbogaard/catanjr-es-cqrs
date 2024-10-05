package nl.robinthedev.catanjr.api.dto;

import java.util.Objects;
import java.util.UUID;

public record GameId(UUID value) {
  public GameId {
    Objects.requireNonNull(value);
  }

  public static GameId randomGameId() {
    return new GameId(UUID.randomUUID());
  }

  public static GameId fromString(String gameId) {
    return new GameId(UUID.fromString(gameId));
  }
}
