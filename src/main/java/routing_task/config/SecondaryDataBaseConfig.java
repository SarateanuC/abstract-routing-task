package routing_task.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
@RequiredArgsConstructor
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "secondaryEntityManager",
        transactionManagerRef = "secondaryTransactionManager",
        basePackages = "routing_task.targetEntities"
)
public class SecondaryDataBaseConfig {
    private final DataSourceRouting dataSourceRouting;

    @Bean("secondaryDataSource")
    public DataSource dataSourceMain() {
        return dataSourceRouting;
    }

    @Bean(name = "secondaryEntityManager")
    public LocalContainerEntityManagerFactoryBean secondaryEntityManager(
            EntityManagerFactoryBuilder builder,
            @Qualifier("secondaryDataSource") DataSource secondaryDataSource) {
        Map<String, String> secondaryJpaProperties = new HashMap<>();
        secondaryJpaProperties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        secondaryJpaProperties.put("hibernate.hnm2ddl.auto", "update");
        return builder.dataSource(secondaryDataSource)
                .packages("routing_task.targetEntities")
                .properties(secondaryJpaProperties)
                .persistenceUnit("secondaryDataSource")
                .build();
    }

    @Bean(name = "secondaryTransactionManager")
    public PlatformTransactionManager secondaryTransactionManager(
            @Qualifier("secondaryEntityManager") EntityManagerFactory entityManagerFactory){
        return new JpaTransactionManager(entityManagerFactory);
    }
}
