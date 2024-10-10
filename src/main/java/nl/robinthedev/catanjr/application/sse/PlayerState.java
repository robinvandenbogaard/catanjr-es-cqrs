package nl.robinthedev.catanjr.application.sse;

import nl.robinthedev.catanjr.api.dto.InventoryDTO;
import nl.robinthedev.catanjr.api.dto.PlayerDTO;

record PlayerState(String username, Actions actions, InventoryDTO inventory) {
  public static PlayerState of(PlayerDTO playerDTO) {
    return new PlayerState(
        playerDTO.username(), Actions.of(playerDTO.actions()), playerDTO.inventory());
  }
}
