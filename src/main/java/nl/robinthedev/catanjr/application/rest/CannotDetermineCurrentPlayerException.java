package nl.robinthedev.catanjr.application.rest;

public class CannotDetermineCurrentPlayerException extends RuntimeException {
  public CannotDetermineCurrentPlayerException() {
    super("Turn for current player could not be determined.");
  }
}
