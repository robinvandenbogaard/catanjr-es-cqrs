package nl.robinthedev.catanjr.api.dto;

import io.vavr.collection.Set;
import nl.robinthedev.catanjr.game.model.board.Board;

public record ShipYardDTO(String id, OwnerDTO owner) {
  public static Set<ShipYardDTO> from2PlayerGame(Board board) {
    return board
        .shipSites()
        .map(
            shipSite ->
                new ShipYardDTO(shipSite.getShipId().value(), OwnerDTO.from(shipSite.getOwner())))
        .toSet();
  }
}
