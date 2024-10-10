package nl.robinthedev.catanjr.game.model.round;

import static org.assertj.core.api.Assertions.assertThatNoException;

import nl.robinthedev.catanjr.api.dto.ActionDTO;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class ActionTest {
  @ParameterizedTest
  @EnumSource(Action.class)
  void has_an_dto_variant(Action action) {
    assertThatNoException().isThrownBy(() -> ActionDTO.valueOf(action.name()));
  }
}
