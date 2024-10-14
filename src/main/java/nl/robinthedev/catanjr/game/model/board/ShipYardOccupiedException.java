package nl.robinthedev.catanjr.game.model.board;

public class ShipYardOccupiedException extends RuntimeException {
  public ShipYardOccupiedException(String message) {
    super(message);
  }
}
