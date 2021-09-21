package digital.number.scanner.service;

import com.google.common.collect.ImmutableList;
import digital.number.scanner.model.DigitalNumber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.regex.Pattern;

@Component
@Slf4j
public class DigitalNumberChunkerV2 {

  private final static Pattern NON_WHITESPACE_REGEX = Pattern.compile("\\S");
  private final static List<String> INVALID_DIGITAL_NUMBER = ImmutableList.of("_");

  private final int chunkSize;
  private final List<String> buffer;

  public DigitalNumberChunkerV2(@Value("${parser.chunker.size}") int chunkSize) {
    this.chunkSize = chunkSize;
    buffer = new ArrayList<>(chunkSize);
  }

  public BiConsumer<Integer, String> apply(Consumer<DigitalNumber> consumer) {
    return (lineNumber, line) -> {
      if (NON_WHITESPACE_REGEX.matcher(line).find()) {
        buffer.add(line);
      } else {
        if (!buffer.isEmpty() && buffer.size() < chunkSize) {
          log.error("Invalid format on line: {}", lineNumber);
          consumer.accept(new DigitalNumber(INVALID_DIGITAL_NUMBER, lineNumber - chunkSize + 1));
          buffer.clear();
        }
      }
      if (buffer.size() == chunkSize) {
        consumer.accept(new DigitalNumber(buffer, lineNumber - chunkSize + 1));
        buffer.clear();
      }
    };
  }

  private Predicate<String> nonWhiteSpace() {
    return (value) -> NON_WHITESPACE_REGEX.matcher(value).find();
  }

}
