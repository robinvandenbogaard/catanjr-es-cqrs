package nl.robinthedev.catanjr.game.model.board;

import nl.robinthedev.catanjr.game.model.SiteId;

public interface Board {

  BoardPlayer getOwner(SiteId shipSite);
}
