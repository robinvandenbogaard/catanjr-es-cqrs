package nl.robinthedev.catanjr.game.model;

import nl.robinthedev.catanjr.game.model.resources.PlayerInventory;

public record BankReport(PlayerInventory currentInventory, PlayerInventory newInventory) {
  public boolean inventoryChanged() {
    return !currentInventory.equals(newInventory);
  }
}
