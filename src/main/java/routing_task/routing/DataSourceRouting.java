package routing_task.routing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;
import routing_task.contextHolder.DataSourceContextHolder;
import routing_task.service.DataSourceService;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

import static routing_task.context.DataSourceEnum.*;

@Component
public class DataSourceRouting extends AbstractRoutingDataSource {
    private DataSourceContextHolder dataSourceContextHolder;
    @Autowired
    private DataSourceService service;


    public DataSourceRouting(DataSourceContextHolder dataSourceContextHolder, DataSourceService service) {
        this.dataSourceContextHolder = dataSourceContextHolder;
        this.service = service;
        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put(DATASOURCE_ONE, this.service.selectAll().get(0));
        dataSourceMap.put(DATASOURCE_TWO, this.service.selectAll().get(1));
        dataSourceMap.put(DATASOURCE_THREE, this.service.selectAll().get(2));
        this.setTargetDataSources(dataSourceMap);
        this.setDefaultTargetDataSource(setDefaultDataSource());
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return dataSourceContextHolder.getBranchContext();
    }

    public DataSource setDefaultDataSource() {
        DriverManagerDataSource defaultDataSource = new DriverManagerDataSource();
        defaultDataSource.setDriverClassName("org.postgresql.Driver");
        defaultDataSource.setUrl("jdbc:postgresql://localhost:5432/database_connector");
        defaultDataSource.setUsername("postgres");
        defaultDataSource.setPassword("postgres");
        return defaultDataSource;
    }
}
