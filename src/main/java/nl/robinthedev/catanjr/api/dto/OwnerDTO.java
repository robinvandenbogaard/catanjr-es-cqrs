package nl.robinthedev.catanjr.api.dto;

import nl.robinthedev.catanjr.game.model.board.BoardPlayer;

public enum OwnerDTO {
  NONE,
  PLAYER1,
  PLAYER2,
  PLAYER3,
  PLAYER4;

  public static OwnerDTO from(BoardPlayer owner) {
    return OwnerDTO.valueOf(owner.name());
  }
}
