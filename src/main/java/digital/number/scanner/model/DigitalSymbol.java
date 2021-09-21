package digital.number.scanner.model;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class DigitalSymbol {

  String symbol;

  public String getSymbol() {
    return symbol;
  }
}
