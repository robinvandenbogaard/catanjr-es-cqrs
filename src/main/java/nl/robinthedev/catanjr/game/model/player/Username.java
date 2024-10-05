package nl.robinthedev.catanjr.game.model.player;

import java.util.Objects;

public record Username(String value) {
  public Username {
    Objects.requireNonNull(value);
    if (value.trim().length() < 3)
      throw new IllegalArgumentException("Username must be at least 3 characters");
  }
}
