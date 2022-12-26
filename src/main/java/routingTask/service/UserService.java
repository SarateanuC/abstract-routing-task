package routingTask.service;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.jta.JtaTransactionManager;
import routingTask.dto.UserAddRequestDto;
import routingTask.entity.DbConnection;
import routingTask.entity.User;
import routingTask.exception.NoSuchCountryException;
import routingTask.repository.DataSourceRepository;
import routingTask.repository.UserRepository;
import routingTask.routing.DataSourceRouting;

import javax.transaction.UserTransaction;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserService {
    private final DataSourceRouting dataSourceRouting;
    private final DataSourceRepository dbConnectionsRepository;
    private final UserRepository userRepository;
    private final UserTransactionImp userTransaction;
@Transactional
    @SneakyThrows
    public void insertUser(List<UserAddRequestDto> userAddRequestDtos) {
        val countries = userAddRequestDtos.stream()
                .map(UserAddRequestDto::getNationality)
                .distinct()
                .collect(toList());
        val connectionsByCountries = dbConnectionsRepository.findConnectionsByCountries(countries);
        if (connectionsByCountries.isEmpty()) {
            throw new NoSuchCountryException();
        }
//        Map<String, List<User>> collect = userAddRequestDtos.stream()
//                .map(user -> User.builder()
//                        .firstName(user.getFirstName())
//                        .gender(user.getGender())
//                        .lastName(user.getLastName())
//                        .password(user.getPassword())
//                        .birthdate(user.getBirthdate())
//                        .userName(user.getUserName())
//                        .nationality(user.getNationality())
//                        .build()).collect(groupingBy(User::getNationality));
        connectionsByCountries.forEach(c -> saveUser(c, userAddRequestDtos));

//        try {
//            userTransaction.begin();
//            connectionsByCountries.forEach(c -> saveUser(c, userAddRequestDtos));
//            userTransaction.commit();
//        } catch (Exception e) {
//            System.out.println("rollback");
//            userTransaction.rollback();
//        }
    }

    private void saveUser(DbConnection connection, List<UserAddRequestDto> userAddRequestDtos) {
        dataSourceRouting.addConnection(connection);
        val collect = userAddRequestDtos.stream()
                .filter(u->u.getNationality().equals(connection.getId()))
                .map(user -> User.builder()
                        .firstName(user.getFirstName())
                        .nationality(user.getNationality())
                        .lastName(user.getLastName())
                        .gender(user.getGender())
                        .password(user.getPassword())
                        .birthdate(user.getBirthdate())
                        .userName(user.getUserName())
                        .build())
                .collect(toList());
        userRepository.saveAll(collect);
        dataSourceRouting.closeConnection();

    }
}



