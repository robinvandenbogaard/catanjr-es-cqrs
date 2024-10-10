package nl.robinthedev.catanjr.api.dto;

import java.util.Set;
import java.util.UUID;
import nl.robinthedev.catanjr.game.model.player.Player;

public record PlayerDTO(
    UUID accountId, String username, InventoryDTO inventory, Set<ActionDTO> actions) {
  public static PlayerDTO of(Player player) {
    return new PlayerDTO(
        player.id().accountId().value(),
        player.id().username().value(),
        InventoryDTO.of(player.inventory()),
        Set.of());
  }

  public PlayerDTO updateInventory(InventoryDTO newInventory) {
    return new PlayerDTO(accountId, username, newInventory, actions);
  }

  public PlayerDTO updateActions(Set<ActionDTO> newActions) {
    return new PlayerDTO(accountId, username, inventory, newActions);
  }
}
