package nl.robinthedev.catanjr.game.model.resources;

public record Sheep(int value) implements Resource {
  public Sheep {
    if (value < 0) throw new IllegalArgumentException("Sheep value must be positive");
  }

  public Sheep minus(Sheep other) {
    return new Sheep(value - other.value);
  }

  public Sheep minus(Integer amount) {
    return new Sheep(value - amount);
  }

  public Sheep add(Sheep other) {
    return new Sheep(value + other.value);
  }

  public Sheep add(Integer amount) {
    return new Sheep(value + amount);
  }
}
