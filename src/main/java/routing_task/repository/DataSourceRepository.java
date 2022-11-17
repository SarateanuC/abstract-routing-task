package routing_task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import routing_task.config.DataSource;

import java.util.UUID;

public interface DataSourceRepository extends JpaRepository<DataSource, UUID> {
}
