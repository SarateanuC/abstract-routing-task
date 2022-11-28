package routingTask.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import routingTask.entity.DbConnection;
import routingTask.entity.Student;
import routingTask.repository.DataSourceRepository;

import routingTask.repository.StudentRepository;
import routingTask.requestDto.DboConnectionAddRequest;
import routingTask.routing.DataSourceRouting;

import java.util.List;

@Service
@EnableScheduling
@RequiredArgsConstructor
public class DataSourceService {
    private final DataSourceRepository dataSourceRepository;
    private final DataSourceRouting dataSourceRouting;
    private final StudentRepository studentRepository;

    @Scheduled(fixedDelayString = "PT50S")
    public void insertStudent() {
        for (DbConnection database : dataSourceRepository.getConnections()) {
            dataSourceRouting.addConnection(database);
            studentRepository.save(Student.builder().firstname("Cornel4").build());
            dataSourceRouting.closeConnection();
        }
    }

    public void insertDataSource(DboConnectionAddRequest dbConnection){
        dataSourceRepository.addConnection(
                dbConnection.getId(),
                dbConnection.getUrl(),
                dbConnection.getUsername(),
                dbConnection.getPassword()
        );
    }

    public void removeDataSource(String source_id){
        dataSourceRepository.deleteById(source_id);
    }

    public List<DbConnection> getAllDataSources(){
        return dataSourceRepository.findAll();
    }

}

