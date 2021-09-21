package digital.number.scanner.model;

import lombok.Value;

import java.util.ArrayList;
import java.util.List;

@Value
public class DigitalNumber {

  List<String> digitalValues;
  int startLine;

  public DigitalNumber(List<String> digitalValues, int startLine) {
    this.digitalValues = new ArrayList<>(digitalValues);
    this.startLine = startLine;
  }

  public List<String> getDigitalValues() {
    return new ArrayList<>(digitalValues);
  }

  public int getStartLine() {
    return startLine;
  }
}
