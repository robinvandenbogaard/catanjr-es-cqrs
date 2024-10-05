package nl.robinthedev.catanjr.api.query;

import java.util.Objects;
import nl.robinthedev.catanjr.api.dto.GameId;

public record GetGameQuery(GameId gameId) {
  public GetGameQuery {
    Objects.requireNonNull(gameId);
  }
}
