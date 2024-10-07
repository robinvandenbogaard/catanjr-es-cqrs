package nl.robinthedev.catanjr.api.dto;

import java.util.UUID;
import nl.robinthedev.catanjr.game.model.player.Player;

public record PlayerDTO(UUID accountId, String username, InventoryDTO inventory) {
  public static PlayerDTO of(Player player) {
    return new PlayerDTO(
        player.id().accountId().value(),
        player.id().username().value(),
        InventoryDTO.of(player.inventory()));
  }

  public PlayerDTO updateInventory(InventoryDTO newInventory) {
    return new PlayerDTO(accountId, username, newInventory);
  }
}
