package digital.number.scanner.service.processor;

import digital.number.scanner.model.DigitalSymbol;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class DigitalSymbolReaderTest {

  private DigitalSymbolReader sut;

  @Test
  public void shouldParse3x3SymbolsWhen9PerLine() {
    sut = new DigitalSymbolReader(3, 3, 9);
    List<String> lines = Arrays.asList(
        " _  _  _  _  _  _  _  _  _ ",
        "| || || || || || || || || |",
        "|_||_||_||_||_||_||_||_||_|");

    assertThat(sut.parse(lines))
        .hasSize(9)
        .extracting(DigitalSymbol::getSymbol)
        .allMatch(symbol -> symbol.equals(" _ | ||_|"));
  }

  @Test
  public void shouldParse3x3SymbolWhen3PerLine() {
    sut = new DigitalSymbolReader(3, 3, 3);
    List<String> lines = Arrays.asList(
        "    _  _ ",
        "  | _| _|",
        "  ||_  _|");

    assertThat(sut.parse(lines))
        .hasSize(3)
        .extracting(DigitalSymbol::getSymbol)
        .containsExactly("     |  |", " _  _||_ ", " _  _| _|");
  }

  @Test
  public void shouldParse5x4SymbolsWhen3PerLine() {
    sut = new DigitalSymbolReader(5, 4, 3);
    List<String> lines = Arrays.asList(
        " __  __  ___",
        "|  ||  ||   ",
        "|__||__||   ",
        "|  ||  ||   ",
        "|  ||__| ___");

    assertThat(sut.parse(lines))
        .hasSize(3)
        .extracting(DigitalSymbol::getSymbol)
        .containsExactly(" __ |  ||__||  ||  |", " __ |  ||__||  ||__|", " ___|   |   |    ___");
  }
}