package nl.robinthedev.catanjr.game.model.player;

import java.util.Objects;
import java.util.UUID;

public record PlayerId(AccountId accountId, Username username) {
  public PlayerId {
    Objects.requireNonNull(accountId);
    Objects.requireNonNull(username);
  }

  public static PlayerId from(UUID accountId, String username) {
    return new PlayerId(new AccountId(accountId), new Username(username));
  }
}
