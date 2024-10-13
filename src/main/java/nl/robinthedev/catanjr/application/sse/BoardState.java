package nl.robinthedev.catanjr.application.sse;

import io.vavr.collection.List;
import io.vavr.collection.Set;
import java.util.Map;
import java.util.stream.Collectors;
import nl.robinthedev.catanjr.api.dto.FortSiteDTO;
import nl.robinthedev.catanjr.api.dto.OwnerDTO;
import nl.robinthedev.catanjr.api.dto.ShipYardDTO;

class BoardState {
  public static Map<String, String> asFortSiteColors(List<FortSiteDTO> fortSites) {
    return fortSites.collect(
        Collectors.toMap(item -> String.valueOf(item.id()), item -> toColor(item.owner())));
  }

  public static Map<String, String> asShipYardColors(Set<ShipYardDTO> shipYards) {
    return shipYards.collect(
        Collectors.toMap(item -> String.valueOf(item.id()), item -> toColor(item.owner())));
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
