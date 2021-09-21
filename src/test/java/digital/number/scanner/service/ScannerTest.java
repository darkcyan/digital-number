package digital.number.scanner.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

public class ScannerTest extends AbstractSpringBootTest {

  @Autowired
  private Scanner scanner;

  @Test
  public void shouldScanMultipleNumbers() {
    assertThat(scanner.scanAndReturn(getInputFile("multiple_numbers.txt")))
        .containsExactly("123456789", "123456789", "123456789");
  }

  @Test
  public void shouldIdentifyInvalidNumbers() {
    assertThat(scanner.scanAndReturn(getInputFile("invalid_numbers.txt")))
        .containsExactly("123456789", "12?456?8?" + DefaultNumberWriter.INVALID_NUMBER_SUFFIX, "123456789");
  }

  @Test
  public void shouldScanMultipleNumbersAndWriteToConsole() {
    scanner.scan(getInputFile("multiple_numbers.txt"));
  }

  private String getInputFile(String fileName) {
    return Paths.get("src", "test", "resources", "input", fileName).toAbsolutePath().toString();
  }

}