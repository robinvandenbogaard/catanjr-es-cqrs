package nl.robinthedev.catanjr.game.model.board;

import nl.robinthedev.catanjr.api.dto.DiceRoll;
import nl.robinthedev.catanjr.game.model.SiteId;
import nl.robinthedev.catanjr.game.model.resources.ResourceChanges;

public interface Board {

  BoardPlayer getOwner(SiteId shipSite);

  ResourceChanges getResources(DiceRoll diceRoll, BoardPlayer nr);
}
