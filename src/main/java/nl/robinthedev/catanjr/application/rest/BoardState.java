package nl.robinthedev.catanjr.application.rest;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import nl.robinthedev.catanjr.api.dto.OwnerDTO;
import nl.robinthedev.catanjr.api.dto.ShipYardDTO;

class BoardState {
  public static Map<String, String> asMap(List<ShipYardDTO> shipYards) {
    return shipYards.stream()
        .collect(Collectors.toMap(ShipYardDTO::id, item -> toColor(item.owner())));
  }

  private static String toColor(OwnerDTO owner) {
    return switch (owner) {
      case NONE -> "grey";
      case PLAYER1 -> "red";
      case PLAYER2 -> "purple";
      case PLAYER3 -> "yellow";
      case PLAYER4 -> "blue";
    };
  }
}
