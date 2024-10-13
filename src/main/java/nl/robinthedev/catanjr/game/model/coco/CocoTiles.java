package nl.robinthedev.catanjr.game.model.coco;

import io.vavr.collection.List;
import java.util.Objects;

public record CocoTiles(List<CocoTile> tiles) {
  public static final CocoTiles INITIAL =
      new CocoTiles(
          List.of(
              CocoTile.Captain,
              CocoTile.Captain,
              CocoTile.Captain,
              CocoTile.Captain,
              CocoTile.Captain,
              CocoTile.Captain,
              CocoTile.Captain,
              CocoTile.Extension,
              CocoTile.Extension,
              CocoTile.Extension,
              CocoTile.Extension,
              CocoTile.Extension,
              CocoTile.PineAppleWood,
              CocoTile.PineAppleWood,
              CocoTile.SheepSword,
              CocoTile.SheepSword));

  public CocoTiles {
    Objects.requireNonNull(tiles, "Tiles must be provided, can't be null");
  }

  public RemainingCocoTilesCount remainingTilesCount() {
    return new RemainingCocoTilesCount(tiles.size());
  }

  public RemainingCocoTilesCount remainingTilesCount(CocoTile cocoTile) {
    return new RemainingCocoTilesCount(tiles.count(cocoTile::equals));
  }
}
