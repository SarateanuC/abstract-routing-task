package routingTask.routing;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.jdbc.datasource.AbstractDataSource;
import org.springframework.stereotype.Component;
import routingTask.entity.DbConnection;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class DataSourceRouting extends AbstractDataSource {
    private DataSource resolvedDataSources = null;

    private final DataSource resolvedDefaultDataSource;

    public DataSourceRouting(DataSourceProperties dataSourceProps) {
        resolvedDefaultDataSource = createDataSource(dataSourceProps.getUrl(), dataSourceProps.getUsername(), dataSourceProps.getPassword());
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

    public void addConnection(DbConnection dbConnections) {
        resolvedDataSources = createDataSource(dbConnections.getUrl(), dbConnections.getUsername(), dbConnections.getPassword());
    }

    @SneakyThrows
    public void closeConnection() {
        this.resolvedDataSources.unwrap(HikariDataSource.class).close();
        this.resolvedDataSources = null;
    }

    private DataSource createDataSource(String url, String username, String password) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);
        return new HikariDataSource(hikariConfig);
    }

}

