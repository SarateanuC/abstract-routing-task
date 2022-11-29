package routingTask.service;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import routingTask.dto.UserAddRequestDto;
import routingTask.entity.DbConnection;
import routingTask.repository.DataSourceRepository;
import routingTask.repository.UserRepository;
import routingTask.requestDto.DboConnectionAddRequest;
import routingTask.routing.DataSourceRouting;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static routingTask.entity.User.builder;

@Service
@EnableScheduling
@RequiredArgsConstructor
public class DataSourceService {
    private final DataSourceRepository dataSourceRepository;
    private final DataSourceRouting dataSourceRouting;
    private final UserRepository userRepository;

    @Transactional
    public void insertUser(List<UserAddRequestDto> userAddRequestDtos) {
        val countries = userAddRequestDtos.stream()
                .map(UserAddRequestDto::getNationality)
                .distinct()
                .collect(toList());

        val connectionsByCountries = dataSourceRepository.findConnectionsByCountries(countries);
        connectionsByCountries.forEach(c -> saveUser(c, userAddRequestDtos));
    }

    private void saveUser(DbConnection connection, List<UserAddRequestDto> userAddRequestDtos) {
        dataSourceRouting.addConnection(connection);
        val collect = userAddRequestDtos.stream()
                .map(user -> builder()
                        .firstName(user.getFirstName())
                        .gender(user.getSex())
                        .last_name(user.getLast_name())
                        .password(user.getPassword())
                        .birth_Date(user.getDate_of_birth())
                        .user_name(user.getUser_name())
                        .nationality(user.getNationality())
                        .build())
                .collect(toList());
        userRepository.saveAll(collect);
        dataSourceRouting.closeConnection();
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

