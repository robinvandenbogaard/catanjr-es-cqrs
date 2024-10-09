package nl.robinthedev.catanjr.game.model.resources;

public record Gold(int value) implements Resource {
  public Gold {
    if (value < 0)
      throw new IllegalArgumentException("Gold value must be positive but was " + value);
  }

  public Gold minus(Gold other) {
    return new Gold(value - other.value());
  }

  public Gold add(Gold other) {
    return new Gold(value + other.value());
  }
}
