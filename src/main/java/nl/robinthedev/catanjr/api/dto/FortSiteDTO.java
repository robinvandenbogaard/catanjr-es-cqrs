package nl.robinthedev.catanjr.api.dto;

import java.util.List;
import java.util.stream.IntStream;
import nl.robinthedev.catanjr.game.model.SiteId;
import nl.robinthedev.catanjr.game.model.board.Board;

public record FortSiteDTO(String id, OwnerDTO owner) {
  public static List<FortSiteDTO> from2PlayerGame(Board board) {
    return IntStream.rangeClosed(1, 16)
        .mapToObj(i -> new FortSiteDTO(i + "", OwnerDTO.from(board.getFortOwner(new SiteId(i)))))
        .toList();
  }
}
