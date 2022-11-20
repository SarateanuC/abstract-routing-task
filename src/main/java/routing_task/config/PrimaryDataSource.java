package routing_task.config;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "datasource.primary")
public class PrimaryDataSource {
    private String url;
    private String password;
    private String username;
}
