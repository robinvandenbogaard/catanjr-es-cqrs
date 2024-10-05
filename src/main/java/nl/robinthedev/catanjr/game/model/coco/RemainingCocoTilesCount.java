package nl.robinthedev.catanjr.game.model.coco;

public record RemainingCocoTilesCount(int value) {
  public RemainingCocoTilesCount {
    if (value < 0) throw new IllegalArgumentException("value cannot be negative");
  }
}
