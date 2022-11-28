package routingTask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import routingTask.entity.DbConnection;

import java.util.List;

@Repository
public interface DataSourceRepository extends JpaRepository<DbConnection,String> {
    @Query(value="select * from decrypted()", nativeQuery=true)
    List<DbConnection> getConnections();

    @Query(value = " call insert_data(?1,?2,?3,?4)",nativeQuery = true)
    void addConnection(String id,String url, String username, String password);
}
