package nl.robinthedev.catanjr.game.model.resources;

public record Wood(int value) implements Resource {
  public Wood {
    if (value < 0)
      throw new IllegalArgumentException("Wood value must be positive but was " + value);
  }

  public Wood minus(Wood other) {
    return new Wood(value - other.value);
  }

  public Wood add(Wood other) {
    return new Wood(value + other.value);
  }
}
