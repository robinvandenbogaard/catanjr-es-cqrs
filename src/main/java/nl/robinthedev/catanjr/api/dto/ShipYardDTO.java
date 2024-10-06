package nl.robinthedev.catanjr.api.dto;

import java.util.List;
import java.util.stream.IntStream;
import nl.robinthedev.catanjr.game.model.SiteId;
import nl.robinthedev.catanjr.game.model.board.Board;

public record ShipYardDTO(String id, OwnerDTO owner) {
  public static List<ShipYardDTO> from2PlayerGame(Board board) {
    return IntStream.rangeClosed(1, 16)
        .mapToObj(i -> new ShipYardDTO(i + "", OwnerDTO.from(board.getOwner(new SiteId(i)))))
        .toList();
  }
}
