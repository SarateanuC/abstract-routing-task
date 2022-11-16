package routing_task.routing;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;
import routing_task.config.DataSourceOneConfig;
import routing_task.config.DataSourceThreeConfig;
import routing_task.config.DataSourceTwoConfig;
import routing_task.config.PrimaryDataSourceConfig;
import routing_task.contextHolder.DataSourceContextHolder;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

import static routing_task.context.DataSourceEnum.*;

@Component
public class DataSourceRouting extends AbstractRoutingDataSource {
    private DataSourceOneConfig dataSourceOneConfig;
    private DataSourceTwoConfig dataSourceTwoConfig;
    private DataSourceThreeConfig dataSourceThreeConfig;
    private PrimaryDataSourceConfig primaryDataSourceConfig;
    private DataSourceContextHolder dataSourceContextHolder;

    public DataSourceRouting(DataSourceContextHolder dataSourceContextHolder, DataSourceOneConfig dataSourceOneConfig, DataSourceTwoConfig dataSourceTwoConfig,
                             DataSourceThreeConfig dataSourceThreeConfig, PrimaryDataSourceConfig primaryDataSourceConfig) {
        this.dataSourceOneConfig = dataSourceOneConfig;
        this.dataSourceTwoConfig = dataSourceTwoConfig;
        this.dataSourceThreeConfig = dataSourceThreeConfig;
        this.primaryDataSourceConfig = primaryDataSourceConfig;
        this.dataSourceContextHolder = dataSourceContextHolder;

        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put(DATASOURCE_ONE, dataSourceOneDataSource());
        dataSourceMap.put(DATASOURCE_TWO, dataSourceTwoDataSource());
        dataSourceMap.put(DATASOURCE_THREE, dataSourceThreeDataSource());
        dataSourceMap.put(PRIMARY_DATASOURCE, primaryDataSourceDataSource());
        this.setTargetDataSources(dataSourceMap);
        this.setDefaultTargetDataSource(primaryDataSourceDataSource());
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return dataSourceContextHolder.getBranchContext();
    }

    public DataSource dataSourceOneDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(dataSourceOneConfig.getUrl());
        dataSource.setUsername(dataSourceOneConfig.getUsername());
        dataSource.setPassword(dataSourceOneConfig.getPassword());
        return dataSource;
    }

    public DataSource dataSourceTwoDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(dataSourceTwoConfig.getUrl());
        dataSource.setUsername(dataSourceTwoConfig.getUsername());
        dataSource.setPassword(dataSourceTwoConfig.getPassword());
        return dataSource;
    }

    public DataSource dataSourceThreeDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(dataSourceThreeConfig.getUrl());
        dataSource.setUsername(dataSourceThreeConfig.getUsername());
        dataSource.setPassword(dataSourceThreeConfig.getPassword());
        return dataSource;
    }

    public DataSource primaryDataSourceDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(primaryDataSourceConfig.getUrl());
        dataSource.setUsername(primaryDataSourceConfig.getUsername());
        dataSource.setPassword(primaryDataSourceConfig.getPassword());
        return dataSource;
    }
}
