package digital.number.scanner.config;

import digital.number.scanner.service.AbstractSpringBootTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class DigitalSymbolMatchConfigTest extends AbstractSpringBootTest {

  @Autowired
  private DigitalSymbolMatchConfig sut;

  @Test
  public void shouldLoadProperties() {
    assertThat(sut.getSymbolMap().size()).isEqualTo(10);
  }
}