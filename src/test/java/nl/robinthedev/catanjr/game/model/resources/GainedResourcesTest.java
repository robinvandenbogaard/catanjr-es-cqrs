package nl.robinthedev.catanjr.game.model.resources;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class GainedResourcesTest {

  @Test
  void of() {
    assertThat(GainedResources.of(1, 2, 3, 4, 5))
        .isEqualTo(
            new GainedResources(
                new Wood(1), new Gold(2), new PineApple(3), new Sheep(4), new Sword(5)));
  }

  @Test
  void swords() {
    assertThat(GainedResources.swords(1)).isEqualTo(GainedResources.of(0, 0, 0, 0, 1));
  }

  @Test
  void gold() {
    assertThat(GainedResources.gold(1)).isEqualTo(GainedResources.of(0, 1, 0, 0, 0));
  }

  @Test
  void pineApple() {
    assertThat(GainedResources.pineApple(1)).isEqualTo(GainedResources.of(0, 0, 1, 0, 0));
  }

  @Test
  void sheep() {
    assertThat(GainedResources.sheep(1)).isEqualTo(GainedResources.of(0, 0, 0, 1, 0));
  }

  @Test
  void wood() {
    assertThat(GainedResources.wood(1)).isEqualTo(GainedResources.of(1, 0, 0, 0, 0));
  }

  @Test
  void add() {
    var gainedResources = GainedResources.of(1, 1, 1, 1, 1);
    var addition = GainedResources.of(1, 2, 3, 4, 5);
    assertThat(gainedResources.add(addition)).isEqualTo(GainedResources.of(2, 3, 4, 5, 6));
  }
}
