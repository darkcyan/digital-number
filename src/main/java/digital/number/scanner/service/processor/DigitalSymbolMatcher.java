package digital.number.scanner.service.processor;

import com.google.common.collect.ImmutableMap;
import digital.number.scanner.config.DigitalSymbolMatchConfig;
import digital.number.scanner.model.DigitalSymbol;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class DigitalSymbolMatcher {

  public final static String NON_MATCHED_SYMBOL = "?";

  private final Map<Integer, String> digitalSymbolMap;

  public DigitalSymbolMatcher(DigitalSymbolMatchConfig config) {
    log.debug("Adding {} symbols to matcher:", config.getSymbolMap().size());
    ImmutableMap.Builder<Integer, String> builder = ImmutableMap.builder();

    config.getSymbolMap().forEach((key, value) -> builder.put(key.hashCode(), value));

    digitalSymbolMap = builder.build();
  }

  public String match(DigitalSymbol digitalSymbol) {
    log.debug("match( {} )", digitalSymbol);
    return digitalSymbolMap.getOrDefault(digitalSymbol.getSymbol().hashCode(), NON_MATCHED_SYMBOL);
  }

}
