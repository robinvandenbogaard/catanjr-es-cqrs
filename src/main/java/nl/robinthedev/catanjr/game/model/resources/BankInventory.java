package nl.robinthedev.catanjr.game.model.resources;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import nl.robinthedev.catanjr.game.model.board.ResourceType;

public record BankInventory(Wood wood, Gold gold, PineApple pineApple, Sheep sheep, Sword sword)
    implements ResourceCollection {
  public static final BankInventory FULL = BankInventory.of(18, 18, 18, 18, 18);
  public static final BankInventory EMPTY = BankInventory.of(0, 0, 0, 0, 0);
  ;

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

  public BankInventory minus(ResourceCollection inventory) {
    return new BankInventory(
        wood.minus(inventory.wood()),
        gold.minus(inventory.gold()),
        pineApple.minus(inventory.pineApple()),
        sheep.minus(inventory.sheep()),
        sword.minus(inventory.sword()));
  }

  public BankInventory minus(ResourceChanges inventory) {
    return new BankInventory(
        wood.minus(inventory.wood()),
        gold.minus(inventory.gold()),
        pineApple.minus(inventory.pineApple()),
        sheep.minus(inventory.sheep()),
        sword.minus(inventory.sword()));
  }

  public Set<ResourceType> getExceedingResources(ResourceChanges totalResources) {
    var result = new HashSet<ResourceType>();
    wood().ifNotReducable(totalResources.wood(), () -> result.add(ResourceType.WOOD));
    gold().ifNotReducable(totalResources.gold(), () -> result.add(ResourceType.GOLD));
    sword().ifNotReducable(totalResources.sword(), () -> result.add(ResourceType.SWORD));
    pineApple()
        .ifNotReducable(totalResources.pineApple(), () -> result.add(ResourceType.PINEAPPLE));
    sheep().ifNotReducable(totalResources.sheep(), () -> result.add(ResourceType.SHEEP));
    return result;
  }
}
