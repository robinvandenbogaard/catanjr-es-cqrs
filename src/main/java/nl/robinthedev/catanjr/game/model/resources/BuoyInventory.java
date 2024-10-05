package nl.robinthedev.catanjr.game.model.resources;

import java.util.Objects;

public record BuoyInventory(Wood wood, Gold gold, PineApple pineApple, Sheep sheep, Sword sword) {
  public static final BuoyInventory INITIAL = BuoyInventory.of(1, 1, 1, 1, 1);

  public BuoyInventory {
    Objects.requireNonNull(wood);
    Objects.requireNonNull(gold);
    Objects.requireNonNull(pineApple);
    Objects.requireNonNull(sheep);
    Objects.requireNonNull(sword);
  }

  public static BuoyInventory of(int wood, int gold, int pineApple, int sheep, int sword) {
    return new BuoyInventory(
        new Wood(wood),
        new Gold(gold),
        new PineApple(pineApple),
        new Sheep(sheep),
        new Sword(sword));
  }
}
