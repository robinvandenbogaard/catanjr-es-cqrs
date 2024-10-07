package nl.robinthedev.catanjr.application.rest;

import java.util.Map;
import java.util.UUID;
import nl.robinthedev.catanjr.api.command.CreateNewGame;
import nl.robinthedev.catanjr.api.command.EndTurn;
import nl.robinthedev.catanjr.api.command.RollDice;
import nl.robinthedev.catanjr.api.dto.GameDTO;
import nl.robinthedev.catanjr.api.dto.GameId;
import nl.robinthedev.catanjr.api.query.GetGameQuery;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("game")
@ResponseBody
class GameController {

  private static final Logger log = LoggerFactory.getLogger(GameController.class);
  private final CommandGateway commandGateway;
  private final QueryGateway queryGateway;

  public GameController(CommandGateway commandGateway, QueryGateway queryGateway) {
    this.commandGateway = commandGateway;
    this.queryGateway = queryGateway;
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("")
  public GameId createNewGame() {
    var gameId = GameId.fromString("e09883fb-aa87-4f6d-a0a3-1caff0aeced8");
    var accountId1 = UUID.fromString("069f188b-607d-4760-a81f-35e7478e176c");
    var accountId2 = UUID.fromString("d8bccdd1-abd3-4545-87da-eb9113222c68");
    commandGateway.sendAndWait(new CreateNewGame(gameId, accountId1, "Robin", accountId2, "Jim"));
    log.info("Created new game {}", gameId);
    return gameId;
  }

  @ResponseStatus(HttpStatus.OK)
  @PostMapping("rollp1")
  public void rollDiceP1() {
    var gameId = GameId.fromString("e09883fb-aa87-4f6d-a0a3-1caff0aeced8");
    var accountId1 = UUID.fromString("069f188b-607d-4760-a81f-35e7478e176c");
    commandGateway.sendAndWait(new RollDice(gameId, accountId1));
    log.info("Rolled dice for p1 {}", gameId);
  }

  @ResponseStatus(HttpStatus.OK)
  @PostMapping("rollp2")
  public void rollDiceP2() {
    var gameId = GameId.fromString("e09883fb-aa87-4f6d-a0a3-1caff0aeced8");
    var accountId2 = UUID.fromString("d8bccdd1-abd3-4545-87da-eb9113222c68");
    commandGateway.sendAndWait(new RollDice(gameId, accountId2));
    log.info("Rolled dice for p2 {}", gameId);
  }

  @ResponseStatus(HttpStatus.OK)
  @PostMapping("endTurnP1")
  public void endTurnP1() {
    var gameId = GameId.fromString("e09883fb-aa87-4f6d-a0a3-1caff0aeced8");
    var accountId1 = UUID.fromString("069f188b-607d-4760-a81f-35e7478e176c");
    commandGateway.sendAndWait(new EndTurn(gameId, accountId1));
    log.info("Turn ended for p1 {}", gameId);
  }

  @ResponseStatus(HttpStatus.OK)
  @PostMapping("endTurnP2")
  public void endTurnP2() {
    var gameId = GameId.fromString("e09883fb-aa87-4f6d-a0a3-1caff0aeced8");
    var accountId2 = UUID.fromString("d8bccdd1-abd3-4545-87da-eb9113222c68");
    commandGateway.sendAndWait(new EndTurn(gameId, accountId2));
    log.info("Turn ended for p2 {}", gameId);
  }

  @GetMapping(value = "/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
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
    return PebbleTemplate.processTemplate(
        "2pgame",
        Map.of(
            "firstPlayer",
            game.firstPlayer(),
            "secondPlayer",
            game.secondPlayer(),
            "buoy",
            game.buoyInventory(),
            "bank",
            game.bankInventory(),
            "shipYardColors",
            BoardState.asMap(game.shipYards())));
  }
}
