package routing_task.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.SneakyThrows;
import lombok.val;
import org.springframework.stereotype.Component;
import routing_task.config.enums.DataSourceEnum;
import routing_task.main.repository.NextDbConnectionRepository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Component
public class ConnectionService {
    private final NextDbConnectionRepository dataSourceRepository;
    private final Map<Object, Object> dbRoutingMap = new HashMap<>();

    @SneakyThrows
    public ConnectionService(NextDbConnectionRepository nextDbConnectionRepository) {
        this.dataSourceRepository = nextDbConnectionRepository;
        DataSourceEnum.getListOfDb().forEach(e -> dbRoutingMap.put(e, createDataSource(e)));
    }

    public Map<Object, Object> getDataSourceTypeMap() {
        return dbRoutingMap;
    }

    private DataSource createDataSource(DataSourceEnum dataSourceEnum) {
        val nextDBConnection = dataSourceRepository.findById(dataSourceEnum)
                .orElseThrow();
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(nextDBConnection.getUrl());
        hikariConfig.setPassword(nextDBConnection.getPassword());
        hikariConfig.setUsername(nextDBConnection.getUsername());
        return new HikariDataSource(hikariConfig);
    }
}
