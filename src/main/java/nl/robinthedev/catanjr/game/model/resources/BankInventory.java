package nl.robinthedev.catanjr.game.model.resources;

import io.vavr.Tuple;
import io.vavr.collection.HashSet;
import io.vavr.collection.Set;
import java.util.Objects;
import nl.robinthedev.catanjr.game.model.board.ResourceType;

public record BankInventory(Wood wood, Gold gold, PineApple pineApple, Sheep sheep, Sword sword)
    implements ResourceCollection {
  public static final BankInventory FULL = BankInventory.of(18, 18, 18, 18, 18);
  public static final BankInventory EMPTY = BankInventory.of(0, 0, 0, 0, 0);

  public BankInventory {
    Objects.requireNonNull(wood);
    Objects.requireNonNull(gold);
    Objects.requireNonNull(pineApple);
    Objects.requireNonNull(sheep);
    Objects.requireNonNull(sword);
  }

  public static BankInventory of(int wood, int gold, int pineApple, int sheep, int sword) {
    return new BankInventory(
        new Wood(wood),
        new Gold(gold),
        new PineApple(pineApple),
        new Sheep(sheep),
        new Sword(sword));
  }

  public BankInventory minus(ResourceChanges changes) {
    return new BankInventory(
        wood.minus(changes.asWood()),
        gold.minus(changes.asGold()),
        pineApple.minus(changes.asPineApple()),
        sheep.minus(changes.asSheep()),
        sword.minus(changes.asSword()));
  }

  public BankInventory add(ResourceChanges changes) {
    return new BankInventory(
        wood.add(changes.asWood()),
        gold.add(changes.asGold()),
        pineApple.add(changes.asPineApple()),
        sheep.add(changes.asSheep()),
        sword.add(changes.asSword()));
  }

  public Set<ResourceType> getExceedingResources(ResourceChanges requested) {
    return HashSet.of(
            Tuple.of(wood().lt(requested.wood()), ResourceType.WOOD),
            Tuple.of(gold().lt(requested.gold()), ResourceType.GOLD),
            Tuple.of(sword().lt(requested.sword()), ResourceType.SWORD),
            Tuple.of(pineApple().lt(requested.pineApple()), ResourceType.PINEAPPLE),
            Tuple.of(sheep().lt(requested.sheep()), ResourceType.SHEEP))
        .foldLeft(HashSet.empty(), (result, tuple) -> tuple._1 ? result.add(tuple._2) : result);
  }
}
