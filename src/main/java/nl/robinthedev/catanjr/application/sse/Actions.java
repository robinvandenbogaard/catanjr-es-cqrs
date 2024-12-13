package nl.robinthedev.catanjr.application.sse;

import io.vavr.collection.Set;
import nl.robinthedev.catanjr.api.dto.ActionDTO;

record Actions(boolean endTurn, boolean rollDice) {

  public static Actions of(Set<ActionDTO> actions) {
    return new Actions(
        actions.contains(ActionDTO.END_TURN), actions.contains(ActionDTO.THROW_DICE));
  }
}
