package digital.number.scanner.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@Data
@ConfigurationProperties(prefix = "digitalsymbols")
public class DigitalSymbolMatchConfig {
  private Map<String, String> symbolMap;

}
