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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.util.StringUtils.hasText;

@Component
public class DataSourceRouting extends AbstractDataSource {
    private Map<String, DataSource> resolvedDataSources = null;
    private String country;

    private final DataSource resolvedDefaultDataSource;

    public DataSourceRouting(DataSourceProperties dataSourceProps) {
        resolvedDefaultDataSource = createDataSource(
                dataSourceProps.getUrl(),
                dataSourceProps.getUsername(),
                dataSourceProps.getPassword()
        );
    }

    @Override
    @SneakyThrows
    public Connection getConnection() {
        return isDataSourceSet() ? resolvedDataSources.get(country).getConnection() : this.resolvedDefaultDataSource.getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return isDataSourceSet() ? resolvedDataSources.get(country).getConnection(username, password) : this.resolvedDefaultDataSource.getConnection(username, password);
    }

    public void createConnections(List<DbConnection> dbConnections) {
        if (resolvedDataSources == null) {
            resolvedDataSources = new HashMap<>();
        }
        dbConnections.forEach(this::createConnection);
    }

    private void createConnection(DbConnection db) {
        DataSource dataSource = createDataSource(db.getUrl(), db.getUsername(), db.getPassword());
        resolvedDataSources.put(db.getId(), dataSource);
    }

    @SneakyThrows
    public void closeConnection() {
        this.resolvedDataSources.forEach((k, v) -> closeConnection(v));
        this.resolvedDataSources = null;
        this.country = null;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void rollBackCommits() {
        resolvedDataSources.forEach((k, v) -> getRollback(v));
    }

    @SneakyThrows
    private void getRollback(DataSource dataSource) {
        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(false);
        connection.rollback();
    }

    @SneakyThrows
    private void closeConnection(DataSource dataSource) {
        dataSource.getConnection().close();
        dataSource.unwrap(HikariDataSource.class).close();
    }

    private DataSource createDataSource(String url, String username, String password) {
        HikariConfig hikariConfig = new HikariConfig();
       // hikariConfig.setAutoCommit(false);
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);
        return new HikariDataSource(hikariConfig);
    }

    private boolean isDataSourceSet() {
        return resolvedDataSources != null && hasText(country);
    }

}

