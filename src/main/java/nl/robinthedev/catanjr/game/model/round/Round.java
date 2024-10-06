package nl.robinthedev.catanjr.game.model.round;

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

  public boolean allowedToRoll(AccountId accountPlayerId) {
    return current.equals(accountPlayerId) && actions.actions().contains(Action.THROW_DICE);
  }

  public Round diceRolled() {
    return new Round(p1, p2, current, actions.diceRolled());
  }
}
