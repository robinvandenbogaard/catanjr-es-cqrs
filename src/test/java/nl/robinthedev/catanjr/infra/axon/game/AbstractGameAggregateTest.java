package nl.robinthedev.catanjr.infra.axon.game;

import static nl.robinthedev.catanjr.api.dto.ResourceTypeDTO.GOLD;
import static nl.robinthedev.catanjr.api.dto.ResourceTypeDTO.PINEAPPLE;
import static nl.robinthedev.catanjr.api.dto.ResourceTypeDTO.SHEEP;
import static nl.robinthedev.catanjr.api.dto.ResourceTypeDTO.SWORD;
import static nl.robinthedev.catanjr.api.dto.ResourceTypeDTO.WOOD;

import io.vavr.collection.HashSet;
import io.vavr.collection.List;
import io.vavr.collection.Set;
import java.util.UUID;
import nl.robinthedev.catanjr.api.dto.BuoyDTO;
import nl.robinthedev.catanjr.api.dto.FortSiteDTO;
import nl.robinthedev.catanjr.api.dto.GameDTO;
import nl.robinthedev.catanjr.api.dto.GameId;
import nl.robinthedev.catanjr.api.dto.InventoryDTO;
import nl.robinthedev.catanjr.api.dto.OwnerDTO;
import nl.robinthedev.catanjr.api.dto.PlayerDTO;
import nl.robinthedev.catanjr.api.dto.ShipYardDTO;
import nl.robinthedev.catanjr.api.event.GameCreatedEvent;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.junit.jupiter.api.BeforeEach;

abstract class AbstractGameAggregateTest {
  protected static final GameId GAME_ID = GameId.randomGameId();
  protected static final UUID ACCOUNT_PLAYER_1 = UUID.randomUUID();
  protected static final UUID ACCOUNT_PLAYER_2 = UUID.randomUUID();
  protected static final String JOHN = "John";
  protected static final String WICK = "Wick";
  protected static final List<FortSiteDTO> INITIAL_FORT_MAP;
  protected static final Set<ShipYardDTO> INITIAL_SHIP_MAP;
  public static final String GAME_TITLE = "My new game";
  protected AggregateTestFixture<GameAggregate> fixture;

  static {
    INITIAL_FORT_MAP =
        List.of(
            new FortSiteDTO(1, OwnerDTO.NONE),
            new FortSiteDTO(2, OwnerDTO.PLAYER1),
            new FortSiteDTO(3, OwnerDTO.NONE),
            new FortSiteDTO(4, OwnerDTO.PLAYER2),
            new FortSiteDTO(5, OwnerDTO.NONE),
            new FortSiteDTO(6, OwnerDTO.NONE),
            new FortSiteDTO(7, OwnerDTO.NONE),
            new FortSiteDTO(8, OwnerDTO.NONE),
            new FortSiteDTO(9, OwnerDTO.NONE),
            new FortSiteDTO(10, OwnerDTO.NONE),
            new FortSiteDTO(11, OwnerDTO.NONE),
            new FortSiteDTO(12, OwnerDTO.NONE),
            new FortSiteDTO(13, OwnerDTO.PLAYER1),
            new FortSiteDTO(14, OwnerDTO.NONE),
            new FortSiteDTO(15, OwnerDTO.PLAYER2),
            new FortSiteDTO(16, OwnerDTO.NONE));

    INITIAL_SHIP_MAP =
        HashSet.of(
            new ShipYardDTO("1-3", OwnerDTO.NONE),
            new ShipYardDTO("2-3", OwnerDTO.PLAYER1),
            new ShipYardDTO("3-5", OwnerDTO.NONE),
            new ShipYardDTO("4-6", OwnerDTO.PLAYER2),
            new ShipYardDTO("5-6", OwnerDTO.NONE),
            new ShipYardDTO("5-8", OwnerDTO.NONE),
            new ShipYardDTO("6-9", OwnerDTO.NONE),
            new ShipYardDTO("7-8", OwnerDTO.NONE),
            new ShipYardDTO("8-11", OwnerDTO.NONE),
            new ShipYardDTO("9-10", OwnerDTO.NONE),
            new ShipYardDTO("9-12", OwnerDTO.NONE),
            new ShipYardDTO("11-12", OwnerDTO.NONE),
            new ShipYardDTO("11-13", OwnerDTO.PLAYER1),
            new ShipYardDTO("12-14", OwnerDTO.NONE),
            new ShipYardDTO("14-15", OwnerDTO.PLAYER2),
            new ShipYardDTO("14-16", OwnerDTO.NONE));
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
            GAME_TITLE,
            new PlayerDTO(ACCOUNT_PLAYER_1, JOHN, new InventoryDTO(0, 0, 1, 0, 1), HashSet.of()),
            new PlayerDTO(ACCOUNT_PLAYER_2, WICK, new InventoryDTO(0, 0, 1, 0, 1), HashSet.of()),
            new InventoryDTO(17, 17, 15, 17, 15),
            List.of(
                new BuoyDTO(WOOD),
                new BuoyDTO(GOLD),
                new BuoyDTO(PINEAPPLE),
                new BuoyDTO(SHEEP),
                new BuoyDTO(SWORD)),
            INITIAL_FORT_MAP,
            INITIAL_SHIP_MAP));
  }
}
