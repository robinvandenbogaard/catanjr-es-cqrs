package nl.robinthedev.catanjr.game.model.resources;

interface Resource {

  int value();

  default void ifNotReducable(Integer other, Runnable task) {
    if (value() - other < 0) {
      task.run();
    }
  }
}
