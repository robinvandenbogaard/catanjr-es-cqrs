package nl.robinthedev.catanjr.game.model.round;

import static nl.robinthedev.catanjr.game.model.round.Action.END_TURN;
import static nl.robinthedev.catanjr.game.model.round.Action.THROW_DICE;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

record Actions(List<Action> actions) {

  Actions(List<Action> actions) {
    Objects.requireNonNull(actions);
    if (actions.isEmpty()) throw new IllegalArgumentException("Actions cannot be empty");
    this.actions = Collections.unmodifiableList(actions);
  }

  public static final Actions THROW_DICE_ONLY = Actions.of(THROW_DICE);

  private static Actions of(Action... actions) {
    return new Actions(List.of(actions));
  }

  public Actions diceRolled() {
    if (!actions.contains(THROW_DICE)) {
      throw new IllegalStateException("Cannot roll dice if action THROW_DICE is not present");
    }
    return Actions.of(END_TURN);
  }

  public boolean contains(Action action) {
    return actions.contains(action);
  }
}
