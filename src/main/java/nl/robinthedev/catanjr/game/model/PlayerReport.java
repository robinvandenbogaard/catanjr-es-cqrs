package nl.robinthedev.catanjr.game.model;

import java.util.UUID;
import nl.robinthedev.catanjr.game.model.resources.PlayerInventory;

public record PlayerReport(
    UUID accountId,
    PlayerInventory currentInventory,
    nl.robinthedev.catanjr.game.model.resources.GainedResources gainedResources,
    PlayerInventory newInventory) {
  public boolean inventoryChanged() {
    return !currentInventory.equals(newInventory);
  }
}
