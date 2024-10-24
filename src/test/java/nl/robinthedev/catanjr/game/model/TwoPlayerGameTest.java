package nl.robinthedev.catanjr.game.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import nl.robinthedev.catanjr.game.model.coco.RemainingCocoTilesCount;
import nl.robinthedev.catanjr.game.model.player.AccountId;
import nl.robinthedev.catanjr.game.model.player.PlayerId;
import nl.robinthedev.catanjr.game.model.player.Username;
import nl.robinthedev.catanjr.game.model.resources.BankInventory;
import nl.robinthedev.catanjr.game.model.resources.BuoyInventory;
import nl.robinthedev.catanjr.game.model.resources.PlayerInventory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class TwoPlayerGameTest {

  static class Scenario {
    enum Player {
      PLAYER_ONE(UUID.randomUUID(), "Robin"),
      PLAYER_TWO(UUID.randomUUID(), "Karen");

      private final PlayerId playerId;

      Player(UUID accountid, String username) {
        playerId = new PlayerId(new AccountId(accountid), new Username(username));
      }
    }
  }

  public static final PlayerId PLAYER_ONE = Scenario.Player.PLAYER_ONE.playerId;
  public static final PlayerId PLAYER_TWO = Scenario.Player.PLAYER_TWO.playerId;
  public static final Game TWO_PLAYER_GAME = GameFactory.of("My new game", PLAYER_ONE, PLAYER_TWO);

  @Test
  void bouy_inventory_has_one_of_each() {
    var buoyInventory = TWO_PLAYER_GAME.buoyInventory();
    assertThat(buoyInventory).isEqualTo(BuoyInventory.of(1, 1, 1, 1, 1));
  }

  @Test
  void player_one_inventory_has_one_sword_and_one_wood_and_nothing_of_others() {
    var playerInventory = TWO_PLAYER_GAME.firstPlayer().inventory();
    assertThat(playerInventory).isEqualTo(PlayerInventory.of(1, 0, 0, 0, 1));
  }

  @Test
  void player_two_inventory_has_one_sword_and_one_wood_and_nothing_of_others() {
    var playerInventory = TWO_PLAYER_GAME.secondPlayer().inventory();
    assertThat(playerInventory).isEqualTo(PlayerInventory.of(1, 0, 0, 0, 1));
  }

  @Test
  void bank_inventory_has_reduced_resources_based_on_players_and_buoy() {
    var bankInventory = TWO_PLAYER_GAME.bankInventory();
    assertThat(bankInventory).isEqualTo(BankInventory.of(15, 17, 17, 17, 15));
  }

  @Test
  void there_are_16_coco_tiles() {
    var tiles = TWO_PLAYER_GAME.cocoTiles();
    assertThat(tiles.remainingTilesCount()).isEqualTo(new RemainingCocoTilesCount(16));
  }

  @ParameterizedTest
  @CsvSource({"2, PLAYER_ONE", "13, PLAYER_ONE", "4, PLAYER_TWO", "15, PLAYER_TWO"})
  void player_one_owns_fort_spots(Integer siteId, String player) {
    var owner = TWO_PLAYER_GAME.ownerOf(new SiteId(siteId));
    assertThat(owner).hasValue(new Owner(Scenario.Player.valueOf(player).playerId));
  }
}
