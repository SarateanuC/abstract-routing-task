package routingTask.config;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;
import routingTask.routing.DataSourceRouting;

import javax.transaction.SystemException;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableTransactionManagement
@DependsOn("dataSourceRouting")
@EnableJpaRepositories(basePackages = "routingTask.repository")
public class Config {
    private final Logger logger =
            LoggerFactory.getLogger(Config.class);
    private final DataSourceRouting dataSourceRouting;

    @Bean
    @Primary
    public DataSourceRouting dataSource() {
        return dataSourceRouting;
    }

    @Bean (initMethod = "init", destroyMethod = "close")
    @DependsOn("userService")
    public UserTransactionManager atomikosTransactionManager() {
        UserTransactionManager utm =
                new UserTransactionManager();
        utm.setStartupTransactionService(false);
        utm.setForceShutdown(true);
        return utm;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JtaTransactionManager ptm =
                new JtaTransactionManager();
        ptm.setTransactionManager(atomikosTransactionManager());
        ptm.setUserTransaction(atomikosTransactionManager());
        return ptm;
    }

    @Bean
    public UserTransactionImp userTransactionImp() {
        UserTransactionImp userTransactionImp = new UserTransactionImp();
        try {
            userTransactionImp.setTransactionTimeout(300);
        } catch (SystemException se) {
            logger.error("Configuration exception.", se);
            return null;
        }
        return userTransactionImp;
    }
}
