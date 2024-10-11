package nl.robinthedev.catanjr.game.model.round;

public class NotYourTurnException extends RuntimeException {
  public NotYourTurnException(String message) {
    super(message);
  }
}
