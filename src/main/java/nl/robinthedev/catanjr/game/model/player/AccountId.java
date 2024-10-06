package nl.robinthedev.catanjr.game.model.player;

import java.util.Objects;
import java.util.UUID;

public record AccountId(UUID value) {
  public AccountId {
    Objects.requireNonNull(value);
  }

  public static AccountId of(UUID uuid) {
    return new AccountId(uuid);
  }
}
