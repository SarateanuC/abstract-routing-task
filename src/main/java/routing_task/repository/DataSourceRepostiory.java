package routing_task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import routing_task.entity.DbConnection;

@Repository
public interface DataSourceRepostiory extends JpaRepository<DbConnection,String> {
}
