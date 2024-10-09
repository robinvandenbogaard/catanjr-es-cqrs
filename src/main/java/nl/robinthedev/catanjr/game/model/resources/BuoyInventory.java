package nl.robinthedev.catanjr.game.model.resources;

import static nl.robinthedev.catanjr.game.model.board.ResourceType.*;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;
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
    if (new HashSet<>(resources).size() == 1) {
      throw new IllegalArgumentException(
          "Need at least two resource types but all were of type " + resources.getFirst());
    }
  }

  public static BuoyInventory of(int wood, int gold, int pineApple, int sheep, int sword) {
    var result =
        Stream.of(
                IntStream.range(0, wood).mapToObj(i -> WOOD),
                IntStream.range(0, gold).mapToObj(i -> GOLD),
                IntStream.range(0, pineApple).mapToObj(i -> PINEAPPLE),
                IntStream.range(0, sheep).mapToObj(i -> SHEEP),
                IntStream.range(0, sword).mapToObj(i -> SWORD))
            .flatMap(Function.identity())
            .toList();
    return new BuoyInventory(result);
  }

  @Override
  public Wood wood() {
    return new Wood((int) resources.stream().filter(WOOD::equals).count());
  }

  @Override
  public Sheep sheep() {
    return new Sheep((int) resources.stream().filter(SHEEP::equals).count());
  }

  @Override
  public Gold gold() {
    return new Gold((int) resources.stream().filter(GOLD::equals).count());
  }

  @Override
  public PineApple pineApple() {
    return new PineApple((int) resources.stream().filter(PINEAPPLE::equals).count());
  }

  @Override
  public Sword sword() {
    return new Sword((int) resources.stream().filter(SWORD::equals).count());
  }
}
