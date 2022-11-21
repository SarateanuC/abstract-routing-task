package routing_task.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import routing_task.contextHolder.DataSourceContextHolder;
import routing_task.main.entity.NextDBConnection;
import routing_task.main.repository.NextDbConnectionRepository;
import routing_task.targetEntities.service.StudentService;


@Service
@EnableScheduling
@RequiredArgsConstructor
public class ConnectionService {
    private final NextDbConnectionRepository dataSourceRepository;
    private final DataSourceContextHolder dataSourceContextHolder;
    private final StudentService service;


    @Scheduled(fixedDelay = 3500000 )
    private void createDataSource() {
        for (NextDBConnection database:dataSourceRepository.findAll()) {
            dataSourceContextHolder.setBranchContext(database);
            System.out.println(database.getUrl());
           // service.save();
           // System.out.println(service.getAll());
            //dataSourceContextHolder.clearBranchContext();
    }
}}
