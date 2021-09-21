package digital.number.scanner.service;

import digital.number.scanner.service.processor.DigitalSymbolMatcher;
import org.springframework.stereotype.Component;

@Component
public class DefaultNumberWriter implements NumberWriter<String, String> {

  static final String INVALID_NUMBER_SUFFIX = "ILL";

  @Override
  public String apply(String value) {
    StringBuilder builder = new StringBuilder(value);
    if (isInValid(value)) {
      writeInvalidValue(builder);
    }
    return builder.toString();
  }

  private void writeInvalidValue(StringBuilder builder) {
    builder.append(INVALID_NUMBER_SUFFIX);
  }

  private boolean isInValid(String value) {
    return value.contains(DigitalSymbolMatcher.NON_MATCHED_SYMBOL);
  }
}
