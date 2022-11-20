package routing_task.config;

import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import static routing_task.config.enums.DataSourceEnum.DATASOURCE_ONE;

@Component
@DependsOn("connectionService")
public class DataSourceRouting extends AbstractRoutingDataSource {
    private final DataSourceContextHolder dataSourceContextHolder;

    public DataSourceRouting(DataSourceContextHolder dataSourceContextHolder, ConnectionService connectionService) {
        this.dataSourceContextHolder = dataSourceContextHolder;
        this.setTargetDataSources(connectionService.getDataSourceTypeMap());
        this.setDefaultTargetDataSource(connectionService.getDataSourceTypeMap().get(DATASOURCE_ONE));
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return dataSourceContextHolder.getBranchContext();
    }
}
