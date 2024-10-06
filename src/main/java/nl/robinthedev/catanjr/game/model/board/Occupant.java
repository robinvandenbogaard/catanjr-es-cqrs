package nl.robinthedev.catanjr.game.model.board;

enum Occupant {
  EMPTY,
  CAPTAIN,
  PLAYER1,
  PLAYER2,
  PLAYER3,
  PLAYER4;

  public static Occupant of(BoardPlayer player) {
    return switch (player) {
      case NONE ->
          throw new IllegalArgumentException("When calculating resources NONE is not an option!");
      case PLAYER1 -> PLAYER1;
      case PLAYER2 -> PLAYER2;
      case PLAYER3 -> PLAYER3;
      case PLAYER4 -> PLAYER4;
    };
  }
}
