package routing_task.service;

import routing_task.config.DataSource;

import java.util.List;

public interface DataSourceService {
    List<DataSource> selectAll();
}
