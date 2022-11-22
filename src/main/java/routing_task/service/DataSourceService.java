package routing_task.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import routing_task.entity.DbConnection;
import routing_task.entity.Student;
import routing_task.repository.DataSourceRepostiory;
import routing_task.repository.StudentRepository;
import routing_task.routing.DataSourceRouting;

@Service
@EnableScheduling
@RequiredArgsConstructor
public class DataSourceService {
    private final DataSourceRepostiory dataSourceRepostiory;
    private final DataSourceRouting dataSourceRouting;
    private final StudentRepository studentRepository;

    @Scheduled(fixedDelayString = "PT1S")
    public void insertInto() {
//        dataSourceRepostiory.findById("First").ifPresent(dataSourceRouting::addConnection);
        for (DbConnection database : dataSourceRepostiory.findAll()) {
            dataSourceRouting.addConnection(database);
            studentRepository.save(Student.builder().firstname("Test").build());
            dataSourceRouting.closeConnection();
        }
//        studentRepository.save(Student.builder().firstname("Radu").build());
//        dataSourceRouting.closeConnection();
    }
}

