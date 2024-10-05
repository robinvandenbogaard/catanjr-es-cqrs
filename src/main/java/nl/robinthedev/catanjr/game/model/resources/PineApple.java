package nl.robinthedev.catanjr.game.model.resources;

public record PineApple(int value) {
  public PineApple {
    if (value < 0) throw new IllegalArgumentException("PineApple value must be positive");
  }

  public PineApple minus(PineApple other) {
    return new PineApple(value - other.value);
  }
}