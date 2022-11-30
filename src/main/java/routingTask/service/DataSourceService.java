package routingTask.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import routingTask.entity.DbConnection;
import routingTask.repository.DataSourceRepository;
import routingTask.requestDto.DboConnectionAddRequest;

import java.util.List;

@Service
@EnableScheduling
@RequiredArgsConstructor
public class DataSourceService {
    private final DataSourceRepository dataSourceRepository;

    public void insertDataSource(DboConnectionAddRequest dbConnection) {
        dataSourceRepository.addConnection(
                dbConnection.getId(),
                dbConnection.getUrl(),
                dbConnection.getUsername(),
                dbConnection.getPassword()
        );
    }

    public void removeDataSource(String source_id) {
        dataSourceRepository.deleteById(source_id);
    }

    public List<DbConnection> getAllDataSources() {
        return dataSourceRepository.findAll();
    }
}

