package nl.robinthedev.catanjr.application.query;

import nl.robinthedev.catanjr.api.dto.GameDTO;
import nl.robinthedev.catanjr.api.query.GetGameQuery;
import nl.robinthedev.catanjr.game.service.Games;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

@Service
class GetGameQueryHandler {

  private final Games games;

  public GetGameQueryHandler(Games games) {
    this.games = games;
  }

  @QueryHandler
  GameDTO getGame(GetGameQuery query) {
    return games.get(query.gameId());
  }
}
