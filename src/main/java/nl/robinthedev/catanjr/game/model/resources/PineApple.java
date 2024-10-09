package nl.robinthedev.catanjr.game.model.resources;

public record PineApple(int value) implements Resource {
  public PineApple {
    if (value < 0)
      throw new IllegalArgumentException("PineApple value must be positive but was " + value);
  }

  public PineApple minus(PineApple other) {
    return new PineApple(value - other.value());
  }

  public PineApple add(PineApple other) {
    return new PineApple(value + other.value);
  }
}
