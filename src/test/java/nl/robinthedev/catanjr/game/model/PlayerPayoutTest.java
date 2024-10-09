package nl.robinthedev.catanjr.game.model;

import static nl.robinthedev.catanjr.game.model.board.ResourceType.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;
import nl.robinthedev.catanjr.game.model.board.BoardPlayer;
import nl.robinthedev.catanjr.game.model.board.ResourceType;
import nl.robinthedev.catanjr.game.model.player.AccountId;
import nl.robinthedev.catanjr.game.model.player.Player;
import nl.robinthedev.catanjr.game.model.player.PlayerId;
import nl.robinthedev.catanjr.game.model.player.Username;
import nl.robinthedev.catanjr.game.model.resources.PlayerInventory;
import nl.robinthedev.catanjr.game.model.resources.ResourceChanges;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PlayerPayoutTest {
  public static Stream<Arguments> confiscation() {
    return Stream.of(
        Arguments.of(
            Named.of("No resourceChanges from player inventory will be confiscated", Set.of()),
            ResourceChanges.of(1, 1, 1, 1, 1)),
        Arguments.of(
            Named.of("All 10 wood from player inventory will be confiscated", Set.of(WOOD)),
            ResourceChanges.of(-10, 1, 1, 1, 1)),
        Arguments.of(
            Named.of("All 10 gold from player inventory will be confiscated", Set.of(GOLD)),
            ResourceChanges.of(1, -10, 1, 1, 1)),
        Arguments.of(
            Named.of(
                "All 10 pineapple from player inventory will be confiscated", Set.of(PINEAPPLE)),
            ResourceChanges.of(1, 1, -10, 1, 1)),
        Arguments.of(
            Named.of("All 10 sheep from player inventory will be confiscated", Set.of(SHEEP)),
            ResourceChanges.of(1, 1, 1, -10, 1)),
        Arguments.of(
            Named.of("All 10 swords from player inventory will be confiscated", Set.of(SWORD)),
            ResourceChanges.of(1, 1, 1, 1, -10)),
        Arguments.of(
            Named.of(
                "All 10 gold and sheep from player inventory will be confiscated",
                Set.of(GOLD, SHEEP)),
            ResourceChanges.of(1, -10, 1, -10, 1)),
        Arguments.of(
            Named.of(
                "All 10 wood, pineapple and swords from player inventory will be confiscated",
                Set.of(WOOD, PINEAPPLE, SWORD)),
            ResourceChanges.of(-10, 1, -10, 1, -10)));
  }

  @ParameterizedTest(name = "{0}")
  @MethodSource("confiscation")
  void confiscate(Set<ResourceType> exceededResources, ResourceChanges expectedResources) {
    var player = p1(10, 10, 10, 10, 10);
    var payout = new PlayerPayout(player, ResourceChanges.of(1, 1, 1, 1, 1));

    assertThat(payout.confiscate(exceededResources))
        .isEqualTo(new PlayerPayout(player, expectedResources));
  }

  public static Player p1(int wood, int gold, int pineApple, int sheep, int sword) {
    return new Player(
        new PlayerId(new AccountId(UUID.randomUUID()), new Username("Edward Snow")),
        PlayerInventory.of(wood, gold, pineApple, sheep, sword),
        BoardPlayer.PLAYER1);
  }
}
