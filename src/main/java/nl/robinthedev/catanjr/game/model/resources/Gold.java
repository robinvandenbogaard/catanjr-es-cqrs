package nl.robinthedev.catanjr.game.model.resources;

public record Gold(int value) {
  public Gold {
    if (value < 0) throw new IllegalArgumentException("Gold value must be positive");
  }

  public Gold minus(Gold other) {
    return new Gold(value - other.value);
  }
}
