package nl.robinthedev.catanjr.game.model.resources;

interface Resource {
  int value();

  default boolean lt(Integer other) {
    return value() < other;
  }
}
