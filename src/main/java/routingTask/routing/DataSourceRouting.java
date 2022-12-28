package routingTask.routing;

import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.jdbc.datasource.AbstractDataSource;
import org.springframework.stereotype.Component;
import routingTask.entity.DbConnection;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

@Component
public class DataSourceRouting extends AbstractDataSource {
    private DataSource resolvedDataSources = null;

    private final DataSource resolvedDefaultDataSource;

    public DataSourceRouting(DataSourceProperties dataSourceProps) {
        resolvedDefaultDataSource = createDataSource(dataSourceProps.getName(), dataSourceProps.getUrl(),
                dataSourceProps.determineUsername(), dataSourceProps.getPassword());
    }

    @Override
    @SneakyThrows
    public Connection getConnection() {
        return resolvedDataSources != null ? resolvedDataSources.getConnection() : this.resolvedDefaultDataSource.getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return resolvedDataSources != null ? resolvedDataSources.getConnection(username, password) : this.resolvedDefaultDataSource.getConnection(username, password);
    }


    public void addConnection(DbConnection db) {
        resolvedDataSources = createDataSource(db.getId(), db.getUrl(), db.getUsername(), db.getPassword());
    }

    @SneakyThrows
    public void closeConnection() {
        resolvedDataSources.getConnection().close();
    }

    private DataSource createDataSource(String id, String url, String username, String password) {
        AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
        atomikosDataSourceBean.setUniqueResourceName(id);
        atomikosDataSourceBean.setXaDataSourceClassName("org.postgresql.xa.PGXADataSource");
        Properties p = new Properties();
        p.setProperty("user", username);
        p.setProperty("serverName", "localhost");
        p.setProperty("portNumber", "5432");
        p.setProperty("password", password);
        p.setProperty("url", url);
        atomikosDataSourceBean.setXaProperties(p);
        atomikosDataSourceBean.setPoolSize(10);
        return atomikosDataSourceBean;
    }
}

