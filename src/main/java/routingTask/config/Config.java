package routingTask.config;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;
import routingTask.routing.DataSourceRouting;

import javax.transaction.SystemException;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableTransactionManagement
@DependsOn("dataSourceRouting")
@EnableJpaRepositories(basePackages = "routingTask")
public class Config {
    private final DataSourceRouting dataSourceRouting;

    @Bean
    @Primary
    public DataSourceRouting dataSource() {
        return dataSourceRouting;
    }

    @Bean(initMethod = "init", destroyMethod = "close")
    public UserTransactionManager userTransactionManager() throws SystemException {
        UserTransactionManager userTransactionManager = new UserTransactionManager();
        userTransactionManager.setTransactionTimeout(300);
        userTransactionManager.setForceShutdown(true);
        return userTransactionManager;
    }

    @Bean
    public JtaTransactionManager transactionManager() throws SystemException {
        JtaTransactionManager jtaTransactionManager = new JtaTransactionManager();
        jtaTransactionManager.setTransactionManager(userTransactionManager());
        jtaTransactionManager.setUserTransaction(userTransactionImp());
        return jtaTransactionManager;
    }

    @Bean
    public UserTransactionImp userTransactionImp() {
        UserTransactionImp userTransactionImp = new UserTransactionImp();
        try {
            userTransactionImp.setTransactionTimeout(300);
        } catch (SystemException se) {
            return null;
        }
        return userTransactionImp;
    }

}
