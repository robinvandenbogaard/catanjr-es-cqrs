package nl.robinthedev.catanjr.game.model.resources;

import java.util.Objects;

public record BankInventory(Wood wood, Gold gold, PineApple pineApple, Sheep sheep, Sword sword) {
  public static final BankInventory FULL = BankInventory.of(18, 18, 18, 18, 18);

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

  public BankInventory minus(PlayerInventory inventory) {
    return new BankInventory(
        wood.minus(inventory.wood()),
        gold.minus(inventory.gold()),
        pineApple.minus(inventory.pineApple()),
        sheep.minus(inventory.sheep()),
        sword.minus(inventory.sword()));
  }

  public BankInventory minus(BuoyInventory inventory) {
    return new BankInventory(
        wood.minus(inventory.wood()),
        gold.minus(inventory.gold()),
        pineApple.minus(inventory.pineApple()),
        sheep.minus(inventory.sheep()),
        sword.minus(inventory.sword()));
  }
}
