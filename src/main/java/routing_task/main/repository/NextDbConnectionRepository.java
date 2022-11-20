package routing_task.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import routing_task.config.enums.DataSourceEnum;
import routing_task.main.entity.NextDBConnection;

public interface NextDbConnectionRepository extends JpaRepository<NextDBConnection, DataSourceEnum> {
}
