package nl.robinthedev.catanjr.game.model.player;

import java.util.Objects;
import nl.robinthedev.catanjr.game.model.resources.PlayerInventory;

public record Player(PlayerId id, PlayerInventory inventory) {
  public Player {
    Objects.requireNonNull(id);
    Objects.requireNonNull(inventory);
  }

  public Player(PlayerId id) {
    this(id, PlayerInventory.INITIAL);
  }

  public static Player of(PlayerId playerId) {
    return new Player(playerId);
  }
}
