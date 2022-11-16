package routing_task.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "primarydatasource.datasource")
public class PrimaryDataSourceConfig {
    private String url;
    private String password;
    private String username;
}
