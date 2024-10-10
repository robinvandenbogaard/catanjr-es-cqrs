package nl.robinthedev.catanjr.application.sse;

import java.util.List;
import nl.robinthedev.catanjr.api.dto.BuoyDTO;

record BuoysState(String buoy1, String buoy2, String buoy3, String buoy4, String buoy5) {
  public static BuoysState from(List<BuoyDTO> buoys) {
    return new BuoysState(
        getStyleClassName(buoys, 0),
        getStyleClassName(buoys, 1),
        getStyleClassName(buoys, 2),
        getStyleClassName(buoys, 3),
        getStyleClassName(buoys, 4));
  }

  private static String getStyleClassName(List<BuoyDTO> buoys, int index) {
    return buoys.get(index).resourceType().name().toLowerCase();
  }
}
