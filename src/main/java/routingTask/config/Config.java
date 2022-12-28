package routingTask.config;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;
import routingTask.routing.DataSourceRouting;

import javax.transaction.SystemException;

@Configuration
@RequiredArgsConstructor
@EnableTransactionManagement
@DependsOn("dataSourceRouting")
@EnableJdbcRepositories(basePackages = "routingTask")
public class Config {
    private final DataSourceRouting dataSourceRouting;

    @Bean
    @Primary
    public DataSourceRouting dataSource() {
        return dataSourceRouting;
    }

    @Bean
    public NamedParameterJdbcOperations operations() {
        return new NamedParameterJdbcTemplate(dataSource());
    }

    @Bean(initMethod = "init", destroyMethod = "close")
    public UserTransactionManager userTransactionManager() throws SystemException {
        UserTransactionManager userTransactionManager = new UserTransactionManager();
        userTransactionManager.setTransactionTimeout(300);
        userTransactionManager.setForceShutdown(true);
        return userTransactionManager;
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


    @Bean
    @DependsOn({"userTransactionImp", "userTransactionManager"})
    public JtaTransactionManager transactionManager() throws SystemException {
        JtaTransactionManager jtaTransactionManager = new JtaTransactionManager();
        jtaTransactionManager.setTransactionManager(userTransactionManager());
        jtaTransactionManager.setUserTransaction(userTransactionManager());
        return jtaTransactionManager;
    }

}
