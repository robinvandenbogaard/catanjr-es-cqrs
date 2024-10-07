package nl.robinthedev.catanjr.game.model.round;

import static nl.robinthedev.catanjr.game.model.round.Action.BUY;
import static nl.robinthedev.catanjr.game.model.round.Action.END_TURN;
import static nl.robinthedev.catanjr.game.model.round.Action.THROW_DICE;
import static nl.robinthedev.catanjr.game.model.round.Action.TRADE_BANK;
import static nl.robinthedev.catanjr.game.model.round.Action.TRADE_BUOY;

import java.util.List;
import java.util.Objects;

final class Actions {

  public Actions(List<Action> actions) {
    Objects.requireNonNull(actions);
    if (actions.isEmpty()) throw new IllegalArgumentException("Actions cannot be empty");
    this.actions = actions;
  }

  public static final Actions THROW_DICE_ONLY = Actions.of(THROW_DICE);
  private final List<Action> actions;

  private static Actions of(Action... actions) {
    return new Actions(List.of(actions));
  }

  public Actions diceRolled() {
    if (!actions.contains(THROW_DICE)) {
      throw new IllegalStateException("Cannot roll dice if action THROW_DICE is not present");
    }
    return Actions.of(TRADE_BUOY, TRADE_BANK, BUY, END_TURN);
  }

  public boolean contains(Action action) {
    return actions.contains(action);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj == null || obj.getClass() != this.getClass()) return false;
    var that = (Actions) obj;
    return Objects.equals(this.actions, that.actions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(actions);
  }

  @Override
  public String toString() {
    return "Actions[" + "actions=" + actions + ']';
  }
}
