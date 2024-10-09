package nl.robinthedev.catanjr.game.model.resources;

public record Sword(int value) implements Resource {
  public Sword {
    if (value < 0)
      throw new IllegalArgumentException("Sword value must be positive but was " + value);
  }

  public Sword minus(Sword other) {
    return new Sword(value - other.value());
  }

  public Sword add(Sword other) {
    return new Sword(value + other.value());
  }
}
