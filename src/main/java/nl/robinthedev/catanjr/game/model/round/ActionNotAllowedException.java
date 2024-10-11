package nl.robinthedev.catanjr.game.model.round;

public class ActionNotAllowedException extends RuntimeException {
  public ActionNotAllowedException(final String message) {
    super(message);
  }
}
