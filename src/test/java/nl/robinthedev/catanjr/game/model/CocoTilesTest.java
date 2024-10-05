package nl.robinthedev.catanjr.game.model;

import static org.assertj.core.api.Assertions.assertThat;

import nl.robinthedev.catanjr.game.model.coco.CocoTile;
import nl.robinthedev.catanjr.game.model.coco.CocoTiles;
import nl.robinthedev.catanjr.game.model.coco.RemainingCocoTilesCount;
import org.junit.jupiter.api.Test;

class CocoTilesTest {

  private final CocoTiles tiles = CocoTiles.INITIAL;

  @Test
  void there_are_7_Captain_coco_tiles() {
    assertThat(tiles.remainingTilesCount(CocoTile.Captain))
        .isEqualTo(new RemainingCocoTilesCount(7));
  }

  @Test
  void there_are_5_Extension_coco_tiles() {
    assertThat(tiles.remainingTilesCount(CocoTile.Extension))
        .isEqualTo(new RemainingCocoTilesCount(5));
  }

  @Test
  void there_are_2_SheepSword_coco_tiles() {
    assertThat(tiles.remainingTilesCount(CocoTile.SheepSword))
        .isEqualTo(new RemainingCocoTilesCount(2));
  }

  @Test
  void there_are_2_PineAppleWood_coco_tiles() {
    assertThat(tiles.remainingTilesCount(CocoTile.PineAppleWood))
        .isEqualTo(new RemainingCocoTilesCount(2));
  }
}
