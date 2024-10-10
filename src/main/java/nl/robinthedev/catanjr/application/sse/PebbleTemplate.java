package nl.robinthedev.catanjr.application.sse;

import io.pebbletemplates.pebble.PebbleEngine;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

class PebbleTemplate {
  public static String processTemplate(String template, Map<String, Object> context) {
    PebbleEngine engine = new PebbleEngine.Builder().build();
    io.pebbletemplates.pebble.template.PebbleTemplate compiledTemplate =
        engine.getTemplate("templates/" + template + ".html");

    Writer writer = new StringWriter();
    try {
      compiledTemplate.evaluate(writer, context);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    return writer.toString();
  }
}
