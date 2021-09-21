package digital.number.scanner.service;

import digital.number.scanner.model.DigitalNumber;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public abstract class AbstractSpringBootTest {

  protected DigitalNumber getDigitalNumber1To9() {
    List<String> lines = Arrays.asList(
        "    _  _     _  _  _  _  _ ",
        "  | _| _||_||_ |_   ||_||_|",
        "  ||_  _|  | _||_|  ||_| _|");

    return new DigitalNumber(lines, 0);
  }

  protected DigitalNumber getDigitalNumberWhenIllegalChar() {
    List<String> lines = Arrays.asList(
        "    _  _     _  _  _  _  _ ",
        "  | _| _|| ||_ |_   ||_||_|",
        "  ||_  _|  | _|| |  ||_| _|");

    return new DigitalNumber(lines, 0);
  }


}
