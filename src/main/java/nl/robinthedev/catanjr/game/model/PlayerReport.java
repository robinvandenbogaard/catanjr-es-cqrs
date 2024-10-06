package nl.robinthedev.catanjr.game.model;

import java.util.UUID;
import nl.robinthedev.catanjr.game.model.resources.PlayerInventory;

public record PlayerReport(
    UUID accountId, PlayerInventory currentInventory, PlayerInventory newInventory) {
  public boolean inventoryChanged() {
    return !currentInventory.equals(newInventory);
  }
}
