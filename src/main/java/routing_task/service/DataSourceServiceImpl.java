package routing_task.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import routing_task.config.DataSource;
import routing_task.repository.DataSourceRepository;

import java.util.List;
@Service
@RequiredArgsConstructor
public class DataSourceServiceImpl implements DataSourceService{
    private final DataSourceRepository dataSourceRepository;
    @Override
    public List<DataSource> selectAll() {
        return dataSourceRepository.findAll();
    }
}
