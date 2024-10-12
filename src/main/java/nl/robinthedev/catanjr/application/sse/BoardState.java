package nl.robinthedev.catanjr.application.sse;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import nl.robinthedev.catanjr.api.dto.FortSiteDTO;
import nl.robinthedev.catanjr.api.dto.OwnerDTO;

class BoardState {
  public static Map<String, String> asMap(List<FortSiteDTO> fortSites) {
    return fortSites.stream()
        .collect(
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
