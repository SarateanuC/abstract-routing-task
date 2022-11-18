package routing_task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import routing_task.config.DataSource;

import java.util.UUID;
@Repository
public interface DataSourceRepository extends JpaRepository<DataSource, UUID> {
}
