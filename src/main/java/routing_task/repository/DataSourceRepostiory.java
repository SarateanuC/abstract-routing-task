package routing_task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import routing_task.entity.DbConnection;

import java.util.List;

@Repository
public interface DataSourceRepostiory extends JpaRepository<DbConnection,String> {
    @Query(value="select * from decrypted()", nativeQuery=true)
    List<DbConnection> getConnections();
}
