package routing_task.routing;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;
import routing_task.contextHolder.DataSourceContextHolder;
import routing_task.service.DataSourceService;

import java.util.HashMap;
import java.util.Map;

import static routing_task.context.DataSourceEnum.*;

@Component
@RequiredArgsConstructor
public class DataSourceRouting extends AbstractRoutingDataSource {
    private DataSourceContextHolder dataSourceContextHolder;
    private DataSourceService service;


    public DataSourceRouting(DataSourceContextHolder dataSourceContextHolder) {
        this.dataSourceContextHolder = dataSourceContextHolder;
        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put(DATASOURCE_ONE, service.selectAll().get(0));
        dataSourceMap.put(DATASOURCE_TWO, service.selectAll().get(1));
        dataSourceMap.put(DATASOURCE_THREE, service.selectAll().get(2));
        this.setTargetDataSources(dataSourceMap);
        this.setDefaultTargetDataSource(service.selectAll().get(0));
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return dataSourceContextHolder.getBranchContext();
    }

//    public DataSource dataSourceOneDataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        DataSource dataSource1 = service.selectAll().get(0);
//        dataSource.setUrl(dataSource1.getUrl());
//        dataSource.setUsername(dataSource1.getUsername());
//        dataSource.setPassword(dataSource1.getPassword());
//        return dataSource1;
//    }
//
//    public DataSource dataSourceTwoDataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        DataSource dataSource2 = service.selectAll().get(1);
//        dataSource.setUrl(dataSourceTwoConfig.getUrl());
//        dataSource.setUsername(dataSourceTwoConfig.getUsername());
//        dataSource.setPassword(dataSourceTwoConfig.getPassword());
//        return dataSource2;
//    }
//
//    public DataSource dataSourceThreeDataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        DataSource dataSource3 = service.selectAll().get(0);
//        dataSource.setUrl(dataSourceThreeConfig.getUrl());
//        dataSource.setUsername(dataSourceThreeConfig.getUsername());
//        dataSource.setPassword(dataSourceThreeConfig.getPassword());
//        return dataSource3;
//    }

}
