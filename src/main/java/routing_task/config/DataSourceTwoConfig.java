package routing_task.config;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@Builder
//@ConfigurationProperties(prefix = "datasourcetwo.datasource")
public class DataSourceTwoConfig {
    private String url;
    private String password;
    private String username;
}
