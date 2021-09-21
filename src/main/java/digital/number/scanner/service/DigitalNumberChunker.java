package digital.number.scanner.service;

import com.google.common.collect.ImmutableList;
import digital.number.scanner.model.DigitalNumber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.regex.Pattern;

@Component
@Slf4j
public class DigitalNumberChunker implements Chunker<DigitalNumber, String> {

  private final static Pattern NON_WHITESPACE_REGEX = Pattern.compile("\\S");
  private final static List<String> INVALID_DIGITAL_NUMBER = ImmutableList.of("_");
  private final int chunkSize;

  public DigitalNumberChunker(@Value("${parser.chunker.size}") int chunkSize) {
    this.chunkSize = chunkSize;
  }

  @Override
  public Consumer<Spliterator<String>> apply(Consumer<DigitalNumber> consumer) {
    return (spliterator) -> {
      AtomicInteger startLine = new AtomicInteger(1);
      List<String> buffer = new ArrayList<>(chunkSize);

      Consumer<String> filteredConsumer = filteredConsumer(nonWhiteSpace(), buffer::add, buffer);

      boolean hasNext = spliterator.tryAdvance(filteredConsumer);
      while (hasNext) {
        if (buffer.size() == chunkSize) {
          consumer.accept(new DigitalNumber(buffer, startLine.get()));
          buffer.clear();
          startLine.addAndGet(chunkSize);
        } else {
          hasNext = spliterator.tryAdvance(filteredConsumer);
          if (!hasNext && !buffer.isEmpty()) {
            log.error("Invalid truncated number on line: {}", startLine);
            consumer.accept(new DigitalNumber(INVALID_DIGITAL_NUMBER, startLine.get()));
            startLine.addAndGet(buffer.size());
            buffer.clear();
            hasNext = spliterator.tryAdvance(filteredConsumer);
          }
        }
      }
    };
  }

  private Predicate<String> nonWhiteSpace() {
    return (value) -> NON_WHITESPACE_REGEX.matcher(value).find();
  }

  private Consumer<String> filteredConsumer(Predicate<String> predicate, Consumer<String> consumer, List<String> buffer) {
    return (value) -> {
      if (predicate.test(value)) {
        consumer.accept(value);
      } else {
        buffer.clear();
      }
    };
  }
}
