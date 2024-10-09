package nl.robinthedev.catanjr.game.model.resources;

public record Gold(int value) implements Resource {
  public Gold {
    if (value < 0) throw new IllegalArgumentException("Gold value must be positive");
  }

  public Gold minus(Gold other) {
    return new Gold(value - other.value);
  }

  public Gold minus(Integer amount) {
    return new Gold(value - amount);
  }

  public Gold add(Gold other) {
    return new Gold(value + other.value);
  }

  public Gold add(Integer amount) {
    return new Gold(value + amount);
  }
}
