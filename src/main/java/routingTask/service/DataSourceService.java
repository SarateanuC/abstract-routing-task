package routingTask.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import routingTask.dto.UserAddRequestDto;
import routingTask.entity.DbConnection;
import routingTask.repository.DataSourceRepository;
import routingTask.repository.UserRepository;
import routingTask.requestDto.DboConnectionAddRequest;
import routingTask.routing.DataSourceRouting;

import java.util.List;

import static routingTask.entity.User.builder;

@Service
@EnableScheduling
@RequiredArgsConstructor
public class DataSourceService {
    private final DataSourceRepository dataSourceRepository;
    private final DataSourceRouting dataSourceRouting;
    private final UserRepository userRepository;

    //@Transactional(rollbackFor = {Exception.class, ConstraintViolationException.class}, propagation = Propagation.REQUIRED)
    public void insertUser(List<UserAddRequestDto> userAddRequestDtos) {
        for (DbConnection database : dataSourceRepository.getConnections()) {
            dataSourceRouting.addConnection(database);
            userAddRequestDtos.stream().filter(
                            user -> user.getNationality().matches(database.getId()))
                    .map(user -> builder()
                            .first_name(user.getFirst_name())
                            .gender(user.getSex())
                            .last_name(user.getLast_name())
                            .password(user.getPassword())
                            .birth_Date(user.getDate_of_birth())
                            .user_name(user.getUser_name())
                            .nationality(user.getNationality())
                            .build())
                    .forEach(userRepository::save);
            dataSourceRouting.closeConnection();
        }
    }

    public void insertDataSource(DboConnectionAddRequest dbConnection) {
        dataSourceRepository.addConnection(
                dbConnection.getId(),
                dbConnection.getUrl(),
                dbConnection.getUsername(),
                dbConnection.getPassword()
        );
    }

    public void removeDataSource(String source_id) {
        dataSourceRepository.deleteById(source_id);
    }

    public List<DbConnection> getAllDataSources() {
        return dataSourceRepository.findAll();
    }

//    public List<DbConnection> getDecrypted() {
//        return dataSourceRepository.getConnections();
//    }
}

