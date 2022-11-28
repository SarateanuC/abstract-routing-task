package routingTask.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import routingTask.routing.DataSourceRouting;

@Configuration
@RequiredArgsConstructor
@DependsOn("dataSourceRouting")
public class Config {
    private final DataSourceRouting dataSourceRouting;

    @Bean
    @Primary
    public DataSourceRouting dataSource() {
        return dataSourceRouting;
    }
}
