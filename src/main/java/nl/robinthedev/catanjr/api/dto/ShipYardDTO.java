package nl.robinthedev.catanjr.api.dto;

import java.util.Set;
import nl.robinthedev.catanjr.game.model.board.Board;

public record ShipYardDTO(String id, OwnerDTO owner) {
  public static Set<ShipYardDTO> from2PlayerGame(Board board) {
    return board
        .shipSites()
        .map(
            shipSite -> new ShipYardDTO(shipSite.getBridgeId(), OwnerDTO.from(shipSite.getOwner())))
        .toJavaSet();
  }
}
