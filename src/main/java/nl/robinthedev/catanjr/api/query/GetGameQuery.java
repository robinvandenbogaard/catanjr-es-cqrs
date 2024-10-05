package nl.robinthedev.catanjr.api.query;

import nl.robinthedev.catanjr.api.dto.GameId;

import java.util.Objects;

public record GetGameQuery(GameId gameId) {
  public GetGameQuery {
    Objects.requireNonNull(gameId);
  }
}
