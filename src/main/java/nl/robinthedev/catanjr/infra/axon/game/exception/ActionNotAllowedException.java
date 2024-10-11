package nl.robinthedev.catanjr.infra.axon.game.exception;

public class ActionNotAllowedException extends RuntimeException {
  public ActionNotAllowedException(final String message) {
    super(message);
  }
}
