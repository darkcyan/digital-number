package digital.number.scanner.service.processor;

import digital.number.scanner.model.DigitalNumber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@Slf4j
@RequiredArgsConstructor
public class DigitalNumberProcessor implements Function<DigitalNumber, String> {

  private final DigitalSymbolReader symbolReader;
  private final DigitalSymbolMatcher symbolMatcher;

  @Override
  public String apply(DigitalNumber digitalNumber) {
    log.debug("process( {} )", digitalNumber);

    String value = symbolReader.parse(digitalNumber.getDigitalValues()).stream()
        .map(symbolMatcher::match)
        .reduce("", String::concat);

    log.debug("process( {} ) -> {}", digitalNumber, value);
    return value;
  }
}