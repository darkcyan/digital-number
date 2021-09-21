package digital.number.scanner.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class ScannerIntegrationTest extends BaseScannerServiceIntegrationTest {

  @Autowired
  private Scanner scanner;

  @Override
  protected List<String> performScanning(String inputFilePath) {
    return scanner.scanAndReturn(inputFilePath);
  }
}
