package nl.robinthedev.catanjr.api.dto;

import io.vavr.collection.List;
import io.vavr.collection.Stream;
import nl.robinthedev.catanjr.game.model.SiteId;
import nl.robinthedev.catanjr.game.model.board.Board;

public record FortSiteDTO(int id, OwnerDTO owner) {
  public static List<FortSiteDTO> from2PlayerGame(Board board) {
    return Stream.rangeClosed(1, 16)
        .map(i -> new FortSiteDTO(i, OwnerDTO.from(board.getFortOwner(new SiteId(i)))))
        .toList();
  }
}
