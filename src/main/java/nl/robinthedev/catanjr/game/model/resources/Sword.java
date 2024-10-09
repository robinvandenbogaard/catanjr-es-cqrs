package nl.robinthedev.catanjr.game.model.resources;

public record Sword(int value) implements Resource {
  public Sword {
    if (value < 0) throw new IllegalArgumentException("Sword value must be positive");
  }

  public Sword minus(Sword other) {
    return new Sword(value - other.value);
  }

  public Sword minus(Integer amount) {
    return new Sword(value - amount);
  }

  public Sword add(Sword other) {
    return new Sword(value + other.value);
  }

  public Sword add(Integer amount) {
    return new Sword(value + amount);
  }
}
