package routingTask.service;

import com.atomikos.icatch.jta.UserTransactionImp;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;
import org.springframework.stereotype.Service;
import routingTask.dto.UserAddRequestDto;
import routingTask.entity.DbConnection;
import routingTask.entity.User;
import routingTask.repository.DataSourceRepository;
import routingTask.repository.UserRepository;
import routingTask.routing.DataSourceRouting;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserService {
    private final DataSourceRouting dataSourceRouting;
    private final DataSourceRepository dbConnectionsRepository;
    private final UserRepository userRepository;
    private final UserTransactionImp userTransaction;

    @SneakyThrows
    public void insertUser(List<UserAddRequestDto> userAddRequestDtos) {
        val countries = userAddRequestDtos.stream()
                .map(UserAddRequestDto::getNationality)
                .distinct()
                .collect(toList());
        val connectionsByCountries = dbConnectionsRepository.findConnectionsByCountries(countries);
//        Map<String, List<User>> collect = userAddRequestDtos.stream().map(user -> User.builder()
//                        .firstName(user.getFirstName())
//                        .nationality(user.getNationality())
//                        .lastName(user.getLastName())
//                        .gender(user.getGender())
//                        .password(user.getPassword())
//                        .birthdate(user.getBirthdate())
//                        .userName(user.getUserName())
//                        .build())
//                .collect(Collectors.groupingBy(User::getNationality));

        try {
            userTransaction.begin();
            connectionsByCountries.forEach(c -> saveUser(c, userAddRequestDtos));
            userTransaction.commit();
        } catch (Exception e) {
            System.out.println("rollback");
            userTransaction.setRollbackOnly();
            throw e;
        }
        dataSourceRouting.closeConnection();

    }

    private void saveUser(DbConnection connection, List<UserAddRequestDto> userList) {
        dataSourceRouting.addConnection(connection);
        val collect = userList.stream()
                .filter(u -> u.getNationality().equals(connection.getId()))
                .map(u -> User.builder()
                        .firstName(u.getFirstName())
                        .nationality(u.getNationality())
                        .lastName(u.getLastName())
                        .gender(u.getGender())
                        .password(u.getPassword())
                        .birthdate(u.getBirthdate())
                        .userName(u.getUserName())
                        .build())
                .collect(toList());
        userRepository.saveAll(collect);
        dataSourceRouting.closeConnection();
    }
}



