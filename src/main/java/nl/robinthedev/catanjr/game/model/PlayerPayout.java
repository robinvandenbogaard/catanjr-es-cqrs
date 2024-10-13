package nl.robinthedev.catanjr.game.model;

import io.vavr.collection.Set;
import nl.robinthedev.catanjr.api.dto.DiceRoll;
import nl.robinthedev.catanjr.game.model.board.Board;
import nl.robinthedev.catanjr.game.model.board.ResourceType;
import nl.robinthedev.catanjr.game.model.player.Player;
import nl.robinthedev.catanjr.game.model.resources.ResourceChanges;

record PlayerPayout(Player player, ResourceChanges resourceChanges) {

  public static PlayerPayout of(Player player, Board board, DiceRoll diceRoll) {
    var gainedResources = board.getResources(diceRoll, player.nr());
    return new PlayerPayout(player, gainedResources);
  }

  public PlayerPayout confiscate(Set<ResourceType> exceeded) {
    var newGains = resourceChanges();
    for (var type : exceeded) {
      newGains =
          switch (type) {
            case SWORD -> newGains.withSword(-player.inventory().sword().value());
            case GOLD -> newGains.withGold(-player.inventory().gold().value());
            case PINEAPPLE -> newGains.withPineApple(-player.inventory().pineApple().value());
            case SHEEP -> newGains.withSheep(-player.inventory().sheep().value());
            case WOOD -> newGains.withWood(-player.inventory().wood().value());
          };
    }
    return new PlayerPayout(player, newGains);
  }

  public PlayerReport asReport() {
    var currentInventory = player.inventory();
    var newInventory =
        currentInventory.add(resourceChanges.additions()).minus(resourceChanges.subtractions());
    return new PlayerReport(player.accountId(), currentInventory, resourceChanges, newInventory);
  }
}
