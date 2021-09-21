package digital.number.scanner.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Spliterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Slf4j
@Component
public class FileParser {

  public void parseFile(String fileName, BiConsumer<Integer, String> consumer) {
    log.debug("parseFile( {} )", fileName);
    try (FileChannel fileChannel = FileChannel.open(Paths.get(fileName), StandardOpenOption.READ)) {
      parseFileChannel(fileChannel, consumer);
    } catch (IOException e) {
      log.error("Error parsing file: {}", fileName, e);
      throw new UncheckedIOException(e);
    }
  }

  private void parseFileChannel(FileChannel fileChannel, BiConsumer<Integer, String> consumer) {
    try (BufferedReader br = new BufferedReader(Channels.newReader(fileChannel, StandardCharsets.UTF_8))) {
      Spliterator<String> spliterator = br.lines().spliterator();

      AtomicInteger lineIndex = new AtomicInteger(1);
      Consumer<String> decoratedConsumer = (value) -> consumer.accept(lineIndex.get(), value);

      boolean hasNext = spliterator.tryAdvance(decoratedConsumer);
      while (hasNext) {
        lineIndex.getAndIncrement();
        hasNext = spliterator.tryAdvance(decoratedConsumer);
      }
    } catch (IOException e) {
      log.error("Exception parsing file: ", e);
      throw new UncheckedIOException(e);
    }
  }

}
