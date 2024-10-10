package nl.robinthedev.catanjr.application.sse;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.Test;

class PebbelTemplateTest {

  @Test
  void renders_properties_from_template_file() {
    assertThat(
            PebbleTemplate.processTemplate(
                "pebbleTemplateTest", Map.of("target", new ExampleData("myValue", "myAttribute"))))
        .isEqualTo("<h1 style=\"myAttribute\">myValue</h1>");
  }

  record ExampleData(String property, String attribute) {}
}
