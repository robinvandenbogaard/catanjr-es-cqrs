package nl.robinthedev.catanjr.infra.axon.game;

import static nl.robinthedev.catanjr.api.dto.ResourceTypeDTO.GOLD;
import static nl.robinthedev.catanjr.api.dto.ResourceTypeDTO.PINEAPPLE;
import static nl.robinthedev.catanjr.api.dto.ResourceTypeDTO.SHEEP;
import static nl.robinthedev.catanjr.api.dto.ResourceTypeDTO.SWORD;
import static nl.robinthedev.catanjr.api.dto.ResourceTypeDTO.WOOD;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import nl.robinthedev.catanjr.api.dto.BuoyDTO;
import nl.robinthedev.catanjr.api.dto.FortSiteDTO;
import nl.robinthedev.catanjr.api.dto.GameDTO;
import nl.robinthedev.catanjr.api.dto.GameId;
import nl.robinthedev.catanjr.api.dto.InventoryDTO;
import nl.robinthedev.catanjr.api.dto.OwnerDTO;
import nl.robinthedev.catanjr.api.dto.PlayerDTO;
import nl.robinthedev.catanjr.api.event.GameCreatedEvent;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.junit.jupiter.api.BeforeEach;

abstract class AbstractGameAggregateTest {
  protected static final GameId GAME_ID = GameId.randomGameId();
  protected static final UUID ACCOUNT_PLAYER_1 = UUID.randomUUID();
  protected static final UUID ACCOUNT_PLAYER_2 = UUID.randomUUID();
  protected static final String JOHN = "John";
  protected static final String WICK = "Wick";
  protected static final List<FortSiteDTO> INITIAL_COLOR_MAP;
  protected AggregateTestFixture<GameAggregate> fixture;

  static {
    INITIAL_COLOR_MAP = new ArrayList<>();
    INITIAL_COLOR_MAP.add(new FortSiteDTO(1, OwnerDTO.NONE));
    INITIAL_COLOR_MAP.add(new FortSiteDTO(2, OwnerDTO.PLAYER1));
    INITIAL_COLOR_MAP.add(new FortSiteDTO(3, OwnerDTO.NONE));
    INITIAL_COLOR_MAP.add(new FortSiteDTO(4, OwnerDTO.PLAYER2));
    INITIAL_COLOR_MAP.add(new FortSiteDTO(5, OwnerDTO.NONE));
    INITIAL_COLOR_MAP.add(new FortSiteDTO(6, OwnerDTO.NONE));
    INITIAL_COLOR_MAP.add(new FortSiteDTO(7, OwnerDTO.NONE));
    INITIAL_COLOR_MAP.add(new FortSiteDTO(8, OwnerDTO.NONE));
    INITIAL_COLOR_MAP.add(new FortSiteDTO(9, OwnerDTO.NONE));
    INITIAL_COLOR_MAP.add(new FortSiteDTO(10, OwnerDTO.NONE));
    INITIAL_COLOR_MAP.add(new FortSiteDTO(11, OwnerDTO.NONE));
    INITIAL_COLOR_MAP.add(new FortSiteDTO(12, OwnerDTO.NONE));
    INITIAL_COLOR_MAP.add(new FortSiteDTO(13, OwnerDTO.PLAYER1));
    INITIAL_COLOR_MAP.add(new FortSiteDTO(14, OwnerDTO.NONE));
    INITIAL_COLOR_MAP.add(new FortSiteDTO(15, OwnerDTO.PLAYER2));
    INITIAL_COLOR_MAP.add(new FortSiteDTO(16, OwnerDTO.NONE));
  }

  private ManipulatableDiceRoller diceRoller;

  @BeforeEach
  void setUp() {
    fixture = new AggregateTestFixture<>(GameAggregate.class);
    diceRoller = new ManipulatableDiceRoller();
    fixture.registerInjectableResource(diceRoller);
  }

  protected void whereNextDiceRollIs(int value) {
    diceRoller.nextRollIs(value);
  }

  protected static GameCreatedEvent getGameCreatedEvent() {
    return new GameCreatedEvent(
        GAME_ID,
        new GameDTO(
            new PlayerDTO(ACCOUNT_PLAYER_1, JOHN, new InventoryDTO(0, 0, 1, 0, 1), Set.of()),
            new PlayerDTO(ACCOUNT_PLAYER_2, WICK, new InventoryDTO(0, 0, 1, 0, 1), Set.of()),
            new InventoryDTO(17, 17, 15, 17, 15),
            List.of(
                new BuoyDTO(WOOD),
                new BuoyDTO(GOLD),
                new BuoyDTO(PINEAPPLE),
                new BuoyDTO(SHEEP),
                new BuoyDTO(SWORD)),
            INITIAL_COLOR_MAP));
  }
}
