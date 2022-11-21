package routing_task.config;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import routing_task.main.entity.NextDBConnection;
import routing_task.main.repository.NextDbConnectionRepository;

@Service
@EnableScheduling
@RequiredArgsConstructor
public class Test {
    private final NextDbConnectionRepository nextDbConnectionRepository;

    @Scheduled(fixedDelay = 1000)
    public void scheduleFixedDelayTask() {
      //  for (NextDBConnection database: nextDbConnectionRepository.findAll()){
        //    dataSourceContextHolder.setBranchContext(dataSourceType);
            //System.out.println(database.getDataSourceEnum());
        }
    }

