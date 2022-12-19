package routingTask.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import routingTask.routing.DataSourceRouting;

import javax.transaction.*;

@Configuration
@RequiredArgsConstructor
@EnableTransactionManagement
@DependsOn("dataSourceRouting")
public class Config {
    private final DataSourceRouting dataSourceRouting;

    @Bean
    @Primary
    public DataSourceRouting dataSource() {
        return dataSourceRouting;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager();
    }

    @Bean
    public UserTransaction userTransaction() {
        return new UserTransaction() {
            @Override
            public void begin() throws NotSupportedException, SystemException {

            }

            @Override
            public void commit() throws RollbackException, HeuristicMixedException, HeuristicRollbackException, SecurityException, IllegalStateException, SystemException {

            }

            @Override
            public void rollback() throws IllegalStateException, SecurityException, SystemException {

            }

            @Override
            public void setRollbackOnly() throws IllegalStateException, SystemException {
            }

            @Override
            public int getStatus() throws SystemException {
                return 0;
            }

            @Override
            public void setTransactionTimeout(int i) throws SystemException {

            }
        };
    }
}
