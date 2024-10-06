package nl.robinthedev.catanjr.game.model.resources;

import java.util.Objects;

public record GainedResources(Wood wood, Gold gold, PineApple pineApple, Sheep sheep, Sword sword)
    implements ResourceCollection {
  public static final GainedResources EMPTY = GainedResources.of(0, 0, 0, 0, 0);

  public GainedResources {
    Objects.requireNonNull(wood);
    Objects.requireNonNull(gold);
    Objects.requireNonNull(pineApple);
    Objects.requireNonNull(sheep);
    Objects.requireNonNull(sword);
  }

  public static GainedResources of(int wood, int gold, int pineApple, int sheep, int sword) {
    return new GainedResources(
        new Wood(wood),
        new Gold(gold),
        new PineApple(pineApple),
        new Sheep(sheep),
        new Sword(sword));
  }

  public static GainedResources swords(int swords) {
    return GainedResources.of(0, 0, 0, 0, swords);
  }

  public static GainedResources gold(int gold) {
    return GainedResources.of(0, gold, 0, 0, 0);
  }

  public static GainedResources pineApple(int pineApple) {
    return GainedResources.of(0, 0, pineApple, 0, 0);
  }

  public static GainedResources sheep(int sheep) {
    return GainedResources.of(0, 0, 0, sheep, 0);
  }

  public static GainedResources wood(int wood) {
    return GainedResources.of(wood, 0, 0, 0, 0);
  }

  public GainedResources add(ResourceCollection other) {
    return new GainedResources(
        wood.add(other.wood()),
        gold.add(other.gold()),
        pineApple.add(other.pineApple()),
        sheep.add(other.sheep()),
        sword.add(other.sword()));
  }
}
