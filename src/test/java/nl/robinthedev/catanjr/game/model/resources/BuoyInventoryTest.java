package nl.robinthedev.catanjr.game.model.resources;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class BuoyInventoryTest {

  @Test
  void initially_has_one_of_all() {
    assertThat(BuoyInventory.INITIAL).isEqualTo(BuoyInventory.of(1, 1, 1, 1, 1));
  }

  @Test
  void not_enough_resources_are_not_allowed() {
    assertThatThrownBy(() -> BuoyInventory.of(1, 1, 1, 1, 0))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void too_many_resources_are_not_allowed() {
    assertThatThrownBy(() -> BuoyInventory.of(2, 0, 4, 0, 0))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void no_resources_are_not_allowed() {
    assertThatThrownBy(() -> BuoyInventory.of(0, 0, 0, 0, 0))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void negative_resources_are_not_allowed() {
    assertThatThrownBy(() -> BuoyInventory.of(-1, 0, 0, 0, 0))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void resources_all_wood_is_not_allowed() {
    assertThatThrownBy(() -> BuoyInventory.of(5, 0, 0, 0, 0))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void resources_all_gold_is_not_allowed() {
    assertThatThrownBy(() -> BuoyInventory.of(0, 5, 0, 0, 0))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void resources_all_pineApple_is_not_allowed() {
    assertThatThrownBy(() -> BuoyInventory.of(0, 0, 5, 0, 0))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void resources_all_sheep_is_not_allowed() {
    assertThatThrownBy(() -> BuoyInventory.of(0, 0, 0, 5, 0))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void resources_all_sword_is_not_allowed() {
    assertThatThrownBy(() -> BuoyInventory.of(0, 0, 0, 0, 5))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void can_be_transformed_to_resources() {
    var buoy = BuoyInventory.of(1, 0, 1, 2, 1);

    assertThat(buoy.wood()).isEqualTo(new Wood(1));
    assertThat(buoy.gold()).isEqualTo(new Gold(0));
    assertThat(buoy.pineApple()).isEqualTo(new PineApple(1));
    assertThat(buoy.sheep()).isEqualTo(new Sheep(2));
    assertThat(buoy.sword()).isEqualTo(new Sword(1));
  }
}
