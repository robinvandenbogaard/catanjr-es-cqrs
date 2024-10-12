package nl.robinthedev.catanjr.infra.axon.game;

public class NotEnoughResources extends RuntimeException {
  public NotEnoughResources(String message, Throwable cause) {
    super(message, cause);
  }
}
