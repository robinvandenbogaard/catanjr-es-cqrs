package nl.robinthedev.catanjr.game.model.resources;

import static nl.robinthedev.catanjr.game.model.board.ResourceType.*;

import io.vavr.collection.List;
import io.vavr.collection.Stream;
import java.util.Objects;
import java.util.function.Function;
import nl.robinthedev.catanjr.game.model.board.ResourceType;

public record BuoyInventory(List<ResourceType> resources) implements ResourceCollection {
  public static final BuoyInventory INITIAL = BuoyInventory.of(1, 1, 1, 1, 1);

  public BuoyInventory {
    Objects.requireNonNull(resources);
    if (resources.size() != 5) {
      throw new IllegalArgumentException(
          "Invalid number of resources, it must always contain 5 resources but was "
              + resources.size());
    }
    if (resources.distinct().size() == 1) {
      throw new IllegalArgumentException(
          "Need at least two resource types but all were of type " + resources.get());
    }
  }

  public static BuoyInventory of(int wood, int gold, int pineApple, int sheep, int sword) {
    var result =
        Stream.of(
                Stream.range(0, wood).map(i -> WOOD),
                Stream.range(0, gold).map(i -> GOLD),
                Stream.range(0, pineApple).map(i -> PINEAPPLE),
                Stream.range(0, sheep).map(i -> SHEEP),
                Stream.range(0, sword).map(i -> SWORD))
            .flatMap(Function.identity())
            .toList();
    return new BuoyInventory(result);
  }

  @Override
  public Wood wood() {
    return new Wood(resources.count(WOOD::equals));
  }

  @Override
  public Sheep sheep() {
    return new Sheep(resources.count(SHEEP::equals));
  }

  @Override
  public Gold gold() {
    return new Gold(resources.count(GOLD::equals));
  }

  @Override
  public PineApple pineApple() {
    return new PineApple(resources.count(PINEAPPLE::equals));
  }

  @Override
  public Sword sword() {
    return new Sword(resources.count(SWORD::equals));
  }
}
