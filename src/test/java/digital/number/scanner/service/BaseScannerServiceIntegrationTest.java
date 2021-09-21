package digital.number.scanner.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.test.context.TestContextManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(Parameterized.class)
public abstract class BaseScannerServiceIntegrationTest {
  private static final String INPUT_DIR = "input";
  private static final String OUTPUT_DIR = "output";

  private TestContextManager testContextManager;

  @Before
  public void setUpContext() throws Exception {
    this.testContextManager = new TestContextManager(getClass());
    this.testContextManager.prepareTestInstance(this);
  }

  @Parameterized.Parameters(name = "{0}")
  public static List<String> inputFileNames() throws IOException {
    Path inputDirPath = Paths.get("src", "test", "resources", INPUT_DIR);
    return Files.list(inputDirPath)
        .map(Path::getFileName)
        .map(Path::toString)
        .collect(Collectors.toList());
  }

  @Parameterized.Parameter
  public String inputFileName;

  protected abstract List<String> performScanning(final String inputFilePath);

  @Test
  public final void testScanner() throws IOException {
    String inputFilePath = Paths.get("src", "test", "resources", INPUT_DIR, inputFileName).toAbsolutePath().toString();
    List<String> expectedOutput = Files.readAllLines(Paths.get("src", "test", "resources", OUTPUT_DIR, inputFileName));
    assertThat(performScanning(inputFilePath)).containsExactlyElementsOf(expectedOutput);
  }
}
