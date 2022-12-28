package routingTask.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import routingTask.entity.DbConnection;

import java.util.List;

@Repository
public interface DataSourceRepository extends CrudRepository<DbConnection, String> {
    @Query(value = "select * from decrypted()")
    List<DbConnection> getConnections();

    @Query(value = "SELECT id, url, username, PGP_SYM_DECRYPT(password\\:\\:bytea, 'AES_KEY') as password " +
            " FROM dbconnections where id in (:countries)")
    List<DbConnection> findConnectionsByCountries(List<String> countries);

    @Transactional
    @Modifying
    @Query(value = "call insert_data(:id,:url,:username,:password)")
    void addConnection(String id, String url, String username, String password);
}