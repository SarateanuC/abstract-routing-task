package routing_task.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mybatis.repository.config.EnableMybatisRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import routing_task.routing.DataSourceRouting;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
@RequiredArgsConstructor
@DependsOn("dataSourceRouting")
@EnableMybatisRepositories(basePackages = "routing_task", transactionManagerRef = "transcationManager")
public class DataSourceConfig {
    private final DataSourceRouting dataSourceRouting;

    @Bean
    @Primary
    public DataSource dataSource() {
        return dataSourceRouting;
    }
}
