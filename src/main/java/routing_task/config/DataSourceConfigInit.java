package routing_task.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "routing_task.main",
        transactionManagerRef = "primaryTransactionManager",
        entityManagerFactoryRef = "primaryEntityManager")
public class DataSourceConfigInit {

    @Bean("primaryDatabase")
    @Primary
    public DataSource dataSourceMain(PrimaryDataSource primaryDataSource) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setUsername(primaryDataSource.getUsername());
        hikariConfig.setPassword(primaryDataSource.getPassword());
        hikariConfig.setJdbcUrl(primaryDataSource.getUrl());
        return new HikariDataSource(hikariConfig);
    }

    @Bean(name = "primaryEntityManager")
    @Primary
    public LocalContainerEntityManagerFactoryBean primaryEntityManagerFactoryBean(
            EntityManagerFactoryBuilder builder,
            @Qualifier("primaryDatabase") DataSource primaryDataSource) {
        Map<String, String> primaryJpaProperties = new HashMap<>();
        primaryJpaProperties.put("hibernate.fialect", "org.hibernate.dialect.PostgreSQLDialect");
        primaryJpaProperties.put("hibernate.hbm2ddl.auto", "update");
        return builder.dataSource(primaryDataSource)
                .packages("routing_task.main")
                .persistenceUnit("primaryDataSource")
                .properties(primaryJpaProperties)
                .build();
    }

    @Bean(name = "primaryTransactionManager")
    @Primary
    public PlatformTransactionManager primaryTransactionManager(
            @Autowired @Qualifier("primaryEntityManager") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
