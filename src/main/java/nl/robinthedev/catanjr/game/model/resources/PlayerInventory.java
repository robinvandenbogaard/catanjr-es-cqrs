package nl.robinthedev.catanjr.game.model.resources;

import java.util.Objects;

public record PlayerInventory(Wood wood, Gold gold, PineApple pineApple, Sheep sheep, Sword sword)
    implements ResourceCollection {
  public PlayerInventory {
    Objects.requireNonNull(wood);
    Objects.requireNonNull(gold);
    Objects.requireNonNull(pineApple);
    Objects.requireNonNull(sheep);
    Objects.requireNonNull(sword);
  }

  public static final PlayerInventory INITIAL = PlayerInventory.of(1, 0, 0, 0, 1);

  public static PlayerInventory of(int wood, int gold, int pineApple, int sheep, int sword) {
    return new PlayerInventory(
        new Wood(wood),
        new Gold(gold),
        new PineApple(pineApple),
        new Sheep(sheep),
        new Sword(sword));
  }

  public PlayerInventory add(ResourceChanges changes) {
    return new PlayerInventory(
        wood.add(changes.asWood()),
        gold.add(changes.asGold()),
        pineApple.add(changes.asPineApple()),
        sheep.add(changes.asSheep()),
        sword.add(changes.asSword()));
  }

  public PlayerInventory minus(ResourceChanges changes) {
    return new PlayerInventory(
        wood.minus(changes.asWood()),
        gold.minus(changes.asGold()),
        pineApple.minus(changes.asPineApple()),
        sheep.minus(changes.asSheep()),
        sword.minus(changes.asSword()));
  }
}
