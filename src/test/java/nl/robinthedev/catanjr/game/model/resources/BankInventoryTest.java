package nl.robinthedev.catanjr.game.model.resources;

import static org.assertj.core.api.Assertions.assertThat;

import nl.robinthedev.catanjr.game.model.board.ResourceType;
import org.junit.jupiter.api.Test;

class BankInventoryTest {
  @Test
  void exceedingResourcesSingle() {
    var emptyBank = BankInventory.EMPTY;
    assertThat(emptyBank.getExceedingResources(ResourceChanges.wood(1)))
        .containsExactlyInAnyOrder(ResourceType.WOOD);
    assertThat(emptyBank.getExceedingResources(ResourceChanges.sheep(2)))
        .containsExactlyInAnyOrder(ResourceType.SHEEP);
    assertThat(emptyBank.getExceedingResources(ResourceChanges.swords(3)))
        .containsExactlyInAnyOrder(ResourceType.SWORD);
    assertThat(emptyBank.getExceedingResources(ResourceChanges.gold(4)))
        .containsExactlyInAnyOrder(ResourceType.GOLD);
    assertThat(emptyBank.getExceedingResources(ResourceChanges.pineApple(5)))
        .containsExactlyInAnyOrder(ResourceType.PINEAPPLE);
  }

  @Test
  void exceedingResourcesCombo() {
    var emptyBank = BankInventory.EMPTY;
    assertThat(emptyBank.getExceedingResources(ResourceChanges.of(1, 1, 1, 1, 1)))
        .containsExactlyInAnyOrder(
            ResourceType.WOOD,
            ResourceType.SHEEP,
            ResourceType.SWORD,
            ResourceType.GOLD,
            ResourceType.PINEAPPLE);
    assertThat(emptyBank.getExceedingResources(ResourceChanges.of(1, 1, 1, 0, 0)))
        .containsExactlyInAnyOrder(ResourceType.WOOD, ResourceType.GOLD, ResourceType.PINEAPPLE);
  }
}
