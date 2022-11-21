package routing_task.routing;

import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;
import routing_task.contextHolder.DataSourceContextHolder;
import routing_task.service.ConnectionService;

import java.util.HashMap;
import java.util.Map;

@Component
//@DependsOn("connectionService")
public class DataSourceRouting extends AbstractRoutingDataSource {
    private final DataSourceContextHolder dataSourceContextHolder;
    private final ApplicationContext applicationContext;

    public DataSourceRouting(DataSourceContextHolder dataSourceContextHolder, ConnectionService connectionService, ApplicationContext applicationContext) {
        this.dataSourceContextHolder = dataSourceContextHolder;
        this.applicationContext = applicationContext;
        this.setDefaultTargetDataSource(applicationContext.getBean("primaryDatabase"));
        Map<Object,Object> nextDBConnectionMap = new HashMap<>();
        //nextDbConnectionRepository.findAll().forEach(e->nextDBConnectionMap.put(e.getDataSourceEnum(),e));
        this.setTargetDataSources(nextDBConnectionMap);
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return dataSourceContextHolder.getBranchContext();
    }
}
