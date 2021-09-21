package digital.number.scanner.service.processor;

import digital.number.scanner.service.AbstractSpringBootTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class DigitalNumberProcessorTest extends AbstractSpringBootTest {

  @Autowired
  private DigitalNumberProcessor sut;

  @Test
  public void shouldProcessDigitalNumber() {
    assertThat(sut.apply(getDigitalNumber1To9())).isEqualTo("123456789");
  }

  @Test
  public void shouldIdentifyInvalidSymbols() {
    assertThat(sut.apply(getDigitalNumberWhenIllegalChar())).isEqualTo("123?5?789");
  }

}