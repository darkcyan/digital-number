package digital.number.scanner.service;

import digital.number.scanner.model.DigitalNumber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
@Slf4j
public class Scanner {

  private final FileParser fileParser;
  private final Chunker<DigitalNumber, Integer, String> chunker;
  private final Function<DigitalNumber, String> processDigitalNumber;
  private final NumberWriter<String, String> outputNumber;

  public List<String> scanAndReturn(String filename) {
    log.info("scanAndReturn: {}", filename);
    List<String> result = new ArrayList<>();
    fileParser.parseFile(filename, chunker.apply(consume(result::add)));

    return result;
  }

  public void scan(String filename) {
    log.info("scan: {}", filename);
    Consumer<String> consoleWriter = System.out::println;
    fileParser.parseFile(filename, chunker.apply(consume(consoleWriter)));
  }

  private Consumer<DigitalNumber> consume(Consumer<String> consumer) {
    Function<DigitalNumber, String> mapper = (number) -> processDigitalNumber.andThen(outputNumber).apply(number);

    return (digitalNumber) -> consumer.accept(mapper.apply(digitalNumber));
  }
}
