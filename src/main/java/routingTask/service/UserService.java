package routingTask.service;

import com.atomikos.icatch.jta.UserTransactionImp;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.jta.JtaTransactionManager;
import routingTask.dto.UserAddRequestDto;
import routingTask.entity.User;
import routingTask.exception.NoSuchCountryException;
import routingTask.repository.DataSourceRepository;
import routingTask.repository.UserRepository;
import routingTask.routing.DataSourceRouting;

import javax.transaction.Transaction;
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
    private final UserRepository usersRepository;
    //private final PlatformTransactionManager platformTransactionManager;
    private final UserTransactionImp userTransaction;

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
        dataSourceRouting.createConnections(connectionsByCountries);
        System.out.println("connected");
        Map<String, List<User>> collect = userAddRequestDtos.stream()
                .map(user -> User.builder()
                        .firstName(user.getFirstName())
                        .gender(user.getGender())
                        .lastName(user.getLastName())
                        .password(user.getPassword())
                        .birthdate(user.getBirthdate())
                        .userName(user.getUserName())
                        .nationality(user.getNationality())
                        .build()).collect(groupingBy(User::getNationality));

//        JtaTransactionManager transactionManager = (JtaTransactionManager) platformTransactionManager;
//        Transaction test = transactionManager.createTransaction("test", 3000);

        try {
            collect.forEach(this::addListOfUsersToDb);
        } catch (Exception e) {
            System.out.println("rollback");
            userTransaction.setRollbackOnly();
            throw e;
        }
        userTransaction.commit();
        dataSourceRouting.closeConnection();
    }

    @SneakyThrows
    private void addListOfUsersToDb(String dbId, List<User> usersToBeAdded) {
        userTransaction.begin();
        dataSourceRouting.setCountry(dbId);
        usersRepository.saveAll(usersToBeAdded);
    }
}



