package nl.robinthedev.catanjr.game.model;

import java.util.UUID;
import nl.robinthedev.catanjr.game.model.resources.PlayerInventory;
import nl.robinthedev.catanjr.game.model.resources.ResourceChanges;

public record PlayerReport(
    UUID accountId,
    PlayerInventory currentInventory,
    ResourceChanges resourceChanges,
    PlayerInventory newInventory) {
  public boolean inventoryChanged() {
    return !currentInventory.equals(newInventory);
  }
}
