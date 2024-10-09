package nl.robinthedev.catanjr.game.model.resources;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ResourceChangesTest {

  @Test
  void of() {
    assertThat(ResourceChanges.of(1, 2, 3, 4, 5)).isEqualTo(new ResourceChanges(1, 2, 3, 4, 5));
  }

  @Test
  void swords() {
    assertThat(ResourceChanges.swords(1)).isEqualTo(ResourceChanges.of(0, 0, 0, 0, 1));
  }

  @Test
  void gold() {
    assertThat(ResourceChanges.gold(1)).isEqualTo(ResourceChanges.of(0, 1, 0, 0, 0));
  }

  @Test
  void pineApple() {
    assertThat(ResourceChanges.pineApple(1)).isEqualTo(ResourceChanges.of(0, 0, 1, 0, 0));
  }

  @Test
  void sheep() {
    assertThat(ResourceChanges.sheep(1)).isEqualTo(ResourceChanges.of(0, 0, 0, 1, 0));
  }

  @Test
  void wood() {
    assertThat(ResourceChanges.wood(1)).isEqualTo(ResourceChanges.of(1, 0, 0, 0, 0));
  }

  @Test
  void add() {
    var gainedResources = ResourceChanges.of(1, 1, 1, 1, 1);
    var addition = ResourceChanges.of(1, 2, 3, 4, 5);
    assertThat(gainedResources.add(addition)).isEqualTo(ResourceChanges.of(2, 3, 4, 5, 6));
  }
}
