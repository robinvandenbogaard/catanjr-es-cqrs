package nl.robinthedev.catanjr.game.model.round;

import java.util.UUID;
import java.util.stream.Stream;
import nl.robinthedev.catanjr.game.model.player.AccountId;

public final class Round {
  private final AccountId p1;
  private final AccountId p2;
  private final AccountId current;
  private final Actions actions;

  Round(AccountId p1, AccountId p2, AccountId current, Actions actions) {
    this.p1 = p1;
    this.p2 = p2;
    this.current = current;
    this.actions = actions;
  }

  public static Round firstRound(AccountId firstPlayer, AccountId secondPlayer) {
    return new Round(firstPlayer, secondPlayer, firstPlayer, Actions.THROW_DICE_ONLY);
  }

  public boolean allowedToRoll(AccountId playerAccountId) {
    return isAllowed(playerAccountId, Action.THROW_DICE);
  }

  public Round diceRolled() {
    return new Round(p1, p2, current, actions.diceRolled());
  }

  public boolean allowedToEndTurn(AccountId playerAccountId) {
    return isAllowed(playerAccountId, Action.END_TURN);
  }

  private boolean isAllowed(AccountId playerAccountId, Action action) {
    return current.equals(playerAccountId) && actions.contains(action);
  }

  public UUID nextPlayer() {
    return current.equals(p1) ? p2.value() : p1.value();
  }

  public UUID currentPlayer() {
    return current.value();
  }

  public Stream<Action> actions() {
    return actions.actions().stream();
  }

  public Round turnEnded(AccountId newPlayerAccountId) {
    AccountId current;
    if (p1.equals(newPlayerAccountId)) {
      current = p1;
    } else {
      current = p2;
    }
    return new Round(p1, p2, current, Actions.THROW_DICE_ONLY);
  }

  public boolean allowedToBuyShip(AccountId playerAccountId) {
    return isAllowed(playerAccountId, Action.BUY_SHIP);
  }
}
