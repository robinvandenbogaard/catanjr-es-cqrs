package nl.robinthedev.catanjr.game.model.player;

import java.util.Objects;
import java.util.UUID;
import nl.robinthedev.catanjr.game.model.board.BoardPlayer;
import nl.robinthedev.catanjr.game.model.resources.PlayerInventory;
import nl.robinthedev.catanjr.game.model.resources.ResourceChanges;

public record Player(PlayerId id, PlayerInventory inventory, BoardPlayer nr) {
  public Player {
    Objects.requireNonNull(id);
    Objects.requireNonNull(inventory);
  }

  public Player(PlayerId id, BoardPlayer nr) {
    this(id, PlayerInventory.INITIAL, nr);
  }

  public static Player of(PlayerId playerId, BoardPlayer nr) {
    return new Player(playerId, nr);
  }

  public UUID accountId() {
    return id.accountId().value();
  }

  public boolean belongsTo(AccountId accountId) {
    return id.accountId().equals(accountId);
  }

  public Player setInventory(PlayerInventory inventory) {
    return new Player(id, inventory, nr);
  }

  public void mustBeAbleToPay(ResourceChanges costs) {
    try {
      inventory.minus(costs);
    } catch (IllegalArgumentException e) {
      throw new NotEnoughResources("You do not have enough resources to pay " + costs, e);
    }
  }
}
