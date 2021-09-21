package digital.number.scanner.service;

import com.google.common.collect.ImmutableList;
import digital.number.scanner.model.DigitalNumber;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
public class FileParserTest {

  private DigitalNumberChunkerV2 chunker;
  private FileParser sut;
  private List<DigitalNumber> digitalNumbers;

  @Before
  public void init() {
    chunker = new DigitalNumberChunkerV2(3);
    sut = new FileParser();
    digitalNumbers = new ArrayList<>();
  }

  @Test
  public void shouldReadFile() {
    sut.parseFile(getInputFile("zero.txt"), chunker.apply(digitalNumbers::add));
    assertThat(digitalNumbers).containsExactly(
        new DigitalNumber(ImmutableList.of(
            " _  _  _  _  _  _  _  _  _ ",
            "| || || || || || || || || |",
            "|_||_||_||_||_||_||_||_||_|"), 1));
  }

  @Test
  public void shouldReadMultipleNumbers() {
    sut.parseFile(getInputFile("multiple_numbers.txt"), chunker.apply(digitalNumbers::add));
    assertThat(digitalNumbers.size()).isEqualTo(3);
  }


  @Test
  public void shouldHandleTruncatedFile() {
    sut.parseFile(getInputFile("truncated_at_end.txt"), chunker.apply(digitalNumbers::add));
    assertThat(digitalNumbers.size()).isEqualTo(2);
  }

  @Test
  public void shouldHandleTruncatedInMiddleV2File() {
    sut.parseFile(getInputFile("truncated_in_middle_2.txt"), chunker.apply(digitalNumbers::add));
    assertThat(digitalNumbers).containsExactly(
        new DigitalNumber(ImmutableList.of(
            " _  _  _  _  _  _  _  _  _ ",
            "| || || || || || || || || |",
            "|_||_||_||_||_||_||_||_||_|"), 1),
        new DigitalNumber(ImmutableList.of(
            "_"), 5),
        new DigitalNumber(ImmutableList.of(
            "_"), 8),
        new DigitalNumber(ImmutableList.of(
            "    _  _  _  _  _  _  _  _ ",
            "|_||_ |_   | _| _|  ||_||_|",
            "  | _||_|  ||_  _|  ||_| _|"), 11));
  }

  @Test
  public void shouldThrowExceptionWhenFileNotFound() {
    assertThatThrownBy(() -> sut.parseFile(getInputFile("missing.txt"), chunker.apply(digitalNumbers::add)))
        .getRootCause().isInstanceOf(NoSuchFileException.class)
        .hasMessageContaining(getInputFile("missing.txt"));
  }

  private String getInputFile(String fileName) {
    return Paths.get("src", "test", "resources", "input", fileName).toAbsolutePath().toString();
  }

}