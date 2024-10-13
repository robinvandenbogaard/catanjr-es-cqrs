package nl.robinthedev.catanjr.game.model;

public record ShipId(String value) {
  public ShipId {
    String[] split = value.split("-");
    if (split.length != 2) {
      throw new IllegalArgumentException("Ship id must be seperated between a '-'");
    }

    try {
      Integer.valueOf(split[0]);
      Integer.valueOf(split[1]);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException(
          "Ship id must be two positive integers split by a '-' ", e);
    }
  }
}
