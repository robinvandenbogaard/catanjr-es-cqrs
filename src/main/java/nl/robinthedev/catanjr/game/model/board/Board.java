package nl.robinthedev.catanjr.game.model.board;

import nl.robinthedev.catanjr.api.dto.DiceRoll;
import nl.robinthedev.catanjr.game.model.SiteId;
import nl.robinthedev.catanjr.game.model.resources.GainedResources;

public interface Board {

  BoardPlayer getOwner(SiteId shipSite);

  GainedResources getResources(DiceRoll diceRoll, BoardPlayer nr);
}
