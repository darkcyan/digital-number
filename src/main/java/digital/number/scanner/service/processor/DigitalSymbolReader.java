package digital.number.scanner.service.processor;

import digital.number.scanner.model.DigitalSymbol;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class DigitalSymbolReader {

  private final int totalRows;
  private final int totalColumns;
  private final int symbolsPerLine;

  public DigitalSymbolReader(@Value("${digitalsymbols.reader.totalrows}") int totalRows,
                             @Value("${digitalsymbols.reader.totalColumns}") int totalColumns,
                             @Value("${digitalsymbols.reader.symbolsPerLine}") int symbolsPerLine) {
    this.totalRows = totalRows;
    this.totalColumns = totalColumns;
    this.symbolsPerLine = symbolsPerLine;
  }

  public List<DigitalSymbol> parse(List<String> lines) {
    log.trace("parse( {} )", lines);
    List<DigitalSymbol> digitalSymbols = new ArrayList<>();

    for (int symbolIndex = 0; symbolIndex < symbolsPerLine; symbolIndex++) {
      String symbol = parse(lines, symbolIndex * totalColumns);
      digitalSymbols.add(new DigitalSymbol(symbol));
    }
    return digitalSymbols;
  }

  private String parse(List<String> lines, final int startColumn) {
    log.trace("parse( {}, {} )", lines, startColumn);
    if (lines.isEmpty() || lines.size() != totalRows) {
      return " ";
    }
    StringBuilder builder = new StringBuilder();
    for (int row = 0; row < totalRows; row++) {
      String line = lines.get(row);
      int endIndex = startColumn + totalColumns;
      if (line.length() < endIndex) {
        return " ";
      }
      builder.append(line, startColumn, endIndex);
    }
    return builder.toString();
  }
}
