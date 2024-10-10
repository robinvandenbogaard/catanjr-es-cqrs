package nl.robinthedev.catanjr.application.rest;

import java.util.UUID;
import nl.robinthedev.catanjr.api.command.CreateNewGame;
import nl.robinthedev.catanjr.api.command.EndTurn;
import nl.robinthedev.catanjr.api.command.RollDice;
import nl.robinthedev.catanjr.api.dto.GameId;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("game")
@ResponseBody
class GameCommandController {

  private static final Logger log = LoggerFactory.getLogger(GameCommandController.class);
  private final CommandGateway commandGateway;

  public GameCommandController(CommandGateway commandGateway) {
    this.commandGateway = commandGateway;
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
}
