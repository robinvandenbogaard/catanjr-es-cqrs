package nl.robinthedev.catanjr.api.dto;

import nl.robinthedev.catanjr.game.model.resources.Gold;
import nl.robinthedev.catanjr.game.model.resources.PineApple;
import nl.robinthedev.catanjr.game.model.resources.ResourceCollection;
import nl.robinthedev.catanjr.game.model.resources.Sheep;
import nl.robinthedev.catanjr.game.model.resources.Sword;
import nl.robinthedev.catanjr.game.model.resources.Wood;

public record InventoryDTO(int gold, int pineApple, int wood, int sheep, int sword) {
  private InventoryDTO(Gold gold, PineApple pineApple, Wood wood, Sheep sheep, Sword sword) {
    this(gold.value(), pineApple.value(), wood.value(), sheep.value(), sword.value());
  }

  public static InventoryDTO of(ResourceCollection inventory) {
    return new InventoryDTO(
        inventory.gold(),
        inventory.pineApple(),
        inventory.wood(),
        inventory.sheep(),
        inventory.sword());
  }
}
