package nl.robinthedev.catanjr.game.model.player;

public class NotEnoughResources extends RuntimeException {
  public NotEnoughResources(String message, Throwable cause) {
    super(message, cause);
  }
}
