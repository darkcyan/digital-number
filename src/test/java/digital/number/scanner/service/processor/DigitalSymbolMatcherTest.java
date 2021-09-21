package digital.number.scanner.service.processor;

import digital.number.scanner.config.DigitalSymbolMatchConfig;
import digital.number.scanner.model.DigitalSymbol;
import digital.number.scanner.service.AbstractSpringBootTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class DigitalSymbolMatcherTest extends AbstractSpringBootTest {

  @Autowired
  DigitalSymbolMatchConfig matchConfig;

  @Autowired
  private DigitalSymbolMatcher matcher;

  @Test
  public void shouldMatchSymbols1To9() {
    assertThat(matchConfig.getSymbolMap()).isNotEmpty();

    assertThat(matchConfig.getSymbolMap()).allSatisfy((key, value) ->
        assertThat(matcher.match(new DigitalSymbol(key))).isEqualTo(value));
  }

  @Test
  public void shouldHandleUnmatchedSymbol() {
    assertThat(matcher.match(new DigitalSymbol("unmatched"))).isEqualTo(DigitalSymbolMatcher.NON_MATCHED_SYMBOL);
  }


}