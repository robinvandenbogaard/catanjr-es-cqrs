package nl.robinthedev.catanjr.game.model.board;



class LandTile {
  private final ResourceType resourceType;
  private final RequiredDiceRoll requiredDiceRoll;
  private Occupant occupant;

  private LandTile(ResourceType resourceType, RequiredDiceRoll requiredDiceRoll) {
    this.resourceType = resourceType;
    this.requiredDiceRoll = requiredDiceRoll;
    this.occupant = Occupant.EMPTY;
  }

  public static LandTile of(ResourceType resourceType, RequiredDiceRoll requiredDiceRoll) {
    return new LandTile(resourceType, requiredDiceRoll);
  }
}
