package nl.robinthedev.catanjr.application.sse;

import java.util.Map;
import java.util.UUID;
import nl.robinthedev.catanjr.api.dto.GameDTO;
import nl.robinthedev.catanjr.api.dto.GameId;
import nl.robinthedev.catanjr.api.query.GetGameQuery;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("game")
@ResponseBody
class ServerSendEventController {
  private final QueryGateway queryGateway;

  public ServerSendEventController(QueryGateway queryGateway) {
    this.queryGateway = queryGateway;
  }

  @GetMapping(value = "{id}/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<ServerSentEvent<String>> subcribeToGame(@PathVariable("id") UUID id) {
    var gameId = new GameId(id);
    var queryResult =
        queryGateway.subscriptionQuery(new GetGameQuery(gameId), GameDTO.class, GameDTO.class);
    return queryResult
        .initialResult()
        .concatWith(queryResult.updates())
        .map(this::toHtml)
        .map(htmlSnippet -> ServerSentEvent.builder(htmlSnippet).build());
  }

  private String toHtml(GameDTO game) {
    PlayerState player1 = PlayerState.of(game.firstPlayer());
    PlayerState player2 = PlayerState.of(game.secondPlayer());
    return PebbleTemplate.processTemplate(
        "2pgame",
        Map.of(
            "firstPlayer",
            player1,
            "secondPlayer",
            player2,
            "buoys",
            BuoysState.from(game.buoyInventory()),
            "bank",
            game.bankInventory(),
            "fortSiteColors",
            BoardState.asMap(game.fortSites())));
  }
}
