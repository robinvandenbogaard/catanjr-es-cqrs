package nl.robinthedev.catanjr.application.rest;

import java.util.UUID;
import nl.robinthedev.catanjr.api.command.BuyFort;
import nl.robinthedev.catanjr.api.command.BuyShip;
import nl.robinthedev.catanjr.api.command.CreateNewGame;
import nl.robinthedev.catanjr.api.command.EndTurn;
import nl.robinthedev.catanjr.api.command.RollDice;
import nl.robinthedev.catanjr.api.dto.GameId;
import nl.robinthedev.catanjr.game.service.PlayerTurn;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
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
  private final PlayerTurn turns;

  public GameCommandController(CommandGateway commandGateway, PlayerTurn turns) {
    this.commandGateway = commandGateway;
    this.turns = turns;
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("")
  public GameId createNewGame() {
    var gameId = GameId.fromString("e09883fb-aa87-4f6d-a0a3-1caff0aeced8");
    var accountId1 = UUID.fromString("069f188b-607d-4760-a81f-35e7478e176c");
    var accountId2 = UUID.fromString("d8bccdd1-abd3-4545-87da-eb9113222c68");
    commandGateway.sendAndWait(
        new CreateNewGame(gameId, "My new Game", accountId1, "Robin", accountId2, "Jim"));
    log.info("Created new game {}", gameId);
    return gameId;
  }

  @ResponseStatus(HttpStatus.OK)
  @PostMapping("rolldice")
  public void rollDice() {
    var gameId = GameId.fromString("e09883fb-aa87-4f6d-a0a3-1caff0aeced8");
    var accountId = getCurrentPlayer(gameId);
    commandGateway.sendAndWait(new RollDice(gameId, accountId));
    log.info("Rolled dice for player {} in {}", accountId, gameId);
  }

  private UUID getCurrentPlayer(GameId gameId) {
    return turns
        .getCurrentPlayerAccount(gameId)
        .getOrElseThrow(CannotDetermineCurrentPlayerException::new);
  }

  @ResponseStatus(HttpStatus.OK)
  @PostMapping("endTurn")
  public void endTurn() {
    var gameId = GameId.fromString("e09883fb-aa87-4f6d-a0a3-1caff0aeced8");
    var accountId = getCurrentPlayer(gameId);
    commandGateway.sendAndWait(new EndTurn(gameId, accountId));
    log.info("Turn ended for {} in {}", accountId, gameId);
  }

  @ResponseStatus(HttpStatus.OK)
  @PostMapping("buyFort/{fortSiteId}")
  public void buyFort(@PathVariable Integer fortSiteId) {
    var gameId = GameId.fromString("e09883fb-aa87-4f6d-a0a3-1caff0aeced8");
    var accountId = getCurrentPlayer(gameId);
    commandGateway.sendAndWait(new BuyFort(gameId, accountId, fortSiteId));
    log.info("Buy fort {} for {}", fortSiteId, gameId);
  }

  @ResponseStatus(HttpStatus.OK)
  @PostMapping("buyShip/{shipYardId}")
  public void buyFort(@PathVariable String shipYardId) {
    var gameId = GameId.fromString("e09883fb-aa87-4f6d-a0a3-1caff0aeced8");
    var accountId = getCurrentPlayer(gameId);
    commandGateway.sendAndWait(new BuyShip(gameId, accountId, shipYardId));
    log.info("Buy ship {} for {}", shipYardId, gameId);
  }
}
