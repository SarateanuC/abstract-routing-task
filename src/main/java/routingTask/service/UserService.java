package routingTask.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;
import org.springframework.stereotype.Service;
import routingTask.dto.UserAddRequestDto;
import routingTask.entity.User;
import routingTask.exception.NoSuchCountryException;
import routingTask.repository.DataSourceRepository;
import routingTask.repository.UserRepository;
import routingTask.routing.DataSourceRouting;

import javax.transaction.UserTransaction;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserService {
    private final DataSourceRepository dataSourceRepository;
    //private final SaveUsersService saveUsersService;
    private final UserTransaction userTransaction;
    private final DataSourceRouting dataSourceRouting;
    private final UserRepository userRepository;

    @SneakyThrows
    public void insertUser(List<UserAddRequestDto> userAddRequestDtos) {
        val countries = userAddRequestDtos.stream()
                .map(UserAddRequestDto::getNationality)
                .distinct()
                .collect(toList());
        val connectionsByCountries = dataSourceRepository.findConnectionsByCountries(countries);
        if (connectionsByCountries.isEmpty()) {
            throw new NoSuchCountryException();
        }
        dataSourceRouting.createConnections(connectionsByCountries);
        if (connectionsByCountries.isEmpty()) {
            throw new NoSuchCountryException();
        }
        Map<String, List<User>> collect = userAddRequestDtos.stream()
                .map(user -> User.builder()
                        .firstName(user.getFirstName())
                        .gender(user.getGender())
                        .lastName(user.getLastName())
                        .password(user.getPassword())
                        .birthdate(user.getBirthdate())
                        .userName(user.getUserName())
                        .nationality(user.getNationality())
                        .build()).collect(Collectors.groupingBy(User::getNationality));
        try {
            userTransaction.begin();
            collect.forEach(this::addListOfUsersToDb);
        } catch (Exception e) {
            userTransaction.setRollbackOnly();
        }
        dataSourceRouting.closeConnection();
    }

        private void addListOfUsersToDb (String dbId, List < User > usersToBeAdded){
            dataSourceRouting.setCountry(dbId);
            userRepository.saveAll(usersToBeAdded);
        }
    }



