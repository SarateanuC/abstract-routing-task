package routing_task.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import routing_task.routing.DataSourceRouting;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@RequiredArgsConstructor
@DependsOn("dataSourceRouting")
@EnableJpaRepositories(basePackages = "routing_task", transactionManagerRef = "transcationManager", entityManagerFactoryRef = "entityManager")
public class DataSourceConfig {
    private final DataSourceRouting dataSourceRouting;

    @Bean
    @Primary
    public DataSource dataSource() {
        return dataSourceRouting;
    }

    @Bean(name = "entityManager")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(EntityManagerFactoryBuilder builder) {
        return builder.dataSource(dataSource()).packages("routing_task").build();
    }

    @Bean(name = "transactionManager")
    public JpaTransactionManager transactionManager(
            @Autowired @Qualifier("entityManager") LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {
        return new JpaTransactionManager(entityManagerFactoryBean.getObject());
    }
}
